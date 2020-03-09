package com.aatrox.component.mq;

import com.aatrox.component.mq.config.ActiveMQConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@ComponentScan({"com.aatrox.component.mq"})
@Import({ActiveMQConfig.class})
public class MQBaseConfig {
}
