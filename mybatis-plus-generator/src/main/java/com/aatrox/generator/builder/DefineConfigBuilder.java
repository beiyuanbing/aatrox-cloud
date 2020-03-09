package com.aatrox.generator.builder;

import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;

/**
 * @author aatrox
 * @desc
 * @date 2019/8/30
 */
public class DefineConfigBuilder extends ConfigBuilder {
    public DefineConfigBuilder(PackageConfig packageConfig, DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig, TemplateConfig template, GlobalConfig globalConfig) {
        super(packageConfig, dataSourceConfig, strategyConfig, template, globalConfig);
    }
}
