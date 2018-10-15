package com.lixin.lime;

import com.lixin.lime.server.controller.LiMeServer;

public class LiMeServerLauncher {
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            LiMeServer server = new LiMeServer();
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
