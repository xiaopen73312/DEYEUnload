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

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
//        System.out.println(m.matches());
        return m.matches();
    }

}