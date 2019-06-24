package com.springboot.springredisidempotence.domain;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author guowf
 * @mail guowf_buaa@163.com
 * @description:
 * @data created in 2019-06-23 08:26
 * 登录日志
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginLog implements Serializable {
    private static final long serialVersionUID = 9035584359424322836L;

    /**日志id*/
    private Integer id;
    /**用户id*/
    private Integer userId;
    /**消息id*/
    private String msgId;
    /**日志类型*/
    private Integer type;
    /**日志说明*/
    private String description;
    /**日志创建时间*/
    private Date createTime;
    /**日志修改时间*/
    private Date updateTime;
}
