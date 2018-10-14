package com.lixin.lime.tester;

import com.lixin.lime.crypto.AesCipher;


/**
 * @author lixin
 */
public class MyTest {
    public static void main(String[] args) {
        try {
            String data = "This content will app";
            String key = "16BytesLengthKey";

            String aes = AesCipher.aesEncryptString(data, key);
            System.out.println(aes);

            String ori = AesCipher.aesDecryptString(aes, key);
            System.out.println(ori);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
