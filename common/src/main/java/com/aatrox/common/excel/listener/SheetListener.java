package com.aatrox.common.excel.listener;

import com.aatrox.common.excel.model.ExcelSheet;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/20
 */
public interface SheetListener {
    void beforeBuild(ExcelSheet excelSheet, Sheet sheet);

    void afterBuild(ExcelSheet excelSheet, Sheet sheet);
}
