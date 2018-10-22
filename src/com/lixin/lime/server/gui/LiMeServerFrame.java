package com.lixin.lime.server.gui;

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
public class LiMeServerFrame extends JFrame implements ActionListener {
    private JPanel contentPane;
    private JLabel labelBrand;
    private JButton buttonStart;
    private JButton buttonStop;

    private JList listLimes;
    private JButton buttonKick;
    private JButton buttonBan;

    private JLabel labelLog;
    private JTextArea textAreaLog;
    private JButton buttonClearLog;

    private JLabel labelHistory;
    private JTextArea textAreaGroup;
    private JButton buttonClearHistory;

    public LiMeServerFrame() {
        setResizable(false);
        setTitle(THE_SERVER_TITLE);
        setBounds(300, 140, 1080, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        labelBrand = new JLabel(THE_BRAND);
        labelBrand.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showServerCopyright();
            }
        });
        labelBrand.setForeground(SystemColor.windowBorder);
        labelBrand.setFont(new Font("Harry P", Font.BOLD, 99));
        labelBrand.setBounds(6, 6, 166, 100);
        contentPane.add(labelBrand);

        buttonStart = new JButton("START");
        buttonStart.addActionListener(this);
        buttonStart.setActionCommand(SERVER_ACTION_START);
        buttonStart.setForeground(Color.BLUE);
        buttonStart.setFont(new Font("Harry P", Font.PLAIN, 24));
        buttonStart.setBounds(194, 13, 117, 36);
        contentPane.add(buttonStart);

        buttonStop = new JButton("STOP");
        buttonStop.setEnabled(false);
        buttonStop.addActionListener(this);
        buttonStop.setActionCommand(SERVER_ACTION_STOP);
        buttonStop.setForeground(Color.RED);
        buttonStop.setFont(new Font("Harry P", Font.PLAIN, 24));
        buttonStop.setBounds(194, 61, 117, 36);
        contentPane.add(buttonStop);

        listLimes = new JList();
        listLimes.setEnabled(false);
        listLimes.setFont(new Font("PingFang SC", Font.PLAIN, 16));
        JScrollPane scrollPaneLimes = new JScrollPane(listLimes);
        scrollPaneLimes.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneLimes.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneLimes.setBounds(6, 118, 300, 505);
        contentPane.add(scrollPaneLimes);

        buttonKick = new JButton("踢人");
        buttonKick.setEnabled(false);
        buttonKick.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        buttonKick.setBounds(6, 635, 145, 29);
        contentPane.add(buttonKick);

        buttonBan = new JButton("封号");
        buttonBan.setEnabled(false);
        buttonBan.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        buttonBan.setBounds(161, 635, 145, 29);
        contentPane.add(buttonBan);

        JSeparator separatorLeft = new JSeparator();
        separatorLeft.setOrientation(SwingConstants.VERTICAL);
        separatorLeft.setBounds(318, 6, 12, 658);
        contentPane.add(separatorLeft);

        labelLog = new JLabel("Server Log");
        labelLog.setHorizontalAlignment(SwingConstants.CENTER);
        labelLog.setForeground(SystemColor.windowBorder);
        labelLog.setFont(new Font("Harry P", Font.PLAIN, 99));
        labelLog.setBounds(342, 6, 397, 100);
        contentPane.add(labelLog);

        textAreaLog = new JTextArea();
        textAreaLog.setEnabled(false);
        textAreaLog.setLineWrap(true);
        textAreaLog.setFont(new Font("PingFang SC", Font.PLAIN, 16));
        textAreaLog.setEditable(false);
        JScrollPane scrollPaneLog = new JScrollPane(textAreaLog);
        scrollPaneLog.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneLog.setPreferredSize(new Dimension(832, 430));
        scrollPaneLog.setMinimumSize(new Dimension(832, 150));
        scrollPaneLog.setBounds(342, 119, 397, 506);
        contentPane.add(scrollPaneLog);

        buttonClearLog = new JButton("清空 Log");
        buttonClearLog.setEnabled(false);
        buttonClearLog.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        buttonClearLog.setBounds(342, 635, 397, 29);
        contentPane.add(buttonClearLog);

        JSeparator separatorRight = new JSeparator();
        separatorRight.setOrientation(SwingConstants.VERTICAL);
        separatorRight.setBounds(751, 6, 12, 658);
        contentPane.add(separatorRight);

        labelHistory = new JLabel("Chat Log");
        labelHistory.setHorizontalAlignment(SwingConstants.CENTER);
        labelHistory.setForeground(SystemColor.windowBorder);
        labelHistory.setFont(new Font("Harry P", Font.PLAIN, 99));
        labelHistory.setBounds(775, 6, 300, 100);
        contentPane.add(labelHistory);

        textAreaGroup = new JTextArea();
        textAreaGroup.setLineWrap(true);
        textAreaGroup.setFont(new Font("PingFang SC", Font.PLAIN, 16));
        textAreaGroup.setEditable(false);
        JScrollPane scrollPaneGroup = new JScrollPane(textAreaGroup);
        scrollPaneGroup.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneGroup.setPreferredSize(new Dimension(832, 430));
        scrollPaneGroup.setMinimumSize(new Dimension(832, 150));
        scrollPaneGroup.setBounds(775, 119, 300, 505);
        contentPane.add(scrollPaneGroup);

        buttonClearHistory = new JButton("清空群聊记录");
        buttonClearHistory.setEnabled(false);
        buttonClearHistory.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        buttonClearHistory.setBounds(775, 635, 300, 29);
        contentPane.add(buttonClearHistory);

        JLabel labelCopyright = new JLabel(THE_COPYRIGHT);
        labelCopyright.setForeground(SystemColor.windowBorder);
        labelCopyright.setBounds(392, 676, 296, 16);
        contentPane.add(labelCopyright);

        setFocusTraversalPolicy(
                new FocusTraversalOnArray(
                        new Component[]{
                                buttonStart,
                                buttonStop
                        }
                )
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == buttonStart) {
            labelBrand.setForeground(new Color(153, 50, 204));
            labelLog.setForeground(new Color(153, 50, 204));
            labelHistory.setForeground(new Color(153, 50, 204));
            enableComponents(true);
        } else if (source == buttonStop) {
            labelBrand.setForeground(SystemColor.windowBorder);
            labelLog.setForeground(SystemColor.windowBorder);
            labelHistory.setForeground(SystemColor.windowBorder);
            enableComponents(false);
        } else {
            limeInternalError(this.getClass().getCanonicalName(), e.getSource().toString());
        }
    }

    public JButton getButtonStart() {
        return buttonStart;
    }

    public JButton getButtonStop() {
        return buttonStop;
    }

    public JButton getButtonKick() {
        return buttonKick;
    }

    public JButton getButtonBan() {
        return buttonBan;
    }

    public JButton getButtonClearLog() {
        return buttonClearLog;
    }

    public JButton getButtonClearHistory() {
        return buttonClearHistory;
    }

    private void enableComponents(boolean bool) {
        buttonStart.setEnabled(!bool);
        buttonStop.setEnabled(bool);
        listLimes.setEnabled(bool);
        buttonKick.setEnabled(bool);
        buttonBan.setEnabled(bool);
        textAreaLog.setEnabled(bool);
        buttonClearLog.setEnabled(bool);
        textAreaGroup.setEnabled(bool);
        buttonClearHistory.setEnabled(bool);
    }
}