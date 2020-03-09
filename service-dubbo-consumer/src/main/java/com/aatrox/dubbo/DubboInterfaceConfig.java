package com.aatrox.dubbo;

import com.aatrox.dubbo.config.DubboBaseConfig;
import com.aatrox.service.OrderDubboService;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

/**
 * 配置相关的rpc接口的配置类
 */
@Configuration
@Import({DubboBaseConfig.class})
public class DubboInterfaceConfig {

    @Autowired
    private Environment environment;



    /* <dubbo:service interface="com.aatrox.service.OrderDubboService"
     ref="orderDubboService" timeout="1000" version="1.0.0">
     这个可有可无
     <dubbo:method name="getUserAddressList" timeout="1000"></dubbo:method>
     </dubbo:service>*/

    /**
     * 调用order的服务
     *
     * @return
     */
    @Bean(name = "orderDubboService")
    public ReferenceBean orderDubboService() {
        ReferenceBean bean = new ReferenceBean();
        bean.setLazy(true);
        bean.setVersion("1.0.0");
        bean.setId("orderDubboService");
        bean.setInterface(OrderDubboService.class);
        bean.setProtocol("dubbo");
        return bean;
    }

}
