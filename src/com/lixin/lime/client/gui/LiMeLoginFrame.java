package com.lixin.lime.client.gui;

import com.lixin.lime.protocol.util.gui.FocusTraversalOnArray;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.lixin.lime.protocol.util.factory.MyStaticFactory.*;

/**
 * @author lixin
 */
public class LiMeLoginFrame extends JFrame {
    /**
     * UI elements
     */
    private JPanel contentPane;
    private JTextField textFieldUsername;
    private JButton buttonRegister;
    private JPasswordField passwordField;
    private JButton buttonFindPassword;
    private JButton buttonLogin;
    private JCheckBox checkboxSavePassword;

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

        JLabel labelVersion = new JLabel(THE_LIME_VERSION);
        labelVersion.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        labelVersion.setBounds(350, 63, 61, 16);
        contentPane.add(labelVersion);

        JLabel labelUsername = new JLabel("用户名");
        labelUsername.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        labelUsername.setBounds(53, 101, 54, 42);
        contentPane.add(labelUsername);

        textFieldUsername = new JTextField();
        textFieldUsername.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        textFieldUsername.setColumns(10);
        textFieldUsername.setBounds(119, 102, 242, 42);
        contentPane.add(textFieldUsername);

        buttonRegister = new JButton("注册账号");
        buttonRegister.setForeground(SystemColor.controlHighlight);
        buttonRegister.setActionCommand(ACTION_LOGIN_REGISTER);
        buttonRegister.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        buttonRegister.setBounds(373, 111, 80, 29);
        contentPane.add(buttonRegister);

        JLabel labelPassword = new JLabel("密码");
        labelPassword.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        labelPassword.setBounds(71, 155, 36, 42);
        contentPane.add(labelPassword);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        passwordField.setColumns(10);
        passwordField.setBounds(119, 156, 242, 42);
        contentPane.add(passwordField);

        buttonFindPassword = new JButton("找回密码");
        buttonFindPassword.setForeground(SystemColor.controlHighlight);
        buttonFindPassword.setActionCommand(ACTION_LOGIN_FIND_PASSWORD);
        buttonFindPassword.setBounds(373, 165, 80, 29);
        contentPane.add(buttonFindPassword);

        buttonLogin = new JButton("Login Now!");
        buttonLogin.setForeground(new Color(153, 50, 204));
        buttonLogin.setActionCommand(ACTION_LOGIN_LOGIN);
        buttonLogin.setFont(new Font("Harry P", Font.PLAIN, 32));
        buttonLogin.setBounds(119, 210, 242, 42);
        contentPane.add(buttonLogin);

        checkboxSavePassword = new JCheckBox("保存密码");
        checkboxSavePassword.setSelected(true);
        checkboxSavePassword.setForeground(SystemColor.windowBorder);
        checkboxSavePassword.setBounds(198, 264, 84, 23);
        contentPane.add(checkboxSavePassword);

        JLabel labelCopyright = new JLabel(THE_COPYRIGHT);
        labelCopyright.setForeground(SystemColor.windowBorder);
        labelCopyright.setBounds(92, 316, 296, 16);
        contentPane.add(labelCopyright);

        setFocusTraversalPolicy(
                new FocusTraversalOnArray(
                        new Component[]{
                                textFieldUsername,
                                passwordField,
                                buttonLogin
                        }
                )
        );
    }

    public JButton getButtonRegister() {
        return buttonRegister;
    }

    public JButton getButtonFindPassword() {
        return buttonFindPassword;
    }

    public JButton getButtonLogin() {
        return buttonLogin;
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
