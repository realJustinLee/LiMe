package com.justin.lime;

import com.justin.lime.server.controller.LiMeServerController;

/**
 * @author Justin Lee
 */
public class LiMeServer {
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
