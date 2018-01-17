package com.cyxPro.zk.chapter05.curator5_4_2.test.countdownlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * CountDownLatch使用方式
 * Created by 66170 on 2018/1/12.
 */
public class ApplicationStartUpUtil {

    private static List<BaseHealthChecker> services;
    private static CountDownLatch latch;
    private final static ApplicationStartUpUtil INSTANCE = new ApplicationStartUpUtil();

    public ApplicationStartUpUtil() {
    }

    public static ApplicationStartUpUtil getInstance() {
        return INSTANCE;
    }

    public static boolean checkExternalServices() throws Exception {
        latch = new CountDownLatch(3);

        services = new ArrayList<BaseHealthChecker>();
        services.add(new NetworkHealthChecker(latch));
        services.add(new CacheHealthChecker(latch));
        services.add(new DataBaseHealthChecker(latch));

        Executor executor = Executors.newFixedThreadPool(services.size());

        for (BaseHealthChecker checker : services) {
            executor.execute(checker);
        }

        latch.await();

        for (BaseHealthChecker checker : services) {
            if (!checker.isServiceUp()) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        boolean result = false;

        try {
            result = ApplicationStartUpUtil.checkExternalServices();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("External services validation completed !! Result was :: " + result);
    }
}
