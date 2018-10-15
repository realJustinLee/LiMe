package com.lixin.lime.client.controller;


import com.lixin.lime.client.gui.LoginFrame;
import com.lixin.lime.client.util.crypto.AesCipher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * @author lixin
 */
public class LiMe implements ActionListener {
    /**
     * Statics
     * GOLDEN_KEY : A-16-Byte-String
     */
    private static final String GOLDEN_KEY = "FuckYouMicrosoft";

    /**
     * The variables
     */
    private final File passwordFile = new File("0xCafeBabe.lime");
    private String username;
    private String password;

    /**
     * The Frames
     */
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
                loginFrame.getBtnLogin().addActionListener(this);
                decryptAndReadFromFile(passwordFile);
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


    private void encryptAndWriteToFile(File file, String username, String password) {
        try {
            if (!file.exists()) {
                Boolean res = file.createNewFile();
            }
            //true = append file
            FileWriter fileWriter = new FileWriter(file.getName(), false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String cryptUsername = username == null ? "" : AesCipher.aesEncryptString(username, GOLDEN_KEY);
            String cryptPassword = password == null ? "" : AesCipher.aesEncryptString(password, GOLDEN_KEY);
            bufferedWriter.write(cryptUsername);
            bufferedWriter.write("\n");
            bufferedWriter.write(cryptPassword);
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decryptAndReadFromFile(File file) {
        try {
            if (!file.exists()) {
                Boolean res = file.createNewFile();
            }
            FileReader fileReader = new FileReader(file.getName());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String cryptUsername = bufferedReader.readLine();
            String cryptPassword = bufferedReader.readLine();
            String username = cryptUsername == null ? "" : AesCipher.aesDecryptString(cryptUsername, GOLDEN_KEY);
            String password = cryptPassword == null ? "" : AesCipher.aesDecryptString(cryptPassword, GOLDEN_KEY);
            loginFrame.setUsername(username);
            loginFrame.setPassword(password);
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "login":
                // 用户名、密码有无校验
                username = loginFrame.getUsername();
                password = loginFrame.getPassword();
                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "请输入用户名");
                } else if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "请输入密码");
                } else {
                    if (loginFrame.savePassword()) {
                        encryptAndWriteToFile(passwordFile, username, password);
                    } else {
                        encryptAndWriteToFile(passwordFile, null, null);
                    }

                    /*
                     * TODO: 登陆校验
                     *  发送 Json 文件到服务器
                     *  服务器将注册信息写入数据库 (Json action : login)
                     *  服务器返回 Json 文件批准登陆 (Json action : approve, arg: login)
                     */

                }
                break;
            default:
                JOptionPane.showMessageDialog(null, "错误地址：" + this.getClass().getCanonicalName(), "发生未知错误", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
}
