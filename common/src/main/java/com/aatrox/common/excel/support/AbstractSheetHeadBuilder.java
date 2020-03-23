package com.aatrox.common.excel.support;

import com.aatrox.common.excel.base.SheetHeadBuilder;
import org.apache.commons.lang3.Validate;

import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public abstract class AbstractSheetHeadBuilder implements SheetHeadBuilder {

    protected final Class<?> targetClazz;

    protected AbstractSheetHeadBuilder(Class<?> targetClazz) {
        Validate.notNull(targetClazz);
        this.targetClazz = targetClazz;
    }

    public boolean isMapTargetClass() {
        return Map.class.isAssignableFrom(this.targetClazz);
    }

    @Override
    public Class<?> getTargetClass(){
        return targetClazz;
    }

}
