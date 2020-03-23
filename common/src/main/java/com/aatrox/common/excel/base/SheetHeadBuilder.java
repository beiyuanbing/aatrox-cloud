package com.aatrox.common.excel.base;

import com.aatrox.common.excel.model.SheetHead;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/20
 */
public interface SheetHeadBuilder {

    SheetHead build();

    Class<?> getTargetClass();
}
