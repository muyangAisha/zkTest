package com.cyxPro.zk.chapter05.curator5_4_2.test;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 66170 on 2018/1/11.
 */
public class LeaderSelectorTest {

    static String master_path = "/curator_recipes_master_path";

    public static void main(String[] args) throws Exception {
        List<LeaderSelector> selectors = new ArrayList<LeaderSelector>();
        List<CuratorFramework> clients = new ArrayList<CuratorFramework>();

        try {
            for (int i = 0; i < 10; i++) {
                CuratorFramework client = CuratorFrameworkFactory.builder()
                        .connectString("127.0.0.1:2181").sessionTimeoutMs(5000)
                        .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                        .build();
                client.start();
                clients.add(client);

                final String name = "client#"+i;
                LeaderSelector selector = new LeaderSelector(client, master_path, new LeaderSelectorListenerAdapter() {
                    public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                        System.out.println(name+"成为Master角色");
                        Thread.sleep(3000);
                        System.out.println(name+"完成Master操作, 释放Master权利");
                    }
                });

                selector.autoRequeue();
                selector.start();
                selectors.add(selector);
            }
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /*for(CuratorFramework client : clients){
                CloseableUtils.closeQuietly(client);
            }

            for(LeaderSelector selector : selectors){
                CloseableUtils.closeQuietly(selector);
            }*/
        }


    }


}
