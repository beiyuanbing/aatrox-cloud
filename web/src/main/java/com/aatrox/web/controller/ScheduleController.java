package com.aatrox.web.controller;

import com.aatrox.web.base.controller.BaseController;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author aatrox
 * @desc
 * @date 2019/11/21
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController extends BaseController {
    @Resource
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    @RequestMapping("/start")
    public String start() {
        threadPoolTaskScheduler.scheduleAtFixedRate(()->{
            System.out.println("nihao");
        },15000);
        return returnSuccessInfo();
    }
}
