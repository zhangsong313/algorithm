package com.zs.tixi.class20;

/*
 * 题目四：
 *      给定两个字符串str1和str2
 *      返回这两个字符串的最长公共子序列
 *      比如：str1="a12b3c456d", str2="1ef23ghi4j56k"
 *      最长公共子序列是"123456"，所以返回长度6.
 *       这个问题leetcode上可以直接测
 *  链接：https://leetcode.com/problems/longest-common-subsequence/
 */
public class Code04_LongestCommonSubsequence {

    /**
     * 思路:
     *      对于两个字符串对应的字符数组,考察0..i位置和0..j位置的最长公共子序列长度。
     *      如果i位置数和j位置数相同：结果为0..i-1和0..j-1位置的最长公共子序列长度加1.
     *      否则:结果为[0.。i-1, 0..j]和[0..i, 0..j-1]的较大值.
     */
    public static int longestCommonSubsequence1(String s1, String s2) {
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        return process1(str1, str2, str1.length-1, str2.length-1);
    }

    /**
     * 当前str1来到i位置，str2来到j位置，请返回[0..i]和[0..j]范围内的最长公共子序列。
     *
     * 如果i==0且j==0。如果str1[0]==str2[0]，返回1，否则返回0
     * 如果i==0.如果str1[0]==str2[j]，返回1，否则递归调用:i=0, j=j-1.
     * 如果j==0.如果str1[i]==str2[0],返回1,否则递归调用:i=i-1, j=0
     * 如果str1[i]==str2[j]，递归调用:i=i-1, j=j-1。返回结果加1，直接返回
     * 尝试i位置不参与比较:递归调用:i=i-1, j=j.
     * 尝试j位置不参与比较：递归调用:i=i, j=j-1
     * 返回以上两种尝试结果的较大值。
     */
    public static int process1(char[] str1, char[] str2, int i, int j){
        if(i==0 && j==0) return str1[0]==str2[0]?1:0;
        if(i==0) return str1[0]==str2[j]?1:process1(str1, str2, 0, j-1);
        if(j==0) return str1[i]==str2[0]?1:process1(str1, str2, i-1, 0);
        if(str1[i]==str2[j]) return process1(str1, str2, i-1, j-1)+1;
        int p1 = process1(str1, str2, i-1, j);
        int p2 = process1(str1, str2, i, j-1);
        return Math.max(p1, p2);
    }

    /**
     * 动态规划表。
     * s1长度为N, s2长度为M
     * 创建N*M数组dp
     * 分析上面的递归逻辑：
     * dp[0][0] 可以直接填。
     * dp[0][j] 依赖 dp[0][j-1]位置，第一行也可以依次填出。
     * dp[i][0] 依赖 dp[i-1][0]位置，第一列可以依次填出。
     * dp[i][j] 依赖 dp[i-1][j-1]，dp[i][j-1]和dp[i-1][j]位置,从第1行可以依次填到最后一行。
     *
     * 最后返回dp[N-1][M-1]
     */
    public static int longestCommonSubsequence2(String s1, String s2) {
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int N = str1.length;
        int M = str2.length;
        int[][] dp = new int[N][M];
        dp[0][0] = str1[0]==str2[0]?1:0;
        for (int j = 1; j < M; j++) {
            dp[0][j] = str1[0]==str2[j]?1:dp[0][j-1];
        }
        for (int i = 1; i < N; i++) {
            dp[i][0] = str1[i]==str2[0]?1:dp[i-1][0];
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                if(str1[i]==str2[j]) {
                   dp[i][j] = dp[i-1][j-1]+1;
                   continue;
                }
                int p1 = dp[i-1][j];
                int p2 =dp[i][j-1];
                dp[i][j] = Math.max(p1, p2);
            }
        }

        return dp[N-1][M-1];
    }
}
