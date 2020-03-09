package com.aatrox.common.utils;

import com.aatrox.common.form.ADForm;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    /**
     * 解决Json序列化数字空值被转成0值
     *
     * @param code
     * @param message
     * @param object
     * @return
     */
    public static String returnJsonNullAbleInfo(String code, String message, Object object) {
        Map<String, Object> result = new HashMap();
        result.put("status", code);
        result.put("message", message);
        result.put("result", object);
        return JSON.toJSONString(result, SerializeConfig.globalInstance, SerializerFeature.WriteNullStringAsEmpty);
    }

    /**
     * 将JSON字符串转为Bean
     *
     * @param jsonStr
     * @param
     * @return Bean
     */
    @SuppressWarnings("unchecked")
    public static final <T> T json2Bean(String jsonStr, T t) {
        JSONObject jsonObj = JSONObject.parseObject(jsonStr);
        jsonObj.keySet().stream().forEach(key -> {
            try {
                Method method = ReflectionUtils.getDeclaredMethod(t.getClass(), "get" + getKeyMethod(key), null);
                if (method == null) {
                    return;
                }
                if (List.class.equals(method.getReturnType())) {
                    Class genericClass = getGenericClass(method.getGenericReturnType());
                    Object o = JSONObject.parseArray(jsonObj.getJSONArray(key).toJSONString(), genericClass);
                    ReflectionUtils.setFieldValue(t, key, o);
                } else if (ReflectionUtils.isBaseType(method.getReturnType())) {
                    //基础类型的转换
                    ReflectionUtils.setFieldValue(t, key, jsonObj.getObject(key, method.getReturnType()));
                } else if (ReflectionUtils.isEnum(ReflectionUtils.getDeclaredField(t, key))) {
                    //枚举类型,可以
                    ReflectionUtils.setFieldValue(t, key, jsonObj.getObject(key, method.getReturnType()));
                } else {
                    //普通对象类型
                    Object object = JSONObject.toJavaObject(jsonObj.getJSONObject(key), method.getReturnType());
                    ReflectionUtils.setFieldValue(t, key, object);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return t;
    }

    public static Class getGenericClass(Type type) {
        Class claz = null;
        String clazStr = type.toString();
        int i = clazStr.indexOf("<");
        if (i > 0) {
            String genericClassStr = clazStr.substring(i + 1, clazStr.length() - 1);
            try {
                claz = Class.forName(genericClassStr);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return claz;
    }

    public static String getKeyMethod(String key) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] charArray = key.toCharArray();
        int index = 0;
        for (char ch : charArray) {
            stringBuilder.append(index++ == 0 ? (ch + "").toUpperCase() : (ch + ""));
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        String jsonStr = "{\"adList\":[{\"cityId\":\"35\",\"cityName\":\"北京\"},{\"cityId\":\"37\",\"cityName\":\"天津\"},{\"cityId\":\"39\",\"cityName\":\"石家庄\"},{\"cityId\":\"40\",\"cityName\":\"唐山\"},{\"cityId\":\"41\",\"cityName\":\"秦皇岛\"},{\"cityId\":\"42\",\"cityName\":\"邯郸\"},{\"cityId\":\"43\",\"cityName\":\"邢台\"}],\"adId\":\"6\",\"status\":\"ENABLE\"}";
        JSONObject jsonObj = JSONObject.parseObject(jsonStr);
        ADForm adForm = new ADForm();
        json2Bean(jsonStr, adForm);
        System.out.println(adForm);
    }
}
