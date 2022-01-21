package com.zs.tixi.class28;

/*
 * 题目二:
 *      判断str1和str2是否是旋转字符串.
 *      旋转字符串指的是将字符串包含任意个字符的左部分移动到右边.
 *      例如123456和456123互为旋转串.
 */
public class Code03_IsRotation {
    /**
     * 思路:旋转串的实质是从字符串中截取若干长度右部分作为开头,剩余左侧字符串作为结尾
     * 假设字符串长度为N
     * 那么将字符串复制一份拼接到原始字符串后面.
     * 拼接后的字符串中任意截取N长度的字符串都是其旋转串.且包含了所有旋转串的可能.
     */
    public static boolean isRotation(String a, String b) {
        if(a==null || b==null || a.length()!=b.length()) return false;
        a += a;
        return Code01_KMP.kmp(a, b)!=-1;
    }

    public static void main(String[] args) {
        String str1 = "yunzuocheng";
        String str2 = "zuochengyun";
        System.out.println(isRotation(str1, str2));

    }
}
