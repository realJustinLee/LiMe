package com.lixin.lime.client.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import static com.lixin.lime.util.factory.MyStaticFactory.*;

/**
 * @author lixin
 */
public class LiMeLoginFrame extends JFrame {
    /**
     * UI elements
     */
    private JPanel contentPane;
    private JTextField textFieldUsername;
    private JPasswordField passwordField;
    private JButton btnLogin;
    private JCheckBox checkboxSavePassword;
    private JButton btnRegister;
    private JButton btnFindPassword;

    /**
     * Create the frame.
     */
    public LiMeLoginFrame() {
        setResizable(false);
        setTitle(THE_BRAND);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(600, 320, 480, 360);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel labelTitle = new JLabel(THE_TITLE);
        labelTitle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showCopyright();
            }
        });
        labelTitle.setForeground(new Color(153, 50, 204));
        labelTitle.setFont(new Font("Harry P", Font.BOLD, 50));
        labelTitle.setBounds(119, 30, 242, 60);
        contentPane.add(labelTitle);

        JLabel labelVersion = new JLabel(THE_VERSION);
        labelVersion.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        labelVersion.setBounds(350, 63, 61, 16);
        contentPane.add(labelVersion);

        JLabel labelUsername = new JLabel("用户名");
        labelUsername.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        labelUsername.setBounds(53, 101, 54, 42);
        contentPane.add(labelUsername);

        textFieldUsername = new JTextField();
        textFieldUsername.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        textFieldUsername.setBounds(119, 102, 242, 42);
        contentPane.add(textFieldUsername);
        textFieldUsername.setColumns(10);

        JLabel labelPassword = new JLabel("密码");
        labelPassword.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        labelPassword.setBounds(71, 155, 36, 42);
        contentPane.add(labelPassword);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        passwordField.setBounds(119, 156, 242, 42);
        contentPane.add(passwordField);
        passwordField.setColumns(10);

        btnLogin = new JButton("Login Now!");
        btnLogin.setForeground(new Color(153, 50, 204));
        btnLogin.setActionCommand(ACTION_LOGIN);
        btnLogin.setFont(new Font("Harry P", Font.PLAIN, 32));
        btnLogin.setBounds(119, 210, 242, 42);
        contentPane.add(btnLogin);

        checkboxSavePassword = new JCheckBox("保存密码");
        checkboxSavePassword.setSelected(true);
        checkboxSavePassword.setForeground(SystemColor.windowBorder);
        checkboxSavePassword.setBounds(198, 264, 84, 23);
        contentPane.add(checkboxSavePassword);

        btnRegister = new JButton("注册账号");
        btnRegister.setActionCommand(ACTION_REGISTER);
        btnRegister.setForeground(SystemColor.controlHighlight);
        btnRegister.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        btnRegister.setBounds(373, 116, 61, 16);
        contentPane.add(btnRegister);

        btnFindPassword = new JButton("找回密码");
        btnFindPassword.setActionCommand(ACTION_FIND_PASSWORD);
        btnFindPassword.setForeground(SystemColor.controlHighlight);
        btnFindPassword.setBounds(373, 170, 61, 16);
        contentPane.add(btnFindPassword);

        JLabel lblCopyright = new JLabel(THE_COPYRIGHT);
        lblCopyright.setForeground(SystemColor.windowBorder);
        lblCopyright.setBounds(100, 316, 280, 16);
        contentPane.add(lblCopyright);
    }

    public JButton getBtnRegister() {
        return btnRegister;
    }

    public JButton getBtnFindPassword() {
        return btnFindPassword;
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
        return String.valueOf(passwordField.getPassword());
    }

    public boolean savePassword() {
        return checkboxSavePassword.isSelected();
    }

    public void savePassword(boolean bool) {
        checkboxSavePassword.setSelected(bool);
    }
}
