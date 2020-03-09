package com.aatrox.generator.bean;

import com.aatrox.generator.enums.DBType;
import com.aatrox.generator.enums.DefineBeanType;
import com.aatrox.generator.util.CommonStringUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;

/**
 * @author aatrox
 * @desc
 * @date 2019/8/28
 */
public class GeneratorInfo {
    /**
     * 生成路径
     **/
    public final static String CONF_DIR_PATH = System.getProperty("user.home") + File.separator + "byb.mybatis-generator";
    /**数据库链接**/
    private String databaseUrl;
    /**数据库用户名**/
    private String databaseUserName;
    /**数据库用户密码**/
    private String databasePassword;
    /**数据库驱动名**/
    private String databaseDriverName=DBType.MYSQL.getDriver();
    /**数据库类型**/
    private DBType dataBaseType=DBType.MYSQL;
    /***生成的表名，多个使用,隔开,如果不给全生成***/
    private String includeTableName;
    /****生成的实体名**/
    private String entityName;
    /***不生成的表名，多个使用,隔开,如果不给全生成***/
    private String excludeTableName;
    /**表的前缀，生成实体会去掉这个前缀**/
    private String tablePrefix="t_";
    /**字段的前缀，生成实体会去掉这个前缀**/
    private String fieldPrefixs = "f,f_,fk";
    /**注释的开发者**/
    private String author="Aatrox";
    /**打开生成目录**/
    private boolean directoryOpen=true;
    /**使用swagger**/
    private boolean swagger2=true;
    /**是否覆盖文件**/
    private boolean fileOverride=true;
    /**mybatis的xml生成BaseResultMap**/
    private boolean xmlBaseResultMap=true;
    /**mybatis的xml生成BaseColumnList**/
    private boolean xmlBaseColumnList=true;
    /**模块名**/
    private String packageModuleName;
    /**包路径父类**/
    private String packageParentPath;
    /***启用mybatis Plus**/
    private boolean openMybatisPlus=true;
    /**是否开启mybaitsplus的注解**/
    private boolean openMybaitsPlusAnnotion=true;
    /**启用RestController**/
    private boolean restControllerStyle=true;
    /***实体名策略**/
    private NamingStrategy entityNaming=NamingStrategy.underline_to_camel;
    /***字段名策略**/
    private NamingStrategy columnNaming=NamingStrategy.underline_to_camel;
    /***entity的lombok**/
    private boolean entityLombokModel=false;
    /****生成entity的公用字段**/
    private String superEntityColumns=null;
    /*****父类controller路径***/
    private String controllerParentPath=null;
    /***controller的requestMapping驼峰转连字符**/
    private boolean controllerMappingHyphenStyle = false;
    /** 自定义模板的内容**/
    private DefineTemplateConfig defineTemplateConfig;
    /** 标准长路径**/
    private boolean defaultLongOutput=false;
    /***clound 模式**/
    private boolean springCloud =false;
    /****single**/
    private boolean single=false;
    /****自定义模板***/
    private List<DefineBean> defineBeanList;
    /***路径***/
    private String dirPath=CONF_DIR_PATH;
    //是否使用form
    private boolean openForm=false;
    //拓展setter方法
    private boolean entityBuilderModel=true;

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public GeneratorInfo setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
        return this;
    }

    public String getDatabaseUserName() {
        return databaseUserName;
    }

    public GeneratorInfo setDatabaseUserName(String databaseUserName) {
        this.databaseUserName = databaseUserName;
        return this;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public GeneratorInfo setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
        return this;
    }

    public String getDatabaseDriverName() {
        if(StringUtils.isEmpty(databaseDriverName)){
            databaseDriverName=dataBaseType.getDriver();
        }
        return databaseDriverName;
    }

    public GeneratorInfo setDatabaseDriverName(String databaseDriverName) {
        this.databaseDriverName = databaseDriverName;
        if(StringUtils.isEmpty(databaseDriverName)){
            databaseDriverName=dataBaseType.getDriver();
        }
        return this;
    }

    public DBType getDataBaseType() {
        return dataBaseType;
    }

    public GeneratorInfo setDataBaseType(DBType dataBaseType) {
        this.dataBaseType = dataBaseType;
        return this;
    }

    public boolean isOpenMybaitsPlusAnnotion() {
        return openMybaitsPlusAnnotion;
    }

    public GeneratorInfo setOpenMybaitsPlusAnnotion(boolean openMybaitsPlusAnnotion) {
        this.openMybaitsPlusAnnotion = openMybaitsPlusAnnotion;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public GeneratorInfo setAuthor(String author) {
        this.author = author;
        return this;
    }

    public boolean isDirectoryOpen() {
        return directoryOpen;
    }

    public GeneratorInfo setDirectoryOpen(boolean directoryOpen) {
        this.directoryOpen = directoryOpen;
        return this;
    }

    public boolean isSwagger2() {
        return swagger2;
    }

    public GeneratorInfo setSwagger2(boolean swagger2) {
        this.swagger2 = swagger2;
        return this;
    }

    public boolean isFileOverride() {
        return fileOverride;
    }

    public GeneratorInfo setFileOverride(boolean fileOverride) {
        this.fileOverride = fileOverride;
        return this;
    }

    public boolean isXmlBaseResultMap() {
        return xmlBaseResultMap;
    }

    public GeneratorInfo setXmlBaseResultMap(boolean xmlBaseResultMap) {
        this.xmlBaseResultMap = xmlBaseResultMap;
        return this;
    }

    public boolean isXmlBaseColumnList() {
        return xmlBaseColumnList;
    }

    public GeneratorInfo setXmlBaseColumnList(boolean xmlBaseColumnList) {
        this.xmlBaseColumnList = xmlBaseColumnList;
        return this;
    }

    public String getPackageModuleName() {
        return packageModuleName;
    }

    public GeneratorInfo setPackageModuleName(String packageModuleName) {
        this.packageModuleName = packageModuleName;
        return this;
    }

    public String getPackageParentPath() {
        return packageParentPath;
    }

    public GeneratorInfo setPackageParentPath(String packageParentPath) {
        this.packageParentPath = packageParentPath;
        return this;
    }

    public boolean isOpenMybatisPlus() {
        return openMybatisPlus;
    }

    public GeneratorInfo setOpenMybatisPlus(boolean openMybatisPlus) {
        this.openMybatisPlus = openMybatisPlus;
        return this;
    }

    public String getIncludeTableName() {
        return includeTableName;
    }

    public GeneratorInfo setIncludeTableName(String includeTableName) {
        this.includeTableName = includeTableName;
        return this;
    }

    public String getExcludeTableName() {
        return excludeTableName;
    }

    public GeneratorInfo setExcludeTableName(String excludeTableName) {
        this.excludeTableName = excludeTableName;
        return this;
    }

    public boolean isRestControllerStyle() {
        return restControllerStyle;
    }

    public GeneratorInfo setRestControllerStyle(boolean restControllerStyle) {
        this.restControllerStyle = restControllerStyle;
        return this;
    }

    public NamingStrategy getEntityNaming() {
        return entityNaming;
    }

    public GeneratorInfo setEntityNaming(NamingStrategy entityNaming) {
        this.entityNaming = entityNaming;
        return this;
    }

    public NamingStrategy getColumnNaming() {
        return columnNaming;
    }

    public GeneratorInfo setColumnNaming(NamingStrategy columnNaming) {
        this.columnNaming = columnNaming;
        return this;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public GeneratorInfo setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
        return this;
    }

    public String getFieldPrefixs() {
        return fieldPrefixs;
    }

    public GeneratorInfo setFieldPrefixs(String fieldPrefixs) {
        this.fieldPrefixs = fieldPrefixs;
        return this;
    }

    public boolean isEntityLombokModel() {
        return entityLombokModel;
    }

    public GeneratorInfo setEntityLombokModel(boolean entityLombokModel) {
        this.entityLombokModel = entityLombokModel;
        return this;
    }

    public String getControllerParentPath() {
        return controllerParentPath;
    }

    public GeneratorInfo setControllerParentPath(String controllerParentPath) {
        this.controllerParentPath = controllerParentPath;
        return this;
    }

    public String getSuperEntityColumns() {
        return superEntityColumns;
    }

    public GeneratorInfo setSuperEntityColumns(String superEntityColumns) {
        this.superEntityColumns = superEntityColumns;
        return this;
    }

    public boolean isControllerMappingHyphenStyle() {
        return controllerMappingHyphenStyle;
    }

    public GeneratorInfo setControllerMappingHyphenStyle(boolean controllerMappingHyphenStyle) {
        this.controllerMappingHyphenStyle = controllerMappingHyphenStyle;
        return this;
    }

    public DefineTemplateConfig getDefineTemplateConfig() {
        if(this.defineTemplateConfig==null){
            initDefineTemplateConfig();
        }
        return defineTemplateConfig;
    }

    public GeneratorInfo setDefineTemplateConfig(DefineTemplateConfig defineTemplateConfig) {
        this.defineTemplateConfig = defineTemplateConfig;
        if(this.defineTemplateConfig==null){
            initDefineTemplateConfig();
        }
        return this;
    }

    public boolean isDefaultLongOutput() {
        return defaultLongOutput;
    }

    public GeneratorInfo setDefaultLongOutput(boolean defaultLongOutput) {
        this.defaultLongOutput = defaultLongOutput;
        return this;
    }

    public String getEntityName() {
        return entityName;
    }

    public GeneratorInfo setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public boolean isSpringCloud() {
        return springCloud;
    }

    public GeneratorInfo setSpringCloud(boolean springCloud) {
        this.springCloud = springCloud;
        return this;
    }

    public void initDefineTemplateConfig(){
        DefineBean model = new DefineBean().setFileName(entityName+ CommonStringUtil.captureName(DefineBeanType.MODEL.name())).setBeanPackage(DefineBeanType.MODEL.getBeanPackage()).setTemplatePath(DefineBeanType.MODEL.getTemplatePath());
        DefineBean dao = new DefineBean().setFileName(entityName+CommonStringUtil.captureName(DefineBeanType.DAO.name())).setBeanPackage(DefineBeanType.DAO.getBeanPackage()).setTemplatePath(DefineBeanType.DAO.getTemplatePath());
        DefineBean xml = new DefineBean().setFileName(entityName+CommonStringUtil.captureName(DefineBeanType.XML.name())).setBeanPackage(DefineBeanType.XML.getBeanPackage()).setTemplatePath(DefineBeanType.XML.getTemplatePath()).setFileType(StringPool.DOT_XML);
        DefineBean service = new DefineBean().setFileName(entityName+CommonStringUtil.captureName(DefineBeanType.SERVICE.name())).setBeanPackage(DefineBeanType.SERVICE.getBeanPackage()).setTemplatePath(DefineBeanType.SERVICE.getTemplatePath());
        DefineBean serviceImpl = new DefineBean().setFileName(entityName+CommonStringUtil.captureName(DefineBeanType.SERVICEIMPL.name())).setBeanPackage(DefineBeanType.SERVICEIMPL.getBeanPackage()).setTemplatePath(DefineBeanType.SERVICEIMPL.getTemplatePath());
        DefineBean controller = new DefineBean().setFileName(entityName+CommonStringUtil.captureName(DefineBeanType.CONTROLLER.name())).setBeanPackage(DefineBeanType.CONTROLLER.getBeanPackage())
                .setTemplatePath(DefineBeanType.CONTROLLER.getTemplatePath());
        DefineBean api=null,remote=null,fegin=null;
        if(springCloud){
            api=new DefineBean().setFileName(entityName+CommonStringUtil.captureName(DefineBeanType.API.name())).setBeanPackage(DefineBeanType.API.getBeanPackage())
                    .setTemplatePath(DefineBeanType.API.getTemplatePath());
            remote=new DefineBean().setFileName(entityName+CommonStringUtil.captureName(DefineBeanType.REMOTE.name())).setBeanPackage(DefineBeanType.REMOTE.getBeanPackage())
                    .setTemplatePath(DefineBeanType.REMOTE.getTemplatePath());
            fegin=new DefineBean().setFileName(entityName+CommonStringUtil.captureName(DefineBeanType.FEGION.name())).setBeanPackage(DefineBeanType.FEGION.getBeanPackage())
                    .setTemplatePath(DefineBeanType.FEGION.getTemplatePath());
        }
        DefineTemplateConfig defineTemplateConfig = new DefineTemplateConfig().setModel(model).setDao(dao)
                .setXml(xml).setService(service).setApi(api).setRemote(remote)
                .setServiceImpl(serviceImpl).setController(controller).setFegin(fegin);
        this.defineTemplateConfig=defineTemplateConfig;

    }

    public boolean isSingle() {
        return single;
    }

    public GeneratorInfo setSingle(boolean single) {
        this.single = single;
        return this;
    }

    public List<DefineBean> getDefineBeanList() {
        return defineBeanList;
    }

    public GeneratorInfo setDefineBeanList(List<DefineBean> defineBeanList) {
        this.defineBeanList = defineBeanList;
        return this;
    }

    public String getDirPath() {
        return dirPath;
    }

    public GeneratorInfo setDirPath(String dirPath) {
        this.dirPath = dirPath;
        return this;
    }

    public static String getConfDirPath() {
        return CONF_DIR_PATH;
    }

    public boolean isOpenForm() {
        return openForm;
    }

    public GeneratorInfo setOpenForm(boolean openForm) {
        this.openForm = openForm;
        return this;
    }

    public boolean isEntityBuilderModel() {
        return entityBuilderModel;
    }

    public GeneratorInfo setEntityBuilderModel(boolean entityBuilderModel) {
        this.entityBuilderModel = entityBuilderModel;
        return this;
    }
}
