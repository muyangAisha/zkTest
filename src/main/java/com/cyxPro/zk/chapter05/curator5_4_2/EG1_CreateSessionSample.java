package com.cyxPro.zk.chapter05.curator5_4_2;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 使用Curator 来创建一个ZooKeeper客户端
 * Created by chenyixiong on 2018/1/10.
 */
public class EG1_CreateSessionSample {
    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181",
                5000,
                3000,
                retryPolicy);

        client.start();

        Thread.sleep(Integer.MAX_VALUE);
    }
}
