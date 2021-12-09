package com.justin.lime.server.controller;

import com.justin.lime.protocol.exception.LiMeException;
import com.justin.lime.protocol.seed.LiMeSeed;
import com.justin.lime.protocol.seed.LiMeSeedMessage;
import com.justin.lime.protocol.util.factory.LiMeExceptionFactory;
import com.justin.lime.server.model.LiMeServerModel;
import com.justin.lime.server.view.LiMeServerFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.justin.lime.protocol.seed.LiMeSeed.*;
import static com.justin.lime.protocol.util.factory.LiMeStaticFactory.*;

/**
 * @author Justin Lee
 */
public class LiMeServerController implements Runnable, ActionListener, LiMeServerFarmer, LiMeServerKnight {

    private final File serverConfigFile = new File(SERVER_CONFIG_FILE_PATH);

    private int port;
    private LiMeServerFrame serverFrame;
    private LiMeServerModel serverModel;

    private ExecutorService cachedThreadPool;


    /**
     * Create the application.
     */
    public LiMeServerController() {
        try {
            decryptAndReadFromFile();
            serverFrame = new LiMeServerFrame(this);
            serverModel = new LiMeServerModel(port, this, this);
        } catch (LiMeException e) {
            e.printStackTrace();
        }
    }

    private void encryptAndWriteToFile() throws LiMeException {
        try {
            if (!serverConfigFile.exists() && !serverConfigFile.createNewFile()) {
                throw LiMeExceptionFactory.newLiMeException(ERROR_CONFIG_FILE);
            }
            FileWriter fileWriter = new FileWriter(serverConfigFile.getName(), false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(port + "\n");
            bufferedWriter.close();
        } catch (IOException e) {
            throw LiMeExceptionFactory.newLiMeException(ERROR_CONFIG_FILE);
        }
    }

    private void decryptAndReadFromFile() throws LiMeException {
        try {
            FileReader fileReader = new FileReader(serverConfigFile.getName());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            port = Integer.parseInt(bufferedReader.readLine());
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            port = DEFAULT_PORT;
        } catch (IOException e) {
            throw LiMeExceptionFactory.newLiMeException(ERROR_CONFIG_FILE);
        }
    }

    @Override
    public void run() {
        serverFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = serverFrame.getHighlightedLime();
        String time = getLiMeTime();
        try {
            switch (e.getActionCommand()) {
                case SERVER_ACTION_START -> {
                    // START Server
                    cachedThreadPool = Executors.newCachedThreadPool();
                    cachedThreadPool.execute(serverModel);
                }
                case SERVER_ACTION_STOP -> {
                    // STOP Server
                    ServerSocket serverSocket = serverModel.getServerSocket();
                    if (serverSocket != null && !serverSocket.isClosed()) {
                        serverSocket.close();
                    }
                    cachedThreadPool.shutdownNow();
                    encryptAndWriteToFile();
                }
                case SERVER_ACTION_KICK -> {
                    // Kick User out
                    serverModel.sendSeedStatus(user, ERROR_ADMIN_KICKED);
                    serverFrame.appendLog("[" + time + "]\n< " + user + " > is KICKED.\n");
                }
                case SERVER_ACTION_BAN -> {
                    // Ban User in database
                    serverModel.sendSeedStatus(user, ERROR_ADMIN_BANNED);
                    serverModel.ban(user);
                    serverFrame.appendLog("[" + time + "]\n< " + user + " > is BANNED.\n");
                }
                case SERVER_ACTION_CLEAR_LOG -> serverFrame.clearLog();
                case SERVER_ACTION_CLEAR_HISTORY -> serverFrame.clearHistory();
                default -> limeInternalError(this.getClass().getCanonicalName(), e.getActionCommand());
            }
        } catch (IOException | SQLException | LiMeException ex) {
            ex.printStackTrace();
            limeInternalError(this.getClass().getCanonicalName(), ex.getMessage());
        }
    }

    @Override
    public void newOnline(String username, HashSet<String> limeSet) {
        String time = getLiMeTime();
        serverFrame.appendLog("[" + time + "]\n< " + username + " > is on line.\n");
        serverFrame.updateListLimes(limeSet);
    }

    @Override
    public void newOffline(String username, HashSet<String> limeSet) {
        String time = getLiMeTime();
        serverFrame.appendLog("[" + time + "]\n< " + username + " > is off line.\n");
        serverFrame.updateListLimes(limeSet);
    }

    @Override
    public void enablePrivileges(boolean bool) {
        serverFrame.enablePrivileges(bool);
    }

    @Override
    public void newChatHistory(LiMeSeed seed) {
        LiMeSeedMessage seedMessage = (LiMeSeedMessage) seed;
        String sender = seedMessage.getSender();
        String encryptedTime = seedMessage.getTime();
        // encryptedMessage = encrypt(encrypt(encrypt(message, encryptedTime), sender), LIME_GROUP_CHAT);
        String message = decrypt(decrypt(decrypt(seedMessage.getMessage(), LIME_GROUP_CHAT), sender), encryptedTime);
        String time = decrypt(encryptedTime);
        serverFrame.appendHistory("< " + sender + " > | < " + time + " >\n" + message + "\n\n");
    }
}

