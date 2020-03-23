package com.aatrox.common.excel.base;

import java.util.Collection;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public interface DataLoader<T> {

    /**
     * 返回总记录数
     * @return
     */
    int selectTotalCount();

    /**
     * 分页加载
     * @param pageNum
     * @param pageSize
     * @return
     */
    Collection<T> load(int pageNum, int pageSize);

}
