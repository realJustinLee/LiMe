package com.justin.lime.protocol.util.factory;

import com.justin.lime.protocol.util.crypto.LiMeAesCipher;
import com.justin.lime.protocol.util.crypto.LiMeMessageDigester;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.Calendar;
import java.util.Random;

/**
 * @author Justin Lee
 */
public class LiMeStaticFactory {

    public static final String CHARSET = "UTF-8";

    /**
     * The Strings
     * Name Format: THE_[Name]*
     */
    public static final String THE_TITLE = "Lixin Messenger";
    public static final String THE_BRAND = "LiMe";
    public static final String THE_AUTHOR = "Justin Lee";
    public static final String THE_COPYRIGHT = "™ and © 1997-" + getLiMeYear() + " " + THE_AUTHOR + ". All Rights Reserved.";
    public static final String THE_LIME_VERSION = "C_v 0.7.1";

    public static final String THE_SERVER_TITLE = THE_TITLE + " Server";
    public static final String THE_SERVER_BRAND = THE_BRAND + " Server";
    public static final String THE_SERVER_VERSION = "S_v 0.7.1";

    /**
     * The Actions
     * Name Format: ACTION_[Frame]_[Button]*
     */
    public static final String ACTION_LOGIN_LOGIN = "login";
    public static final String ACTION_LOGIN_REGISTER = "register";
    public static final String ACTION_LOGIN_FORGOT_PASSWORD = "find_password";
    public static final String ACTION_REGISTER_REGISTER = "commit_register";
    public static final String ACTION_REGISTER_CANCEL = "cancel_register";
    public static final String ACTION_AGREEMENT_CONFIRM = "confirm_agreement";
    public static final String ACTION_CHAT_LOGOUT = "logout";
    public static final String ACTION_CHAT_SEND_MESSAGE = "send_message";
    public static final String ACTION_CHAT_SEND_FILE = "send_file";

    /**
     * The Server Actions
     * Name Format: SERVER_ACTION_[Button]* = "server_[button]"
     */
    public static final String SERVER_ACTION_START = "server_start";
    public static final String SERVER_ACTION_STOP = "server_stop";
    public static final String SERVER_ACTION_KICK = "server_kick";
    public static final String SERVER_ACTION_BAN = "server_ban";
    public static final String SERVER_ACTION_CLEAR_LOG = "server_clear_log";
    public static final String SERVER_ACTION_CLEAR_HISTORY = "server_clear_history";

    /**
     * GOLDEN_KEY : Ab-16-Byte-String
     * The Golden Key should be strictly controlled
     */
    private static final String GOLDEN_KEY = "FuckYouMicrosoft";

    public static final String CLIENT_CONFIG_FILE_PATH = "client.properties";
    public static final String SERVER_CONFIG_FILE_PATH = "server.properties";

    /**
     * communication information
     * <p>
     * PORT to choose
     * <p>
     * 5463 = T9 keyboard - LiMe
     * 54946 = T9 keyboard - LiXin
     */
    public static final String LOCALHOST = "127.0.0.1";
    public static final int DEFAULT_PORT = 5463;
    public static final int DEFAULT_DB_PORT = 1984;
    public static final String SQL_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    /**
     * client property names
     */

    public static final String PROP_LIME_COMMENT = "LiMe Config file";
    public static final String PROP_NAME_LIME_HOST = "lime.host";
    public static final String PROP_NAME_LIME_PORT = "lime.port";
    public static final String PROP_NAME_LIME_ENCRYPTED_KEY = "lime.encrypted.key";
    public static final String PROP_NAME_LIME_ENCRYPTED_USERNAME = "lime.encrypted.username";
    public static final String PROP_NAME_LIME_ENCRYPTED_PASSWORD = "lime.encrypted.password";

    /**
     * server property names
     */

    public static final String PROP_SERVER_COMMENT = "LiMe Server Config file";
    public static final String PROP_NAME_SERVER_PORT = "server.port";

    public static final String PROP_NAME_SERVER_DB_HOST = "server.db.host";
    public static final String PROP_NAME_SERVER_DB_PORT = "server.db.port";
    public static final String PROP_NAME_SERVER_DB_DB = "server.db.db";
    public static final String PROP_NAME_SERVER_DB_USERNAME = "server.db.username";
    public static final String PROP_NAME_SERVER_DB_PASSWORD = "server.db.password";

    public static final String PROP_NAME_SERVER_EMAIL_USER = "server.email.user";
    public static final String PROP_NAME_SERVER_EMAIL_DOMAIN = "server.email.domain";
    public static final String PROP_NAME_SERVER_EMAIL_PASSWORD = "server.email.password";


    /**
     * communication information
     * <p>
     * HOST domain to choose
     * <p>
     * 1. juadm.com
     * <p>
     * WANTED DOMAIN: lime.com | lime.online
     */

    private static final String ADMIN_EMAIL = "";
    private static final String STAFF_EMAIL = "";

    public static final String URL_LIME_HOMEPAGE = "https://github.com/realJustinLee/LiMe";
    private static final String URL_LIME_AGREEMENT = URL_LIME_HOMEPAGE + "/blob/master/LICENSE";

    public static final String URL_LIME_AGREEMENT_OFFLINE = "/index.html";

    // The public methods

    public static String getLiMeTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime().toString();
    }

    public static String getLiMeYear() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    public static void limeInfo(String message) {
        JOptionPane.showMessageDialog(null, message, THE_BRAND + " 提示", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void limeWarning(String message) {
        JOptionPane.showMessageDialog(null, message, THE_BRAND + " 警告", JOptionPane.WARNING_MESSAGE);
    }

    public static void limeInternalError(String address, String param) {
        JOptionPane.showMessageDialog(null,
                "错误地址：" + address + "\n" + "参数：" + param,
                THE_BRAND + " 内部错误", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }

    public static void limeExternalError(String detail, String message) {
        JOptionPane.showMessageDialog(null, detail, message, JOptionPane.ERROR_MESSAGE);
    }

    public static void showCopyright() {
        JOptionPane.showMessageDialog(null,
                THE_TITLE + " " + THE_LIME_VERSION + "\n" + THE_COPYRIGHT,
                THE_BRAND,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showServerCopyright() {
        JOptionPane.showMessageDialog(null,
                THE_SERVER_TITLE + " " + THE_SERVER_VERSION + "\n" + THE_COPYRIGHT,
                THE_SERVER_BRAND,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showHomepage() {
        // TODO: 打开 LiMe 主页
        showUrl(URL_LIME_HOMEPAGE);
    }

    /**
     * This method is Deprecated
     */
    public static void showAgreement() {
        // TODO: 打开用户协议网页
        showUrl(URL_LIME_AGREEMENT);
    }

    public static void limeEmailAdmin() {
        sendEmailFromLiMe(ADMIN_EMAIL);
    }

    public static void limeEmailStaff() {
        sendEmailFromLiMe(STAFF_EMAIL);
    }

    public static String generatePasswordAndKey() {
        String codePool = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int cpl = codePool.length();
        int length = (new Random().nextInt(4) + 2) * 6 - 1;
        char[] buffer = new char[length];
        for (int i = 0; i < length; i++) {
            if ((i + 1) % 6 == 0) {
                buffer[i] = '-';
            } else {
                buffer[i] = codePool.charAt(new Random().nextInt(cpl));
            }
        }
        return String.valueOf(buffer);
    }

    public static String digest(String content) {
        try {
            byte[] digestedBytes = LiMeMessageDigester.md5(content.getBytes(CHARSET));
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(digestedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String encrypt(String content) {
        try {
            return LiMeAesCipher.aesEncryptString(content, GOLDEN_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String encrypt(String content, String key) {
        try {
            byte[] contentBytes = content.getBytes(CHARSET);
            byte[] digestedKeyBytes = LiMeMessageDigester.md5(key.getBytes(CHARSET));
            byte[] encryptedBytes = LiMeAesCipher.aesEncryptBytes(contentBytes, digestedKeyBytes);
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String decrypt(String content) {
        try {
            return LiMeAesCipher.aesDecryptString(content, GOLDEN_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String decrypt(String content, String key) {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] encryptedBytes = decoder.decode(content);
            byte[] digestedKeyBytes = LiMeMessageDigester.md5(key.getBytes(CHARSET));
            byte[] decryptedBytes = LiMeAesCipher.aesDecryptBytes(encryptedBytes, digestedKeyBytes);
            return new String(decryptedBytes, CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // The private methods

    private static void showUrl(String url) {
        try {
            Desktop desktop = Desktop.getDesktop();
            String message = "https://" + url;
            URI uri = URI.create(message);
            desktop.browse(uri);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private static void sendEmailFromLiMe(String to) {
        try {
            Desktop desktop = Desktop.getDesktop();
            String message = "mailto:" + to;
            URI uri = URI.create(message);
            desktop.mail(uri);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
