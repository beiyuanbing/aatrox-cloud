package com.aatrox.web.base;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * @author aatrox
 * @desc
 * @date 2019/9/20
 */
@EnableDiscoveryClient
/**因为要配置一个redis链接，所以将系统自带的配置排除，分别是
 *RedisAutoConfiguration.class 和 RedisRepositoriesAutoConfiguration.class
 * 防止redis自动配置**/
@SpringBootApplication(exclude = {RedisAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class,
        RabbitAutoConfiguration.class,
        SolrAutoConfiguration.class,
        ActiveMQAutoConfiguration.class,
        JmxAutoConfiguration.class
})
public class BaseApplication {
}
