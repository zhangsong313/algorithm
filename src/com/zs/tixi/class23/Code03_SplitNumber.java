package com.zs.tixi.class23;

/*
 * 题目三：
 *      给定一个正数n，求n的裂开方法数。
 *      规定：后面的数不能比前面的数小。
 *      比如4的裂开方法有：
 *      1+1+1+1, 1+1+2, 1+3, 2+2, 4
 *      5种，所以返回5.
 */
public class Code03_SplitNumber {

    // n为正数
    public static int ways(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return process(1, n);
    }

    /**
     * 上个数是pre,还剩下rest要拆,请返回拆开的方法数.
     *
     * 如果rest==0 返回1
     * 如果pre>rest 返回0
     * 尝试拆num,范围为[pre..rest]
     *      递归调用:(num, rest-num)
     * 返回上面的结果数之和
     */
    private static int process(int pre, int rest) {
        if(rest==0||pre==rest) return 1;
        if(pre>rest) return 0;
        int p = 0;
        for (int num = pre; num <= rest; num++) {
            p+=process(num, rest-num);
        }
        return p;
    }

    /**
     * 动态规划表
     * 从上面的递归过程可以看出,(pre, rest)的值依赖(num, rest-num)的值
     *
     * 创建一张二维表dp[n+1][n+1]
     * 第一列全部填1
     * 对角线全部填1.
     * 表的左下半部分其余位置都是0.
     * 从第n-1行开始填.
     * (pre, rest)的值是(num, rest-num)的累加和
     *
     * 斜率优化:
     * (pre, rest)的值等于(pre+1, rest)+(pre, rest-pre)
     *
     * 返回dp[1][n]
     */
    public static int dp1(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[][] dp = new int[n+1][n+1];
        for (int i = 0; i <= n; i++) {
            dp[i][i] = 1;
        }
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }
        for (int pre = n-1; pre >=1; pre--) {
            for (int rest = pre; rest <= n; rest++) {
                dp[pre][rest] = dp[pre+1][rest]+dp[pre][rest-pre];
            }
        }
        return dp[1][n];
    }

    public static void main(String[] args) {
        int test = 39;
        System.out.println(ways(test));
        System.out.println(dp1(test));
    }
}
