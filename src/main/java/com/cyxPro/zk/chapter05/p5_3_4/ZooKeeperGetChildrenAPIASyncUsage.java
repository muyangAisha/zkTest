package com.cyxPro.zk.chapter05.p5_3_4;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by chenyixiong on 2018/1/9.
 */
public class ZooKeeperGetChildrenAPIASyncUsage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;

    public void process(WatchedEvent watchedEvent) {
        if (Watcher.Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            if(Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()){
                connectedSemaphore.countDown();
            }else if(watchedEvent.getType() == Event.EventType.NodeChildrenChanged){
                try {
                    System.out.println("ReGetChild:"+zk.getChildren(watchedEvent.getPath(),true));
                }catch (Exception e){}
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String path = "/zk-book";
        zk = new ZooKeeper("127.0.0.1:2181",
                5000,
                new ZooKeeperGetChildrenAPIASyncUsage());

        connectedSemaphore.await();

        zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        zk.getChildren(path, true, new AsyncCallback.Children2Callback() {
            public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
                System.out.println("Get Children znode result: [response code: " + rc + ", param path: " + path + ", ctx: " + ctx + ", children list: " + children + ", stat: " + stat);
            }
        }, null);

        zk.create(path + "/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        Thread.sleep(Integer.MAX_VALUE);
    }
}
