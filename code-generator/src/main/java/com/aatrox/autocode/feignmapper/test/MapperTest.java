package com.aatrox.autocode.feignmapper.test;

import com.aatrox.autocode.feignmapper.helper.FeignToDocxHelper;

public class MapperTest {
    public MapperTest() {
    }

    public static void main(String[] args) throws Exception {
        FeignToDocxHelper helper = new FeignToDocxHelper("com.saas.oa.config.apilist.restful");
        helper.doMapper();
    }
}
