package com.aatrox.generator.enums;

/**
 * 数据库类型
 */
public enum DBType {
    MYSQL("com.mysql.jdbc.Driver"),
    ORACLE("oracle.jdbc.driver.OracleDriver"),
    SQLSERVER("");

    private String driver;

    DBType(String driver) {
        this.driver = driver;
    }

    public String getDriver() {
        return driver;
    }

    public DBType setDriver(String driver) {
        this.driver = driver;
        return this;
    }
}
