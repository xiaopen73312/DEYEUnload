package com.deye.xl.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 匹配 是否& 开头 ！结束
 */
public class RegexMatches {

    // String str = "&$DEYE$18082001$V2.A$0$DYL-I-TC-H-V2.00$DYL-I-TC-S-V2.1B$13555555555$!";
    public static boolean regexMes(String str) {

        String pattern = "^&+.+!$";
        int tnum = getSubStr(str, "&");
        int endnum = getSubStr(str, "!");
        if (tnum != endnum) {
            return false;
        }
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
//        System.out.println(m.matches());
        return m.matches();
    }

    /**
     * 获取字符串出现的个数
     *
     * @param str 传进来的原字符串
     * @param chs 传进来的要查找的字符串
     * @return 统计字符串出现的个数
     */
    public static int getSubStr(String str, String chs) {
        // 用空字符串替换所有要查找的字符串
        String destStr = str.replaceAll(chs, "");
//        System.out.println(destStr);
        // 查找字符出现的个数 = （原字符串长度 - 替换后的字符串长度）/要查找的字符串长度
        int charCount = (str.length() - destStr.length()) / chs.length();

        return charCount;
    }
}