package com.cyxPro.zk.chapter05.p5_3_4;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * Created by chenyixiong on 2018/1/10.
 */
public class ZookeeperGetDataAPIASyncUsage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;

    public void process(WatchedEvent watchedEvent) {
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            if (Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {
                connectedSemaphore.countDown();
            } else if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                try {
                    zk.getData(watchedEvent.getPath(), true,new AsyncCallback.DataCallback(){
                        public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                            System.out.println(rc + "," + path + "," + new String(data));
                            System.out.println(stat.getCzxid() + "," + stat.getMzxid() + "," + stat.getVersion());
                        }
                    }, null);
                } catch (Exception e) {
                }
            }
        }
    }

    public static void main(String[] args) throws Exception{
        String path = "/zk-book";
        zk = new ZooKeeper("127.0.0.1:2181", 5000, new ZookeeperGetDataAPIASyncUsage());
        connectedSemaphore.await();

        zk.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        zk.getData(path,true,new AsyncCallback.DataCallback(){
            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                System.out.println(rc + "," + path + "," + new String(data));
                System.out.println(stat.getCzxid() + "," + stat.getMzxid() + "," + stat.getVersion());
            }
        }, null);

        zk.setData(path, "123".getBytes(),-1);

        Thread.sleep(Integer.MAX_VALUE);
    }
}
