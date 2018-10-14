package com.lixin.messenger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author lixin
 */
public class LoginFrame extends JFrame {

    private JPanel contentPane;
    private JTextField textFieldUsername;
    private JTextField passwordFieldPassword;

    /**
     * Test the LoginFrame.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LoginFrame frame = new LoginFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public LoginFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(640, 320, 480, 320);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Lixin Messenger");
        lblTitle.setForeground(new Color(153, 50, 204));
        lblTitle.setFont(new Font("Harry P", Font.BOLD, 50));
        lblTitle.setBounds(119, 30, 242, 60);
        contentPane.add(lblTitle);

        JLabel lblUsername = new JLabel("用户名");
        lblUsername.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        lblUsername.setBounds(77, 120, 54, 42);
        contentPane.add(lblUsername);

        textFieldUsername = new JTextField();
        textFieldUsername.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        textFieldUsername.setBounds(143, 120, 250, 42);
        contentPane.add(textFieldUsername);
        textFieldUsername.setColumns(10);

        JLabel lblPassword = new JLabel("密码");
        lblPassword.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        lblPassword.setBounds(95, 174, 36, 42);
        contentPane.add(lblPassword);

        passwordFieldPassword = new JPasswordField();
        passwordFieldPassword.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        passwordFieldPassword.setBounds(143, 174, 250, 42);
        contentPane.add(passwordFieldPassword);
        passwordFieldPassword.setColumns(10);

        JButton btnLogin = new JButton("登陆");
        btnLogin.addActionListener(e -> {
            // TODO: 添加登陆操作

        });
        btnLogin.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        btnLogin.setBounds(143, 228, 120, 36);
        contentPane.add(btnLogin);

        JButton btnRegister = new JButton("注册");
        btnRegister.addActionListener(e -> {
            // TODO: 添加注册操作

        });
        btnRegister.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        btnRegister.setBounds(273, 228, 120, 36);
        contentPane.add(btnRegister);
    }
}
