package com.lixin.lime.client.view;

import com.lixin.lime.protocol.util.gui.FocusTraversalOnArray;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.lixin.lime.protocol.util.factory.MyStaticFactory.*;

/**
 * @author lixin
 */
public class LiMeRegisterFrame extends JFrame implements ActionListener {

    private LiMeUserAgreementFrame userAgreementFrame;

    private JPanel contentPane;
    private JTextField textFieldUsername;
    private JPasswordField passwordField;
    private JTextField textFieldEmail;
    private JToggleButton toggleButtonGender;
    private JCheckBox checkboxAgree;
    private JButton btnRegister;
    private JButton btnCancel;

    /**
     * Create the frame.
     */
    public LiMeRegisterFrame() {
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
                showCopyright();
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
        textFieldUsername.setHorizontalAlignment(JTextField.CENTER);
        textFieldUsername.setBounds(119, 130, 242, 42);
        contentPane.add(textFieldUsername);

        JLabel labelPassword = new JLabel("密码");
        labelPassword.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        labelPassword.setBounds(71, 183, 36, 42);
        contentPane.add(labelPassword);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        passwordField.setColumns(10);
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        passwordField.setBounds(119, 184, 242, 42);
        contentPane.add(passwordField);

        JLabel labelGender = new JLabel("性别");
        labelGender.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        labelGender.setBounds(71, 237, 36, 42);
        contentPane.add(labelGender);

        toggleButtonGender = new JToggleButton("男");
        toggleButtonGender.addActionListener(this);
        toggleButtonGender.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        toggleButtonGender.setBounds(119, 238, 242, 42);
        contentPane.add(toggleButtonGender);

        checkboxAgree = new JCheckBox("我同意");
        checkboxAgree.addActionListener(this);
        checkboxAgree.setBounds(168, 346, 72, 23);
        contentPane.add(checkboxAgree);

        JLabel labelEmail = new JLabel("Email");
        labelEmail.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        labelEmail.setBounds(62, 291, 45, 42);
        contentPane.add(labelEmail);

        textFieldEmail = new JTextField();
        textFieldEmail.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        textFieldEmail.setColumns(10);
        textFieldEmail.setHorizontalAlignment(JTextField.CENTER);
        textFieldEmail.setBounds(119, 292, 242, 42);
        contentPane.add(textFieldEmail);

        JLabel labelAgreement = new JLabel("《用户协议》");
        labelAgreement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                initUserAgreementFrame();
                userAgreementFrame.setVisible(true);
            }
        });
        labelAgreement.setForeground(new Color(7, 73, 217));
        labelAgreement.setBounds(235, 350, 80, 16);
        contentPane.add(labelAgreement);

        btnRegister = new JButton("注册");
        btnRegister.setActionCommand(ACTION_REGISTER_REGISTER);
        btnRegister.setEnabled(false);
        btnRegister.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        btnRegister.setBounds(119, 378, 117, 36);
        contentPane.add(btnRegister);

        btnCancel = new JButton("取消");
        btnCancel.setActionCommand(ACTION_REGISTER_CANCEL);
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
        labelBrand.setForeground(new Color(154, 154, 154));
        labelBrand.setFont(new Font("Harry P", Font.BOLD, 99));
        labelBrand.setBounds(157, 444, 166, 100);
        contentPane.add(labelBrand);

        JLabel labelCopyright = new JLabel(THE_COPYRIGHT);
        labelCopyright.setForeground(new Color(154, 154, 154));
        labelCopyright.setBounds(92, 556, 296, 16);
        contentPane.add(labelCopyright);

        setFocusTraversalPolicy(
                new FocusTraversalOnArray(
                        new Component[]{
                                textFieldUsername,
                                passwordField,
                                toggleButtonGender,
                                textFieldEmail,
                                checkboxAgree,
                                btnRegister,
                                btnCancel
                        }
                )
        );
    }

    private void initUserAgreementFrame() {
        userAgreementFrame = new LiMeUserAgreementFrame(URL_LIME_AGREEMENT_OFFLINE);
        userAgreementFrame.setLocationRelativeTo(this);
        userAgreementFrame.getButtonAgree().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == toggleButtonGender) {
            if (toggleButtonGender.isSelected()) {
                toggleButtonGender.setText("女");
            } else {
                toggleButtonGender.setText("男");
            }
        } else if (source == checkboxAgree) {
            if (checkboxAgree.isSelected()) {
                btnRegister.setEnabled(true);
            } else {
                btnRegister.setEnabled(false);
            }
        } else {
            if (e.getActionCommand().equals(ACTION_AGREEMENT_CONFIRM)) {
                checkboxAgree.setSelected(true);
                btnRegister.setEnabled(true);
                userAgreementFrame.dispose();
            } else {
                limeInternalError(this.getClass().getCanonicalName(), e.getSource().toString());
            }
        }
    }

    public JButton getBtnRegister() {
        return btnRegister;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public String getUsername() {
        return textFieldUsername.getText();
    }

    public String getPassword() {
        return String.valueOf(passwordField.getPassword());
    }

    public String getGender() {
        return toggleButtonGender.getText();
    }

    public String getEmail() {
        return textFieldEmail.getText();
    }
}