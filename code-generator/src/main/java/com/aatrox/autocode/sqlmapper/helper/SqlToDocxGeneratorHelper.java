package com.aatrox.autocode.sqlmapper.helper;

import com.aatrox.autocode.base.helper.BaseHelper;
import com.aatrox.autocode.sqlmapper.param.DBConnectParam;
import com.aatrox.autocode.sqlmapper.param.SchemaInfo;
import com.aatrox.autocode.sqlmapper.param.SqlModelDefine;
import com.aatrox.autocode.sqlmapper.util.DBConnectUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class SqlToDocxGeneratorHelper extends BaseHelper {
    //数据库参数
    private DBConnectParam dbParam;
    //数据库名称
    private String dbName;
    //表名
    private String tableName;
    /**
     * 生成路径
     **/
    public final static String CONF_DIR_PATH = System.getProperty("user.home") + File.separator + "byb.docx";

    public SqlToDocxGeneratorHelper() {
    }

    public SqlToDocxGeneratorHelper(DBConnectParam dbParam, String dbName, String tableName) {
        this.dbParam = dbParam;
        this.dbName = dbName;
        this.tableName = tableName;
    }

    public void doMapper() throws Exception {
        Resource resource = new ClassPathResource("sqlToDocx/template.docx");
        //读取文档内容
        XWPFDocument document = new XWPFDocument(resource.getInputStream());
        XWPFTable root = document.getTables().get(0);
        System.out.println(toString());
        SchemaInfo scheme = DBConnectUtil.getSchemaInfo(dbParam, dbName, tableName);
        List<SqlModelDefine> columnList = scheme.getColumnList();
        System.out.println("开始生成数据库描述表格...");
        //设置表名
        String title = (!StringUtils.isEmpty(dbName) ? dbName + "." : "") + tableName;
        if (!StringUtils.isEmpty(scheme.getTableDesc())) {
            title = title + "(" + scheme.getTableDesc() + ")";
        }
        root.getRows().get(0).getCell(1).setText(title);
        int columnSize = root.getRows().get(2).getTableCells().size();
        int rowNum = 2;
        for (int i = 0; i < columnList.size(); i++) {
            if (i != 0) {
                addNewRow(root, columnSize);
            }
            SqlModelDefine modelDefine = columnList.get(i);
            XWPFTableRow row = root.getRow(rowNum);
            row.getCell(0).setText(modelDefine.getColumnName());
            row.getCell(1).setText(modelDefine.getColumnType());
            row.getCell(2).setText(modelDefine.isPrimaryKey() ? "√" : "");
            row.getCell(3).setText(!modelDefine.isCanNullAble() ? "√" : "");
            row.getCell(4).setText(modelDefine.isIndexKey() ? "√" : "");
            row.getCell(5).setText(modelDefine.getComment());
            rowNum++;
        }
        makeFile(document);
        System.out.println("开始生成数据库描述表格...DONE");
        System.out.println();
        System.out.println("DONE...");
        System.out.println("文档地址为:" + CONF_DIR_PATH);
        super.openRoot(CONF_DIR_PATH);
    }

    private void makeFile(XWPFDocument document) throws IOException {
        File root = new File(CONF_DIR_PATH);
        if (!root.exists()) {
            root.mkdir();
        }
        File file = new File(CONF_DIR_PATH + File.separator + tableName + ".docx");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        document.write(new FileOutputStream(file));
    }

    private void addNewRow(XWPFTable root, int colSize) {
        XWPFTableRow targetRow = new XWPFTableRow(root.getCTTbl().addNewTr(), root);
        for (int i = 0; i < colSize; i++) {
            targetRow.createCell();
        }
        root.getRows().add(targetRow);
    }

    @Override
    public String toString() {
        return "SqlToDocxGeneratorHelper{" +
                "dbParam=" + dbParam +
                ", dbName='" + dbName + '\'' +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}
