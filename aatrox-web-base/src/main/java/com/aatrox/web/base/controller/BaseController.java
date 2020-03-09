package com.aatrox.web.base.controller;

import com.aatrox.apilist.form.JsonCommonCodeEnum;
import com.aatrox.apilist.form.JsonReturnCodeEnum;
import com.aatrox.common.utils.StringUtils;
import com.aatrox.web.base.util.CommonWebUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-15
 */
public class BaseController {
    public static SerializerFeature[] features = {
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullNumberAsZero,
            SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteNullBooleanAsFalse,
            SerializerFeature.DisableCircularReferenceDetect};


    /**
     * 从Request获取参数相关 Start
     **/
    public Date getDate(String name, String format) {
        return CommonWebUtil.getDate(name, format);
    }


    public double getDouble(String name) {
        return getDouble(name, 0.0D);
    }


    public double getDouble(String name, double defaultValue) {
        return CommonWebUtil.getDouble(name, defaultValue);
    }


    public Boolean getBoolean(String name, boolean defaultValue) {
        return CommonWebUtil.getBoolean(name, defaultValue);
    }
    /**从Request获取参数相关 End**/

    /**
     * 返回对应的数据  Start
     **/
    protected String returnSuccessInfo() {
        return returnJsonInfoCustom(null, JsonCommonCodeEnum.C0000, null, null, null);
    }


    protected String returnSuccessInfo(Object json) {
        return returnJsonInfoCustom(json, JsonCommonCodeEnum.C0000, null, null, null);
    }


    protected String returnSuccessInfo(Object json, SerializerFeature[] customFeatures) {
        return returnJsonInfoCustom(json, JsonCommonCodeEnum.C0000, customFeatures, null, null);
    }


    protected String returnSuccessInfo(Object json, SerializeFilter filters) {
        return returnJsonInfoCustom(json, JsonCommonCodeEnum.C0000, null, null, new SerializeFilter[]{filters, null});
    }


    protected String returnSuccessInfo(Object json, String dateFormat) {
        return returnJsonInfoCustom(json, JsonCommonCodeEnum.C0000, null, dateFormat, null);
    }


    protected String returnSuccessInfo(Object json, String dateFormat, SerializeFilter filters) {
        return returnJsonInfoCustom(json, JsonCommonCodeEnum.C0000, null, dateFormat, new SerializeFilter[]{filters});
    }


    protected String returnWrong(JsonReturnCodeEnum errCode) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", errCode.getStatus());
        result.put("message", errCode.getMessage());
        result.put("result", null);
        return JSON.toJSONString(result);
    }


    protected String returnWithCustomMessage(String message, String code) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", code);
        result.put("message", message);
        result.put("result", null);
        return JSON.toJSONString(result);
    }


    protected String returnWithCustomMessage(String message, JsonReturnCodeEnum code) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", code.getStatus());
        result.put("message", StringUtils.isNotEmpty(message) ? message : code.getMessage());
        result.put("result", null);
        return JSON.toJSONString(result);
    }


    protected String returnJsonInfo(Object json, JsonReturnCodeEnum code) {
        return returnJsonInfoCustom(json, code, null, null, null);
    }


    protected String returnJsonInfo(Object json, JsonReturnCodeEnum code, String dateFormat) {
        return returnJsonInfoCustom(json, code, null, dateFormat, null);
    }


    protected String returnJsonInfo(Object json, JsonReturnCodeEnum code, SerializeFilter filters) {
        return returnJsonInfoCustom(json, code, null, null, new SerializeFilter[]{filters, null});
    }


    protected String returnJsonInfoCustom(Object json, JsonReturnCodeEnum code, SerializerFeature[] customFeatures, String dateFormat, SerializeFilter... filters) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", code.getStatus());
        result.put("message", code.getMessage());
        result.put("result", json);
        return JSON.toJSONString(result, SerializeConfig.globalInstance, filters, dateFormat, JSON.DEFAULT_GENERATE_FEATURE,
                (customFeatures == null) ? features : customFeatures);
    }
    /**返回对应的数据  End**/
}
