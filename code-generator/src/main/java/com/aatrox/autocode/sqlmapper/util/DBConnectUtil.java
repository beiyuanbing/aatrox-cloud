package com.aatrox.autocode.sqlmapper.util;

import com.aatrox.autocode.sqlmapper.param.DBConnectParam;
import com.aatrox.autocode.sqlmapper.param.DBType;
import com.aatrox.autocode.sqlmapper.param.SchemaInfo;
import com.aatrox.autocode.sqlmapper.param.SqlModelDefine;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBConnectUtil {
    /**
     * 获取所有的信息
     *
     * @param dbConnectParam
     * @param dbName
     * @param tableName
     * @return
     */
    public static SchemaInfo getSchemaInfo(DBConnectParam dbConnectParam, String dbName, String tableName) {
        SchemaInfo schemaInfo = null;
        Connection connection = null;
        try {
            connection = getConnection(dbConnectParam.getDbType(), dbConnectParam.getUsername(), dbConnectParam.getPassword(), dbConnectParam.getUrl());
            switch (dbConnectParam.getDbType()) {
                case MYSQL:
                    schemaInfo = getSchemaInfoFromMysql(connection, dbName, tableName);
                    break;
                case ORACLE:
                    schemaInfo = getSchemaInfoFromOracle(connection, dbName, tableName);
                    break;
                case SQLSERVER:
                    schemaInfo = getSchemaInfoFromSqlServer(connection, dbName, tableName);
                    break;
                default:
                    System.out.println("暂不支持其他数据库");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭数据库连接");
            closeConnect(connection);
        }
        System.out.println("获得表数据库结构---done");
        return schemaInfo;
    }

    /**
     * 从mysql获取表结构
     *
     * @param connection
     * @return
     */
    private static SchemaInfo getSchemaInfoFromMysql(Connection connection, String dbName, String tableName) {
        if (connection == null) {
            return null;
        }
        SchemaInfo schemaInfo = new SchemaInfo();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        List<SqlModelDefine> cloumnList = new ArrayList<>();
        try {
            //设置表名
            schemaInfo.setTableName(tableName);
            //获取表的comments
            String sql = String.format("select TABLE_COMMENT from information_schema.TABLES where TABLE_SCHEMA='%s' and table_name='%s'", dbName, tableName);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                schemaInfo.setTableDesc(resultSet.getString("TABLE_COMMENT"));
            }
            //获取表的字段值
            sql = String.format("select * from information_schema.columns  where TABLE_SCHEMA='%s' and table_name='%s' order by ordinal_position", dbName, tableName);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SqlModelDefine sqlModelDefine = new SqlModelDefine()
                        .setColumnName(resultSet.getString("COLUMN_NAME"))
                        .setDataType(resultSet.getString("DATA_TYPE"))
                        .setComment(resultSet.getString("COLUMN_COMMENT"))
                        .setPrimaryKey("PRI".equals(resultSet.getString("COLUMN_KEY")))
                        .setCanNullAble(getIsNullAble(resultSet.getString("IS_NULLABLE")));
                //设置主键信息
                if ("PRI".equals(resultSet.getString("COLUMN_KEY"))) {
                    schemaInfo.setPrimaryKeyField(sqlModelDefine);
                }
                cloumnList.add(sqlModelDefine);
            }
            schemaInfo.setColumnList(cloumnList);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeJdbcStatement(preparedStatement, resultSet);
        }
        return schemaInfo;
    }

    /**
     * 从Oracle获取表结构
     *
     * @param connection
     * @return
     */
    private static SchemaInfo getSchemaInfoFromOracle(Connection connection, String dbName, String tableName) {
        if (connection == null) {
            return null;
        }
        SchemaInfo schemaInfo = new SchemaInfo();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        List<SqlModelDefine> cloumnList = new ArrayList<>();
        try {
            //设置表名
            schemaInfo.setTableName(tableName);
            //获取表的comments
            String sql = String.format("select COMMENTS from user_tab_comments where table_name='%s' ", tableName);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                schemaInfo.setTableDesc(resultSet.getString("COMMENTS"));
            }
            //获取表的字段值
            sql = String.format("select utc.DATA_LENGTH,utc.DATA_PRECISION,utc.DATA_SCALE,utc.NULLABLE,utc.DATA_TYPE,utc.COLUMN_NAME," +
                    "utc.COLUMN_ID,ucc.COMMENTS " +
                    "from user_tab_columns utc\n" +
                    "left join user_col_comments ucc on utc.table_name=ucc.table_name and utc.column_name = ucc.column_name\n" +
                    "where utc.table_name='%s' order by utc.COLUMN_ID", tableName);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            Map<String, SqlModelDefine> temp = new HashMap<>();
            while (resultSet.next()) {
                SqlModelDefine sqlModelDefine = new SqlModelDefine()
                        .setColumnName(resultSet.getString("COLUMN_NAME"))
                        .setDataType(resultSet.getString("DATA_TYPE"))
                        .setComment(resultSet.getString("COMMENTS"))
                        .setCanNullAble("Y".equals(resultSet.getString("NULLABLE")));
                String dataLength = resultSet.getString("DATA_LENGTH");
                String dataPrecision = resultSet.getString("DATA_PRECISION");
                String dataScale = resultSet.getString("DATA_SCALE");
                if (!StringUtils.isEmpty(dataPrecision)) {
                    sqlModelDefine.setColumnType(sqlModelDefine.getDataType() + "(" + dataPrecision + "," + dataScale + ")");
                } else {
                    sqlModelDefine.setColumnType(sqlModelDefine.getDataType() + "(" + dataLength + ")");
                }
                temp.put(sqlModelDefine.getColumnName(), sqlModelDefine);
                cloumnList.add(sqlModelDefine);
            }
            //判断索引是否为索引
            sql = String.format("select t.*,i.index_type from user_ind_columns t,user_indexes i \n" +
                    "where t.index_name = i.index_name and t.table_name = i.table_name and t.table_name = '%s'", tableName);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String column = resultSet.getString("COLUMN_NAME");
                SqlModelDefine def = temp.get(column);
                if (def != null) {
                    def.setIndexKey(true);
                }
            }
            //判断主键使用
            sql = String.format("select cu.* from user_cons_columns cu, user_constraints au \n" +
                    "where cu.constraint_name = au.constraint_name and au.constraint_type = 'P' and au.table_name = '%s'", tableName);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String column = resultSet.getString("COLUMN_NAME");
                SqlModelDefine def = temp.get(column);
                if (def != null) {
                    def.setPrimaryKey(true);
                    schemaInfo.setPrimaryKeyField(def);
                }
            }
            schemaInfo.setColumnList(cloumnList);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeJdbcStatement(preparedStatement, resultSet);
        }
        return schemaInfo;
    }

    /**
     * SqlServer的数据库
     *
     * @param connection
     * @param dbName
     * @param tableName
     * @return
     */
    private static SchemaInfo getSchemaInfoFromSqlServer(Connection connection, String dbName, String tableName) {
        System.out.println("暂时不支持mysql和oracle外的数据库");
        return null;
    }

    /**
     * mysql判断是否为bool
     *
     * @param isNullAble
     * @return
     */
    private static boolean getIsNullAble(String isNullAble) {
        return "NO".equals(isNullAble) ? false : true;
    }

    private static void closeJdbcStatement(PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
            }
        }
    }
    /**
     * 获取数据库连接
     *
     * @param dbType
     * @param username
     * @param password
     * @param url
     * @return
     */
    public static Connection getConnection(DBType dbType, String username, String password, String url) {
        try {
            Class.forName(dbType.getDriver());
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void closeConnect(Connection conn) {
        // 关闭数据库连接
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    public static void main(String[] args) {
        DBConnectParam dbConnectParam = new DBConnectParam(DBType.MYSQL, "root", "123456", "jdbc:mysql://localhost:3306/order");
        SchemaInfo schemaInfo = DBConnectUtil.getSchemaInfo(dbConnectParam, "order", "t_order_info");
        System.out.println(schemaInfo);
      /*  DBConnectParam dbConnectParam = new DBConnectParam(DBType.ORACLE,
                "saas_newhouse",
                "saas_newhouse", "jdbc:oracle:thin:@ (DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST = 192.168.0.44)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = qftest44)))");
        SchemaInfo schemaInfo = DBConnectUtil.getSchemaInfo(dbConnectParam, "saas_newhouse", "T_NEWHOUSE_ACCESSORY");
        System.out.println(schemaInfo);*/
    }
}
