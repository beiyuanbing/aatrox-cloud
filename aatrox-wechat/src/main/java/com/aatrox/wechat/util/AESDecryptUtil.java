package com.aatrox.wechat.util;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;

/**
 * @author aatrox
 * @desc
 * @date 2020/1/16
 */
public class AESDecryptUtil {
    static final String KEY_ALGORITHM = "AES";
    static final String algorithmStr = "AES/CBC/PKCS7Padding";
    private static final Logger logger = LoggerFactory.getLogger(AESDecryptUtil.class);

    public AESDecryptUtil() {
    }

    public static String decrypt(String encryptedData, String sessionKey, String iv) {
        try {
            byte[] sessionKeyBase64 = Base64.decodeBase64(sessionKey);
            byte[] encryptedDataBase64 = Base64.decodeBase64(encryptedData);
            byte[] ivBase64 = Base64.decodeBase64(iv);
            Key key = initKey(sessionKeyBase64);
            Cipher cipher = null;

            try {
                cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            } catch (NoSuchAlgorithmException var9) {
                logger.error(var9.getMessage(), var9);
            } catch (NoSuchPaddingException var10) {
                logger.error(var10.getMessage(), var10);
            }

            cipher.init(2, key, new IvParameterSpec(ivBase64));
            byte[] encryptedText = cipher.doFinal(encryptedDataBase64);
            return new String(encryptedText, "UTF-8");
        } catch (Exception var11) {
            logger.error(var11.getMessage(), var11);
            return null;
        }
    }

    private static Key initKey(byte[] keyBytes) {
        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }

        Security.addProvider(new BouncyCastleProvider());
        return new SecretKeySpec(keyBytes, "AES");
    }
}
