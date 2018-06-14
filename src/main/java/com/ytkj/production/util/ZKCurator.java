package com.ytkj.production.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by lafangyuan on 2018/6/14.
 */
@Configuration
@Component
@Slf4j
public class ZKCurator {
    @Value("${zookeeper.hostPort}")
    private String hostPort;

    @Value("${zookeeper.pnode}")
    private String pnode;

    CuratorFramework client1 = null;
    public void start() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client1 = CuratorFrameworkFactory.builder().connectString(hostPort)
                .sessionTimeoutMs(5000)//会话超时时间
                .connectionTimeoutMs(5000)//连接超时时间
                .retryPolicy(retryPolicy)
                .build();
        client1.start();
        try {
            client1.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                    .forPath(pnode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final PathChildrenCache childrenCache = new PathChildrenCache(client1,pnode,true);
        childrenCache.start();
        childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
                log.info("childEvent:{}", JSON.toJSONString(event));
                WatchData watchData = new WatchData().build(event);
                DataCache.addServiceData(watchData);
                DataCache.addData(watchData);
            }
        });
    }
}
