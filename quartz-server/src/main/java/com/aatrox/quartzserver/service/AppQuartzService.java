package com.aatrox.quartzserver.service;

import com.aatrox.quartzserver.dao.AppQuartzDao;
import com.aatrox.quartzserver.model.AppQuartz;
import com.aatrox.quartzserver.util.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AppQuartzService {
    @Resource
    private AppQuartzDao appQuartzDao;

    public AppQuartz selectById(Integer id) {
        return appQuartzDao.selectById(id);
    }

    public int insertAppQuartz(AppQuartz form) {
        if (StringUtils.isEmpty(form.getStartTime())) {
            form.setStartTime(DateUtils.getDateString(new Date()));
        }
        return appQuartzDao.insertAppQuartz(form);
    }

    public int updateAppQuartz(AppQuartz form) {
        return appQuartzDao.updateAppQuartz(form);
    }

    public void deleteById(Integer id) {
        appQuartzDao.deleteById(id);
    }

    public List<AppQuartz> selectAppQuartzs(AppQuartz form) {
        return appQuartzDao.selectAppQuartzs(form);
    }
}
