package com.aatrox;

import java.util.HashMap;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class ContextHolder {
    private static final ThreadLocal<Map<String, Object>> contextHolder = new ThreadLocal();

    public ContextHolder() {
    }

    public static void setDataSource(ContextHolder.DataSourceContextKey dataSource) {
        Map<String, Object> holder = getContextHolder();
        holder.put(ContextHolder.DataSourceContextKey.class.getName(), dataSource);
    }

    public static String getDataSource() {
        Map<String, Object> holder = getContextHolder();
        Object dataSource = holder.get(ContextHolder.DataSourceContextKey.class.getName());
        return dataSource == null ? ContextHolder.DataSource.AATROX_CLOUD.toString() : dataSource.toString();
    }

    public static ContextHolder.DataSourceContextKey getDataSourceKey() {
        Map<String, Object> holder = getContextHolder();
        ContextHolder.DataSourceContextKey dataSource = (ContextHolder.DataSourceContextKey) holder.get(ContextHolder.DataSourceContextKey.class.getName());
        return (ContextHolder.DataSourceContextKey) (dataSource == null ? ContextHolder.DataSource.AATROX_CLOUD : dataSource);
    }

    public static void setContextData(Class<? extends ContextHolder.ContextKey> type, Object data) {
        Map<String, Object> holder = getContextHolder();
        holder.put(type.getName(), data);
    }

    public static Object getContextData(Class<? extends ContextHolder.ContextKey> type) {
        Map<String, Object> holder = getContextHolder();
        return holder.get(type.getName());
    }

    public static Object removeContextData(Class<? extends ContextHolder.ContextKey> type) {
        Map<String, Object> holder = getContextHolder();
        return holder.remove(type.getName());
    }

    private static Map<String, Object> getContextHolder() {
        Map<String, Object> holder = (Map) contextHolder.get();
        if (holder == null) {
            holder = new HashMap();
            contextHolder.set(holder);
        }

        return (Map) holder;
    }

    static enum DataSource implements ContextHolder.DataSourceContextKey {
        AATROX_CLOUD;

        private DataSource() {
        }
    }

    public interface DataSourceContextKey extends ContextHolder.ContextKey {
    }

    public interface ContextKey {
    }
}

