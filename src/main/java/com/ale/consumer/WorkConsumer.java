package com.ale.consumer;

import com.ale.utils.RabbitConstant;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component

public class WorkConsumer {
    // @RabbitListener 注解进行 queue 定义、exchange 定义、绑定 exchange + queue
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = RabbitConstant.WORK_QUEUE, durable = "true"),
            exchange = @Exchange(name = RabbitConstant.WORK_EXCHANGE)
       // 这里需要注意，加上containerFactory就是能者多劳模式，不加就是轮询模式
    ), containerFactory = "simpleRabbitListenerContainerFactory")
    public void receive(String message) {
        System.out.println("workConsumer-1 = " + message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = RabbitConstant.WORK_QUEUE, durable = "true"),
            exchange = @Exchange(name = RabbitConstant.WORK_EXCHANGE)
            // 这里需要注意，加上containerFactory就是能者多劳模式，不加就是轮询模式
    ), containerFactory = "simpleRabbitListenerContainerFactory")
    public void receive2(String message) {
        System.out.println("workConsumer-2 = " + message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = RabbitConstant.WORK_QUEUE, durable = "true"),
            exchange = @Exchange(name = RabbitConstant.WORK_EXCHANGE)
            // 这里需要注意，加上containerFactory就是能者多劳模式，不加就是轮询模式
    ), containerFactory = "simpleRabbitListenerContainerFactory")
    public void receive3(String message) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("workConsumer-3 = " + message);
    }
}
