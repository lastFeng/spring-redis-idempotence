package com.springboot.springredisidempotence.service.impl;

import com.springboot.springredisidempotence.amqp.MessageHelper;
import com.springboot.springredisidempotence.common.Constant;
import com.springboot.springredisidempotence.common.ResponseCode;
import com.springboot.springredisidempotence.common.ServerResponse;
import com.springboot.springredisidempotence.config.RabbitConfig;
import com.springboot.springredisidempotence.domain.LoginLog;
import com.springboot.springredisidempotence.domain.MsgLog;
import com.springboot.springredisidempotence.domain.User;
import com.springboot.springredisidempotence.mapper.MsgLogMapper;
import com.springboot.springredisidempotence.mapper.UserMapper;
import com.springboot.springredisidempotence.service.UserService;
import com.springboot.springredisidempotence.util.JodaTimeUtil;
import com.springboot.springredisidempotence.util.JsonUtil;
import com.springboot.springredisidempotence.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author guowf
 * @mail guowf_buaa@163.com
 * @description:
 * @data created in 2019-06-23 19:09
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MsgLogMapper msgLogMapper;

    @Override
    public List<User> getAll() {
        return userMapper.selectAll();
    }

    @Override
    public User getOne(Integer id) {
        return userMapper.selectOne(id);
    }

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }

    @Override
    public User getByUsernameAndPassword(String username, String password) {
        return userMapper.selectByUsernameAndPassword(username, password);
    }

    @Override
    public ServerResponse login(String username, String password) {
        // 首先判断用户名或密码是否为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return ServerResponse.error(ResponseCode.USERNAME_OR_PASSWORD_EMPTY.getMsg());
        }
        // 查询数据库有没有改用户
        User user = userMapper.selectByUsernameAndPassword(username, password);

        // 数据库中没有
        if (user == null){
            return ServerResponse.error(ResponseCode.USERNAME_OR_PASSWORD_WRONG.getMsg());
        }

        // 数据库中有这个内容，保存并发送消息
        saveAndSendMsg(user);

        return ServerResponse.success();
    }

    /**
     * 保存并发送消息
     * @param user 保存的用户信息
     * */
    private void saveAndSendMsg(User user) {
        LoginLog loginLog = new LoginLog();

        // TODO util.RandomUtil
        String msgId = RandomUtil.UUID32();
        loginLog.setUserId(user.getId());
        loginLog.setMsgId("");
        loginLog.setType(Constant.LogType.LOGIN);
        Date date = new Date();
        loginLog.setCreateTime(date);
        loginLog.setUpdateTime(date);

        // 在rabbitmq消息队列中传输的操作
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(msgId);
        // TODO amqp.MessageHelper
        rabbitTemplate.convertAndSend(RabbitConfig.LOGIN_LOG_EXCHANGE_NAME, RabbitConfig.LOGIN_LOG_ROUTING_KEY_NAME,
                MessageHelper.objToMsg(loginLog), correlationData);

        // 将消息日志存储在数据库中
        MsgLog msgLog = new MsgLog();
        msgLog.setMsgId(msgId);
        // TODO util.JsonUtil
        msgLog.setMsg(JsonUtil.objToStr(loginLog));
        msgLog.setStatus(Constant.MsgLogStatus.SENDING);
        msgLog.setTryCount(0);
        msgLog.setCreateTime(date);
        msgLog.setUpdateTime(date);
        // TODO util.JodaTimeUtil
        msgLog.setNextTryTime(JodaTimeUtil.plusMinutes(date, 1));
        msgLogMapper.insert(msgLog);
    }
}
