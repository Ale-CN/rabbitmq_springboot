package com.ale.consumer;

import org.springframework.stereotype.Component;

/**
 * 订单消费者
 */
@Component
public class UserConsumer {
//    /**
//     * 模拟队列超长，进入死信队列
//     *
//     * @param message
//     * @param channel
//     */
//    @RabbitListener(bindings = {
//            @QueueBinding(
//                    value = @Queue(name = RabbitConstant.USER_QUEUE, arguments = {
//                            @Argument(name = "x-dead-letter-exchange", value = RabbitConstant.DEAD_LETTER_EXCHANGE),
//                            @Argument(name = "x-dead-letter-routing-key", value = RabbitConstant.DEAD_LETTER_ROUTING_KEY_USER),
//                            // 队列消息超时时间，单位：微秒  1s = 1000000μs
////                            @Argument(name = "x-message-ttl", value = "1000", type = "java.lang.Integer"),
//                            //队列最大长度
//                            @Argument(name = "x-max-length", value = "1", type = "java.lang.Integer")
//                            //可以指定多种属性
//                    }),
//                    exchange = @Exchange(name = RabbitConstant.USER_EXCHANGE, type = ExchangeTypes.TOPIC),
//                    key = {RabbitConstant.USER_ROUTING_KEY}// user.#
//            )
//    })
//    public void userConsumer(Message message, Channel channel) throws IOException {
//        System.out.println("userConsumer 收到消息：" + new String(message.getBody()) + " Expiration:" + message.getMessageProperties().getExpiration());
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//    }
//
//
//    /**
//     * 模拟拒收消息，进入死信队列（basicNack / basicReject，且 requeue=false ）
//     *
//     * @param message
//     * @param channel
//     */
//    @RabbitListener(bindings = {
//            @QueueBinding(
//                    value = @Queue(name = RabbitConstant.USER_QUEUE, arguments = {
//                            @Argument(name = "x-dead-letter-exchange", value = RabbitConstant.DEAD_LETTER_EXCHANGE),
//                            @Argument(name = "x-dead-letter-routing-key", value = RabbitConstant.DEAD_LETTER_ROUTING_KEY_USER),
//                            // 队列消息超时时间，单位：微秒  1s = 1000000μs
////                            @Argument(name = "x-message-ttl", value = "1000", type = "java.lang.Integer"),
//                            //队列最大长度
//                            @Argument(name = "x-max-length", value = "1", type = "java.lang.Integer")
//                            //可以指定多种属性
//                    }),
//                    exchange = @Exchange(name = RabbitConstant.USER_EXCHANGE, type = ExchangeTypes.TOPIC),
//                    key = {RabbitConstant.USER_ROUTING_KEY}// log.*.error
//            )
//    })
//    public void userNack(Message message, Channel channel) throws IOException {
//        System.out.println("userNack 拒收消息：" + new String(message.getBody()) + " Expiration:" + message.getMessageProperties().getExpiration());
//        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
//    }
}
