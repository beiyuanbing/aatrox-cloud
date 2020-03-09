package com.aatrox.logservice.service.impl;

import com.aatrox.logservice.dao.LogRecordDao;
import com.aatrox.logservice.entity.LogRecord;
import com.aatrox.logservice.service.LogRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志记录表 服务实现类
 * </p>
 *
 * @author aatrox
 * @since 2019-06-05
 */
@Service("logRecordService")
public class LogRecordServiceImpl extends ServiceImpl<LogRecordDao, LogRecord> implements LogRecordService {

}
