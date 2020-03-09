package com.aatrox.quartzserver.service;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service("msgService")
public class MsgService {

    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;

    @PostConstruct
    public void init() {
        System.out.println(123);
    }

    public void send(String queueTopicConstants, String msg) {
        jmsTemplate.convertAndSend(queueTopicConstants, msg);
    }

}
