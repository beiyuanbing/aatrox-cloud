package com.aatrox.autocode.project.test;

import com.aatrox.autocode.project.helper.NewServiceProjectHelper;
import com.aatrox.autocode.project.helper.NewWebProjectHelper;
import org.junit.Test;

import java.io.IOException;

public class GeneratorPorjectTest {

    @Test
    public void newService() throws IOException {
        NewServiceProjectHelper helper = new NewServiceProjectHelper("aatrox", "statistics", "service", "com");
        helper.setNeedDubbo(true);
        helper.setNeedOpen(true);
        helper.doNew();
    }

    @Test
    public void newWeb() throws IOException {
        NewWebProjectHelper helper = new NewWebProjectHelper("saas", "system", "mgr", "");
        helper.setNeedRedis(true);
        helper.setNeedAuth(true);
        helper.doNew();
    }
}
