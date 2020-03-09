package com.aatrox.quartzserver.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
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
