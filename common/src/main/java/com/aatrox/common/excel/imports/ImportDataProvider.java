package com.aatrox.common.excel.imports;

import com.aatrox.common.excel.support.AbstractDataProvider;

import java.util.Collection;
import java.util.Collections;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public class ImportDataProvider<T> extends AbstractDataProvider<T> {

    private Collection<T> datas;

    @Override
    public Collection<T> getDatas() {
        return datas;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void fillDatas(Collection<?> datas) {
        this.datas = Collections.unmodifiableCollection((Collection<T>) datas);
    }

}
