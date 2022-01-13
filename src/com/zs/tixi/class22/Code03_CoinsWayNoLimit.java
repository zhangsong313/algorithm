package com.zs.tixi.class22;

/*
 * 题目三:
 *      arr是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
 *      每个值都认为是一种面值，且认为张数是无限的。
 *      返回组成aim的方法数。
 *      例如：arr={1, 2} aim=4
 *      方法如下：1+1+1+1, 1+1+2, 2+2
 *      一共就三种方法所以返回3.
 */
public class Code03_CoinsWayNoLimit {
    public static int coinsWay(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        return process(0, aim, arr);
    }

    /**
     * 当前来到arr的i位置，剩余需要凑的钱数为rest，请返回有多少种方法。
     *
     * 如果rest==0 返回1.
     * 如果当前来到结尾位置：返回0.
     * 如果arr[i]小于等于rest，尝试拿当前位置的钱：
     *      循环递归调用：(i, rest-arr[i]*i) 其中i是循环次数
     * 尝试不拿当前位置的钱。递归调用：(i+1, rest)
     *
     * 返回以上尝试的和。
     */
//    private static int process(int i, int rest, int[] arr){
//        if (rest==0) return 1;
//        if(i==arr.length) return 0;
//        int p1 = 0;
//        int rest2 = rest;
//        while (arr[i]<=rest2){
//            p1+= process(i+1, rest2-=arr[i], arr);
//        }
//        int p2 = process(i+1, rest, arr);
//        return p1+p2;
//    }

    /**
     * 当前来到arr的i位置，剩余需要凑出rest钱。请返回方法数。
     *
     * 如果rest==0,返回1.
     * 如果当前来到了末尾位置，返回0.
     *
     * 尝试从当前位置取z张，但取的钱数不能超过rest。
     * int p = 0;
     * 循环：z=0;当rest>=z*arr[i];z++
     *      p += 递归调用: (i+1, rest-z*arr[i])
     *
     * 返回p
     */
    private static int process(int i, int rest, int[] arr){
        if(rest==0) return 1;
        if(i==arr.length) return 0;

        int p=0;
        for (int z = 0; rest>=z*arr[i]; z++) {
            p+=process(i+1, rest-z*arr[i], arr);
        }
        return p;
    }

    /**
     * 动态规划表
     * 从上面的递归过程可以看到(i, rest)位置依赖(i+1, rest-z*arr[i])的值.
     *
     * arr长度为N
     * 创建数组dp[N+1][aim+1]
     * 先填第N行，除了第0列填1外，其余位置都填0.
     * 从第N-1行往上填:
     *      dp[i][rest] 等于 dp[i+1][rest-z*arr[i]]的累加和.
     * 斜率优化：
     * dp[i+1][rest-z*arr[i]]的累加和实际可以拆分为dp[i][rest-arr[i]]+dp[i+1][rest]
     */
    public static int dp1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N+1][aim+1];
        dp[N][0] = 1;
        for (int i = N-1; i >=0; i--) {
            for (int rest = 0; rest <= aim; rest++) {
                if(rest>=arr[i]){
                    dp[i][rest] = dp[i][rest-arr[i]]+dp[i+1][rest];
                } else {
                    dp[i][rest] = dp[i+1][rest];
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
        int maxLen = 10;
        int maxValue = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinsWay(arr, aim);
            int ans2 = dp1(arr, aim);
            if (ans1 != ans2 ) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
