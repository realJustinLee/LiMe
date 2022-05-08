package com.justin.lime.client.controller;

import com.justin.lime.client.model.LiMeModel;
import com.justin.lime.client.view.LiMeChatFrame;
import com.justin.lime.client.view.LiMeLoginFrame;
import com.justin.lime.client.view.LiMeRegisterFrame;
import com.justin.lime.protocol.exception.LiMeException;
import com.justin.lime.protocol.seed.LiMeSeed;
import com.justin.lime.protocol.seed.LiMeSeedFile;
import com.justin.lime.protocol.seed.LiMeSeedMessage;
import com.justin.lime.protocol.seed.LiMeSeedRespond;
import com.justin.lime.protocol.util.factory.LiMeExceptionFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

import static com.justin.lime.protocol.seed.LiMeSeed.ERROR_CONFIG_FILE;
import static com.justin.lime.protocol.seed.LiMeSeed.LIME_GROUP_CHAT;
import static com.justin.lime.protocol.util.factory.LiMeStaticFactory.*;

/**
 * @author Justin Lee
 */
public class LiMeController implements Runnable, ActionListener, LiMeFarmer, LiMeKnight {
    private Properties properties;

    /**
     * The variables
     */
    private String host;
    private int port;
    private String username;
    private String receiver;
    private String password;
    private boolean savePassword;

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
        try {
            properties = new Properties();
            decryptAndReadFromFile();
            model = new LiMeModel(host, port, this, this);
            model.connectToServer();
            loginFrame = new LiMeLoginFrame(this, username, password, savePassword);
        } catch (LiMeException e) {
            handleLiMeException(e);
            System.exit(0);
        }
    }

    private void initChatFrame() {
        chatFrame = new LiMeChatFrame(username, this);
        HashMap<String, String> history = chatFrame.getHistory();
        history.put(LIME_GROUP_CHAT, "");
        // Update UI from history
        chatFrame.updateTextAreaHistory();
    }

    private void encryptAndWriteToFile() throws LiMeException {
        try {
            if (savePassword) {
                String randomKey = generatePasswordAndKey();
                String encryptedKey = encrypt(randomKey);
                String encryptedUsername = encrypt(username, randomKey);
                String encryptedPassword = encrypt(password, randomKey);
                properties.setProperty(PROP_NAME_LIME_ENCRYPTED_KEY, encryptedKey);
                properties.setProperty(PROP_NAME_LIME_ENCRYPTED_USERNAME, encryptedUsername);
                properties.setProperty(PROP_NAME_LIME_ENCRYPTED_PASSWORD, encryptedPassword);
            }
            FileWriter writer = new FileWriter(CLIENT_CONFIG_FILE_PATH);
            properties.store(writer, PROP_LIME_COMMENT);
        } catch (IOException e) {
            throw LiMeExceptionFactory.newLiMeException(ERROR_CONFIG_FILE);
        }
    }

    private void decryptAndReadFromFile() throws LiMeException {
        try {
            FileReader reader = new FileReader(CLIENT_CONFIG_FILE_PATH);
            properties.load(reader);
            host = properties.getProperty(PROP_NAME_LIME_HOST, LOCALHOST);
            port = Integer.parseInt(properties.getProperty(PROP_NAME_LIME_PORT, String.valueOf(DEFAULT_PORT)));
            String encryptedKey = properties.getProperty(PROP_NAME_LIME_ENCRYPTED_KEY);
            String encryptedUsername = properties.getProperty(PROP_NAME_LIME_ENCRYPTED_USERNAME);
            String encryptedPassword = properties.getProperty(PROP_NAME_LIME_ENCRYPTED_PASSWORD);
            savePassword = encryptedKey != null;
            if (savePassword) {
                String randomKey = decrypt(encryptedKey);
                username = encryptedUsername == null ? "" : decrypt(encryptedUsername, randomKey);
                password = encryptedPassword == null ? "" : decrypt(encryptedPassword, randomKey);
            }
        } catch (FileNotFoundException e) {
            host = LOCALHOST;
            port = DEFAULT_PORT;
        } catch (IOException e) {
            throw LiMeExceptionFactory.newLiMeException(ERROR_CONFIG_FILE);
        }
    }

    // Actions

    private synchronized void actionLoginLogin() throws LiMeException {
        // 用户名、密码有无校验
        if (loginFrame.getUsername().isEmpty()) {
            limeWarning("请输入用户名");
        } else if (loginFrame.getPassword().isEmpty()) {
            limeWarning("请输入密码");
        } else {
            username = loginFrame.getUsername();
            password = loginFrame.getPassword();
            savePassword = loginFrame.savePassword();
            // login() throws LiMeException
            initChatFrame();
            model.login(username, password);
            // 登录信息正确才写入文件
            encryptAndWriteToFile();
            loginFrame.dispose();
            chatFrame.setVisible(true);
        }
    }

    private void actionLoginRegister() {
        registerFrame = new LiMeRegisterFrame(this);
        registerFrame.setVisible(true);
    }

    private void actionLoginForgotPassword() throws LiMeException {
        username = loginFrame.getUsername();
        if (username.isEmpty()) {
            limeWarning("请输入用户名");
        } else {
            model.sendRequestForgotPassword(username);
            limeWarning("一封密码重置邮件已发送至您预留的邮箱，请查收");
        }
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
            model.register(username, password, gender, email);
            limeInfo("注册成功，即将转向登录界面");
            registerFrame.dispose();
            loginFrame.setUsername(username);
            loginFrame.setPassword(password);
            loginFrame.setVisible(true);
        }
    }

    private void actionRegisterCancel() {
        registerFrame.dispose();
    }

    private synchronized void actionChatLogout() throws LiMeException {
        // logout() throws LiMeException
        model.logout(username);
        chatFrame.dispose();
        loginFrame = new LiMeLoginFrame(this, username, password, savePassword);
        loginFrame.setVisible(true);
        limeInfo("已成功登出 " + THE_BRAND);
    }

    private void actionChatSendMessage() throws LiMeException {
        // 发送消息
        receiver = chatFrame.getReceiver();
        JTextArea textAreaMessage = chatFrame.getTextAreaMessage();
        String message = textAreaMessage.getText();
        model.sendMessage(username, receiver, message);
        textAreaMessage.setText(null);
        // 写入历史
        HashMap<String, String> history = chatFrame.getHistory();
        String msgLog = history.get(receiver) + "< " + username + " > | < " + getLiMeTime() + " >\n" + message + "\n\n";
        history.put(receiver, msgLog);
        // Update UI from history
        chatFrame.updateTextAreaHistory();
    }

    private void actionChatSendFile() throws LiMeException {
        // 发送文件
        receiver = chatFrame.getReceiver();
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.showDialog(new JLabel(), "选择");
        File file = chooser.getSelectedFile();
        if (file != null) {
            model.sendFile(username, receiver, file);
            // Log UI
            HashMap<String, String> history = chatFrame.getHistory();
            String msgLog = history.get(receiver) + "< " + username + " > | < " + getLiMeTime() + " >\n" + file.getName() + "\n\n";
            history.put(receiver, msgLog);
            // Update UI from history
            chatFrame.updateTextAreaHistory();
        }
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
                case ACTION_LOGIN_LOGIN -> actionLoginLogin();
                case ACTION_LOGIN_REGISTER -> actionLoginRegister();
                case ACTION_LOGIN_FORGOT_PASSWORD -> actionLoginForgotPassword();
                case ACTION_REGISTER_REGISTER -> actionRegisterRegister();
                case ACTION_REGISTER_CANCEL -> actionRegisterCancel();
                case ACTION_CHAT_LOGOUT -> actionChatLogout();
                case ACTION_CHAT_SEND_MESSAGE -> actionChatSendMessage();
                case ACTION_CHAT_SEND_FILE -> actionChatSendFile();
                default -> limeInternalError(this.getClass().getCanonicalName(), actionCommand);
            }
        } catch (LiMeException ex) {
            handleLiMeException(ex);
        }
    }

    @Override
    public void newLiMeMessage(LiMeSeed seed) {
        LiMeSeedMessage seedMessage = (LiMeSeedMessage) seed;
        String sender = seedMessage.getSender();
        // The sender here is the sender of the Message == the receiver in the ChatFrame
        String encryptedTime = seedMessage.getTime();
        // encryptedMessage = encrypt(encrypt(encrypt(message, encryptedTime), sender), receiver);
        // TODO: username != key 时抛出LiMeException，catch 后写入 Log
        String message = decrypt(decrypt(decrypt(seedMessage.getMessage(), username), sender), encryptedTime);
        String time = decrypt(encryptedTime);
        HashMap<String, String> history = chatFrame.getHistory();
        String msgLog = history.get(sender) + "< " + sender + " > | < " + time + " >\n" + message + "\n\n";
        history.put(sender, msgLog);
        // Update UI from history
        chatFrame.updateTextAreaHistory();
    }

    @Override
    public void newFriendList(LiMeSeed seed) {
        LiMeSeedRespond seedRespond = (LiMeSeedRespond) seed;
        HashMap<String, String> history = chatFrame.getHistory();
        HashSet<String> friendList = new HashSet<>(history.keySet());
        HashSet<String> newFriendList = seedRespond.getFriendList();
        // Add new friends to oldList
        for (String friend : newFriendList) {
            if (!friendList.contains(friend)) {
                history.put(friend, "");
            }
        }
        // Remove ex-friends from oldList
        for (String friend : friendList) {
            if (friend.equals(LIME_GROUP_CHAT)) {
                continue;
            }
            if (!newFriendList.contains(friend)) {
                history.remove(friend);
            }
        }
        // Remove self from friend list
        history.remove(username);
        // Update UI from history
        chatFrame.updateListFriends();
    }

    @Override
    public synchronized void newLiMeFile(LiMeSeed seed) {
        // recv and store the file
        LiMeSeedFile seedFile = (LiMeSeedFile) seed;
        File fileRecv = seedFile.getFile();
        // 选择路径
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showDialog(new JLabel(), "选择文件夹");
        File folder = chooser.getSelectedFile();
        // 文件夹路径不存在
        if (!folder.exists() && !folder.isDirectory()) {
            boolean res = folder.mkdirs();
        }
        try {
            // 如果文件不存在就创建
            File fileDest = new File(folder.getAbsolutePath() + "/" + fileRecv.getName());
            System.out.println(fileDest.getAbsolutePath());
            if (!fileDest.exists()) {
                boolean res = fileDest.createNewFile();
            }
            // 写入文件
            FileChannel inputChannel = new FileInputStream(fileRecv).getChannel();
            FileChannel outputChannel = new FileOutputStream(fileDest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
            inputChannel.close();
            outputChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Log UI
        String time = getLiMeTime();
        HashMap<String, String> history = chatFrame.getHistory();
        String sender = seed.getSender();
        String msgLog = history.get(sender) + "< " + sender + " > | < " + time + " >\n" + fileRecv.getName() + "\n\n";
        history.put(sender, msgLog);
        // Update UI from history
        chatFrame.updateTextAreaHistory();
    }

    @Override
    public void handleLiMeException(LiMeException e) {
        limeExternalError(e.getDetail(), e.getMessage());
    }

    @Override
    public void newGroupChat(LiMeSeed seed) {
        LiMeSeedMessage seedMessage = (LiMeSeedMessage) seed;
        String sender = seedMessage.getSender();
        if (sender.equals(username)) {
            return;
        }
        String encryptedTime = seedMessage.getTime();
        // encryptedMessage = encrypt(encrypt(encrypt(message, encryptedTime), sender), LIME_GROUP_CHAT);
        // TODO: username != key 时抛出 LiMeException，catch 后写入 Log
        String message = decrypt(decrypt(decrypt(seedMessage.getMessage(), LIME_GROUP_CHAT), sender), encryptedTime);
        String time = decrypt(encryptedTime);
        HashMap<String, String> history = chatFrame.getHistory();
        String msgLog = history.get(LIME_GROUP_CHAT) + "< " + sender + " > || < " + time + " >\n" + message + "\n\n";
        history.put(LIME_GROUP_CHAT, msgLog);
        // Update UI from history
        chatFrame.updateTextAreaHistory();
    }
}
