package com.aatrox.autocode.sqlmapper.test;

import com.aatrox.autocode.sqlmapper.helper.SqlToDocxGeneratorHelper;
import com.aatrox.autocode.sqlmapper.helper.SqlToModelGeneratorHelper;
import com.aatrox.autocode.sqlmapper.param.DBConnectParam;
import com.aatrox.autocode.sqlmapper.param.DBType;
import org.junit.Test;

public class GeneratorTest {


    @Test
    public void testMysqlHelper() throws Exception {
        DBConnectParam dbConnectParam = new DBConnectParam(DBType.MYSQL, "root", "123456", "jdbc:mysql://localhost:3306/quartz");
        SqlToModelGeneratorHelper model = new SqlToModelGeneratorHelper(dbConnectParam, "quartz",
                "T_APP_QUARTZ", "", "com.aatrox.quartzserver",
                "com.aatrox.quartzserver",
                "AppQuartz", "定时器实体类", "byb");
        model.setChainProperty(true);
        model.setNeedOpen(true);
        model.setActualFieldName(true);
        model.autoGenerate();
    }

    @Test
    public void testOracleHelper() throws Exception {
        DBConnectParam dbConnectParam = new DBConnectParam(DBType.ORACLE, "saas_newhouse", "saas_newhouse",
                "jdbc:oracle:thin:@ (DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST = 192.168.0.44)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = qftest44)))");
        SqlToModelGeneratorHelper model = new SqlToModelGeneratorHelper(dbConnectParam, "saas_newhouse",
                "T_NEWHOUSE_ACCESSORY", "f", "com.saas.newhouse.service.garden",
                "saas.newhouse.web",
                "Accessory", "附件", "byb");
        model.setChainProperty(true);
        model.autoGenerate();
    }

    @Test
    public void testSqlToDocx() throws Exception {
        DBConnectParam dbConnectParam = new DBConnectParam(DBType.ORACLE, "saas_newhouse", "saas_newhouse",
                "jdbc:oracle:thin:@ (DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST = 192.168.0.44)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = qftest44)))");
        SqlToDocxGeneratorHelper docx = new SqlToDocxGeneratorHelper(dbConnectParam, "", "T_NEWHOUSE_ACCESSORY");
        docx.doMapper();
    }

}
