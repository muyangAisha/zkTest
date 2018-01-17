package com.cyxPro.zk.chapter05.curator5_4_2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 一个经典时间戳生成的并发问题
 * Created by 66170 on 2018/1/12.
 */
public class EG11_RecipesNoLock {

    public static void main(String[] args) throws Exception {
        final CountDownLatch down = new CountDownLatch(1);
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        down.await();
                    } catch (Exception e) {
                    }

                    String orderNo = sdf.format(new Date());
                    System.out.println("生成的订单号:" + orderNo);
                }
            }).start();
        }
        down.countDown();
    }
}
