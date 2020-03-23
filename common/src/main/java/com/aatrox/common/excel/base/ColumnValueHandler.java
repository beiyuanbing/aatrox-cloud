package com.aatrox.common.excel.base;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public interface ColumnValueHandler {

    /**
     * 导入值处理
     * @param value
     * @return
     */
    Object processImportValue(String value);

    /**
     * 导出值处理
     * @param value
     * @return
     */
    String processExportValue(Object value);

}
