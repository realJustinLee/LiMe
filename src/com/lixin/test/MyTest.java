package com.lixin.test;


import java.util.HashMap;

/**
 * @author lixin
 */
public class MyTest {
    private static HashMap<String, String> his = new HashMap<>();

    public static void main(String[] args) {
        his.put("test", "");
        System.out.println(his);
        his.put("test", "aaa");
        his.put("test2", "aaa");
        System.out.println(his);
    }

}
