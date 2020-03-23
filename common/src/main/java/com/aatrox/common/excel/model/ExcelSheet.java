package com.aatrox.common.excel.model;

import com.aatrox.common.excel.base.DataProvider;
import com.aatrox.common.excel.base.SheetHeadBuilder;
import com.aatrox.common.excel.listener.SheetListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/20
 */
public class ExcelSheet {
    //数据关联的实体类
    private final Class<?> clazz;

    //对应的表头
    private final SheetHead head;

    //sheet页名称
    private final String name;

    // 导入导出时出错的提示信息
    private final List<String> errorMsgHolder = new ArrayList<String>();

    // sheet 页数据提供者
    private final DataProvider<?> datasProvider;

    // sheet 页事件监听，在 sheet 页开始或结束写入时插入特殊处理
    private final SheetListener sheetListener;

    private ExcelSheet(SheetHeadBuilder builder, DataProvider<?> datasProvider, SheetListener sheetListener, String sheetName) {
        this.clazz = builder.getTargetClass();
        this.head = builder.build();
        this.name = sheetName;
        this.datasProvider = datasProvider;
        this.sheetListener = sheetListener;
    }

    public static ExcelSheet createSheet(SheetHeadBuilder builder, DataProvider<?> datasProvider, SheetListener sheetListener, String sheetName) {
        ExcelSheet sheet = new ExcelSheet(builder, datasProvider, sheetListener, sheetName);
        return sheet;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public SheetHead getHead() {
        return head;
    }

    public String getName() {
        return name;
    }

    public List<String> getErrorMsgHolder() {
        return errorMsgHolder;
    }

    public DataProvider<?> getDatasProvider() {
        return datasProvider;
    }

    public SheetListener getSheetListener() {
        return sheetListener;
    }

    public Collection<?> getDatas() {
        return datasProvider.getDatas();
    }

    public void fillDatas(Collection<?> datas) {
        datasProvider.fillDatas(datas);
    }
}
