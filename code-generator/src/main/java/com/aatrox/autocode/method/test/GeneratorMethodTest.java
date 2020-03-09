package com.aatrox.autocode.method.test;

import com.aatrox.autocode.method.helper.NewMethodHelper;
import com.aatrox.autocode.method.param.FormInfo;
import org.junit.Test;

import java.io.IOException;

public class GeneratorMethodTest {
    @Test
    public void newMethod() throws IOException {
        FormInfo formInfo = new FormInfo("IdsForm", false);
        NewMethodHelper helper = new NewMethodHelper("readMessage", NewMethodHelper.HttpMethodEnum.POST, "", "/notify/message", "message", formInfo, formInfo.getFormName(), NewMethodHelper.ReturnTypeEnum.ALONE, "消息已读", "ApiSwaggerTags.NOTIFY_TAG", "消息已读传参");
        helper.setRemoteName("notifyRemote");
        helper.doNew();
    }
}
