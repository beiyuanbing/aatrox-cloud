package com.aatrox.autocode.sqlmapper.param;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库类型对应字段
 */
public class DBTypeConstants {
    static final Map<String, String> mysqlTypeMap = new HashMap();
    static final Map<String, String> oracleTypeMap = new HashMap();

    static {
        mysqlTypeMap.put("TINYINT", "Integer");
        mysqlTypeMap.put("SMALLINT", "Integer");
        mysqlTypeMap.put("MEDIUMINT", "Integer");
        mysqlTypeMap.put("INT", "Integer");
        mysqlTypeMap.put("INTEGER", "Integer");
        mysqlTypeMap.put("BIGINT", "Long");
        mysqlTypeMap.put("FLOAT", "BigDecimal");
        mysqlTypeMap.put("DOUBLE", "BigDecimal");
        mysqlTypeMap.put("DECIMAL", "BigDecimal");

        mysqlTypeMap.put("DATE", "Date");
        mysqlTypeMap.put("TIME", "Date");
        mysqlTypeMap.put("YEAR", "Date");
        mysqlTypeMap.put("DATETIME", "Date");
        mysqlTypeMap.put("TIMESTAMP", "Date");

        mysqlTypeMap.put("CHAR", "String");
        mysqlTypeMap.put("VARCHAR", "String");
        mysqlTypeMap.put("TINYBLOB", "String");
        mysqlTypeMap.put("TINYTEXT", "String");
        mysqlTypeMap.put("BLOB", "String");
        mysqlTypeMap.put("TEXT", "String");
        mysqlTypeMap.put("MEDIUMBLOB", "String");
        mysqlTypeMap.put("MEDIUMTEXT", "String");
        mysqlTypeMap.put("LONGBLOB", "String");
        mysqlTypeMap.put("LONGTEXT", "String");
    }

    static {
        oracleTypeMap.put("TIMESTAMP(6)", "Date");
        oracleTypeMap.put("TIMESTAMP", "Date");
        oracleTypeMap.put("DATE", "Date");

        oracleTypeMap.put("FLOAT", "BigDecimal");
        oracleTypeMap.put("NUMBER", "BigDecimal");

        oracleTypeMap.put("LONG", "String");
        oracleTypeMap.put("CLOB", "String");
        oracleTypeMap.put("CHAR", "String");
        oracleTypeMap.put("UNDEFINED", "String");
        oracleTypeMap.put("NCLOB", "String");
        oracleTypeMap.put("VARCHAR2", "String");
        oracleTypeMap.put("BLOB", "String");
    }
}
