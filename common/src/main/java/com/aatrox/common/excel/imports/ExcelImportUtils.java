package com.aatrox.common.excel.imports;

import com.aatrox.common.excel.base.ColumnValueHandler;
import com.aatrox.common.excel.base.DataProvider;
import com.aatrox.common.excel.base.FileType;
import com.aatrox.common.excel.model.ExcelSheet;
import com.aatrox.common.excel.model.SheetHead;
import com.aatrox.common.utils.IOUtils;
import com.aatrox.common.utils.ReflectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
class ExcelImportUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ExcelImportUtils.class);

    public static boolean doImport(InputStream is, ExcelImportDocument excelDocument) {
        Validate.notNull(excelDocument);

        FileType fileType = excelDocument.getFileType();
        switch (fileType) {
            case EXCEL97:
                return doImport97(is, excelDocument);

            case EXCEL07:
                return doImport07(is, excelDocument);

            default:
                throw new IllegalArgumentException("error fileType : " + fileType);
        }
    }

    private static boolean doImport97(InputStream is, ExcelImportDocument excelDocument) {
        HSSFWorkbook hssfWorkbook = null;
        try {
            hssfWorkbook = new HSSFWorkbook(is);
            return readSheets(hssfWorkbook, excelDocument);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            excelDocument.getErrorMsgHolder().add(e.getMessage());
            return false;
        } finally {
            try {
                if (hssfWorkbook != null) {
                    hssfWorkbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            IOUtils.closeSilently(is);
        }
    }

    private static boolean doImport07(InputStream is, ExcelImportDocument excelDocument) {
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(is);
            return readSheets(xssfWorkbook, excelDocument);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        } finally {
            if (xssfWorkbook != null) {
                try {
                    xssfWorkbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            IOUtils.closeSilently(is);
        }
    }

    private static boolean readSheets(Workbook workbook, ExcelImportDocument excelDocument) {
        boolean importSuccess = true;

        List<ExcelSheet> sheets = excelDocument.getSheets();
        for(int i = 0; i < sheets.size(); i++) {
            ExcelSheet excelSheet = sheets.get(i);
            try {
                Sheet sheet;
                String sheetName = excelSheet.getName();
                if(StringUtils.isNotBlank(sheetName) && (sheet = workbook.getSheet(sheetName)) != null) {
                    // 优选按名称匹配
                } else {
                    sheet = workbook.getSheetAt(i);
                }

                Validate.notNull(sheet);
                importSuccess &= readSheet(excelSheet, sheet, excelSheet.getClazz());
            } catch (Exception parseSheetException) {
                importSuccess = false;
                excelSheet.getErrorMsgHolder().add(parseSheetException.getMessage());
                LOG.error(parseSheetException.getMessage(), parseSheetException);
            }
        }
        return importSuccess;
    }

    private static <E> boolean readSheet(ExcelSheet excelSheet, Sheet sheet, Class<E> clazz)
            throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        // 校验上传的 excel 文档模板是否正确，如果模板不正确则不解析
        SheetHead sheetHead = excelSheet.getHead();
        Row headRow = sheet.getRow(0);
        if (!verifyHeadRow(headRow, sheetHead, excelSheet.getErrorMsgHolder())) {
            return false;
        }

        // 逐行逐列解析 excel
        final Field[] fields = sheetHead.getFields();
        final DataProvider<?> datasProvider = excelSheet.getDatasProvider();
        final int lastRowNum = sheet.getLastRowNum();
        final int lastColumnNum = fields.length;

        List<E> result = new ArrayList<>(lastRowNum);
        for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }

            E entity = ReflectionUtils.newInstance(clazz);
            boolean emptyRow = true;
            for (int columnNum = 0; columnNum < lastColumnNum; columnNum++) {
                Cell cell = row.getCell(columnNum);
                if (cell == null) {
                    continue;
                }

                cell.setCellType(CellType.STRING);
                String cellVal = cell.getStringCellValue();
                if(StringUtils.isNotBlank(cellVal)) {
                    Field field = fields[columnNum];
                    org.springframework.util.ReflectionUtils.makeAccessible(field);

                    // 处理列的值，如果指定了列的值处理器
                    ColumnValueHandler valueHandler = datasProvider.getValueHandler(field.getName());
                    Object newVal = valueHandler.processImportValue(cellVal.trim());
                    org.springframework.util.ReflectionUtils.setField(field, entity, newVal);
                    emptyRow = false;
                }
            }

            if(!emptyRow) {
                result.add(entity);
            }
        }
        excelSheet.fillDatas(result);
        return true;
    }


    /**
     * 校验上传的 excel 文档的模板是否正确
     * @param headRow
     * @param sheetHead
     * @param errorMsgHolder
     * @return
     */
    private static boolean verifyHeadRow(Row headRow, SheetHead sheetHead, List<String> errorMsgHolder) {
        if (headRow == null) {
            errorMsgHolder.add("导入的 excel 未定义表头，请检查，正确的列顺序：" + sheetHead.toString());
            return false;
        }

        List<String> headTitles = sheetHead.getHeadTitles();
        int lastCellNum = headRow.getLastCellNum();
        if (lastCellNum != headTitles.size()) {
            // 表头列的数量和实体中定义的表头类的数量不一致
            errorMsgHolder.add("导入的 excel 表头长度和模板定义的长度不一致，请检查，正确的列顺序：" + sheetHead.toString());
            return false;
        }

        boolean verify = true;
        for (int i = 0; i < lastCellNum; i++) {
            Cell cell = headRow.getCell(i);
            String title = cell.getStringCellValue();
            String templateTitle = headTitles.get(i);
            if (!templateTitle.equals(title)) {
                verify = false;
                errorMsgHolder.add("导入的 excel 表头列定义和模板定义的不一致，请检查，正确的列顺序：" + sheetHead.toString());
                break;
            }
        }
        return verify;
    }

}
