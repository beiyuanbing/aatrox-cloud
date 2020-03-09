package com.aatrox.autocode.sqlmapper.helper;

import com.aatrox.autocode.base.helper.BaseHelper;
import com.aatrox.autocode.sqlmapper.param.DBConnectParam;
import com.aatrox.autocode.sqlmapper.param.DBType;
import com.aatrox.autocode.sqlmapper.param.SchemaInfo;
import com.aatrox.autocode.sqlmapper.param.SqlModelDefine;
import com.aatrox.autocode.sqlmapper.util.DBConnectUtil;
import com.aatrox.common.utils.CommonStringUtil;
import com.aatrox.common.utils.MapUtil;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.aatrox.autocode.sqlmapper.util.AutoCodeUtils.*;

public class SqlToModelGeneratorHelper extends BaseHelper {
    //数据库参数
    private DBConnectParam dbParam;
    //model包名
    private String packageName;
    //web包名
    private String webPackageName;
    //数据库名称
    private String dbName;
    //表名
    private String tableName;
    //字段前缀
    private String columnPrefix;
    //对象名
    private String objectName;
    private String objectNameSmall;
    private String objectNameBig;
    //代码注释描述
    private String desc;
    private String author;
    //是否驼峰写法
    private boolean underLineToCamelCase = true;
    //xml的dao路径
    private boolean xmlModelWithPackagePath = true;
    //是否原来的字段
    private boolean actualFieldName = false;
    //生成目录
    private String rootDir;
    //是否链式
    private boolean isChainProperty = true;
    //是否生成model的toString方法
    private boolean generateToStringMethod = true;
    //是否需要parent
    private boolean isNeedSuperClass = false;

    /**
     * 生成路径
     **/
    public final static String CONF_DIR_PATH = System.getProperty("user.home") + File.separator + "byb.autocode";

    public SqlToModelGeneratorHelper() {
    }

    public SqlToModelGeneratorHelper(DBConnectParam dbParam, String dbName, String tableName,
                                     String columnPrefix, String packageName, String webPackageName,
                                     String objectName, String desc, String author) {
        this.dbParam = dbParam;
        this.packageName = packageName;
        this.webPackageName = webPackageName;
        this.dbName = dbName;
        this.tableName = tableName;
        this.columnPrefix = columnPrefix;
        this.objectName = objectName;
        this.desc = desc;
        this.author = author;
        this.objectNameSmall = (objectName.charAt(0) + "").toLowerCase() + objectName.substring(1);
        this.objectNameBig = CommonStringUtil.toUpperCase(objectName, true);
    }

    public void autoGenerate() throws IOException {
        System.out.println(toString());
        //先获取表结构
        SchemaInfo schemaInfo = DBConnectUtil.getSchemaInfo(dbParam, dbName, tableName);
        //生成根目录
        makeRoot();
        //生成对象结构
        makeObject(schemaInfo);
        //生成模板文件
        makeTemplate();
        //生成Dao.xml
        makeMyBatisXml(schemaInfo);
        System.out.println("生成完成");
        System.out.println("目路地址为：" + rootDir);
        super.openRoot(rootDir);
    }

    /**
     * 生成对象结构
     */
    public void makeObject(SchemaInfo schemaInfo) throws IOException {
        List<SqlModelDefine> columnList = schemaInfo.getColumnList();
        if (columnList == null || columnList.size() == 0) {
            return;
        }
        StringBuffer modelAll = new StringBuffer();
        modelAll.append(String.format("package %s.apilist.model;\n\n", packageName));
        StringBuffer formAll = new StringBuffer();
        formAll.append(String.format("package %s.apilist.form;\n\n", packageName));

        //相关的import
        StringBuffer modelImport = new StringBuffer();
        StringBuffer formImport = new StringBuffer();
        StringBuffer modelFieldSb = new StringBuffer();
        StringBuffer formFieldSb = new StringBuffer();
        //getSet方法的buffer;
        StringBuffer getsetMethodSb = new StringBuffer();
        //toString的buffer
        StringBuffer toStringBuffer = new StringBuffer();

        toStringBuffer.append(objectName + "{");
        int index = 0;
        for (SqlModelDefine dbField : columnList) {
            if (this.isNeedSuperClass && index == 0) {
                index++;
                continue;
            }
            String objectField = getObjectString(dbField);
            String fieldType = getType(dbField.getDataType(),dbField.getColumnType(), dbParam.getDbType());
            //得到导入的字段
            modelImport.append(this.getImport(fieldType));
            formImport.append(this.getImport(fieldType));

            //field
            modelFieldSb.append(String.format("\t@ApiModelProperty(value = \"%s\")\n", dbField.getComment()));
            modelFieldSb.append(String.format("\tprivate %s %s;\n\n", fieldType, objectField));
            formFieldSb.append(String.format("\t@ApiModelProperty(value = \"%s\"%s)\n", dbField.getComment(), dbField.isCanNullAble() ? "" : ",required = true"));
            if (!dbField.isCanNullAble()) {
                formFieldSb.append(String.format("\t%s(message = \"请输入%s\")\n",
                        fieldType.equals("String") ? "@NotBlank" : "@NotNull", dbField.getComment()));
                if (fieldType.equals("String")) {
                    formImport.append("import org.hibernate.validator.constraints.NotBlank;\n");
                } else {
                    formImport.append("import javax.validation.constraints.NotNull;\n");
                }
            }
            formFieldSb.append(String.format("\tprivate %s %s;\n\n", fieldType, objectField));
            //得到getset
            getsetMethodSb.append(this.makeGetterSetter(fieldType, objectField));
        }

        modelImport.append("import io.swagger.annotations.ApiModel;\n");
        formImport.append("import io.swagger.annotations.ApiModel;\n");
        modelImport.append("import io.swagger.annotations.ApiModelProperty;\n");
        formImport.append("import io.swagger.annotations.ApiModelProperty;\n");
        modelImport.append(getClassDesc("Model"));
        formImport.append(getClassDesc("保存Form"));
        //String apiModel = String.format("@ApiModel(value = \"%s\"%s)\n",desc,
        //        StringUtils.isNotBlank(scheme.getTableDesc())?",description = \""+scheme.getTableDesc()+"\"":"");
        String apiModel = String.format("@ApiModel(description = \"%s\")\n", schemaInfo.getTableDesc());
        modelImport.append(apiModel);

        // //需要superclass的进行特别处理，不需要的就直接全部输出
        if (!this.isNeedSuperClass) {
            modelImport.append(String.format("public class %sModel  {\n", objectName));
            formImport.append(String.format("public class %sInsertForm {\n", objectName));
        } else {
            //需要superclass的进行特别处理，不需要的就直接全部输出
            modelImport.append(String.format("public class %sModel extends Entity<String> {\n", this.objectName));
            formImport.append(String.format("public class %sInsertForm extends IdUnForm {\n", this.objectName));
        }

        modelAll.append(modelImport);
        modelAll.append(modelFieldSb).append("\n");
        modelAll.append(getsetMethodSb);
        modelAll.append(toStringGenerator(schemaInfo, objectName + "Model"));
        modelAll.append("}\n");

        formAll.append(formImport);
        formAll.append(formFieldSb).append("\n");
        formAll.append(getsetMethodSb);
        formAll.append(toStringGenerator(schemaInfo, objectName + "InsertForm"));
        formAll.append("}\n");
        String content = modelAll.toString();

        makeFile(rootDir, objectName + "Model.java", fillSetterFormate(content, objectName + "Model", columnList.size()));
        String formAllStr = formAll.toString();
        makeFile(rootDir, objectName + "InsertForm.java", fillSetterFormate(formAllStr, objectName + "InsertForm", columnList.size()));
        formAllStr = formAllStr.replaceAll("InsertForm", "EditForm");
        if (this.isNeedSuperClass) {
            formAllStr = formAllStr.replaceAll("IdUnForm", "IdForm");
        }
        //.replaceAll("IdUnForm", "IdForm");
        makeFile(rootDir, objectName + "EditForm.java", fillSetterFormate(formAllStr, objectName + "EditForm", columnList.size()));

    }

    /**
     * 填充setter
     *
     * @param content
     * @param modelName
     * @param size
     * @return
     */
    public String fillSetterFormate(String content, String modelName, int size) {
        if (!isChainProperty) {
            return content;
        }
        String[] tempArray = new String[size];
        while (size > 0) {
            tempArray[size - 1] = modelName;
            size--;
        }
        return String.format(content, tempArray);
    }

    /**
     * toString方法的生成器
     *
     * @return
     */
    private String TOSTRING_METHOD_TEMPLATE = "\t@Override\n\tpublic String toString() {\n";
    private String TOSTRING_TEMPLATE = "\n\t\t +\"{0}%s=\" + %s";

    public String toStringGenerator(SchemaInfo schemaInfo, String modelName) {
        if (schemaInfo == null || !generateToStringMethod) {
            return "";
        }
        StringBuilder toStringsb = new StringBuilder();
        toStringsb.append(TOSTRING_METHOD_TEMPLATE);
        toStringsb.append("\t\treturn \"%s{\"%s \n\t\t +\"}\";\n");
        toStringsb.append("\t}\n");
        String content = "";
        List<SqlModelDefine> columnList = schemaInfo.getColumnList();
        if (columnList != null && columnList.size() > 0) {
            for (int i = 0; i < columnList.size(); i++) {
                SqlModelDefine sqlModelDefine = columnList.get(i);
                String objectField = getObjectString(sqlModelDefine);
                content += String.format(MessageFormat.format(TOSTRING_TEMPLATE, i != 0 ? "," : ""), objectField, objectField);
            }
        }
        String result = String.format(toStringsb.toString(), modelName, content, modelName);
        return result;
    }

    //由模板生成文件
    private void makeTemplate() throws IOException {
        Map<String, String> param = MapUtil.NEW();
        param.put("rootPackage", packageName);
        param.put("webRootPackage", webPackageName);
        param.put("objectName", objectName);
        param.put("objectNameSmall", objectNameSmall);
        param.put("objectNameBig", objectNameBig);
        param.put("date", DATE_F);
        param.put("author", author);
        param.put("desc", desc);

        String content = readResource("sqlToModel/api.new");
        content = replacePlaceHolder(content, param);
        makeFile(rootDir, objectName + "Api.java", content);
        content = readResource("sqlToModel/controller.new");
        content = replacePlaceHolder(content, param);
        makeFile(rootDir, objectName + "ManagerController.java", content);
        content = readResource("sqlToModel/dao.new");
        content = replacePlaceHolder(content, param);
        makeFile(rootDir, objectName + "Dao.java", content);
        content = readResource("sqlToModel/feign.new");
        content = replacePlaceHolder(content, param);
        makeFile(rootDir, objectName + "Feign.java", content);
        content = readResource("sqlToModel/queryForm.new");
        content = replacePlaceHolder(content, param);
        makeFile(rootDir, objectName + "QueryForm.java", content);
        content = readResource("sqlToModel/remote.new");
        content = replacePlaceHolder(content, param);
        makeFile(rootDir, objectName + "ManagerRemote.java", content);
        content = readResource("sqlToModel/service.new");
        content = replacePlaceHolder(content, param);
        makeFile(rootDir, objectName + "Service.java", content);
    }

    /**
     * 生成xml
     */
    private void makeMyBatisXml(SchemaInfo scheme) throws IOException {
        List<SqlModelDefine> defs = scheme.getColumnList();
        //主键信息
        SqlModelDefine primaryKeyField = scheme.getPrimaryKeyField();
        //主键的字段信息
        String priObjectField = primaryKeyField == null ? "id" : getObjectString(primaryKeyField);
        //数据类型
        String fieldType = primaryKeyField == null ? "string" : getType(primaryKeyField.getDataType(),primaryKeyField.getColumnType(), dbParam.getDbType()).toLowerCase();
        //数据库字段
        String sqlFieldName = primaryKeyField == null ? "FID" : primaryKeyField.getColumnName();

        String modelPackagePath = String.format("%s.apilist.model.", packageName);
        String modelName = (xmlModelWithPackagePath ? modelPackagePath : "") + objectName + "Model";
        StringBuffer all = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \n" +
                "\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n\n");
        StringBuffer sbMap = new StringBuffer(String.format("<mapper namespace=\"%s.dao.automapper.%sDao\">\n",
                packageName, objectName));
        sbMap.append(String.format("\t<resultMap id=\"%sModelResult\" type=\"%s\">\n", objectName, modelName));

        String selectById = String.format("\t<select id=\"selectById\" parameterType=\"%s\" " +
                "resultMap=\"%sModelResult\">\n" +
                "\t\tselect *\n" +
                "\t\tfrom %s.%s\n" +
                "\t\twhere %s=#{%s}\n" +
                "\t</select>\n\n", fieldType, objectName, dbName, tableName, sqlFieldName, priObjectField);

        String delete = String.format("\t<update id=\"deleteById\" parameterType=\"%s\">\n" +
                "\t\tupdate %s.%s\n" +
                "\t\tset FSTATUS='DELETED'\n" +
                "\t\twhere %s=#{%s}\n" +
                "\t</update>\n", fieldType, dbName, tableName, sqlFieldName, priObjectField);

        String queryPage = String.format("\t<select id=\"query%sPage\" " +
                "parameterType=\"%s.apilist.form.%sQueryForm\" " +
                "resultMap=\"%sModelResult\">\n" +
                "\t\tselect *\n" +
                "\t\tfrom %s.%s\n" +
                "\t\twhere 1=1\n" +
                "\t\t<if test=\"\">\n" +
                "\t\t\tand 1=1\n" +
                "\t\t</if>\n" +
                "\t</select>\n", objectName, packageName, objectName, objectName, dbName, tableName);

        String insertFormClass = packageName + ".apilist.form." + objectName + "InsertForm";
        String editFormClass = packageName + ".apilist.form." + objectName + "EditForm";
        StringBuffer sbInsert = new StringBuffer(
                String.format("\t<insert id=\"insert%s\" parameterType=\"%s\">\n", objectName, insertFormClass));
        sbInsert.append(String.format("\t\tinsert into %s.%s(", dbName, tableName));

        StringBuffer sbInsertValues = new StringBuffer(" values(");

        StringBuffer sbUpdate = new StringBuffer(
                String.format("\t<update id=\"update%s\" parameterType=\"%s\">\n", objectName, editFormClass));
        sbUpdate.append(String.format("\t\tupdate %s.%s\n\t\t<set>\n", dbName, tableName));

        String pKeyColumn = "FID";
        for (int i = 0; i < defs.size(); i++) {
            SqlModelDefine dbField = defs.get(i);
            String objectField = getObjectString(dbField);
            if (dbField.isPrimaryKey()) {
                sbMap.append(String.format("\t\t<id property=\"%s\" column=\"%s\"/>\n", objectField, dbField.getColumnName()));
                pKeyColumn = dbField.getColumnName();
            } else {
                sbMap.append(String.format("\t\t<result property=\"%s\" column=\"%s\"/>\n", objectField, dbField.getColumnName()));
            }
            sbInsert.append(dbField.getColumnName()).append(",");
            sbInsertValues.append("#{").append(objectField).append("},");
            if (!dbField.isPrimaryKey()) {
                sbUpdate.append(String.format("\t\t\t<if test=\"%s!=null\">%s=#{%s},</if>\n",
                        objectField, dbField.getColumnName(), objectField));
            }
        }
        sbMap.append("\t</resultMap>\n\n");

        sbInsert.setCharAt(sbInsert.length() - 1, ')');
        sbInsertValues.setCharAt(sbInsertValues.length() - 1, ')');
        sbInsert.append("\n\t\t\t").append(sbInsertValues).append("\n");
        sbInsert.append("\t</insert>\n\n");

        sbUpdate.append("\t\t</set>\n\t\twhere " + pKeyColumn + "=#{id}\n");
        sbUpdate.append("\t</update>\n\n");
        all.append(sbMap).append(selectById).append(queryPage)
                .append(sbInsert).append(sbUpdate).append(delete).append("\n</mapper>");
        makeFile(rootDir, objectName + "Dao.xml", all.toString());
    }

    /**
     * 获取导入的class
     *
     * @param fieldType
     * @return
     */
    public String getImport(String fieldType) {
        String importClass = "";
        if (fieldType.equals("BigDecimal")) {
            importClass = "import java.math.BigDecimal;\n";
        } else if (fieldType.equals("Integer") || fieldType.equals("Long")) {
            importClass = "import java.lang.*;\n";
        } else if (fieldType.equals("Date")) {
            importClass = "import java.util.Date;\n";
        }
        return importClass;
    }

    /**
     * 得到getset方法
     *
     * @param fieldType
     * @param fieldName
     * @return
     */
    private String SETTER_TEMPLATE = "\tpublic %s set%s(%s %s){\n \t\tthis.%s = %s;\n\t%s}\n\n";
    private String GETTER_TEMPLATE = "\tpublic %s get%s(){\n \t\treturn %s;\n\t}\n\n";
    private String REUTRN_THIS_TEMPLATE = " \treturn this;\n\t";

    public String makeGetterSetter(String fieldType, String fieldName) {
        StringBuffer stringBuffer = new StringBuffer();
        String _fieldName = (fieldName.charAt(0) + "").toUpperCase() + fieldName.substring(1);
        stringBuffer.append(String.format(SETTER_TEMPLATE, isChainProperty ? "%s" : Void.TYPE.toString(), _fieldName, fieldType, fieldName, fieldName, fieldName,
                isChainProperty ? REUTRN_THIS_TEMPLATE : ""));
        stringBuffer.append(String.format(GETTER_TEMPLATE, fieldType, _fieldName, fieldName));
        return stringBuffer.toString();
    }

    private static final String DATE_F = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    private String getObjectString(SqlModelDefine dbField) {
        if (actualFieldName) {
            return dbField.getColumnName();
        }
        String objectField = dbField.getColumnName().toLowerCase();
        if (!StringUtils.isEmpty(columnPrefix) && objectField.indexOf(columnPrefix) == 0) {
            objectField = objectField.substring(columnPrefix.length());
        }
        objectField = (objectField.charAt(0) + "").toLowerCase() + objectField.substring(1);
        if (underLineToCamelCase) {
            String[] underLineWords = objectField.split("_");
            if (underLineWords.length > 1) {
                StringBuffer sbuffer = new StringBuffer();
                sbuffer.append(underLineWords[0]);
                for (int wordIndex = 1; wordIndex < underLineWords.length; wordIndex++) {
                    sbuffer.append((underLineWords[wordIndex].charAt(0) + "").toUpperCase() +
                            underLineWords[wordIndex].substring(1));
                }
                objectField = sbuffer.toString();
            }
        }
        return objectField;
    }

    private void makeRoot() {
        File root = new File(CONF_DIR_PATH);
        if (!root.exists()) {
            root.mkdir();
        }
        String fileDirPath = CONF_DIR_PATH + File.separator + objectNameSmall;
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        rootDir = fileDirPath;
    }

    private String getClassDesc(String descMore) {
        return String.format("/**\n * %s\n * Created by %s on %s\n */\n",
                desc + descMore, author,
                DATE_F, objectName);
    }

  /*  //获取数据类型
    private String getType(String dataType, DBType dbType) {
        String type = dbType.getTypeMap().get(dataType.toUpperCase());
        if (type != null) {
            return type;
        }
        return "String";
    }*/

    private String getType(String dataType, String columnType, DBType dbType) {
        if (dataType.equalsIgnoreCase("NUMBER") && (columnType.indexOf(",") < 0 || columnType.indexOf(",0") > 0)) {
            return "Integer";
        } else {
            String type = (String) dbType.getTypeMap().get(dataType.toUpperCase());
            return type != null ? type : "String";
        }
    }

    public boolean isUnderLineToCamelCase() {
        return underLineToCamelCase;
    }

    public SqlToModelGeneratorHelper setUnderLineToCamelCase(boolean underLineToCamelCase) {
        this.underLineToCamelCase = underLineToCamelCase;
        return this;
    }

    public boolean isXmlModelWithPackagePath() {
        return xmlModelWithPackagePath;
    }

    public SqlToModelGeneratorHelper setXmlModelWithPackagePath(boolean xmlModelWithPackagePath) {
        this.xmlModelWithPackagePath = xmlModelWithPackagePath;
        return this;
    }

    public boolean isChainProperty() {
        return isChainProperty;
    }

    public SqlToModelGeneratorHelper setChainProperty(boolean chainProperty) {
        isChainProperty = chainProperty;
        return this;
    }

    public boolean isGenerateToStringMethod() {
        return generateToStringMethod;
    }

    public SqlToModelGeneratorHelper setGenerateToStringMethod(boolean generateToStringMethod) {
        this.generateToStringMethod = generateToStringMethod;
        return this;
    }

    public boolean isNeedSuperClass() {
        return isNeedSuperClass;
    }

    public SqlToModelGeneratorHelper setNeedSuperClass(boolean needSuperClass) {
        isNeedSuperClass = needSuperClass;
        return this;
    }

    public boolean isActualFieldName() {
        return actualFieldName;
    }

    public SqlToModelGeneratorHelper setActualFieldName(boolean actualFieldName) {
        this.actualFieldName = actualFieldName;
        return this;
    }

    @Override
    public String toString() {
        return "连接信息{" +
                "dbParam=" + dbParam +
                ", packageName='" + packageName + '\'' +
                ", webPackageName='" + webPackageName + '\'' +
                ", dbName='" + dbName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", columnPrefix='" + columnPrefix + '\'' +
                ", objectName='" + objectName + '\'' +
                ", objectNameSmall='" + objectNameSmall + '\'' +
                ", objectNameBig='" + objectNameBig + '\'' +
                ", desc='" + desc + '\'' +
                ", author='" + author + '\'' +
                ", underLineToCamelCase=" + underLineToCamelCase +
                ", actualFieldName=" + actualFieldName +
                ", xmlModelWithPackagePath=" + xmlModelWithPackagePath +
                ", rootDir='" + rootDir + '\'' +
                ", isNeedSuperClass='" + isNeedSuperClass + '\'' +
                ", isNeedOpen='" + super.isNeedOpen() + '\'' +
                '}';
    }

    public static void main(String[] args) throws IOException {
        DBConnectParam dbConnectParam = new DBConnectParam(DBType.MYSQL, "root", "123456", "jdbc:mysql://localhost:3306/order");
        SqlToModelGeneratorHelper model = new SqlToModelGeneratorHelper(dbConnectParam, "order",
                "t_order_info", "f", "com.saas.oa.erp",
                "com.saas.oa.web.mgr",
                "OrderInfo", "消息", "byb");
        model.setChainProperty(true);
        model.setGenerateToStringMethod(true);
        model.autoGenerate();
    }
}
