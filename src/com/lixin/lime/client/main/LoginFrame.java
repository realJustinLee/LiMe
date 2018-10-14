package com.lixin.lime.client.main;

import com.lixin.lime.crypto.AesCipher;

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
    private File passwordFile = new File("password.lixin");
    /**
     * A-16-Byte-String
     */
    private String lixinGoldenKey = "FuckYouMicrosoft";

    // UI elements
    private JPanel contentPane;
    private JTextField textFieldUsername;
    private JTextField passwordField;
    private JCheckBox checkboxSavePassword;
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

        JButton btnLogin = new JButton("Login Now!");
        btnLogin.setForeground(new Color(153, 50, 204));
        btnLogin.addActionListener(e -> {

            // 用户名、密码有无校验
            if (textFieldUsername.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "请输入用户名");
            } else if (passwordField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "请输入密码");
            } else {
                if (checkboxSavePassword.isSelected()) {
                    JOptionPane.showMessageDialog(null, "保存密码");
                    encryptAndWriteToFile(passwordFile, textFieldUsername.getText(), passwordField.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "不保存密码");
                    // TODO: 清空密码文件

                }

                // TODO: 登陆校验

            }
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

        decryptAndReadFromFile(passwordFile);
    }

    private void encryptAndWriteToFile(File file, String username, String password) {
        try {
            //true = append file
            FileWriter fileWriter = new FileWriter(file.getName(), false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String cryptUsername = AesCipher.aesEncryptString(username, lixinGoldenKey);
            String cryptPassword = AesCipher.aesEncryptString(password, lixinGoldenKey);
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
            FileReader fileReader = new FileReader(file.getName());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String cryptUsername = bufferedReader.readLine();
            String cryptPassword = bufferedReader.readLine();
            String username = AesCipher.aesDecryptString(cryptUsername, lixinGoldenKey);
            String password = AesCipher.aesDecryptString(cryptPassword, lixinGoldenKey);
            textFieldUsername.setText(username);
            passwordField.setText(password);
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
