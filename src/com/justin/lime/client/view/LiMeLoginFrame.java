package com.justin.lime.client.view;

import com.justin.lime.protocol.util.gui.FocusTraversalOnArray;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.justin.lime.protocol.util.factory.LiMeStaticFactory.*;

/**
 * @author Justin Lee
 */
public class LiMeLoginFrame extends JFrame {
    /**
     * UI elements
     */
    private final JPanel contentPane;
    private final JTextField textFieldUsername;
    private final JButton buttonRegister;
    private final JPasswordField passwordField;
    private final JButton buttonRestorePassword;
    private final JButton buttonLogin;
    private final JCheckBox checkboxSavePassword;

    /**
     * Create the frame.
     */
    public LiMeLoginFrame(ActionListener controller) {
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
        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitle.setForeground(new Color(153, 50, 204));
        labelTitle.setFont(new Font("Harry P", Font.BOLD, 50));
        labelTitle.setBounds(0, 30, 480, 60);
        contentPane.add(labelTitle);

        JLabel labelVersion = new JLabel(THE_LIME_VERSION);
        labelVersion.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        labelVersion.setBounds(350, 65, 61, 16);
        contentPane.add(labelVersion);

        JLabel labelUsername = new JLabel("用户名");
        labelUsername.setHorizontalAlignment(SwingConstants.RIGHT);
        labelUsername.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        labelUsername.setBounds(53, 101, 54, 42);
        contentPane.add(labelUsername);

        textFieldUsername = new JTextField();
        textFieldUsername.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        textFieldUsername.setColumns(10);
        textFieldUsername.setHorizontalAlignment(JTextField.CENTER);
        textFieldUsername.setBounds(119, 102, 242, 42);
        contentPane.add(textFieldUsername);

        buttonRegister = new JButton("注册账号");
        buttonRegister.setForeground(new Color(7, 73, 217));
        buttonRegister.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        buttonRegister.setBounds(350, 111, 100, 29);
        buttonRegister.setBorderPainted(false);
        buttonRegister.setActionCommand(ACTION_LOGIN_REGISTER);
        buttonRegister.addActionListener(controller);
        contentPane.add(buttonRegister);

        JLabel labelPassword = new JLabel("密码");
        labelPassword.setHorizontalAlignment(SwingConstants.RIGHT);
        labelPassword.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        labelPassword.setBounds(53, 155, 54, 42);
        contentPane.add(labelPassword);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        passwordField.setColumns(10);
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        passwordField.setBounds(119, 156, 242, 42);
        contentPane.add(passwordField);

        buttonRestorePassword = new JButton("找回密码");
        buttonRestorePassword.setForeground(new Color(7, 73, 217));
        buttonRestorePassword.setBounds(350, 165, 100, 29);
        buttonRestorePassword.setBorderPainted(false);
        buttonRestorePassword.setActionCommand(ACTION_LOGIN_FORGOT_PASSWORD);
        buttonRestorePassword.addActionListener(controller);
        contentPane.add(buttonRestorePassword);

        buttonLogin = new JButton("Login Now!");
        buttonLogin.setForeground(new Color(153, 50, 204));
        buttonLogin.setFont(new Font("Harry P", Font.PLAIN, 32));
        buttonLogin.setBounds(119, 210, 242, 42);
        buttonLogin.setActionCommand(ACTION_LOGIN_LOGIN);
        buttonLogin.addActionListener(controller);
        contentPane.add(buttonLogin);

        checkboxSavePassword = new JCheckBox("保存密码");
        checkboxSavePassword.setSelected(true);
        checkboxSavePassword.setForeground(new Color(154, 154, 154));
        checkboxSavePassword.setBounds(198, 264, 84, 23);
        contentPane.add(checkboxSavePassword);

        JLabel labelCopyright = new JLabel(THE_COPYRIGHT);
        labelCopyright.setHorizontalAlignment(SwingConstants.CENTER);
        labelCopyright.setForeground(new Color(154, 154, 154));
        labelCopyright.setBounds(0, 316, 480, 16);
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

    public LiMeLoginFrame(ActionListener listener, String username, String password, boolean savePassword) {
        this(listener);
        setUsername(username);
        setPassword(password);
        savePassword(savePassword);
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
