package com.ytkj.production.controller;

import com.ytkj.production.http.Result;
import com.ytkj.production.util.DataCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by lafangyuan on 2018/6/12.
 */
@Controller
public class WatcherController {

    @RequestMapping("{path}")
    public String page(@PathVariable String path){
        return path;
    }

    @RequestMapping("/loadData")
    @ResponseBody
    public Result loadWatchData(){
        return new Result().success(DataCache.loadData());
    }
    @RequestMapping("/loadServiceData")
    @ResponseBody
    public Result loadServiceData(){
        return new Result().success(DataCache.loadServiceData());
    }

    /*@RequestMapping("/watch")
    public Result watcher(String node){
        RequestWatcher.receive(node);
        return new Result().success();
    }*/
}
