package com.justin.lime.server.mailbox;

/**
 * @author Justin Lee
 */
public class MailAccount {
    private final String sendAddress;
    private final String password;
    private final String mailHost;
    private final int port;
    private final boolean auth;
    private final boolean startTls;

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
