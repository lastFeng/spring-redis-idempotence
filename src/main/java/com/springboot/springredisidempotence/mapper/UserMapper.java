package com.springboot.springredisidempotence.mapper;

import com.springboot.springredisidempotence.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author guowf
 * @mail guowf_buaa@163.com
 * @description:
 * @data created in 2019-06-23 09:13
 * 用户Mapper
 */
public interface UserMapper {
    /**查找所有用户*/
    List<User> selectAll();

    /**根据用户id查找用户*/
    User selectOne(Integer id);

    /**插入新用户*/
    void insert(User user);

    /**更新用户*/
    void update(User user);

    /**删除用户*/
    void delete(Integer id);

    /**通过用户名和密码查询用户*/
    User selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
