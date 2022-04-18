package com.zs.shuati.class04;

/**
 * 7.给定三个字符串s1、s2、s3，请你帮忙验证s3是否是由s1和s2交错组成的
 * Leetcode题目：https://leetcode.com/problems/interleaving-string/
 */
public class Code07_InterleavingString {

    /**
     * 思路：
     * 样本对应的动态规划：
     * s1和s2指定范围是否可以交错组成s3对应范围
     */
    public static boolean interleavingString(String s1, String s2, String s3){
        if(s1 == null || s2 == null || s3 == null){
            return false;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        char[] str3 = s3.toCharArray();
        if(str1.length != str2.length || str2.length != str3.length){
            return false;
        }

        return process(str1.length, str2.length, str1, str2, str3);
    }

    /**
     * 请返回str1的前len1范围和str2的len2范围是否可以交错组成str3的len3(len1+len2)范围。
     * base case:len1=0且len2=0, 返回true
     * len1=0且len2不等于0，递归调用process(0, len2-1)为true 且 len2的最后一个字符与len3相等.
     * len2=0且len1不等于0，递归调用process(len1-1, 0)为true 且 len1的最后一个字符与len3相等。
     * 尝试1：
     *      len3的最后一个字符来自len1的最后一个字符
     *      len3的最后一个字符与len1的最后一个字符相同 且 递归调用process(len1-1, len2)为true
     *  尝试2：
     *      len3的最后一个字符来自len2的最后一个字符
     *      len3的最后一个字符与len2的最后一个字符相同 且 递归调用process(len1, len2-1)为true
     * 结果为两种尝试结果取并集。
     *
     * 所以 (i, j)位置的答案依赖(i-1, j)和(i, j-1)
     *
     */
    private static boolean process(int len1, int len2, char[] str1, char[] str2, char[] str3) {
        if(len1==0 && len2==0) return true;
        if(len1==0)return process(0, len2-1, str1, str2, str3);
        if(len2==0)return process(len1-1, 0, str1, str2, str3);
        boolean p1 = str3[len1+len2-1]==str1[len1-1] && process(len1-1, len2, str1, str2, str3);
        boolean p2 = str3[len1+len2-1]==str2[len2-1] && process(len1, len2-1, str1, str2, str3);
        return p1 || p2;
    }

    /**
     * 根据process1创建动态规划表
     * s1长度为M
     * s2长度为N
     * dp[M+1][N+1]
     * dp[0][0]为true
     * 第0行：
     *      当前来到第j列：
     *      str2[j-1] == str3[j-1] 且 dp[0][j-1]==true
     * 第0列：
     *      当前来到第i行
     *      str1[i-1] == str3[i-1] 且 dp[i-1][0]==true
     *
     * 第i行,第j列
     *      dp[i-1][j] || dp[i][j-1]
     */
    public static boolean interleavingString2(String s1, String s2, String s3){
        if(s1 == null || s2 == null || s3 == null){
            return false;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        char[] str3 = s3.toCharArray();
        if(str1.length != str2.length || str2.length != str3.length){
            return false;
        }

        boolean[][] dp = new boolean[str1.length+1][str2.length+1];
        dp[0][0] = true;
        for (int i = 0; i <= str1.length; i++) {
            dp[i][0] = dp[i-1][0] || str1[i-1]==str3[i-1];
        }
        for (int i = 0; i <= str2.length; i++) {
            dp[0][i] = dp[0][i-1] || str2[i-1]==str3[i-1];
        }
        for (int i = 1; i <= str1.length; i++) {
            for (int j = 1; j <= str2.length; j++) {
                dp[i][j] = dp[i-1][j] || dp[i][j-1];
            }
        }
        return dp[str1.length][str2.length];
    }
}
