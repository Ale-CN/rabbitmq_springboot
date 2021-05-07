package com.ale.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


/**
 * 消息发送到 queue 失败时触发
 * <p>
 * 如果消息未能投递到目标 queue 里将触发回调 returnCallback ，
 * 一旦向 queue 投递消息未成功，这里一般会记录下当前消息的详细投递数据，
 * 方便后续做【重发】或者【补偿】等操作。
 */
@Slf4j
@Component
public class CustomerReturnCallback implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息发送到【queue】失败！！ -returnedMessage ===> replyCode={} ,replyText={} ,exchange={} ,routingKey={}", replyCode, replyText, exchange, routingKey);
    }
}
