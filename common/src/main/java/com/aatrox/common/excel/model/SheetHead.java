package com.aatrox.common.excel.model;

import com.aatrox.common.utils.ListUtil;
import com.aatrox.common.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/20
 */
public class SheetHead {
    //excel文本title
    private final List<String> headTitles=new ArrayList<>();

    //对应实体属性字段
    private final List<String> fieldNames=new ArrayList<>();

    //key字段名跟SheetHeadColumn的对应
    private final Map<String,SheetHeadColumn> headColumnMap=new HashMap<>();

    //对应的实体列表
    private Field[] fields;

    public SheetHead addColumn(SheetHeadColumn headColumn){
        headTitles.add(headColumn.getTitle());
        fieldNames.add(headColumn.getFieldName());
        headColumnMap.put(headColumn.getFieldName(),headColumn);
        return this;
    }


    public List<String> getHeadTitles() {
        return headTitles;
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }

    public Map<String, SheetHeadColumn> getHeadColumnMap() {
        return headColumnMap;
    }

    public Field[] getFields() {
        return fields;
    }

    public SheetHead setFields(Field[] fields) {
        this.fields = fields;
        return this;
    }

    @Override
    public String toString() {
        return ListUtil.isEmpty(this.headTitles) ? "" : StringUtils.join(this.headTitles, ",");
    }
}
