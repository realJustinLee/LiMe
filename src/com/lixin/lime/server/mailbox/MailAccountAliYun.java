package com.lixin.lime.server.mailbox;

/**
 * @author lixin
 */
public class MailAccountAliYun extends MailAccount {
    private static final int ALI_YUN_PORT = 25;

    public MailAccountAliYun(String user, String host, String password) {
        super(user, host, password, ALI_YUN_PORT, true, false);
    }
}
