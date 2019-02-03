package com.lixin.lime.server.controller;

import com.lixin.lime.server.view.LiMeServerFrame;
import com.lixin.lime.server.model.LiMeServerModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.lixin.lime.protocol.seed.LiMeSeed.*;
import static com.lixin.lime.protocol.util.factory.MyStaticFactory.*;

/**
 * @author lixin
 */
public class LiMeServerController implements Runnable, ActionListener, LiMeServerFarmer {

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
        serverModel = new LiMeServerModel(this);
        cachedThreadPool = Executors.newCachedThreadPool();
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
        switch (e.getActionCommand()) {
            case SERVER_ACTION_START:
                // START Server
                cachedThreadPool.execute(serverModel);
                break;
            case SERVER_ACTION_STOP:
                // TODO: STOP Server
                // cachedThreadPool.shutdownNow();
                // System.exit(0);
                break;
            case SERVER_ACTION_KICK:
                serverModel.sendSeedStatus(user, ERROR_ADMIN_KICKED);
                // log UI
                serverFrame.appendLog("[" + time + "]\n< " + user + " > is KICKED.\n");
                break;
            case SERVER_ACTION_BAN:
                serverModel.sendSeedStatus(user, ERROR_ADMIN_BANNED);
                // Ban User in database
                serverModel.ban(user);
                // log UI
                serverFrame.appendLog("[" + time + "]\n< " + user + " > is BANNED.\n");
                break;
            case SERVER_ACTION_CLEAR_LOG:
                serverFrame.clearLog();
                break;
            case SERVER_ACTION_CLEAR_HISTORY:
                serverFrame.clearHistory();
                break;
            default:
                limeInternalError(this.getClass().getCanonicalName(), e.getActionCommand());
                break;
        }
    }

    @Override
    public void newChatHistory(String sender, String time, String message) {
        serverFrame.appendHistory("< " + sender + " > | < " + time + " >\n" + message + "\n");
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
}

