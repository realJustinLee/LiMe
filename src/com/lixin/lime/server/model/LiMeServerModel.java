package com.lixin.lime.server.model;

import com.lixin.lime.protocol.datastructure.LiMeStalk;
import com.lixin.lime.protocol.seed.*;
import com.lixin.lime.server.controller.LiMeServerFarmer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.lixin.lime.protocol.seed.LiMeSeed.*;
import static com.lixin.lime.protocol.util.factory.MyStaticFactory.*;

/**
 * @author lixin
 */
public class LiMeServerModel implements Runnable {
    private ArrayList<LiMeStalk> limeHub;
    private LiMeServerFarmer serverFarmer;

    private ExecutorService cachedThreadPool;

    public LiMeServerModel(LiMeServerFarmer serverFarmer) {
        limeHub = new ArrayList<>();
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
                    // TODO: Log connection to UI

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
                            // TODO: pass Message
                            LiMeSeedMessage seedMessage = (LiMeSeedMessage) seed;


                            break;
                        case LOGIN:
                            // TODO: Login
                            LiMeSeedLogin seedLogin = (LiMeSeedLogin) seed;
                            if (verify(seedLogin.getUsername(), seedLogin.getPassword())) {
                                LiMeStalk stalk = new LiMeStalk(seedLogin.getUsername(), socketLime, ois, oos);
                                if (!limeHub.contains(stalk)) {
                                    limeHub.add(stalk);
                                    sendSeedStatus(STATUS_LOGIN_SUCCESS);
                                    // TODO: Log UI


                                } else {
                                    sendSeedStatus(ERROR_LOGIN_CONFLICT);
                                }
                            } else {
                                sendSeedStatus(ERROR_LOGIN_PASSWORD);
                            }
                            break;
                        case LOGOUT:
                            // Logout: remove the LiMeStalk from the limeHub
                            LiMeSeedLogout seedLogout = (LiMeSeedLogout) seed;
                            LiMeStalk stalk = new LiMeStalk(seedLogout.getUsername(), socketLime, ois, oos);
                            limeHub.remove(stalk);
                            // TODO: Log UI


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
                            // TODO: Respond receiver_ip
                            LiMeSeedRequest seedRequest = (LiMeSeedRequest) seed;


                            break;
                        case GET_FRIENDS:
                            // TODO: (V2.0)Return the user's friends
                            // TODO: Return all online
                            LiMeSeedRequest request = (LiMeSeedRequest) seed;

                            break;
                        default:
                            limeInternalError(this.getClass().getCanonicalName(), String.valueOf(action));
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                //TODO: Catch Exception


            }
        }

        private void sendSeedStatus(int status) throws IOException {
            oos.writeObject(new LiMeSeedStatus(status));
            oos.flush();
        }

        private void sendSeedRespond(int type, String sender, String receiver, String message, ArrayList<String> limeList) throws IOException {
            oos.writeObject(new LiMeSeedRespond(type, sender, receiver, message, limeList));
            oos.flush();
        }
    }
}
