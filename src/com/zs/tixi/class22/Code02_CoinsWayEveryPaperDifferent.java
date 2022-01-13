package com.zs.tixi.class22;

/*
 * 题目二：
 *      arr是货币数组，其中的值都是正数。再给定一个正数aim。
 *      每个值都认为是一张货币。
 *      即使是值相同的货币也认为每一张都是不同的。
 *      返回组成aim的方法数。
 *      例如：arr={1, 1, 1}, aim = 2
 *      第0个和第1个能组成2，第1个和第2个呢个组成2，第0个和 第三个能组成2，
 *      一共三种方法，所以返回3.
 */
public class Code02_CoinsWayEveryPaperDifferent {

    public static int coinWays(int[] arr, int aim) {
        return process(0, aim, arr);
    }

    /**
     * 从左到右的尝试模型
     * 当前来到i位置，剩余需要凑出的钱数为rest，请返回有多少种方法。
     * 如果当前位置为末尾位置：
     *      如果rest为0，返回1，否则返回0.
     * 如果rest为0，直接返回1.
     *
     * 如果rest大于等于arr[i]，尝试拿当前位置货币，递归调用:(i+1, rest-arr[i])
     * 尝试不拿当前位置的钱，递归调用(i+1, rest)
     *
     * 返回以上两种尝试结果的和。
     */
    private static int process(int i, int rest, int[] arr){
        if(rest==0) return 1;
        if(i==arr.length) return 0;

        int p1=0;
        if(rest>=arr[i]){
            p1 = process(i+1, rest-arr[i], arr);
        }
        int p2 = process(i+1, rest, arr);
        return p1+p2;
    }

    /**
     * 动态规划表
     * 根据上面的递归过程：(i, rest)位置的值依赖(i+1, rest-arr[i])和(i+1, rest)位置的值
     *
     * arr的长度为N
     * 创建一个二维数组dp[N+1][aim+1]
     * 先填第N行，除了rest==0填1以外，其它位置都是0.
     * 从第N-1行往上填。
     *      第0列都填1.
     *      每列值都等于(i+1, rest-arr[i])和(i+1, rest)位置的和.
     *
     * 返回dp[0][aim]
     */
    public static int dp(int[] arr, int aim) {
        int N = arr.length;
        int[][] dp = new int[N+1][aim+1];
        dp[N][0] = 1;
        for (int i = N-1; i >=0; i--) {
            for (int rest = 0; rest <= aim; rest++) {
                int p1=0;
                if(rest>=arr[i]){
                    p1 = dp[i+1][rest-arr[i]];
                }
                int p2 = dp[i+1][rest];
                dp[i][rest] = p1+p2;
            }
        }

        return dp[0][aim];
    }

    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
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
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinWays(arr, aim);
            int ans2 = dp(arr, aim);
            if (ans1 != ans2) {
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
