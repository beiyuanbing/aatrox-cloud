package com.aatrox.component.mq.processor;

import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.util.StringUtils;

import javax.jms.JMSException;
import javax.jms.Message;

public class ScheduleMessagePostProcessor implements MessagePostProcessor {
    private long delay = 0L;
    private String corn = null;

    public ScheduleMessagePostProcessor(long delay) {
        this.delay = delay;
    }

    public ScheduleMessagePostProcessor(String cron) {
        this.corn = cron;
    }

    @Override
    public Message postProcessMessage(Message message) throws JMSException {
        if (this.delay > 0L) {
            message.setLongProperty("AMQ_SCHEDULED_DELAY", this.delay);
        }

        if (!StringUtils.isEmpty(this.corn)) {
            message.setStringProperty("AMQ_SCHEDULED_CRON", this.corn);
        }

        return message;
    }
}
