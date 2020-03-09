package com.aatrox.component.activemq.listener;

import com.alibaba.fastjson.JSONObject;
import org.apache.activemq.command.ActiveMQObjectMessage;

import javax.jms.Message;
import javax.jms.TextMessage;

public abstract class ActiveMqAbstractListener {

    protected String decodeMessage(Message message) {
        String msg = "";

        try {
            if (message instanceof TextMessage) {
                msg = ((TextMessage) message).getText().trim();
            } else if (message instanceof ActiveMQObjectMessage) {
                msg = JSONObject.toJSONString(((ActiveMQObjectMessage) message).getObject());
            }
        } catch (Exception var4) {
        }

        return msg;
    }

    protected abstract void doTask(Message message);
}
