package com.aatrox.web.rabbitmq.product;

import org.springframework.stereotype.Component;
/**
 * @author aatrox
 * @desc
 * @date 2019-08-19
 */
@Component
public class RabbitProducer {
  /*  @Autowired
    private RabbitTemplate rabbitTemplate;

    public void stringSend() {
        Date date = new Date();
        String dateString = new SimpleDateFormat("YYYY-mm-DD hh:MM:ss").format(date);
        System.out.println("[string] send msg:" + dateString);
        // 第一个参数为刚刚定义的队列名称
        this.rabbitTemplate.convertAndSend("string", dateString);
    }*/
}
