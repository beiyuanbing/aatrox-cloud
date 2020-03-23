package com.aatrox.common.excel.support;

import com.aatrox.common.excel.base.ColumnValueHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public class ExportValueHandlerCollections {

    private final Map<String, ColumnValueHandler> valueHandlerMap = new HashMap<>();

    public ExportValueHandlerCollections registValueHandler(String key, ColumnValueHandler valueHandler) {
        this.valueHandlerMap.put(key, valueHandler);
        return this;
    }

    public void each(BiConsumer<String, ColumnValueHandler> consumer) {
        valueHandlerMap.forEach(consumer);
    }
}
