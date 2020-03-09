package com.aatrox.threads;

import com.aatrox.ContextHolder;

import java.util.concurrent.Callable;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/2
 */
public abstract class LocalCallable<V> implements Callable<V> {
    private ContextHolder.DataSourceContextKey dataSource = ContextHolder.getDataSourceKey();

    public LocalCallable() {
    }

    @Override
    public V call() {
        ContextHolder.setDataSource(this.dataSource);
        return this.doCall();
    }

    protected abstract V doCall();
}

