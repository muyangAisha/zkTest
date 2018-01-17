package com.cyxPro.zk.chapter05.curator5_4_2;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  使用CyclicBarrier 模拟一个赛跑比赛
 *  同一个JVM 使用CuclicBarrier完全可以解决多线程同步问题
 * Created by 66170 on 2018/1/12.
 */
public class EG14_RecipesCyclicBarrier {
    public static CyclicBarrier barrier = new CyclicBarrier(3);

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            final String name = (i + 1) + "号选手";
            executor.submit(new Thread(new Runnable() {
                public void run() {
                    System.out.println(name + "准备好了.");
                    try {
                        EG14_RecipesCyclicBarrier.barrier.await();
                    } catch (Exception e) {
                    }
                    System.out.println(name + " 起跑!");
                }
            }));
        }
        executor.shutdown();
    }
}
