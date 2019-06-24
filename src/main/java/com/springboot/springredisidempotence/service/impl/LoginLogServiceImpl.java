package com.springboot.springredisidempotence.service.impl;

import com.springboot.springredisidempotence.domain.LoginLog;
import com.springboot.springredisidempotence.mapper.LoginLogMapper;
import com.springboot.springredisidempotence.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author guowf
 * @mail guowf_buaa@163.com
 * @description:
 * @data created in 2019-06-23 19:06
 */
public class LoginLogServiceImpl implements LoginLogService {
    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public void insert(LoginLog loginLog) {
        loginLogMapper.insert(loginLog);
    }

    @Override
    public LoginLog selectByMsgId(String msgId) {
        return loginLogMapper.selectByMsgId(msgId);
    }
}
