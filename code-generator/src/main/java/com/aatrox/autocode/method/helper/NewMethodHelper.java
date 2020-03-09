package com.aatrox.autocode.method.helper;

import com.aatrox.autocode.base.helper.BaseHelper;
import com.aatrox.autocode.method.param.FormFieldInfo;
import com.aatrox.autocode.method.param.FormInfo;
import com.aatrox.common.utils.AutoCodeUtils;
import com.aatrox.common.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NewMethodHelper extends BaseHelper {
    private String methodName;
    private NewMethodHelper.HttpMethodEnum httpMethod;
    private String apiUrlPath;
    private String authAnno;
    private String apiDesc;
    private String apiTag;
    private String apiReturnDesc;
    private String returnObject;
    private NewMethodHelper.ReturnTypeEnum returnObjectType;
    private String beanPrefix;
    private FormInfo formInfo;
    private String remoteName;
    private String feignName;
    private String serviceName;
    private String daoName;
    private String rootDir;
    public static final String CONF_DIR_PATH;

    public NewMethodHelper(String methodName, NewMethodHelper.HttpMethodEnum httpMethod, String authAnno, String apiUrlPath, String beanPrefix, FormInfo formInfo, String returnObject, NewMethodHelper.ReturnTypeEnum returnObjectType, String apiDesc, String apiTag, String apiReturnDesc) {
        this.methodName = methodName;
        this.httpMethod = httpMethod;
        this.authAnno = authAnno;
        this.apiUrlPath = apiUrlPath;
        this.beanPrefix = beanPrefix;
        this.formInfo = formInfo;
        this.returnObject = returnObject;
        this.returnObjectType = returnObjectType;
        this.apiDesc = apiDesc;
        this.apiTag = apiTag;
        this.apiReturnDesc = apiReturnDesc;
        if (StringUtils.isEmpty(methodName)) {
            throw new RuntimeException("methodName不得为空");
        }
    }

    public void doNew() throws IOException {
        System.out.println(this.toString());
        System.out.println("开始生成项目文件...");
        System.out.println("开始生成根目录...");
        this.makeRoot();
        System.out.println("开始生成根目录...DONE");
        System.out.println("开始生成代码文件...");
        this.makeMethod();
        if (this.formInfo.isCustom()) {
            System.out.println("开始生成Form文件...");
            this.makeForm();
            System.out.println("开始生成Form文件...DONE");
        }

        System.out.println("开始生成代码文件...DONE");
        System.out.println("生成项目文件...DONE");
        System.out.println("完成操作...");
        System.out.println("请到以下地址复制文件..." + CONF_DIR_PATH);
        super.openRoot(CONF_DIR_PATH);
    }

    private void makeForm() throws IOException {
        StringBuffer formAll = new StringBuffer();
        formAll.append(String.format("package form;\n\n"));
        formAll.append("@ApiModel\n");
        formAll.append(String.format("public class %s %s {\n", this.formInfo.getFormName(), StringUtils.isNotEmpty(this.formInfo.getExtendsForm()) ? "extends " + this.formInfo.getExtendsForm() : "implements ValidationForm"));
        StringBuffer formFieldSb = new StringBuffer();
        StringBuffer getSetSb = new StringBuffer();
        Iterator var4 = this.formInfo.getFieldList().iterator();

        while (var4.hasNext()) {
            FormFieldInfo fieldInfo = (FormFieldInfo) var4.next();
            String objectField = fieldInfo.getFieldName();
            String fieldType = fieldInfo.getFieldType();
            formFieldSb.append(String.format("\t@ApiModelProperty(value = \"%s\"%s)\n", fieldInfo.getDesc(), fieldInfo.isCanNull() ? "" : ",required = true"));
            if (!fieldInfo.isCanNull()) {
                formFieldSb.append(String.format("\t%s(message = \"请输入%s\")\n", fieldType.equals("String") ? "@NotBlank" : "@NotNull", fieldInfo.getDesc()));
            }

            formFieldSb.append(String.format("\tprivate %s %s;\n\n", fieldType, objectField));
            String var10000 = (objectField.charAt(0) + "").toUpperCase();
            String _objectField = var10000 + objectField.substring(1);
            getSetSb.append(String.format("\tpublic void set%s(%s %s){\n", _objectField, fieldType, objectField));
            getSetSb.append(String.format("\t\tthis.%s = %s;\n\t}\n\n", objectField, objectField));
            getSetSb.append(String.format("\tpublic %s get%s(){\n", fieldType, _objectField));
            getSetSb.append(String.format("\t\treturn %s;\n\t}\n\n", objectField));
        }

        formAll.append(formFieldSb).append("\n");
        formAll.append(getSetSb);
        formAll.append("}\n");
        AutoCodeUtils.makeFile(this.rootDir, this.formInfo.getFormName() + ".java", formAll.toString());
    }

    private void makeMethod() throws IOException {
        String content = AutoCodeUtils.readResource("newMethod/method.new");
        String returnObjectWithType = this.returnObject;
        String authAnnotation = "";
        if (StringUtils.isNotEmpty(this.authAnno)) {
            authAnnotation = "@AuthorityAnnotation(AuthorityAnnotationEnums." + this.authAnno + ")";
        }

        if (this.returnObjectType.equals(NewMethodHelper.ReturnTypeEnum.ALONE)) {
            returnObjectWithType = this.returnObject;
        } else if (this.returnObjectType.equals(NewMethodHelper.ReturnTypeEnum.LIST)) {
            returnObjectWithType = "List<" + this.returnObject + ">";
        } else if (this.returnObjectType.equals(NewMethodHelper.ReturnTypeEnum.PAGINATION)) {
            returnObjectWithType = "Pagination<" + this.returnObject + ">";
        }

        Map<String, String> param = new HashMap();
        param.put("methodName", this.methodName);
        param.put("httpMethod", this.httpMethod.desc);
        param.put("apiUrlPath", this.apiUrlPath);
        param.put("authAnnotation", authAnnotation);
        param.put("apiDesc", this.apiDesc);
        param.put("apiTag", this.apiTag);
        param.put("apiReturnDesc", this.apiReturnDesc);
        param.put("returnObject", this.returnObject);
        param.put("formName", this.formInfo.getFormName());
        param.put("returnObjectWithType", returnObjectWithType);
        param.put("remoteName", StringUtils.isEmpty(this.remoteName) ? this.beanPrefix + "Remote" : this.remoteName);
        param.put("feignName", StringUtils.isEmpty(this.feignName) ? this.beanPrefix + "Feign" : this.feignName);
        param.put("serviceName", StringUtils.isEmpty(this.serviceName) ? this.beanPrefix + "Service" : this.serviceName);
        param.put("daoName", StringUtils.isEmpty(this.daoName) ? this.beanPrefix + "Dao" : this.daoName);
        param.put("requestBody", this.httpMethod.equals(NewMethodHelper.HttpMethodEnum.GET) ? "" : "@RequestBody ");
        AutoCodeUtils.deleteFile(this.rootDir, "method.txt");
        content = AutoCodeUtils.replacePlaceHolder(content, param);
        AutoCodeUtils.makeFile(this.rootDir, "method.txt", content);
    }

    private void makeRoot() {
        File root = new File(CONF_DIR_PATH);
        String fileDirPath = CONF_DIR_PATH + File.separator + this.methodName;
        if (!root.exists()) {
            root.mkdir();
        }

        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        this.rootDir = fileDirPath;
    }

    public void setRemoteName(String remoteName) {
        this.remoteName = remoteName;
    }

    public void setFeignName(String feignName) {
        this.feignName = feignName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setDaoName(String daoName) {
        this.daoName = daoName;
    }

    static {
        String var10000 = System.getProperty("user.home");
        CONF_DIR_PATH = var10000 + File.separator + "byb.newmethod";
    }

    public static enum ReturnTypeEnum {
        ALONE("单独对象"),
        LIST("List对象"),
        PAGINATION("分页对象");

        private String desc;

        private ReturnTypeEnum(String desc) {
            this.desc = desc;
        }
    }

    public static enum HttpMethodEnum {
        GET("GetMapping"),
        POST("PostMapping");

        private String desc;

        private HttpMethodEnum(String desc) {
            this.desc = desc;
        }
    }
}

