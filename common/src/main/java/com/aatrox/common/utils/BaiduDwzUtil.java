package com.aatrox.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 百度短链接
 *
 * @author apple
 */
public class BaiduDwzUtil {

    private final static String CREATE_API = "https://dwz.cn/admin/v2/create";
    // TODO:设置Token
    private final static String TOKEN = "c6d9cfe977041d5622d80aeb8661def2";


    /**
     * 创建短网址
     */
    public static String createShortUrl(ShortRequestModel requestModel) {
        if (requestModel == null) {
            return null;
        }
        String paramsJson = JSONObject.toJSONString(requestModel);
        try {
            String result = HttpUtils.post(CREATE_API, MapUtil.NEW("Content-Type", "application/json", "Token", TOKEN), paramsJson);
            // 抽取生成短网址
            UrlResponse urlResponse = JSONObject.parseObject(result).toJavaObject(UrlResponse.class);
            if (urlResponse.getCode() == 0) {
                return urlResponse.getShortUrl();
            } else {
                System.out.println(urlResponse.getErrMsg());
            }
            return null;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建短网址
     *
     * @param longUrl 长网址：即原网址
     *                termOfValidity
     *                有效期：1-year或long-term
     * @return 成功：短网址
     * 失败：返回空字符串
     */
    public static String createShortUrl(String longUrl, String termOfValidity) {
        String params = "{\"Url\":\"" + longUrl + "\"" + ",\"TermOfValidity\":\"" + termOfValidity + "\"}";

        BufferedReader reader = null;
        try {
            // 创建连接
            URL url = new URL(CREATE_API);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.setRequestProperty("Token", TOKEN); // 设置发送数据的格式");

            // 发起请求
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(params);
            out.flush();
            out.close();

            // 读取响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            String res = "";
            while ((line = reader.readLine()) != null) {
                res += line;
            }
            reader.close();

            // 抽取生成短网址
            UrlResponse urlResponse = new Gson().fromJson(res, UrlResponse.class);
            if (urlResponse.getCode() == 0) {
                return urlResponse.getShortUrl();
            } else {
                System.out.println(urlResponse.getErrMsg());
            }

            return null;
        } catch (IOException e) {
            // TODO
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
       /* String res = createShortUrl("http://www.baidu.com","1-year");
        System.out.println(res);*/
        ShortRequestModel requestModel = new ShortRequestModel().setUrl("http://www.baidu.com").setTermOfValidityEnum(TermOfValidityEnum.ONE_YEAR);
        System.out.println(createShortUrl(requestModel));
    }

    static class UrlResponse {
        @SerializedName("Code")
        @JSONField(name = "Code")
        private int code;

        @SerializedName("ErrMsg")
        @JSONField(name = "ErrMsg")
        private String errMsg;

        @SerializedName("LongUrl")
        @JSONField(name = "LongUrl")
        private String longUrl;

        @SerializedName("ShortUrl")
        @JSONField(name = "ShortUrl")
        private String shortUrl;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }

        public String getLongUrl() {
            return longUrl;
        }

        public void setLongUrl(String longUrl) {
            this.longUrl = longUrl;
        }

        public String getShortUrl() {
            return shortUrl;
        }

        public void setShortUrl(String shortUrl) {
            this.shortUrl = shortUrl;
        }
    }

    static class ShortRequestModel {
        @JSONField(name = "Url")
        private String url;

        @JSONField(name = "TermOfValidity")
        private String termOfValidity = "long-term";

        @JSONField(serialize = false)
        private TermOfValidityEnum termOfValidityEnum = TermOfValidityEnum.LONG_TERM;

        public String getUrl() {
            return url;
        }

        public ShortRequestModel setUrl(String url) {
            this.url = url;
            return this;
        }

        public String getTermOfValidity() {
            return termOfValidityEnum.getCode();
        }

        public ShortRequestModel setTermOfValidity(String termOfValidity) {
            this.termOfValidity = termOfValidity;
            return this;
        }

        public TermOfValidityEnum getTermOfValidityEnum() {
            return termOfValidityEnum;
        }

        public ShortRequestModel setTermOfValidityEnum(TermOfValidityEnum termOfValidityEnum) {
            this.termOfValidityEnum = termOfValidityEnum;
            return this;
        }
    }

    static enum TermOfValidityEnum {
        LONG_TERM("long-term", "永久"),
        ONE_YEAR("1-year", "一年");
        private String code;
        private String desc;

        TermOfValidityEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public TermOfValidityEnum setCode(String code) {
            this.code = code;
            return this;
        }

        public String getDesc() {
            return desc;
        }

        public TermOfValidityEnum setDesc(String desc) {
            this.desc = desc;
            return this;
        }
    }
}
