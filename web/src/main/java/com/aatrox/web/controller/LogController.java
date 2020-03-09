package com.aatrox.web.controller;

import com.aatrox.logservice.entity.LogRecord;
import com.aatrox.web.remote.LogRemote;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author aatrox
 * @desc
 * @date 2019/9/20
 */
@RestController
@RequestMapping("/log")
public class LogController {
    @Resource
    private LogRemote logRemote;

    @PostMapping("/insert")
    public Object insert(LogRecord model) {
        return logRemote.insetLog(model);
    }
}
