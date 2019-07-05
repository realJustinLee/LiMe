package com.lixin.lime.server.mailbox;

/**
 * @author lixin
 */
public class MailAccountGmail extends MailAccount {
    private static final int GMAIL_PORT = 587;

    public MailAccountGmail(String user, String host, String password) {
        super(user, host, password, GMAIL_PORT, true, true);
    }
}
