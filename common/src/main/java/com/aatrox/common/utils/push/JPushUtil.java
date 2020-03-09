package com.aatrox.common.utils.push;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import com.aatrox.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author aatrox
 * @desc 极光推送工具类
 * @date 2019-08-08 14:18
 **/
public class JPushUtil {

    private final static String MASTERSECRET = "3443fb06f7062018d4728557";
    private final static String APPKEY = "ad25456fb7059ab60e681c9f";
    private static Logger LOGGER = LoggerFactory.getLogger(JPushUtil.class);

    /**
     * private static JPushClient jpushClient;
     * static {
     * jpushClient = new JPushClient(MASTERSECRET, APPKEY);
     * <p>
     * }
     **/
    public static void main(String[] args) throws Exception {
        System.out.println(push(new PushModelRequest().setPlateForm(PushConstant.APP_PLATEFORM_ALL).setAppName("尘缘").setContent("深圳欢迎你")));
    }

    public static PushModelResponse push(PushModelRequest modelRequest) {
        LOGGER.info("调用极光平台推送");
        if (StringUtils.isEmpty(modelRequest.getMasterSecet())) {
            modelRequest.setMasterSecet(MASTERSECRET);
        }
        if (StringUtils.isEmpty(modelRequest.getAppKey())) {
            modelRequest.setAppKey(APPKEY);
        }
        StringBuilder aliasBuilder = new StringBuilder();
        /**
         * 指定用户推送
         */
        if (PushConstant.APP_PLATEFORM_IOS.equalsIgnoreCase(modelRequest.getPlateForm())) {
            aliasBuilder.append(modelRequest.isAliasPrefix() ? "" : PushConstant.IOS_ALIAS_PREFIX + modelRequest.getUserAlias());
        } else if (PushConstant.APP_PLATEFORM_ANDROID.equalsIgnoreCase(modelRequest.getPlateForm())) {
            aliasBuilder.append(modelRequest.isAliasPrefix() ? "" : PushConstant.IOS_ALIAS_PREFIX + modelRequest.getUserAlias());
        } else {
            aliasBuilder.append(modelRequest.isAliasPrefix() ? "" : PushConstant.IOS_ALIAS_PREFIX + modelRequest.getUserAlias() + ",");
            aliasBuilder.append(modelRequest.isAliasPrefix() ? "" : PushConstant.ANDROID_ALIAS_PREFIX + modelRequest.getUserAlias());
        }

        String[] aliasArray = null;
        String aliasStr = aliasBuilder.toString();
        if (StringUtils.isNotEmpty(aliasStr)) {
            aliasArray = aliasStr.split(",");
        }

        JPushClient jpushClient = new JPushClient(modelRequest.getMasterSecet(), modelRequest.getAppKey(), null, ClientConfig.getInstance());
        PushPayload payload = PushPayloadTools.buildPushObject_And_Message(modelRequest.getPlateForm(), modelRequest.getEnv(), aliasArray, modelRequest.getAppName(), modelRequest.getContent());
        //调用极光的方法
        return invokeJiGuangPush(jpushClient, payload);

    }

    /**
     *  * 共用的推送调用极光推送的接口创建好payload的对象类即可
     *  * @param paramsMap
     *  * @param jpushClient
     *  * @param payload
     *  * @return
     */
    public static PushModelResponse invokeJiGuangPush(JPushClient jpushClient, PushPayload payload) {
        PushModelResponse modelResponse = new PushModelResponse().setSendRequest(payload.toJSON().toString());
        LOGGER.info("send Request push:" + payload.toJSON());
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOGGER.info("Got result - " + result);
            modelResponse.setPushResult(result.toString());
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            LOGGER.error("Connection error, should retry later", e);
            modelResponse.setCode(500).setMsg("Connection error, should retry later");
        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            LOGGER.error("Should review the error, and fix the request", e);
            LOGGER.info("HTTP Status: " + e.getStatus());
            LOGGER.info("Error Code: " + e.getErrorCode());
            LOGGER.info("Error Message: " + e.getErrorMessage());
            modelResponse.setCode(e.getErrorCode()).setMsg(e.getErrorMessage());
        } finally {// 请求结束后，调用 NettyHttpClient 中的 close 方法，否则进程不会退出。
            if (jpushClient != null) {
                jpushClient.close();
            }
        }
        return modelResponse;
    }

    /**
     * @return
     * @desc 调用极光平台群推推送
     * @author aatrox
     * @params * @param modelrequest
     * @date 2019-08-08 15:33
     */
    public static PushModelResponse pushGroup(PushModelRequest modelRequest) {
        LOGGER.info("调用极光平台群推推送");
        JPushClient jpushClient = new JPushClient(modelRequest.getMasterSecet(), modelRequest.getAppKey(), null, ClientConfig.getInstance());
        PushPayload payload = PushPayloadTools.buildPushObject_BroadCast(modelRequest.getContent(), modelRequest.getEnv(), modelRequest.getPlateForm());
        //调用极光的方法
        return invokeJiGuangPush(jpushClient, payload);
    }
}
