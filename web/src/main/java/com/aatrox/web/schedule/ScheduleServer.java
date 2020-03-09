/*
package com.aatrox.web.schedule;

import com.aatrox.common.utils.ListUtil;
import com.aatrox.common.utils.ReflectionUtils;
import com.aatrox.common.utils.StringUtils;
import com.aatrox.web.config.ApplicationContextRegister;
import com.alibaba.fastjson.JSONObject;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

@Component
public class ScheduleServer {


    @JmsListener(destination = "saasnewhouse.*")
    public void task(ActiveMQObjectMessage message) {
        try {
            String physicalName = message.getDestination().getPhysicalName();
            String beanName = physicalName.substring(physicalName.indexOf(".") + 1);
            String methodName = JSONObject.parseObject(JSONObject.toJSONString(message.getObject())).getString("taskParam");
            if (StringUtils.isNotEmpty(beanName)) {
                Object scheduleBean = ApplicationContextRegister.getApplicationContext().getBean(beanName);
                if (StringUtils.isEmpty(methodName)) {
                    List<Method> methods = ReflectionUtils.getDeclareMethods(scheduleBean);
                    methodName = ListUtil.isEmpty(methods) ? null : methods.get(0).getName();
                }
                if (StringUtils.isEmpty(methodName)) {
                    return;
                }
                Method method = ReflectionUtils.getDeclaredMethod(scheduleBean, methodName, null);
                method.invoke(scheduleBean, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
*/
