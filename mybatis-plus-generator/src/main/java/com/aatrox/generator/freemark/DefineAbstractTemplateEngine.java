package com.aatrox.generator.freemark;

import com.aatrox.generator.bean.DefineBean;
import com.aatrox.generator.config.DefineConfig;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author aatrox
 * @desc
 * @date 2019/8/30
 */
public abstract class DefineAbstractTemplateEngine extends AbstractTemplateEngine {

    protected List<DefineBean> defineBeanList;

    /**
     * 自定义配置
     */
    protected DefineConfig defineConfig;
    /***mkdir*/
    protected String dirPath;
    /** 标准长路径**/
    private boolean defaultLongOutput=false;
    /*****使用form的模式***/
    private boolean openForm=false;
    /**
     * <p>
     * 输出 java xml 文件
     * </p>
     */
    @Override
    public AbstractTemplateEngine batchOutput() {
        try {
            List<TableInfo> tableInfoList = getConfigBuilder().getTableInfoList();
            for (TableInfo tableInfo : tableInfoList) {
                //定制化
                Set<String> importPackages = tableInfo.getImportPackages();
                if (!defineConfig.isOpenMybatisPlus()&&defineConfig != null && !defineConfig.isOpenMybaitsPlusAnnotion()) {
                    importPackages.removeIf(x -> x.indexOf("com.baomidou.mybatisplus.annotation") >= 0);
                }

                Map<String, Object> objectMap = getObjectMap(tableInfo);
                //定制化
                objectMap.put("openMybaitPlus",defineConfig.isOpenMybatisPlus());
                objectMap.put("openMybaitsPlusAnnotion",defineConfig.isOpenMybaitsPlusAnnotion());
                //开始springCloud模式
                objectMap.put("springCloud",defineConfig.isSpringCloud());
                objectMap.put("openForm",defineConfig.isOpenForm());

                Map<String, String> pathInfo = getConfigBuilder().getPathInfo();
                TemplateConfig template = getConfigBuilder().getTemplate();
                // 自定义内容
                InjectionConfig injectionConfig = getConfigBuilder().getInjectionConfig();
                if (null != injectionConfig) {
                    injectionConfig.initMap();
                    objectMap.put("cfg",injectionConfig.getMap());

                    List<FileOutConfig> focList = injectionConfig.getFileOutConfigList();
                    if (CollectionUtils.isNotEmpty(focList)) {
                        for (FileOutConfig foc : focList) {
                            if (isCreate(FileType.OTHER, foc.outputFile(tableInfo))) {
                                writer(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
                            }
                        }
                    }
                }
                if(defineBeanList!=null&&defineBeanList.size()>0){
                    defineBeanList.stream().forEach(defineBean -> {
                        String fileName = getFileName(defineBean.getTemplatePath());
                        String key = fileName.substring(0, fileName.lastIndexOf("."));
                        defineBean.setFileName(tableInfo.getEntityName()+key).setOutPath(dirPath+"/"+tableInfo.getEntityName()
                                +(defaultLongOutput?("/"+key.toLowerCase()):"")
                                +"/"+tableInfo.getEntityName()+fileName);
                        objectMap.put(key,defineBean);
                        if (isCreate(FileType.OTHER, defineBean.getOutPath())) {
                            try {
                                writer(objectMap, defineBean.getTemplatePath(), defineBean.getOutPath());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    });

                }
                // Mp.java
                String entityName = tableInfo.getEntityName();
                if (null != entityName && null != pathInfo.get(ConstVal.ENTITY_PATH)) {
                    String entityFile = String.format((pathInfo.get(ConstVal.ENTITY_PATH) + File.separator + "%s" + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.ENTITY, entityFile)) {
                        writer(objectMap, templateFilePath(template.getEntity(getConfigBuilder().getGlobalConfig().isKotlin())), entityFile);
                    }
                }
                // MpMapper.java
                if (null != tableInfo.getMapperName() && null != pathInfo.get(ConstVal.MAPPER_PATH)) {
                    String mapperFile = String.format((pathInfo.get(ConstVal.MAPPER_PATH) + File.separator + tableInfo.getMapperName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.MAPPER, mapperFile)) {
                        writer(objectMap, templateFilePath(template.getMapper()), mapperFile);
                    }
                }
                // MpMapper.xml
                if (null != tableInfo.getXmlName() && null != pathInfo.get(ConstVal.XML_PATH)) {
                    String xmlFile = String.format((pathInfo.get(ConstVal.XML_PATH) + File.separator + tableInfo.getXmlName() + ConstVal.XML_SUFFIX), entityName);
                    if (isCreate(FileType.XML, xmlFile)) {
                        writer(objectMap, templateFilePath(template.getXml()), xmlFile);
                    }
                }
                // IMpService.java
                if (null != tableInfo.getServiceName() && null != pathInfo.get(ConstVal.SERVICE_PATH)) {
                    String serviceFile = String.format((pathInfo.get(ConstVal.SERVICE_PATH) + File.separator + tableInfo.getServiceName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.SERVICE, serviceFile)) {
                        writer(objectMap, templateFilePath(template.getService()), serviceFile);
                    }
                }
                // MpServiceImpl.java
                if (null != tableInfo.getServiceImplName() && null != pathInfo.get(ConstVal.SERVICE_IMPL_PATH)) {
                    String implFile = String.format((pathInfo.get(ConstVal.SERVICE_IMPL_PATH) + File.separator + tableInfo.getServiceImplName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.SERVICE_IMPL, implFile)) {
                        writer(objectMap, templateFilePath(template.getServiceImpl()), implFile);
                    }
                }
                // MpController.java
                if (null != tableInfo.getControllerName() && null != pathInfo.get(ConstVal.CONTROLLER_PATH)) {
                    String controllerFile = String.format((pathInfo.get(ConstVal.CONTROLLER_PATH) + File.separator + tableInfo.getControllerName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.CONTROLLER, controllerFile)) {
                        writer(objectMap, templateFilePath(template.getController()), controllerFile);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("无法创建文件，请检查配置信息！", e);
        }
        return this;
    }



    public String getFileName(String templatePath){
        int lastIndexOf = templatePath.lastIndexOf("/");
        lastIndexOf=lastIndexOf<0?0:lastIndexOf+1;
        return templatePath.substring(lastIndexOf).replace(".ftl", "");
    }


    public List<DefineBean> getDefineBeanList() {
        return defineBeanList;
    }

    public DefineAbstractTemplateEngine setDefineBeanList(List<DefineBean> defineBeanList) {
        this.defineBeanList = defineBeanList;
        return this;
    }

    public DefineConfig getDefineConfig() {
        return defineConfig;
    }

    public DefineAbstractTemplateEngine setDefineConfig(DefineConfig defineConfig) {
        this.defineConfig = defineConfig;
        return this;
    }

    public String getDirPath() {
        return dirPath;
    }

    public DefineAbstractTemplateEngine setDirPath(String dirPath) {
        this.dirPath = dirPath;
        return this;
    }

    public boolean isDefaultLongOutput() {
        return defaultLongOutput;
    }

    public DefineAbstractTemplateEngine setDefaultLongOutput(boolean defaultLongOutput) {
        this.defaultLongOutput = defaultLongOutput;
        return this;
    }
}
