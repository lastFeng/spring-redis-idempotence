package com.springboot.springredisidempotence.service.impl;

import com.springboot.springredisidempotence.common.Constant;
import com.springboot.springredisidempotence.common.ResponseCode;
import com.springboot.springredisidempotence.common.ServerResponse;
import com.springboot.springredisidempotence.exception.ServiceException;
import com.springboot.springredisidempotence.service.TokenService;
import com.springboot.springredisidempotence.util.JedisUtil;
import com.springboot.springredisidempotence.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author guowf
 * @mail guowf_buaa@163.com
 * @description:
 * @data created in 2019-06-23 19:07
 */
@Service
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_NAME = "token";

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public ServerResponse createToken() {
        String str = RandomUtil.UUID32();

        // 创建token
        StringBuffer token = new StringBuffer();
        token.append(Constant.Redis.TOKEN_PREFIX).append(str);

        // 设置Redis
        jedisUtil.set(token.toString(), token.toString(), Constant.Redis.EXPIRE_TIME_MINUTE);
        return ServerResponse.success(token.toString());
    }

    @Override
    public void checkToken(HttpServletRequest request) {
        // 获取token
        String token = request.getHeader(TOKEN_NAME);

        // 判断token是否存在header中,在参数中获取
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(TOKEN_NAME);
        }

        // token依然为空，抛出异常, 没有token，
        if (StringUtils.isBlank(token)) {
            throw new ServiceException(ResponseCode.ILLEGAL_ARGUMENT.getMsg());
        }

        // 有Token，但是不存在于redis中，重复提交
        if (!jedisUtil.exists(token)) {
            throw new ServiceException(ResponseCode.REPETITVE_OPERATION.getMsg());
        }

        // 第一次，访问之后删除redis中的token
        Long del = jedisUtil.del(token);

        if (del <= 0) {
            throw new ServiceException(ResponseCode.REPETITVE_OPERATION.getMsg());
        }
    }
}
