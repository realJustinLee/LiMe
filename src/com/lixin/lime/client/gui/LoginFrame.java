package com.lixin.lime.client.gui;

import com.lixin.lime.client.util.crypto.AesCipher;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URI;

/**
 * @author lixin
 */
public class LoginFrame extends JFrame {
    /**
     * UI elements
     */
    private JPanel contentPane;
    private JTextField textFieldUsername;
    private JTextField passwordField;
    private JCheckBox checkboxSavePassword;
    private JButton btnLogin;


    private RegisterFrame registerFrame;

    /**
     * Create the frame.
     */
    public LoginFrame() {
        setResizable(false);
        setTitle("LiMe");
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
                        "Copyright © 2018 Lixin. All rights reserved.",
                        "LiMe --> Lixin Messenger v0.1",
                        JOptionPane.INFORMATION_MESSAGE);
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
                EventQueue.invokeLater(() -> {
                    try {
                        registerFrame = new RegisterFrame();
                        registerFrame.setVisible(true);
                    } catch (Exception exc) {
                        exc.printStackTrace();
                    }
                });
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
                try {
                    Desktop desktop = Desktop.getDesktop();
                    String message = "mailto:JustinDellAdam@live.com";
                    URI uri = URI.create(message);
                    desktop.mail(uri);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        });
        labelFindPassword.setForeground(SystemColor.controlHighlight);
        labelFindPassword.setBounds(373, 170, 54, 16);
        contentPane.add(labelFindPassword);

        btnLogin = new JButton("Login Now!");
        btnLogin.setForeground(new Color(153, 50, 204));
        btnLogin.setActionCommand("login");
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

//        decryptAndReadFromFile(passwordFile);
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public void setUsername(String username) {
        textFieldUsername.setText(username);
    }

    public String getUsername() {
        return textFieldUsername.getText();
    }

    public void setPassword(String password) {
        passwordField.setText(password);
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public boolean savePassword() {
        return checkboxSavePassword.isSelected();
    }
}
