package com.aatrox.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-19
 */
@RestController
@RequestMapping("/rabbitmq")
public class RabbitController {

   /* @Autowired
    private RabbitProducer producer;


    @RequestMapping("/send")
    public String send() {
        for (int i = 0; i < 10; i++) {
            producer.stringSend();
        }
        return "success";
    }*/
}
