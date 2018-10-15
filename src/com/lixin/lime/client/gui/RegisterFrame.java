package com.lixin.lime.client.gui;

import com.lixin.lime.client.util.factory.MyStaticFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.lixin.lime.client.util.factory.MyStaticFactory.*;

/**
 * @author lixin
 */
public class RegisterFrame extends JFrame {

    private JPanel contentPane;
    private JTextField textFieldUsername;
    private JPasswordField passwordField;
    private JButton btnRegister;
    private JTextField textFieldEmail;

    /**
     * Create the frame.
     */
    public RegisterFrame() {
        setResizable(false);
        setTitle("注册 " + THE_BRAND);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(600, 200, 480, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel labelTitle = new JLabel(THE_TITLE);
        labelTitle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MyStaticFactory.showCopyright();
            }
        });
        labelTitle.setForeground(new Color(153, 50, 204));
        labelTitle.setFont(new Font("Harry P", Font.BOLD, 50));
        labelTitle.setBounds(119, 30, 242, 60);
        contentPane.add(labelTitle);

        JLabel labelUsername = new JLabel("用户名");
        labelUsername.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        labelUsername.setBounds(53, 129, 54, 42);
        contentPane.add(labelUsername);

        textFieldUsername = new JTextField();
        textFieldUsername.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        textFieldUsername.setColumns(10);
        textFieldUsername.setBounds(119, 130, 242, 42);
        contentPane.add(textFieldUsername);

        JLabel labelPassowrd = new JLabel("密码");
        labelPassowrd.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        labelPassowrd.setBounds(71, 183, 36, 42);
        contentPane.add(labelPassowrd);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        passwordField.setColumns(10);
        passwordField.setBounds(119, 184, 242, 42);
        contentPane.add(passwordField);

        JLabel labelGender = new JLabel("性别");
        labelGender.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        labelGender.setBounds(71, 237, 36, 42);
        contentPane.add(labelGender);

        JToggleButton tglbtnGender = new JToggleButton("男");
        tglbtnGender.addActionListener(e -> {
            switch (tglbtnGender.getText()) {
                case "男":
                    tglbtnGender.setText("女");
                    break;
                case "女":
                    tglbtnGender.setText("男");
                    break;
                default:
                    break;
            }
        });
        tglbtnGender.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        tglbtnGender.setBounds(119, 238, 242, 42);
        contentPane.add(tglbtnGender);

        JCheckBox checkboxAgree = new JCheckBox("我同意");
        checkboxAgree.addActionListener(e -> {
            if (checkboxAgree.isSelected()) {
                btnRegister.setEnabled(true);
            } else {
                btnRegister.setEnabled(false);
            }
        });

        JLabel labelEmail = new JLabel("Email");
        labelEmail.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        labelEmail.setBounds(62, 291, 45, 42);
        contentPane.add(labelEmail);

        JLabel labelCopyright = new JLabel(THE_COPYRIGHT);
        labelCopyright.setForeground(SystemColor.windowBorder);
        labelCopyright.setBounds(100, 556, 280, 16);
        contentPane.add(labelCopyright);
        checkboxAgree.setBounds(168, 346, 72, 23);
        contentPane.add(checkboxAgree);

        JLabel labelAgreement = new JLabel("《用户协议》");
        labelAgreement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showAgreement();
            }
        });
        labelAgreement.setForeground(SystemColor.controlHighlight);
        labelAgreement.setBounds(235, 350, 80, 16);
        contentPane.add(labelAgreement);

        btnRegister = new JButton("注册");
        btnRegister.addActionListener(e -> {
            // TODO: 注册，发送Json文件到服务器，服务器将注册信息写入数据库 (Json action : register)

        });
        btnRegister.setEnabled(false);
        btnRegister.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        btnRegister.setBounds(119, 378, 117, 36);
        contentPane.add(btnRegister);

        JButton btnCancel = new JButton("取消");
        btnCancel.addActionListener(e -> {
            // TODO: 取消注册

        });
        btnCancel.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        btnCancel.setBounds(245, 378, 117, 36);
        contentPane.add(btnCancel);

        JLabel labelBrand = new JLabel(THE_BRAND);
        labelBrand.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showHomepage();
            }
        });
        labelBrand.setForeground(SystemColor.windowBorder);
        labelBrand.setFont(new Font("Harry P", Font.BOLD, 99));
        labelBrand.setBounds(157, 444, 166, 100);
        contentPane.add(labelBrand);

        textFieldEmail = new JTextField();
        textFieldEmail.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        textFieldEmail.setColumns(10);
        textFieldEmail.setBounds(119, 292, 242, 42);
        contentPane.add(textFieldEmail);
    }
}
