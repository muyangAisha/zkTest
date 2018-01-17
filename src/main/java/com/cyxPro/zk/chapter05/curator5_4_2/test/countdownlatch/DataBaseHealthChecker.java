package com.cyxPro.zk.chapter05.curator5_4_2.test.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * Created by 66170 on 2018/1/12.
 */
public class DataBaseHealthChecker extends BaseHealthChecker {

    public DataBaseHealthChecker(CountDownLatch latch) {
        super(latch, "DataBase Service");
    }

    public void verifyService() {
        System.out.println("Checking "+this.getServiceName());
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName()+" is up");
    }
}
