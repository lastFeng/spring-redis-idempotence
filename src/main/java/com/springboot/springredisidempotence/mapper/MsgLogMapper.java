package com.springboot.springredisidempotence.mapper;

import com.springboot.springredisidempotence.domain.MsgLog;

import java.util.List;

/**
 * @author guowf
 * @mail guowf_buaa@163.com
 * @description:
 * @data created in 2019-06-23 09:07
 */
public interface MsgLogMapper {
    /**
     * 插入消息日志
     * @param msgLog 待插入的消息日志
     * */
    void insert(MsgLog msgLog);
    /**
     * 更新消息状态
     * @param msgLog 需要更新的消息日志
     * */
    void updateStatus(MsgLog msgLog);
    /**
     * 查找超时的消息日志
     * @return list
     * */
    List<MsgLog> selectTimeoutMsg();
    /**
     * 更新尝试次数
     * @param msgLog 需要更新次数的消息日志对象
     * */
    void updateTryCount(MsgLog msgLog);
}
