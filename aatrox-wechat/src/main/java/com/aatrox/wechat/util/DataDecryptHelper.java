package com.aatrox.wechat.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author aatrox
 * @desc
 * @date 2020/1/16
 */
public class DataDecryptHelper {
    private static final Logger logger = LoggerFactory.getLogger(DataDecryptHelper.class);

    public DataDecryptHelper() {
    }

    public static String decryptPhone(String encryptedData, String sessionKey, String iv) {
        String result = AESDecryptUtil.decrypt(encryptedData, sessionKey, iv);
        if (result != null) {
            try {
                return JSONObject.parseObject(result).getString("purePhoneNumber");
            } catch (Exception var5) {
                logger.error(var5.getMessage(), var5);
            }
        }

        return null;
    }
}
