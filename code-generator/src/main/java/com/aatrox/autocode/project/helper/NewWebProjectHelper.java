package com.aatrox.autocode.project.helper;


import com.aatrox.autocode.base.helper.BaseHelper;
import com.aatrox.common.utils.AutoCodeUtils;
import com.aatrox.common.utils.CommonMapUtil;
import com.aatrox.common.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NewWebProjectHelper extends BaseHelper {
    private String projectName;
    private String bizName;
    private String webName;
    private String packagePrefix;
    private boolean needDubbo;
    private boolean needRedis;
    private boolean needActivemq;
    private boolean needRabbitmq;
    private boolean needAuth;
    private String webDir;
    private String rootWebDirPath;
    private String groupId;
    private String rootPackage;
    public static final String CONF_DIR_PATH;

    public NewWebProjectHelper(String projectName, String bizName, String webName, String packagePrefix) {
        this.projectName = projectName;
        this.bizName = bizName;
        this.webName = webName;
        this.packagePrefix = packagePrefix;
        if (!StringUtils.isEmpty(projectName) && !StringUtils.isEmpty(webName)) {
            this.groupId = String.format("%s.%sweb", projectName, StringUtils.isNotEmpty(bizName) ? bizName + "." : "");
            this.rootPackage = (StringUtils.isEmpty(packagePrefix) ? "" : packagePrefix + ".") + this.groupId + "." + webName;
        } else {
            throw new RuntimeException("projectName或webName不得为空");
        }
    }

    public void doNew() throws IOException {
        System.out.println(this.toString());
        System.out.println("开始生成项目文件...");
        System.out.println("开始生成根目录...");
        this.makeRoot();
        System.out.println("开始生成根目录...DONE");
        System.out.println("开始生成代码文件...");
        this.makeWeb();
        System.out.println("开始生成代码文件...DONE");
        System.out.println("生成项目文件...DONE");
        System.out.println("完成操作...");
        System.out.println("请到以下地址复制文件..." + CONF_DIR_PATH);
        super.openRoot(CONF_DIR_PATH);
    }

    private void makeWeb() throws IOException {
        this.makeWebPomXml();
        this.makeWebSrc();
    }

    private void makeWebSrc() throws IOException {
        String srcPath = this.rootWebDirPath + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotEmpty(this.packagePrefix)) {
            srcPath = srcPath + this.packagePrefix + File.separator;
        }

        srcPath = srcPath + this.projectName + File.separator + (StringUtils.isNotEmpty(this.bizName) ? this.bizName + File.separator : "") + "web" + File.separator + this.webName;
        String resourcePath = this.rootWebDirPath + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        (new File(srcPath)).mkdirs();
        (new File(resourcePath)).mkdirs();
        this.makeController(srcPath);
        this.makeRemote(srcPath);
        this.makeInterceptor(srcPath);
        this.makeConfig(srcPath);
        if (this.needDubbo) {
            this.makeDubbo(srcPath);
        }

        if (this.needActivemq) {
            this.makeActivemq(srcPath);
        }

        String start = AutoCodeUtils.readResource("newProject/newWeb/start.new");
        Map<String, String> param = CommonMapUtil.NEW("rootPackage", this.rootPackage);
        String var10000 = this.webName.substring(0, 1).toUpperCase();
        String fileName = var10000 + this.webName.substring(1) + "WebApplication";
        param.put("webAppName", fileName);
        start = AutoCodeUtils.replacePlaceHolder(start, param);
        AutoCodeUtils.makeFile(srcPath, fileName + ".java", start);
        this.makeApplication(resourcePath);
        this.makeProperteis(resourcePath);
        this.makeBootstrap(resourcePath);
        this.makeLogback(resourcePath);
    }

    private void makeConfig(String srcPath) throws IOException {
        String path = srcPath + File.separator + "config";
        (new File(path)).mkdir();
        String interceptorConfig = AutoCodeUtils.readResource("newProject/newWeb/interceptorConfig.new");
        Map<String, String> param = CommonMapUtil.NEW("rootPackage", this.rootPackage);
        if (this.needAuth) {
            param.put("importAuthClass", "import " + this.rootPackage + ".interceptor.AuthorityInterceptor;\n");
            param.put("importAuth", "        registry.addInterceptor(new AuthorityInterceptor()).addPathPatterns(\"/security/**\");\n");
        } else {
            param.put("importAuthClass", "");
            param.put("importAuth", "");
        }

        interceptorConfig = AutoCodeUtils.replacePlaceHolder(interceptorConfig, param);
        AutoCodeUtils.makeFile(path, "InterceptorConfig.java", interceptorConfig);
        String swagger = AutoCodeUtils.readResource("newProject/newWeb/swagger2Config.new");
        swagger = AutoCodeUtils.replacePlaceHolder(swagger, param);
        AutoCodeUtils.makeFile(path, "Swagger2Config.java", swagger);
        String swaggerTag = AutoCodeUtils.readResource("newProject/newWeb/swaggerTag.new");
        swaggerTag = AutoCodeUtils.replacePlaceHolder(swaggerTag, param);
        AutoCodeUtils.makeFile(path, "ApiSwaggerTags.java", swaggerTag);
        if (this.needAuth) {
            String auth = AutoCodeUtils.readResource("newProject/newWeb/authorityAnnotation.new");
            auth = AutoCodeUtils.replacePlaceHolder(auth, param);
            AutoCodeUtils.makeFile(path, "AuthorityAnnotation.java", auth);
            String authEnum = AutoCodeUtils.readResource("newProject/newWeb/authorityAnnotationEnums.new");
            authEnum = AutoCodeUtils.replacePlaceHolder(authEnum, param);
            AutoCodeUtils.makeFile(path, "AuthorityAnnotationEnums.java", authEnum);
        }

    }

    private void makeRemote(String srcPath) throws IOException {
        String path = srcPath + File.separator + "remote";
        (new File(path)).mkdir();
        String remote = AutoCodeUtils.readResource("newProject/newWeb/firstRemote.new");
        Map<String, String> param = CommonMapUtil.NEW("rootPackage", this.rootPackage);
        remote = AutoCodeUtils.replacePlaceHolder(remote, param);
        AutoCodeUtils.makeFile(path, "FirstRemote.java", remote);
    }

    private void makeActivemq(String srcPath) throws IOException {
        String path = srcPath + File.separator + "mq";
        (new File(path)).mkdir();
        String firstListenerQueue = AutoCodeUtils.readResource("newProject/module/firstListenerQueue.new");
        Map<String, String> param = CommonMapUtil.NEW("rootPackage", this.rootPackage);
        firstListenerQueue = AutoCodeUtils.replacePlaceHolder(firstListenerQueue, param);
        AutoCodeUtils.makeFile(path, "FirstListenerQueue.java", firstListenerQueue);
        String configPath = srcPath + File.separator + "config";
        (new File(configPath)).mkdir();
        String activeConfig = AutoCodeUtils.readResource("newProject/module/activeConfig.new");
        activeConfig = AutoCodeUtils.replacePlaceHolder(activeConfig, param);
        AutoCodeUtils.makeFile(configPath, "ActiveConfig.java", activeConfig);
    }

    private void makeProperteis(String resourcePath) throws IOException {
        String properties = AutoCodeUtils.readResource("newProject/newWeb/properties.new");
        Map<String, String> param = CommonMapUtil.NEW();
        param.put("activemq-prop", this.needActivemq ? AutoCodeUtils.readResource("newProject/module/props/activemq-prop.new") : "");
        param.put("rabbitmq-prop", this.needRabbitmq ? AutoCodeUtils.readResource("newProject/module/props/rabbitmq-prop.new") : "");
        param.put("dubbo-prop", this.needDubbo ? AutoCodeUtils.readResource("newProject/module/props/dubbo-prop.new") : "");
        param.put("redis-prop", this.needRedis ? AutoCodeUtils.readResource("newProject/module/props/redis-prop.new") : "");
        properties = AutoCodeUtils.replacePlaceHolder(properties, param);
        param.put("serviceName", this.webDir);
        param.put("projectName", this.projectName);
        properties = AutoCodeUtils.replacePlaceHolder(properties, param);
        AutoCodeUtils.makeFile(resourcePath, this.webDir + "-dev.properties", properties);
    }

    private void makeLogback(String resourcePath) throws IOException {
        String logBack = AutoCodeUtils.readResource("newProject/newWeb/logback.new");
        String var10000 = this.projectName;
        String rootDir = var10000 + (StringUtils.isNotEmpty(this.bizName) ? "/" + this.bizName : "");
        Map<String, String> param = CommonMapUtil.NEW("path", rootDir + "/webs/" + this.webDir);
        logBack = AutoCodeUtils.replacePlaceHolder(logBack, param);
        AutoCodeUtils.makeFile(resourcePath, "logback.xml", logBack);
    }

    private void makeApplication(String resourcePath) throws IOException {
        String application = AutoCodeUtils.readResource("newProject/newWeb/application.new");
        Map<String, String> param = CommonMapUtil.NEW("serviceName", this.webDir);
        application = AutoCodeUtils.replacePlaceHolder(application, param);
        AutoCodeUtils.makeFile(resourcePath, "application.yml", application);
    }

    private void makeBootstrap(String resourcePath) throws IOException {
        String bootstrap = AutoCodeUtils.readResource("newProject/module/bootstrap.new");
        Map<String, String> param = CommonMapUtil.NEW("serviceName", this.webDir);
        bootstrap = AutoCodeUtils.replacePlaceHolder(bootstrap, param);
        AutoCodeUtils.makeFile(resourcePath, "bootstrap.yml", bootstrap);
    }

    private void makeDubbo(String srcPath) throws IOException {
        String configPath = srcPath + File.separator + "config";
        (new File(configPath)).mkdir();
        String dubboConfiguration = AutoCodeUtils.readResource("newProject/module/dubboConfiguration.new");
        Map<String, String> param = CommonMapUtil.NEW("rootPackage", this.rootPackage);
        dubboConfiguration = AutoCodeUtils.replacePlaceHolder(dubboConfiguration, param);
        AutoCodeUtils.makeFile(configPath, "DubboConfiguration.java", dubboConfiguration);
    }

    private void makeInterceptor(String srcPath) throws IOException {
        String path = srcPath + File.separator + "interceptor";
        (new File(path)).mkdir();
        String dataSourceKey = this.webDir.toUpperCase().replaceAll("-", "_");
        String contextInterceptor = AutoCodeUtils.readResource("newProject/module/contextInterceptor.new");
        Map<String, String> param = CommonMapUtil.NEW("rootPackage", this.rootPackage);
        param.put("dataSourceKey", dataSourceKey);
        contextInterceptor = AutoCodeUtils.replacePlaceHolder(contextInterceptor, param);
        AutoCodeUtils.makeFile(path, "ContextInterceptor.java", contextInterceptor);
        String swagger = AutoCodeUtils.readResource("newProject/newWeb/swaggerLoginInterceptor.new");
        swagger = AutoCodeUtils.replacePlaceHolder(swagger, param);
        AutoCodeUtils.makeFile(path, "SwaggerLoginInterceptor.java", swagger);
        if (this.needAuth) {
            String auth = AutoCodeUtils.readResource("newProject/newWeb/authorityInterceptor.new");
            auth = AutoCodeUtils.replacePlaceHolder(auth, param);
            AutoCodeUtils.makeFile(path, "AuthorityInterceptor.java", auth);
        }

    }

    private void makeController(String srcPath) throws IOException {
        String path = srcPath + File.separator + "controller";
        (new File(path)).mkdir();
        String openApiPath = path + File.separator + "openapi";
        (new File(openApiPath)).mkdir();
        (new File(path + File.separator + "security")).mkdir();
        String webBase = AutoCodeUtils.readResource("newProject/newWeb/webBaseController.new");
        Map<String, String> param = CommonMapUtil.NEW("rootPackage", this.rootPackage);
        webBase = AutoCodeUtils.replacePlaceHolder(webBase, param);
        AutoCodeUtils.makeFile(path, "WebBaseController.java", webBase);
        String first = AutoCodeUtils.readResource("newProject/newWeb/firstController.new");
        if (this.needAuth) {
            param.put("importAuthClass", "import " + this.rootPackage + ".config.AuthorityAnnotation;\nimport " + this.rootPackage + ".config.AuthorityAnnotationEnums;\n");
            param.put("authAnnotation", "    @AuthorityAnnotation(AuthorityAnnotationEnums.ADMIN)\n");
        } else {
            param.put("importAuthClass", "");
            param.put("authAnnotation", "");
        }

        first = AutoCodeUtils.replacePlaceHolder(first, param);
        AutoCodeUtils.makeFile(openApiPath, "FirstController.java", first);
    }

    private void makeWebPomXml() throws IOException {
        String pom = AutoCodeUtils.readResource("newProject/newWeb/pom.new");
        Map<String, String> replace = new HashMap();
        replace.put("artifactId", this.webDir);
        replace.put("groupId", this.groupId);
        replace.put("parent-version", "1.1.0-SNAPSHOT");
        replace.put("redis-pom", this.needRedis ? AutoCodeUtils.readResource("newProject/module/poms/redis-pom.new") : "");
        replace.put("dubbo-pom", this.needDubbo ? AutoCodeUtils.readResource("newProject/module/poms/dubbo-pom.new") : "");
        replace.put("activemq-pom", this.needActivemq ? AutoCodeUtils.readResource("newProject/module/poms/activemq-pom.new") : "");
        replace.put("rabbitmq-pom", this.needRabbitmq ? AutoCodeUtils.readResource("newProject/module/poms/rabbitmq-pom.new") : "");
        pom = AutoCodeUtils.replacePlaceHolder(pom, replace);
        AutoCodeUtils.makeFile(this.rootWebDirPath, "pom.xml", pom);
    }

    private void makeRoot() {
        File root = new File(CONF_DIR_PATH);
        if (!root.exists()) {
            root.mkdir();
        }

        String var10001 = StringUtils.isNotEmpty(this.bizName) ? "-" + this.bizName : "";
        this.webDir = "web" + var10001 + "-" + this.webName;
        this.rootWebDirPath = CONF_DIR_PATH + File.separator + this.webDir;
        File rootDirFile = new File(this.rootWebDirPath);
        if (rootDirFile.exists()) {
            rootDirFile.delete();
        }

        rootDirFile.mkdir();
    }

    @Override
    public String toString() {
        return "NewWebProjectHelper{projectName='" + this.projectName + "', bizName='" + this.bizName + "', webName='" + this.webName + "'}";
    }

    public void setNeedDubbo(boolean needDubbo) {
        this.needDubbo = needDubbo;
    }

    public void setNeedRedis(boolean needRedis) {
        this.needRedis = needRedis;
    }

    public void setNeedActivemq(boolean needActivemq) {
        this.needActivemq = needActivemq;
    }

    public void setNeedAuth(boolean needAuth) {
        this.needAuth = needAuth;
    }

    public void setNeedRabbitmq(boolean needRabbitmq) {
        this.needRabbitmq = needRabbitmq;
    }

    static {
        String var10000 = System.getProperty("user.home");
        CONF_DIR_PATH = var10000 + File.separator + "byb.newproject";
    }
}

