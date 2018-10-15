package com.lixin.lime.client.controller;


import com.lixin.lime.client.gui.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author lixin
 */
public class LiMe implements ActionListener {

    private LoginFrame loginFrame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LiMe app = new LiMe();
                app.connectServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public LiMe() {
        initialize();
    }

    /**
     * Initialize the LoginFrame.
     */
    private void initialize() {
        EventQueue.invokeLater(() -> {
            try {
                loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Connect to the Server.
     */
    private void connectServer() {
        // TODO: connect the server
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "login":
                JOptionPane.showMessageDialog(null, "OK", "Login", JOptionPane.OK_OPTION);
                break;
            default:
                JOptionPane.showMessageDialog(null, "错误地址：" + this.getClass().getCanonicalName(), "发生未知错误", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
}
