package com.aatrox.wechat.wxmp.request;

/**
 * @author aatrox
 * @desc
 * @date 2020/1/16
 */

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
 * @desc 订阅消息的request
 * @date 2020/1/7
 */
public class SubscribeMessageSendRequest extends WxmpBaseRequest<WxmpBaseResponse> {
    /**
     * 目标用户的openId
     **/
    private String toUser;
    /**
     * 消息的模板id
     **/
    private String templateId;
    /**
     * 跳转页面
     **/
    private String page;
    /***模板对应的数据内容**/
    private Map<String, Object> data;

    public SubscribeMessageSendRequest(String toUser, String templateId) {
        this.toUser = toUser;
        this.templateId = templateId;
    }

    @Override
    protected boolean needAccessToken() {
        return true;
    }

    @Override
    protected String requestUrl() {
        StringBuffer sb = new StringBuffer("https://api.weixin.qq.com/cgi-bin/message/subscribe/send");
        sb.append("?access_token=").append(this.accessToken);
        return sb.toString();
    }

    @Override
    protected String toPostBodyString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser", this.toUser);
        jsonObject.put("template_id", this.templateId);
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

    @Override
    protected WxmpBaseResponse doRequest() {
        HttpClientUtil.HttpParam httpParam = HttpClientUtil.HttpParam.makePostHttpParam(this.requestUrl());
        httpParam.setHeader(CommonMapUtil.NEW("Content-Type", ContentType.APPLICATION_JSON.getMimeType()));
        httpParam.setPostBodyString(this.toPostBodyString());
        HttpClientUtil.HttpResult result = HttpClientUtil.httpRequest(httpParam);
        return (WxmpBaseResponse) JSONObject.parseObject(result.contentToString(), WxmpBaseResponse.class);
    }


    public String getToUser() {
        return toUser;
    }

    public SubscribeMessageSendRequest setToUser(String toUser) {
        this.toUser = toUser;
        return this;
    }

    public String getTemplateId() {
        return templateId;
    }

    public SubscribeMessageSendRequest setTemplateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    public String getPage() {
        return page;
    }

    public SubscribeMessageSendRequest setPage(String page) {
        this.page = page;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public SubscribeMessageSendRequest setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }
}