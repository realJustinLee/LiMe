package com.justin.lime.protocol.util.crypto;

import java.util.Base64;
import java.util.Random;

import static com.justin.lime.protocol.util.factory.LiMeStaticFactory.CHARSET;

public class LiMeCipher {
    private static final String CODE_POOL = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final String golden_key;

    public LiMeCipher(String golden_key) {
        this.golden_key = golden_key;
    }

    public static String generatePasswordAndKey() {
        int cpl = CODE_POOL.length();
        int length = (new Random().nextInt(4) + 2) * 6 - 1;
        char[] buffer = new char[length];
        for (int i = 0; i < length; i++) {
            if ((i + 1) % 6 == 0) {
                buffer[i] = '-';
            } else {
                buffer[i] = CODE_POOL.charAt(new Random().nextInt(cpl));
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

    public String encrypt(String content) {
        try {
            return LiMeAesCipher.aesEncryptString(content, golden_key);
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

    public String decrypt(String content) {
        try {
            return LiMeAesCipher.aesDecryptString(content, golden_key);
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
}
