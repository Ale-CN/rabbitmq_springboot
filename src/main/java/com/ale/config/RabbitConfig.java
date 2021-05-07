package com.ale.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
//@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitConfig {

//    @Resource
//    RabbitProperties rabbitProperties;

//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory factory = new CachingConnectionFactory();
//        factory.setHost(rabbitProperties.getHost());
//        factory.setPort(rabbitProperties.getPort());
//        factory.setUsername(rabbitProperties.getUsername());
//        factory.setPassword(rabbitProperties.getPassword());
//        factory.setVirtualHost(rabbitProperties.getVirtualHost());
//        factory.setPublisherConfirmType();
//        return factory;
//    }

    /**
     * 实现多线程处理队列消息
     *
     * @RabbitListener默认是单线程监听队列 当线程消费消息容易引起消息处理缓慢，消息堆积，不能最大化利用硬件资源
     * 可以通过配置mq工厂参数，增加并发量处理数据即可实现多线程处理监听队列，实现多线程处理消息
     */
    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(CachingConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // 确认方式: NONE-不确认，MANUAL-手动确认, AUTO-自动确认
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        // 消息预期数量
        factory.setPrefetchCount(1);
        // 开启多线程处理队列消息
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(5);
        //设置批量
//        factory.setBatchListener(true);
//        factory.setConsumerBatchEnabled(true);//设置BatchMessageListener生效
//        factory.setBatchSize(10);//设置监听器一次批量处理的消息数量

        return factory;
    }

    @Bean
    public AmqpAdmin amqpAdmin(CachingConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) //必须是 prototype 类型
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, CustomerConfirmCallback customerConfirmCallback, CustomerReturnCallback customerReturnCallback) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setUsePublisherConnection(true);

        // 消息只要被rabbit broker接收到就会执行confirmCallback
        // 被broker执行只能保证消息到达服务器，并不能保证一定被投递到目标queue里
        rabbitTemplate.setConfirmCallback(customerConfirmCallback);

        // confirm 模式只能保证消息达到 broker 不能保证消息准确投递到目标 queue 中
        // 有些业务场景下，需要保证消息一定投递到目标 queue 中，此时需要用到 return 退回模式
        // 如果未能达到目前 queue 中将调用 returnCallback,可以记录下详细投递数据，定期巡检或者纠错
        rabbitTemplate.setReturnCallback(customerReturnCallback);
        rabbitTemplate.setMandatory(true);

        return rabbitTemplate;
    }

    /**
     * 自定义序列化转换器
     * 1.rabbitMq 默认使用的是 jdk 自带的序列化工具，序列化后队列中的字符串不是 json 格式
     * 2.使用自定义序列化转换器，将对象转换为 json 格式存放到队列中
     *
     * @return
     */
//    @Bean
//    public MessageConverter messageConverter() {
//        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//        return jackson2JsonMessageConverter;
//    }
}
