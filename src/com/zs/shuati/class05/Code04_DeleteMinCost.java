package com.zs.shuati.class05;

import java.util.HashSet;
import java.util.Set;

/**
 * 4 给定两个字符串s1和s2，问s2最少删除多少字符可以成为s1的子串？
 * 比如 s1 = "abcde"，s2 = "axbc"，s2删掉'x'即可，返回1
 */
public class Code04_DeleteMinCost {

    /**
     * 列举s2所有子序列，与s1通过kmp判断是否为子串。
     *
     */
    public static int minCost1(String s1, String s2) {
        // 收集所有子序列
        char[] str2 = s2.toCharArray();
        Set<String> set = new HashSet<>();
        String path = "";
        allSubSquence(set, str2, 0, path);

        // 判断子序列是否是s1子串。找到最少删除字符数。
        int ans = Integer.MAX_VALUE;
        for (String s : set) {
            if(s1.indexOf(s) != -1){
                ans = Math.min(ans, str2.length-s.length());
            }
        }
        return ans;
    }

    private static void allSubSquence(Set<String> set, char[] str2, int i, String path) {
        if(i==str2.length){
            set.add(path);
            return;
        }
        allSubSquence(set, str2, i+1, path);
        allSubSquence(set, str2, i+1, path+str2[i]);
    }

    /**
     * 考察s1所有子串的情况，判断s2能否通过删除转换为对应的子串
     */
    public static int minCost2(String s1, String s2) {
        if (s1.length() == 0 || s2.length()==0) {
            return s2.length();
        }
        int ans = s2.length(); // 初始化答案为s2删除为空串的场景
        for (int i = 0; i < s1.length(); i++) {
            for (int j = i+1; j <= s1.length(); j++) {
                String subStr = s1.substring(i, j); // s1的子串.
//                System.out.println("sub : "+subStr);
                int distance = onlyDelete(subStr, s2); // s2通过删除变为subStr情况下，删除的字符数。
                ans = Math.min(ans, distance);

            }
        }
        return ans;
    }

    /**
     * s2通过删除变为s1情况下，删除的字符数。
     * 无法实现返回整型最大值。
     * 考虑s2的len2长度 通过删除变为 s1的len1长度情况下的最少字符数。
     * len2 == len1 == 0 返回0
     * len2 < len1 返回无穷大
     * len1 == 0 返回len2的长度
     * 考虑0..len2-1转换为0..len1范围:递归调用(len1, len2-1)+1
     * 考虑0..len2-1转换为0..len1-1范围：如果len1位置字符等于len2位置字符,递归调用(len1-1, len2-1)
     * 两种情况结果取较小值。
     *
     * 根据上述推论，(i,j)位置依赖(i, j-1)和(i-1, j-1).
     *
     */
    private static int onlyDelete(String s1, String s2) {
        if(s2.length()<s1.length()) return Integer.MAX_VALUE;

        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int N = str1.length;
        int M = str2.length;
        int[] dp = new int[M+1];
        for (int i = 1; i <=M ; i++) { // 第0行
            dp[i] = i;
        }
        int pre = dp[0];
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j < i && j <=M; j++) {
                pre = dp[j];
                dp[j] =  Integer.MAX_VALUE; // 将len2小于len1的情况全部填写为无穷大.
            }
            for (int j = i; j <= M; j++) {
                int cur = dp[j];
                dp[j] = Integer.MAX_VALUE;
                if(dp[j-1] != Integer.MAX_VALUE){
                    dp[j] = dp[j-1] + 1;
                }
                if(str1[i-1] == str2[j-1]){
                    dp[j] = pre;
                }
                pre = cur;
            }
        }
        return dp[M];
    }

    /**
     * 在解法2中包含了很多重复的过程，比如:
     * "abcd", "def"
     * 由于会对所有s1的子串与s2做删除情况下的距离计算。
     * 而("abc", "def")与("abcd", "def")相比，最终的dp表仅仅是少了最后一行而已。
     * 因此，对于起始位置相同的子串，计算删除情况下的距离可以复用上次的计算结果。
     */
    public static int minCost3(String s1, String s2) {
        if(s1.length()==0 || s2.length()==0){
            return s2.length();
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int N = str1.length;
        int M = str2.length;
        int[][] dp = new int[N+1][M+1];
        int ans = s2.length();
        for (int i = 0; i < s1.length(); i++) {

            // 初始化第一行数据 : j=i
            for (int k = 0; k <=M ; k++) {
                dp[i][k] = k;
            }
            for (int j = i+1; j < s1.length()+1; j++) {
                // 当前处理的是i..j范围的子串。直接利用之前的数据填写第j行即可，不需要重新填写整张表
                for (int k = 0; k < j-i && k<=M; k++) {
                    dp[j][k] = Integer.MAX_VALUE;
                }
                for (int k = j-i; k <=M ; k++) {
                    dp[j][k] = Integer.MAX_VALUE;
                    if(dp[j][k-1] != Integer.MAX_VALUE){
                        dp[j][k] = dp[j][k-1] + 1;
                    }
                    if(str1[j-1] == str2[k-1]){
                        dp[j][k] = dp[j-1][k-1];
                    }
                }

                ans = Math.min(ans, dp[j][M]);
            }
        }
        return ans;
    }

    public static String generateRandomString(int l, int v) {
        int len = (int) (Math.random() * l);
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ('a' + (int) (Math.random() * v));
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {
//        System.out.println(minCost1("dec", "a"));
//        System.out.println(minCost3("dabc", "aba"));


        int str1Len = 20;
        int str2Len = 10;
        int v = 5;
        int testTime = 10000;
        boolean pass = true;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            String str1 = generateRandomString(str1Len, v);
            String str2 = generateRandomString(str2Len, v);
            int ans1 = minCost1(str1, str2);
            int ans2 = minCost2(str1, str2);
            int ans3 = minCost3(str1, str2);
//            int ans4 = minCost4(str1, str2);
            if (ans1 != ans2
//                    || ans3 != ans4
                    || ans1 != ans3
            ) {
                pass = false;
                System.out.println(str1);
                System.out.println(str2);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
//                System.out.println(ans4);
                break;
            }
        }
        System.out.println("test pass : " + pass);
    }
}
