package com.aatrox.generator.generator;

import com.aatrox.generator.bean.DefineBean;
import com.aatrox.generator.bean.DefineTemplateConfig;
import com.aatrox.generator.bean.GeneratorInfo;
import com.aatrox.generator.config.DefineConfig;
import com.aatrox.generator.freemark.AatroxTemplateEngine;
import com.aatrox.generator.freemark.DefineFreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2019/8/28
 */
public class GeneratorUtil {

    /**
     * 生成路径
     **/
    public final static String CONF_DIR_PATH = System.getProperty("user.home") + File.separator + "byb.mybatis-generator";

    public static void generator(GeneratorInfo generatorInfo){
        if(generatorInfo.isSingle()&&StringUtils.isEmpty(generatorInfo.getIncludeTableName())){
            System.out.println("要生成的表includeTableName不能为空");
            return;
        }
        AutoGenerator mpg = new AutoGenerator();
        //全局配置
        mpg.setGlobalConfig(globalConfigInit(generatorInfo));
        // 数据源配置
        mpg.setDataSource(dataSourceConfigInit(generatorInfo));
        // 包配置
        mpg.setPackageInfo(packageConfigInit(generatorInfo));
        //自定义模板设置
        if(generatorInfo.isSingle()) {
            setTemplateDefine(mpg, generatorInfo);
        }else{
            TemplateConfig templateConfig = new TemplateConfig()
                    .setMapper(null)
                    .setEntityKt(null)
                    .setEntity(null)
                    .setController(null)
                    .setService(null)
                    .setServiceImpl(null)
                    .setXml(null);
            mpg.setTemplate(templateConfig);
        }
        // 策略配置
        mpg.setStrategy(strategyConfigInit(generatorInfo));
        //模板配置
        mpg.setTemplateEngine(templateEngineInit(generatorInfo));
        mpg.execute();
    }



    /**
     * 全局配置
     */
    private static GlobalConfig globalConfigInit(GeneratorInfo generatorInfo){
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(CONF_DIR_PATH);
        gc.setAuthor(generatorInfo.getAuthor());
        gc.setOpen(generatorInfo.isDirectoryOpen());
        //是否覆盖
        gc.setFileOverride(generatorInfo.isFileOverride());
        //开启swagger
        gc.setSwagger2(generatorInfo.isSwagger2());
        // XML ResultMap
        gc.setBaseResultMap(generatorInfo.isXmlBaseResultMap());
        // XML ColumnList
        gc.setBaseColumnList(generatorInfo.isXmlBaseColumnList());
        return gc;
    }

    /**
     * 数据源配置
     * @param generatorInfo
     * @return
     */
    private static DataSourceConfig dataSourceConfigInit(GeneratorInfo generatorInfo){
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(generatorInfo.getDatabaseUrl());
        // dsc.setSchemaName("public");
        dsc.setDriverName(generatorInfo.getDatabaseDriverName());
        dsc.setUsername(generatorInfo.getDatabaseUserName());
        dsc.setPassword(generatorInfo.getDatabasePassword());
        return dsc;
    }

    /**
     * 包配置
     * @return
     */
    private static PackageConfig packageConfigInit(GeneratorInfo generatorInfo) {
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(generatorInfo.getPackageModuleName());
        pc.setParent(generatorInfo.getPackageParentPath());
        return pc;
    }


    /**
     * 模板配置
     * @Return
     */
    private static AbstractTemplateEngine templateEngineInit(GeneratorInfo generatorInfo){

        //模板配置
        if(generatorInfo.isSingle()) {
            DefineFreemarkerTemplateEngine templateEngine = new DefineFreemarkerTemplateEngine();
            DefineConfig defineConfig = new DefineConfig().setOpenMybatisPlus(generatorInfo.isOpenMybatisPlus());
            if (!generatorInfo.isOpenMybatisPlus()) {
                defineConfig.setOpenMybaitsPlusAnnotion(false);
            } else {
                defineConfig.setOpenMybaitsPlusAnnotion(generatorInfo.isOpenMybaitsPlusAnnotion());
            }
            defineConfig.setSpringCloud(generatorInfo.isSpringCloud());
            templateEngine.setDefineConfig(defineConfig);
            return templateEngine;
        }else{
            AatroxTemplateEngine templateEngine=new AatroxTemplateEngine();
            DefineConfig defineConfig = new DefineConfig().setOpenMybatisPlus(generatorInfo.isOpenMybatisPlus()).setOpenForm(generatorInfo.isOpenForm());
            if (!generatorInfo.isOpenMybatisPlus()) {
                defineConfig.setOpenMybaitsPlusAnnotion(false);
            } else {
                defineConfig.setOpenMybaitsPlusAnnotion(generatorInfo.isOpenMybaitsPlusAnnotion());
            }
            defineConfig.setSpringCloud(generatorInfo.isSpringCloud());
            templateEngine.setDefineConfig(defineConfig);
            templateEngine.setDefineBeanList(generatorInfo.getDefineBeanList());
            templateEngine.setDirPath(generatorInfo.getDirPath());
            templateEngine.setDefaultLongOutput(generatorInfo.isDefaultLongOutput());
            return templateEngine;
        }
    }

    /**
     * 策略配置
     * @return
     */
    private static StrategyConfig strategyConfigInit(GeneratorInfo generatorInfo) {
        StrategyConfig strategy = new StrategyConfig();
        //实体名策略
        strategy.setNaming(generatorInfo.getEntityNaming());
        //字段名策略
        strategy.setColumnNaming(generatorInfo.getColumnNaming());
        //  strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        strategy.setEntityLombokModel(generatorInfo.isEntityLombokModel());
        strategy.setRestControllerStyle(generatorInfo.isRestControllerStyle());
        // 公共父类
        strategy.setSuperControllerClass(generatorInfo.getControllerParentPath());
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns(StringUtils.isEmpty(generatorInfo.getSuperEntityColumns())?null:generatorInfo.getSuperEntityColumns().split(","));
        //strategy.setInclude(StringUtils.isEmpty(generatorInfo.getIncludeTableName())?null:generatorInfo.getIncludeTableName().split(","));
        //strategy.setExclude(StringUtils.isEmpty(generatorInfo.getExcludeTableName())?null:generatorInfo.getExcludeTableName().split(","));
        //使用一个个生产的方式
        strategy.setInclude(StringUtils.isEmpty(generatorInfo.getIncludeTableName())?null:generatorInfo.getIncludeTableName().split(","));
        strategy.setExclude(StringUtils.isEmpty(generatorInfo.getExcludeTableName())?null:generatorInfo.getExcludeTableName().split(","));
        strategy.setControllerMappingHyphenStyle(generatorInfo.isControllerMappingHyphenStyle());
        strategy.setTablePrefix(generatorInfo.getTablePrefix());
        //设置表的前缀
        strategy.setFieldPrefix(StringUtils.isEmpty(generatorInfo.getFieldPrefixs()) ? null : generatorInfo.getFieldPrefixs().split(","));
        //是否生成tableField的注解
        strategy.entityTableFieldAnnotationEnable(generatorInfo.isOpenMybaitsPlusAnnotion());
        //拓展setter方法
        strategy.setEntityBuilderModel(generatorInfo.isEntityBuilderModel());
        return strategy;
    }

    /**
     * 自定义模板设置
     * @param mpg
     * @param generatorInfo
     */
    private static void setTemplateDefine(AutoGenerator mpg,GeneratorInfo generatorInfo){
        final DefineTemplateConfig defineTemplateConfig = generatorInfo.getDefineTemplateConfig();
        Map<String, Object> templateMap = defineTemplateConfig.getTemplateMap();
        InjectionConfig injectionConfig = new InjectionConfig() {
            //自定义属性注入:abc
            //在.ftl(或者是.vm)模板中，通过${cfg.abc}获取属性
            @Override
            public void initMap() {
                this.setMap(templateMap);
            }
        };
        // 自定义配置会被优先输出
        List<DefineBean> defineBeanList = defineTemplateConfig.getDefineBeanList();
        injectionConfig.setFileOutConfigList(setConfigOutPut(generatorInfo,defineBeanList));
        mpg.setCfg(injectionConfig);
        TemplateConfig templateConfig = new TemplateConfig()
                                        .setMapper(null)
                                        .setEntityKt(null)
                                        .setEntity(null)
                                        .setController(null)
                                        .setService(null)
                                        .setServiceImpl(null)
                                        .setXml(null);
        mpg.setTemplate(templateConfig);
    }

    /**
     * 根据内容自动设置输出文件和路径
     *
     * @param defineBeanList
     * @return
     */
    private static List<FileOutConfig> setConfigOutPut(GeneratorInfo generatorInfo,List<DefineBean> defineBeanList) {
        if(defineBeanList==null){
            return null;
        }
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        defineBeanList.stream().forEach(defineBean -> {
            focList.add(new FileOutConfig(defineBean.getTemplatePath()) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                    if(generatorInfo.isDefaultLongOutput()) {
                        return CONF_DIR_PATH + "/" + tableInfo.getEntityName() + "/"+defineBean.getBeanType()
                                + "/" + defineBean.getFileName() + defineBean.getFileType();
                    }else{
                        return CONF_DIR_PATH + "/" + tableInfo.getEntityName()+ "/" + defineBean.getFileName() + defineBean.getFileType();
                    }
                }
            });
        });
        return focList;

    }

}
