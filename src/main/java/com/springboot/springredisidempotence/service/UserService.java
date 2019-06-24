package com.springboot.springredisidempotence.service;

import com.springboot.springredisidempotence.common.ServerResponse;
import com.springboot.springredisidempotence.domain.User;

import java.util.List;

/**
 * @author guowf
 * @mail guowf_buaa@163.com
 * @description:
 * @data created in 2019-06-23 19:01
 */
public interface UserService {

    /**
     * 获取用户列表
     * @return lists of user
     * */
    List<User> getAll();

    /**
     * 根据用户Id获取用户
     * @param id 用户ID
     * @return User
     * */
    User getOne(Integer id);

    /**
     * 新增用户
     * */
    void add(User user);

    /**更新用户*/
    void update(User user);

    /**通过ID删除用户*/
    void delete(Integer id);

    /**通过用户名和密码查找用户*/
    User getByUsernameAndPassword(String username, String password);

    /**获取登录状态*/
    ServerResponse login(String username, String password);
}
