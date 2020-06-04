package com.aatrox.component.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * @author aatrox
 * @desc /**
 * Broker:它提供一种传输服务,它的角色就是维护一条从生产者到消费者的路线，保证数据能按照指定的方式进行传输,
 * Exchange：消息交换机,它指定消息按什么规则,路由到哪个队列。
 * Queue:消息的载体,每个消息都会被投到一个或多个队列。
 * Binding:绑定，它的作用就是把exchange和queue按照路由规则绑定起来.
 * Routing Key:路由关键字,exchange根据这个关键字进行消息投递。
 * vhost:虚拟主机,一个broker里可以有多个vhost，用作不同用户的权限分离。
 * Producer:消息生产者,就是投递消息的程序.
 * Consumer:消息消费者,就是接受消息的程序.
 * Channel:消息通道,在客户端的每个连接里,可建立多个channel.
 * @date 2019-08-19
 */
@ConditionalOnProperty(value = {"basic.rabbitmq.on"}, havingValue = "true", matchIfMissing = true)
public class RabbitmqConfig {

    @Autowired
    private Environment environment;

    public static final String BASIC_RABBITMQ_HOST = "basic.rabbitmq.host";
    public static final String BASIC_RABBITMQ_PORT = "basic.rabbitmq.port";
    public static final String BASIC_RABBITMQ_USERNAME = "basic.rabbitmq.username";
    public static final String BASIC_RABBITMQ_PASSWORD = "basic.rabbitmq.password";

    @Bean(name = "rabbitmqConnectionFactory")
    ConnectionFactory rabbitmqConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(this.environment.getProperty(BASIC_RABBITMQ_HOST),
                Integer.valueOf(this.environment.getProperty(BASIC_RABBITMQ_PORT)));
        connectionFactory.setUsername(this.environment.getProperty(BASIC_RABBITMQ_USERNAME));
        connectionFactory.setPassword(this.environment.getProperty(BASIC_RABBITMQ_PASSWORD));
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate(ConnectionFactory rabbitmqConnectionFactory) {
        RabbitTemplate template = new RabbitTemplate(rabbitmqConnectionFactory);
        return template;
    }

    /**
     * 定义队列名
     */
    private final static String QUEUE_NAME = "string";
    /***
     * 交换机名
     */
    private final static String EXCHANGE_NAME="exchange";
    /**
     * 定义route key
     */
    private final static String ROUTE_KEY="routeKey";

    /**
     * 定义string队列
     *
     * @return
     */
    @Bean
    public Queue queue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    /**
     * 定义交换机
     */
    @Bean
    public Exchange exchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).build();
    }

    /**
     * 交换机和队列的绑定
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding queueToExchange(Queue queue,Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTE_KEY).noargs();
    }

    /**
     * rabbitmq 的消息发送demo
     */
 /*   @Resource
    private AmqpTemplate amqpTemplate;
    public void sendMessage(RabbitTemplate rabbitTemplate){
        Object msgObject=new Object();
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,ROUTE_KEY,msgObject);
        amqpTemplate.convertAndSend(EXCHANGE_NAME,ROUTE_KEY,msgObject);
    }*/
}
