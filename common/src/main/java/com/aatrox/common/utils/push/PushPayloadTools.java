package com.aatrox.common.utils.push;

import cn.jiguang.common.DeviceType;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author aatrox
 * @desc 推送平台设置
 * @date 2019-08-08 15:40
 **/
public class PushPayloadTools {
    private static Logger LOGGER = LoggerFactory.getLogger(PushPayloadTools.class);

    /**
     *  * 根据平台去广播所有消息广播消息
     *  * @param message
     *  * @param plateForm 平台 all ios  android
     *  * @return
     *  
     */
    public static PushPayload buildPushObject_BroadCast(String message, String env, String plateForm) {
        LOGGER.info("调用广播");
        PushPayload.Builder pushBuilder = PushPayload.newBuilder();
        Platform.Builder platformBuilder = Platform.newBuilder();
        if (PushConstant.APP_PLATEFORM_IOS.equalsIgnoreCase(plateForm)) {
            platformBuilder.addDeviceType(DeviceType.IOS).build();
        } else if (PushConstant.APP_PLATEFORM_ALL.equalsIgnoreCase(plateForm)) {
            platformBuilder.setAll(true).build();
        } else {//安卓
            platformBuilder.addDeviceType(DeviceType.Android).build();
        }
        //生产
        if (PushConstant.ENVIRONMENT_PRO.equalsIgnoreCase(env)) {
            pushBuilder.setOptions(Options.newBuilder().setApnsProduction(true).build());
        } else {
            pushBuilder.setOptions(Options.newBuilder().setApnsProduction(false).build());
        }
        return pushBuilder.setPlatform(platformBuilder.build())
                .setAudience(Audience.all())
                .setNotification(Notification.alert(message))
                .setMessage(Message.content(message))
                .build();
    }

    /**
     *  * 创建发送平台
     *  * ios 和安卓都发送，aliasArray推送别名对象，title是标题，message是消息内容
     *  * @param aliasArray
     *  * @param plateForm 平台 all ios  android
     *  * @param title
     *  * @param message
     *  * @return
     *  
     */
    public static PushPayload buildPushObject_And_Message(String plateForm, String env, String[] aliasArray, String title, final String message) {
        LOGGER.info("调用平台推送");
        Platform.Builder platformBuilder = Platform.newBuilder();
//推送通知
        Notification.Builder notificationBuilder = Notification.newBuilder();
        if (PushConstant.APP_PLATEFORM_IOS.equalsIgnoreCase(plateForm)) {
            platformBuilder.addDeviceType(DeviceType.IOS).build();
            notificationBuilder.addPlatformNotification(IosNotification.newBuilder().setAlert(message).disableBadge().build());

        } else if (PushConstant.APP_PLATEFORM_ALL.equalsIgnoreCase(plateForm)) {
            //进行全平台推送
            platformBuilder.setAll(true).build();
            notificationBuilder.addPlatformNotification(IosNotification.newBuilder().setAlert(message).disableBadge().build())
                    .addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).setAlert(message).build());
        } else {
            //安卓
            platformBuilder.addDeviceType(DeviceType.Android).build();
            notificationBuilder.addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).setAlert(message).build());
        }

        Audience.Builder audiBuilder = null;
        ;
        if (aliasArray != null && aliasArray.length > 0) {
            audiBuilder = Audience.newBuilder().addAudienceTarget(AudienceTarget.alias(aliasArray));
        } else {
            audiBuilder = Audience.newBuilder().setAll(true);
        }

        PushPayload.Builder pushBuilder = PushPayload.newBuilder();
        if (audiBuilder != null) {
            pushBuilder.setAudience(audiBuilder.build());
        }

        //生产
        if (PushConstant.ENVIRONMENT_PRO.equalsIgnoreCase(env)) {
            pushBuilder.setOptions(Options.newBuilder().setApnsProduction(true).build());
        } else {
            pushBuilder.setOptions(Options.newBuilder().setApnsProduction(false).build());
        }
        PushPayload pushPayload = pushBuilder
                .setPlatform(platformBuilder.build())
                .setAudience(audiBuilder != null ? audiBuilder.build() : null)
                .setNotification(notificationBuilder.build())
                .setMessage(Message.content(message))
                .build();
        return pushPayload;

    }
}
