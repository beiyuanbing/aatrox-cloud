package com.aatrox.generator.freemark;

import com.aatrox.generator.config.DefineConfig;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Map;
import java.util.Set;

/**
 * @author aatrox
 * @desc 自定义的FreeMarkerFtl的模板，方便使用
 * @date 2019/8/23
 */
public class DefineFreemarkerTemplateEngine extends FreemarkerTemplateEngine {

    /**
     * 自定义配置
     */
    private DefineConfig defineConfig;
    /**
     * <p>
     * 渲染对象 MAP 信息
     * </p>
     * 重写了渲染对象，方便使用
     *
     * @param tableInfo 表信息对象
     * @return
     */
    @Override
    public Map<String, Object> getObjectMap(TableInfo tableInfo) {
        Set<String> importPackages = tableInfo.getImportPackages();
        if (!defineConfig.isOpenMybatisPlus()&&defineConfig != null && !defineConfig.isOpenMybaitsPlusAnnotion()) {
            importPackages.removeIf(x -> x.indexOf("com.baomidou.mybatisplus.annotation") >= 0);
        }
        Map<String, Object> objectMap = super.getObjectMap(tableInfo);
        // 自定义内容
        InjectionConfig injectionConfig = getConfigBuilder().getInjectionConfig();
        objectMap.putAll(injectionConfig.getMap());
        objectMap.put("openMybaitPlus",defineConfig.isOpenMybatisPlus());
        objectMap.put("openMybaitsPlusAnnotion",defineConfig.isOpenMybaitsPlusAnnotion());
        //开始springCloud模式
        objectMap.put("springCloud",defineConfig.isSpringCloud());
        return objectMap;
    }

    /***
     * 此处可做手脚重新设置每张表的内容
     * @return
     */

    public DefineConfig getDefineConfig() {
        return defineConfig;
    }

    public DefineFreemarkerTemplateEngine setDefineConfig(DefineConfig defineConfig) {
        this.defineConfig = defineConfig;
        return this;
    }
}
