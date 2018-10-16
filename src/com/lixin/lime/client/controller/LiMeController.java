package com.lixin.lime.client.controller;


import com.lixin.lime.client.gui.LiMeChatFrame;
import com.lixin.lime.client.gui.LiMeLoginFrame;
import com.lixin.lime.client.gui.LiMeRegisterFrame;
import com.lixin.lime.util.crypto.AesCipher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import static com.lixin.lime.util.factory.MyStaticFactory.*;

/**
 * @author lixin
 */
public class LiMeController implements Runnable, ActionListener {
    /**
     * The password file
     */
    private final File passwordFile = new File("0xCafeBabe.lime");

    /**
     * The variables
     */
    private String username;
    /**
     * TODO: password 改成 char[] 来提升安全性
     */
    private String password;

    /**
     * The Frames
     */
    private LiMeLoginFrame loginFrame;
    private LiMeRegisterFrame registerFrame;
    private LiMeChatFrame chatFrame;

    /**
     * Create the application.
     */
    public LiMeController() {
        initialize();
    }

    /**
     * Initialize the LiMeLoginFrame.
     */
    private void initialize() {
        try {
            // Login Frame init
            loginFrame = new LiMeLoginFrame();
            loginFrame.getBtnLogin().addActionListener(this);
            loginFrame.getBtnRegister().addActionListener(this);
            loginFrame.getBtnFindPassword().addActionListener(this);
            decryptAndReadFromFile(passwordFile);

            // Register Frame init
            registerFrame = new LiMeRegisterFrame();
            registerFrame.getBtnRegister().addActionListener(this);

            // Chat Frame init
            chatFrame = new LiMeChatFrame();

            connectToServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        loginFrame.setVisible(true);
    }

    /**
     * Connect to the Server.
     */
    private void connectToServer() {
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
            boolean savePassword = loginFrame.savePassword();
            String cryptUsername = savePassword ? AesCipher.aesEncryptString(username, GOLDEN_KEY) : "";
            String cryptPassword = savePassword ? AesCipher.aesEncryptString(password, GOLDEN_KEY) : "";
            bufferedWriter.write(savePassword ? "true" : "false");
            bufferedWriter.write("\n");
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
            String savePassword = bufferedReader.readLine();
            String cryptUsername = bufferedReader.readLine();
            String cryptPassword = bufferedReader.readLine();
            boolean bool = "true".equals(savePassword);
            username = cryptUsername == null ? "" : AesCipher.aesDecryptString(cryptUsername, GOLDEN_KEY);
            password = cryptPassword == null ? "" : AesCipher.aesDecryptString(cryptPassword, GOLDEN_KEY);
            loginFrame.savePassword(bool);
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
            case ACTION_LOGIN:
                // 用户名、密码有无校验
                username = loginFrame.getUsername();
                password = loginFrame.getPassword();
                if (username.isEmpty()) {
                    limeWarning("请输入用户名");
                } else if (password.isEmpty()) {
                    limeWarning("请输入密码");
                } else {
                    encryptAndWriteToFile(passwordFile, username, password);

                    /*
                     * TODO: 登陆校验
                     *  发送 Json 文件到服务器
                     *  服务器将注册信息写入数据库 (Json action : login)
                     *  服务器返回 Json 文件批准登陆 (Json action : approve, arg: login)
                     */

                    boolean loggedIn = true;

                    if (loggedIn) {
                        chatFrame.setVisible(true);
                    }

                }
                break;
            case ACTION_REGISTER:
                registerFrame.setVisible(true);
                registerFrame.clearUI();
                break;
            case ACTION_FIND_PASSWORD:
                emailAdmin();
                break;
            case ACTION_COMMIT_REGISTER:
                // 用户名、密码、email有无校验
                username = registerFrame.getUsername();
                password = registerFrame.getPassword();
                String email = registerFrame.getEmail();
                if (username.isEmpty()) {
                    limeWarning("用户名不得为空");
                } else if (password.isEmpty()) {
                    limeWarning("密码不得为空");
                } else if (email.isEmpty()) {
                    limeWarning("Email不得为空");
                } else {
                    // TODO: 注册，发送Json文件到服务器，服务器将注册信息写入数据库 (Json action : register)

                    boolean registered;

                }
                break;
            default:
                JOptionPane.showMessageDialog(null,
                        "错误地址：" + this.getClass().getCanonicalName(),
                        "发生未知错误",
                        JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
}
