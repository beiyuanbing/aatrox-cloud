package com.aatrox.common.excel.export;

import com.aatrox.common.excel.model.ExcelDocument;
import com.aatrox.common.excel.support.CommonSheetDataProvider;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public class ExcelExportDocument extends ExcelDocument {

    public ExcelExportDocument(String fileName) {
        super(fileName);
    }

    public <E> ExcelDocument createSheetWithAnnotation(Class<E> clazz, List<E> datas, String sheetName) {
        createSheetWithAnnotation(clazz, new CommonSheetDataProvider<E>(datas), sheetName);
        return this;
    }

    public <E> ExcelDocument createSheetWithAnnotation(Class<E> clazz, List<E> datas) {
        createSheetWithAnnotation(clazz, new CommonSheetDataProvider<E>(datas), null);
        return this;
    }

    public boolean doExport(OutputStream os) throws IllegalAccessException, IOException {
        return ExportUtils.doExport(os, this);
    }

}
