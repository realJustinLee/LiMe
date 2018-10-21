package com.lixin.test;

import java.util.Calendar;

/**
 * @author lixin
 */
public class MyTest {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.YEAR));

        boolean a = true;
        int b = 1;
        int c = 100;

        L1:
        {
            while (c > 0) {
                switch (b) {
                    case 1:
                        System.out.println(b);
                        b++;
                        break L1;
//                        break;
                    case 2:
                        System.out.println(b);
                        break;
                    default:
                        break;
                }
            }
        }


    }
}
