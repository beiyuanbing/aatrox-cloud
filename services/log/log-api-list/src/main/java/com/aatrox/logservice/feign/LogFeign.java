package com.aatrox.logservice.feign;

import com.aatrox.logservice.constants.ZoneStants;
import com.aatrox.logservice.entity.LogRecord;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2019/9/20
 */

@FeignClient(value = ZoneStants.FEGIN_URL, contextId = "LogFeign")
public interface LogFeign {
    @PostMapping("/log/deleteByPrimaryKey")
    int deleteByPrimaryKey(Integer fid);

    @PostMapping("/log/insert")
    int insert(LogRecord record);

    @PostMapping("/log/insertSelective")
    int insertSelective(LogRecord record);

    @PostMapping("/log/selectByPrimaryKey")
    LogRecord selectByPrimaryKey(Integer fid);

    @PostMapping("/log/selectByParam")
    List<LogRecord> selectByParam(Map<String, Object> paramsMap);

    @PostMapping("/log/updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(LogRecord record);

    @PostMapping("/log/updateByPrimaryKey")
    int updateByPrimaryKey(LogRecord record);

    @PostMapping("/log/selectPage")
    Page<LogRecord> selectPage(LogRecord queryForm);
}