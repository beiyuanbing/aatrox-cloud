package com.aatrox.quartzserver.dao;


import com.aatrox.quartzserver.model.AppQuartz;

import java.util.List;

/**
 * 订单系统Dao层
 * Created by byb on 2019-08-01
 */
public interface AppQuartzDao {
    AppQuartz selectById(Integer id);

    int insertAppQuartz(AppQuartz form);

    int updateAppQuartz(AppQuartz form);

    void deleteById(Integer id);

    List<AppQuartz> selectAppQuartzs(AppQuartz form);
}
