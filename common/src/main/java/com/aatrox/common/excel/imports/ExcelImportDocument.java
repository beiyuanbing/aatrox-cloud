package com.aatrox.common.excel.imports;

import com.aatrox.common.excel.model.ExcelDocument;
import com.aatrox.common.excel.model.ExcelSheet;
import org.apache.commons.lang3.Validate;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Excel 导入文档
 * 支持多个 sheet 页同时导入，每个 sheet 页必须定义一个表头，支持不同 sheet 页不同表头
 * 表头的定义可以采用注解方式和普通的构造方式
 *
 * @since 1.0
 */
public class ExcelImportDocument extends ExcelDocument {

    private final String fileName;
    private final InputStream inputStream;

    public ExcelImportDocument(InputStream inputStream, String fileName) {
        super(fileName);
        this.fileName = fileName;
        this.inputStream = inputStream;
    }

    public boolean doImport() {
        Validate.notNull(inputStream, "inputStream must not null");

        InputStream is = null;
        try {
            is = new BufferedInputStream(this.inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doImport(is);
    }

    public boolean doImport(InputStream is) {
        return ExcelImportUtils.doImport(is, this);
    }

    public List<String> getErrorMsgHolder() {
        List<String> errorMsgHolder = new ArrayList<>();
        for(ExcelSheet sheet : sheets) {
            errorMsgHolder.addAll(sheet.getErrorMsgHolder());
        }
        return errorMsgHolder;
    }

    public Map<Integer, Collection<?>> getAllSheetDatas() {
        Map<Integer, Collection<?>> allSheetDatas = new HashMap<>();
        for(int i = 0; i < sheets.size(); i++) {
            allSheetDatas.put(Integer.valueOf(i), sheets.get(i).getDatas());
        }
        return allSheetDatas;
    }

    /**
     * 默认获取第一个 sheet 页数据
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getDefaultDatas(Class<T> clazz) {
        return new ArrayList<T>((Collection<T>) sheets.get(0).getDatas());
    }

    /**
     * 获取指定 sheet 页数据
     * @param sheetIndex sheet 页下标
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> Collection<T> getSheetDatas(int sheetIndex, Class<T> clazz) {
        return (Collection<T>) sheets.get(sheetIndex).getDatas();
    }

    @Override
    public String getFileName() {
        return fileName;
    }
}
