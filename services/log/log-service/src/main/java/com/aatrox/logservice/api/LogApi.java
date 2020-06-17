package com.aatrox.logservice.api;

import com.aatrox.common.utils.StringUtils;
import com.aatrox.logservice.entity.LogRecord;
import com.aatrox.logservice.feign.LogFeign;
import com.aatrox.logservice.service.LogRecordService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/log")
public class LogApi implements LogFeign {
    @Resource
    private LogRecordService logRecordService;

    @Override
    public int deleteByPrimaryKey(Integer fid) {
        return 0;
    }

    @Override
    @PostMapping("/insert")
    public int insert(@RequestBody LogRecord record) {
        if(StringUtils.isEmpty(record.getContent())){
            throw new RuntimeException("不能为空");
        }
        return logRecordService.save(record) ? 1 : 0;
    }


    @Override
    public int insertSelective(LogRecord record) {
        return 0;
    }

    @Override
    public LogRecord selectByPrimaryKey(Integer fid) {
        return null;
    }

    @Override
    public List<LogRecord> selectByParam(Map<String, Object> paramsMap) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(LogRecord record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(LogRecord record) {
        return 0;
    }

    @Override
    public Page<LogRecord> selectPage(LogRecord queryForm) {
        return null;
    }
}
