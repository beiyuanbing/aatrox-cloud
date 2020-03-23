package com.aatrox.common.excel.export;

import com.aatrox.common.excel.base.DataProvider;
import com.aatrox.common.excel.base.SheetHeadBuilder;
import com.aatrox.common.excel.listener.SheetListener;
import com.aatrox.common.excel.support.AnnotationSheetHeadBuilder;
import com.aatrox.common.excel.support.CommonSheetDataProvider;
import com.aatrox.common.excel.support.ExportValueHandlerCollections;
import com.google.common.collect.Lists;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public class MultiSheetExportBuilder {


    private final List<SheetHolder> sheetHolders = Lists.newArrayList();


    public MultiSheetExportBuilder() {
    }

    public <T> MultiSheetExportBuilder addSheet(SheetHeadBuilder headBuilder, DataProvider<T> dataProvider) {
        sheetHolders.add(new SheetHolder(headBuilder, dataProvider));
        return this;
    }

    public <T> MultiSheetExportBuilder addSheet(SheetHeadBuilder headBuilder, DataProvider<T> dataProvider, SheetListener sheetListener) {
        sheetHolders.add(new SheetHolder(headBuilder, dataProvider, sheetListener));
        return this;
    }

    public <T> MultiSheetExportBuilder addSheet(Class<T> tClass, List<T> datas) {
        return this.addSheet(new AnnotationSheetHeadBuilder(tClass), new CommonSheetDataProvider<>(datas));
    }

    public <T> MultiSheetExportBuilder addSheet(Class<T> tClass, List<T> datas, ExportValueHandlerCollections exportValueHandlerCollections) {
        return this.addSheet(new AnnotationSheetHeadBuilder(tClass), new CommonSheetDataProvider<>(datas, exportValueHandlerCollections));
    }

    public void export(HttpServletResponse response, String fileName) throws Exception {
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        response.setContentType("application/octet-stream");

        try (OutputStream os = response.getOutputStream()) {
            ExcelExportDocument document = new ExcelExportDocument(fileName);
            sheetHolders.forEach(sh -> document.createSheet(sh.sheetHeadBuilder, sh.dataProvider, sh.sheetListener));
            document.doExport(os);
        }
    }

    private class SheetHolder {

        private final SheetHeadBuilder sheetHeadBuilder;
        private final DataProvider<?> dataProvider;
        private final SheetListener sheetListener;

        SheetHolder(SheetHeadBuilder sheetHeadBuilder, DataProvider<?> dataProvider) {
            this(sheetHeadBuilder, dataProvider, null);
        }
        SheetHolder(SheetHeadBuilder sheetHeadBuilder, DataProvider<?> dataProvider, SheetListener sheetListener) {
            this.sheetHeadBuilder = sheetHeadBuilder;
            this.dataProvider = dataProvider;
            this.sheetListener = sheetListener;
        }
    }


}
