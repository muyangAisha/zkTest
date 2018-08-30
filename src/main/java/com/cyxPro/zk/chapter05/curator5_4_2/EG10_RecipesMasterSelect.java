package com.cyxPro.zk.chapter05.curator5_4_2;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 使用Curator 实现分布式Master选举(看不出来)
 * 看 LeaderSelectorTest
 * Created by 66170 on 2018/1/10.
 */
public class EG10_RecipesMasterSelect {

    static String master_path = "/curator_recipes_master_path";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString("127.0.0.1:2181").sessionTimeoutMs(5000)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    public static void main(String[] args) throws Exception{
        client.start();
        LeaderSelector selector = new LeaderSelector(client, master_path, new LeaderSelectorListenerAdapter() {
            public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                System.out.println("cyx11111111111111111111111成为Master角色");
                Thread.sleep(3000);
                System.out.println("完成Master操作, 释放Master权利11111111111111111111111111");


            }
        });

        selector.autoRequeue();
        selector.start();

//        selector.close();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
