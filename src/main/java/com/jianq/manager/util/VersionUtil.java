package com.jianq.manager.util;

/**
 * @author Leo on 2017/9/29
 */
public class VersionUtil {
    /**
     * 版本号比较
     *
     * @param newVersion
     * @param oldVersion
     * @return 返回true表示 newVersion > oldVersion 返回false表示newVersion <= oldVersion
     */
    public static boolean compareVersion(String newVersion, String oldVersion) {
        if (newVersion == null || oldVersion == null) {
            return false;
        }
        String[] versionArray1 = newVersion.split("\\.");//注意此处为正则匹配，不能用"."；
        String[] versionArray2 = oldVersion.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        if (diff > 0) {
            return true;
        }
        return false;
    }
}
