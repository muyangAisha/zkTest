package com.cyxPro.zk.chapter05.curator5_4_2;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * NodeCache事件监听
 * Created by 66170 on 2018/1/10.
 */
public class EG8_NodeCacheSample {
    static String path = "/zk-book/nodecache";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString("127.0.0.1:2181").sessionTimeoutMs(5000)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    public static void main(String[] args) throws Exception {
        client.start();

        /*final NodeCache cache = new NodeCache(client, path, false);
        //ture:nodeCache在第一次启动的时候就会立刻从ZooKeeper上读取对应节点的内容,并保存在Cache中。
        cache.start(true);
        cache.getListenable().addListener(new NodeCacheListener() {
            public void nodeChanged() throws Exception {
                //System.out.println("new data: " + new String(cache.getCurrentData().getData()));
                if (cache.getCurrentData() != null) {
                    System.out.println("Node data not null, new data: " + new String(cache.getCurrentData().getData()));
                } else {
                    System.out.println("Node delete");
                }
            }
        });*/


        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                .forPath(path, "init".getBytes());

        final NodeCache cache = new NodeCache(client, path, false);

        //ture:nodeCache在第一次启动的时候就会立刻从ZooKeeper上读取对应节点的内容,并保存在Cache中。
        cache.start(true);
        System.out.println("Cache Start, Get Data: " + new String(cache.getCurrentData().getData()));

        cache.getListenable().addListener(new NodeCacheListener() {
            public void nodeChanged() throws Exception {
                //System.out.println("Node data update, new data: " + new String(cache.getCurrentData().getData()));
                if (cache.getCurrentData() != null) {
                    System.out.println("Node data not null, new data: " + new String(cache.getCurrentData().getData()));
                } else {
                    System.out.println("Node delete");
                }
            }
        });
        client.setData().forPath(path, "u".getBytes());
        Thread.sleep(1000 * 1);
        client.delete().deletingChildrenIfNeeded().forPath(path);

        /*Thread.sleep(1000 * 10);
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                .forPath(path, "init2".getBytes());

        Thread.sleep(1000 * 2);

        client.close();
        System.out.println("close");
        Thread.sleep(1000 * 2);

        client.delete().deletingChildrenIfNeeded().forPath(path);*/

        Thread.sleep(Integer.MAX_VALUE);
    }

}
