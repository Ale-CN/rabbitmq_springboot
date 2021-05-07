package com.ale.consumer;

import com.ale.utils.RabbitConstant;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LogTopicConsumer {


//    @RabbitListener(bindings = {
//            @QueueBinding(
//                    value = @Queue(name = RabbitConstant.LOG_TOPIC_QUEUE_ALL),
//                    exchange = @Exchange(name = RabbitConstant.LOG_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
//                    key = {RabbitConstant.LOG_ROUTING_KEY_ALL}// log.#
//            )
//    })
//    public void receiveAllLog(LogInfo msg) {
//        System.out.println("consumer-log -[all]: " + msg);
//    }

//    @RabbitListener(bindings = {
//            @QueueBinding(
//                    value = @Queue(name = RabbitConstant.LOG_TOPIC_QUEUE_INFO_WARN),
//                    exchange = @Exchange(name = RabbitConstant.LOG_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
//                    key = {RabbitConstant.LOG_ROUTING_KEY_INFO, RabbitConstant.LOG_ROUTING_KEY_WARN}// log.info,log.warn
//            )
//    })
//    public void receiveInfoAndWarnLog(LogInfo msg) {
//        System.out.println("consumer-log -[info and warn]: " + msg);
//    }

//    @RabbitListener(bindings = {
//            @QueueBinding(
//                    value = @Queue(name = RabbitConstant.LOG_TOPIC_QUEUE_ERROR),
//                    exchange = @Exchange(name = RabbitConstant.LOG_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
//                    key = {RabbitConstant.LOG_ROUTING_KEY_ERROR}// log.*.error
//            )
//    })
//    public void receiveErrorLog(LogInfo msg) {
//        System.out.println("consumer-log.receiveErrorLog -[error]: " + msg);
//    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = RabbitConstant.LOG_TOPIC_QUEUE_ERROR),
                    exchange = @Exchange(name = RabbitConstant.LOG_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = {RabbitConstant.LOG_ROUTING_KEY_ERROR}// log.*.error
            )
    })
    public void receiveErrorLog2(Message message, Channel channel) throws IOException {
        System.out.println("consumer-log.receiveErrorLog2 -[error]: " + new String(message.getBody()));
        // 参数1：唯一标识，参数2：是否多条确认（确认所有 <= 参数1 的消息）
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
