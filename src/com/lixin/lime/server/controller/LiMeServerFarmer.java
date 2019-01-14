package com.lixin.lime.server.controller;

/**
 * @author lixin
 */
public interface LiMeServerFarmer {
    void newChatLog(String log);

    void newOnline(String username);

    void newOffline(String username);

}
