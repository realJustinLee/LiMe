package com.lixin.lime;

import com.lixin.lime.server.controller.LiMeServerController;

/**
 * @author lixin
 */
public class LiMeServerLauncher {
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            LiMeServerController server = new LiMeServerController();
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
