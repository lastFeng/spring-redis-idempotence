package com.springboot.springredisidempotence.service;

import com.springboot.springredisidempotence.common.ServerResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author guowf
 * @mail guowf_buaa@163.com
 * @description:
 * @data created in 2019-06-23 13:51
 */
public interface TokenService {
    /**
     * 创建token
     * @return 响应对象
     * */
    ServerResponse createToken();
    /**
     * 检测token是否存在
     * @param request 通过传入的request来进行token获取，
     *                如果数据库里没有token，则表示重复提交或出错，进行相应处理
     * */
    void checkToken(HttpServletRequest request);
}
