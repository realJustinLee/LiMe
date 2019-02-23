package com.lixin.lime.client.view;

import com.lixin.lime.protocol.util.gui.FocusTraversalOnArray;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;

import static com.lixin.lime.protocol.util.factory.MyStaticFactory.*;

/**
 * @author lixin
 */
public class LiMeChatFrame extends JFrame {

    private String receiver;

    /**
     * HashMap[String receiver, String text]
     */
    private HashMap<String, String> history;

    private JPanel contentPane;
    private JButton buttonLogout;

    private JList<String> listFriends;
    private JTextArea textAreaHistory;
    private JTextArea textAreaMessage;

    private JButton buttonSendFile;
    private JButton buttonSendMessage;

    /**
     * Create the frame.
     */
    public LiMeChatFrame(String username) {
        setTitle(THE_BRAND + " @" + username);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(300, 140, 1080, 720);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel labelTitle = new JLabel(THE_TITLE);
        labelTitle.setHorizontalAlignment(SwingConstants.LEFT);
        labelTitle.setForeground(new Color(153, 50, 204));
        labelTitle.setFont(new Font("Harry P", Font.BOLD, 50));
        labelTitle.setBounds(6, 5, 242, 60);
        contentPane.add(labelTitle);

        JLabel labelUsername = new JLabel("用户名");
        labelUsername.setHorizontalAlignment(SwingConstants.RIGHT);
        labelUsername.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        labelUsername.setBounds(705, 24, 54, 42);
        contentPane.add(labelUsername);

        JTextField textFieldUsername = new JTextField(" @" + username);
        textFieldUsername.setEnabled(false);
        textFieldUsername.setEditable(false);
        textFieldUsername.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        textFieldUsername.setColumns(10);
        textFieldUsername.setBounds(770, 24, 242, 42);
        contentPane.add(textFieldUsername);

        listFriends = new JList<>();
        listFriends.setFont(new Font("PingFang SC", Font.PLAIN, 16));
        listFriends.addListSelectionListener(e -> {
            // 在鼠标松开时候和键盘移动之后响应事件
            if (!e.getValueIsAdjusting()) {
                // 切换 receiver 并且转换历史数据
                receiver = listFriends.getSelectedValue();
                updateTextAreaHistory();
            }
        });
        JScrollPane scrollPaneFriends = new JScrollPane(listFriends);
        scrollPaneFriends.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneFriends.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneFriends.setBounds(6, 78, 200, 614);
        contentPane.add(scrollPaneFriends);

        JSeparator separatorV = new JSeparator();
        separatorV.setOrientation(SwingConstants.VERTICAL);
        separatorV.setBounds(218, 78, 12, 614);
        contentPane.add(separatorV);

        textAreaHistory = new JTextArea();
        textAreaHistory.setEditable(false);
        textAreaHistory.setLineWrap(true);
        textAreaHistory.setFont(new Font("PingFang SC", Font.PLAIN, 16));
        JScrollPane scrollPaneHistory = new JScrollPane(textAreaHistory);
        scrollPaneHistory.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneHistory.setPreferredSize(new Dimension(832, 430));
        scrollPaneHistory.setMinimumSize(new Dimension(832, 150));
        textAreaMessage = new JTextArea();
        textAreaMessage.setLineWrap(true);
        textAreaMessage.setFont(new Font("PingFang SC", Font.PLAIN, 16));
        JScrollPane scrollPaneMessage = new JScrollPane(textAreaMessage);
        scrollPaneMessage.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneMessage.setMinimumSize(new Dimension(832, 150));
        JSplitPane splitPaneRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPaneHistory, scrollPaneMessage);
        splitPaneRight.setBounds(242, 77, 832, 574);
        contentPane.add(splitPaneRight);

        buttonLogout = new JButton("登出");
        buttonLogout.setForeground(Color.RED);
        buttonLogout.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        buttonLogout.setBounds(1024, 33, 50, 29);
        buttonLogout.setActionCommand(ACTION_CHAT_LOGOUT);
        contentPane.add(buttonLogout);

        buttonSendFile = new JButton("传文件");
        buttonSendFile.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        buttonSendFile.setBounds(835, 663, 117, 29);
        buttonSendFile.setActionCommand(ACTION_CHAT_SEND_FILE);
        contentPane.add(buttonSendFile);

        buttonSendMessage = new JButton("发送");
        buttonSendMessage.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        buttonSendMessage.setBounds(957, 663, 117, 29);
        buttonSendMessage.setActionCommand(ACTION_CHAT_SEND_MESSAGE);
        contentPane.add(buttonSendMessage);

        JLabel labelCopyright = new JLabel(THE_COPYRIGHT);
        labelCopyright.setHorizontalAlignment(SwingConstants.LEFT);
        labelCopyright.setForeground(new Color(154, 154, 154));
        labelCopyright.setBounds(242, 676, 320, 16);
        contentPane.add(labelCopyright);

        // FIXME: 在 textArea 监听不到 Tab 键
        setFocusTraversalPolicy(
                new FocusTraversalOnArray(
                        new Component[]{
                                textAreaMessage,
                                buttonSendMessage,
                                buttonSendFile
                        }
                )
        );


        history = new HashMap<>();
        // TODO: Read History From File
    }

    public JButton getButtonLogout() {
        return buttonLogout;
    }

    public JTextArea getTextAreaMessage() {
        return textAreaMessage;
    }

    public JButton getButtonSendFile() {
        return buttonSendFile;
    }

    public JButton getButtonSendMessage() {
        return buttonSendMessage;
    }

    public String getReceiver() {
        return receiver;
    }

    public HashMap<String, String> getHistory() {
        return history;
    }

    public void updateListFriends() {
        listFriends.setListData(history.keySet().toArray(new String[0]));
        this.invalidate();
    }

    public void updateTextAreaHistory() {
        textAreaHistory.setText(history.get(receiver));
        this.invalidate();
    }
}
