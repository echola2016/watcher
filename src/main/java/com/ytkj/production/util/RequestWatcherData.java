package com.ytkj.production.util;

import lombok.Data;

import java.util.Date;

/**
 * Created by lafangyuan on 2018/6/13.
 */
@Data
public class RequestWatcherData {

    private Date lastCreateAt;

    private int stat; //1 上线 2 下线

    private String node;


}
