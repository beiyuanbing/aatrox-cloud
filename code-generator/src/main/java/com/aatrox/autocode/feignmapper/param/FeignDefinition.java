package com.aatrox.autocode.feignmapper.param;

import com.aatrox.common.utils.StringUtils;
import com.aatrox.model.ModelDocDef;
import com.aatrox.model.ModelPropertyDef;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.core.type.classreading.AnnotationMetadataReadingVisitor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class FeignDefinition {
    private String className;
    private List<FeignMethodDefinition> methodDefinitions = new ArrayList();

    private String getShortClassName(String className) {
        return className.indexOf(".") > -1 ? className.substring(className.lastIndexOf(".") + 1) : className;
    }

    public FeignDefinition(AnnotationMetadataReadingVisitor classMeta) {
        this.className = this.getShortClassName(classMeta.getClassName());

        try {
            Class type = Class.forName(classMeta.getClassName());
            Method[] methods = type.getDeclaredMethods();
            Method[] var4 = methods;
            int var5 = methods.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                Method method = var4[var6];
                ModelDocDef def = (ModelDocDef) method.getDeclaredAnnotation(ModelDocDef.class);
                if (def != null) {
                    FeignDefinition.FeignMethodDefinition methodDef = new FeignDefinition.FeignMethodDefinition();
                    methodDef.methodName = method.getName();
                    methodDef.desc = def.value();
                    this.analysisReturnType(methodDef, method.getGenericReturnType().toString());
                    this.analysisParameterType(methodDef, method.getParameters());
                    this.methodDefinitions.add(methodDef);
                }
            }
        } catch (Exception var10) {
        }

    }

    private void analysisParameterType(FeignDefinition.FeignMethodDefinition methodDef, Parameter[] parameters) {
        if (parameters != null) {
            Parameter[] var3 = parameters;
            int var4 = parameters.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Parameter p = var3[var5];
                methodDef.parameterType = this.getShortClassName(p.getType().toString());
                Class type = p.getType();
                ApiModel apiModel = (ApiModel) type.getDeclaredAnnotation(ApiModel.class);
                if (apiModel != null) {
                    methodDef.parameterTypeDesc = apiModel.value();
                }

                Field[] fields = type.getDeclaredFields();
                if (fields != null) {
                    Field[] var10 = fields;
                    int var11 = fields.length;

                    for (int var12 = 0; var12 < var11; ++var12) {
                        Field f = var10[var12];
                        ApiModelProperty property = (ApiModelProperty) f.getDeclaredAnnotation(ApiModelProperty.class);
                        ModelPropertyDef pd = (ModelPropertyDef) f.getDeclaredAnnotation(ModelPropertyDef.class);
                        StringBuffer sbKey = new StringBuffer();
                        if (property != null && !property.hidden()) {
                            StringBuffer sbInfo = new StringBuffer(property.value());
                            if (property.required()) {
                                sbKey.append("[必填]");
                            } else {
                                sbKey.append("[选填]");
                            }

                            if (StringUtils.isNotEmpty(property.example())) {
                                sbInfo.append("{").append(property.example()).append("}");
                            }

                            methodDef.parameterMap.put(sbKey.append(f.getName()).toString(), sbInfo.toString());
                        } else if (pd != null) {
                            if (pd.required()) {
                                sbKey.append("[必填]");
                            } else {
                                sbKey.append("[选填]");
                            }

                            methodDef.parameterMap.put(sbKey.append(f.getName()).toString(), pd.value());
                        }
                    }
                }
            }
        }

    }

    private void analysisReturnType(FeignDefinition.FeignMethodDefinition methodDef, String returnType) {
        int startIndex = returnType.indexOf("<");

        String genericType;
        for (genericType = null; startIndex > -1; startIndex = returnType.indexOf("<")) {
            int endIndex = returnType.lastIndexOf(">");
            genericType = returnType.substring(0, startIndex);
            returnType = returnType.substring(startIndex + 1, endIndex);
        }

        if (genericType != null) {
            methodDef.genericType = this.getShortClassName(genericType);
        }

        methodDef.returnType = this.getShortClassName(returnType);

        try {
            Class type = Class.forName(returnType);
            ApiModel apiModel = (ApiModel) type.getDeclaredAnnotation(ApiModel.class);
            if (apiModel != null) {
                methodDef.returnTypeDesc = StringUtils.isEmpty(apiModel.value()) ? apiModel.description() : apiModel.value();
            }

            Field[] fields = type.getDeclaredFields();
            if (fields != null) {
                Field[] var8 = fields;
                int var9 = fields.length;

                for (int var10 = 0; var10 < var9; ++var10) {
                    Field f = var8[var10];
                    ApiModelProperty property = (ApiModelProperty) f.getDeclaredAnnotation(ApiModelProperty.class);
                    ModelPropertyDef pd = (ModelPropertyDef) f.getDeclaredAnnotation(ModelPropertyDef.class);
                    if (property != null && !property.hidden()) {
                        methodDef.returnMap.put(f.getName(), property.value());
                    } else if (pd != null) {
                        methodDef.returnMap.put(f.getName(), pd.value());
                    }
                }
            }
        } catch (ClassNotFoundException var14) {
        }

    }

    public String getClassName() {
        return this.className;
    }

    public List<FeignDefinition.FeignMethodDefinition> getMethodDefinitions() {
        return this.methodDefinitions;
    }

    public static class FeignMethodDefinition {
        private String methodName;
        private String desc;
        private String genericType;
        private String returnType;
        private String returnTypeDesc;
        private String parameterType;
        private String parameterTypeDesc;
        private LinkedHashMap<String, String> returnMap = new LinkedHashMap();
        private LinkedHashMap<String, String> parameterMap = new LinkedHashMap();

        public FeignMethodDefinition() {
        }

        public String getMethodName() {
            return this.methodName;
        }

        public String getDesc() {
            return this.desc;
        }

        public String getGenericType() {
            return this.genericType;
        }

        public String getReturnType() {
            return this.returnType;
        }

        public String getReturnTypeDesc() {
            return this.returnTypeDesc;
        }

        public String getParameterType() {
            return this.parameterType;
        }

        public String getParameterTypeDesc() {
            return this.parameterTypeDesc;
        }

        public LinkedHashMap<String, String> getReturnMap() {
            return this.returnMap;
        }

        public LinkedHashMap<String, String> getParameterMap() {
            return this.parameterMap;
        }
    }
}

