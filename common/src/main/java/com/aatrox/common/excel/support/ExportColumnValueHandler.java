package com.aatrox.common.excel.support;

import com.aatrox.common.excel.base.ColumnValueHandler;
import org.apache.commons.lang3.NotImplementedException;

/**
 * @author: liaozhicheng
 * @date: 2020-03-13
 * @since 1.0
 */
public interface ExportColumnValueHandler extends ColumnValueHandler {

    @Override
    default Object processImportValue(String value) {
        throw new NotImplementedException("not implemented method.");
    }

}
