package com.deye.demo.util;

import java.util.List;

public class NullUtil {

    /**
     * 判断是否非空
     */
    public static boolean isNotEmpty(Object obj) {
        if (obj instanceof List) {
            return obj != null && ((List<?>) obj).size() > 0;
        } else {
            return obj != null && !"".equals(obj.toString());
        }
    }

    /**
     * 一次判断多个对象是否为null(为null或者“”时返回false). 当传入的类型是List时，会验证list的长度，如果长度为0也会返回false
     */
    public static boolean isMultilNotEmpty(Object... objs) {
        for (Object obj : objs) {
            if (!isNotEmpty(obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断输入的字符串参数是否为空
     *
     * @return boolean 空则返回true,非空则flase
     */
    public static boolean isEmpty(String input) {
        return null == input || 0 == input.length() || 0 == input.replaceAll("\\s", "").length();
    }


    /**
     * 判断输入的字节数组是否为空
     *
     * @return boolean 空则返回true,非空则flase
     */
    public static boolean isEmpty(byte[] bytes) {
        return null == bytes || 0 == bytes.length;
    }

}
