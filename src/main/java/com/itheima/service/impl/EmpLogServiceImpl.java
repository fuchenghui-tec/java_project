package com.itheima.service.impl;

import com.itheima.entity.EmpLog;
import com.itheima.mapper.EmpLogMapper;
import com.itheima.service.EmpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpLogServiceImpl implements EmpLogService {
    @Autowired
    private EmpLogMapper empLogMapper;

    /**
     * 插入操作日志
     * @param empLog
     */
    // @Transactional(propagation = Propagation.REQUIRED)  //开启事务，REQUIRED默认值，有则加入，无则创建
    @Transactional(propagation = Propagation.REQUIRES_NEW)  //开启事务, 开启新事务
    @Override
    public void insertLog(EmpLog empLog){
        empLogMapper.insert(empLog);
    }
}
