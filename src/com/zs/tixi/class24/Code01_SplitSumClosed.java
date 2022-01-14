package com.zs.tixi.class24;

/*
 * 题目一：
 *      给定一个正数数组arr，
 *      请把arr中所有的数分成两个集合。尽量让两个集合的累加和接近。
 *      返回：
 *          最接近的情况下，较小集合的累加和。
 */
public class Code01_SplitSumClosed {

    public static int right(int[] arr) {
        if(arr==null|| arr.length==0) return 0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum+=arr[i];
        }
        return process(0, sum/2, arr);
    }

    /**
     * 当前来到i位置,请返回i位置到末尾最接近rest但不超过rest的累加和.
     *
     * 如果当前来到arr结尾位置,返回0.
     * 如果当前位置的数大于rest,返回0.
     * 尝试选当前位置的数.递归调用(i+1, rest-arr[i]),返回结果加arr[i]
     * 尝试不选当前位置的数,递归调用(i+1, rest)
     *
     * 返回上面两种尝试结果的较大值.
     */
    private static int process(int i, int rest, int[] arr){
        if(i==arr.length) return 0;
        int p1 = 0;
        if(arr[i]<=rest) p1+=process(i+1, rest-arr[i], arr)+arr[i];
        int p2 = process(i+1, rest, arr);
        return Math.max(p1, p2);
    }


    /**
     * 动态规划表
     * 根据上面的递归过程可以知道, (i, rest)位置的值依赖(i+1, rest-arr[i])和(i+1, rest)位置的值
     *
     * arr的长度为N,累加和为sum
     * 创建一张二维表dp[N+1][sum/2+1]
     *
     * 第N行的所有值都为0.
     * 从第N-1行开始填起.
     * (i, rest)的值等于(i+1 rest-arr[i])+arr[i]和(i+1, rest)的较大值.
     *
     * 返回dp[0][sum/2]
     */
    public static int dp(int[] arr) {
        if(arr==null|| arr.length==0) return 0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum+=arr[i];
        }
        int N = arr.length;
        int R = sum/2+ 1;
        int[][] dp = new int[N+1][R];
        for (int i = N-1; i >=0; i--) {
            for (int rest = 0; rest < R; rest++) {
                dp[i][rest] = dp[i+1][rest];
                if(arr[i]<=rest) dp[i][rest] = Math.max(dp[i][rest], dp[i+1][rest-arr[i]]+arr[i]);
            }
        }

        return dp[0][sum/2];
    }

    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 50;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = right(arr);
            int ans2 = dp(arr);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
