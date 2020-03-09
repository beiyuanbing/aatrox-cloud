package com.aatrox.common.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CommonMapUtil {
    public CommonMapUtil() {
    }

    public static double getDouble(Map<String, Object> map, String key) {
        return getDouble(map, key, 0.0D);
    }

    public static double getDouble(Map<String, Object> map, String key, double defaultValue) {
        if (null != key && null != map) {
            try {
                return Double.parseDouble(map.get(key).toString());
            } catch (Exception var5) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public static int getInt(Map<String, Object> map, String key) {
        return getInt(map, key, 0);
    }

    public static int getInt(Map<String, Object> map, String key, int defaultValue) {
        if (null != key && null != map) {
            try {
                String str = String.valueOf(map.get(key));
                if (StringUtils.isNotEmpty(str)) {
                    str = str.replaceAll(",", "");
                    return Integer.valueOf(str);
                }
            } catch (Exception var4) {
            }

            return defaultValue;
        } else {
            return defaultValue;
        }
    }

    public static boolean getBoolean(Map<String, Object> map, String key) {
        return getBoolean(map, key, false);
    }

    public static boolean getBoolean(Map<String, Object> map, String key, boolean defaultValue) {
        if (null != key && null != map) {
            try {
                return Boolean.valueOf(String.valueOf(map.get(key)));
            } catch (Exception var4) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public static String getString(Map<String, Object> map, String key) {
        return getString(map, key, (String) null);
    }

    public static String getString(Map<String, Object> map, String key, String defaultValue) {
        if (null != key && null != map) {
            try {
                String val = String.valueOf(map.get(key));
                if (StringUtils.isNotEmpty(val)) {
                    return val;
                }
            } catch (Exception var4) {
            }

            return defaultValue;
        } else {
            return defaultValue;
        }
    }

    public static BigDecimal getBigDecimal(Map<String, Object> map, String key) {
        return getBigDecimal(map, key, (BigDecimal) null);
    }

    public static BigDecimal getBigDecimal(Map<String, Object> map, String key, BigDecimal defaultValue) {
        if (null != key && null != map) {
            try {
                BigDecimal val = (BigDecimal) map.get(key);
                if (null != val) {
                    return val;
                }
            } catch (Exception var4) {
            }

            return defaultValue;
        } else {
            return defaultValue;
        }
    }

    public static Object getObject(Map<String, Object> map, String key) {
        return getObject(map, key, (Object) null);
    }

    public static Object getObject(Map<String, Object> map, Object key, Object defaultValue) {
        if (null != key && null != map) {
            Object val = map.get(key);
            return null != val ? val : defaultValue;
        } else {
            return defaultValue;
        }
    }

    public static <T> T get(Map<String, Object> map, String key) {
        try {
            return (T) getObject(map, key, (Object) null);
        } catch (Exception var3) {
            return null;
        }
    }

    public static final <K, V> Map<K, V> put(Map<K, V> map, boolean filter, K k, V v) {
        if (map != null && filter && k != null && v != null) {
            map.put(k, v);
        }

        return map;
    }

    public static final <K, V> Map<K, V> put(Map<K, V> map, K k, V v) {
        return put(map, true, k, v);
    }

    public static final <K, V> Map<K, V> NEW() {
        return new HashMap();
    }

    public static final <K, V> Map<K, V> NEW(int initialCapacity) {
        return new HashMap(initialCapacity);
    }

    public static final <K, V> Map<K, V> NEW(boolean filter, K k, V v) {
        Map<K, V> map = NEW();
        put(map, filter, k, v);
        return map;
    }

    public static final <K, V> Map<K, V> NEW(boolean filter, K k1, V v1, K k2, V v2) {
        Map<K, V> map = NEW();
        put(map, filter, k1, v1);
        put(map, filter, k2, v2);
        return map;
    }

    public static final <K, V> Map<K, V> NEW(boolean filter, K k1, V v1, K k2, V v2, K k3, V v3) {
        Map<K, V> map = NEW();
        put(map, filter, k1, v1);
        put(map, filter, k2, v2);
        put(map, filter, k3, v3);
        return map;
    }

    public static final <K, V> Map<K, V> NEW(K k, V v) {
        return NEW(true, k, v);
    }

    public static final <K, V> Map<K, V> NEW(K k1, V v1, K k2, V v2) {
        return NEW(true, k1, v1, k2, v2);
    }

    public static final <K, V> Map<K, V> NEW(K k1, V v1, K k2, V v2, K k3, V v3) {
        return NEW(true, k1, v1, k2, v2, k3, v3);
    }

    public static final <K, V> Map<K, V> cleanNullOrEmpty(Map<K, V> map) {
        if (hasItem(map)) {
            Iterator var1 = map.keySet().iterator();

            while (true) {
                Object key;
                do {
                    if (!var1.hasNext()) {
                        return map;
                    }

                    key = var1.next();
                } while (key != null && map.get(key) != null);

                map.remove(key);
            }
        } else {
            return map;
        }
    }

    public static final boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static final boolean hasItem(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static final int size(Map<?, ?> map) {
        int size = 0;
        if (hasItem(map)) {
            size = map.size();
        }

        return size;
    }

    public static Map BeamToMap(Object bean) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for (int i = 0; i < propertyDescriptors.length; ++i) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }

        return returnMap;
    }

    public static Object MapToBean(Class type, Map map) throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        Object obj = type.newInstance();
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for (int i = 0; i < propertyDescriptors.length; ++i) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (map.containsKey(propertyName)) {
                Object value = map.get(propertyName);
                Object[] args = new Object[]{value};
                descriptor.getWriteMethod().invoke(obj, args);
            }
        }

        return obj;
    }
}

