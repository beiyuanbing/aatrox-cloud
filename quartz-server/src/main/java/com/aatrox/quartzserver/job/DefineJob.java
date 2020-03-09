package com.aatrox.quartzserver.job;

import com.aatrox.quartzserver.config.ApplicationContextRegister;
import com.aatrox.quartzserver.constants.JobConstants;
import com.aatrox.quartzserver.service.MsgService;
import org.quartz.*;
import org.springframework.stereotype.Component;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class DefineJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap data = context.getTrigger().getJobDataMap();
        String invokeParam = (String) data.get(JobConstants.INVOKE_PARAM);
        //在这里实现业务逻辑
        //此处做发送业务
        JobKey key = jobDetail.getKey();
        System.out.println(key.getGroup() + "," + key.getName() + "," + invokeParam);
        MsgService msgService = (MsgService) ApplicationContextRegister.getApplicationContext().getBean("msgService");
        msgService.send(key.getGroup() + "," + key.getName(), invokeParam);
    }
}