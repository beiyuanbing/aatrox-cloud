package com.aatrox.web.remote;

import com.aatrox.logservice.entity.LogRecord;
import com.aatrox.logservice.feign.LogFeign;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author aatrox
 * @desc
 * @date 2019/9/20
 */
@Service
public class LogRemote {
    @Resource
    private LogFeign logFeign;

    @Hystrix
    public int insetLog(LogRecord logRecord) {
        return logFeign.insert(logRecord);
    }
}
