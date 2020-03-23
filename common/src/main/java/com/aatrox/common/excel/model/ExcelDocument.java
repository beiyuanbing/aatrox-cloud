package com.aatrox.common.excel.model;

import com.aatrox.common.excel.base.DataProvider;
import com.aatrox.common.excel.base.OfficeDocument;
import com.aatrox.common.excel.base.Sheet;
import com.aatrox.common.excel.base.SheetHeadBuilder;
import com.aatrox.common.excel.listener.SheetListener;
import com.aatrox.common.excel.support.AnnotationSheetHeadBuilder;
import com.aatrox.common.excel.support.CommonSheetDataProvider;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public class ExcelDocument extends OfficeDocument {

    private static final String DEFAULT_SHEET_NAME = "Sheet";

    protected final List<ExcelSheet> sheets = new ArrayList<>();
    private final AtomicInteger sheetNameIndex = new AtomicInteger(0);

    public ExcelDocument(String fileName) {
        super(fileName);
    }

    public ExcelDocument createSheetWithAnnotation(Class<?> clazz) {
        return createSheetWithAnnotation(clazz, CommonSheetDataProvider.newEmptyDataProvider(clazz), null, null);
    }

    public ExcelDocument createSheetWithAnnotation(Class<?> clazz, String sheetName) {
        return createSheetWithAnnotation(clazz, CommonSheetDataProvider.newEmptyDataProvider(clazz), null, sheetName);
    }

    public ExcelDocument createSheetWithAnnotation(Class<?> clazz, DataProvider<?> datasProvider) {
        return createSheetWithAnnotation(clazz, datasProvider, null, null);
    }

    public ExcelDocument createSheetWithAnnotation(Class<?> clazz, DataProvider<?> datasProvider, String sheetName) {
        return this.createSheetWithAnnotation(clazz, datasProvider, null, sheetName);
    }

    public ExcelDocument createSheetWithAnnotation(Class<?> clazz, DataProvider<?> datasProvider, SheetListener sheetListener, String sheetName) {
        return this.createSheet(new AnnotationSheetHeadBuilder(clazz), datasProvider, sheetListener, sheetName);
    }

    public ExcelDocument createSheet(SheetHeadBuilder builder, DataProvider<?> datasProvider) {
        return this.createSheet(builder, datasProvider, null, null);
    }

    public ExcelDocument createSheet(SheetHeadBuilder builder, DataProvider<?> datasProvider, String sheetName) {
        return this.createSheet(builder, datasProvider, null, sheetName);
    }

    public ExcelDocument createSheet(SheetHeadBuilder builder, DataProvider<?> datasProvider, SheetListener sheetListener) {
        return this.createSheet(builder, datasProvider, sheetListener, null);
    }

    public ExcelDocument createSheet(SheetHeadBuilder builder, DataProvider<?> datasProvider, SheetListener sheetListener, String sheetName) {
        sheetName = getUniqueSheetName(builder, sheetName);
        ExcelSheet sheet = ExcelSheet.createSheet(builder, datasProvider, sheetListener, sheetName);
        this.addSheet(sheet);
        return this;
    }

    public ExcelDocument addSheet(ExcelSheet sheet) {
        Objects.requireNonNull(sheet, "sheet");
        this.sheets.add(sheet);
        return this;
    }

    public String getUniqueSheetName(SheetHeadBuilder builder, String sheetName) {
        if(StringUtils.isBlank(sheetName) && builder instanceof AnnotationSheetHeadBuilder) {
            Class<?> clazz = builder.getTargetClass();
            if(clazz.isAnnotationPresent(Sheet.class)) {
                sheetName = clazz.getAnnotation(Sheet.class).name();
            }
        }

        if(StringUtils.isBlank(sheetName)) {
            return new StringBuilder(DEFAULT_SHEET_NAME).append(sheetNameIndex.incrementAndGet()).toString();
        } else {
            if(hasSameSheetName(sheetName)) {
                return sheetName + sheetNameIndex.incrementAndGet();
            }
            else {
                return sheetName;
            }
        }
    }

    public List<ExcelSheet> getSheets() {
        return sheets;
    }

    private boolean hasSameSheetName(String sheetName) {
        return sheets.stream().anyMatch(s -> s.getName().equals(sheetName));
    }

}
