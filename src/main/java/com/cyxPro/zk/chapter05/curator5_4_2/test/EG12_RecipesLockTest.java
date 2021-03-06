package com.cyxPro.zk.chapter05.curator5_4_2.test;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * Created by chenyixiong on 2018/3/20.
 */
public class EG12_RecipesLockTest {
    static String lock_path = "/curator_recipes_lock_path";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString("127.0.0.1:2181")
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    public static void main(String[] args) throws Exception {
        client.start();
        final InterProcessMutex lock = new InterProcessMutex(client, lock_path);
        final CountDownLatch down = new CountDownLatch(1);
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
        for (int i = 0; i < 30; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        //down.await();
                        //获取锁
                        lock.acquire();
                    } catch (Exception e) {
                    }

                    String orderNo = sdf.format(new Date());
                    System.out.println("生成的订单号是：" + orderNo);

                    try {
                        Thread.sleep(1000*2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        //释放锁
                        lock.release();
                    } catch (Exception e) {
                    }

                }
            }).start();
        }
        //down.countDown();
    }
}
