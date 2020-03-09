package com.aatrox.common.utils;

/**
* @author aatrox
* @desc 进行布鲁尔型判断的工具类
* @date 2019/12/11
*/
public class BooleanUtil {

    /**
     * 比较source在不在target之中，只要一个符合就是true,不同类型比较为false;
     * source不允许为null
     * @param source
     * @param target
     * @return
     */
    public static boolean checkOr(Object source,Object ...target){
        //非基础类型,枚举的使用equals方法进行比较
        //不过枚举类型也是可以使用equals方法进行比较
        boolean isUseEquals=!(source.getClass().isPrimitive()||source.getClass().isEnum());
        if(source==null){
            return target==null;
        }
        if(target==null){
            return source==null;
        }
        boolean result=false;
        for (int i=0;i<target.length;i++){
            boolean judgeResult = isUseEquals ? target[i].equals(source) : target[i] == source;
            if(judgeResult){
                result=true;
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(BooleanUtil.checkOr("1","1","2","3"));
        System.out.println(BooleanUtil.checkOr("4","1","2","3"));
    }
}

