package com.aatrox.autocode.project.test;

import com.aatrox.autocode.project.helper.NewServiceProjectHelper;
import com.aatrox.autocode.project.helper.NewWebProjectHelper;

import java.io.IOException;

public class NewProjectTest {
    public NewProjectTest() {
    }

    public static void main(String[] args) throws IOException {
        newService();
    }

    private static void newService() throws IOException {
        NewServiceProjectHelper helper = new NewServiceProjectHelper("saas", "oa", "erp", "com");
        helper.setNeedDubbo(true);
        helper.doNew();
    }

    private static void newWeb() throws IOException {
        NewWebProjectHelper helper = new NewWebProjectHelper("saas", "system", "mgr", "");
        helper.setNeedRedis(true);
        helper.setNeedAuth(true);
        helper.doNew();
    }
}
