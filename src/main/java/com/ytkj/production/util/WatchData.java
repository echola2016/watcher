package com.ytkj.production.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.Date;

/**
 * Created by lafangyuan on 2018/6/12.
 */
@Data
public class WatchData {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8")
    private Date createAt;

    private String type;

    private String msg;

    private String znode;

    private int color=0;


    public WatchData build(WatchedEvent event){
        znode = event.getPath();
        if(event.getType().equals(Watcher.Event.EventType.NodeDeleted)){
            msg="服务下线";
            color=1;
        }else if(event.getType().equals(Watcher.Event.EventType.NodeCreated)){
            msg="服务上线";
        }else if(event.getType().equals(Watcher.Event.EventType.None)){
            if(event.getState().equals(Watcher.Event.KeeperState.SyncConnected)){
                msg="监控服务连接成功";
            }else if(event.getState().equals(Watcher.Event.KeeperState.Disconnected)){
                msg="监控服务连接失败，请检查网络";
                color=1;
            }else if(event.getState().equals(Watcher.Event.KeeperState.Expired)){
                msg="服务连接超时";
                color=1;
            }
        }
        return this;
    }
    public WatchData build(PathChildrenCacheEvent event){
        if(event.getData()==null) {
            switch (event.getType()){
                case CONNECTION_LOST:{
                    msg = "监控服务失去连接";
                    color = 1;
                    return this;
                }
                case CONNECTION_RECONNECTED:{
                    msg = "监控服务连接成功";
                    return this;
                }
            }

        }
        znode = event.getData().getPath();
        switch (event.getType()){
            case CHILD_ADDED:{
                msg="服务上线";
                break;
            }
            case CHILD_REMOVED:{
                msg="服务下线";
                color=1;
                break;
            }
            case CONNECTION_RECONNECTED:{
                msg="服务连接成功";
                break;
            }
            case CONNECTION_LOST:{
                msg="服务连接失败";
                color=1;
                break;
            }
        }
        return this;
    }
    public WatchData buildLossConnection(){
        msg = "连接Zookeeper失败";
        color=1;
        return this;
    }
    private void buildMsg(){
        if("NodeCreated".equals(type)){
            msg="服务上线";
        }else if("NodeDeleted".equals(type)){
            msg="服务下线";
        }
    }
}
