package com.aatrox.common.utils.objectdiff.actor;

import com.aatrox.common.utils.EnumStrUtils;
import com.aatrox.common.utils.ReflectionUtils;
import com.aatrox.common.utils.StringUtils;
import com.aatrox.common.utils.objectdiff.DiffParam;
import com.aatrox.common.utils.objectdiff.DiffResult;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by yoara on 2018/1/4.
 */
public class DefaultActor implements DiffActorInterface {
    public static final String DIFF_TEMPLATE = "%s:从\"%s\"修改成\"%s\",";
    //  private static final String DIFF_TEMPLATE = "├─[%s](%s => %s)\r\n";
    @Override
    public DiffResult doAct(DiffParam param) {
        DiffResult result = new DiffResult();
        List<Field> pickHistoryEntryFieldList = param.pickHistoryEntryFieldList();
        Map<String, Field> pickEntryFieldMap = param.pickEntryFieldMap(pickHistoryEntryFieldList);
        StringBuffer content = new StringBuffer();
        try {
            for (Field field : pickHistoryEntryFieldList) {
                Object historyValue = field.get(param.getHistoryEntity());
                Object nowValue = pickEntryFieldMap.get(field.getName()) == null
                        ? null
                        : pickEntryFieldMap.get(field.getName()).get(param.getEntity());

                String hvStr = String.class.equals(field.getGenericType()) ? StringUtils.isEmpty((String) historyValue) ? "无" : (String) historyValue :
                        JSONObject.toJSONString(historyValue == null ? "无" : historyValue, features);
                String nvStr = String.class.equals(field.getGenericType()) ? StringUtils.isEmpty((String) nowValue) ? "无" : (String) nowValue :
                        JSONObject.toJSONString(nowValue == null ? "无" : nowValue, features);
                if (ReflectionUtils.isEnum(field) && historyValue != null) {
                    hvStr = EnumStrUtils.getEnumStr(field.getType(), String.valueOf(historyValue));
                }
                if (ReflectionUtils.isEnum(field) && nowValue != null) {
                    nvStr = EnumStrUtils.getEnumStr(field.getType(), String.valueOf(nowValue));
                }
                if (!hvStr.equals(nvStr)) {
                    ApiModelProperty apiDesc = field.getAnnotation(ApiModelProperty.class);
                    content.append(String.format(DIFF_TEMPLATE, apiDesc == null ? field.getName() : apiDesc.value(), hvStr, nvStr));
                   /* content.append("├─").append("[").append(apiDesc == null ? field.getName() : apiDesc.value()).append("]")
                            .append("(").append(hvStr).append(" => ").append(nvStr)
                            .append(")").append("\r\n");*/
                }
            }
        } catch (Exception e) {
            result.setResult(false);
            result.setErrMessage(e.getMessage());
        }
        result.setResult(true);
        result.setData(content.toString());
        return result;
    }

    public static SerializerFeature[] features = {
            //输出值为null的字段
            SerializerFeature.WriteMapNullValue,
            //数值字段如果为null,输出为0
            SerializerFeature.WriteNullNumberAsZero,
            //字符类型字段如果为null,输出为""
            SerializerFeature.WriteNullStringAsEmpty,
            //List字段如果为null,输出为[]
            SerializerFeature.WriteNullListAsEmpty,
            //Boolean字段如果为null,输出为false,而非null
            SerializerFeature.WriteNullBooleanAsFalse,
            //禁止循环引用检测
            SerializerFeature.DisableCircularReferenceDetect,
            //使用格式化日期
            SerializerFeature.WriteDateUseDateFormat
    };
}
