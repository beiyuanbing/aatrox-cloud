package com.aatrox.common.excel.support;

import com.aatrox.common.excel.model.SheetHead;
import com.aatrox.common.excel.model.SheetHeadColumn;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public class CommonSheetHeadBuilder extends AbstractSheetHeadBuilder {

    private final SheetHead head;

    public CommonSheetHeadBuilder(Class<?> targetClazz) {
        super(targetClazz);
        this.head = new SheetHead();
    }

    public CommonSheetHeadBuilder append(String display, String name, Integer width) {
        head.addColumn(new SheetHeadColumn(display, name, width * SheetHeadColumn.DEFAULT_MULTIPLE));
        return this;
    }

    public CommonSheetHeadBuilder append(String display, String name) {
        this.append(display, name, SheetHeadColumn.DEFAULT_COLUMN_WIDTH);
        return this;
    }

    public CommonSheetHeadBuilder appendIfTrue(boolean condition, String display, String name) {
        if(condition)
            this.append(display, name, SheetHeadColumn.DEFAULT_COLUMN_WIDTH);
        return this;
    }

    @Override
    public SheetHead build() {
        if(isMapTargetClass()) {
            // 如果是用 Map 来封装返回数据，这里不做处理
        } else {
            final List<String> fieldNames = head.getFieldNames();
            Field[] fields = new Field[fieldNames.size()];
            int i = 0;
            for(String fieldName : fieldNames) {
                try {
                    fields[i++] = targetClazz.getDeclaredField(fieldName);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
            head.setFields(fields);
        }
        return head;
    }

    @Override
    public Class<?> getTargetClass() {
        return targetClazz;
    }

}
