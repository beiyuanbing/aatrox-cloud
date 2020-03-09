package com.aatrox.quartzserver.controller;

import com.aatrox.quartzserver.constants.DealStatusConstants;
import com.aatrox.quartzserver.entity.ReturnMsg;
import com.aatrox.quartzserver.entity.enums.StatusEnum;
import com.aatrox.quartzserver.model.AppQuartz;
import com.aatrox.quartzserver.service.AppQuartzService;
import com.aatrox.quartzserver.service.JobService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yasuo
 */
@RestController
@RequestMapping("/api")
public class JobController {
    @Autowired
    private JobService jobService;
    @Autowired
    private AppQuartzService appQuartzService;
    /**
     * 新增
     */
    @RequestMapping(value = "/addJob", method = RequestMethod.POST)
    public ReturnMsg addjob(@RequestBody AppQuartz appQuartz) throws Exception {
        appQuartzService.insertAppQuartz(appQuartz);
        String result = jobService.addJob(appQuartz);
        if (DealStatusConstants.SUCCESS.equals(result)) {
            return ReturnMsg.SUCCESS("新增成功").setData(result);
        } else {
            return ReturnMsg.FAIL(result);
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/updateJob", method = RequestMethod.POST)
    public ReturnMsg modifyJob(@RequestBody AppQuartz appQuartz) throws Exception {
        String result = jobService.modifyJob(appQuartz);
        if (DealStatusConstants.SUCCESS.equals(result)) {
            appQuartzService.updateAppQuartz(appQuartz);
            return ReturnMsg.SUCCESS("修改成功").setData(result);
        } else {
            return ReturnMsg.FAIL(result);
        }
    }

    /**
     * 保存修改
     */
    @RequestMapping(value = "/saveOrUpdate", method = {RequestMethod.POST})
    public ReturnMsg saveOrupdate(@RequestBody AppQuartz job) throws Exception {

        String result = jobService.saveOrupdate(job);
        if (DealStatusConstants.SUCCESS.equals(result)) {
            return ReturnMsg.SUCCESS("成功").setData(result);
        } else {
            return ReturnMsg.FAIL(result);
        }
    }

    /**
     * 暂停job
     */
    @RequestMapping(value = "/pauseJob", method = RequestMethod.POST)
    public ReturnMsg pausejob(@RequestBody Integer quartzId) throws Exception {
        AppQuartz appQuartz = appQuartzService.selectById(quartzId);
        String result = jobService.pauseJob(appQuartz.getJobName(), appQuartz.getJobGroup());
        if (DealStatusConstants.SUCCESS.equals(result)) {
            appQuartzService.updateAppQuartz(new AppQuartz().setQuartzId(quartzId).setStatus(StatusEnum.PAUSED));
            return ReturnMsg.SUCCESS("暂停成功");
        } else {
            return ReturnMsg.FAIL(result);
        }
    }

    /**
     * 恢复job
     */
    @RequestMapping(value = "/resumeJob", method = RequestMethod.POST)
    public ReturnMsg resumejob(@RequestBody Integer quartzId) throws Exception {
        AppQuartz appQuartz = appQuartzService.selectById(quartzId);
        String result = jobService.resumeJob(appQuartz.getJobName(), appQuartz.getJobGroup());
        if (DealStatusConstants.SUCCESS.equals(result)) {
            appQuartzService.updateAppQuartz(new AppQuartz().setQuartzId(quartzId).setStatus(StatusEnum.NORMAL));
            return ReturnMsg.SUCCESS("恢复成功");
        } else {
            return ReturnMsg.FAIL(result);
        }
    }


    /**
     * 删除job
     */
    @RequestMapping(value = "/deleteJob", method = RequestMethod.POST)
    public ReturnMsg deleteJob(@RequestBody Integer quartzId) throws Exception {
        AppQuartz appQuartz = appQuartzService.selectById(quartzId);
        String result = jobService.deleteJob(appQuartz);
        if (DealStatusConstants.SUCCESS.equals(result)) {
            appQuartzService.deleteById(quartzId);
            return ReturnMsg.SUCCESS("删除成功");
        } else {
            return ReturnMsg.FAIL(result);
        }
    }

    /**
     * 暂停所有
     */
    @RequestMapping(value = "/pauseAll", method = RequestMethod.GET)
    public ReturnMsg pauseAllJob() throws Exception {
        jobService.pauseAllJob();
        return ReturnMsg.SUCCESS("暂停所有成功");
    }

    /**
     * 恢复所有
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/repauseAll", method = RequestMethod.GET)
    public ReturnMsg repauseAllJob() throws Exception {
        jobService.resumeAllJob();
        return ReturnMsg.SUCCESS("全部恢复成功");
    }

    /**
     * 执行一次任务
     * @param quartzId
     * @return
     */
    @RequestMapping(value = "/runJob", method = {RequestMethod.GET, RequestMethod.POST})
    public ReturnMsg runJob(@RequestBody Integer quartzId) {
        AppQuartz appQuartz = null;
        try {
            appQuartz = appQuartzService.selectById(quartzId);
            jobService.triggerJob(appQuartz);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return ReturnMsg.FAIL(e.getMessage());
        }
        return ReturnMsg.SUCCESS("执行成功");
    }

    /**
     * 查询当前任务
     * @param quartzId
     * @return
     */
    @RequestMapping(value = "/selectJob", method = {RequestMethod.GET, RequestMethod.POST})
    public ReturnMsg selectJob(Integer quartzId) {
        AppQuartz appQuartz = null;
        try {
            appQuartz = appQuartzService.selectById(quartzId);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnMsg.FAIL(e.getMessage());
        }
        return ReturnMsg.SUCCESS("成功").setData(appQuartz);
    }
}
