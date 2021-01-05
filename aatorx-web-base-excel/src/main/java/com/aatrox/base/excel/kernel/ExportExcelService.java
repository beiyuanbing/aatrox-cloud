package com.aatrox.base.excel.kernel;

import com.aatrox.base.excel.utils.ApplicationContextRegister;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author aatrox
 * @desc
 * @date 2021/1/5
 */
@Service
public class ExportExcelService {
    /**
     * 直接是mybatis的dao+方法的方式执行
     *
     * @param sqlMapsId
     * @param params
     * @return
     */
    public List getListBySqlMapsId(String sqlMapsId, Object params) {
        //比较麻烦,得引入数据库的相关的，现在不是直接调用了走了http所有优点区别
//        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
//        try {
//            return sqlSession.selectList("sqlMapsId", params);
//        } finally {
//            sqlSession.close();
//        }
        return null;
    }

    /**
     * 调用bean方法的方式，目前是只支持参数类型为params的方式，后续也会加入各种类型
     *
     * @param beanName
     * @param methodName
     * @param params
     * @return
     */
    public List getListByBeanId(String beanName, String methodName, Object params, Class claz) {
        Object bean = ApplicationContextRegister.getApplicationContext().getBean(beanName);
        Method method = ReflectionUtils.findMethod(bean.getClass(), methodName, claz);
        List resultList = (List) ReflectionUtils.invokeMethod(method, bean, params);
        return resultList;
    }
}


