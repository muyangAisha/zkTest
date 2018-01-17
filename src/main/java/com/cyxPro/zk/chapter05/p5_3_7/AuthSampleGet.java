package com.cyxPro.zk.chapter05.p5_3_7;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by chenyixiong on 2018/1/10.
 */
public class AuthSampleGet {
    final static String PATH = "/zk-book-auth_test";

    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 5000, null);
        zk.addAuthInfo("digest", "foo:true".getBytes());
        zk.create(PATH,"init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

        ZooKeeper zk2 = new ZooKeeper("127.0.0.1:2181", 50000, null);
        zk2.getData(PATH,false,null);

        Thread.sleep(Integer.MAX_VALUE);
    }
}
