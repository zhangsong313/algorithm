package com.zs.tixi.class23;

/*
 * 题目二：
 *      arr是面值数组， 其中的值都是正数且没有重复。再给定一个正数aim。
 *      每个值都认为是一张面值，且张数是无限的。
 *      返回组成aim的最小货币数。
 */
public class Code02_MinCoinsNoLimit {
    public static int minCoins(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    /**
     * 当前来到i位置,剩余需要凑出的钱数为rest,请返回最小货币数.
     *
     * 如果rest==0 返回0;
     * 如果i是末尾处,返回-1.
     * 尝试当前位置拿z张钱,要求:z*arr[i]<=rest
     *      递归调用:(i+1, rest-z*arr[i])
     *
     * 上面的所有尝试结果取最小值返回.(过滤掉返回-1的结果)
     */
    private static int process(int[] arr, int i, int rest) {
        if(rest==0) return 0;
        if(i==arr.length) return Integer.MAX_VALUE;
        int p = Integer.MAX_VALUE;
        for (int z = 0; z*arr[i]<=rest; z++) {
            int ans = process(arr, i + 1, rest - z * arr[i]);
            if(ans != Integer.MAX_VALUE){
                p = Math.min(p, ans+z);
            }
        }
        return p;
    }

    /**
     * 动态规划表
     * 根据上面的递归过程,(i, rest)位置的值依赖(i+1, rest-z*arr[i])的值
     *
     * arr的长度为N
     * 创建一个二维数组dp[N+1][aim+1]
     *
     * 第N行,除了第一列,剩余都是无穷大
     * 从第N-1行填起,(i, rest)的值为(i+1, rest-z*arr[i])+z中的最小值.注意去掉无穷大的结果
     *
     * 斜率优化:
     * 如果rest-arr[i]<0 或 (i, rest-arr[i])是无穷大,值为(i+1, rest)
     * 否则: 值为(i, rest-arr[i])+1和(i+1, rest)中的较小值
     */
    public static int dp1(int[] arr, int aim) {
        int N = arr.length;
        int[][] dp = new int[N+1][aim+1];
        for (int i = 1; i <= aim; i++) {
            dp[N][i] = Integer.MAX_VALUE;
        }
        for (int i = N-1; i >=0; i--) {
            for (int rest = 0; rest <= aim; rest++) {
                if(rest-arr[i]<0 || dp[i][rest-arr[i]]==Integer.MAX_VALUE){
                    dp[i][rest] = dp[i+1][rest];
                } else {
                    dp[i][rest] = Math.min(dp[i+1][rest], dp[i][rest-arr[i]]+1);
                }
            }
        }
        return dp[0][aim];
    }

    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        boolean[] has = new boolean[maxValue + 1];
        for (int i = 0; i < N; i++) {
            do {
                arr[i] = (int) (Math.random() * maxValue) + 1;
            } while (has[arr[i]]);
            has[arr[i]] = true;
        }
        return arr;
    }

    // 为了测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 300000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = randomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = minCoins(arr, aim);
            int ans2 = dp1(arr, aim);
//            int ans3 = dp2(arr, aim);
            if (ans1 != ans2
//                    || ans1 != ans3
            ) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
//                System.out.println(ans3);
                break;
            }
        }
        System.out.println("功能测试结束");
    }
}
