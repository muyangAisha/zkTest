package com.cyxPro.zk.chapter05.p5_3_2;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.AsyncCallback;

import java.util.concurrent.CountDownLatch;

/**
 * Created by chenyixiong on 2018/1/8.
 */
public class ZooKeeperCreateAPIASyncUsage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public void process(WatchedEvent watchedEvent) {
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            connectedSemaphore.countDown();
        }
    }

    public static void main(String[] args) throws Exception {
        ZooKeeper zookeeper = new ZooKeeper("127.0.0.1:2181",
                5000,
                new ZooKeeperCreateAPIASyncUsage());

        connectedSemaphore.await();

        zookeeper.create("/zk-test-ephemeral-",
                "".getBytes(),
                Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                new AsyncCallback.StringCallback() {
                    public void processResult(int rc, String path, Object ctx, String name) {
                        System.out.println("Create path result: [" + rc + ", " + path + ", " + ctx + ", real path name: " + name);
                    }
                },
                "I am context.");

        zookeeper.create("/zk-test-ephemeral-",
                "".getBytes(),
                Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                new AsyncCallback.StringCallback() {
                    public void processResult(int rc, String path, Object ctx, String name) {
                        System.out.println("Create path result: [" + rc + ", " + path + ", " + ctx + ", real path name: " + name);
                    }
                },
                "I am context.");

        zookeeper.create("/zk-test-ephemeral-",
                "".getBytes(),
                Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL,
                new AsyncCallback.StringCallback() {
                    public void processResult(int rc, String path, Object ctx, String name) {
                        System.out.println("Create path result: [" + rc + ", " + path + ", " + ctx + ", real path name: " + name);
                        System.out.println();
                    }
                },
                "I am context.");

        Thread.sleep(Integer.MAX_VALUE);
    }
}
