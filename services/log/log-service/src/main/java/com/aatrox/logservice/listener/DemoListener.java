package com.aatrox.logservice.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author aatrox
 * @desc 自定义SpringApplicationListener
 * @date 2019/9/27
 */
public class DemoListener implements SpringApplicationRunListener {
    private final SpringApplication application;

    private final String[] args;

    public DemoListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }

    @Override
    public void starting() {
        System.out.println(" DemoListener starting");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println(" DemoListener environmentPrepared");

    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println(" DemoListener contextPrepared");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println(" DemoListener contextLoaded");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println(" DemoListener started");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println(" DemoListener running");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println(" DemoListener failed");
    }
}
