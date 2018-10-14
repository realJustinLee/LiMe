package com.lixin.lime.tester;

import com.lixin.lime.crypto.AesCipher;


/**
 * @author lixin
 */
public class MyTest {
    public static void main(String[] args) {
        try {
            String data = "NowIWorkForApple";
            String key = "FuckYouMicrosoft";

            String des = AesCipher.aesEncryptString(data, key);
            System.out.println(des);

            String src = AesCipher.aesDecryptString(des, key);
            System.out.println(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
