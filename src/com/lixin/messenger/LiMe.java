package com.lixin.messenger;


import java.awt.*;

/**
 * @author lixin
 */
public class LiMe {

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
}
