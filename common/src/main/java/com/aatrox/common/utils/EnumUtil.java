package com.aatrox.common.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * 枚举工具类
 * 使用方法：需要提供 枚举的坐标类，工具会扫描坐标类所在jar包或者class的所有枚举内容
 * 约束： swagger的注释字段上必须要手动加上枚举名（给前端使用）。例如：@ApiModelProperty(value = "审批状态_ApproveStatusEnum")
 *
 * @author ch
 * @create 2019-02-26 16:32
 */
public class EnumUtil {

    private static Map<String, Object> packageMapCache = MapUtil.NEW();

    /**
     * 获取坐标枚举类所在包的所有枚举
     *
     * @param clazzs
     * @return
     */
    private synchronized static Map<String, Object> getPackClassMap(Class... clazzs) {
        if (!packageMapCache.isEmpty()) {
            return packageMapCache;
        }
        for (Class clazz : clazzs) {
            Set<Class<?>> classesFromPackage = getClasssFromPackage(clazz.getPackage().getName());
            for (Class<?> aClass : classesFromPackage) {
                packageMapCache.put(aClass.getSimpleName(), aClass);
            }
        }
        return packageMapCache;
    }

    /**
     * 获取枚举内容
     *
     * @param class1
     * @return
     */
    private static List<Map<String, Object>> getEnumContent(Class<?> class1) {
        List<?> list = Arrays.asList(class1.getEnumConstants());
        List<Method> methods = Arrays.asList(class1.getDeclaredMethods()).stream().filter(method -> method.getName().contains("get")).collect(Collectors.toList());
        List<Map<String, Object>> list2 = ListUtil.NEW();
        list.forEach(enumClasses -> methods.forEach(method -> {
            try {
                list2.add(MapUtil.NEW("name", method.invoke(enumClasses), "value", enumClasses.toString()));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }));
        return list2;
    }

    /**
     * 扫描坐标类所在的包下，所有的枚举
     *
     * @param clazzs    坐标类，需要这个类所在的包路径，需要扫描
     * @param enumNames 需要获取的枚举名字
     * @return
     */
    public static Map<String, List<Map<String, Object>>> scanEnum(List<String> enumNames, Class... clazzs) {
        Map<String, Object> packClassMap = getPackClassMap(clazzs);

        Map<String, List<Map<String, Object>>> result = MapUtil.NEW();
        packClassMap.entrySet().stream().filter(e ->
                enumNames.contains(e.getKey())
        ).forEach(e -> {
            String enumName = e.getKey();
            Object enumClass = e.getValue();
            Class<?> class1;
            try {
                class1 = Class.forName((((Class) enumClass).getEnumConstants()[0]).getClass().getName());
                if (class1.isEnum()) {
                    result.put(enumName, getEnumContent(class1));
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }


        });
        return result;
    }


    /**
     * 根据包名获取枚举类
     *
     * @param pack
     * @return
     */
    public static Set<Class<?>> getClasssFromPackage(String pack) {

        // 第一个class类的集合
        Set<Class<?>> classes = new LinkedHashSet<>();
        // 是否循环迭代
        boolean recursive = true;
        // 获取包的名字 并进行替换
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findClassInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    findClassInPackageByJar(url, packageDirName, packageName, recursive, classes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }


    /**
     * 根据包名找
     *
     * @param url            jvm内存地址url
     * @param packageDirName 地址路径
     * @param packageName    包路径
     * @param recursive      是否遍历内循环
     * @param classes        存储
     */
    public static void findClassInPackageByJar(URL url, String packageDirName, String packageName, boolean recursive, Set<Class<?>> classes) {
        // 如果是jar包文件
        JarFile jar;
        try {
            // 获取jar
            jar = ((JarURLConnection) url.openConnection()).getJarFile();
            Enumeration<JarEntry> entries = jar.entries();
            // 同样的进行循环迭代
            while (entries.hasMoreElements()) {
                // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                // 如果是以/开头的
                if (name.charAt(0) == '/') {
                    name = name.substring(1);
                }
                // 如果前半部分和定义的包名相同
                if (name.startsWith(packageDirName)) {
                    int idx = name.lastIndexOf('/');
                    // 如果以"/"结尾 是一个包
                    if (idx != -1) {
                        // 获取包名 把"/"替换成"."
                        packageName = name.substring(0, idx).replace('/', '.');
                    }
                    if ((idx != -1) || recursive) {
                        if (name.endsWith(".class") && !entry.isDirectory()) {
                            String className = name.substring(packageName.length() + 1, name.length() - 6);
                            try {
                                classes.add(Class.forName(packageName + '.' + className));
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在package对应的路径下找到所有的class
     */
    public static void findClassInPackageByFile(String packageName, String filePath, final boolean recursive,
                                                Set<Class<?>> clazzs) {
        File dir = new File(filePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 在给定的目录下找到所有的文件，并且进行条件过滤
        File[] dirFiles = dir.listFiles(file -> {
            // 接受dir目录
            boolean acceptDir = recursive && file.isDirectory();
            // 接受class文件
            boolean acceptClass = file.getName().endsWith("class");
            return acceptDir || acceptClass;
        });

        for (File file : dirFiles) {
            if (file.isDirectory()) {
                findClassInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, clazzs);
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    clazzs.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
