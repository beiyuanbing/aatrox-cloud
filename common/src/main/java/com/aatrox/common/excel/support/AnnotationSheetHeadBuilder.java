package com.aatrox.common.excel.support;

import com.aatrox.common.excel.base.ColumnHead;
import com.aatrox.common.excel.base.Sheet;
import com.aatrox.common.excel.model.SheetHead;
import com.aatrox.common.excel.model.SheetHeadColumn;
import com.aatrox.common.utils.ReflectionUtils;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public class AnnotationSheetHeadBuilder extends AbstractSheetHeadBuilder {

    public AnnotationSheetHeadBuilder(Class<?> targetClazz) {
        super(targetClazz);
        Validate.isTrue(targetClazz.isAssignableFrom(Sheet.class), String.format("注解方式创建表头，类必须使用 @%s 注解", Sheet.class.getSimpleName()));
    }

    @Override
    public SheetHead build() {
        return ParseSheetHeadUtils.parseHead(targetClazz);
    }


    private static final class ParseSheetHeadUtils {

        private static final ConcurrentHashMap<Class<?>, SheetHead> sheetHeadCache = new ConcurrentHashMap<>();

        private static SheetHead parseHead(Class<?> targetClazz) {
            SheetHead sheetHead = sheetHeadCache.get(targetClazz);
            if (sheetHead == null) {
                sheetHead = doParseHead(targetClazz);
                sheetHeadCache.put(targetClazz, sheetHead);
            }
            return sheetHead;
        }

        /**
         * 根据实体类中的注解，解析出 excel 模板的表头
         *
         * @param targetClazz
         * @return
         */
        private static SheetHead doParseHead(Class<?> targetClazz) {
            synchronized (sheetHeadCache) {
                // retry
                SheetHead cacheHead = sheetHeadCache.get(targetClazz);
                if (cacheHead != null) {
                    return cacheHead;
                }

                SheetHead sheetHead = new SheetHead();
                Field[] fields = ReflectionUtils.getSpecifiedAndSort(targetClazz, ColumnHead.class, Comparator.comparingInt(f -> (f.getAnnotation(ColumnHead.class)).order()));
                for (Field field : fields) {
                    ColumnHead an = field.getAnnotation(ColumnHead.class);
                    SheetHeadColumn headColumn = new SheetHeadColumn(an.title(), field.getName(), an.width() * SheetHeadColumn.DEFAULT_MULTIPLE);
                    sheetHead.addColumn(headColumn);
                }
                sheetHead.setFields(fields);
                return sheetHead;
            }
        }
    }

}