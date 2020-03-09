package com.aatrox.component.activemq;

import com.aatrox.component.activemq.config.ActiveMqConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@ComponentScan({"com.aatrox.component.activemq"})
@Import({ActiveMqConfig.class})
public class ActiveMqBaseConfig {
}
