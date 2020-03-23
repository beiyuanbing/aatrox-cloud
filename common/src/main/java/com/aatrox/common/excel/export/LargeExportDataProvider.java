package com.aatrox.common.excel.export;

import com.aatrox.common.excel.base.DataLoader;
import com.aatrox.common.excel.support.AbstractDataProvider;

import java.util.Collection;
import java.util.logging.Logger;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public class LargeExportDataProvider<T> extends AbstractDataProvider<T> {

    private static final Logger LOG = Logger.getLogger(LargeExportDataProvider.class.getName());

    // 数据分页加载器
    private final DataLoader<T> dataLoader;

    private PageHelper pageHelper;

    // 分页查询时，每页查看的记录数
    private final int pageSize;

    public LargeExportDataProvider(DataLoader<T> dataLoader, int pageSize) {
        this.dataLoader = dataLoader;
        this.pageSize = pageSize;
    }

    public LargeExportDataProvider(DataLoader<T> dataLoader) {
        this(dataLoader, PageHelper.DEFAULT_PAGE_SIZE);
    }

    public boolean isLast() {
        return pageHelper.isLastPage();
    }

    public Collection<T> loadPageDatas() {
        if(this.pageHelper == null) {
            int totalRecord = dataLoader.selectTotalCount();
            this.pageHelper = new PageHelper(totalRecord, this.pageSize);
        }

        long start = System.currentTimeMillis();

        int currentPage = this.pageHelper.getNextPageNum();
        Collection<T> datas = dataLoader.load(currentPage, pageSize);

        long end = System.currentTimeMillis();
        LOG.info(new StringBuilder().append("load date in page number: ")
                .append(currentPage).append(", page data size: ").append(datas.size())
                .append(", use total time: ").append(end - start).append("ms").toString());
        return datas;
    }

    @Override
    public Collection<T> getDatas() {
        throw new UnsupportedOperationException("Unsupported operation, use #loadPageDatas method for load data.");
    }

    @Override
    public void fillDatas(Collection<?> datas) {
        throw new UnsupportedOperationException("Must fill data in constructor.");
    }

}
