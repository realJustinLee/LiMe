package com.lixin.lime.protocol.util.datastructure;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 用于存储连接在 Server 或者 LiMe对话主机 上的 LiMe客户端 信息
 *
 * @author lixin
 */
public class LiMeStalk {
    private String username;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
}
