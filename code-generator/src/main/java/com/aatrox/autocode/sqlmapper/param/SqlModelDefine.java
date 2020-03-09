package com.aatrox.autocode.sqlmapper.param;

/**
 * sql语言定义
 */
public class SqlModelDefine {
    //字段名
    private String columnName;
    //字段类型
    private String dataType;
    //是否主键
    private boolean primaryKey;
    //注释
    private String comment;
    //是否允许为空
    private boolean canNullAble;
    //字段拓展
    private String columnType;
    //是否是索引字段
    private boolean indexKey;

    public String getColumnName() {
        return columnName;
    }

    public SqlModelDefine setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public String getDataType() {
        return dataType;
    }

    public SqlModelDefine setDataType(String dataType) {
        this.dataType = dataType;
        return this;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public SqlModelDefine setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public SqlModelDefine setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public boolean isCanNullAble() {
        return canNullAble;
    }

    public SqlModelDefine setCanNullAble(boolean canNullAble) {
        this.canNullAble = canNullAble;
        return this;
    }

    public String getColumnType() {
        return columnType;
    }

    public SqlModelDefine setColumnType(String columnType) {
        this.columnType = columnType;
        return this;
    }

    public boolean isIndexKey() {
        return indexKey;
    }

    public SqlModelDefine setIndexKey(boolean indexKey) {
        this.indexKey = indexKey;
        return this;
    }

    @Override
    public String toString() {
        return "字段信息{" +
                "cloumnName='" + columnName + '\'' +
                ", dataType='" + dataType + '\'' +
                ", primaryKey=" + primaryKey +
                ", comment='" + comment + '\'' +
                ", canNullAble='" + canNullAble + '\'' +
                ", columnType='" + columnType + '\'' +
                ", indexKey='" + indexKey + '\'' +
                '}';
    }
}
