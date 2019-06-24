package com.springboot.springredisidempotence.domain;

import lombok.*;

/**
 * @author guowf
 * @mail guowf_buaa@163.com
 * @description:
 * @data created in 2019-06-23 08:34
 * 登录用户信息pojo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    /**用户id*/
    private Integer id;
    /**用户名*/
    private String username;
    /**用户密码*/
    private String password;
}
