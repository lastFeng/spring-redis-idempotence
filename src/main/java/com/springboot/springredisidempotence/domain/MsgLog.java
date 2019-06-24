package com.springboot.springredisidempotence.domain;

import lombok.*;
import org.checkerframework.checker.units.qual.A;

import java.util.Date;

/**
 * @author guowf
 * @mail guowf_buaa@163.com
 * @description:
 * @data created in 2019-06-23 08:31
 * 消息日志pojo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MsgLog {
    /**日志消息id*/
    private String msgId;
    /**日志消息*/
    private String msg;
    /**消息状态*/
    private Integer status;
    /**尝试次数*/
    private Integer tryCount;
    /**下一次尝试时间*/
    private Date nextTryTime;
    /**创建时间*/
    private Date createTime;
    /**修改时间*/
    private Date updateTime;
}
