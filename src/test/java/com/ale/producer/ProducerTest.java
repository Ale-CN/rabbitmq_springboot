package com.ale.producer;

import com.ale.entity.Order;
import com.ale.entity.User;
import com.ale.utils.JsonUtils;
import com.ale.utils.RabbitConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerTest {
    @Resource
    RabbitTemplate rabbitTemplate;

    @Test
    public void work() {
        for (int i = 0; i < 9; i++) {
            // work 方式只需要指定发送的 routerKey 就行了
            // convertAndSend 未指定 exchange 的情况下会将消息发送给默认交换机(AMQP default)
            // 而默认的交换机有一个特点，只要你的 routerKey 在这个交换机中有同名的队列，他就会自动路由上
            // 即该 exchange 会将消息发送到 routingKey 值相同的 queen 中
            rabbitTemplate.convertAndSend(RabbitConstant.WORK_QUEUE, "i = " + i);
        }
    }

    @Test
    public void fanout() {
        for (int i = 0; i < 3; i++) {
            // fanout 方式 routingKey 值无效，所以不需要设置
            rabbitTemplate.convertAndSend(RabbitConstant.FANOUT_EXCHANGE, "", " i = " + i);
        }
    }

    @Test
    public void direct() {
        // direct 方式通过指定 exchange + routingKey 的方式
        // 让 exchange 将消息根据与 routingKey 的绑定关系，转发到对应的队列中
        rabbitTemplate.convertAndSend(RabbitConstant.DIRECT_EXCHANGE, RabbitConstant.DIRECT_ROUTING_KEY_ORANGE, "orange!!!");
        rabbitTemplate.convertAndSend(RabbitConstant.DIRECT_EXCHANGE, RabbitConstant.DIRECT_ROUTING_KEY_ORANGE, "orange!!!");
        rabbitTemplate.convertAndSend(RabbitConstant.DIRECT_EXCHANGE, RabbitConstant.DIRECT_ROUTING_KEY_GREEN, "green!!!");
        rabbitTemplate.convertAndSend(RabbitConstant.DIRECT_EXCHANGE, RabbitConstant.DIRECT_ROUTING_KEY_BLACK, "black!!!");
    }

    @Test
    public void topic() {
        // topic 方式通过指定 exchange + routingKey 的方式
        // 让 exchange 将消息根据与 routingKey 的绑定关系，转发到对应的队列中
        // . 号用来隔开多个路由键。 eg：log.info.user.auth
        // * 号通配符可以指定【只匹配一个】个路由值
        // # 号通配符可以表示【0 or 多】个路由值
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);

        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());

        Message msg = new Message("NumberFormatException".getBytes(), messageProperties);
        rabbitTemplate.convertAndSend(RabbitConstant.LOG_TOPIC_EXCHANGE, "log.user.error", msg, correlationData);
        msg = new Message("Exception".getBytes(), messageProperties);
        rabbitTemplate.convertAndSend(RabbitConstant.LOG_TOPIC_EXCHANGE, "log.user.error", msg, correlationData);
//        rabbitTemplate.convertAndSend(RabbitConstant.LOG_TOPIC_EXCHANGE, "log.user.error", new LogInfo("error", "log.user.error!!", "Exception"));
//        rabbitTemplate.convertAndSend(RabbitConstant.LOG_TOPIC_EXCHANGE, "log.user.auth.info", new LogInfo("all", "log.user.auth.info!!", "Exception"));
//        rabbitTemplate.convertAndSend(RabbitConstant.LOG_TOPIC_EXCHANGE, "log.user.auth.error", new LogInfo("error", "log.error!!", "Exception"));
//        rabbitTemplate.convertAndSend(RabbitConstant.LOG_TOPIC_EXCHANGE, RabbitConstant.LOG_ROUTING_KEY_INFO, new LogInfo("info", "info!!", ""));
//        rabbitTemplate.convertAndSend(RabbitConstant.LOG_TOPIC_EXCHANGE, RabbitConstant.LOG_ROUTING_KEY_WARN, new LogInfo("warn", "warn!!", null));
    }


    @Test
    public void deadLetter() throws JsonProcessingException {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        Order order = new Order();
        order.setOrderId(1001L);
        order.setOrderCode("1001");
        order.setTitle("test-1001");
        Message message = new Message(JsonUtils.parse2Json(order).getBytes(), new MessageProperties());
     //   rabbitTemplate.convertAndSend(RabbitConstant.ORDER_EXCHANGE, RabbitConstant.ORDER_ROUTING_KEY, message, correlationData);

        correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        User user = new User(1L, "le", "le");
        message = new Message(JsonUtils.parse2Json(user).getBytes(), new MessageProperties());
       // rabbitTemplate.convertAndSend(RabbitConstant.USER_EXCHANGE, RabbitConstant.USER_ROUTING_KEY, message, correlationData);

        correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        user = new User(2L, "zs", "zs");
        MessageProperties properties = new MessageProperties();
        message = new Message(JsonUtils.parse2Json(user).getBytes(), properties);
        rabbitTemplate.convertAndSend(RabbitConstant.USER_EXCHANGE, RabbitConstant.USER_ROUTING_KEY, message, correlationData);

        // 消息过期，且消息不在队列首位时，该消息并不会被放入死信队列
        // 1.因为会检测首位消息是否过期，
        // 2.可以利用一个消费者轮询去消费（只消费不进行ack，让所有的消息都能到达队首，实现对队列中所有过期消息的检索）
        correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        user = new User(33L, "xxx", "xxx");
        properties = new MessageProperties();
        properties.setExpiration("100");
        message = new Message(JsonUtils.parse2Json(user).getBytes(), properties);
        rabbitTemplate.convertAndSend(RabbitConstant.USER_EXCHANGE, RabbitConstant.USER_ROUTING_KEY, message, correlationData);

    }

}