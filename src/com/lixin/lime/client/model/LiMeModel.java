package com.lixin.lime.client.model;

import com.lixin.lime.client.controller.LiMeFarmer;
import com.lixin.lime.protocol.exception.LiMeException;
import com.lixin.lime.protocol.seed.LiMeSeed;
import com.lixin.lime.protocol.util.factory.LiMeExceptionFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.lixin.lime.protocol.seed.LiMeSeed.*;
import static com.lixin.lime.protocol.util.factory.MyStaticFactory.*;

/**
 * @author lixin
 */
public class LiMeModel {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private LiMeFarmer farmer;


    private LiMeExceptionFactory exceptionFactory;

    public LiMeModel(String host, int port, LiMeFarmer farmer) throws LiMeException {
        exceptionFactory = new LiMeExceptionFactory();
        this.farmer = farmer;
        try {
            socket = new Socket(host, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            throw exceptionFactory.newLiMeException(ERROR_CONNECTION);
        }
    }

    public void login(String username, String password) throws LiMeException {
        LiMeSeed seedReturn = null;
        try {
            oos.writeObject(new LiMeSeed(username, password));
            oos.flush();
            seedReturn = (LiMeSeed) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw exceptionFactory.newLiMeException(ERROR_CONNECTION);
        }
        if (seedReturn == null) {
            throw exceptionFactory.newLiMeException(ERROR_CONNECTION);
        } else {
            int action = seedReturn.getAction();
            if (action != LOGIN_SUCCESS) {
                throw exceptionFactory.newLiMeException(action);
            }
        }

        /*
         * CachedThreadPool 是通过 java.util.concurrent.Executors 创建的 ThreadPoolExecutor 实例
         * 这个实例会根据需要，在线程可用时，重用之前构造好的池中线程
         * 这个线程池在执行 大量短生命周期的异步任务时（many short-lived asynchronous task），可以显著提高程序性能
         * 调用 execute 时，可以重用之前已构造的可用线程，如果不存在可用线程，那么会重新创建一个新的线程并将其加入到线程池中
         * 如果线程超过 60 秒还未被使用，就会被中止并从缓存中移除。因此，线程池在长时间空闲后不会消耗任何资源
         */
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(new SeedGrinder());
    }

    synchronized public void sendMessage(String sender, String receiver, String message) throws LiMeException {
        try {
            oos.writeObject(new LiMeSeed(sender, receiver, message, getLiMeTime()));
        } catch (Exception e) {
            e.printStackTrace();
            throw exceptionFactory.newLiMeException(ERROR_CONNECTION);
        }
    }

    private class SeedGrinder implements Runnable {
        @Override
        public void run() {
            LiMeSeed seed;
            try {
                while ((seed = (LiMeSeed) ois.readObject()) != null) {
                    farmer.newLiMeMessage(seed);
                }
            } catch (Exception e) {
                e.printStackTrace();
                farmer.handleLiMeException(exceptionFactory.newLiMeException(ERROR_CONNECTION));
            }
        }
    }
}
