package com.lixin.test;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author lixin
 */
public class MyTest extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MyTest frame = new MyTest();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    private MyTest() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(660, 260, 360, 480);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        String[] data = {"one", "two", "three", "four"};
        JList<String> listFriends = new JList<>(data);
        listFriends.setFont(new Font("PingFang SC", Font.PLAIN, 16));
        listFriends.addListSelectionListener(e -> {
            // 在鼠标松开时候和键盘移动之后响应事件
            if (!e.getValueIsAdjusting()) {
                System.out.println(listFriends.getSelectedValue());
            }
        });
        JScrollPane scrollPaneFriends = new JScrollPane(listFriends);
        scrollPaneFriends.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneFriends.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneFriends.setBounds(30, 20, 300, 400);
        contentPane.add(scrollPaneFriends);

        JButton buttonOk = new JButton("完成");
        buttonOk.setBounds(120, 425, 120, 30);
        contentPane.add(buttonOk);
    }
}
