package com.aatrox.common.utils;

import java.util.*;

public class ListUtil {


    public static boolean isNotEmpty(List<?> list) {
        if (list != null && !list.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(List<?> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 根据指定长度进行切割分组，如果超过就丢掉
     *
     * @param list
     * @param split
     * @param dropFlag
     * @return
     */
    public static ArrayList<ArrayList<String>> splitList(ArrayList<String> list, int split, boolean dropFlag) {
        ArrayList<ArrayList<String>> list2 = new ArrayList<>();
        int size = list.size();
        int x = size / split;
        // 等于本身
        if (x <= 1) {
            list2.add(list);
            return list2;
        }
        for (int i = 0; i < x; i++) {
            try {
                List<String> subList = list.subList(i * split, (i + 1) * split);
                list2.add(new ArrayList<>(subList));
            } catch (Exception e) {
                // e.printStackTrace();
                if (dropFlag) {
                    break;
                } else {
                    List<String> subList = list.subList(i * split, list.size());
                    list2.add(new ArrayList<>(subList));
                }
            }
        }
        return list2;
    }

    /**
     * 可以将无序的map进行划分，根据个数进行划分
     *
     * @param executeQuery
     * @param split        每隔间隙进行划分
     * @return
     */
    public static ArrayList<Map<String, Object>> takeList(List<Map<String, Object>> executeQuery, int split) {
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();

        for (Map<String, Object> hashMap : executeQuery) {
            int i = 1;
            HashMap<String, Object> hashMap2 = new HashMap<>();
            Iterator<Map.Entry<String, Object>> iterator = hashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                if (i <= split) {
                    Map.Entry<String, Object> next = iterator.next();
                    hashMap2.put(next.getKey(), next.getValue());
                } else {
                    break;
                }
            }
            arrayList.add(hashMap2);
        }
        return arrayList;
    }


    /**
     * 新增一个key，在原有的有序Map中，获取该key在Map中的区间位置，从而获得Map的值
     * 例如  0-100   101 -200   放入99，那么就可以获得map中0-100key的值
     *
     * @param addKey
     * @param hashmap
     * @return
     */
    public static Object getHashIndexValue(double addKey, Map<Double, Object> hashmap, Integer index) {

        List<Double> list = new ArrayList<>();
        Set<Map.Entry<Double, Object>> entrySet = hashmap.entrySet();
        int i = 0;
        for (Map.Entry<Double, Object> entry : entrySet) {
            list.add(i, entry.getKey());
        }

        ArrayList<Double> list2 = new ArrayList<>(list);
        list2.add(addKey);

        Collections.sort(list2);
        int indexOf = list2.indexOf(addKey);

        Object key;
        if (null == index) {
            key = hashmap.get(list.get(indexOf));
        } else {
            int temp;
            if (index + indexOf < 0) {
                temp = 0;
            } else if (index + indexOf > list2.size()) {
                temp = list2.size();
            } else {
                temp = index + indexOf;
            }
            key = hashmap.get(list.get(temp));
        }
        return key;
    }

    public static final <T> List<T> NEW(T... list) {
        List<T> ts = Arrays.asList(list);
        ArrayList<T> ts1 = new ArrayList<>(ts);
        //清除null所有数据
        ts1.removeAll(Collections.singleton(null));
        return ts1;
    }

    /**
     * 将List转为字符串
     *
     * @param list     list
     * @param separtor 分隔符
     * @return 字符串
     */
    public static final String toString(List<?> list, String separtor, boolean checkString) {
        StringBuilder sb = new StringBuilder();
        if (list != null && !list.isEmpty()) {
            Class objClz = list.get(0).getClass();
            for (int i = 0; i < list.size(); i++) {
                sb.append(i == 0 ? "" : separtor).append(checkString && objClz == String.class ? "'" + list.get(i) + "'" : list.get(i));
            }
        }
        return sb.toString();
    }

    /**
     * 将List转为字符串
     * 默认是不记string类型
     */
    public static final String toString(List<?> list, String separtor) {
        return toString(list, separtor, false);
    }

    public static void main(String[] args) {
        List<String> s = Arrays.asList("123", "121313");
        System.out.println(ListUtil.toString(s, "  OR  "));
    }

    public static final <E> E[] toArray(List<E> list, E[] es) {
        return list.toArray(es);
    }

    /**
     * 判断两个list是否相同
     *
     * @param list1
     * @param list2
     * @return
     */
    public static boolean checkSameList(List<Integer> list1, List<Integer> list2) {
        if (ListUtil.isEmpty(list1) || ListUtil.isEmpty(list2)) {
            return ListUtil.isEmpty(list1) && ListUtil.isEmpty(list2) ? true : false;
        }
        list1.sort(Comparator.naturalOrder());
        list2.sort(Comparator.naturalOrder());
        //此处进行去重
        LinkedHashSet<Integer> set1 = new LinkedHashSet<>(list1.size());
        LinkedHashSet<Integer> set2 = new LinkedHashSet<>(list2.size());
        set1.addAll(list1);
        set2.addAll(list2);
        if (set1 == null && set2 == null) {
            return true; // Both are null
        }

        if (set1 == null || set2 == null || set1.size() != set2.size()
                || set1.size() == 0 || set2.size() == 0) {
            return false;
        }

        Iterator ite1 = set1.iterator();
        Iterator ite2 = set2.iterator();

        boolean isFullEqual = true;

        while (ite2.hasNext()) {
            if (!set1.contains(ite2.next())) {
                isFullEqual = false;
                break;
            }
        }
        return isFullEqual;
    }
}
