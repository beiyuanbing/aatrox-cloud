package com.aatrox.generator.constants;

/**
 * 配置文件的常量
 */
public class MPConstants {
    //数据库驱动名
    public static final String DATABASE_DRIVERNAME = "database.driverName";
    //数据库用户名
    public static final String DATABASE_USERNAME = "database.username";
    //数据库密码
    public static final String DATABAE_PASSWORD = "database.password";
    //数据库的url
    public static final String DATABAE_URL = "database.url";
    //数据库表名，多个使用,隔开
    public static final String DATABAE_TABLENAME = "database.tableName";
    //数据库表名不需要生成的
    public static final String DATABASE_EXCLUDE_TABLENAME = "database.exclude.tableName";
    //数据库类型
    public static final String DATABASE_TYPE = "database.type";
    //注释的开发者
    public static final String DESCRIPTION_AUTH = "description.auth";
    //包路径
    public static final String PACKAGE_PARENT = "package.parent";
    //是否使用lombok
    public static final String LOMBOK_OPEN = "lombok.open";
    //是否开启swagger
    public static final String SWAGGER_OPEN = "swagger.open";
    //输出目录
    public static final String GLOBAL_OUTPUT_DIR = "global.outputDir";
    //是否跳过视图生成
    public static final String SKIP_VIEW = "skip.view";
    //字段的前缀
    public static final String FIELD_PREFIXS = "field.prefixs";
    //字段是否需要前缀
    public static final String FIELD_REMOVE_PREFIX = "field.removePrefix";
    //表的前缀
    public static final String TABLE_PREFIXS = "table.prefixs";
}
