package com.justin.lime;

import com.justin.lime.client.controller.LiMeController;

import java.awt.*;

/**
 * @author Justin Lee
 */
public class LiMe {
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
