package com.lixin.lime.server.model;

import com.lixin.lime.protocol.datastructure.LiMeStalk;
import com.lixin.lime.server.controller.LiMeServerFarmer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.lixin.lime.protocol.util.factory.MyStaticFactory.*;

/**
 * @author lixin
 */
public class LiMeServerModel implements Runnable {
    private ArrayList<LiMeStalk> limeHub;
    private LiMeServerFarmer serverFarmer;

    private ExecutorService cachedThreadPool;

    public LiMeServerModel(LiMeServerFarmer serverFarmer) {
        this.serverFarmer = serverFarmer;
        cachedThreadPool = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSock = new ServerSocket(PORT);
            while (true) {
                try {
                    Socket socketLime = serverSock.accept();
                    cachedThreadPool.execute(new LiMeJuicer(socketLime));
                    System.out.println("got a connection");
                    // TODO: Log connection to UI
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class LiMeJuicer implements Runnable {
        private Socket socketLime;
        private ObjectOutputStream oos;
        private ObjectInputStream ois;


        LiMeJuicer(Socket socketLime) {
            try {
                this.socketLime = socketLime;
                oos = new ObjectOutputStream(socketLime.getOutputStream());
                ois = new ObjectInputStream(socketLime.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {

        }
    }
}
