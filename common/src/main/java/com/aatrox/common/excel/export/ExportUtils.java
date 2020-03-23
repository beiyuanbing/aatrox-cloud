package com.aatrox.common.excel.export;

import com.aatrox.common.excel.base.ColumnValueHandler;
import com.aatrox.common.excel.base.DataProvider;
import com.aatrox.common.excel.base.FileType;
import com.aatrox.common.excel.listener.SheetListener;
import com.aatrox.common.excel.model.ExcelSheet;
import com.aatrox.common.excel.model.SheetHead;
import com.aatrox.common.excel.model.SheetHeadColumn;
import org.apache.commons.lang3.Validate;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public class ExportUtils {
    static boolean doExport(OutputStream os, ExcelExportDocument excelDocument) throws IOException, IllegalAccessException {
        Validate.notNull(excelDocument);

        FileType fileType = excelDocument.getFileType();
        switch (fileType) {
            case EXCEL97:
                return doExport97(os, excelDocument);

            case EXCEL07:
                return doExport07(os, excelDocument);

            default:
                throw new IllegalArgumentException("error fileType : " + fileType);
        }
    }

    static boolean doLargeExport(OutputStream os, LargeExcelExportDocument excelDocument, boolean concurrent)
            throws IllegalAccessException, IOException {
        Validate.notNull(excelDocument);

        SXSSFWorkbook sxssfWorkbook = null;
        try {
            sxssfWorkbook = new SXSSFWorkbook(excelDocument.getWindowSize());

            List<ExcelSheet> sheets = excelDocument.getSheets();
            Validate.notEmpty(sheets);

            for (int i = 0; i < sheets.size(); i++) {
                ExcelSheet excelSheet = sheets.get(i);
                SheetListener listener = excelSheet.getSheetListener();
                Sheet sheet = sxssfWorkbook.createSheet(excelSheet.getName());

                if (listener != null) {
                    listener.beforeBuild(excelSheet, sheet);
                }
                buildSheetHead(sxssfWorkbook, excelSheet, sheet);
                if (concurrent) {
                    // 使用多线程并发加载数据
                    buildConcurrentLargeSheetBody(excelSheet, sheet);
                } else {
                    buildLargeSheetBody(excelSheet, sheet);
                }

                if (listener != null) {
                    listener.afterBuild(excelSheet, sheet);
                }
            }
            sxssfWorkbook.write(os);
            return true;
        } finally {
            if (sxssfWorkbook != null) {
                sxssfWorkbook.dispose();
                try {
                    sxssfWorkbook.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private static boolean doExport97(OutputStream os, ExcelExportDocument excelDocument) throws IOException, IllegalAccessException {
        HSSFWorkbook hssfWorkbook = null;

        try {
            hssfWorkbook = new HSSFWorkbook();
            writeSheets(hssfWorkbook, excelDocument);
            hssfWorkbook.write(os);
            return true;
        } finally {
            try {
                if (hssfWorkbook != null) {
                    hssfWorkbook.close();
                }
            } catch (IOException e) {
            }
        }
    }

    private static boolean doExport07(OutputStream os, ExcelExportDocument excelDocument) throws IOException, IllegalAccessException {
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook();
            writeSheets(xssfWorkbook, excelDocument);
            xssfWorkbook.write(os);
            return true;
        } finally {
            if (xssfWorkbook != null) {
                try {
                    xssfWorkbook.close();
                } catch (IOException e) {
                    //
                }
            }
        }
    }

    private static void writeSheets(Workbook workbook, ExcelExportDocument excelDocument) throws IllegalAccessException {
        List<ExcelSheet> sheets = excelDocument.getSheets();
        Validate.notEmpty(sheets);

        for (int i = 0; i < sheets.size(); i++) {
            ExcelSheet excelSheet = sheets.get(i);
            Sheet sheet = workbook.createSheet(excelSheet.getName());
            writeSheet(workbook, excelSheet, sheet);
        }
    }

    private static void writeSheet(Workbook workbook, ExcelSheet excelSheet, Sheet sheet) throws IllegalAccessException {
        SheetListener listener = excelSheet.getSheetListener();
        if (listener != null) {
            listener.beforeBuild(excelSheet, sheet);
        }

        buildSheetHead(workbook, excelSheet, sheet);
        buildSheetBody(excelSheet, sheet);

        if (listener != null) {
            listener.afterBuild(excelSheet, sheet);
        }
    }

    // 生成表头
    private static void buildSheetHead(Workbook workbook, ExcelSheet excelSheet, Sheet sheet) {
        SheetHead head = excelSheet.getHead();
        List<String> titles = head.getHeadTitles();
        final Row row = sheet.createRow(0);
        for (int i = 0; i < titles.size(); i++) {
            final Cell cell = row.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(titles.get(i));

            CellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.LEFT);
            cell.setCellStyle(style);
        }
    }

    private static void buildSheetBody(ExcelSheet excelSheet, Sheet sheet) throws IllegalArgumentException, IllegalAccessException {
        Collection<?> datas = excelSheet.getDatas();
        if (CollectionUtils.isEmpty(datas)) {
            return;
        }

        Iterator<?> iterator = datas.iterator();
        int rowNum = 1;  // 从第二行开始插入数据
        while (iterator.hasNext()) {
            Object data = iterator.next();
            buildRow(excelSheet, sheet, data, rowNum);
            rowNum++;
        }
    }

    private static void buildLargeSheetBody(ExcelSheet excelSheet, Sheet sheet)
            throws IllegalArgumentException, IllegalAccessException {
        final LargeExportDataProvider<?> datasProvider = (LargeExportDataProvider<?>) excelSheet.getDatasProvider();
        int rowNum = 1;  // 从第二行开始插入数据
        do {
            Collection<?> datas = datasProvider.loadPageDatas();
            Iterator<?> iterator = datas.iterator();
            while (iterator.hasNext()) {
                Object data = iterator.next();
                buildRow(excelSheet, sheet, data, rowNum);
                rowNum++;
            }
        } while (!datasProvider.isLast());
    }

    private static void buildConcurrentLargeSheetBody(ExcelSheet excelSheet, Sheet sheet)
            throws IllegalArgumentException, IllegalAccessException {
        final ConcurrentLargeExportDataProvider<?> dataProvider = (ConcurrentLargeExportDataProvider<?>) excelSheet.getDatasProvider();
        dataProvider.loadDatas();

        int rowNum = 1;  // 从第二行开始插入数据
        while (!dataProvider.isLast()) {
            Object data = dataProvider.next();

            if (data == null) {
                continue;
            }

            buildRow(excelSheet, sheet, data, rowNum);
            rowNum++;
        }
    }

    private static void buildRow(ExcelSheet excelSheet, Sheet sheet, Object data, int rowNum)
            throws IllegalArgumentException, IllegalAccessException {
        final SheetHead head = excelSheet.getHead();
        final DataProvider<?> datasProvider = excelSheet.getDatasProvider();
        final Map<String, SheetHeadColumn> headColumnMap = head.getHeadColumnMap();
        final Field[] fields = head.getFields();
        final List<String> fieldNames = head.getFieldNames();

        Row row = sheet.createRow(rowNum);
        boolean dataHoldMap = Map.class.isAssignableFrom(data.getClass()) && fields == null;  // 查询返回的数据封装在 Map 中

        for (int j = 0, l = fieldNames.size(); j < l; j++) {
            String cellValue = "", fieldName = fieldNames.get(j);
            if (dataHoldMap) {
                Map<?, ?> dataMap = (Map<?, ?>) data;
                cellValue = getMapValue(fieldName, dataMap, datasProvider);
            } else {
                Field field = fields[j];
                cellValue = getFieldValue(field, data, datasProvider);
            }

            // 创建单元格
            Cell cell = row.createCell(j);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(cellValue);

            // 设置列宽，只要设置一次就可以了
            SheetHeadColumn headColumn = headColumnMap.get(fieldName);
            sheet.setColumnWidth(j, headColumn.getWidth());
        }
    }

    private static String getMapValue(String key, Map<?, ?> dataMap, DataProvider<?> datasProvider) {
        String cellValue = "";
        if (datasProvider.existsValueHandler(key)) {
            ColumnValueHandler valueHandler = datasProvider.getValueHandler(key);
            cellValue = valueHandler.processExportValue(dataMap);
        } else {
            Object value = dataMap.get(key);
            if (value != null) {
                cellValue = value.toString();
            }
        }
        return cellValue;
    }

    private static String getFieldValue(Field field, Object data, DataProvider<?> datasProvider)
            throws IllegalArgumentException, IllegalAccessException {
        // 处理单元格的值
        String cellValue = "";
        final String fieldName = field.getName();
        if (datasProvider.existsValueHandler(fieldName)) {
            ColumnValueHandler valueHandler = datasProvider.getValueHandler(fieldName);
            cellValue = valueHandler.processExportValue(data);
        } else {
            ReflectionUtils.makeAccessible(field);
            Object fieldValue = field.get(data);
            if (fieldValue != null) {
                cellValue = fieldValue.toString();
            }
        }
        return cellValue;
    }
}
