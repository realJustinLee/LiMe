package com.lixin.lime.client.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static com.lixin.lime.protocol.util.factory.MyStaticFactory.*;

/**
 * @author lixin
 */
class LiMeUserAgreementFrame extends JFrame {

    private JPanel contentPane;
    private JEditorPane htmlPane;
    private JButton buttonAgree;

    /**
     * Create the frame.
     */
    LiMeUserAgreementFrame(String path) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 440, 556);
        setUndecorated(true);
        setOpacity(0.67f);
        setAlwaysOnTop(true);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        try {
            URL url = null;
            try {
                url = getClass().getResource(path);
            } catch (Exception e) {
                System.err.println("Failed to open " + path);
            }
            if (url != null) {
                htmlPane = new JEditorPane(url);
                htmlPane.setEditable(false);
                htmlPane.addHyperlinkListener(createHyperLinkListener());
                JScrollPane scrollPaneAgreement = new JScrollPane(htmlPane);
                scrollPaneAgreement.setBounds(20, 20, 400, 460);
                contentPane.add(scrollPaneAgreement);
            } else {
                System.err.println("Local html agreement not found!");
            }
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e);
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }

        buttonAgree = new JButton("我同意以上协议");
        buttonAgree.setFont(new Font("PingFang SC", Font.PLAIN, 13));
        buttonAgree.setBounds(160, 490, 120, 36);
        buttonAgree.setActionCommand(ACTION_AGREEMENT_CONFIRM);
        contentPane.add(buttonAgree);
    }

    private HyperlinkListener createHyperLinkListener() {
        return e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                if (e instanceof HTMLFrameHyperlinkEvent) {
                    ((HTMLDocument) htmlPane.getDocument()).processHTMLFrameHyperlinkEvent(
                            (HTMLFrameHyperlinkEvent) e);
                } else {
                    try {
                        htmlPane.setPage(e.getURL());
                    } catch (IOException ioe) {
                        System.out.println("IOE: " + ioe);
                    }
                }
            }
        };
    }

    JButton getButtonAgree() {
        return buttonAgree;
    }
}

