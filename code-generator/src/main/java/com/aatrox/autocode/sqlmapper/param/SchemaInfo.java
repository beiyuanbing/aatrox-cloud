package com.aatrox.autocode.sqlmapper.param;

import java.util.List;

public class SchemaInfo {
    //表名
    private String tableName;
    //表字描述
    private String tableDesc;
    //字段数据
    private List<SqlModelDefine> columnList;
    //主键信息
    private SqlModelDefine primaryKeyField;


    public String getTableName() {
        return tableName;
    }

    public SchemaInfo setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public List<SqlModelDefine> getColumnList() {
        return columnList;
    }

    public SchemaInfo setColumnList(List<SqlModelDefine> columnList) {
        this.columnList = columnList;
        return this;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public SchemaInfo setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
        return this;
    }

    public SqlModelDefine getPrimaryKeyField() {
        return primaryKeyField;
    }

    public SchemaInfo setPrimaryKeyField(SqlModelDefine primaryKeyField) {
        this.primaryKeyField = primaryKeyField;
        return this;
    }

    @Override
    public String toString() {
        return "SchemaInfo{" +
                "tableName='" + tableName + '\'' +
                ", tableDesc='" + tableDesc + '\'' +
                ", columnList=" + columnList +
                ", primaryKeyField=" + primaryKeyField +
                '}';
    }
}
