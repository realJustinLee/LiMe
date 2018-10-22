package com.lixin.lime.client.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author lixin
 */
public class LiMeFileTransferFrame extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LiMeFileTransferFrame frame = new LiMeFileTransferFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public LiMeFileTransferFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(660, 440, 360, 120);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setBounds(6, 6, 348, 20);
        contentPane.add(progressBar);

        JLabel labelProgress = new JLabel("0MB/1MB");
        labelProgress.setBounds(6, 38, 61, 16);
        contentPane.add(labelProgress);

        JButton buttonOk = new JButton("完成");
        buttonOk.setEnabled(false);
        buttonOk.setBounds(108, 63, 117, 29);
        contentPane.add(buttonOk);

        JButton buttonCancel = new JButton("取消");
        // 乱写进度条的
        buttonCancel.addActionListener(e -> {
            int val = progressBar.getValue();
            progressBar.setValue(++val);
        });
        buttonCancel.setBounds(237, 63, 117, 29);
        contentPane.add(buttonCancel);
    }
}
