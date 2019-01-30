package com.lixin.lime.server.model;

import com.lixin.lime.protocol.datastructure.LiMeStalk;
import com.lixin.lime.protocol.seed.*;
import com.lixin.lime.server.controller.LiMeServerFarmer;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
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
                    serverFarmer.enablePrivileges(true);
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

    public void sendSeedStatus(String username, int status) {
        LiMeStalk stalk = limeHub.get(username);
        ObjectOutputStream oos = stalk.getOos();
        try {
            oos.writeObject(new LiMeSeedStatus(status));
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            removeLime(username);
        }
    }

    private void broadcastFriendList() throws IOException {
        // TODO: 下个版本发准确的朋友列表
        HashSet<String> keySet = new HashSet<>(limeHub.keySet());
        for (LiMeStalk stalk : limeHub.values()) {
            ObjectOutputStream oos = stalk.getOos();
            oos.writeObject(new LiMeSeedRespond(FRIENDS_UPDATE, null, null, null, keySet));
            oos.flush();
        }
    }

    // *************************************************************************************
    // private methods
    // *************************************************************************************

    private boolean verify(String username, String password) {
        // TODO: Verify Login from Database[MySql Server]

        return true;
    }

    private boolean register(String username, String password, String gender, String email) {
        // TODO: Register User to Database[MySql Server]

        return true;
    }

    private void removeLime(String username) {
        // Logout: remove the LiMeStalk from the limeHub
        LiMeStalk stalk = limeHub.get(username);
        try {
            stalk.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        limeHub.remove(username);
        // Log UI
        HashSet<String> keySet = new HashSet<>(limeHub.keySet());
        serverFarmer.newOffline(username, keySet);
        if (keySet.isEmpty()) {
            serverFarmer.enablePrivileges(false);
        }
    }

    private class ServerSeedGrinder implements Runnable {
        private Socket socketLime;
        private ObjectOutputStream oos;
        private ObjectInputStream ois;
        private String username;


        ServerSeedGrinder(Socket socketLime) {
            try {
                this.socketLime = socketLime;
                oos = new ObjectOutputStream(socketLime.getOutputStream());
                ois = new ObjectInputStream(socketLime.getInputStream());
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
                            // 如果发生了 Exception 就表示用户掉线，则把用户从HashMap中踢掉
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
                                    HashSet<String> keySet = new HashSet<>(limeHub.keySet());
                                    serverFarmer.newOnline(username, keySet);
                                    // broadcast friendList
                                    broadcastFriendList();
                                } else {
                                    sendSeedStatus(ERROR_LOGIN_CONFLICT);
                                }
                            } else {
                                sendSeedStatus(ERROR_LOGIN_PASSWORD);
                            }
                            break;
                        case LOGOUT:
                            removeLime(username);
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
                            HashSet<String> keySet = new HashSet<>(limeHub.keySet());
                            sendSeedRespond(FRIENDS_UPDATE, null, request.getSender(), null, keySet);
                            break;
                        default:
                            limeInternalError(this.getClass().getCanonicalName(), String.valueOf(action));
                            break;
                    }
                }
            } catch (EOFException e) {
                removeLime(username);
                System.out.println("lost a connection");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void sendSeedStatus(int status) throws IOException {
            oos.writeObject(new LiMeSeedStatus(status));
            oos.flush();
        }

        private void sendSeedRespond(int type, String sender, String receiver, String message, HashSet<String> limeSet) throws IOException {
            oos.writeObject(new LiMeSeedRespond(type, sender, receiver, message, limeSet));
            oos.flush();
        }
    }
}