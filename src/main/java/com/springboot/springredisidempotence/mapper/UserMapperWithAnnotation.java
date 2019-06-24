package com.springboot.springredisidempotence.mapper;

import com.springboot.springredisidempotence.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author guowf
 * @mail guowf_buaa@163.com
 * @description:
 * @data created in 2019-06-23 09:32
 */
public interface UserMapperWithAnnotation {
    /**查找所有用户*/
    @Select("select * from user")
    @Results({
            @Result(property = "username", column = "username", jdbcType = JdbcType.VARCHAR),
            @Result(property = "password", column = "password")
    })
    List<User> selectAll();

    /**根据用户id查找对应用户*/
    @Select("Select * from user where id=#{id}")
    @Results({
            @Result(property = "username", column = "username", jdbcType = JdbcType.VARCHAR),
            @Result(property = "password", column = "password")
    })
    User selectOne(Integer id);

    /**插入新用户*/
    @Insert("insert into user(username, password) values(#{username}, #{password})")
    void insert(User user);

    /**更新用户*/
    @Update("update user set username=#{username}, password=#{password} where id=#{id}")
    void update(User user);

    /**删除用户*/
    @Delete("delete from user where id=#{id}")
    void delete(Integer id);
}
