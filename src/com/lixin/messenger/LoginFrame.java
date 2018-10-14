package com.lixin.messenger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author lixin
 */
public class LoginFrame extends JFrame {

    private JPanel contentPane;
    private JTextField textFieldUsername;
    private JTextField passwordField;
    private JCheckBox checkboxSavePassword;

    /**
     * Create the frame.
     */
    public LoginFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(600, 320, 480, 360);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel labelTitle = new JLabel("Lixin Messenger");
        labelTitle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null,
                        "LiMe --> Lixin Messenger v0.1\n" +
                                "Copyright © 2018 Lixin. All rights reserved.");
            }
        });
        labelTitle.setForeground(new Color(153, 50, 204));
        labelTitle.setFont(new Font("Harry P", Font.BOLD, 50));
        labelTitle.setBounds(119, 30, 242, 60);
        contentPane.add(labelTitle);

        JLabel labelUsername = new JLabel("用户名");
        labelUsername.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        labelUsername.setBounds(53, 101, 54, 42);
        contentPane.add(labelUsername);

        textFieldUsername = new JTextField();
        textFieldUsername.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        textFieldUsername.setBounds(119, 102, 242, 42);
        contentPane.add(textFieldUsername);
        textFieldUsername.setColumns(10);

        JLabel labelRegister = new JLabel("注册账号");
        labelRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "注册账号");
                // TODO: 注册账号

            }
        });
        labelRegister.setForeground(SystemColor.controlHighlight);
        labelRegister.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        labelRegister.setBounds(373, 116, 54, 16);
        contentPane.add(labelRegister);

        JLabel labelPassword = new JLabel("密码");
        labelPassword.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        labelPassword.setBounds(71, 155, 36, 42);
        contentPane.add(labelPassword);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        passwordField.setBounds(119, 156, 242, 42);
        contentPane.add(passwordField);
        passwordField.setColumns(10);

        JLabel labelFindPassword = new JLabel("找回密码");
        labelFindPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "找回密码");
                // TODO: 找回密码

            }
        });
        labelFindPassword.setForeground(SystemColor.controlHighlight);
        labelFindPassword.setBounds(373, 170, 54, 16);
        contentPane.add(labelFindPassword);

        JButton btnLogin = new JButton("Login Now!");
        btnLogin.setForeground(new Color(153, 50, 204));
        btnLogin.addActionListener(e -> {
            if (checkboxSavePassword.isSelected()) {
                JOptionPane.showMessageDialog(null, "保存密码");
                // TODO: 写入密码文件

            }else {
                JOptionPane.showMessageDialog(null, "不保存密码");
                // TODO: 清空密码文件

            }
            // TODO: 登陆


        });
        btnLogin.setFont(new Font("Harry P", Font.PLAIN, 32));
        btnLogin.setBounds(119, 210, 242, 42);
        contentPane.add(btnLogin);

        checkboxSavePassword = new JCheckBox("保存密码");
        checkboxSavePassword.setSelected(true);
        checkboxSavePassword.setForeground(SystemColor.windowBorder);
        checkboxSavePassword.setBounds(198, 264, 84, 23);
        contentPane.add(checkboxSavePassword);

        JLabel lblCopyright = new JLabel("Copyright © 2018 Lixin. All rights reserved.");
        lblCopyright.setForeground(SystemColor.windowBorder);
        lblCopyright.setBounds(100, 316, 280, 16);
        contentPane.add(lblCopyright);
    }
}
