package com.aatrox.common.excel.export;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public class LargeExcelExportDocument extends ExcelExportDocument {

    private static final int DEFAULT_WINDOW_SIZE = 100;

    private int windowSize;

    public LargeExcelExportDocument(String fileName) {
        this(fileName,  DEFAULT_WINDOW_SIZE);
    }

    public LargeExcelExportDocument(String fileName, int windowSize) {
        super(fileName);
        this.windowSize = DEFAULT_WINDOW_SIZE;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
    }

    @Override
    public boolean doExport(OutputStream os) throws IllegalAccessException, IOException {
        return ExportUtils.doLargeExport(os, this, false);
    }

}
