package com.lixin.lime.server.controller;

import com.lixin.lime.server.gui.LiMeServerFrame;
import com.lixin.lime.server.model.LiMeServerModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    }

    @Override
    public void run() {
        serverFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case SERVER_ACTION_START:
                // START Server
                cachedThreadPool.execute(serverModel);
                break;
            case SERVER_ACTION_STOP:
                // TODO: STOP Server
                // cachedThreadPool.shutdown();
                // System.exit(0);
                break;
            case SERVER_ACTION_KICK:

                break;
            case SERVER_ACTION_BAN:

                break;
            case SERVER_ACTION_CLEAR_LOG:

                break;
            case SERVER_ACTION_CLEAR_HISTORY:

                break;
            default:
                limeInternalError(this.getClass().getCanonicalName(), e.getActionCommand());
                break;
        }
    }
}

