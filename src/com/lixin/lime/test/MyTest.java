package com.lixin.lime.test;

import com.lixin.lime.client.controller.LiMe;

/**
 * @author lixin
 */
public class MyTest {
    public static void main(String[] args) {
        MyTest test = new MyTest();
        test.printName();
    }

    private void printName() {
        System.out.println(this.getClass().getCanonicalName());
    }
}
