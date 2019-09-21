package com.deye.xl.util;

public class SubStr {

    public static String[] get(String str) {
        String[] strs = str.split("\\$");
//        for (int i = 0, len = strs.length; i < len; i++) {
//            System.out.println(strs[i].toString());
//        }
        return strs;
    }

    public static String getip(String str) {
        String[] strs = str.split("\\.");
        for (int i = 0, len = strs.length; i < len; i++) {
            if (strs[i].length() == 1) {
                strs[i] = "00" + strs[i];
            }
            if (strs[i].length() == 2) {
                strs[i] = "0" + strs[i];
            }

        }
        return strs[0] + '.' + strs[1] + '.' + strs[2] + '.' + strs[3];
    }

    public static String getStr(String str) {
        Float strInt = Float.valueOf(str) * 100;
//        strInt.intValue();
        str = String.valueOf(strInt.intValue());
        String reStr = str;
        if (reStr.length() == 1) {
            reStr = "00" + reStr;

        }
        if (reStr.length() == 2) {
            reStr = "0" + reStr;

        }

        return reStr;
    }

    //补五位
    public static String getStrF(String str) {
        Float strInt = Float.valueOf(str) * 100;
//        strInt.intValue();
        str = String.valueOf(Math.abs(strInt.intValue()));
        String reStr = str;
        if (reStr.length() == 1) {
            reStr = "0000" + reStr;

        }
        if (reStr.length() == 2) {
            reStr = "000" + reStr;

        }
        if (reStr.length() == 3) {
            reStr = "00" + reStr;

        }
        if (reStr.length() == 4) {
            reStr = "0" + reStr;

        }
        return reStr;
    }
}
