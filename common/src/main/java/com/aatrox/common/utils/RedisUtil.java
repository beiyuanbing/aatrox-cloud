package com.aatrox.common.utils;

import java.io.*;

public class RedisUtil {


//反序列化：将字节数组转成java对象

    private static Object bytesToObject(byte[] value) {
        ByteArrayInputStream bytesIn = null;
        ObjectInputStream in = null;
        try {
            bytesIn = new ByteArrayInputStream(value);
            in = new ObjectInputStream(bytesIn);
            Object obj = in.readObject();
            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                bytesIn.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    //序列化，将Java对象转换成字节数组
    private static byte[] objectToBytes(Object obj) {
        ByteArrayOutputStream bytesOut = null;
        ObjectOutputStream out = null;
        try {
            bytesOut = new ByteArrayOutputStream();
            out = new ObjectOutputStream(bytesOut);
            out.writeObject(obj);//序列化，将obj写入ByteArray数组流中
            byte[] bytes = bytesOut.toByteArray();
            return bytes;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                bytesOut.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        String arr = "\\xAC\\xED\\x00\\x05sr\\x00\\x11java.util.HashMap\\x05\\x07\\xDA\\xC1\\xC3\\x16`\\xD1\\x03\\x00\\x02F\\x00\\x0AloadFactorI\\x00\\x09thresholdxp?@\\x00\\x00\\x00\\x00\\x00\\x00w\\x08\\x00\\x00\\x00\\x10\\x00\\x00\\x00\\x00x";
        Object o = bytesToObject(arr.getBytes());
        System.out.println(o);
    }
}
