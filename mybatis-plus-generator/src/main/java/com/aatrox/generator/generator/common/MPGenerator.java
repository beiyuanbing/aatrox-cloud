package com.aatrox.generator.generator.common;

import com.aatrox.generator.constants.MPConstants;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;

/**
 * 自动化生成对应的实体
 *
 * @author aatrox
 */
public class MPGenerator {
    /**
     * 生成路径
     **/
    public final static String CONF_DIR_PATH = System.getProperty("user.home") + File.separator + "byb.mybatis-generator";
    /**
     * 配置文件名字
     **/
    public final static String fileName = "generator.properties";

    public static void main(String[] args) {
        Map<String, String> property = getProperty();
        AutoGenerator autoGenerator = new AutoGenerator();
        /**全局配置**/
        GlobalConfig globalConfig = new GlobalConfig();
        //设置输出目录
        String outputDir = property.get(MPConstants.GLOBAL_OUTPUT_DIR);
        globalConfig.setOutputDir(StringUtils.isEmpty(outputDir) ? CONF_DIR_PATH : outputDir);
        //注解设置作者名
        globalConfig.setAuthor(property.get(MPConstants.DESCRIPTION_AUTH));
        // 是否覆盖同名文件，默认是false
        globalConfig.setFileOverride(true);
        // 不需要ActiveRecord特性的请改为false
        globalConfig.setActiveRecord(true);
        // XML ResultMap
        globalConfig.setBaseResultMap(true);
        //开启swagger
        globalConfig.setSwagger2(Boolean.valueOf(property.get(MPConstants.SWAGGER_OPEN)));
        // XML ColumnList
        globalConfig.setBaseColumnList(true);
        globalConfig.setMapperName("%sDao");
        globalConfig.setXmlName("%sDao");
        autoGenerator.setGlobalConfig(globalConfig);
        /**数据源配置**/
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        //设置数据库类型
        DbType dbType = DbType.valueOf(property.getOrDefault(MPConstants.DATABASE_TYPE, DbType.MYSQL.name()).toUpperCase());
        dataSourceConfig.setDbType(dbType);
        dataSourceConfig.setDriverName(property.get(MPConstants.DATABASE_DRIVERNAME));
        dataSourceConfig.setUrl(property.get(MPConstants.DATABAE_URL));
        dataSourceConfig.setUsername(property.get(MPConstants.DATABASE_USERNAME));
        dataSourceConfig.setPassword(property.get(MPConstants.DATABAE_PASSWORD));
        autoGenerator.setDataSource(dataSourceConfig);

        /**策略配置**/
        StrategyConfig strategy = new StrategyConfig();
        //表前缀
        String table_prefixs = property.get(MPConstants.TABLE_PREFIXS);
        strategy.setTablePrefix(StringUtils.isEmpty(table_prefixs) ? null : table_prefixs.split(","));
        //设置表的前缀
        String field_prefixs = property.get(MPConstants.FIELD_PREFIXS);
        strategy.setFieldPrefix(StringUtils.isEmpty(field_prefixs) ? null : field_prefixs.split(","));
        //是否生成tableField的注解
        strategy.entityTableFieldAnnotationEnable(true);
        //跳过视图生成
        strategy.setSkipView(Boolean.valueOf(property.get(MPConstants.SKIP_VIEW)));
        // 表名生成策略,是否使用驼峰写法
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //栏位驼峰写法
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //要生成的表
        String tableName = property.get(MPConstants.DATABAE_TABLENAME);
        strategy.setInclude(StringUtils.isEmpty(tableName) ? null : tableName.split(","));
        //排除生成的表
        if (StringUtils.isEmpty(tableName)) {
            String excludeTableName = property.get(MPConstants.DATABASE_EXCLUDE_TABLENAME);
            strategy.setExclude(StringUtils.isEmpty(tableName) ? null : excludeTableName.split(","));
        }
        //是否要前缀
        strategy.setEntityBooleanColumnRemoveIsPrefix(Boolean.valueOf(property.get(MPConstants.FIELD_REMOVE_PREFIX)));
        //是否使用lombok
        strategy.setEntityLombokModel(Boolean.valueOf(property.get(MPConstants.LOMBOK_OPEN)));
        //
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        autoGenerator.setStrategy(strategy);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(property.get(MPConstants.PACKAGE_PARENT));
        //pc.setModuleName("test");
        autoGenerator.setPackageInfo(pc);
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.execute();

    }

    /**
     * 得到配置文件信息
     *
     * @return
     */
    public static Map<String, String> getProperty() {
        //初始化大小
        Map<String, String> propertyMap = new HashMap<>(20);
        try {
            File file = ResourceUtils.getFile("classpath:" + fileName);
            Properties properties = PropertiesLoaderUtils.loadProperties(new FileUrlResource(file.getPath()));
            Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();
            if (entrySet != null && !entrySet.isEmpty()) {
                Iterator<Map.Entry<Object, Object>> iterator = entrySet.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Object, Object> next = iterator.next();
                    propertyMap.put(String.valueOf(next.getKey()), String.valueOf(next.getValue()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return propertyMap;
    }
}
