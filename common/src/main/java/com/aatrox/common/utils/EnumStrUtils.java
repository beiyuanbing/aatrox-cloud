package com.aatrox.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @desc 含枚举类的对象自动增加对应的描述属性
 * @auth beiyuanbing
 * @date 2019-05-05 08:34
 **/
public class EnumStrUtils {


    /**
     * 对象增加枚举类型的描述字段
     *
     * @param object
     * @return
     */
    public static Object toObjectAddEnumStr(Object object) {
        if (object == null) {
            return object;
        }
        if (object instanceof Collection) {
            //对象本身是集合类型
            return dealCollectProperty((List) object);
        }
        //处理字段是枚举类型
        object = dealEnumProperty(object);
        //处理collect类型
        object = dealCollectProperty(object);
        //处理普通对象的枚举
        return dealCommonProperty(object);
    }

    /***
     * 对象增加枚举类型的描述字段，并返回jsonstr
     * ignoreNull是否忽略空值
     * @param object
     * @return
     */
    private static String toObjectEnumStrJson(Object object) {
        return toObjectEnumStrJson(object, true);
    }

    private static String toObjectEnumStrJson(Object object, boolean ignoreNull) {
        String result;
        if (ignoreNull) {
            result = JSONObject.toJSONString(toObjectAddEnumStr(object));
        } else {
            result = JSONObject.toJSONString(toObjectAddEnumStr(object), SerializerFeature.WriteMapNullValue);
        }
        return result;
    }

    /**
     * 处理枚举属性的字段
     *
     * @param object
     * @return
     */
    private static Object dealEnumProperty(Object object) {
        List<Field> enumList = ReflectionUtils.getDeclaredFields(object).stream()
                .filter(
                        e -> !ReflectionUtils.isCollectionField(e)
                                && !ReflectionUtils.isTypeVariable(e)
                                && !ReflectionUtils.isBaseType(e)
                )
                .filter(field -> {
                    try {
                        field.setAccessible(true);
                        Class clz = field.getType();
                        return clz.isEnum();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }).collect(Collectors.toList());
        return createEnumStr(object, enumList);
    }

    /***
     * 处理基础类型的对象
     * @param object
     * @return
     */
    private static Object dealCommonProperty(Object object) {
        ReflectionUtils.getDeclaredFields(object).stream().filter(e -> !ReflectionUtils.isCollectionField(e)).forEach(field -> {
            try {
                field.setAccessible(true);
                if (!ReflectionUtils.isBaseType(field) && !field.getType().isEnum()) {
                    ReflectionUtils.setFieldValue(object, field.getName(), toObjectAddEnumStr(field.get(object)),false);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return object;
    }


    /**
     * 处理集合类的属性
     *
     * @param object
     * @return
     */
    private static Object dealCollectProperty(Object object) {
        ReflectionUtils.getDeclaredFields(object).stream()
                .filter(e -> null != e && ReflectionUtils.isCollectionField(e))
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        Type genericType = field.getGenericType();
                        if (((ParameterizedType) genericType).getRawType().getTypeName().equals(List.class.getTypeName())) {
                            Object list = field.get(object);
                            dealCollectProperty((List) list);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        return object;
    }

    private static Object dealCollectProperty(List list) {
        if (ListUtil.isEmpty(list)) {
            return null;
        }
        try {
            Method m = list.getClass().getDeclaredMethod("size");
            int size = (Integer) m.invoke(list);
            List<Object> result = new ArrayList<>();
            if (size == 0) {
                return list;
            }
            for (int i = 0; i < size; i++) {
                result.add(dealEnumProperty(list.get(i)));
            }
            //清空以前的
            if (result != null && result.size() > 0) {
                list.clear();
                list.addAll(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 增加枚举值
     *
     * @param source
     * @param enumCollect
     * @return
     */
    public static Object createEnumStr(Object source, List<Field> enumCollect) {
        if (enumCollect == null || enumCollect.size() == 0) {
            return source;
        }
        List<String> propertyName = new ArrayList<>();
        List<Object> valueList = new ArrayList<>();
        enumCollect.forEach(field -> {
            try {
                field.setAccessible(true);
                propertyName.add(field.getName() + "Str");
                Class clz = Class.forName(field.getGenericType().getTypeName());
                valueList.add(getEnumStr(clz, field.get(source) == null ? null : ((Enum) field.get(source)).name()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return ReflectionUtils.addObjectPropertys(source, propertyName, valueList, String.class,false);
    }

    /**
     * 获取枚举的值
     *
     * @param classz
     * @param enumName
     * @return
     */
    public static String getEnumStr(Class classz, String enumName) {
        return getEnumStr(classz, enumName, null);
    }

    /**
     * 获取枚举的值
     *
     * @param classz
     * @param enumName
     * @return
     */
    public static String getEnumStr(Class classz, String enumName, String fieldName) {
        if (classz == null || !classz.isEnum() || enumName == null || "".equals(enumName)) {
            return null;
        }
        try {
            Object o = Arrays.stream(classz.getEnumConstants()).filter(enumClasses -> ((Enum) enumClasses).name().equals(enumName)).findFirst().get();
            StringBuilder stringBuilder = new StringBuilder("get");
            if (StringUtils.isNotEmpty(fieldName)) {
                stringBuilder.append(StringUtils.firstChToUpper(fieldName));
            }
            List<Method> methods = Arrays.stream(classz.getDeclaredMethods()).filter(method -> method.getName().contains(stringBuilder.toString())).collect(Collectors.toList());
            return methods.stream().findFirst().get().invoke(o).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
