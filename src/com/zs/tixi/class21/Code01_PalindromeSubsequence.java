package com.zs.tixi.class21;

/*
 * 题目一：
 *      给定一个字符串str，返回这个字符串的最长回文子序列长度。
 *      比如：str="a12b3c43def2ghi1kpm"
 *      最长回文子序列是“1234321” 或者"123c321"，返回长度7
 *      测试链接：https://leetcode.com/problems/longest-palindromic-subsequence/
 */
public class Code01_PalindromeSubsequence {

    /**
     * 思路一：
     *      之前已经解决过最长公共子序列问题，
     *      将字符串反转，求字符串和反转后字符串的最长公共子序列长度
     *      结果就是字符串的最长回文子序列长度
     */
    public static int lpsl1(String s) {
        if(s==null|| s.length()==0) return 0;
        char[] s1 = s.toCharArray();
        char[] s2 = reverse(s1);
        return longestCommonSubsequence2(s1, s2);
    }

    /**
     * 从com.zs.tixi.class20.Code04_LongestCommonSubsequence复制过来的。
     * @return
     */
    public static int longestCommonSubsequence2(char[] str1, char[] str2) {
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

    /**
     * 反转字符数组
     */
    private static char[] reverse(char[] s1) {
        int N = s1.length;
        char[] s2 = new char[N];
        for (int i = 0; i < N; i++) {
            s2[i] = s1[N-1-i];
        }
        return s2;
    }

    /**
     * 思路二：
     *      不断缩小范围尝试。
     */
    public static int lpsl2(String s) {
        if(s==null|| s.length()==0) return 0;
        char[] s1 = s.toCharArray();
        return process(s1, 0, s.length()-1);
    }

    /**
     * 请返回字符数组s上L..R范围内的最长回文子序列。
     * 如果L>R 返回0
     * 如果L==R 返回1
     * 如果s[L]==s[R] 递归调用：L=L+1, R=R-1。结果加2后返回。
     * 尝试缩小左侧范围：递归调用:L=L+1, R=R
     * 尝试缩小右侧范围: 递归调用:L=L, R=R-1
     * 返回以上两种尝试结果的较大值。
     */
    private static int process(char[] s, int L, int R){
        if(L>R) return 0;
        if(L==R) return 1;
        if(s[L]==s[R]) return process(s, L+1, R-1)+2;
        int p1 = process(s, L+1, R);
        int p2 = process(s, L, R-1);
        return Math.max(p1, p2);
    }

    /**
     * 动态规划表
     *
     * 分析上面递归过程的依赖关系。
     * [L, R]位置依赖[L+1, R-1], [L+1, R], [L, R-1]三个位置。
     * 假设字符串s的长度为N，创建一张N*N大小的二维表。行表示L， 列表示R。
     * 二维表的左下部分是L>R的场景，都填0.
     * 对角线上是L==R，都填1.
     * 剩下的任意位置值依赖，左下，下，左三个位置。
     * 从最后一行填到第一行，每行都从L, L+1位置填起，最后返回右上角[0, N-1]位置的值
     */
    public static int lpsl3(String str) {
        char[] s = str.toCharArray();
        int N = s.length;
        int[][] dp = new int[N][N];
        for (int i = 0; i < N; i++) {
            dp[i][i] = 1;
        }
        for (int L = N-1; L>=0; L--) {
            for (int R = L+1; R < N; R++) {
                if(s[L]==s[R]){
                    dp[L][R] = dp[L+1][R-1]+2;
                    continue;
                }
                dp[L][R] = Math.max(dp[L+1][R], dp[L][R-1]);
            }
        }
        return dp[0][N-1];
    }

    public static void main(String[] args) {
        int ans = lpsl3("bbbab");
        System.out.println(ans);
    }

}
