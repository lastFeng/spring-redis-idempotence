package com.springboot.springredisidempotence.service;

import com.springboot.springredisidempotence.domain.LoginLog;

/**
 * @author guowf
 * @mail guowf_buaa@163.com
 * @description:
 * @data created in 2019-06-23 13:49
 */
public interface LoginLogService {
    /**
     * 插入登录日志
     * @param loginLog 需要插入的登录日志
     * */
    void insert(LoginLog loginLog);

    /**
     * 根据消息id来查询登录日志
     * @param msgId 消息id
     * @return 如果有：返回登录日志；没有返回null
     * */
    LoginLog selectByMsgId(String msgId);
}
