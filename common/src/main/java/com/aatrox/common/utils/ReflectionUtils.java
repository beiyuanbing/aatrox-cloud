package com.aatrox.common.utils;

import org.apache.commons.lang.Validate;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @desc 反射的工具类
 * @auth beiyuanbing
 * @date 2019-04-29 10:30
 **/
public class ReflectionUtils {

    private static final String BASE_MODULE = "java.base";

    /**
     * 直接设置对象属性值, 忽略 private/protected 修饰符
     *
     * @param object    : 目标对象
     * @param fieldName : 属性名
     * @param value     : 将要设置的值
     */

    public static void setFieldValue(Object object, String fieldName, Object value) {
        setFieldValue(object, fieldName, value, true);

    }

    public static void setFieldValue(Object object, String fieldName, Object value, boolean overwrite) {
        try {
            //根据 对象和属性名通过取 Field对象
            Field field = getDeclaredField(object, fieldName);
            if (field == null) {
                return;
            }
            //设置可访问
            field.setAccessible(true);
            if (!overwrite && field.get(object) != null) {
                return;
            }

            //将 object 中 field 所代表的值 设置为 value
            field.set(object, value);
        } catch (Exception e) {
//            e.printStackTrace();
        }

    }

    /**
     * 直接调用对象方法, 而忽略修饰符(private, protected, default)
     *
     * @param object         : 子类对象
     * @param methodName     : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @param parameters     : 父类中的方法参数
     * @return 父类中方法的执行结果
     */

    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes,
                                      Object[] parameters) {
        //根据 对象、方法名和对应的方法参数 通过取 Method 对象
        Method method = getDeclaredMethod(object, methodName, parameterTypes);

        //抑制Java对方法进行检查,主要是针对私有方法而言
        method.setAccessible(true);

        try {
            if (null != method) {

                //调用object 的 method 所代表的方法，其方法的参数是 parameters
                return method.invoke(object, parameters);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 类型                 int        二进制
     * <p>
     * PUBLIC:           1             1
     * PRIVATE:         2             10
     * PROTECTED: 4              100
     * STATIC:           8              1000
     * FINAL:           16             10000
     * SYNCHRONIZED: 32     100000
     * VOLATILE: 64                 1000000
     * TRANSIENT: 128            10000000
     * NATIVE: 256                    100000000
     * INTERFACE: 512             1000000000
     * ABSTRACT: 1024            10000000000
     * STRICT: 2048                  100000000000
     *
     * @param field
     * @return
     */
    //判断是不是private类型方法
    public static boolean isTransient(Field field) {
        return ((field.getModifiers() & 0x80) != 0);
    }


    /**
     * 获取所有的方法
     *
     * @param object
     * @return
     */
    public static List<Method> getDeclareMethods(Object object) {
        if (object == null) {
            return null;
        }
        return getDeclareMethods(object.getClass());
    }

    public static List<Method> getDeclareMethods(Class claz) {
        Method[] methods = claz.getDeclaredMethods();
        if (methods == null) {
            return null;
        }
        return Arrays.asList(methods);
    }

    /**
     * 设置对象的属性，一个值填充多个值域
     *
     * @param object         : 目标对象
     * @param fieldNameArray : 属性名数组
     * @param value          : 将要设置的值
     */
    public static void setFieldValue(Object object, Object value, String... fieldNameArray) {
        try {
            if (fieldNameArray == null || fieldNameArray.length == 0) {
                return;
            }
            for (String fieldName : fieldNameArray) {
                setFieldValue(object, value, fieldName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 一次性设置多个属性的值
     * @param object : 目标对象
     * @param fieldNameList : 属性名数组
     * @param valueList : 值数组
     */
    public static void setFieldValues(Object object, List<String> fieldNameList, List<Object> valueList) {
        setFieldValues(object, fieldNameList, valueList, true);
    }

    /***
     * 一次性设置多个属性的值
     * @param object : 目标对象
     * @param fieldNameList : 属性名数组
     * @param valueList : 值数组
     */
    public static void setFieldValues(Object object, List<String> fieldNameList, List<Object> valueList, boolean overwrite) {
        try {
            if (fieldNameList == null || fieldNameList.size() == 0 || valueList == null || valueList.size() == 0) {
                return;
            }
            for (int i = 0; i < valueList.size() && i < fieldNameList.size(); i++) {
                setFieldValue(object, fieldNameList.get(i), valueList.get(i), overwrite);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接读的属性值, 忽略 private/protected 修饰符
     *
     * @param object    : 目标对象
     * @param fieldName : 属性名
     * @return 返回对象值
     */

    public static Object getFieldValue(Object object, String fieldName) {
        try {
            //根据 对象和属性名通过取 Field对象
            Field field = getDeclaredField(object, fieldName);
            if (field == null) {
                return null;
            }
            //设置可访问
            field.setAccessible(true);
            //获的属性值
            return field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 循环向上转型, 获取所有的父类包括子类的属性
     *
     * @param object    : 子类对象
     * @param fieldName : 属性名
     * @return 返回字段
     */

    public static Field getDeclaredField(Object object, String fieldName) {
        Field field = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }

        return field;
    }

    /**
     * 是否是子类
     *
     * @param genericClazz
     * @param targetClass
     * @return
     */
    public static boolean isExtends(Class genericClazz, Class targetClass) {
        return targetClass.isAssignableFrom(genericClazz);
    }


    /**
     * 判断object是否为基本类型
     *
     * @param object
     * @return
     */
    public static boolean isBaseType(Object object) {
        return isBaseType(object.getClass());
    }

    public static boolean isBaseType(Field field) {
        return isBaseType(field.getType());
    }

    /**
     * 最简洁的判断是不是java的baseType
     * 判断是不是基础模块？
     * 只是适用于jdk为9以上的版本，jdk9以下(不包含)建议使用下面注释的版本
     *
     * @param aClass
     * @return
     */
    public static boolean isBaseType(Class aClass) {
        if (aClass == null) {
            return false;
        }
        return BASE_MODULE.equals(aClass.getModule().getName());
    }
    /*public static boolean isBaseType(Class className) {
        if (className.equals(java.lang.Integer.class) ||
                className.equals(java.lang.Byte.class) ||
                className.equals(java.lang.Long.class) ||
                className.equals(java.lang.Double.class) ||
                className.equals(java.lang.Float.class) ||
                className.equals(java.lang.Character.class) ||
                className.equals(java.lang.Short.class) ||
                className.equals(java.lang.Boolean.class) ||
                className.equals(java.lang.String.class) ||
                className.equals(java.util.Date.class) ||
                className.equals(int.class) ||
                className.equals(byte.class) ||
                className.equals(long.class) ||
                className.equals(double.class) ||
                className.equals(float.class) ||
                className.equals(char.class) ||
                className.equals(short.class) ||
                className.equals(boolean.class)) {
            return true;
        }
        return false;
    }*/

    /**
     * 是否是TypeVariable: 是各种类型变量的公共父接口
     *
     * @param field
     * @return
     */
    public static boolean isTypeVariable(Field field) {
        if (field == null) {
            return false;
        }
        Type genericType = field.getGenericType();
        if (genericType instanceof TypeVariable) {
            return true;
        }
        return false;
    }

    /**
     * 循环向上转型, 获取所有的字段包括父类的对象
     *
     * @param : 目标对象
     * @return 所有的栏位
     */

    public static List<Field> getDeclaredFields(Object object) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        return fields;
    }

    /**
     * 拷贝对象默认是父类对象也拷贝
     *
     * @param target
     * @param source
     * @param notNull 不允许拷贝空值
     */
    public static void copyProperties(Object target, Object source, boolean notNull) {

        try {
            if (target == null) {
                return;
            }
            //获取所有的字段
            List<Field> targetFields = getDeclaredFields(target);
            //循坏遍历获取值
            for (Field field : targetFields) {
                Object fieldValue = getFieldValue(source, field.getName());
                if (notNull && fieldValue == null) {
                    continue;
                }
                setFieldValue(target, field.getName(), fieldValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 循环向上转型, 获取对应的方法
     *
     * @param object         : 对象
     * @param methodName     : 方法名
     * @param parameterTypes : 方法参数类型
     * @return 方法对象
     */

    public static Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method;

        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {
                //这里甚么都不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会进入
            }
        }

        return null;
    }

    /**
     * 拷贝对象默认是父类对象也拷贝,不允许拷贝空值
     *
     * @param target
     * @param source
     */
    public static void copyProperties(Object target, Object source) {
        copyProperties(target, source, true);
    }

    /**
     * 对象增加新的栏位并返回对应的clig的对象,并设置对应的值
     *
     * @param superClasz
     * @param property
     * @param type
     * @param value
     * @return
     */
    public static Object addPropertyAndSetValue(Class superClasz, String property, Class type, Object value) {
        Object obj = addProperty(superClasz, property, type);
        setValue(obj, property, value);
        return obj;
    }

    /***
     * 对象增加新的栏位并返回对应的clig的对象
     * @param superClasz
     * @param property
     * @param type
     * @return
     */
    public static Object addProperty(Class superClasz, String property, Class type) {
        BeanGenerator generator = new BeanGenerator();
        generator.setSuperclass(superClasz);
        generator.addProperty(property, type);
        Object obj = generator.create();
        return obj;
    }

    /**
     * 复制源对象的值
     *
     * @param source
     * @param property
     * @param type
     * @return
     */
    public static Object addObjectProperty(Object source, String property, Class type) {
        Object obj = addProperty(source == null ? null : source.getClass(), property, type);
        if (source != null) {
            copyProperties(obj, source);
        }
        return obj;
    }

    /***
     * 增加多栏位并且赋值
     * @param source
     * @param propertyList
     * @param valueList
     * @return
     */
    public static Object addObjectPropertys(Object source, List<String> propertyList, List<Object> valueList, Class common) {
        return addObjectPropertys(source,propertyList,valueList,common,true);
    }

    /***
     * 增加多栏位并且赋值
     * @param source
     * @param propertyList
     * @param valueList
     * @param overWrite
     * @return
     */
    public static Object addObjectPropertys(Object source, List<String> propertyList, List<Object> valueList, Class common,boolean overWrite) {
        BeanGenerator generator = new BeanGenerator();
        generator.setSuperclass(source.getClass());
        if (propertyList == null || valueList == null || propertyList.size() == 0 || valueList.size() == 0 || propertyList.size() != valueList.size()) {
            return source;
        }
        for (int i = 0; i < propertyList.size() && i < valueList.size(); i++) {
            String propertyName = propertyList.get(i);
            Object value = valueList.get(i);
            if (propertyName == null || "".equals(propertyName) || value == null) {
                continue;
            }
            generator.addProperty(propertyList.get(i), common == null ? value.getClass() : common);
        }
        Object target = generator.create();
        if (source != null && target != null) {
            copyProperties(target, source);
            for (int i = 0; i < propertyList.size() && i < valueList.size(); i++) {
                String propertyName = propertyList.get(i);
                Object value = valueList.get(i);
                if (propertyName == null || "".equals(propertyName)) {
                    continue;
                }
                setValue(target, propertyName, value,overWrite);
            }
        }
        return target;
    }

    /***
     * 设置clig对象和普通对象的属性，基本上都是通吃
     * 优化后可以实现所有的
     * @param obj
     * @param property
     * @param value
     */
    public static void setValue(Object obj, String property, Object value) {
        setValue(obj,property,value,true);
    }

    /**
     * 是否覆盖值
     * @param obj
     * @param property
     * @param value
     * @param overWrite
     */
    public static void setValue(Object obj, String property, Object value,boolean overWrite) {
        if (isPropertyChain(obj, property)) {
            setFieldValue(obj, property, value,overWrite);
        } else {
            BeanMap beanMap = BeanMap.create(obj);
            //当前值
            Object current=beanMap.get(property);
            if(!overWrite&&current!=null){
                return;
            }
            beanMap.put(property, value);
        }
    }


    /**
     * 栏位是否是数组
     *
     * @param field
     * @return
     */
    public static boolean isCollectionField(Field field) {
        if (field == null) {
            return false;
        }
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            return true;
        }
        return false;

    }


    /**
     * 判断属性是不是链式的set方法
     *
     * @param obj
     * @param property
     * @return
     */
    public static boolean isPropertyChain(Object obj, String property) {
        Field field = getDeclaredField(obj, property);
        if (field != null) {
            String methodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            Method method = getDeclaredMethod(obj, methodName, field.getType());
            //实现链式编程
            if (method != null && !method.getReturnType().equals(Void.TYPE)) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * 判断栏位是不是枚举类型
     *
     * @param obj
     * @param property
     * @return
     */
    public static boolean isEnum(Object obj, String property) {
        Field field = getDeclaredField(obj, property);
        if (field != null) {

            Class clz = field.getType();
            return clz.isEnum();
        }
        return false;

    }

    public static boolean isEnum(Field field) {
        if (field != null) {

            Class clz = field.getType();
            return clz.isEnum();
        }
        return false;

    }

    /**
     * 获取利用反射获取类里面的值和名称
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        System.out.println(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }

    /**
     * 获取零值
     *
     * @param type
     * @return
     */
    public static Object getNullValue(Class type) {
        if (boolean.class.equals(type)) {
            return false;
        } else if (byte.class.equals(type)) {
            return 0;
        } else if (short.class.equals(type)) {
            return 0;
        } else if (int.class.equals(type)) {
            return 0;
        } else if (long.class.equals(type)) {
            return 0;
        } else if (float.class.equals(type)) {
            return 0;
        } else if (double.class.equals(type)) {
            return 0;
        } else if (char.class.equals(type)) {
            return ' ';
        }
        return null;
    }


    /**
     * 将目标对象的属性及其父类的属性 转换成map集合name-field
     *
     * @param clazz
     * @return
     */
    public static Map<String, Field> getFieldMap(Class<?> clazz) {
        List<Field> fieldList = getFieldList(clazz);
        return fieldList.stream().filter(f -> f.getModifiers() != 26).collect(Collectors.toMap(e -> e.getName(), e -> e));
    }

    /**
     * 将目标对象的属性及其父类的属性 转换成map集合name-field
     * 字段都设置为可访问
     *
     * @param clazz
     * @return
     */
    public static Map<String, Field> getFieldMap(Class<?> clazz, boolean accessible) {
        List<Field> fieldList = getFieldList(clazz);
        return fieldList.stream().filter(f -> f.getModifiers() != 26)
                .peek(elem -> elem.setAccessible(accessible))
                .collect(Collectors.toMap(e -> e.getName(), e -> e));
    }

    /**
     * 将源对象的属性及其父类的属性转成集合
     *
     * @param clazz
     * @return
     */
    public static List<Field> getFieldList(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }

/*    public static <T> T getFullObject(T t1, T t2) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = t1.getClass();
        T temp = (T) clazz.getDeclaredConstructor().newInstance();
        BeanUtils.copyProperties(t1, temp);
        List<Field> fieldList = getFieldList(clazz);
        //找temp的空字段
        List<Field> notNullField = fieldList
                .stream()
                .filter(e -> null == getFieldValue(temp, e.getName()))
                .collect(Collectors.toList());
        //t2进行填充
        notNullField.stream()
                .forEach(e -> setFieldValue(temp, e.getName(), getFieldValue(t2, e.getName())));
        return temp;
    }*/

    public static class Son {
        private String name;

        private int age;

        private Date birthday;

        private Son son;

        public String getName() {
            return name;
        }

        public Son setName(String name) {
            this.name = name;
            return this;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Son{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
    private static List<Field> recursionFields(Class<?> type) {
        Validate.notNull(type, "Parameter 'type' is required");

        Class<?> clazz = type;

        List<Field> fieldList = new ArrayList<Field>();
        while (clazz != Object.class) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }

        return fieldList;
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Ignore {

    }
    /**
     * 获取指定类及其父类中某一注解标识的所有属性
     *
     * @param type
     * @param specify 指定注解
     * @return
     */
    public static Field[] getSpecified(Class<?> type, Class<? extends Annotation> specify) {
        List<Field> fieldList = recursionFields(type);

        if (specify != null) {
            Iterator<Field> fieldIterator = fieldList.iterator();
            while (fieldIterator.hasNext()) {
                Field field = fieldIterator.next();
                if(!field.isAnnotationPresent(specify)) {
                    fieldIterator.remove();
                }
            }
        }
        return fieldList.toArray(Field[]::new);
    }

    public static  <T> T newInstance(Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return clazz.getDeclaredConstructor().newInstance();
    }


    public static Field[] getSpecifiedAndSort(Class<?> type, Class<? extends Annotation> specify, Comparator<Field> comparator) {
        return Stream.of(getSpecified(type, specify)).sorted(comparator).toArray(Field[]::new);
    }

    public static void main(String[] args) {
/*        String column = "abc";

        Son son = new Son().setName("1111");
        son.setAge(100);
//        System.out.println(EnumStrUtils.toObjectEnumStrJson(son, true));
        Field field = getDeclaredField(son, "birthday");
        Class<?> type = field.getType();
        System.out.println(type);
        System.out.println(isBaseType(field));*/
    }
}
