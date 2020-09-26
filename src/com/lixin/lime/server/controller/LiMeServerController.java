package com.lixin.lime.server.controller;

import com.lixin.lime.protocol.seed.LiMeSeed;
import com.lixin.lime.protocol.seed.LiMeSeedMessage;
import com.lixin.lime.server.model.LiMeServerModel;
import com.lixin.lime.server.view.LiMeServerFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.lixin.lime.protocol.seed.LiMeSeed.*;
import static com.lixin.lime.protocol.util.factory.LiMeStaticFactory.*;

/**
 * @author lixin
 */
public class LiMeServerController implements Runnable, ActionListener, LiMeServerFarmer, LiMeServerKnight {

    private LiMeServerFrame serverFrame;
    private LiMeServerModel serverModel;

    private ExecutorService cachedThreadPool;


    /**
     * Create the application.
     */
    public LiMeServerController() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        initServerFrame();
        serverModel = new LiMeServerModel(this, this);
    }

    private void initServerFrame() {
        serverFrame = new LiMeServerFrame();
        serverFrame.getButtonStart().addActionListener(this);
        serverFrame.getButtonStop().addActionListener(this);
        serverFrame.getButtonKick().addActionListener(this);
        serverFrame.getButtonBan().addActionListener(this);
        serverFrame.getButtonClearLog().addActionListener(this);
        serverFrame.getButtonClearHistory().addActionListener(this);
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
        } catch (IOException | SQLException ex) {
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

