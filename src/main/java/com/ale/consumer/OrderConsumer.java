package com.ale.consumer;

import com.ale.utils.RabbitConstant;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 订单消费者
 */
@Component
public class OrderConsumer {
    /**
     * 模拟队列超时，进入死信队列
     *
     * @param message
     * @param channel
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = RabbitConstant.ORDER_QUEUE, arguments = {
                            // 指定死信交换机
                            @Argument(name = "x-dead-letter-exchange", value = RabbitConstant.DEAD_LETTER_EXCHANGE),
                            // 指定死信 routingKey
                            @Argument(name = "x-dead-letter-routing-key", value = RabbitConstant.DEAD_LETTER_ROUTING_KEY_ORDER),
                            // 队列消息超时时间，单位：微秒  1s = 1000000μs
                            @Argument(name = "x-message-ttl", value = "1000", type = "java.lang.Integer")
                            // ,@Argument(name = "x-max-length",value = "5",type = "java.lang.Integer")//队列最大长度
                            //可以指定多种属性
                    }),
                    exchange = @Exchange(name = RabbitConstant.ORDER_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = {RabbitConstant.ORDER_ROUTING_KEY}// log.*.error
            )
    })
    public void orderConsumer(Message message, Channel channel) throws IOException {
        System.out.println("orderConsumer 收到消息：" + new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
