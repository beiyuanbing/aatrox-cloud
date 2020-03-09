package com.aatrox.generator.test;

import com.aatrox.generator.bean.DefineBean;
import com.aatrox.generator.bean.GeneratorInfo;
import com.aatrox.generator.enums.DBType;
import com.aatrox.generator.enums.DefineBeanType;
import com.aatrox.generator.generator.GeneratorUtil;
import org.junit.Test;

import java.util.List;

/**
 * @author aatrox
 * @desc
 * @date 2019/8/28
 */
public class GeneratorTest {

    /**
     * 单表生成
     */
    @Test
    public void testSingle(){
        GeneratorInfo generatorInfo = new GeneratorInfo()
                .setDataBaseType(DBType.MYSQL)
                .setDatabaseUrl("jdbc:mysql://localhost:3306/12306?useUnicode=true&useSSL=false&characterEncoding=utf8")
                .setDatabaseUserName("root")
                .setDatabasePassword("123456")
                .setIncludeTableName("City")
                .setPackageModuleName("service-12306")
                .setEntityName("City")
                .setDefaultLongOutput(false)
                .setSingle(true)
                .setSpringCloud(false)
                .setOpenMybatisPlus(true)
                .setOpenMybaitsPlusAnnotion(true);
        GeneratorUtil.generator(generatorInfo);
    }

    /**
     * 生成多个表,也可以实现单表生成
     */
    @Test
    public void testMulti(){
        GeneratorInfo generatorInfo = new GeneratorInfo()
                .setDataBaseType(DBType.MYSQL)
                .setDatabaseUrl("jdbc:mysql://localhost:3306/order?useUnicode=true&useSSL=false&characterEncoding=utf8")
                .setDatabaseUserName("root")
                .setDatabasePassword("123456")
                .setPackageModuleName("service-oa")
                //.setIncludeTableName("t_order_info")
                .setSpringCloud(true)
                .setDefaultLongOutput(false)
                //.setSingle(false)
                .setOpenMybatisPlus(false)
                .setOpenMybaitsPlusAnnotion(true)
                .setOpenForm(true)
                .setDefineBeanList(List.of(
                new DefineBean().setBeanPackage(DefineBeanType.MODEL.getBeanPackage()).setTemplatePath(DefineBeanType.MODEL.getTemplatePath()),
                new DefineBean().setBeanPackage(DefineBeanType.INSERTFORM.getBeanPackage()).setTemplatePath(DefineBeanType.INSERTFORM.getTemplatePath()),
                new DefineBean().setBeanPackage(DefineBeanType.EDITFORM.getBeanPackage()).setTemplatePath(DefineBeanType.EDITFORM.getTemplatePath()),
                new DefineBean().setBeanPackage(DefineBeanType.DAO.getBeanPackage()).setTemplatePath(DefineBeanType.DAO.getTemplatePath()),
                new DefineBean().setBeanPackage(DefineBeanType.XML.getBeanPackage()).setTemplatePath(DefineBeanType.XML.getTemplatePath()),
                new DefineBean().setBeanPackage(DefineBeanType.SERVICE.getBeanPackage()).setTemplatePath(DefineBeanType.SERVICE.getTemplatePath()),
                new DefineBean().setBeanPackage(DefineBeanType.SERVICEIMPL.getBeanPackage()).setTemplatePath(DefineBeanType.SERVICEIMPL.getTemplatePath()),
                new DefineBean().setBeanPackage(DefineBeanType.FEGION.getBeanPackage()).setTemplatePath(DefineBeanType.FEGION.getTemplatePath()),
                new DefineBean().setBeanPackage(DefineBeanType.API.getBeanPackage()).setTemplatePath(DefineBeanType.API.getTemplatePath()),
                new DefineBean().setBeanPackage(DefineBeanType.REMOTE.getBeanPackage()).setTemplatePath(DefineBeanType.REMOTE.getTemplatePath()),
                new DefineBean().setBeanPackage(DefineBeanType.CONTROLLER.getBeanPackage()).setTemplatePath(DefineBeanType.CONTROLLER.getTemplatePath()))
                );
        GeneratorUtil.generator(generatorInfo);
    }
}
