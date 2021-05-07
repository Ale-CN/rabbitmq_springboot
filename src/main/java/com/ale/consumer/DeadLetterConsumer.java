package com.ale.consumer;

import com.ale.utils.RabbitConstant;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 死信队列
 */
@Component
public class DeadLetterConsumer {
    /**
     * 工单信息私信队列消费者
     *
     * @param message
     * @param channel
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = RabbitConstant.DEAD_LETTER_QUEUE_ORDER, durable = "true"),
                    exchange = @Exchange(name = RabbitConstant.DEAD_LETTER_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = {RabbitConstant.DEAD_LETTER_ROUTING_KEY_ORDER}
            )
    })
    public void deadOrderConsumer(Message message, Channel channel) throws IOException {
        System.out.println("收到 order 死信消息：" + new String(message.getBody()));
        // 手动 ack
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 工单信息私信队列消费者
     *
     * @param message
     * @param channel
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = RabbitConstant.DEAD_LETTER_QUEUE_USER, durable = "true"),
                    exchange = @Exchange(name = RabbitConstant.DEAD_LETTER_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = {RabbitConstant.DEAD_LETTER_ROUTING_KEY_USER}
            )
    })
    public void deadUserConsumer(Message message, Channel channel) throws IOException {
        System.out.println("收到 user 死信消息：" + new String(message.getBody()));
        // 手动 ack
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
