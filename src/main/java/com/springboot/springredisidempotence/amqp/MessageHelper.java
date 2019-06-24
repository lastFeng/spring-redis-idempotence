package com.springboot.springredisidempotence.amqp;

import com.springboot.springredisidempotence.util.JsonUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;

/**
 * @author guowf
 * @mail guowf_buaa@163.com
 * @description:
 * @data created in 2019-06-23 19:35
 * 将消息转化为需要的类
 */
public class MessageHelper {
    public static Message objToMsg(Object obj){
        if (null == obj) {
            return null;
        }

        Message message = MessageBuilder.withBody(JsonUtil.objToStr(obj).getBytes()).build();
        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        message.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
        return message;
    }

    public static <T> T msgToObj(Message message, Class<T> clazz){
        if (null == message || null == clazz){
            return null;
        }

        String str = new String(message.getBody());
        T obj = JsonUtil.strToObj(str, clazz);
        return obj;
    }
}
