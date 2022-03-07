package com.zs.tixi.class39;

/**
 * int[] d，d[i]：i号怪兽的能力
 * int[] p，p[i]：i号怪兽要求的钱
 * 开始时你的能力是0，你的目标是从0号怪兽开始，通过所有的怪兽。
 * 如果你当前的能力，小于i号怪兽的能力，你必须付出p[i]的钱，贿赂这个怪兽，然后怪兽就会加入你
 * 他的能力直接累加到你的能力上；如果你当前的能力，大于等于i号怪兽的能力
 * 你可以选择直接通过，你的能力并不会下降，你也可以选择贿赂这个怪兽，然后怪兽就会加入你
 * 他的能力直接累加到你的能力上
 * 返回通过所有的怪兽，需要花的最小钱数
 * （课上会给出不同的数据量描述）
 */
public class Code04_MoneyProblem {
    /**
     * 以i和能力为变量。
     * 适合能力数值较小的情况。
     */
    public static int moneyProblem1(int[] d, int[] p){
        return process1(d, p, 0, 0);
    }

    /**
     * 当前来到i位置，能力为ability。
     * 请返回i位置以后，最终通过所有怪兽所花的最少钱。
     * 如果当前来到结束位置，返回0.
     * 如果能力大于等于当前怪兽的能力，可以尝试直接通过，递归调用(i+1, ability);
     * 尝试贿赂，递归调用p[i]+(i+1, ability+d[i])
     * 返回上面两种情况的较小值。
     */
    private static int process1(int[] d, int[] p, int i, int ab) {
        if(i == d.length) return 0;
        int p1 = Integer.MAX_VALUE;
        if(ab>=d[i]){
            p1 = process1(d, p, i+1, ab);
        }
        int p2 = process1(d, p, i+1, ab+d[i]) + p[i];
        return Math.min(p1, p2);
    }

    /**
     * 方法1改动态规划.
     * 数组d长度为N，所有怪兽能力累加和为M
     * 创建动态规划表dp[N+1][M+1]
     * 第N行全部为0
     * 第N-1..0行
     *      (i, ab)依赖(i+1, ab)和(i+1, ab+d[i])
     * 最终返回dp[0][0]
     */
    public static int dp1(int[] d, int[] p){
        int N = d.length;
        int M = 0;
        for (int ab : d) {
            M += ab;
        }
        int[] dp = new int[M+1];
        for (int i = N-1; i >=0; i--) {
            for (int j = 0; j<=M; j++) {
                int p1 = Integer.MAX_VALUE;
                if(j>=d[i]){
                    p1 = dp[j];
                }
                int p2 = Integer.MAX_VALUE;
                if(j+d[i]<=M){
                    p2 = dp[j+d[i]] + p[i];
                }
                dp[j] = Math.min(p1, p2);
            }
        }
        return dp[0];
    }

    /**
     * 以i和怪兽花费的金钱为变量。
     * 计算出所有怪兽贿赂所需金钱的累加和M
     * 从0尝试到M，第一个通过所有怪兽的贿赂和返回
     */
    public static long moneyProblem2(int[] d, int[] p){
        long M = 0;
        for (int i : p) {
            M += i;
        }
        for (int i = 0; i <M; i++) {
            if(process2(d, p, d.length-1, i)!=-1)return i;
        }
        return M;
    }

    /**
     * 现在来到i位置，0..i通过所有怪兽需要花费恰好m元，
     * 返回通过后获得的最大能力值。
     * 如果当前来到0，m==p[i]返回d[i],否则返回-1
     * 尝试当前位置不花钱，要求通过上个位置花费m的情况下结果不为-1.且通过后的能力大于等于当前怪兽的能力
     * 尝试当前位置花钱，要求花费m-p[i]可以通过上个位置。
     * 返回两种情况的较大值。
     */
    private static long process2(int[] d, int[] p, int i, int m) {
        if(i==0) return m==p[i]?d[i]:-1;
        long p1 = -1;
        long ability1 = process2(d, p, i - 1, m);
        if( ability1 >= d[i]){
            p1 = ability1;
        }
        long p2 = -1;
        long ability2 = process2(d, p, i-1, m-p[i]);
        if(ability2 != -1){
            p2 = ability2 + d[i];
        }
        return Math.max(p1, p2);
    }

    /**
     * 根据moneyProblem2写动态规划。
     * d长度为N，p累加和为M
     * 创建dp[N][M+1]
     * 第0行，只有p[0]位置为d[0]，其余位置为-1.
     * 第1..N-1行
     *      (i, m)依赖(i-1, m)和（i-1, m-p[i]）
     * 返回第N-1行第一个不为-1的下标。
     */
    public static long dp2(int[] d, int[] p){
        int N = d.length;
        int M = 0;
        for (int i : p) {
            M += i;
        }
        long[] dp = new long[M+1];

        for (int i = 0; i < dp.length; i++) {
            dp[i] = -1;
        }
        dp[p[0]] = d[0];

        for (int i = 1; i < N; i++) {
            for (int m = M; m >=0; m--) {
                long p1 = -1;
                if( dp[m] >= d[i]){
                    p1 = dp[m];
                }
                long p2 = -1;
                if(m-p[i]>=0 && dp[m-p[i]] != -1){
                    p2 = dp[m-p[i]] + d[i];
                }
                dp[m] = Math.max(p1, p2);
            }
        }
        for (int i = 0; i < dp.length; i++) {
            if(dp[i]!=-1) return i;
        }
        return M;
    }


    public static int[][] generateTwoRandomArray(int len, int value) {
        int size = (int) (Math.random() * len) + 1;
        int[][] arrs = new int[2][size];
        for (int i = 0; i < size; i++) {
            arrs[0][i] = (int) (Math.random() * value) + 1;
            arrs[1][i] = (int) (Math.random() * value) + 1;
        }
        return arrs;
    }

    public static void main(String[] args) {
        int len = 10;
        int value = 20;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            int[][] arrs = generateTwoRandomArray(len, value);
            int[] d = arrs[0];
            int[] p = arrs[1];
            long ans1 = moneyProblem1(d, p);
            long ans2 = dp1(d, p);
            long ans3 = moneyProblem2(d, p);
            long ans4 = dp2(d,p);
            if (ans1 != ans2 || ans2 != ans3 || ans1 != ans4) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println(ans4);
                throw new RuntimeException("测试失败");
            }
        }

    }
}
