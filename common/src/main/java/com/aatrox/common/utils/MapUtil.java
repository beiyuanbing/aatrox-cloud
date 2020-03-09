package com.aatrox.common.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 快速操作map
 *
 * @ch
 * @create 2017-12-20 15:03
 */
public class MapUtil {

    /**
     * 为Map设值
     *
     * @param map    Map实例
     * @param filter 是否过滤空键或空值
     * @param k      键
     * @param v      值
     * @return 设置后的Map
     */
    public static final <K, V> Map<K, V> put(Map<K, V> map, boolean filter, K k, V v) {
        if (map != null) {
            if (filter && k != null && v != null) {
                map.put(k, v);
            }
        }
        return map;
    }

    /**
     * 为Map设值(过滤空键或空值)
     *
     * @param map Map实例
     * @param k   键
     * @param v   值
     * @return 设置后的Map
     */
    public static final <K, V> Map<K, V> put(Map<K, V> map, K k, V v) {
        return put(map, true, k, v);
    }

    /**
     * 创建HashMap实例
     *
     * @return HashMap实例
     */
    public static final <K, V> Map<K, V> NEW() {
        return new HashMap<>();
    }


    /**
     * 创建HashMap实例
     *
     * @param filter 是否过滤空键或空值
     * @param k      键
     * @param v      值
     * @return HashMap实例
     */
    public static final <K, V> Map<K, V> NEW(boolean filter, K k, V v) {
        Map<K, V> map = NEW();
        put(map, filter, k, v);
        return map;
    }

    /**
     * 创建HashMap实例
     *
     * @param filter 是否过滤空键或空值
     * @param k1     键1
     * @param v1     值1
     * @param k2     键2
     * @param v2     值2
     * @return HashMap实例
     */
    public static final <K, V> Map<K, V> NEW(boolean filter, K k1, V v1, K k2, V v2) {
        Map<K, V> map = NEW();
        put(map, filter, k1, v1);
        put(map, filter, k2, v2);
        return map;
    }

    /**
     * 创建HashMap实例
     *
     * @param filter 是否过滤空键或空值
     * @param k1     键1
     * @param v1     值1
     * @param k2     键2
     * @param v2     值2
     * @param k3     键3
     * @param v3     值3
     * @return HashMap实例
     */
    public static final <K, V> Map<K, V> NEW(boolean filter, K k1, V v1, K k2, V v2, K k3, V v3) {
        Map<K, V> map = NEW();
        put(map, filter, k1, v1);
        put(map, filter, k2, v2);
        put(map, filter, k3, v3);
        return map;
    }

    /**
     * 创建HashMap实例(过滤空键或空值)
     *
     * @param k 键
     * @param v 值
     * @return HashMap实例
     */
    public static final <K, V> Map<K, V> NEW(K k, V v) {
        return NEW(true, k, v);
    }

    /**
     * 创建HashMap实例(过滤空键或空值)
     *
     * @param k1 键1
     * @param v1 值1
     * @param k2 键2
     * @param v2 值2
     * @return HashMap实例
     */
    public static final <K, V> Map<K, V> NEW(K k1, V v1, K k2, V v2) {
        return NEW(true, k1, v1, k2, v2);
    }

    /**
     * 创建HashMap实例(过滤空键或空值)
     *
     * @param k1 键1
     * @param v1 值1
     * @param k2 键2
     * @param v2 值2
     * @param k3 键3
     * @param v3 值3
     * @return HashMap实例
     */
    public static final <K, V> Map<K, V> NEW(K k1, V v1, K k2, V v2, K k3, V v3) {
        return NEW(true, k1, v1, k2, v2, k3, v3);
    }

    /**
     * 过滤Map中的空键或空值
     *
     * @param map Map实例
     * @return 过滤后的Map实例
     */
    public static final <K, V> Map<K, V> cleanNullOrEmpty(Map<K, V> map) {
        if (isNotEmpty(map)) {
            for (K key : map.keySet()) {
                if (key == null || map.get(key) == null) {
                    map.remove(key);
                }
            }
        }
        return map;
    }

    /**
     * 判断Map是否为空(null||empty)
     *
     * @param map
     * @return boolean
     */
    public static final boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断Map是否包含项(size>0)
     *
     * @param map
     * @return boolean
     */
    public static final boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 获取Map的size
     *
     * @param map
     * @return int 为null时返回0
     */
    public static final int size(Map<?, ?> map) {
        int size = 0;
        if (isNotEmpty(map)) {
            size = map.size();
        }

        return size;
    }


    /**
     * 将一个 JavaBean 对象转化为一个  Map
     *
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static Map BeanToMap(Object bean) {
        Map returnMap = new HashMap();
        try {
            Class type = bean.getClass();
            BeanInfo beanInfo;
            beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    if (result != null) {
                        returnMap.put(propertyName, result);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;
    }

    /**
     * 将一个 Map 对象转化为一个 JavaBean
     *
     * @param type 要转化的类型
     * @param map  包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InstantiationException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings("rawtypes")
    public static Object MapToBean(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);

                Object[] args = new Object[1];
                args[0] = value;

                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }
}
