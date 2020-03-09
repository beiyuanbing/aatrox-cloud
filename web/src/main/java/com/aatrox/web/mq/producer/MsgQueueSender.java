package com.aatrox.web.mq.producer;

/**
 * 进行生产消息
 */

/*
@Component
public class MsgQueueSender {
    @Resource
    private JmsTemplate jmsTemplate;

    public void send(String queueTopicConstants, JSONObject jsonObj) {
        jmsTemplate.convertAndSend(queueTopicConstants, jsonObj.toJSONString());
    }


    //监听队列返回
    @JmsListener(destination = QueueTopicConstants.MYTEST_OUT)
    public void receiveQueue(String text) {
        System.out.println("producer收到回复的报文为:" + text);
    }

}
*/

