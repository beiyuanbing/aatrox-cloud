package com.aatrox.common.excel.export;

import com.aatrox.common.excel.support.AbstractDataProvider;

import java.util.Collection;
import java.util.Collections;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public class ExportDataProvider<T> extends AbstractDataProvider<T> {

    // 对应 sheet 页数据
    private final Collection<T> datas;

    public ExportDataProvider(Collection<T> datas) {
        this.datas = Collections.unmodifiableCollection(datas);
    }

    @Override
    public Collection<T> getDatas() {
        return datas;
    }

    @Override
    public void fillDatas(Collection<?> datas) {
        throw new UnsupportedOperationException("Must fill data in constructor.");
    }

}
