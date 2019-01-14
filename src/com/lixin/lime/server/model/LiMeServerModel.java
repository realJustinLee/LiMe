package com.lixin.lime.server.model;

import com.lixin.lime.protocol.datastructure.LiMeStalk;
import com.lixin.lime.protocol.seed.*;
import com.lixin.lime.server.controller.LiMeServerFarmer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.lixin.lime.protocol.seed.LiMeSeed.*;
import static com.lixin.lime.protocol.util.factory.MyStaticFactory.*;

/**
 * @author lixin
 */
public class LiMeServerModel implements Runnable {
    private HashMap<String, LiMeStalk> limeHub;
    private LiMeServerFarmer serverFarmer;

    private ExecutorService cachedThreadPool;

    public LiMeServerModel(LiMeServerFarmer serverFarmer) {
        limeHub = new HashMap<>();
        this.serverFarmer = serverFarmer;
        cachedThreadPool = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSock = new ServerSocket(PORT);
            while (true) {
                try {
                    Socket socketLime = serverSock.accept();
                    cachedThreadPool.execute(new ServerSeedGrinder(socketLime));
                    System.out.println("got a connection");
                    // TODO: Log connection count to UI
                    //  HAVE to Build UI in advance

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean verify(String username, String password) {
        // TODO: Verify Login from Database[MySql Server]

        return true;
    }

    private boolean register(String username, String password, String gender, String email) {
        // TODO: Register User to Database[MySql Server]

        return true;
    }


    private class ServerSeedGrinder implements Runnable {
        private Socket socketLime;
        private ObjectOutputStream oos;
        private ObjectInputStream ois;
        private boolean loggedIn;
        private String username;


        ServerSeedGrinder(Socket socketLime) {
            try {
                this.socketLime = socketLime;
                oos = new ObjectOutputStream(socketLime.getOutputStream());
                ois = new ObjectInputStream(socketLime.getInputStream());
                loggedIn = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            LiMeSeed seed;
            try {
                while ((seed = (LiMeSeed) ois.readObject()) != null) {
                    int action = seed.getAction();
                    switch (action) {
                        case MESSAGE:
                            LiMeSeedMessage seedMessage = (LiMeSeedMessage) seed;
                            LiMeStalk receiverStalk = limeHub.get(seedMessage.getReceiver());
                            receiverStalk.getOos().writeObject(seedMessage);
                            receiverStalk.getOos().flush();
                            // TODO: 如果发生了 Exception 就表示用户掉线，则把用户从HashMap中踢掉
                            break;
                        case LOGIN:
                            // Login
                            LiMeSeedLogin seedLogin = (LiMeSeedLogin) seed;
                            username = seedLogin.getUsername();
                            if (verify(username, seedLogin.getPassword())) {
                                LiMeStalk stalk = new LiMeStalk(seedLogin.getUsername(), socketLime, ois, oos);
                                if (!limeHub.containsKey(username)) {
                                    limeHub.put(username, stalk);
                                    sendSeedStatus(STATUS_LOGIN_SUCCESS);
                                    // Log UI
                                    serverFarmer.newOnline(username);
                                } else {
                                    sendSeedStatus(ERROR_LOGIN_CONFLICT);
                                }
                            } else {
                                sendSeedStatus(ERROR_LOGIN_PASSWORD);
                            }
                            break;
                        case LOGOUT:
                            actionLogout();
                            break;
                        case REGISTER:
                            LiMeSeedRegister seedRegister = (LiMeSeedRegister) seed;
                            if (register(seedRegister.getUsername(), seedRegister.getPassword(), seedRegister.getGender(), seedRegister.getEmail())) {
                                sendSeedStatus(STATUS_REGISTER_SUCCESS);
                            } else {
                                sendSeedStatus(ERROR_REGISTER_CONFLICT);
                            }
                            break;
                        case RECEIVER_IP:
                            // Respond receiver_ip
                            LiMeSeedRequest seedRequest = (LiMeSeedRequest) seed;
                            String receiverIp = limeHub.get(seedRequest.getReceiver()).getSocket().getInetAddress().getHostAddress();
                            sendSeedRespond(FRIENDS_UPDATE, null, seedRequest.getSender(), receiverIp, null);
                            break;
                        case FRIENDS_UPDATE:
                            // TODO: (V2.0)Return the user's friends
                            LiMeSeedRequest request = (LiMeSeedRequest) seed;
                            // Return all online
                            sendSeedRespond(FRIENDS_UPDATE, null, request.getSender(), null, limeHub.keySet());
                            break;
                        default:
                            limeInternalError(this.getClass().getCanonicalName(), String.valueOf(action));
                            break;
                    }
                }
            } catch (Exception e) {
                actionLogout();
                e.printStackTrace();
            }
        }

        private void actionLogout() {
            // Logout: remove the LiMeStalk from the limeHub
            LiMeStalk stalk = limeHub.get(username);
            try {
                stalk.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            limeHub.remove(username);
            // Log UI
            serverFarmer.newOffline(username);
        }

        private void sendSeedStatus(int status) throws IOException {
            oos.writeObject(new LiMeSeedStatus(status));
            oos.flush();
        }

        private void sendSeedRespond(int type, String sender, String receiver, String message, Set<String> limeSet) throws IOException {
            oos.writeObject(new LiMeSeedRespond(type, sender, receiver, message, limeSet));
            oos.flush();
        }
    }
}
