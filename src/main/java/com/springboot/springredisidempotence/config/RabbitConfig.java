package com.springboot.springredisidempotence.config;

import com.springboot.springredisidempotence.common.Constant;
import com.springboot.springredisidempotence.domain.MsgLog;
import com.springboot.springredisidempotence.mapper.MsgLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author guowf
 * @mail guowf_buaa@163.com
 * @description:
 * @data created in 2019-06-23 09:51
 */
@Configuration
@Slf4j
public class RabbitConfig {
    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private MsgLogMapper msgLogMapper;

    public static final String LOGIN_LOG_QUEUE_NAME = "login.log.queue";
    public static final String LOGIN_LOG_EXCHANGE_NAME = "login.log.exchange";
    public static final String LOGIN_LOG_ROUTING_KEY_NAME = "login.log.routing.key";

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());

        // 消息是否成功发送到exchange
        rabbitTemplate.setConfirmCallback(((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息成功发送到Exchange");

                String msgId = correlationData.getId();
                MsgLog msgLog = new MsgLog();
                msgLog.setMsgId(msgId);
                msgLog.setStatus(Constant.MsgLogStatus.SUCCESS);
                msgLogMapper.updateStatus(msgLog);
            }
            else {
                log.info("消息发送到Exchange失败： cause：{}",correlationData, cause);
            }
        }));

        // 消息是否从Exchange路由到Queue
        // 注意：这是一个失败回调，只有消息从Exchange路由到Queue失败才会回调这个方法
        rabbitTemplate.setReturnCallback(((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("消息从Exchange路由到Queue失败：Exchange：{}, route: {}, " +
                    "replyCode: {}, replyText: {}, message: {}", exchange, routingKey, replyCode, replyText, message);
        }));
        return rabbitTemplate;
    }

    /**
     * 消息转换
     */
    @Bean
    public Jackson2JsonMessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    /**用户登录队列*/
    @Bean
    public Queue loginUserQueue(){
        return new Queue(LOGIN_LOG_QUEUE_NAME, true);
    }

    /**直接转换通道*/
    @Bean
    public DirectExchange logUserExchange(){
        return new DirectExchange(LOGIN_LOG_EXCHANGE_NAME, true, false);
    }

    /**数据绑定*/
    @Bean
    public Binding logUserBinding(){
        return BindingBuilder.bind(loginUserQueue()).to(logUserExchange()).with(LOGIN_LOG_ROUTING_KEY_NAME);
    }

}
