package com.aatrox.autocode.sqlmapper.param;

public class DBConnectParam {
    //数据库类型
    private DBType dbType;
    //用户名
    private String username;
    //密码
    private String password;
    //连接串
    private String url;

    public DBConnectParam() {
    }

    public DBConnectParam(DBType dbType, String username, String password, String url) {
        this.dbType = dbType;
        this.username = username;
        this.password = password;
        this.url = url;
    }

    public DBType getDbType() {
        return dbType;
    }

    public DBConnectParam setDbType(DBType dbType) {
        this.dbType = dbType;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public DBConnectParam setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public DBConnectParam setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public DBConnectParam setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public String toString() {
        return "数据库连接：{" +
                "dbType=" + dbType +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
