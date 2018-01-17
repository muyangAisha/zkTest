package com.cyxPro.zk.chapter05.curator5_4_2;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 使用Fluent风格的API接口来创建一个ZooKeeper客户端
 * Created by 66170 on 2018/1/10.
 */
public class EG2_CreateSessionSampleFluent {
    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                .sessionTimeoutMs(5000).retryPolicy(retryPolicy).build();

        client.start();

        Thread.sleep(Integer.MAX_VALUE);
    }
}
