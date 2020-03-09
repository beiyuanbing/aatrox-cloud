package com.aatrox.autocode.feignmapper.scanner;

import com.aatrox.autocode.feignmapper.param.FeignDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.AnnotationMetadataReadingVisitor;
import org.springframework.core.type.classreading.MetadataReader;

import java.util.ArrayList;
import java.util.List;

public class ClassPathScanner extends ClassPathScanningCandidateComponentProvider {
    private static ClassPathScanner scanner = new ClassPathScanner(true);

    public static ClassPathScanner getInstance() {
        return scanner;
    }

    private ClassPathScanner(boolean useDefaultFilters) {
        super(useDefaultFilters);
    }

    public List<FeignDefinition> doScan(String basePackage) {
        return this.findFeignDef(basePackage);
    }

    private List<FeignDefinition> findFeignDef(String basePackage) {
        ArrayList result = new ArrayList();

        try {
            String var10000 = this.resolveBasePackage(basePackage);
            String packageSearchPath = "classpath*:" + var10000 + "/**/*.class";
            Resource[] resources = ((ResourcePatternResolver) this.getResourceLoader()).getResources(packageSearchPath);
            Resource[] var5 = resources;
            int var6 = resources.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                Resource resource = var5[var7];
                if (resource.isReadable()) {
                    try {
                        MetadataReader metadataReader = this.getMetadataReaderFactory().getMetadataReader(resource);
                        AnnotationMetadataReadingVisitor classMeta = (AnnotationMetadataReadingVisitor) metadataReader.getClassMetadata();
                        FeignDefinition def = new FeignDefinition(classMeta);
                        if (def.getMethodDefinitions().size() > 0) {
                            result.add(def);
                        }
                    } catch (Exception var12) {
                    }
                }
            }
        } catch (Exception var13) {
        }

        return result;
    }
}
