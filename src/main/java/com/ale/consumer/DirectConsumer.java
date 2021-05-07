package com.ale.consumer;

import com.ale.utils.RabbitConstant;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectConsumer {
    // 注解方式：声明、绑定队列、指定 routingKey
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = RabbitConstant.DIRECT_QUEUE_ORANGE_GREEN),
                    // 设置交换机名称、交换机类型 direct(默认即为：direct)
                    exchange = @Exchange(value = RabbitConstant.DIRECT_EXCHANGE),
                    // 设置 routingKey
                    key = {RabbitConstant.DIRECT_ROUTING_KEY_GREEN, RabbitConstant.DIRECT_ROUTING_KEY_ORANGE}
            )
    })
    public void receiveOrangeAndGreen(String infoMsg) {
        System.out.println("consumer-receiveOrangeAndGreen: " + infoMsg);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = RabbitConstant.DIRECT_QUEUE_BLACK),
                    // 设置交换机名称、交换机类型 direct(默认即为：direct)
                    exchange = @Exchange(value = RabbitConstant.DIRECT_EXCHANGE),
                    // 设置 routingKey
                    key = {RabbitConstant.DIRECT_ROUTING_KEY_BLACK}
            )
    })
    public void receiveBlack(String msg) {
        System.out.println("consumer-receiveBlack: " + msg);
    }
}
