package com.lixin.test;

import javax.swing.*;
import java.net.InetAddress;

/**
 * @author lixin
 */
public class MyTest extends JFrame {
    public static void main(String[] args) {
        try {
            InetAddress ipv4Address1 = InetAddress.getByName("1.2.3.4");
            System.out.println("ipv4Address1:" + ipv4Address1.getHostAddress());
            //ipv4Address1:1.2.3.4
            InetAddress ipv4Address2 = InetAddress.getByName("www.ibm.com");
            System.out.println("ipv4Address2:" + ipv4Address2.getHostAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
