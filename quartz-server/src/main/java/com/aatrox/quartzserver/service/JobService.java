package com.aatrox.quartzserver.service;

import com.aatrox.quartzserver.constants.DealStatusConstants;
import com.aatrox.quartzserver.constants.JobConstants;
import com.aatrox.quartzserver.job.DefineJob;
import com.aatrox.quartzserver.model.AppQuartz;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class JobService {
    @Autowired
    @Qualifier("scheduler")
    private Scheduler scheduler;

    @Autowired
    private AppQuartzService appQuartzService;

    /**
     * 新增的更新操作
     *
     * @param appQuartz
     * @return
     * @throws Exception
     */
    public String saveOrupdate(AppQuartz appQuartz) throws Exception {
        String result = null;
        /**新增操作**/
        if (appQuartz.getQuartzId() == null) {
            appQuartzService.insertAppQuartz(appQuartz);
            result = this.addJob(appQuartz);
        } else {
            result = this.modifyJob(appQuartz);
            if (DealStatusConstants.SUCCESS.equals(result)) {
                appQuartzService.updateAppQuartz(appQuartz);
            }
        }
        return result;
    }

    /**
     * 新建一个任务
     */
    public String addJob(AppQuartz appQuartz) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse(appQuartz.getStartTime());
        /**表达式格式不正确**/
        if (!CronExpression.isValidExpression(appQuartz.getCronExpression())) {
            return "Illegal cron expression";
        }
        JobDetail jobDetail = null;
        /**构建job信息**/
        jobDetail = JobBuilder.newJob(DefineJob.class).withIdentity(appQuartz.getJobName(), appQuartz.getJobGroup())
                .withDescription(appQuartz.getDescription()).build();

        /**表达式调度构建器(即任务执行的时间,不立即执行)**/
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(appQuartz.getCronExpression()).withMisfireHandlingInstructionDoNothing();

        /**按新的cronExpression表达式构建一个新的trigger**/
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(appQuartz.getJobName(), appQuartz.getJobGroup()).startAt(date)
                .withDescription(appQuartz.getTriggerDesc())
                .withSchedule(scheduleBuilder).build();

        /**传递参数**/
        if (appQuartz.getInvokeParam() != null && !"".equals(appQuartz.getInvokeParam())) {
            trigger.getJobDataMap().put(JobConstants.INVOKE_PARAM, appQuartz.getInvokeParam());
        }
        scheduler.scheduleJob(jobDetail, trigger);
        //pauseJob(appQuartz.getJobName(),appQuartz.getJobGroup());
        return DealStatusConstants.SUCCESS;
    }

    /**
     * 获取Job状态
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public String getJobState(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);
        return scheduler.getTriggerState(triggerKey).name();
    }

    /**
     * 暂停所有任务
     */
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 暂停任务
     ***/
    public String pauseJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return DealStatusConstants.FAIL;
        } else {
            scheduler.pauseJob(jobKey);
            return DealStatusConstants.SUCCESS;
        }

    }

    /**
     * 恢复所有任务
     ***/
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    /**
     * 恢复某个任务
     ***/
    public String resumeJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return DealStatusConstants.FAIL;
        } else {
            scheduler.resumeJob(jobKey);
            return DealStatusConstants.SUCCESS;
        }
    }

    /**
     * 删除某个任务
     ***/
    public String deleteJob(AppQuartz appQuartz) throws SchedulerException {
        JobKey jobKey = new JobKey(appQuartz.getJobName(), appQuartz.getJobGroup());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return "jobDetail is null";
        } else if (!scheduler.checkExists(jobKey)) {
            return "jobKey is not exists";
        } else {
            scheduler.deleteJob(jobKey);
            return DealStatusConstants.SUCCESS;
        }

    }

    /**
     * 修改任务
     ***/
    public String modifyJob(AppQuartz appQuartz) throws SchedulerException {
        if (!CronExpression.isValidExpression(appQuartz.getCronExpression())) {
            return "Illegal cron expression";
        }
        TriggerKey triggerKey = TriggerKey.triggerKey(appQuartz.getJobName(), appQuartz.getJobGroup());
        JobKey jobKey = new JobKey(appQuartz.getJobName(), appQuartz.getJobGroup());
        if (scheduler.checkExists(jobKey) && scheduler.checkExists(triggerKey)) {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            /**表达式调度构建器,不立即执行**/
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(appQuartz.getCronExpression()).withMisfireHandlingInstructionDoNothing();
            /***按新的cronExpression表达式重新构建trigger***/
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder)
                    .withDescription(appQuartz.getTriggerDesc())
                    .build();
            /**修改参数**/
            if (!trigger.getJobDataMap().get(JobConstants.INVOKE_PARAM).equals(appQuartz.getInvokeParam())) {
                trigger.getJobDataMap().put(JobConstants.INVOKE_PARAM, appQuartz.getInvokeParam());
            }
            /**按新的trigger重新设置job执行**/
            scheduler.rescheduleJob(triggerKey, trigger);
            return DealStatusConstants.SUCCESS;
        } else {
            return "job or trigger not exists";
        }

    }

    /**
     * 执行线程
     ***/
    public void triggerJob(AppQuartz appQuartz) throws SchedulerException {
        if (appQuartz == null) {
            return;
        }
        JobKey jobKey = JobKey.jobKey(appQuartz.getJobName(), appQuartz.getJobGroup());
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(JobConstants.INVOKE_PARAM, appQuartz.getInvokeParam());
        scheduler.triggerJob(jobKey, jobDataMap);
    }

}
