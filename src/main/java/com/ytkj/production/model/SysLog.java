package com.ytkj.production.model;

import com.ytkj.production.util.WatchData;
import lombok.Data;

import java.util.Date;

/**
 * Created by lafangyuan on 2018/6/12.
 */
@Data
public class SysLog {

    private String userName="Watcher";
    private String module="Watcher";
    private String subModule="监控";
    private String type;
    private String brief;
    private String detail;
    private Date time;

    public SysLog build(WatchData watchData){
        this.type=watchData.getType()==null?"NewConnection":watchData.getType();
        this.brief=watchData.getMsg();
        this.time = new Date();
        return this;
    }
}
