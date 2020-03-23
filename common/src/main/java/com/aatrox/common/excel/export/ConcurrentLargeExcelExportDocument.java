package com.aatrox.common.excel.export;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public class ConcurrentLargeExcelExportDocument extends LargeExcelExportDocument {

    public ConcurrentLargeExcelExportDocument(String fileName) {
        super(fileName);
    }

    public ConcurrentLargeExcelExportDocument(String fileName, int windowSize) {
        super(fileName, windowSize);
    }


    @Override
    public boolean doExport(OutputStream os) throws IllegalAccessException, IOException {
        return ExportUtils.doLargeExport(os, this, true);
    }
}
