package com.utils;

public class CodeUtils {

    private static final String code = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 获取随机长度编码
     * @param length 编码长度
     */
    public static String getRandomCodes(int length) {
        StringBuffer rands = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int rand = (int) (Math.random() * code.length());
            rands.append(code.charAt(rand));
        }
        return rands.toString();
    }

}
