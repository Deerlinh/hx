package com.jianq.manager.util;


import java.util.List;

/**
 * 集合工具类
 *
 * @author Leo on 2017/8/29
 */
public class ListUtil {

    public static boolean isEmpty(List list) {
        boolean flag = false;
        if (null == list || list.isEmpty()) {
            flag = true;
        }
        return flag;
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }

    /**
     * 随机获取list中的一个
     *
     * @param tlist
     * @param <T>
     * @return
     */
    public static <T> T randomAccessOne(List<T> tlist) {
        T t = null;
        if (isNotEmpty(tlist)) {
            int index = (int) (Math.random() * tlist.size());
            t = tlist.get(index);
        }
        return t;
    }
}
