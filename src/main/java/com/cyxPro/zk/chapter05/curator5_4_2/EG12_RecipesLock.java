package com.cyxPro.zk.chapter05.curator5_4_2;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 使用Curator 实现分布式锁功能
 * Created by 66170 on 2018/1/12.
 */
public class EG12_RecipesLock {
    static String lock_path = "/curator_recipes_lock_path";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString("127.0.0.1:2181")
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    public static void main(String[] args) throws Exception {
        client.start();
        final InterProcessMutex lock = new InterProcessMutex(client, lock_path);
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
        for (int i = 0; i < 30; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        //获取锁
                        lock.acquire();
                    } catch (Exception e) {
                    }

                    String orderNo = sdf.format(new Date());
                    System.out.println("生成的订单号是：" + orderNo);

                    try {
                        //释放锁
                        lock.release();
                    } catch (Exception e) {
                    }

                }
            }).start();
        }
    }

}
