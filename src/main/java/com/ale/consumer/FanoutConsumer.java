package com.ale.consumer;

import com.ale.utils.RabbitConstant;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutConsumer {

    // 注解方式：声明、绑定队列、指定 routingKey (fanout 方式 routingKey 不需要设置)
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,// 未指定名称时，系统会帮我们【创建临时队列】
                    exchange = @Exchange(value = RabbitConstant.FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT)// 绑定fanout交换机
            )
    })
    public void receive1(String fanoutMsg) {
        System.out.println("fanoutConsumer-1: " + fanoutMsg);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,// 未指定名称时，系统会帮我们【创建临时队列】
                    exchange = @Exchange(value = RabbitConstant.FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT)// 绑定fanout交换机
            )
    })
    public void receive2(String fanoutMsg) {
        System.out.println("fanoutConsumer-2: " + fanoutMsg);
    }

}
