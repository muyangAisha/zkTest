package com.cyxPro.zk.chapter05.p5_3_3;

import com.cyxPro.zk.chapter05.p5_3_2.ZookeeperCreateAPISyncUsage;
import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * Created by chenyixiong on 2018/1/8.
 */
public class ZooKeeperDeleteAPISyncUsage implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public void process(WatchedEvent watchedEvent) {
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            connectedSemaphore.countDown();
        }
    }

    public static void main(String[] args) throws Exception {
        ZooKeeper zookeeper = new ZooKeeper("127.0.0.1:2181",
                5000,
                new ZooKeeperDeleteAPISyncUsage());

        connectedSemaphore.await();

        String path = zookeeper.create("/zk-book",
                "testdata".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        System.out.println("success create znode: " + path);

        String childPath = zookeeper.create("/zk-book/c1",
                "childTestData".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        System.out.println("success create znode: " + childPath);

        try {
            zookeeper.delete(path, -1);
        } catch (Exception e) {
            System.out.println("fail to delete znode: " + path);
        }

        zookeeper.delete(childPath, -1);
        System.out.println("success delete znode: " + childPath);

        zookeeper.delete(path, -1);
        System.out.println("success delete znode: " + path);

        Thread.sleep(Integer.MAX_VALUE);
    }


}
