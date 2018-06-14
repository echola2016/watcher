package com.ytkj.production.dao;

import com.ytkj.production.model.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by lafangyuan on 2018/6/12.
 */
@Repository
public class SysLogDaoImpl implements SysLogDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public void insert(SysLog sysLog) {
        jdbcTemplate.update("insert into sys_log(user_name,time,session_id,module,sub_module,type,brief)" +
                " values (?,?,?,?,?,?,?)",sysLog.getUserName(),sysLog.getTime(),0,sysLog.getModule(),
                sysLog.getSubModule(),sysLog.getType(),sysLog.getBrief());
    }
}
