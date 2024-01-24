package com.example.studentapartmentms.common;

/**
 * 工具类
 */
public class Utils {
    /**
     * 判断文本是否是数字
     * @param str 要判断的文本
     * @return 数字返回 true，反之 false
     */
    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
