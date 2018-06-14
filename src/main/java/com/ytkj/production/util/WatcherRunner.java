package com.ytkj.production.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by lafangyuan on 2018/6/12.
 */
@Component
public class WatcherRunner implements ApplicationRunner {


    @Autowired
    private NetWatcher netWatcher;

    @Autowired
    private ZKCurator zkCurator;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        netWatcher.start();
        zkCurator.start();
    }
}
