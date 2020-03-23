package com.aatrox.common.excel.support;


import com.aatrox.common.excel.listener.SheetListener;
import com.aatrox.common.excel.model.ExcelSheet;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * 用来做单元格合并的 sheet listener
 *
 * @author: liaozhicheng
 * @date: 2020-03-18
 * @since 1.0
 */
public class MergeCellListener implements SheetListener {

    private final List<CellRangeAddress> mergeRangeAddresses = Lists.newArrayList();

    /**
     * 分别为起始行，结束行，起始列，结束列
     * 行和列都是从0开始计数，且起始结束都会合并
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     * @return
     */
    public MergeCellListener addRange(int firstRow, int lastRow, int firstCol, int lastCol) {
        mergeRangeAddresses.add(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
        return this;
    }

    @Override
    public void beforeBuild(ExcelSheet excelSheet, Sheet sheet) {
    }

    @Override
    public void afterBuild(ExcelSheet excelSheet, Sheet sheet) {
        mergeRangeAddresses.forEach(sheet::addMergedRegion);
    }
}
