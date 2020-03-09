package com.aatrox.threads;

import com.aatrox.ContextHolder;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/2
 */
public abstract class LocalRunnable implements Runnable {
    private ContextHolder.DataSourceContextKey dataSource = ContextHolder.getDataSourceKey();

    public LocalRunnable() {
    }

    @Override
    public void run() {
        ContextHolder.setDataSource(this.dataSource);
        this.doRun();
    }

    protected abstract void doRun();
}