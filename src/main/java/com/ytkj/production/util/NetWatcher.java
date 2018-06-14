package com.ytkj.production.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lafangyuan on 2018/6/13.
 */
@Component
@Configuration
public class NetWatcher {

    @Value("${target-ips}")
    private String targetIp;

    public boolean isConnect(String ip){
        boolean connect = false;
        Runtime runtime = Runtime.getRuntime();
        Process process;
        try {
            process = runtime.exec("ping " + ip);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            is.close();
            isr.close();
            br.close();

            if (null != sb && !sb.toString().equals("")) {
                String logString = "";
                if (sb.toString().indexOf("TTL") > 0) {
                    // 网络畅通
                    connect = true;
                } else {
                    // 网络不畅通
                    connect = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connect;
    }

    public void start() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    boolean cn = isConnect(targetIp);
                    WatchData watchData = new WatchData();
                    watchData.setZnode(targetIp);
                    if (cn) {
                        watchData.setColor(3);
                        watchData.setMsg("网络连接成功");
                    } else {
                        watchData.setColor(4);
                        watchData.setMsg("网络连接失败");
                    }
                    DataCache.addData(watchData);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();

    }
}
