package com.aatrox.common.utils;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * @desc 常用的http请求
 * 里面也有ssl的http请求
 * @auth beiyuanbing
 * @date 2019-05-06 10:00
 **/
public class HttpUtils {

    /**
     * get
     *
     * @param host
     * @param path
     * @return
     * @throws Exception
     */
    public static HttpResponse doGet(String host, String path) throws Exception {
        return doGet(host, path, new HashMap<String, String>(), new HashMap<String, String>());

    }

    public static HttpResponse doGet(String host, String path,
                                     Map<String, String> headers,
                                     Map<String, String> querys)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpGet request = new HttpGet(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        return httpClient.execute(request);
    }

    /**
     * post com.aatrox.common.form
     *
     * @param host
     * @param path
     * @param headers
     * @param querys
     * @param bodys
     * @return
     * @throws Exception
     */
    public static HttpResponse doPost(String host, String path,
                                      Map<String, String> headers,
                                      Map<String, String> querys,
                                      Map<String, String> bodys)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        if (bodys != null) {
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

            for (String key : bodys.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
            formEntity.setContentType("application/x-www-com.aatrox.common.form-urlencoded; charset=UTF-8");
            request.setEntity(formEntity);
        }

        return httpClient.execute(request);
    }

    /**
     * Post String
     *
     * @param host
     * @param path
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPost(String host, String path,
                                      Map<String, String> headers,
                                      Map<String, String> querys,
                                      String body)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        if (!StringUtils.isEmpty(body)) {
            request.setEntity(new StringEntity(body, "utf-8"));
        }

        return httpClient.execute(request);
    }

    /**
     * Post stream
     *
     * @param host
     * @param path
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPost(String host, String path,
                                      Map<String, String> headers,
                                      Map<String, String> querys,
                                      byte[] body)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        if (body != null) {
            request.setEntity(new ByteArrayEntity(body));
        }

        return httpClient.execute(request);
    }

    /**
     * Put String
     *
     * @param host
     * @param path
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPut(String host, String path,
                                     Map<String, String> headers,
                                     Map<String, String> querys,
                                     String body)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpPut request = new HttpPut(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        if (!StringUtils.isEmpty(body)) {
            request.setEntity(new StringEntity(body, "utf-8"));
        }

        return httpClient.execute(request);
    }

    /**
     * Put stream
     *
     * @param host
     * @param path
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPut(String host, String path,
                                     Map<String, String> headers,
                                     Map<String, String> querys,
                                     byte[] body)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpPut request = new HttpPut(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        if (body != null) {
            request.setEntity(new ByteArrayEntity(body));
        }

        return httpClient.execute(request);
    }

    /**
     * Delete
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @return
     * @throws Exception
     */
    public static HttpResponse doDelete(String host, String path, String method,
                                        Map<String, String> headers,
                                        Map<String, String> querys)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpDelete request = new HttpDelete(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        return httpClient.execute(request);
    }

    private static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (!StringUtils.isEmpty(path)) {
            sbUrl.append(path);
        }
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, String> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (StringUtils.isEmpty(query.getKey()) && !StringUtils.isEmpty(query.getValue())) {
                    sbQuery.append(query.getValue());
                }
                if (!StringUtils.isEmpty(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (!StringUtils.isEmpty(query.getValue())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }

        return sbUrl.toString();
    }

    private static HttpClient wrapClient(String host) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        if (host.startsWith("https://")) {
            httpClient = createSSLClientDefault();
        }

        return httpClient;
    }

    private static CloseableHttpClient createSSLClientDefault() {

        SSLContext sslContext;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                @Override
                public boolean isTrusted(X509Certificate[] xcs, String string) {
                    return true;
                }
            }).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return HttpClients.createDefault();
    }

    /**
     * Encode a URL segment with special chars replaced.
     */
    public static String urlEncode(String value, String encoding) {
        if (value == null) {
            return "";
        }

        try {
            String encoded = URLEncoder.encode(value, encoding);
            return encoded.replace("+", "%20").replace("*", "%2A")
                    .replace("~", "%7E").replace("/", "%2F");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("FailedToEncodeUri", e);
        }
    }

    public static String urlDecode(String value, String encoding) {
        if (!StringUtils.isEmpty(value)) {
            return value;
        }

        try {
            return URLDecoder.decode(value, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("FailedToDecodeUrl", e);
        }
    }

    /**
     * Encode request parameters to URL segment.
     */
    public static String paramToQueryString(Map<String, String> params, String charset) {

        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder paramString = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> p : params.entrySet()) {
            String key = p.getKey();
            String value = p.getValue();

            if (!first) {
                paramString.append("&");
            }

            // Urlencode each request parameter
            paramString.append(urlEncode(key, charset));
            if (value != null) {
                paramString.append("=").append(urlEncode(value, charset));
            }

            first = false;
        }

        return paramString.toString();
    }

    private static final String ISO_8859_1_CHARSET = "iso-8859-1";
    private static final String UTF8_CHARSET = "utf-8";

    // To fix the bug that the header value could not be unicode chars.
    // Because HTTP headers are encoded in iso-8859-1,
    // we need to convert the utf-8(java encoding) strings to iso-8859-1 ones.
    public static void convertHeaderCharsetFromIso88591(Map<String, String> headers) {
        convertHeaderCharset(headers, ISO_8859_1_CHARSET, UTF8_CHARSET);
    }

    // For response, convert from iso-8859-1 to utf-8.
    public static void convertHeaderCharsetToIso88591(Map<String, String> headers) {
        convertHeaderCharset(headers, UTF8_CHARSET, ISO_8859_1_CHARSET);
    }

    private static void convertHeaderCharset(Map<String, String> headers,
                                             String fromCharset, String toCharset) {

        for (Map.Entry<String, String> header : headers.entrySet()) {
            if (header.getValue() == null) {
                continue;
            }

            try {
                header.setValue(new String(header.getValue().getBytes(fromCharset), toCharset));
            } catch (UnsupportedEncodingException e) {
                throw new IllegalArgumentException("Invalid charset name: " + e.getMessage(), e);
            }
        }
    }

    protected static final String defaultCharset = "UTF-8"; // 默认编码

    /**
     * 发起HTTP GET请求
     *
     * @param url URL
     * @return 响应结果
     */
    public static final String get(String url) {
        return execute(new HttpGet(url));
    }

    /**
     * checklink是做连接检查超时断开
     *
     * @param url
     * @param checkLink
     * @return
     */
    public static final String get(String url, boolean checkLink) {
        return execute(new HttpGet(url), true);
    }

    /**
     * 发起HTTP GET请求
     *
     * @param charset 编码
     * @param uri     URI
     * @param params  请求参数
     * @return 响应结果
     */
    public static final String get(String charset, String uri, List<? extends NameValuePair> params) {
        String queryString = URLEncodedUtils.format(params, charset);
        return get(uri + "?" + queryString);
    }

    /**
     * 发起HTTP GET请求
     *
     * @param charset 编码
     * @param uri     URI
     * @param params  请求参数
     * @return 响应结果
     */
    public static final String get(String charset, String uri, NameValuePair... params) {
        if (params.length == 0) {
            return get(uri);
        } else {
            return get(charset, uri, Arrays.asList(params));
        }
    }

    /**
     * 发起HTTP GET请求
     *
     * @param charset 编码
     * @param uri     URI
     * @param params  请求参数
     * @return 响应结果
     */
    public static final String get(String charset, String uri, Map<String, Object> params) {
        if (MapUtil.isEmpty(params)) {
            return get(uri);
        } else {
            return get(charset, uri, mapToNameValuePairs(params));
        }
    }

    /**
     * 增加超时断开连接方式
     */
    public static final String get(String uri, Map<String, Object> params, boolean checkLink) {
        String queryString = URLEncodedUtils.format(mapToNameValuePairs(params), defaultCharset);
        System.out.println(queryString);
        return get(uri + "?" + queryString, checkLink);
    }

    /**
     * 发起HTTP GET请求(UTF-8)
     *
     * @param uri    URI
     * @param params 请求参数
     * @return 响应结果
     */
    public static final String get(String uri, List<? extends NameValuePair> params) {
        return get(defaultCharset, uri, params);
    }

    /**
     * 发起HTTP GET请求(UTF-8)
     *
     * @param uri    URI
     * @param params 请求参数
     * @return 响应结果
     */
    public static final String get(String uri, NameValuePair... params) {
        return get(defaultCharset, uri, params);
    }

    /**
     * 发起HTTP GET请求(UTF-8)
     *
     * @param uri    URI
     * @param params 请求参数
     * @return 响应结果
     */
    public static final String get(String uri, Map<String, Object> params) {
        return get(defaultCharset, uri, params);
    }

    /**
     * 发起HTTP POST请求
     *
     * @param url URL
     * @return 响应结果
     */
    public static final String post(String url) {
        return execute(new HttpPost(url));
    }

    /**
     * 发起HTTP POST请求
     *
     * @param charset 编码
     * @param uri     URI
     * @param params  请求参数
     * @return 响应结果
     */
    public static final String post(String charset, String uri, List<? extends NameValuePair> params) {
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, charset);
            HttpPost httppost = new HttpPost(uri);
            httppost.setEntity(entity);
            return execute(httppost);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("不支持的编码类型: " + charset, e);
        }
    }

    /**
     * post请求,允许添加请求head
     *
     * @param url
     * @param headMap
     * @param contentMap
     * @return
     */
    public static String post(String url, Map<String, ?> headMap, Map<String, Object> contentMap) {
        String queryString = URLEncodedUtils.format(mapToNameValuePairs(contentMap), defaultCharset);
        return post(url, headMap, queryString);

    }

    /**
     * post请求,允许添加请求head
     *
     * @param url
     * @param headMap
     * @param content
     * @return
     */
    public static String post(String url, Map<String, ?> headMap, String content) {
        try {
            HttpPost httppost = new HttpPost(url);
            if (headMap != null && headMap.size() > 0) {
                headMap.forEach((k, v) -> httppost.setHeader(k, String.valueOf(v)));
            }
            httppost.setEntity(new StringEntity(content));
            return execute(httppost);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("不支持的编码类型: " + defaultCharset, e);
        }
    }

    /**
     * 发起HTTP POST请求
     *
     * @param charset 编码
     * @param uri     URI
     * @param params  请求参数
     * @return 响应结果
     */
    public static final String postByURI(String charset, String uri, Map<String, Object> params) {
        if (MapUtil.isEmpty(params)) {
            return post(uri);
        } else {
            return postByURI(charset, uri, mapToNameValuePairs(params));
        }
    }

    /**
     * 发起HTTP POST请求
     *
     * @param charset 编码
     * @param uri     URI
     * @param params  请求参数
     * @return 响应结果
     */
    public static final String postByURI(String charset, String uri, List<? extends NameValuePair> params) {
        try {
            URL url = new URL(uri);
            URI uris = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, charset);
            HttpPost httppost = new HttpPost(uris);
            httppost.setEntity(entity);
            return execute(httppost);
        } catch (Exception e) {
            if (e instanceof UnsupportedEncodingException) {
                throw new RuntimeException("不支持的编码类型: " + charset, e);
            } else {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * 发起HTTP POST请求
     *
     * @param charset 编码
     * @param uri     URI
     * @param params  请求参数
     * @return 响应结果
     */
    public static final String post(String charset, String uri, NameValuePair... params) {
        if (params.length == 0) {
            return post(uri);
        } else {
            return post(charset, uri, Arrays.asList(params));
        }
    }

    /**
     * 发起HTTP POST请求
     *
     * @param charset 编码
     * @param uri     URI
     * @param params  请求参数
     * @return 响应结果
     */
    public static final String post(String charset, String uri, Map<String, Object> params) {
        if (MapUtil.isEmpty(params)) {
            return post(uri);
        } else {
            return post(charset, uri, mapToNameValuePairs(params));
        }
    }

    /**
     * 发起HTTP POST请求(UTF-8)
     *
     * @param uri    URI
     * @param params 请求参数
     * @return 响应结果
     */
    public static final String post(String uri, List<? extends NameValuePair> params) {
        return post(defaultCharset, uri, params);
    }

    /**
     * 发起HTTP POST请求(UTF-8)
     *
     * @param uri    URI
     * @param params 请求参数
     * @return 响应结果
     */
    public static final String post(String uri, NameValuePair... params) {
        return post(defaultCharset, uri, params);
    }

    /**
     * 发起HTTP POST请求(UTF-8)
     *
     * @param uri    URI
     * @param params 请求参数
     * @return 响应结果
     */
    public static final String post(String uri, Map<String, Object> params) {
        return post(defaultCharset, uri, params);
    }

    /**
     * 发起HTTP请求
     *
     * @param request HTTP请求
     * @return 响应结果
     */
    private static final String execute(HttpUriRequest request, boolean checkLink) {
        String body;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        try {
            if (checkLink) {
                httpClientBuilder.setDefaultRequestConfig(RequestConfig.custom()
                        .setSocketTimeout(2000)
                        .setConnectTimeout(2000)
                        .setConnectionRequestTimeout(2000)
                        .setStaleConnectionCheckEnabled(true).build());
            }
            client = httpClientBuilder.build();
            response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                body = EntityUtils.toString(entity, "UTF-8");
                EntityUtils.consumeQuietly(entity);
            } else {
                String formatter = "HTTP请求失败: %s, status: %s %s";
                StatusLine statusLine = response.getStatusLine();
                String msg = String.format(formatter, request.getRequestLine(), statusLine.getStatusCode(), statusLine.getReasonPhrase());
                throw new RuntimeException(msg);
            }
        } catch (IOException e) {
            throw new RuntimeException("HTTP请求异常: " + request.getRequestLine(), e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
            }
        }

        return body;
    }

    private static String execute(HttpUriRequest request) {
        return execute(request, false);
    }

    /**
     * 将Map转为NameValuePair集合,将过滤空键或空值
     *
     * @param map Map对象
     * @return NameValuePair集合
     */
    private static final List<? extends NameValuePair> mapToNameValuePairs(Map<?, ?> map) {
        List<NameValuePair> nameValuePairs = new ArrayList<>(map.size());
        Object key, value;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            if (key != null && value != null) {
                BasicNameValuePair nameValuePair = new BasicNameValuePair(key.toString(), value.toString());
                nameValuePairs.add(nameValuePair);
            }
        }
        return nameValuePairs;
    }

    public static String doDelete(String data, String url) throws IOException {
        CloseableHttpClient client = null;
        HttpDeleteWithBody httpDelete = null;
        String result = null;
        try {
            client = HttpClients.createDefault();
            httpDelete = new HttpDeleteWithBody(url);

            httpDelete.addHeader("Content-type", "application/json; charset=utf-8");
            httpDelete.setHeader("Accept", "application/json; charset=utf-8");
            httpDelete.setEntity(new StringEntity(data));

            CloseableHttpResponse response = client.execute(httpDelete);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
        return result;
    }

    /**
     * 发送https请求
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return String
     */
    public static String execute(String requestUrl, String requestMethod, String outputStr, boolean decode) {
        requestMethod = StringUtils.isEmpty(requestMethod) ? "POST" : requestMethod;
        String result = null;
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes(defaultCharset));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            int len;
            byte[] arr = new byte[1024];
            while ((len = inputStream.read(arr)) != -1) {
                bos.write(arr, 0, len);
                bos.flush();
            }
            arr = bos.toByteArray();
            inputStream.close();
            inputStream = null;
            bos.close();
            bos = null;
            conn.disconnect();
            result = decode ? Base64Utils.encodeToString(arr) : new String(arr);
        } catch (Exception ce) {
            ce.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        HttpResponse httpResponse = doGet("https://www.baidu.com/s?wd=刘奕宁", null);
        System.out.println("Result:" + httpResponse.getEntity());
    }

    static class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
        public static final String METHOD_NAME = "DELETE";

        /**
         * 获取方法（必须重载）
         *
         * @return
         */
        @Override
        public String getMethod() {
            return METHOD_NAME;
        }

        public HttpDeleteWithBody(final String uri) {
            super();
            setURI(URI.create(uri));
        }

        public HttpDeleteWithBody(final URI uri) {
            super();
            setURI(uri);
        }

        public HttpDeleteWithBody() {
            super();
        }

    }

}
