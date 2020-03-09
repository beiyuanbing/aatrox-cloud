package com.aatrox.autocode.project.helper;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.aatrox.autocode.base.helper.BaseHelper;
import com.aatrox.common.utils.AutoCodeUtils;
import com.aatrox.common.utils.CommonMapUtil;
import com.aatrox.common.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NewServiceProjectHelper extends BaseHelper {
    private String projectName;
    private String bizName;
    private String serviceName;
    private String packagePrefix;
    private boolean needSolr;
    private boolean needSolr7x;
    private boolean needDubbo;
    private boolean needMysql;
    private boolean needTidb;
    private boolean needH2sql;
    private boolean needOracle;
    private boolean needRedis;
    private boolean needActivemq;
    private boolean needRabbitmq;
    private String serviceDir;
    private String rootServiceDirPath;
    private String rootApiListDirPath;
    private String groupId;
    private String rootPackage;
    public static final String CONF_DIR_PATH;

    public NewServiceProjectHelper(String projectName, String bizName, String serviceName, String packagePrefix) {
        this.projectName = projectName;
        this.bizName = bizName;
        this.serviceName = serviceName;
        this.packagePrefix = packagePrefix;
        if (!StringUtils.isEmpty(projectName) && !StringUtils.isEmpty(serviceName)) {
            this.groupId = String.format("%s.%sservice", projectName, !StringUtils.isEmpty(bizName) ? bizName + "." : "");
            this.rootPackage = (StringUtils.isEmpty(packagePrefix) ? "" : packagePrefix + ".") + this.groupId + "." + serviceName;
        } else {
            throw new RuntimeException("projectName或serviceName不得为空");
        }
    }

    public void doNew() throws IOException {
        System.out.println(this.toString());
        System.out.println("开始生成项目文件...");
        System.out.println("开始生成根目录...");
        this.makeRoot();
        System.out.println("开始生成根目录...DONE");
        System.out.println("开始生成代码文件...");
        this.makeApiList();
        this.makeService();
        System.out.println("开始生成代码文件...DONE");
        System.out.println("生成项目文件...DONE");
        System.out.println("完成操作...");
        System.out.println("请到以下地址复制文件..." + CONF_DIR_PATH);
        super.openRoot(CONF_DIR_PATH);
    }

    private void makeApiList() throws IOException {
        this.makeApiListPomXml();
        this.makeApiListSrc();
    }

    private void makeService() throws IOException {
        this.makeServicePomXml();
        this.makeServiceSrc();
    }

    private void makeApiListSrc() throws IOException {
        String srcPath = this.rootApiListDirPath + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotEmpty(this.packagePrefix)) {
            srcPath = srcPath + this.packagePrefix + File.separator;
        }

        srcPath = srcPath + this.projectName + File.separator + (StringUtils.isNotEmpty(this.bizName) ? this.bizName + File.separator : "") + "service" + File.separator + this.serviceName + File.separator + "apilist";
        (new File(srcPath)).mkdirs();
        this.makeApiListForm(srcPath);
        this.makeApiListModel(srcPath);
        this.makeApiListRestful(srcPath);
        Map<String, String> param = CommonMapUtil.NEW("rootPackage", this.rootPackage + ".apilist");
        String returnCode = AutoCodeUtils.readResource("newProject/newService/apilist/returnCode.new");
        returnCode = AutoCodeUtils.replacePlaceHolder(returnCode, param);
        AutoCodeUtils.makeFile(srcPath, "ReturnCodeEnum.java", returnCode);
    }

    private void makeApiListRestful(String srcPath) throws IOException {
        String path = srcPath + File.separator + "restful";
        (new File(path)).mkdir();
        String zoneConstants = AutoCodeUtils.readResource("newProject/newService/apilist/zoneConstants.new");
        Map<String, String> param = CommonMapUtil.NEW("rootPackage", this.rootPackage + ".apilist");
        param.put("serviceDir", this.serviceDir);
        zoneConstants = AutoCodeUtils.replacePlaceHolder(zoneConstants, param);
        AutoCodeUtils.makeFile(path, "ZoneConstants.java", zoneConstants);
        String firstFeign = AutoCodeUtils.readResource("newProject/newService/apilist/firstFeign.new");
        firstFeign = AutoCodeUtils.replacePlaceHolder(firstFeign, param);
        AutoCodeUtils.makeFile(path, "FirstFeign.java", firstFeign);
    }

    private void makeApiListModel(String srcPath) throws IOException {
        String path = srcPath + File.separator + "model";
        (new File(path)).mkdir();
        String firstModel = AutoCodeUtils.readResource("newProject/newService/apilist/firstModel.new");
        Map<String, String> param = CommonMapUtil.NEW("rootPackage", this.rootPackage + ".apilist");
        firstModel = AutoCodeUtils.replacePlaceHolder(firstModel, param);
        AutoCodeUtils.makeFile(path, "FirstModel.java", firstModel);
    }

    private void makeApiListForm(String srcPath) throws IOException {
        String formPath = srcPath + File.separator + "form";
        (new File(formPath)).mkdir();
        String firstForm = AutoCodeUtils.readResource("newProject/newService/apilist/firstForm.new");
        Map<String, String> param = CommonMapUtil.NEW("rootPackage", this.rootPackage + ".apilist");
        firstForm = AutoCodeUtils.replacePlaceHolder(firstForm, param);
        AutoCodeUtils.makeFile(formPath, "FirstForm.java", firstForm);
    }

    private void makeApiListPomXml() throws IOException {
        String pom = AutoCodeUtils.readResource("newProject/newService/apilist/pom.new");
        Map<String, String> param = new HashMap();
        param.put("artifactId", this.serviceDir + "-apilist");
        param.put("groupId", this.groupId);
        param.put("parent-version", "1.1.0-SNAPSHOT");
        pom = AutoCodeUtils.replacePlaceHolder(pom, param);
        AutoCodeUtils.makeFile(this.rootApiListDirPath, "pom.xml", pom);
    }

    private void makeConfig(String srcPath) throws IOException {
        String path = srcPath + File.separator + "config";
        (new File(path)).mkdir();
        String interceptorConfig = AutoCodeUtils.readResource("newProject/newService/service/interceptorConfig.new");
        Map<String, String> param = CommonMapUtil.NEW("rootPackage", this.rootPackage);
        interceptorConfig = AutoCodeUtils.replacePlaceHolder(interceptorConfig, param);
        AutoCodeUtils.makeFile(path, "InterceptorConfig.java", interceptorConfig);
    }

    private void makeServiceSrc() throws IOException {
        String srcPath = this.rootServiceDirPath + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotEmpty(this.packagePrefix)) {
            srcPath = srcPath + this.packagePrefix + File.separator;
        }

        srcPath = srcPath + this.projectName + File.separator + (StringUtils.isNotEmpty(this.bizName) ? this.bizName + File.separator : "") + "service" + File.separator + this.serviceName;
        String resourcePath = this.rootServiceDirPath + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        (new File(srcPath)).mkdirs();
        this.makeApi(srcPath);
        this.makeInterceptor(srcPath);
        this.makeServiceDir(srcPath);
        this.makeDao(srcPath);
        this.makeConfig(srcPath);
        if (this.needSolr || this.needSolr7x) {
            this.makeSolr(srcPath);
        }

        if (this.needDubbo) {
            this.makeDubbo(srcPath);
        }

        if (this.needActivemq) {
            this.makeActivemq(srcPath);
        }

        String start = AutoCodeUtils.readResource("newProject/newService/service/start.new");
        Map<String, String> param = CommonMapUtil.NEW("rootPackage", this.rootPackage);
        StringBuffer importClass = new StringBuffer();
        StringBuffer applicationImport = new StringBuffer();
        String var10000 = this.serviceName.substring(0, 1).toUpperCase();
        String fileName = var10000 + this.serviceName.substring(1) + "ServiceApplication";
        param.put("serviceAppName", fileName);
        if (this.needOracle) {
            importClass.append("import aatrox.infra.feature.oracle.OracleConfiguration;\n");
            applicationImport.append("OracleConfiguration.class,");
            (new File(resourcePath + File.separator + "mybatis" + File.separator + "oracle" + File.separator + this.serviceName)).mkdirs();
        } else if (this.needMysql) {
            importClass.append("import aatrox.infra.feature.mysql.MysqlConfiguration;\n");
            applicationImport.append("MysqlConfiguration.class,");
            (new File(resourcePath + File.separator + "mybatis" + File.separator + "mysql" + File.separator + this.serviceName)).mkdirs();
        } else if (this.needTidb) {
            importClass.append("import aatrox.infra.feature.tidb.TidbConfiguration;\n");
            applicationImport.append("TidbConfiguration.class,");
            (new File(resourcePath + File.separator + "mybatis" + File.separator + "tidb" + File.separator + this.serviceName)).mkdirs();
        } else {
            importClass.append("import aatrox.infra.feature.h2sql.H2SqlConfiguration;\n");
            applicationImport.append("H2SqlConfiguration.class,");
            (new File(resourcePath + File.separator + "mybatis" + File.separator + "h2sql" + File.separator + this.serviceName)).mkdirs();
        }

        param.put("importClass", importClass.toString());
        param.put("import", applicationImport.toString());
        start = AutoCodeUtils.replacePlaceHolder(start, param);
        AutoCodeUtils.makeFile(srcPath, fileName + ".java", start);
        AutoCodeUtils.makeFile(resourcePath, "application.yml", "");
        this.makeProperteis(resourcePath);
        this.makeBootstrap(resourcePath);
        this.makeLogback(resourcePath);
    }

    private void makeActivemq(String srcPath) throws IOException {
        String configPath = srcPath + File.separator + "config";
        (new File(configPath)).mkdir();
        String activeConfig = AutoCodeUtils.readResource("newProject/module/activeConfig.new");
        Map<String, String> param = CommonMapUtil.NEW("rootPackage", this.rootPackage);
        activeConfig = AutoCodeUtils.replacePlaceHolder(activeConfig, param);
        AutoCodeUtils.makeFile(configPath, "ActiveConfig.java", activeConfig);
        String path = srcPath + File.separator + "mq";
        (new File(path)).mkdir();
        String firstListenerQueue = AutoCodeUtils.readResource("newProject/module/firstListenerQueue.new");
        firstListenerQueue = AutoCodeUtils.replacePlaceHolder(firstListenerQueue, param);
        AutoCodeUtils.makeFile(path, "FirstListenerQueue.java", firstListenerQueue);
    }

    private void makeProperteis(String resourcePath) throws IOException {
        String properties = AutoCodeUtils.readResource("newProject/newService/service/properties.new");
        Map<String, String> param = CommonMapUtil.NEW();
        param.put("activemq-prop", this.needActivemq ? AutoCodeUtils.readResource("newProject/module/props/activemq-prop.new") : "");
        param.put("rabbitmq-prop", this.needRabbitmq ? AutoCodeUtils.readResource("newProject/module/props/rabbitmq-prop.new") : "");
        param.put("dubbo-prop", this.needDubbo ? AutoCodeUtils.readResource("newProject/module/props/dubbo-prop.new") : "");
        param.put("redis-prop", this.needRedis ? AutoCodeUtils.readResource("newProject/module/props/redis-prop.new") : "");
        param.put("solr-prop", !this.needSolr && !this.needSolr7x ? "" : AutoCodeUtils.readResource("newProject/module/props/solr-prop.new"));
        param.put("oracle-prop", this.needOracle ? AutoCodeUtils.readResource("newProject/module/props/oracle-prop.new") : "");
        param.put("mysql-prop", this.needMysql ? AutoCodeUtils.readResource("newProject/module/props/mysql-prop.new") : "");
        param.put("h2sql-prop", this.needH2sql ? AutoCodeUtils.readResource("newProject/module/props/h2sql-prop.new") : "");
        param.put("tidb-prop", this.needTidb ? AutoCodeUtils.readResource("newProject/module/props/tidb-prop.new") : "");
        properties = AutoCodeUtils.replacePlaceHolder(properties, param);
        param.put("serviceName", this.serviceDir);
        param.put("package", this.rootPackage);
        param.put("projectName", this.projectName);
        properties = AutoCodeUtils.replacePlaceHolder(properties, param);
        AutoCodeUtils.makeFile(resourcePath, this.serviceDir + "-dev.properties", properties);
    }

    private void makeLogback(String resourcePath) throws IOException {
        String logback = AutoCodeUtils.readResource("newProject/newService/service/logback.new");
        String var10000 = this.projectName;
        String rootDir = var10000 + (StringUtils.isNotEmpty(this.bizName) ? "/" + this.bizName : "");
        Map<String, String> param = CommonMapUtil.NEW("path", rootDir + "/services/" + this.serviceDir);
        logback = AutoCodeUtils.replacePlaceHolder(logback, param);
        AutoCodeUtils.makeFile(resourcePath, "logback.xml", logback);
    }

    private void makeBootstrap(String resourcePath) throws IOException {
        Map<String, String> param = CommonMapUtil.NEW("serviceName", this.serviceDir);
        String bootstrap = AutoCodeUtils.readResource("newProject/module/bootstrap.new");
        bootstrap = AutoCodeUtils.replacePlaceHolder(bootstrap, param);
        AutoCodeUtils.makeFile(resourcePath, "bootstrap.yml", bootstrap);
    }

    private void makeDubbo(String srcPath) throws IOException {
        String configPath = srcPath + File.separator + "config";
        (new File(configPath)).mkdir();
        Map<String, String> param = CommonMapUtil.NEW("rootPackage", this.rootPackage);
        String dubboConfiguration = AutoCodeUtils.readResource("newProject/module/dubboConfiguration.new");
        dubboConfiguration = AutoCodeUtils.replacePlaceHolder(dubboConfiguration, param);
        AutoCodeUtils.makeFile(configPath, "DubboConfiguration.java", dubboConfiguration);
    }

    private void makeSolr(String srcPath) throws IOException {
        String path = srcPath + File.separator + "solr";
        (new File(path)).mkdir();
        String firstSearcher = AutoCodeUtils.readResource("newProject/newService/service/firstSearcher.new");
        Map<String, String> param = CommonMapUtil.NEW("rootPackage", this.rootPackage);
        firstSearcher = AutoCodeUtils.replacePlaceHolder(firstSearcher, param);
        AutoCodeUtils.makeFile(path, "FirstSearcher.java", firstSearcher);
        String configPath = srcPath + File.separator + "config";
        (new File(configPath)).mkdir();
        String solrConfiguration = AutoCodeUtils.readResource("newProject/newService/service/solrConfiguration.new");
        solrConfiguration = AutoCodeUtils.replacePlaceHolder(solrConfiguration, param);
        AutoCodeUtils.makeFile(configPath, "SolrConfiguration.java", solrConfiguration);
    }

    private void makeDao(String srcPath) throws IOException {
        if (this.needOracle || this.needMysql || this.needH2sql || this.needTidb) {
            String path = srcPath + File.separator + "dao" + File.separator + "automapper";
            (new File(path)).mkdirs();
            String firstDao = AutoCodeUtils.readResource("newProject/newService/service/firstDao.new");
            Map<String, String> param = CommonMapUtil.NEW("rootPackage", this.rootPackage);
            firstDao = AutoCodeUtils.replacePlaceHolder(firstDao, param);
            AutoCodeUtils.makeFile(path, "FirstDao.java", firstDao);
            String configPath = srcPath + File.separator + "config";
            (new File(configPath)).mkdir();
            String autoMapConfig = AutoCodeUtils.readResource("newProject/newService/service/autoMapConfig.new");
            autoMapConfig = AutoCodeUtils.replacePlaceHolder(autoMapConfig, param);
            AutoCodeUtils.makeFile(configPath, "AutoMapConfig.java", autoMapConfig);
        }

    }

    private void makeServiceDir(String srcPath) throws IOException {
        String path = srcPath + File.separator + "service";
        (new File(path)).mkdir();
        String firstService = AutoCodeUtils.readResource("newProject/newService/service/firstService.new");
        Map<String, String> param = CommonMapUtil.NEW("rootPackage", this.rootPackage);
        firstService = AutoCodeUtils.replacePlaceHolder(firstService, param);
        AutoCodeUtils.makeFile(path, "FirstService.java", firstService);
    }

    private void makeInterceptor(String srcPath) throws IOException {
        String path = srcPath + File.separator + "interceptor";
        (new File(path)).mkdir();
        String dataSourceKey = this.serviceDir.toUpperCase().replaceAll("-", "_");
        String contextInterceptor = AutoCodeUtils.readResource("newProject/module/contextInterceptor.new");
        Map<String, String> param = CommonMapUtil.NEW("rootPackage", this.rootPackage);
        param.put("dataSourceKey", dataSourceKey);
        contextInterceptor = AutoCodeUtils.replacePlaceHolder(contextInterceptor, param);
        AutoCodeUtils.makeFile(path, "ContextInterceptor.java", contextInterceptor);
    }

    private void makeApi(String srcPath) throws IOException {
        String path = srcPath + File.separator + "api";
        (new File(path)).mkdir();
        String firstApi = AutoCodeUtils.readResource("newProject/newService/service/firstApi.new");
        Map<String, String> param = CommonMapUtil.NEW("rootPackage", this.rootPackage);
        firstApi = AutoCodeUtils.replacePlaceHolder(firstApi, param);
        AutoCodeUtils.makeFile(path, "FirstApi.java", firstApi);
    }

    private void makeServicePomXml() throws IOException {
        String pom = AutoCodeUtils.readResource("newProject/newService/service/pom.new");
        Map<String, String> param = new HashMap();
        param.put("artifactId", this.serviceDir);
        param.put("groupId", this.groupId);
        param.put("parent-version", "1.1.0-SNAPSHOT");
        param.put("redis-pom", this.needRedis ? AutoCodeUtils.readResource("newProject/module/poms/redis-pom.new") : "");
        param.put("dubbo-pom", this.needDubbo ? AutoCodeUtils.readResource("newProject/module/poms/dubbo-pom.new") : "");
        param.put("activemq-pom", this.needActivemq ? AutoCodeUtils.readResource("newProject/module/poms/activemq-pom.new") : "");
        param.put("rabbitmq-pom", this.needRabbitmq ? AutoCodeUtils.readResource("newProject/module/poms/rabbitmq-pom.new") : "");
        param.put("solr-pom", this.needSolr ? AutoCodeUtils.readResource("newProject/module/poms/solr-pom.new") : "");
        param.put("solr7x-pom", this.needSolr7x ? AutoCodeUtils.readResource("newProject/module/poms/solr7x-pom.new") : "");
        param.put("mysql-pom", this.needMysql ? AutoCodeUtils.readResource("newProject/module/poms/mysql-pom.new") : "");
        param.put("oracle-pom", this.needOracle ? AutoCodeUtils.readResource("newProject/module/poms/oracle-pom.new") : "");
        param.put("h2sql-pom", this.needH2sql ? AutoCodeUtils.readResource("newProject/module/poms/h2sql-pom.new") : "");
        param.put("tidb-pom", this.needTidb ? AutoCodeUtils.readResource("newProject/module/poms/tidb-pom.new") : "");
        pom = AutoCodeUtils.replacePlaceHolder(pom, param);
        AutoCodeUtils.makeFile(this.rootServiceDirPath, "pom.xml", pom);
    }

    private void makeRoot() {
        File root = new File(CONF_DIR_PATH);
        if (!root.exists()) {
            root.mkdir();
        }

        String var10000 = this.projectName;
        String rootDir = var10000 + (StringUtils.isNotEmpty(this.bizName) ? "-" + this.bizName : "") + "-" + this.serviceName;
        String rootDirPath = CONF_DIR_PATH + File.separator + rootDir;
        File rootDirFile = new File(rootDirPath);
        if (rootDirFile.exists()) {
            rootDirFile.delete();
        }

        rootDirFile.mkdir();
        String var10001 = StringUtils.isNotEmpty(this.bizName) ? "-" + this.bizName : "";
        this.serviceDir = "service" + var10001 + "-" + this.serviceName;
        this.rootServiceDirPath = rootDirPath + File.separator + this.serviceDir;
        (new File(this.rootServiceDirPath)).mkdir();
        this.rootApiListDirPath = this.rootServiceDirPath + "-apilist";
        (new File(this.rootApiListDirPath)).mkdir();
    }

    @Override
    public String toString() {
        return "NewProjectHelper{projectName='" + this.projectName + "', bizName='" + this.bizName + "', serviceName='" + this.serviceName + "'}";
    }

    public void setNeedSolr(boolean needSolr) {
        this.needSolr = needSolr;
    }

    public void setNeedSolr7x(boolean needSolr7x) {
        this.needSolr7x = needSolr7x;
    }

    public void setNeedDubbo(boolean needDubbo) {
        this.needDubbo = needDubbo;
    }

    public void setNeedMysql(boolean needMysql) {
        this.needMysql = needMysql;
    }

    public void setNeedOracle(boolean needOracle) {
        this.needOracle = needOracle;
    }

    public void setNeedRedis(boolean needRedis) {
        this.needRedis = needRedis;
    }

    public void setNeedActivemq(boolean needActivemq) {
        this.needActivemq = needActivemq;
    }

    public void setNeedH2sql(boolean needH2sql) {
        this.needH2sql = needH2sql;
    }

    public String getPackagePrefix() {
        return this.packagePrefix;
    }

    public void setNeedTidb(boolean needTidb) {
        this.needTidb = needTidb;
    }

    public void setNeedRabbitmq(boolean needRabbitmq) {
        this.needRabbitmq = needRabbitmq;
    }

    static {
        String var10000 = System.getProperty("user.home");
        CONF_DIR_PATH = var10000 + File.separator + "byb.newproject";
    }
}

