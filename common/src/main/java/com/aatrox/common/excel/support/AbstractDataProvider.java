package com.aatrox.common.excel.support;

import com.aatrox.common.excel.base.ColumnValueHandler;
import com.aatrox.common.excel.base.DataProvider;
import org.apache.commons.lang3.Validate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public abstract class AbstractDataProvider<T> implements DataProvider<T> {

    // 对应的数据转换器 { key: fieldName, value: valueHandler }
    private final Map<String, ColumnValueHandler> valueHandlerMap = new HashMap<String, ColumnValueHandler>();

    @Override
    public DataProvider<T> registerValueHandler(String key, ColumnValueHandler valueHandler) {
        Validate.notBlank(key);
        Validate.notNull(valueHandler);
        valueHandlerMap.put(key, valueHandler);
        return this;
    }

    @Override
    public boolean existsValueHandler(String key) {
        return valueHandlerMap.containsKey(key);
    }

    @Override
    public ColumnValueHandler getValueHandler(String key) {
        if (existsValueHandler(key)) {
            return valueHandlerMap.get(key);
        }
        return DefaultColumnValueHandler.getInstance();
    }

    public Map<String, ColumnValueHandler> getValueHandlerMap() {
        return valueHandlerMap;
    }

}
