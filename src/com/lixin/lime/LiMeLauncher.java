package com.lixin.lime;

import com.lixin.lime.client.controller.LiMe;

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
                LiMe lime = new LiMe();
                lime.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
