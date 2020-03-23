package com.aatrox.common.excel.support;

import com.aatrox.common.excel.base.ColumnValueHandler;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public abstract class DefaultColumnValueHandler implements ColumnValueHandler {

    protected static FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd");

    private static final ColumnValueHandler INSTANCE = new DefaultColumnValueHandler() {

        @Override
        public Object processImportValue(String value) {
            return value;
        }

        @Override
        public String processExportValue(Object value) {
            if(value == null) {
                return "";
            }

            if(value instanceof Date) {
                return format.format((Date) value);
            }

            return value.toString();
        }

    };

    public static ColumnValueHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public Object processImportValue(String value) {
        throw new UnsupportedOperationException("processImportValue method not implemented");
    }

    @Override
    public String processExportValue(Object value) {
        throw new UnsupportedOperationException("processExportValue method not implemented");
    }

}
