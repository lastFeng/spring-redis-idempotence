package com.springboot.springredisidempotence.common;

/**
 * @author guowf
 * @mail guowf_buaa@163.com
 * @description:
 * @data created in 2019-06-23 10:09
 */
public class Constant {
    public interface Redis{
        String OK = "OK";
        // 过期时间，60s，一分钟
        Integer EXPIRE_TIME_MINUTE = 60;
        // 过期时间，一小时
        Integer EXPIRE_TIME_HOUR = 60 * 60;
        // 过期时间，一天
        Integer EXPIRE_TIME_DAY = 60 * 60 * 24;

        String TOKEN_PREFIX = "token";
        String ACCESSS_LIMIT_PREFIX = "accessLimit";
    }

    public interface LogType{
        // 登录
        Integer LOGIN = 1;
        // 登出
        Integer LOGOUT = 2;
    }

    public interface MsgLogStatus{
        Integer SENDING = 0;
        Integer SUCCESS = 1;
        Integer FAIL = 2;
    }
}
