package com.springboot.springredisidempotence.amqp;

import com.rabbitmq.client.Channel;
import com.springboot.springredisidempotence.config.RabbitConfig;
import com.springboot.springredisidempotence.domain.LoginLog;
import com.springboot.springredisidempotence.service.LoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author guowf
 * @mail guowf_buaa@163.com
 * @description:
 * @data created in 2019-06-23 19:28
 * 登录用户消费类
 */
@Component
@Slf4j
public class LogUserConsumer {
    @Autowired
    private LoginLogService loginLogService;

    /**监听哪个消息队列，有就消费*/
    @RabbitListener(queues = RabbitConfig.LOGIN_LOG_QUEUE_NAME)
    public void logUserConsumer(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG)long tag) throws IOException{
        try {
            log.info("收到消息： {}", message.toString());

            LoginLog loginLog = MessageHelper.msgToObj(message, LoginLog.class);
            LoginLog lLog = loginLogService.selectByMsgId(loginLog.getMsgId());

            // 消费幂等性
            if (null == lLog) {
                loginLogService.insert(loginLog);
            }
        } catch (Exception e) {
            log.error("logUserConsumer error", e);
            // 有问题，要回溯
            channel.basicNack(tag, false, true);
        } finally {
            channel.basicAck(tag, false);
        }
    }
}
