package com.lixin.lime.protocol.util.crypto;

import java.security.MessageDigest;
import java.util.Base64;

/**
 * @author lixin
 */
public class LiMeMessageDigester {
    public static String md5(String content) {
        try {
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(md5(content.getBytes()));
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] md5(byte[] contentBytes) {
        try {
            MessageDigest digester = MessageDigest.getInstance("MD5");
            return digester.digest(contentBytes);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * deprecated
     */
    public static String md5(String content, String charset) {
        try {
            MessageDigest digester = MessageDigest.getInstance("MD5");
            if (charset.isEmpty()) {
                return md5(content);
            }
            byte[] digestedBytes = digester.digest(content.getBytes(charset));
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(digestedBytes);
        } catch (Exception e) {
            return null;
        }
    }
}
