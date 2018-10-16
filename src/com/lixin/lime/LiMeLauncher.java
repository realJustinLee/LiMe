package com.lixin.lime;

import com.lixin.lime.client.controller.LiMeController;

import java.awt.*;

/**
 * @author lixin
 */
public class LiMeLauncher {
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LiMeController lime = new LiMeController();
                lime.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
