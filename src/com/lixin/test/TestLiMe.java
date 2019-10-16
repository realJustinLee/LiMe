package com.lixin.test;


import com.lixin.lime.client.controller.LiMeController;

import java.awt.*;

/**
 * @author lixin
 */
public class TestLiMe {
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
