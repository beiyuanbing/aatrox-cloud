package com.aatrox.component.mq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * 必须加，使配置生效
 *
 * @author yasuo
 */
@ConditionalOnProperty(value = {"basic.mq.on"}, havingValue = "true", matchIfMissing = true)
public class ActiveMQConfig {
    @Autowired
    private Environment environment;
    public static final String BASIC_ACTIVEMQ_URL = "basic.activemq.url";
    public static final String BASIC_ACTIVEMQ_SESSION_SIZE = "basic.activemq.sessionSize";

    @Bean({"amqConnectionFactory"})
    public ActiveMQConnectionFactory amqConnectionFactory() {
        ActiveMQConnectionFactory amqConnectionFactory = new ActiveMQConnectionFactory();
        amqConnectionFactory.setBrokerURL(environment.getProperty(BASIC_ACTIVEMQ_URL));
        amqConnectionFactory.setSendTimeout(10000);
        amqConnectionFactory.setConnectResponseTimeout(5000);
        amqConnectionFactory.setTrustAllPackages(true);
        return amqConnectionFactory;
    }

    @Bean({"connectionFactory"})
    public CachingConnectionFactory connectionFactory(ActiveMQConnectionFactory amqConnectionFactory) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(amqConnectionFactory);
        connectionFactory.setSessionCacheSize((Integer) environment.getProperty(BASIC_ACTIVEMQ_SESSION_SIZE, Integer.class));
        return connectionFactory;
    }

    @Bean({"jmsTemplate"})
    public JmsTemplate jmsTemplate(CachingConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        return jmsTemplate;
    }
}
