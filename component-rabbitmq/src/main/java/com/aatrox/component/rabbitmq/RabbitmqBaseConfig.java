package com.aatrox.component.rabbitmq;

import com.aatrox.component.rabbitmq.config.RabbitmqConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-19
 */
@ComponentScan({"com.aatrox.component.rabbitmq"})
@Import({RabbitmqConfig.class})
public class RabbitmqBaseConfig {
}
