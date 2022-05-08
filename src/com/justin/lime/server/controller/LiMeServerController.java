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
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.justin.lime.protocol.seed.LiMeSeed.*;
import static com.justin.lime.protocol.util.factory.LiMeStaticFactory.*;

/**
 * @author Justin Lee
 */
public class LiMeServerController implements Runnable, ActionListener, LiMeServerFarmer, LiMeServerKnight {
    private Properties properties;

    private int port;
    private String db_host;
    private int db_port;
    private String db_db;
    private String db_username;
    private String db_password;
    private String email_user;
    private String email_domain;
    private String email_password;
    private LiMeServerFrame serverFrame;
    private LiMeServerModel serverModel;

    private ExecutorService cachedThreadPool;


    /**
     * Create the application.
     */
    public LiMeServerController() {
        try {
            properties = new Properties();
            readFromConfigFile();
            serverFrame = new LiMeServerFrame(this);
            serverModel = new LiMeServerModel(port, db_host, db_port, db_db, db_username, db_password, email_user, email_domain, email_password, this, this);
        } catch (LiMeException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void writeToConfigFile() throws LiMeException {
        try {
            FileWriter writer = new FileWriter(SERVER_CONFIG_FILE_PATH);
            properties.store(writer, PROP_SERVER_COMMENT);
        } catch (IOException e) {
            throw LiMeExceptionFactory.newLiMeException(ERROR_CONFIG_FILE);
        }
    }

    private void initConfigFile() throws LiMeException {
        port = DEFAULT_PORT;
        db_host = LOCALHOST;
        db_port = DEFAULT_DB_PORT;
        db_db = "";
        db_username = "";
        db_password = "";
        email_user = "";
        email_domain = "";
        email_password = "";
        properties.setProperty(PROP_NAME_SERVER_PORT, String.valueOf(port));
        properties.setProperty(PROP_NAME_SERVER_DB_HOST, db_host);
        properties.setProperty(PROP_NAME_SERVER_DB_PORT, String.valueOf(db_port));
        properties.setProperty(PROP_NAME_SERVER_DB_DB, db_db);
        properties.setProperty(PROP_NAME_SERVER_DB_USERNAME, db_username);
        properties.setProperty(PROP_NAME_SERVER_DB_PASSWORD, db_password);
        properties.setProperty(PROP_NAME_SERVER_EMAIL_USER, email_user);
        properties.setProperty(PROP_NAME_SERVER_EMAIL_DOMAIN, email_domain);
        properties.setProperty(PROP_NAME_SERVER_EMAIL_PASSWORD, email_password);
        writeToConfigFile();
    }

    private void readFromConfigFile() throws LiMeException {
        try {
            FileReader reader = new FileReader(SERVER_CONFIG_FILE_PATH);
            properties.load(reader);
            port = Integer.parseInt(properties.getProperty(PROP_NAME_SERVER_PORT, String.valueOf(DEFAULT_PORT)));
            db_host = properties.getProperty(PROP_NAME_SERVER_DB_HOST, LOCALHOST);
            db_port = Integer.parseInt(properties.getProperty(PROP_NAME_SERVER_DB_PORT, String.valueOf(DEFAULT_PORT)));
            db_db = properties.getProperty(PROP_NAME_SERVER_DB_DB);
            db_username = properties.getProperty(PROP_NAME_SERVER_DB_USERNAME);
            db_password = properties.getProperty(PROP_NAME_SERVER_DB_PASSWORD);
            email_user = properties.getProperty(PROP_NAME_SERVER_EMAIL_USER);
            email_domain = properties.getProperty(PROP_NAME_SERVER_EMAIL_DOMAIN);
            email_password = properties.getProperty(PROP_NAME_SERVER_EMAIL_PASSWORD);
        } catch (FileNotFoundException e) {
            initConfigFile();
            throw LiMeExceptionFactory.newLiMeException(ERROR_UPDATE_CONFIG);
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
                    writeToConfigFile();
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

