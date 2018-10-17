package com.lixin.lime.server.controller;

import com.lixin.lime.server.gui.LiMeServerFrame;
import com.lixin.lime.server.model.LiMeServerModel;

/**
 * @author lixin
 */
public class LiMeServerController implements Runnable {

    private LiMeServerFrame serverFrame;
    private LiMeServerModel serverModel;

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
        serverFrame = new LiMeServerFrame();
        serverModel = new LiMeServerModel();
    }

    @Override
    public void run() {
        serverFrame.setVisible(true);
    }
}

