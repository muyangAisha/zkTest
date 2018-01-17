package com.cyxPro.zk.chapter05.p5_3_1;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * Created by chenyixiong on 2018/1/8.
 */
public class ZooKeeperCounstructorUsageWithSID_PASSWD implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public void process(WatchedEvent watchedEvent) {
        System.out.println("Receive watched event:" + watchedEvent);
        System.out.println();
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            connectedSemaphore.countDown();
        }
    }

    public static void main(String[] args) throws Exception {
        ZooKeeper zookeeper = new ZooKeeper("127.0.0.1:2181",
                5000,
                new ZooKeeperCounstructorUsageWithSID_PASSWD());

        connectedSemaphore.await();

        long sessionId=zookeeper.getSessionId();
        byte[] passwd=zookeeper.getSessionPasswd();

        zookeeper =new ZooKeeper("127.0.0.1:2181",
                5000,
                new ZooKeeperCounstructorUsageWithSID_PASSWD(),
                1,"test".getBytes());

        zookeeper =new ZooKeeper("127.0.0.1:2181",
                5000,
                new ZooKeeperCounstructorUsageWithSID_PASSWD(),
                sessionId,passwd);

        Thread.sleep(Integer.MAX_VALUE);
    }
}
