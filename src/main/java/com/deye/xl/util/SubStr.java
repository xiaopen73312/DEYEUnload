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


}
