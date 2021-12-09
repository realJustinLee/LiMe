package com.lixin.lime.protocol.datastructure;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 用于存储连接在 Server 或者 LiMe对话主机 上的 LiMe客户端 信息
 *
 * @author lixin
 */
public record LiMeStalk(String username, Socket socket, ObjectInputStream ois,
                        ObjectOutputStream oos) {

    public String getUsername() {
        return username;
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }
        LiMeStalk stalk = (LiMeStalk) obj;
        return username.equals(stalk.username);
    }


    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
