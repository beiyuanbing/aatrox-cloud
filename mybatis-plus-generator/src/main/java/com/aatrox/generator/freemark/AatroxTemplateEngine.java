package com.aatrox.generator.freemark;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2019/8/30
 */
public class AatroxTemplateEngine extends DefineAbstractTemplateEngine {
    private Configuration configuration;

    @Override
    public AatroxTemplateEngine init(ConfigBuilder configBuilder) {
        super.init(configBuilder);
        configuration = new Configuration(Configuration.VERSION_2_3_0);
        configuration.setDefaultEncoding(ConstVal.UTF8);
        configuration.setClassForTemplateLoading(AatroxTemplateEngine.class, StringPool.SLASH);
        return this;
    }
    @Override
    public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
        Template template = configuration.getTemplate(templatePath);
        FileOutputStream fileOutputStream = new FileOutputStream(new File(outputFile));
        template.process(objectMap, new OutputStreamWriter(fileOutputStream, ConstVal.UTF8));
        fileOutputStream.close();
        logger.debug("模板:" + templatePath + ";  文件:" + outputFile);
    }


    @Override
    public String templateFilePath(String filePath) {
        StringBuilder fp = new StringBuilder();
        fp.append(filePath).append(".ftl");
        return fp.toString();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public AatroxTemplateEngine setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }
}
