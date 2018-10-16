package com.lixin.lime.server.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author lixin
 */
public class LiMeServerController implements Runnable {

    private JFrame frame;

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
        frame = new JFrame();
        frame.setResizable(false);
        frame.setTitle("LiMeController Server --> The Lixin Messenger Server");
        frame.setBounds(600, 320, 480, 360);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel label = new JLabel("LiMeController");
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
        label.setForeground(SystemColor.windowBorder);
        label.setFont(new Font("Harry P", Font.BOLD, 99));
        label.setBounds(157, 80, 166, 100);
        frame.getContentPane().add(label);

        JButton btnStart = new JButton("START");
        btnStart.addActionListener(e -> {
            // TODO: START Server

        });
        btnStart.setForeground(Color.BLUE);
        btnStart.setFont(new Font("Harry P", Font.PLAIN, 24));
        btnStart.setBounds(119, 250, 117, 36);
        frame.getContentPane().add(btnStart);

        JButton btnStop = new JButton("STOP");
        btnStop.addActionListener(e -> {
            // TODO: STOP Server

        });
        btnStop.setForeground(Color.RED);
        btnStop.setFont(new Font("Harry P", Font.PLAIN, 24));
        btnStop.setBounds(245, 250, 117, 36);
        frame.getContentPane().add(btnStop);
    }

    @Override
    public void run() {
        frame.setVisible(true);
    }
}

