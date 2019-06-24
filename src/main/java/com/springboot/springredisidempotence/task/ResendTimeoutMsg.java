/*
 * Copyright 2001-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.springboot.springredisidempotence.task;

import com.springboot.springredisidempotence.amqp.MessageHelper;
import com.springboot.springredisidempotence.common.Constant;
import com.springboot.springredisidempotence.config.RabbitConfig;
import com.springboot.springredisidempotence.domain.LoginLog;
import com.springboot.springredisidempotence.domain.MsgLog;
import com.springboot.springredisidempotence.mapper.MsgLogMapper;
import com.springboot.springredisidempotence.util.JodaTimeUtil;
import com.springboot.springredisidempotence.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/24 13:41
 * 对于超时的消息进行重试，当重试次数达到预定值，就不在重新发送
 * 没过10s进行重试
 */
@Component
@Slf4j
public class ResendTimeoutMsg {
	private static final int MAX_TRY_COUNT = 3;

	@Autowired
	private MsgLogMapper msgLogMapper;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Scheduled(cron = "0/10 * * * * ?")
	public void resendTimeoutMsg() {
		log.info("开始执行消息重试定时任务");

		List<MsgLog> msgLogs = msgLogMapper.selectTimeoutMsg();

		msgLogs.forEach(msgLog -> {
			String msgId = msgLog.getMsgId();
			if (msgLog.getTryCount() > MAX_TRY_COUNT) {
				MsgLog mLog = new MsgLog();
				mLog.setMsgId(msgId);
				mLog.setStatus(Constant.MsgLogStatus.FAIL);
				mLog.setCreateTime(msgLog.getCreateTime());
				mLog.setUpdateTime(new Date());
				msgLogMapper.updateStatus(mLog);

				log.info("超过最大重试次数，消息投递失败， msgId: {}", msgId);
			}
			else {
				MsgLog mLog = new MsgLog();
				mLog.setMsgId(msgId);
				mLog.setNextTryTime(JodaTimeUtil.plusMinutes(msgLog.getNextTryTime(), 1));
				msgLogMapper.updateTryCount(mLog);

				// 重新投递
				CorrelationData correlationData = new CorrelationData();
				correlationData.setId(msgId);
				rabbitTemplate.convertAndSend(RabbitConfig.LOGIN_LOG_EXCHANGE_NAME,
					RabbitConfig.LOGIN_LOG_ROUTING_KEY_NAME,
					MessageHelper.objToMsg(JsonUtil.strToObj(msgLog.getMsg(), LoginLog.class)), correlationData);

				log.info("第" + (msgLog.getTryCount() + 1) + "次重新投递消息");
			}
		});
	}
}