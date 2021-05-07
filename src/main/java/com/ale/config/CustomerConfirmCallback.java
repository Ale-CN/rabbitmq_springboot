package com.ale.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


/**
 * 消息发送到 broker 成功时触发
 * <p>
 * 消息只要被 rabbitmq broker 接收到就会触发 confirmCallback 回调
 */
@Slf4j
@Component
public class CustomerConfirmCallback implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            System.out.println(String.format("消息已成功发送到【broker】，correlationData=%s ,ack=%s, cause=%s", correlationData.getId(), ack, cause));
            log.info("消息已成功发送到【broker】，correlationData={} ,ack={}, cause={}", correlationData.getId(), ack, cause);
        } else {
            System.out.println(String.format("消息发送到【broker】失败！，correlationData=%s ,ack=%s, cause=%s", correlationData.getId(), ack, cause));
            log.info("消息发送到【broker】失败！，correlationData={} ,ack={}, cause={}", correlationData.getId(), ack, cause);
        }
    }
}
