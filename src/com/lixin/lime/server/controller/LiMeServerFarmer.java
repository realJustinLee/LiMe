package com.lixin.lime.server.controller;

import java.util.HashSet;

/**
 * @author lixin
 */
public interface LiMeServerFarmer {
    void newChatHistory(String sender, String time, String message);

    void newOnline(String username, HashSet<String> limeSet);

    void newOffline(String username, HashSet<String> limeSet);

}
