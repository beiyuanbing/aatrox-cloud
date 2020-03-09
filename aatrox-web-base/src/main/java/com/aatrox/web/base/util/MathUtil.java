package com.aatrox.web.base.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2019/9/2
 */
public class MathUtil {
    private final static int DEFAULT_DIV_SCALE = 10;

    /**
     * 资源调配，通过线程数和资源量进行合理分配，线程需要执行的量
     * 下标的使用方式：
     * 1.适合集合sublist，无需修改。包含头，不包含尾.推荐。只用这个
     * 2.遍历集合index()。需要将尾部去掉。不能包含尾部
     * 3.如果是sql，那么需要将头部去掉
     *
     * @param threadCount 预计需要多少条线程
     * @param resoureSize 预计每条线程执行资源数
     * @return
     */
    public static Map<Integer, Integer> getSplistResourseIndexByThreadCount(Integer threadCount, Integer resoureSize) {
        Map<Integer, Integer> map = new LinkedHashMap<>();
        // 如果资源量小于线程数，那么一条线程就可以完成了
        if (resoureSize < threadCount) {
            // 一个进程完成就够了
            map.put(0, resoureSize);
        } else {
            int size = (resoureSize) / threadCount;
            for (int threadId = 0; threadId < threadCount; threadId++) {
                // 为每个线程分配任务
                int startIndex = threadId * size; // 线程开始下载的位置
                int endIndex = (threadId + 1) * size; // 线程结束下载的位置
                if (threadId == (threadCount - 1)) { //
                    // 如果是最后一个线程,将剩下的文件全部交给这个线程完成
                    endIndex = resoureSize;
                }
                map.put(startIndex, endIndex);
            }
        }
        return map;
    }

    /**
     * 根据预计线程总数和预计每条线程执行资源数，获取分段执行的下标
     *
     * @param workCount   预计线程总数
     * @param resoureSize 预计每条线程执行资源数
     * @return
     */
    public static Map<Integer, Integer> getSplistResourseIndexByWorkCount(Integer workCount, Integer resoureSize) {
        // 如果资源量小于任务量，那么一条线程完成就可以了
        Map<Integer, Integer> map;
        if (resoureSize < workCount) {
            map = getSplistResourseIndexByThreadCount(1, resoureSize);
        } else {
            // 根据任务量和任务总数获得 任务数目
            Integer threadCount = resoureSize / workCount;
            map = getSplistResourseIndexByThreadCount(threadCount == 0 ? 1 : threadCount, resoureSize);
        }
        return map;
    }
//
//    // ----------------检验start---------------------
//
//    /**
//     * 判断字符串是否是整数
//     */
//    public static boolean isInteger(String value) {
//        try {
//            Integer.parseInt(value);
//            return true;
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }
//
//    /**
//     * 判断字符串是否是浮点数
//     */
//    public static boolean isDouble(String value) {
//        try {
//            Double.parseDouble(value);
//            return value.contains(".");
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }
//
//    /**
//     * 判断字符串是否是数字
//     */
//    public static boolean isNumber(String value) {
//        return isInteger(value) || isDouble(value);
//    }
//    // ----------------end---------------------
//
//    /**
//     * 相加
//     *
//     * @param v1
//     * @return double
//     */
//    public static BigDecimal add(double v1, double... doublelist) {
//        BigDecimal b1 = new BigDecimal(Double.toString(v1));
//        for (double d : doublelist) {
//            BigDecimal b2 = new BigDecimal(Double.toString(d));
//            b1 = b1.add(b2);
//        }
//        return b1;
//
//    }
//
//    public static BigDecimal add(String v1, String... strings) {
//        BigDecimal b1 = new BigDecimal(v1);
//        for (String string : strings) {
//            BigDecimal b2 = new BigDecimal(string);
//            b1 = b1.add(b2);
//        }
//        return b1;
//    }
//
//    /**
//     * 减法
//     *
//     * @param v1
//     * @return
//     */
//    public static BigDecimal subtract(double v1, double... doublelist) {
//        BigDecimal b1 = new BigDecimal(Double.toString(v1));
//        for (double d : doublelist) {
//            BigDecimal b2 = new BigDecimal(Double.toString(d));
//            b1 = b1.subtract(b2);
//        }
//        return b1;
//
//    }
//
//    /**
//     * -
//     *
//     * @param v1
//     * @return
//     */
//    public static BigDecimal subtract(String v1, String... strings) {
//        BigDecimal b1 = new BigDecimal(v1);
//        for (String string : strings) {
//            BigDecimal b2 = new BigDecimal(string);
//            b1 = b1.subtract(b2);
//        }
//        return b1;
//    }
//
//    /**
//     * 相乘
//     *
//     * @param v1
//     * @return
//     */
//    public static BigDecimal multiply(double v1, double... doublelist) {
//        BigDecimal b1 = new BigDecimal(Double.toString(v1));
//        for (double d : doublelist) {
//            BigDecimal b2 = new BigDecimal(Double.toString(d));
//            b1 = b1.multiply(b2);
//        }
//        return b1;
//    }
//
//    public static BigDecimal multiply(String v1, String... strings) {
//        BigDecimal b1 = new BigDecimal(v1);
//        for (String string : strings) {
//            BigDecimal b2 = new BigDecimal(string);
//            b1 = b1.multiply(b2);
//        }
//        return b1;
//    }
//
//    /**
//     * 相除
//     *
//     * @param v1
//     * @return double
//     */
//    public static BigDecimal divide(double v1, double... doublelist) {
//        return divide(DEFAULT_DIV_SCALE, v1, doublelist);
//    }
//
//    public static BigDecimal divide(int scale, double v1, double... doublelist) {
//        return divide(scale, BigDecimal.ROUND_HALF_EVEN, v1, doublelist);
//    }
//
//    public static BigDecimal divide(int scale, int round_mode, double v1, double... doublelist) {
//        if (scale < 0) {
//            throw new IllegalArgumentException("The scale must be a positive integer or zero");
//        }
//
//        BigDecimal b1 = new BigDecimal(Double.toString(v1));
//        for (double d : doublelist) {
//            BigDecimal b2 = new BigDecimal(Double.toString(d));
//            b1 = b1.divide(b2, scale, round_mode);
//        }
//        return b1;
//
//    }
//
//    public static BigDecimal divide(String v1, String... strs) {
//        return divide(DEFAULT_DIV_SCALE, v1, strs);
//    }
//
//    public static BigDecimal divide(int scale, String v1, String... strs) {
//        return divide(DEFAULT_DIV_SCALE, BigDecimal.ROUND_HALF_EVEN, v1, strs);
//    }
//
//    public static BigDecimal divide(int scale, int round_mode, String v1, String... strs) {
//        if (scale < 0) {
//            throw new IllegalArgumentException("The scale must be a positive integer or zero");
//        }
//
//        BigDecimal b1 = new BigDecimal(v1);
//        for (String string : strs) {
//            BigDecimal b2 = new BigDecimal(string);
//            b1 = b1.divide(b2, scale, round_mode);
//        }
//
//        return b1;
//
//    }
//
//    public static BigDecimal round(double v, int scale) {
//        return round(v, scale, BigDecimal.ROUND_HALF_EVEN);
//    }
//
//    public static BigDecimal round(double v, int scale, int round_mode) {
//        if (scale < 0) {
//            throw new IllegalArgumentException("The scale must be a positive integer or zero");
//        }
//        BigDecimal b = new BigDecimal(Double.toString(v));
//        return b.setScale(scale, round_mode);
//    }
//
//    public static BigDecimal round(String v, int scale) {
//        return round(v, scale, BigDecimal.ROUND_HALF_EVEN);
//    }
//
//    public static BigDecimal round(String v, int scale, int round_mode) {
//        if (scale < 0) {
//            throw new IllegalArgumentException("The scale must be a positive integer or zero");
//        }
//        BigDecimal b = new BigDecimal(v);
//        return b.setScale(scale, round_mode);
//    }
//
//    /**
//     * 页面返回字符串，装换成整形数据返回
//     *
//     * @param numbers
//     * @return
//     */
//    public static List<Integer> getIntgerList(List<String> numbers) {
//        List<Integer> intList = new ArrayList<>();
//        numbers.forEach(number -> {
//            try {
//                intList.add(Integer.parseInt(number));
//            } catch (Exception e) {
//                return;
//            }
//        });
//        Collections.sort(intList);
//        return intList;
//    }

// public void main(String[] args) {
// System.out.println(2.21 - 2);
// System.out.println(7 * 0.8);
//
// System.out.println(subtract(312.21, 312));
// System.out.println(multiply(7, 0.8));
// }
//    public void main(String[] args) {
//        ArrayList<Integer> arrayList = new ArrayList<Integer>();
//        arrayList.add(1);
//        arrayList.add(1);
//        arrayList.add(1);
//        Map<Integer, Integer> splistResourseIndex = MathUtil.getSplistResourseIndexByThreadCount(10, arrayList.size());
//        Set<Entry<Integer, Integer>> entrySet = splistResourseIndex.entrySet();
//        for (Entry<Integer, Integer> entry : entrySet) {
//            Integer start = entry.getKey();
//            Integer end = entry.getValue();
//            System.out.println(start + ":" + end);
//
//            System.out.println(arrayList.subList(start, end));
//        }
//    }
}