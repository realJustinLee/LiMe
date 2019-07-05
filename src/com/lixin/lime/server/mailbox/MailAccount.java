package com.lixin.lime.server.mailbox;

/**
 * @author lixin
 */
public class MailAccount {
    private String sendAddress;
    private String password;
    private String mailHost;
    private int port;
    private boolean auth;
    private boolean startTls;

    public MailAccount(String user, String host, String password, int port, boolean auth, boolean startTls) {
        sendAddress = user + "@" + host;
        this.password = password;
        this.mailHost = "smtp." + host;
        this.port = port;
        this.auth = auth;
        this.startTls = startTls;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public String getPassword() {
        return password;
    }

    public String getMailHost() {
        return mailHost;
    }

    public int getPort() {
        return port;
    }

    public boolean isAuth() {
        return auth;
    }

    public boolean isStartTls() {
        return startTls;
    }
}
