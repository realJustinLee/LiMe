package com.justin.lime.server.model;

import com.justin.lime.protocol.datastructure.LiMeStalk;
import com.justin.lime.protocol.entity.User;
import com.justin.lime.protocol.seed.*;
import com.justin.lime.protocol.util.crypto.LiMeCipher;
import com.justin.lime.server.controller.LiMeServerFarmer;
import com.justin.lime.server.controller.LiMeServerKnight;
import com.justin.lime.server.dao.LiMeDatabaseConnector;
import com.justin.lime.server.mailbox.LiMeServerMailBox;
import com.justin.lime.server.mailbox.MailAccountGmail;

import javax.mail.MessagingException;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.justin.lime.protocol.seed.LiMeSeed.*;
import static com.justin.lime.protocol.util.crypto.LiMeCipher.digest;
import static com.justin.lime.protocol.util.crypto.LiMeCipher.generatePasswordAndKey;
import static com.justin.lime.protocol.util.factory.LiMeStaticFactory.*;

/**
 * @author Justin Lee
 */
public class LiMeServerModel implements Runnable {
    private final LiMeCipher cipher;

    private final int port;
    private final String db_host;
    private final int db_port;
    private final String db_db;
    private final String db_username;
    private final String db_password;
    private final String email_user;
    private final String email_domain;
    private final String email_password;

    private final LiMeServerFarmer serverFarmer;
    private final LiMeServerKnight serverKnight;

    private final HashMap<String, LiMeStalk> limeHub;
    private ServerSocket serverSocket;
    private Connection connection;
    private LiMeServerMailBox mailBox;

    private final ExecutorService cachedThreadPool;

    public LiMeServerModel(LiMeCipher cipher,
                           int port,
                           String db_host,
                           int db_port,
                           String db_db,
                           String db_username,
                           String db_password,
                           String email_user,
                           String email_domain,
                           String email_password,
                           LiMeServerFarmer serverFarmer,
                           LiMeServerKnight serverKnight) {
        this.cipher = cipher;
        this.port = port;
        this.db_host = db_host;
        this.db_port = db_port;
        this.db_db = db_db;
        this.db_username = db_username;
        this.db_password = db_password;
        this.email_user = email_user;
        this.email_domain = email_domain;
        this.email_password = email_password;
        this.serverFarmer = serverFarmer;
        this.serverKnight = serverKnight;

        limeHub = new HashMap<>();
        cachedThreadPool = Executors.newCachedThreadPool();
    }

    @Override
    public synchronized void run() {
        // init sql
        try {
            LiMeDatabaseConnector databaseConnector = new LiMeDatabaseConnector(db_host, db_port, db_db, db_username, db_password);
            connection = databaseConnector.getConnection();
            mailBox = new LiMeServerMailBox(
                    new MailAccountGmail(email_user, email_domain, email_password)
            );
            // init socket
            serverSocket = new ServerSocket(port);
            System.out.println("ServerSocket initialized @ " + port);
            while (!serverSocket.isClosed()) {
                Socket socketLime = serverSocket.accept();
                cachedThreadPool.execute(new ServerSeedGrinder(socketLime));
                System.out.println("Got a connection");
                serverFarmer.enablePrivileges(true);
                // TODO: Log connection count to UI
                //  HAVE to Build UI in advance

            }
        } catch (BindException e) {
            e.printStackTrace();
            limeInternalError(this.getClass().getCanonicalName(), "Port " + port + " is occupied");
        } catch (SocketException e) {
            if (serverSocket.isClosed()) {
                System.out.println("ServerSocket closed @ " + port);
                limeInfo("ServerSocket closed");
            } else {
                e.printStackTrace();
                limeInternalError(this.getClass().getCanonicalName(), e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Database connection failure.");
            e.printStackTrace();
            limeInternalError(this.getClass().getCanonicalName(), THE_SERVER_BRAND + " unable to connect to database.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void sendSeedStatus(String username, int status) {
        LiMeStalk stalk = limeHub.get(username);
        ObjectOutputStream oos = stalk.getOos();
        try {
            oos.writeObject(new LiMeSeedStatus(status));
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            removeLime(username, true);
        }
    }

    public void ban(String username) throws SQLException {
        // email user that he/she is banned
        String subject = "Account Banned!";
        String content = "Your account: " + username + " is permanently banned, due to the violation of multiple agreements!";
        emailUser(username, subject, content);
        // Ban User to Database[MySql Server]
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE `users` SET `banned` = TRUE WHERE `username` = ?;");
        preparedStatement.setString(1, username);
        preparedStatement.executeUpdate();
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    //  private methods
    //
    ////////////////////////////////////////////////////////////////////////////

    private boolean verify(User user) {
        // Verify Login from Database[MySql Server]
        try {
            String username = user.getUsername();
            // 不可与系统服务重名
            if (username.equals(LIME_GROUP_CHAT)) {
                return false;
            }
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT `password` FROM `users` WHERE `username` = ? AND `banned` = FALSE;");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1).equals(digest(user.getPassword()));
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean register(User user) {
        // Register User to Database[MySql Server]
        try {
            String username = user.getUsername();
            // 不可与系统服务重名
            if (username.equals(LIME_GROUP_CHAT)) {
                return false;
            }
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO `users`(`username`, `password`, `gender`, `email`) VALUES (?, ?, ?, ?);");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, digest(user.getPassword()));
            preparedStatement.setString(3, user.getGender());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.executeUpdate();
            String subject = "LiMe Register Success!";
            String content = "Congratulations, " + username + "!\n" +
                    "You are a Nobel LiMe user now!";
            emailUser(username, subject, content);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void resetPassword(String username, String password) {
        // Update User's Password to Database[MySql Server]
        try {
            // 不可与系统服务重名
            if (username.equals(LIME_GROUP_CHAT)) {
                return;
            }
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE `users` SET `password` = ? WHERE `username` = ?;");
            preparedStatement.setString(1, digest(cipher.encrypt(password)));
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 向客户发送包含新密码的 Email
        String subject = "Password Reset!";
        String content = "Your password is successfully reset to:\n" + password;
        emailUser(username, subject, content);
    }

    private void emailUser(String username, String subject, String content) {
        // Request email address from Database[MySql Server]
        String email = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT `email` FROM `users` WHERE username = ? AND banned = FALSE;");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                email = resultSet.getString(1);
            }
            if (email != null) {
                // email user
                mailBox.sendSimpleMail(email, subject, content);
            }
        } catch (SQLException | MessagingException e) {
            e.printStackTrace();
        }
    }

    private void broadcastFriendList() throws IOException {
        // TODO: A more accurate friend list in the next version.
        HashSet<String> keySet = new HashSet<>(limeHub.keySet());
        for (LiMeStalk stalk : limeHub.values()) {
            ObjectOutputStream oos = stalk.getOos();
            oos.writeObject(new LiMeSeedRespond(FRIENDS_UPDATE, null, null, null, keySet));
            oos.flush();
        }
    }

    private void removeLime(String username, boolean closeSocket) {
        // Logout: remove the LiMeStalk from the limeHub
        if (limeHub.containsKey(username)) {
            LiMeStalk stalk = limeHub.get(username);
            if (closeSocket) {
                try {
                    stalk.getSocket().close();
                    stalk.getOis().close();
                    stalk.getOos().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            limeHub.remove(username);
            // Log UI
            HashSet<String> keySet = new HashSet<>(limeHub.keySet());
            serverFarmer.newOffline(username, keySet);
            if (keySet.isEmpty()) {
                serverFarmer.enablePrivileges(false);
            }
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
                        case MESSAGE -> {
                            LiMeSeedMessage seedMessage = (LiMeSeedMessage) seed;
                            String receiver = seedMessage.getReceiver();
                            if (!receiver.equals(LIME_GROUP_CHAT)) {
                                LiMeStalk receiverStalk = limeHub.get(receiver);
                                receiverStalk.getOos().writeObject(seedMessage);
                                receiverStalk.getOos().flush();
                            } else {
                                // Group Messaging
                                for (LiMeStalk stalk : limeHub.values()) {
                                    stalk.getOos().writeObject(seedMessage);
                                    stalk.getOos().flush();
                                }
                                // Log Group Chat History
                                serverKnight.newChatHistory(seed);
                            }
                        }
                        // If there's an Exception occurred, that means the user is offline.
                        // Then we should remove that user from HashMap.
                        case LOGIN -> {
                            // Login
                            LiMeSeedLogin seedLogin = (LiMeSeedLogin) seed;
                            username = seedLogin.getUsername();
                            if (verify(new User(username, seedLogin.getPassword()))) {
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
                        }
                        case LOGOUT -> removeLime(username, false);
                        case REGISTER -> {
                            LiMeSeedRegister seedRegister = (LiMeSeedRegister) seed;
                            if (register(new User(seedRegister.getUsername(), seedRegister.getPassword(), seedRegister.getGender(), seedRegister.getEmail()))) {
                                sendSeedStatus(STATUS_REGISTER_SUCCESS);
                            } else {
                                sendSeedStatus(ERROR_REGISTER_CONFLICT);
                            }
                        }
                        case RECEIVER_IP -> {
                            // Respond receiver_ip
                            LiMeSeedRequest seedRequest = (LiMeSeedRequest) seed;
                            String theReceiver = seedRequest.getReceiver();
                            String receiverIp = limeHub.get(theReceiver).getSocket().getInetAddress().getHostAddress();
                            sendSeedRespond(RECEIVER_IP, seedRequest.getSender(), theReceiver, receiverIp, null);
                        }
                        case FRIENDS_UPDATE -> {
                            // TODO: (V2.0)Return the User's friends
                            LiMeSeedRequest request = (LiMeSeedRequest) seed;
                            // Return all online
                            HashSet<String> keySet = new HashSet<>(limeHub.keySet());
                            sendSeedRespond(FRIENDS_UPDATE, null, request.getSender(), null, keySet);
                        }
                        case FILE -> {
                            // TODO: The current version of the server forwards messages from an user to another,
                            //  and we should let the clients establish independent socket connections in the next version.
                            LiMeSeedFile seedFile = (LiMeSeedFile) seed;
                            String fileReceiver = seedFile.getReceiver();
                            if (!fileReceiver.equals(LIME_GROUP_CHAT)) {
                                LiMeStalk limeStalk = limeHub.get(seedFile.getReceiver());
                                limeStalk.getOos().writeObject(seedFile);
                                limeStalk.getOos().flush();
                            } else {
                                // TODO: Allow group filing in the next version
                                System.out.println("Group File Request");
                            }
                        }
                        case FORGOT_PASSWORD -> {
                            LiMeSeedRequest liMeSeedRequest = (LiMeSeedRequest) seed;
                            username = liMeSeedRequest.getSender();
                            resetPassword(username, generatePasswordAndKey());
                        }
                        default -> limeInternalError(this.getClass().getCanonicalName(), String.valueOf(action));
                    }
                }
            } catch (EOFException e) {
                removeLime(username, true);
                System.out.println("Lost a connection");
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
