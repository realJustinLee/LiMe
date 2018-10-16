package com.lixin.lime.client.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static com.lixin.lime.util.factory.MyStaticFactory.*;

/**
 * @author lixin
 */
public class LiMeChatFrame extends JFrame {

    private JPanel contentPane;
    private JButton buttonLogout;

    /**
     * Create the frame.
     */
    public LiMeChatFrame(String username) {
        setTitle(THE_BRAND + " @" + username);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(300, 140, 1080, 720);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel labelTitle = new JLabel("Lixin Messenger");
        labelTitle.setForeground(new Color(153, 50, 204));
        labelTitle.setFont(new Font("Harry P", Font.BOLD, 50));
        labelTitle.setBounds(6, 5, 242, 60);
        contentPane.add(labelTitle);

        JLabel labelUsername = new JLabel("用户名");
        labelUsername.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        labelUsername.setBounds(705, 24, 54, 42);
        contentPane.add(labelUsername);

        JTextField textFieldUsername = new JTextField("@" + username);
        textFieldUsername.setEnabled(false);
        textFieldUsername.setEditable(false);
        textFieldUsername.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        textFieldUsername.setColumns(10);
        textFieldUsername.setBounds(770, 24, 242, 42);
        contentPane.add(textFieldUsername);

        JList listFriends = new JList();
        listFriends.setBounds(6, 78, 200, 614);
        contentPane.add(listFriends);

        JSeparator separatorV = new JSeparator();
        separatorV.setOrientation(SwingConstants.VERTICAL);
        separatorV.setBounds(218, 78, 12, 614);
        contentPane.add(separatorV);

        JTextArea textAreaHistory = new JTextArea();
        textAreaHistory.setBounds(242, 78, 832, 430);
        contentPane.add(textAreaHistory);

        JSeparator separatorH = new JSeparator();
        separatorH.setBounds(242, 520, 832, 12);
        contentPane.add(separatorH);

        JTextArea textAreaMessage = new JTextArea();
        textAreaMessage.setBounds(242, 544, 832, 107);
        contentPane.add(textAreaMessage);

        buttonLogout = new JButton("登出");
        buttonLogout.setForeground(Color.RED);
        buttonLogout.setActionCommand(ACTION_CHAT_LOGOUT);
        buttonLogout.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        buttonLogout.setBounds(1024, 33, 50, 29);
        contentPane.add(buttonLogout);

        JButton buttonSendFile = new JButton("传文件");
        buttonSendFile.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        buttonSendFile.setBounds(835, 663, 117, 29);
        contentPane.add(buttonSendFile);

        JButton buttonSendMessage = new JButton("发送");
        buttonSendMessage.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        buttonSendMessage.setBounds(957, 663, 117, 29);
        contentPane.add(buttonSendMessage);
    }

    public JButton getButtonLogout() {
        return buttonLogout;
    }
}
