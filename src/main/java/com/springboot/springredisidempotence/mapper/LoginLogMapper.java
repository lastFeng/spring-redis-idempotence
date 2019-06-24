package com.springboot.springredisidempotence.mapper;

import com.springboot.springredisidempotence.domain.LoginLog;

/**
 * @author guowf
 * @mail guowf_buaa@163.com
 * @description:
 * @data created in 2019-06-23 08:36
 */
public interface LoginLogMapper {
    /**
     * 插入登录日志
     * @param loginLog 登录日志对象
     * */
    void insert(LoginLog loginLog);

    /**
     * 通过消息id查找登录日志
     * @param msgId 消息id
     * @return 登录日志
     * */
    LoginLog selectByMsgId(String msgId);
}
