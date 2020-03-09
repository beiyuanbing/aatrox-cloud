package com.aatrox.quartzserver.model;

import com.aatrox.quartzserver.entity.enums.StatusEnum;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class AppQuartz implements Serializable {
    /**
     * 主键
     **/
    private Integer quartzId;
    /**
     * 任务名称
     **/
    private String jobName;
    /**
     * 任务分组
     **/
    private String jobGroup;
    /**
     * 任务描述
     **/
    private String description;
    /**
     * 任务开始时间
     **/
    private String startTime;
    /**
     * 任务状态
     **/
    private StatusEnum status;
    /**
     * corn表达式
     **/
    private String cronExpression;
    /**
     * 触发器描述
     **/
    private String triggerDesc;
    /**
     * 需要传递的参数
     **/
    private String invokeParam;
    /**
     * 备注
     **/
    private String remark;
    public Integer getQuartzId() {
        return quartzId;
    }

    public AppQuartz setQuartzId(Integer quartzId) {
        this.quartzId = quartzId;
        return this;
    }

    public String getJobName() {
        return jobName;
    }

    public AppQuartz setJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public AppQuartz setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public AppQuartz setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public AppQuartz setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
        return this;
    }

    public String getInvokeParam() {
        return invokeParam;
    }

    public AppQuartz setInvokeParam(String invokeParam) {
        this.invokeParam = invokeParam;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AppQuartz setDescription(String description) {
        this.description = description;
        return this;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public String getStatusStr() {
        return status == null ? "" : status.getDesc();
    }

    public AppQuartz setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public AppQuartz setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getTriggerDesc() {
        return triggerDesc;
    }

    public AppQuartz setTriggerDesc(String triggerDesc) {
        this.triggerDesc = triggerDesc;
        return this;
    }
}
