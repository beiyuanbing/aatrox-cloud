package com.aatrox.wechat.wxpay.util;

import com.aatrox.common.utils.MD5Util;
import com.aatrox.wechat.util.XMLParser;
import com.alibaba.fastjson.annotation.JSONField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2020/6/30
 */
public class Signature {
    private final static Logger logger = LoggerFactory.getLogger(Signature.class);
    /**
     * 签名算法
     *
     * @param o 要参与签名的数据对象
     * @param partnerKey 商户key
     * @return 签名
     * @throws IllegalAccessException
     */
    public static String getSign(Object o, String partnerKey) throws IllegalAccessException {
        ArrayList<String> list = new ArrayList<>();
        if( o instanceof Map){
            Map<String,Object> oMap = (Map<String,Object>)o;
            for (Map.Entry<String,Object> entry : oMap.entrySet()) {
                if (entry.getValue() != null && !"".equals(entry.getValue().toString())) {
                    list.add(entry.getKey() + "=" + entry.getValue() + "&");
                }
            }
        }else{
            Class cls = o.getClass();
            while (cls != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
                Field[] fields = cls.getDeclaredFields();
                for (Field f : fields) {
                    JSONField an = f.getAnnotation(JSONField.class);
                    if(an!=null&&!an.serialize()){
                        continue;
                    }
                    f.setAccessible(true);
                    if (f.get(o) != null && !"".equals(f.get(o))) {
                        list.add(f.getName() + "=" + f.get(o) + "&");
                    }
                }
                cls = cls.getSuperclass(); //得到父类,然后赋给自己
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + partnerKey;
        logger.info("Sign Before MD5:" + result);
        result = MD5Util.getMD5(result.getBytes()).toUpperCase();
        logger.info("Sign Result:" + result);
        return result;
    }

    /**
     * 签名算法
     *
     * @param xmlStr 签名xml字符串
     * @return 签名
     * @throws IllegalAccessException
     */
    public static String getSignFromXmlStr(String xmlStr, String partnerKey) throws Exception{
        Map<String, String> map = XMLParser.getResponseFromXML(xmlStr,Map.class);
        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign", "");
        return getSign(map,partnerKey);
    }
}
