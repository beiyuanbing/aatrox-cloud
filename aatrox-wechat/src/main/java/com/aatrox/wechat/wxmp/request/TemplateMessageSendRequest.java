package com.aatrox.wechat.wxmp.request;

import com.aatrox.common.utils.CommonMapUtil;
import com.aatrox.common.utils.HttpClientUtil;
import com.aatrox.wechat.wxmp.base.WxmpBaseRequest;
import com.aatrox.wechat.wxmp.base.WxmpBaseResponse;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.entity.ContentType;

import java.util.Iterator;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2020/1/16
 */
public class TemplateMessageSendRequest extends WxmpBaseRequest<WxmpBaseResponse> {
    private String toUser;
    private String templateId;
    private String formId;
    private String page;
    private Map<String, Object> data;
    private String emphasisKeyword;

    public TemplateMessageSendRequest(String toUser, String templateId, String formId) {
        this.toUser = toUser;
        this.templateId = templateId;
        this.formId = formId;
    }

    @Override
    protected boolean needAccessToken() {
        return true;
    }

    @Override
    protected String requestUrl() {
        StringBuffer sb = new StringBuffer("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send");
        sb.append("?access_token=").append(this.accessToken);
        return sb.toString();
    }

    @Override
    protected String toPostBodyString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser", this.toUser);
        jsonObject.put("template_id", this.templateId);
        jsonObject.put("form_id", this.formId);
        jsonObject.put("emphasis_keyword", this.emphasisKeyword);
        jsonObject.put("page", this.page);
        if (this.data != null) {
            JSONObject dataJ = new JSONObject();
            jsonObject.put("data", dataJ);
            Iterator var3 = this.data.entrySet().iterator();

            while (var3.hasNext()) {
                Map.Entry<String, Object> item = (Map.Entry) var3.next();
                JSONObject itemJ = new JSONObject();
                itemJ.put("value", item.getValue());
                dataJ.put((String) item.getKey(), itemJ);
            }
        }

        return jsonObject.toJSONString();
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void setEmphasisKeyword(String emphasisKeyword) {
        this.emphasisKeyword = emphasisKeyword;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Override
    protected WxmpBaseResponse doRequest() {
        HttpClientUtil.HttpParam httpParam = HttpClientUtil.HttpParam.makePostHttpParam(this.requestUrl());
        httpParam.setHeader(CommonMapUtil.NEW("Content-Type", ContentType.APPLICATION_JSON.getMimeType()));
        httpParam.setPostBodyString(this.toPostBodyString());
        HttpClientUtil.HttpResult result = HttpClientUtil.httpRequest(httpParam);
        return (WxmpBaseResponse) JSONObject.parseObject(result.contentToString(), WxmpBaseResponse.class);
    }
}