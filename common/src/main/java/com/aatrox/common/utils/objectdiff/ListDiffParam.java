package com.aatrox.common.utils.objectdiff;


import java.util.List;

public class ListDiffParam extends DiffParam {
    /**
     * 当前对象
     */
    private Object entity;

    /**
     * 历史对象
     */
    private Object historyEntity;

    /**
     * 包含对象字段
     */
    private List<String> includeField;
    /**
     * 不包含对象字段
     */
    private List<String> excludeField;
    /**
     * 判断数据的标识，同条数据的标识,如果没有标识不作处理比较结果
     * 还有个进行重写equals方法的办法，但是太麻烦，没用通用性。
     */
    private String identityField;
    /**
     * 实体的描述
     */
    private String entityDesc;
    /**
     * 是否需要制表符
     */
    private boolean tableSymbol = false;

    public String getIdentityField() {
        return identityField;
    }

    public ListDiffParam setIdentityField(String identityField) {
        this.identityField = identityField;
        return this;
    }

    public String getEntityDesc() {
        return entityDesc;
    }

    public ListDiffParam setEntityDesc(String entityDesc) {
        this.entityDesc = entityDesc;
        return this;
    }

    public boolean isTableSymbol() {
        return tableSymbol;
    }

    public ListDiffParam setTableSymbol(boolean tableSymbol) {
        this.tableSymbol = tableSymbol;
        return this;
    }

    @Override
    public Object getEntity() {
        return entity;
    }

    @Override
    public ListDiffParam setEntity(Object entity) {
        this.entity = entity;
        return this;
    }

    @Override
    public Object getHistoryEntity() {
        return historyEntity;
    }

    @Override
    public ListDiffParam setHistoryEntity(Object historyEntity) {
        this.historyEntity = historyEntity;
        return this;
    }

    @Override
    public List<String> getIncludeField() {
        return includeField;
    }

    @Override
    public ListDiffParam setIncludeField(List<String> includeField) {
        this.includeField = includeField;
        return this;
    }

    @Override
    public List<String> getExcludeField() {
        return excludeField;
    }

    @Override
    public ListDiffParam setExcludeField(List<String> excludeField) {
        this.excludeField = excludeField;
        return this;
    }
}
