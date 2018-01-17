package com.cyxPro.zk.chapter05.p5_3_1;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * Created by chenyixiong on 2018/1/8.
 */
public class ZooKeeperCounstructorUsageSimple implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public void process(WatchedEvent watchedEvent) {
        System.out.println("Receive watched event:" + watchedEvent);
        if(Event.KeeperState.SyncConnected == watchedEvent.getState()){
            connectedSemaphore.countDown();
        }
    }

    public static void main(String[] args) throws Exception{
        ZooKeeper zookeeper=new ZooKeeper("127.0.0.1:2181",5000, new ZooKeeperCounstructorUsageSimple());
        System.out.println(zookeeper.getState());

        try {
            connectedSemaphore.await();
        }catch (InterruptedException e){
            System.out.println("ZooKeeper session established.");
        }
    }
}
