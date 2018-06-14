package com.ytkj.production.util;

import com.ytkj.production.http.Result;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lafangyuan on 2018/6/12.
 */
public class DataCache {

    private static LinkedList<WatchData> watchDataList = new LinkedList<>();
    private static LinkedList<WatchData> watchDataServiceList = new LinkedList<>();

    public static List<WatchData> loadServiceData(){
        return watchDataServiceList;
    }
    public static List<WatchData> loadData(){
            return watchDataList;
    }
    public static void addData(WatchData watchData){
        watchData.setCreateAt(new Date());
        watchDataList.add(watchData);
        if(watchDataList.size()>20){
            watchDataList.remove();
        }
    }
    public static void addServiceData(WatchData watchData){
        watchData.setCreateAt(new Date());
        watchDataServiceList.add(watchData);
        if(watchDataServiceList.size()>10){
            watchDataServiceList.remove();
        }
    }
}
