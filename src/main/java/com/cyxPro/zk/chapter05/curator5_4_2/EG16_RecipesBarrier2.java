package com.cyxPro.zk.chapter05.curator5_4_2;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Curator还提供了另一种线程自发出发Barrier释放的模式
 * Created by 66170 on 2018/1/15.
 */
public class EG16_RecipesBarrier2 {
    static String path = "/curator_recipes_barrier_path";

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        CuratorFramework client = CuratorFrameworkFactory.builder()
                                .connectString("127.0.0.1:2181")
                                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                .build();

                        client.start();

                        // 成员数为5
                        DistributedDoubleBarrier doubleBarrier = new DistributedDoubleBarrier(client, path, 5);
                        Thread.sleep(Math.round(Math.random() * 3000));

                        System.out.println(Thread.currentThread().getName() + "号进入Barrier");
                        // 准备进入状态 成员达到5,所有成员同时触发
                        doubleBarrier.enter();
                        System.out.println(Thread.currentThread().getName() + "号启动");
                        Thread.sleep(Math.round(Math.random() * 3000));
                        // 再次等待 成员达到5 同时退出
                        doubleBarrier.leave();
                        System.out.println(Thread.currentThread().getName() + "号退出");
                    } catch (Exception e) {}
                }
            }).start();
        }
    }
}
