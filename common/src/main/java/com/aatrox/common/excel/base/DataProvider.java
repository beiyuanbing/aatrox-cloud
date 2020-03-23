package com.aatrox.common.excel.base;

import java.util.Collection;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public interface DataProvider<T> {

    Collection<T> getDatas();

    /**
     * 填充数据
     * @param datas
     */
    void fillDatas(Collection<?> datas);

    /**
     * 对应的 key 是否存在值处理器
     * @param key
     * @return
     */
    boolean existsValueHandler(String key);

    /**
     * 注册一个值处理器
     * @param key
     * @param valueHandler
     * @return
     */
    DataProvider<T> registerValueHandler(String key, ColumnValueHandler valueHandler);

    /**
     * 获取值处理器
     * @param key
     * @return
     */
    ColumnValueHandler getValueHandler(String key);

}