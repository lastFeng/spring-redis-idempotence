package com.springboot.springredisidempotence.service.impl;

import com.springboot.springredisidempotence.common.ServerResponse;
import com.springboot.springredisidempotence.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @author guowf
 * @mail guowf_buaa@163.com
 * @description:
 * @data created in 2019-06-23 19:07
 */
public class TokenServiceImpl implements TokenService {

    // TODO: 将token插入到redis中

    @Override
    public ServerResponse createToken() {
        return null;
    }

    @Override
    public void checkToken(HttpServletRequest request) {

    }
}
