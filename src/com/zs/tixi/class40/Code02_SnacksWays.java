package com.zs.tixi.class40;

/**
 * 牛牛家里一共有n袋零食, 第i袋零食体积为v[i]，背包容量为w，牛牛想知道在总体积不超过背包容量的情况下,
 * 一共有多少种零食放法，体积为0也算一种放法
 * 1 <= n <= 30, 1 <= w <= 2 * 10^9，v[I] (0 <= v[i] <= 10^9）
 */
public class Code02_SnacksWays {
    /**
     * 以位置i和剩余容量rest为动态规划参数
     */
    public static int ways1(int[] arr, int w){
        return process1(arr, 0, w);
    }

    /**
     * 当前来到i位置，还剩余rest容量，请返回i..end位置总共右多少种方法。
     * 如果当前来到结束位置,直接返回1.
     * 尝试不拿当前位置零食，递归调用(i+1, rest)
     * 如果arr[i]<=rest,尝试拿当前位置零食，递归调用(i+1, rest-arr[i])
     * 返回两种方式返回的方法数和。
     */
    private static int process1(int[] arr, int i, int rest) {
        if(i==arr.length) return 1;
        int ans = process1(arr, i+1, rest);
        if(arr[i]<=rest){
            ans += process1(arr, i+1, rest-arr[i]);
        }

        return ans;
    }

    /**
     * process1建立动态规划表
     * arr长度为N
     * 创建dp数组dp[N+1][w+1]
     * 第N行全部填1.
     * 第N-1..0行
     *      (i, j)位置依赖(i+1, j)和(i+1, rest-arr[i])位置。
     * 返回dp[0][w]
     */
    public static int dp(int[] arr, int w){
        int N = arr.length;
        int[] dp = new int[w+1];
        for (int i = 0; i <= w; i++) {
            dp[i] = 1;
        }

        for (int i = N-1; i >=0; i--) {
            for (int rest = w; rest >=arr[i]; rest--) {
                dp[rest] += dp[rest-arr[i]];
            }
        }

        return dp[w];
    }

    public static int ways3(int[] arr, int w) {
        int N = arr.length;
        int[][] dp = new int[N][w + 1];
        for (int i = 0; i < N; i++) {
            dp[i][0] = 1;
        }
        if (arr[0] <= w) {
            dp[0][arr[0]] = 1;
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= w; j++) {
                dp[i][j] = dp[i - 1][j] + ((j - arr[i]) >= 0 ? dp[i - 1][j - arr[i]] : 0);
            }
        }
        int ans = 0;
        for (int j = 0; j <= w; j++) {
            ans += dp[N - 1][j];
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = { 4, 3, 2, 9, 4, 3, 2, 9, 4, 3,
                4, 3, 2, 9, 4, 3, 2, 9, 4, 3,
                4, 3, 2, 9, 4, 3, 2, 9, 4, 3 };

        int w = 500;
        long start = System.currentTimeMillis();
        System.out.println(ways1(arr, w));
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        System.out.println(dp(arr, w));
        System.out.println(ways3(arr, w));

    }
}
