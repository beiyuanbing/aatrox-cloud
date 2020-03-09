package com.aatrox.autocode.feignmapper.test;

import com.aatrox.autocode.feignmapper.helper.FeignToDocxHelper;
import org.junit.Test;

public class GeneratorMapperTest {
    @Test
    public void mapper() throws Exception {
        FeignToDocxHelper helper = new FeignToDocxHelper("com.saas.oa.config.apilist.restful");
        helper.setNeedOpen(true);
        helper.doMapper();
    }
}
