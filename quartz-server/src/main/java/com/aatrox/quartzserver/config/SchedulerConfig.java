package com.aatrox.quartzserver.config;

import com.aatrox.quartzserver.constants.JobConstants;
import com.aatrox.quartzserver.job.DefineJob;
import com.aatrox.quartzserver.model.AppQuartz;
import com.aatrox.quartzserver.service.AppQuartzService;
import org.quartz.*;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@Configuration
public class SchedulerConfig {

    @Resource
    private AppQuartzService appQuartzService;

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        PrivateSpringBeanJobFactory jobFactory = new PrivateSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public Scheduler scheduler(DataSource dataSource, JobFactory jobFactory) throws Exception {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        // this allows to update triggers in DB when updating settings in config file
        factory.setOverwriteExistingJobs(true);
        factory.setDataSource(dataSource);
        // use specify jobFactory to create jobDetail
        factory.setJobFactory(jobFactory);

        factory.setQuartzProperties(quartzProperties());
        factory.afterPropertiesSet();

        Scheduler scheduler = factory.getScheduler();
        scheduler.setJobFactory(jobFactory);
        //初始化所有的任务
        this.initJob(scheduler);
        scheduler.start();
        return scheduler;
    }

    /***
     * 初始化任务
     * @param scheduler
     */
    public void initJob(Scheduler scheduler) {
        // register all jobs
        List<AppQuartz> jobList = appQuartzService.selectAppQuartzs(null);
        if (jobList == null || jobList.size() == 0) {
            return;
        }
        Set<AppQuartz> jobs = new HashSet<>(jobList);
        for (AppQuartz job : jobs) {
            try {
                TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
                CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                if (null == trigger) {
                    JobDetail jobDetail = JobBuilder.newJob(DefineJob.class)
                            .withIdentity(job.getJobName(), job.getJobGroup())
                            .withDescription(job.getDescription())
                            //.withDescription(job.getDesc())
                            .build();
                    jobDetail.getJobDataMap().put(JobConstants.INVOKE_PARAM, job.getInvokeParam());
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
                            .getCronExpression());
                    trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())
                            .withDescription(job.getTriggerDesc())
                            .withSchedule(scheduleBuilder)
                            .build();
                    //设置参数
                    trigger.getJobDataMap().put(JobConstants.INVOKE_PARAM, job.getInvokeParam());
                    scheduler.scheduleJob(jobDetail, trigger);
                } else {
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
                            .getCronExpression());
                    trigger = trigger.getTriggerBuilder()
                            .withIdentity(triggerKey)
                            .withDescription(job.getTriggerDesc())
                            .withSchedule(scheduleBuilder).build();
                    //设置参数
                    trigger.getJobDataMap().put(JobConstants.INVOKE_PARAM, job.getInvokeParam());
                    scheduler.rescheduleJob(triggerKey, trigger);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
}
