package com.lixin.lime.client.controller;


import com.lixin.lime.client.gui.LiMeChatFrame;
import com.lixin.lime.client.gui.LiMeLoginFrame;
import com.lixin.lime.client.gui.LiMeRegisterFrame;
import com.lixin.lime.client.model.LiMeModel;
import com.lixin.lime.protocol.exception.LiMeException;
import com.lixin.lime.protocol.seed.LiMeSeed;
import com.lixin.lime.protocol.seed.LiMeSeedMessage;
import com.lixin.lime.protocol.seed.LiMeSeedRespond;
import com.lixin.lime.protocol.util.crypto.AesCipher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

import static com.lixin.lime.protocol.util.factory.MyStaticFactory.*;

/**
 * @author lixin
 */
public class LiMeController implements Runnable, LiMeFarmer, ActionListener {
    /**
     * The password file
     */
    private final File passwordFile = new File(PASSWORD_FILE_PATH);

    /**
     * The variables
     */
    private String username;
    /**
     * TODO: password 改成 char[] 来提升安全性
     */
    private String password;

    /**
     * The Views(Frames)
     */
    private LiMeLoginFrame loginFrame;
    private LiMeRegisterFrame registerFrame;
    private LiMeChatFrame chatFrame;

    /**
     * The Model
     */
    private LiMeModel model;

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
        // 测试版本
        try {
            model = new LiMeModel(HOST, PORT, this);
            initLoginFrame();
            model.connectToServer();
        } catch (LiMeException e) {
            handleLiMeException(e);
            System.exit(0);
        }
    }

    private void initLoginFrame() {
        loginFrame = new LiMeLoginFrame();
        loginFrame.getButtonLogin().addActionListener(this);
        loginFrame.getButtonRegister().addActionListener(this);
        loginFrame.getButtonFindPassword().addActionListener(this);
        decryptAndReadFromFile(passwordFile);
    }

    private void initRegisterFrame() {
        registerFrame = new LiMeRegisterFrame();
        registerFrame.getBtnRegister().addActionListener(this);
        registerFrame.getBtnCancel().addActionListener(this);
    }

    private void initChatFrame() {
        chatFrame = new LiMeChatFrame(username);
        chatFrame.getButtonLogout().addActionListener(this);
        chatFrame.getButtonSendFile().addActionListener(this);
        chatFrame.getButtonSendMessage().addActionListener(this);
    }

    @Override
    public void run() {
        loginFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String actionCommand = e.getActionCommand();
            switch (actionCommand) {
                case ACTION_LOGIN_LOGIN:
                    actionLoginLogin();
                    break;
                case ACTION_LOGIN_REGISTER:
                    actionLoginRegister();
                    break;
                case ACTION_LOGIN_FIND_PASSWORD:
                    actionLoginFindPassword();
                    break;
                case ACTION_REGISTER_REGISTER:
                    actionRegisterRegister();
                    break;
                case ACTION_REGISTER_CANCEL:
                    actionRegisterCancel();
                    break;
                case ACTION_CHAT_LOGOUT:
                    actionChatLogout();
                    break;
                case ACTION_CHAT_SEND_MESSAGE:
                    actionChatSendMessage();
                    break;
                case ACTION_CHAT_SEND_FILE:
                    actionChatSendFile();
                    break;
                default:
                    limeInternalError(this.getClass().getCanonicalName(), actionCommand);
                    break;
            }
        } catch (LiMeException ex) {
            handleLiMeException(ex);
        }
    }

    @Override
    public void newLiMeMessage(LiMeSeed seed) {
        LiMeSeedMessage seedMessage = (LiMeSeedMessage) seed;
        // The sender here is the sender of the Message == the receiver in the ChatFrame
        String sender = seedMessage.getSender();
        String message = seedMessage.getMessage();
        String time = seedMessage.getTime();
        HashMap<String, String> history = chatFrame.getHistory();
        String msgLog = history.get(sender) + "< " + sender + " > | < " + time + " >\n" + message;
        history.put(sender, msgLog);
        // Update UI from history
        chatFrame.updateTextAreaHistory();
    }

    @Override
    public void updateFriendList(LiMeSeed seed) {
        LiMeSeedRespond seedRespond = (LiMeSeedRespond) seed;
        HashMap<String, String> history = chatFrame.getHistory();
        HashSet<String> friendList = (HashSet<String>) history.keySet();
        HashSet<String> newFriendList = (HashSet<String>) seedRespond.getFriendList();
        // Add new friends to oldList
        for (String friend : newFriendList) {
            if (!friendList.contains(friend)) {
                history.put(friend, "");
            }
        }
        // Remove ex-friends from oldList
        for (String friend : friendList) {
            if (!newFriendList.contains(friend)) {
                history.remove(friend);
            }
        }
        // Update UI from history
        chatFrame.updateListFriends();
    }

    @Override
    public void handleLiMeException(LiMeException e) {
        limeExternalError(e.getDetail(), e.getMessage());
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

    // Actions

    private void actionLoginLogin() throws LiMeException {
        // 用户名、密码有无校验
        username = loginFrame.getUsername();
        password = loginFrame.getPassword();
        if (username.isEmpty()) {
            limeWarning("请输入用户名");
        } else if (password.isEmpty()) {
            limeWarning("请输入密码");
        } else {
            // login() throws LiMeException
            if (model.login(username, password)) {
                // 登录信息正确才写入文件
                encryptAndWriteToFile(passwordFile, username, password);
                loginFrame.dispose();
                initChatFrame();
                chatFrame.setVisible(true);
            }
        }
    }

    private void actionLoginRegister() {
        initRegisterFrame();
        registerFrame.setVisible(true);
    }

    private void actionLoginFindPassword() {
        limeEmailAdmin();
    }

    private void actionRegisterRegister() throws LiMeException {
        // 用户名、密码、email有无校验
        username = registerFrame.getUsername();
        password = registerFrame.getPassword();
        String gender = registerFrame.getGender();
        String email = registerFrame.getEmail();
        if (username.isEmpty()) {
            limeWarning("用户名不得为空");
        } else if (password.isEmpty()) {
            limeWarning("密码不得为空");
        } else if (email.isEmpty()) {
            limeWarning("Email不得为空");
        } else {
            // register() throws LiMeException
            if (model.register(username, password, gender, email)) {
                limeInfo("注册成功，即将转向登录界面");
                registerFrame.dispose();
                loginFrame.setUsername(username);
                loginFrame.setPassword(password);
                loginFrame.setVisible(true);
            }
        }
    }

    private void actionRegisterCancel() {
        registerFrame.dispose();
    }

    private void actionChatLogout() {
        try {
            // logout() throws LiMeException
            model.logout(username);
            chatFrame.dispose();
            initLoginFrame();
            loginFrame.setVisible(true);
            limeInfo("已成功登出 " + THE_BRAND);

            // FIXME: 发布版本删除此句子
            System.exit(0);
        } catch (LiMeException ex) {
            handleLiMeException(ex);
            System.exit(0);
        }
    }

    private void actionChatSendMessage() throws LiMeException {
        // 发送消息
        String receiver = chatFrame.getReceiver();
        JTextArea textAreaMessage = chatFrame.getTextAreaMessage();
        String message = textAreaMessage.getText();
        model.sendMessage(username, receiver, message);
        textAreaMessage.setText(null);
        // 写入历史
        HashMap<String, String> history = chatFrame.getHistory();
        String msgLog = history.get(receiver) + "< " + receiver + " > | < " + getLiMeTime() + " >\n" + message;
        history.put(receiver, msgLog);
        // Update UI from history
        chatFrame.updateTextAreaHistory();
    }

    private void actionChatSendFile() {
        // TODO: 发送文件
        limeInfo("功能正在开发，敬请期待");
    }
}
