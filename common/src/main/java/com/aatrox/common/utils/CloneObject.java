package com.aatrox.common.utils;

import java.io.*;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * 克隆的工具类
 * 又引用其他的类，其他的类又有引用别的类，
 * 那么想要深度拷贝必须所有的类及其引用的类都得实现Cloneable接口，
 * 重写clone方法，这样以来非常麻烦，
 * 简单的方法是让所有的对象实现序列化接口（Serializable），然后通过序列化反序列化的方法来深度拷贝对象。
 */
public class CloneObject {
    private static final Logger logger = Logger.getLogger("CloneObject");

    public static <T> T clone(T obj) {
        T dest = null;
        try {
            boolean isSerializable = Arrays.stream(obj.getClass().getInterfaces()).filter(Serializable.class::equals).count() > 0;
            if (!isSerializable) {
                //必须实现了序列化接口，不然无法进行深拷贝拷贝
                logger.info("对象没有实现Serializable接口,所以无法进行拷贝");
                return dest;
            }
            //将对象序列化成为流，因为写在流是对象里的一个拷贝
            //而原始对象扔在存在JVM中，所以利用这个特性可以实现深拷贝
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            //将流序列化为对象
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            dest = (T) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dest;
    }

    static class Parent implements Serializable {
        private String str;

        public Parent(String str) {
            this.str = str;
        }

        public String getStr() {
            return str;
        }

        public Parent setStr(String str) {
            this.str = str;
            return this;
        }

        @Override
        public String toString() {
            return "Parent{" +
                    "str='" + str + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        Parent parent = new Parent("123");
        Parent clone = CloneObject.clone(parent);
        clone.setStr("2343");
        System.out.println(parent);
        System.out.println(clone);
    }

}
