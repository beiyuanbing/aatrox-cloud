package com.aatrox.common.utils;

import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.HttpMethod;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ContentDisposition;

import javax.net.ssl.SSLContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2020/1/16
 */
public class HttpClientUtil {
    private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    public HttpClientUtil() {
    }

    public static void entityForDownload(byte[] content, HttpServletRequest request, HttpServletResponse response, String fileName, boolean closeOutputStream) throws IOException {
        InputStream stream = new ByteArrayInputStream(content);
        fileName = fileName.replace(" ", "");
        String agent = request.getHeader("USER-AGENT");
        if (agent != null && agent.toUpperCase().indexOf("MSIE") != -1) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            fileName = new String(fileName.getBytes(), "ISO-8859-1");
        }

        response.reset();
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/octet-stream; charset=UTF-8");
        ServletOutputStream out = null;

        try {
            out = response.getOutputStream();
            byte[] b = new byte[1024];

            int j;
            while((j = stream.read(b)) != -1) {
                out.write(b, 0, j);
            }

            out.flush();
        } finally {
            if (closeOutputStream && out != null) {
                out.close();
            }

        }
    }

    public static HttpClientUtil.HttpResult httpRequest(HttpClientUtil.HttpParam param) {
        try {
            if (param.getMethod() == HttpMethod.POST) {
                HttpPost post = initPost(param);
                return execute(post, param);
            } else {
                HttpGet get = initGet(param);
                return execute(get, param);
            }
        } catch (Exception var2) {
            throw new RuntimeException(var2.getMessage());
        }
    }

    public static HttpClientUtil.HttpResult get(String uri) {
        return get(uri, (String)null, false);
    }

    public static HttpClientUtil.HttpResult get(String uri, String contentType) {
        return get(uri, contentType, false);
    }

    public static HttpClientUtil.HttpResult get(String uri, String contentType, boolean isSSL) {
        HttpClientUtil.HttpParam param = HttpClientUtil.HttpParam.makeGetHttpParam(uri);
        if (StringUtils.isNotEmpty(contentType)) {
            param.setHeader(CommonMapUtil.NEW("Content-Type", contentType));
        }

        param.setNeedSsl(isSSL);
        return httpRequest(param);
    }

    public static HttpClientUtil.HttpResult post(String uri) {
        return post(uri, (String)null, false);
    }

    public static HttpClientUtil.HttpResult post(String uri, String contentType) {
        return post(uri, contentType, false);
    }

    public static HttpClientUtil.HttpResult post(String uri, String contentType, boolean isSSL) {
        HttpClientUtil.HttpParam httpParam = HttpClientUtil.HttpParam.makePostHttpParam(uri);
        if (StringUtils.isNotEmpty(contentType)) {
            httpParam.setHeader(CommonMapUtil.NEW("Content-Type", contentType));
        }

        httpParam.setNeedSsl(isSSL);
        return httpRequest(httpParam);
    }

    private static HttpGet initGet(HttpClientUtil.HttpParam param) throws URISyntaxException {
        URIBuilder builder = initUriBuilder(param);
        HttpGet httpGet = new HttpGet(builder.build());
        if (param.getHeader() != null && param.getHeader().size() > 0) {
            Iterator var3 = param.getHeader().entrySet().iterator();

            while(var3.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry)var3.next();
                httpGet.addHeader((String)entry.getKey(), (String)entry.getValue());
            }
        }

        httpGet.setProtocolVersion(param.getVersion());
        return httpGet;
    }

    private static URIBuilder initUriBuilder(HttpClientUtil.HttpParam param) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(param.uri);
        if (param.queryString != null && param.queryString.size() > 0) {
            builder.addParameters(param.queryString);
        }

        return builder;
    }

    private static HttpPost initPost(HttpClientUtil.HttpParam param) throws URISyntaxException, UnsupportedEncodingException {
        URIBuilder builder = initUriBuilder(param);
        HttpPost httpPost = new HttpPost(builder.build());
        if (param.getHeader() != null && param.getHeader().size() > 0) {
            Iterator var3 = param.getHeader().entrySet().iterator();

            while(var3.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry)var3.next();
                httpPost.addHeader((String)entry.getKey(), (String)entry.getValue());
            }
        }

        if (param.getPostFormData() != null) {
            List<NameValuePair> nvps = new ArrayList();
            JSONObject paramJson = (JSONObject)JSONObject.toJSON(param.getPostFormData());
            Iterator var5 = paramJson.entrySet().iterator();

            while(var5.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry)var5.next();
                Object value = entry.getValue();
                if (value != null) {
                    nvps.add(new BasicNameValuePair((String)entry.getKey(), value.toString()));
                }
            }

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        } else if (param.getPostBodyString() != null) {
            httpPost.setEntity(new StringEntity(param.getPostBodyString(), Consts.UTF_8));
        }

        httpPost.setProtocolVersion(param.getVersion());
        return httpPost;
    }

    public static void main(String[] args) {
        get("http://www.baidu.com");
    }

    private static HttpClientUtil.HttpResult execute(HttpRequestBase request, HttpClientUtil.HttpParam param) {
        CloseableHttpClient client = null;

        try {
            client = param.isNeedSsl() ? sslClient() : HttpClients.createDefault();
        } catch (Exception var29) {
            log.error("HttpClientUtil ssl wrong:" + var29.getMessage(), var29);
            return null;
        }

        CloseableHttpResponse rsp = null;
        boolean var4 = false;

        try {
            int timeOut = param.getTimeOut() > -1 ? param.getTimeOut() : 10000;
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeOut).setConnectionRequestTimeout(timeOut).setSocketTimeout(timeOut).build();
            request.setConfig(requestConfig);
            rsp = client.execute(request);
            int status = rsp.getStatusLine().getStatusCode();
            HttpEntity entity = rsp.getEntity();
            ContentType contentType = ContentType.get(entity);
            HttpClientUtil.HttpResult result = new HttpClientUtil.HttpResult(EntityUtils.toByteArray(entity), status, contentType);
            Header contentDisposition = rsp.getFirstHeader("Content-disposition");
            if (contentDisposition != null) {
                result.contentDisposition = ContentDisposition.parse(contentDisposition.getValue());
            }

            HttpClientUtil.HttpResult var11 = result;
            return var11;
        } catch (ClientProtocolException var30) {
            log.error("HttpClientUtil found ClientProtocolException:" + var30.getMessage(), var30);
        } catch (IOException var31) {
            log.error("HttpClientUtil found IOException:" + var31.getMessage(), var31);
        } finally {
            if (rsp != null) {
                try {
                    rsp.close();
                } catch (IOException var28) {
                    log.error(var28.getMessage());
                }
            }

            if (client != null) {
                try {
                    client.close();
                } catch (IOException var27) {
                    log.error(var27.getMessage());
                }
            }

        }

        return null;
    }

    private static CloseableHttpClient sslClient() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial((chain, authType) -> {
            return true;
        }).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, (String[])null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        return httpclient;
    }

    public static class HttpParam {
        private HttpMethod method;
        private Map<String, String> header;
        private boolean needSsl;
        private String uri;
        private Object postFormData;
        private String postBodyString;
        private List<NameValuePair> queryString = new ArrayList();
        private int timeOut = -1;
        private HttpVersion version;

        private HttpParam() {
        }

        public static HttpClientUtil.HttpParam makePostHttpParam(String uri) {
            HttpClientUtil.HttpParam param = new HttpClientUtil.HttpParam();
            param.method = HttpMethod.POST;
            param.uri = uri;
            return param;
        }

        public static HttpClientUtil.HttpParam makeGetHttpParam(String uri) {
            HttpClientUtil.HttpParam param = new HttpClientUtil.HttpParam();
            param.method = HttpMethod.GET;
            param.uri = uri;
            return param;
        }

        public void setHeader(Map<String, String> header) {
            this.header = header;
        }

        public void setNeedSsl(boolean needSsl) {
            this.needSsl = needSsl;
        }

        public Object getPostFormData() {
            return this.postFormData;
        }

        public void setPostFormData(Object postFormData) {
            this.postFormData = postFormData;
        }

        public HttpMethod getMethod() {
            return this.method;
        }

        public Map<String, String> getHeader() {
            return this.header;
        }

        public boolean isNeedSsl() {
            return this.needSsl;
        }

        public String getUri() {
            return this.uri;
        }

        public int getTimeOut() {
            return this.timeOut;
        }

        public void setTimeOut(int timeOut) {
            this.timeOut = timeOut;
        }

        public HttpVersion getVersion() {
            return this.version;
        }

        public void setVersion(HttpVersion version) {
            this.version = version;
        }

        public void addQueryString(String key, String value) {
            this.queryString.add(new BasicNameValuePair(key, value));
        }

        public String getPostBodyString() {
            return this.postBodyString;
        }

        public void setPostBodyString(String postBodyString) {
            this.postBodyString = postBodyString;
        }
    }

    public static class HttpResult {
        private byte[] content;
        private int status;
        private ContentType contentType;
        private ContentDisposition contentDisposition;

        public HttpResult(byte[] content, int status, ContentType contentType) {
            this.content = content;
            this.status = status;
            this.contentType = contentType;
        }

        public byte[] getContent() {
            return this.content;
        }

        public int getStatus() {
            return this.status;
        }

        public ContentType getContentType() {
            return this.contentType;
        }

        public ContentDisposition getContentDisposition() {
            return this.contentDisposition;
        }

        public void contentForDownload(HttpServletRequest request, HttpServletResponse response, String fileName, boolean closeOutputStream) throws IOException {
            HttpClientUtil.entityForDownload(this.content, request, response, fileName, closeOutputStream);
        }

        public String contentToString() {
            Charset charset = null;

            try {
                charset = this.contentType.getCharset();
            } catch (Exception var3) {
            }

            if (charset == null) {
                charset = Charset.forName("UTF-8");
            }

            return new String(this.content, charset);
        }
    }
}
