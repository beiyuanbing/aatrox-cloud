package com.aatrox.web.base.service;

import java.util.List;

/**
 * @author aatrox
 * @desc 用于进行exportExcel使用的
 * @date 2019/9/2
 */
public interface ExportExcelService {
    /**
     * 直接是mybatis的dao+方法的方式执行
     * @param sqlMapsId
     * @param params
     * @return
     */
    List getListBySqlMapsId(String sqlMapsId,Object params);

    /**
     * 调用bean方法的方式，目前是只支持参数类型为params的方式，后续也会加入各种类型
     * @param beanName
     * @param methodName
     * @param params
     * @return
     */
    List getListByBeanId(String beanName,String methodName, Object params);

}
