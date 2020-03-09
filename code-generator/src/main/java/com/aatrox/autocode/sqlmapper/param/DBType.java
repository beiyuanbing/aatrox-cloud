package com.aatrox.autocode.sqlmapper.param;

import java.util.Map;

public enum DBType {
    MYSQL("com.mysql.jdbc.Driver", DBTypeConstants.mysqlTypeMap),
    ORACLE("oracle.jdbc.driver.OracleDriver", DBTypeConstants.oracleTypeMap),
    SQLSERVER("", null);

    private String driver;
    private Map<String, String> typeMap;

    DBType(String driver, Map<String, String> typeMap) {
        this.driver = driver;
        this.typeMap = typeMap;
    }

    public String getDriver() {
        return driver;
    }

    public DBType setDriver(String driver) {
        this.driver = driver;
        return this;
    }

    public Map<String, String> getTypeMap() {
        return typeMap;
    }

    public DBType setTypeMap(Map<String, String> typeMap) {
        this.typeMap = typeMap;
        return this;
    }
}
