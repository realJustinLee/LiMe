package com.lixin.lime.server.view;

import com.lixin.lime.protocol.util.gui.FocusTraversalOnArray;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import static com.lixin.lime.protocol.util.factory.LiMeStaticFactory.*;

/**
 * @author lixin
 */
public class LiMeServerFrame extends JFrame implements ActionListener {

    private String highlightedLime;

    private JPanel contentPane;
    private JLabel labelBrand;
    private JButton buttonStart;
    private JButton buttonStop;

    private JList<String> listLimes;
    private JButton buttonKick;
    private JButton buttonBan;

    private JLabel labelLog;
    private JTextArea textAreaLog;
    private JButton buttonClearLog;

    private JLabel labelHistory;
    private JTextArea textAreaHistory;
    private JButton buttonClearHistory;

    public LiMeServerFrame() {
        setResizable(false);
        setTitle(THE_SERVER_TITLE);
        setBounds(200, 140, 1280, 720);
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
        labelBrand.setForeground(new Color(154, 154, 154));
        labelBrand.setFont(new Font("Harry P", Font.BOLD, 99));
        labelBrand.setBounds(6, 6, 166, 100);
        contentPane.add(labelBrand);

        buttonStart = new JButton("START");
        buttonStart.setForeground(new Color(73, 156, 84));
        buttonStart.setFont(new Font("Harry P", Font.PLAIN, 24));
        buttonStart.setBounds(206, 13, 117, 36);
        buttonStart.setActionCommand(SERVER_ACTION_START);
        buttonStart.addActionListener(this);
        contentPane.add(buttonStart);

        buttonStop = new JButton("STOP");
        buttonStop.setEnabled(false);
        buttonStop.setForeground(new Color(199, 84, 80));
        buttonStop.setFont(new Font("Harry P", Font.PLAIN, 24));
        buttonStop.setBounds(206, 61, 117, 36);
        buttonStop.setActionCommand(SERVER_ACTION_STOP);
        buttonStop.addActionListener(this);
        contentPane.add(buttonStop);

        listLimes = new JList<>();
        listLimes.setEnabled(false);
        listLimes.setFont(new Font("PingFang SC", Font.PLAIN, 16));
        listLimes.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                highlightedLime = listLimes.getSelectedValue();
            }
        });
        JScrollPane scrollPaneLimes = new JScrollPane(listLimes);
        scrollPaneLimes.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneLimes.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneLimes.setBounds(6, 118, 317, 500);
        contentPane.add(scrollPaneLimes);

        buttonKick = new JButton("踢人");
        buttonKick.setEnabled(false);
        buttonKick.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        buttonKick.setBounds(6, 635, 155, 29);
        buttonKick.setActionCommand(SERVER_ACTION_KICK);
        contentPane.add(buttonKick);

        buttonBan = new JButton("封号");
        buttonBan.setEnabled(false);
        buttonBan.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        buttonBan.setBounds(168, 635, 155, 29);
        buttonBan.setActionCommand(SERVER_ACTION_BAN);
        contentPane.add(buttonBan);

        JSeparator separatorLeft = new JSeparator();
        separatorLeft.setOrientation(SwingConstants.VERTICAL);
        separatorLeft.setBounds(335, 6, 12, 658);
        contentPane.add(separatorLeft);

        labelLog = new JLabel("Server Log");
        labelLog.setHorizontalAlignment(SwingConstants.CENTER);
        labelLog.setForeground(new Color(154, 154, 154));
        labelLog.setFont(new Font("Harry P", Font.PLAIN, 99));
        labelLog.setBounds(359, 6, 400, 100);
        contentPane.add(labelLog);

        textAreaLog = new JTextArea();
        textAreaLog.setEnabled(false);
        textAreaLog.setLineWrap(true);
        textAreaLog.setFont(new Font("PingFang SC", Font.PLAIN, 16));
        textAreaLog.setEditable(false);
        JScrollPane scrollPaneLog = new JScrollPane(textAreaLog);
        scrollPaneLog.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneLog.setBounds(359, 120, 400, 500);
        contentPane.add(scrollPaneLog);

        buttonClearLog = new JButton("清空 Log");
        buttonClearLog.setEnabled(false);
        buttonClearLog.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        buttonClearLog.setBounds(359, 635, 400, 29);
        buttonClearLog.setActionCommand(SERVER_ACTION_CLEAR_LOG);
        contentPane.add(buttonClearLog);

        JSeparator separatorRight = new JSeparator();
        separatorRight.setOrientation(SwingConstants.VERTICAL);
        separatorRight.setBounds(771, 6, 12, 658);
        contentPane.add(separatorRight);

        labelHistory = new JLabel("Chat Log");
        labelHistory.setHorizontalAlignment(SwingConstants.CENTER);
        labelHistory.setForeground(new Color(154, 154, 154));
        labelHistory.setFont(new Font("Harry P", Font.PLAIN, 99));
        labelHistory.setBounds(795, 6, 480, 100);
        contentPane.add(labelHistory);

        textAreaHistory = new JTextArea();
        textAreaHistory.setLineWrap(true);
        textAreaHistory.setFont(new Font("PingFang SC", Font.PLAIN, 16));
        textAreaHistory.setEditable(false);
        JScrollPane scrollPaneGroup = new JScrollPane(textAreaHistory);
        scrollPaneGroup.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneGroup.setBounds(795, 119, 480, 505);
        contentPane.add(scrollPaneGroup);

        buttonClearHistory = new JButton("清空群聊记录");
        buttonClearHistory.setEnabled(false);
        buttonClearHistory.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        buttonClearHistory.setBounds(795, 635, 480, 29);
        buttonClearHistory.setActionCommand(SERVER_ACTION_CLEAR_HISTORY);
        contentPane.add(buttonClearHistory);

        JLabel labelCopyright = new JLabel(THE_COPYRIGHT);
        labelCopyright.setHorizontalAlignment(SwingConstants.CENTER);
        labelCopyright.setForeground(new Color(154, 154, 154));
        labelCopyright.setBounds(0, 676, 1280, 16);
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
            labelBrand.setForeground(new Color(154, 154, 154));
            labelLog.setForeground(new Color(154, 154, 154));
            labelHistory.setForeground(new Color(154, 154, 154));
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
        textAreaLog.setEnabled(bool);
        buttonClearLog.setEnabled(bool);
        textAreaHistory.setEnabled(bool);
        buttonClearHistory.setEnabled(bool);
    }

    public void enablePrivileges(boolean bool) {
        buttonKick.setEnabled(bool);
        buttonBan.setEnabled(bool);
    }

    public String getHighlightedLime() {
        return highlightedLime;
    }

    public void appendLog(String newLog) {
        textAreaLog.append(newLog);
    }

    public void clearLog() {
        textAreaLog.setText("");
    }

    public void appendHistory(String newHistory) {
        textAreaHistory.append(newHistory);
    }

    public void clearHistory() {
        textAreaHistory.setText("");
    }

    public void updateListLimes(HashSet<String> limeSet) {
        listLimes.setListData(limeSet.toArray(new String[0]));
        this.invalidate();
    }
}