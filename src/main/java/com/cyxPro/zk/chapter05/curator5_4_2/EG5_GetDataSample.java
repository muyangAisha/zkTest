package com.cyxPro.zk.chapter05.curator5_4_2;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * 使用Curator获取数据内容
 * Created by 66170 on 2018/1/10.
 */
public class EG5_GetDataSample {
    static String path="/zk-book";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString("127.0.0.1:2181").sessionTimeoutMs(5000)
            .retryPolicy(new ExponentialBackoffRetry(1000,3))
            .build();

    public static void main(String[] args) throws Exception{
        client.start();
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                .forPath(path,"init".getBytes());

        Stat stat=new Stat();
        System.out.println(new String(client.getData().storingStatIn(stat).forPath(path)));

    }
 }
