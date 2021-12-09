package com.justin.lime.server.mailbox;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author Justin Lee
 */
public class LiMeServerMailBox {
    private final String from;
    private final Session session;

    public LiMeServerMailBox(MailAccount mailAccount) {
        from = mailAccount.getSendAddress();
        Properties properties = new Properties();
        properties.put("mail.smtp.host", mailAccount.getMailHost());
        properties.put("mail.smtp.port", mailAccount.getPort());
        properties.put("mail.smtp.auth", mailAccount.isAuth());
        properties.put("mail.smtp.starttls.enable",mailAccount.isStartTls());
        session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, mailAccount.getPassword());
                    }
                });

    }

    public void sendSimpleMail(String recipient, String subject, String content) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(subject);
        message.setContent(content, "text/html;charset=UTF-8");
        Transport.send(message);
    }
}
