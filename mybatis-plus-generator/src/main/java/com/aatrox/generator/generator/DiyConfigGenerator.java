package com.aatrox.generator.generator;

import com.aatrox.generator.bean.DefineBean;
import com.aatrox.generator.config.DefineConfig;
import com.aatrox.generator.freemark.DefineFreemarkerTemplateEngine;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;

/**
 * 自动化生成对应的实体
 * 自定义的生成的代码工具类
 *
 * @author aatrox
 */
public class DiyConfigGenerator {
    /**
     * 生成路径
     **/
    public final static String CONF_DIR_PATH = System.getProperty("user.home") + File.separator + "byb.mybatis-generator";
    /**
     * 配置文件名字
     **/
    public final static String fileName = "generator.properties";

    public static void main(String[] args) {// 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(CONF_DIR_PATH);
        gc.setAuthor("jobob");
        gc.setOpen(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        //开启swagger
        gc.setSwagger2(true);
        // XML ColumnList
        gc.setBaseColumnList(true);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/order?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(null);
        pc.setParent(null);
        mpg.setPackageInfo(pc);

        final DefineBean model = new DefineBean().setFileName("UserInfoModel").setBeanPackage("com.aatrox.model").setTemplatePath("template/diy/entity/Model.java.ftl");
        final DefineBean dao = new DefineBean().setFileName("UserInfoDao").setBeanPackage("com.aatrox.dao").setTemplatePath("template/diy/Dao.java.ftl");
        final DefineBean xml = new DefineBean().setFileName("UserInfoDao").setBeanPackage("com.aatrox.xml").setTemplatePath("template/diy/Dao.xml.ftl").setFileType(StringPool.DOT_XML);
        final DefineBean service = new DefineBean().setFileName("UserInfoService").setBeanPackage("com.aatrox.service").setTemplatePath("template/diy/Service.java.ftl");
        final DefineBean serviceImpl = new DefineBean().setFileName("UserInfoServiceImpl").setBeanPackage("com.aatrox.service.impl").setTemplatePath("template/diy/ServiceImpl.java.ftl");
        final DefineBean controller = new DefineBean().setFileName("UserInfoController").setBeanPackage("com.aatrox.controller").setTemplatePath("template/diy/Controller.java.ftl");
        InjectionConfig injectionConfig = new InjectionConfig() {
            //自定义属性注入:abc
            //在.ftl(或者是.vm)模板中，通过${cfg.abc}获取属性
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("Model", model);
                map.put("Dao", dao);
                map.put("Xml", xml);
                map.put("Service", service);
                map.put("ServiceImpl", serviceImpl);
                map.put("Controller", controller);
                this.setMap(map);
            }
        };
        // 自定义输出配置
        List<DefineBean> defineBeanList = Arrays.asList(model, xml, dao,service,serviceImpl,controller);
        // 自定义配置会被优先输出
        injectionConfig.setFileOutConfigList(setConfigOutPut(defineBeanList));
        mpg.setCfg(injectionConfig);

        // 配置自定义输出模板
        TemplateConfig templateConfig = new TemplateConfig()
                .setEntity(null).setService(null).setController(null).setXml(null).setServiceImpl(null).setMapper(null);

        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //  strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        // strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
        //strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("id");
        //strategy.setEntityLombokModel(false);
        strategy.setInclude("t_user_info");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("t_");
        //设置表的前缀
        String field_prefixs = "f,f_";
        strategy.setFieldPrefix(StringUtils.isEmpty(field_prefixs) ? null : field_prefixs.split(","));
        mpg.setStrategy(strategy);
        DefineFreemarkerTemplateEngine templateEngine = new DefineFreemarkerTemplateEngine();
        templateEngine.setDefineConfig(new DefineConfig().setOpenMybaitsPlusAnnotion(false));
        mpg.setTemplateEngine(templateEngine);
        mpg.execute();
    }

    /**
     * 根据内容自动设置输出文件和路径
     *
     * @param defineBeanList
     * @return
     */
    public static List<FileOutConfig> setConfigOutPut(List<DefineBean> defineBeanList) {
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        defineBeanList.stream().forEach(defineBean -> {
            focList.add(new FileOutConfig(defineBean.getTemplatePath()) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                    return CONF_DIR_PATH + "/" + tableInfo.getServiceName() + "/src/main/" + defineBean.getBeanPackage().replace(".", "/")
                            + "/" + defineBean.getFileName() + defineBean.getFileType();
                }
            });
        });
        return focList;

    }

}
