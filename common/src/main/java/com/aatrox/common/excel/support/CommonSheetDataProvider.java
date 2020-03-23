package com.aatrox.common.excel.support;

import com.aatrox.common.excel.base.DataProvider;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public class CommonSheetDataProvider<T> extends AbstractDataProvider<T> {

    // 对应 sheet 页数据
    private Collection<T> datas;

    private CommonSheetDataProvider() {
    }

    public CommonSheetDataProvider(Collection<T> datas) {
        this.datas = Collections.unmodifiableCollection(datas);
    }

    public CommonSheetDataProvider(Collection<T> datas, ExportValueHandlerCollections exportValueHandlerCollections) {
        this.datas = Collections.unmodifiableCollection(datas);
        exportValueHandlerCollections.each(this::registerValueHandler);
    }

    public CommonSheetDataProvider(Supplier<Collection<T>> supplier) {
        this.datas = supplier.get();
    }

    public static <T> DataProvider<T> newEmptyDataProvider(Class<T> clazz) {
        return new CommonSheetDataProvider<>();
    }

    @Override
    public Collection<T> getDatas() {
        return datas;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void fillDatas(Collection<?> datas) {
        this.datas = (Collection<T>) datas;
    }

}
