package com.aatrox.wechat.wxpay.util;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketTimeoutException;

/**
 * @author aatrox
 * @desc
 * @date 2020/6/30
 */
public class HttpsRequest {
    private final static Logger logger  = LoggerFactory.getLogger(HttpsRequest.class);

    /**H请求器的配置**/
    private RequestConfig requestConfig;

    /**HTTP请求器**/
    private CloseableHttpClient httpClient;

    public void setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * 通过Https往API post xml数据
     *
     * @param url    API地址
     * @param xmlObj 要提交的XML数据对象
     * @return API回包的实际数据
     */
    public String sendPostXml(String url, Object xmlObj){
        //解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

        //将要提交给API的数据对象转换成XML格式数据Post给API
        xStreamForRequestPostData.processAnnotations(xmlObj.getClass());
        xStreamForRequestPostData.alias("xml",xmlObj.getClass());
        String postDataXML = xStreamForRequestPostData.toXML(xmlObj);
        logger.info("[pay:weixin][post]");
        logger.info(postDataXML);
        return this.excute(url,postDataXML);
    }

    /**
     * 调用微信接口进行调用微信支付下单
     * @param url
     * @param object
     * @return
     */
    public String sendPostJson(String url, Object object){
        String jsonStr = JSON.toJSONString(object);
        return this.excute(url,jsonStr);

    }

    protected String excute(String url,String entityStr){
        String result = null;

        HttpPost httpPost = new HttpPost(url);
        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(entityStr, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
        //设置请求器的配置
        httpPost.setConfig(requestConfig);

        try {
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity, "UTF-8");

        } catch (ConnectionPoolTimeoutException e) {
            logger.error("[pay:weixin]"+"http get throw ConnectionPoolTimeoutException(wait time out)");

        } catch (ConnectTimeoutException e) {
            logger.error("[pay:weixin]"+"http get throw ConnectTimeoutException");

        } catch (SocketTimeoutException e) {
            logger.error("[pay:weixin]"+"http get throw SocketTimeoutException");

        } catch (Exception e) {
            logger.error("[pay:weixin]"+"http get throw Exception");

        } finally {
            httpPost.abort();
        }
        logger.info("[pay:weixin][return]"+result);
        return result;

    }
}
