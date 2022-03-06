package com.zs.shuati.class02;

import java.util.Arrays;

/**
 * 现有司机N*2人，调度中心会将所有司机平分给A、B两区域，i号司机去A可得收入为income[i][0]，去B可得收入为income[i][1]
 * 返回能使所有司机总收入最高的方案是多少钱?
 *
 * 思路:
 * 可以去A去的司机有N人.
 * 遍历income数组.
 *      当前来到i位置,请返回i..N选择后最大收入多少.要求rest不能小于0.
 *
 */
public class Code04_Drive {

    public static int maxMoney1(int[][] income) {
        if(income==null || income.length==0 ||
                income[0]==null || income[0].length<2
                || (income.length&1) !=0) return 0;
        return process1(income, 0, income.length/2);
    }

    /**
     * 当前来到i位置,请返回i..income.length最高总收入.rest为A区还有多少名额可选.
     * 如果当前来到结尾位置.返回0.
     * 如果rest大于0,尝试选择A区域,递归调用(i+1, rest-1);
     * 如果income.length-rest大于0,尝试选择B区域,递归调用(i+1, rest);
     * 比较两种方案收入,返回较大结果.
     */
    private static int process1(int[][] income, int i, int rest) {
        if(i==income.length) return 0;
        int p1 = 0;
        if(rest>0){
            p1 = income[i][0]+process1(income, i+1, rest-1);
        }
        int p2 = 0;
        if(income.length-i>rest){
            p2 = income[i][1]+process1(income, i+1, rest);
        }
        return Math.max(p1, p2);
    }

    // 严格位置依赖的动态规划版本
    /**
     *
     * income长度为N
     * M为n/2;
     * 构建二维数组dp[N+1][M+1]
     * 第N行全部为0.
     * 第N-1..0行
     *      根据递归过程可以看出依赖关系,(i,rest)位置依赖(i+1, rest)和(i+1, rest-1)
     * 返回dp[0][M]位置的值.
     */
    public static int maxMoney2(int[][] income) {
        if(income==null || income.length==0 ||
                income[0]==null || income[0].length<2
                || (income.length&1) !=0) return 0;

        int N = income.length;
        int M = N/2;
        int[] dp = new int[M+1];
        for (int i = N-1; i >=0; i--) {
            for (int rest = M; rest >=0; rest--) {
                int p1 = 0;
                if(rest>0){
                    p1 = income[i][0]+dp[rest-1];
                }
                int p2 = 0;
                if(income.length-i>rest){
                    p2 = income[i][1]+dp[rest];
                }
                dp[rest] = Math.max(p1, p2);
            }
        }
        return dp[M];
    }

    // 这题有贪心策略 :
    // 假设一共有10个司机，思路是先让所有司机去A，得到一个总收益
    // 然后看看哪5个司机改换门庭(去B)，可以获得最大的额外收益
    // 这道题有贪心策略，打了我的脸
    // 但是我课上提到的技巧请大家重视
    // 根据数据量猜解法可以省去大量的多余分析，节省时间
    // 这里感谢卢圣文同学
    public static int maxMoney3(int[][] income) {
        int N = income.length;
        int[] arr = new int[N];
        int sum = 0;
        for (int i = 0; i < N; i++) {
            arr[i] = income[i][1] - income[i][0];
            sum += income[i][0];
        }
        Arrays.sort(arr);
        int M = N >> 1;
        for (int i = N - 1; i >= M; i--) {
            sum += arr[i];
        }
        return sum;
    }

    // 返回随机len*2大小的正数矩阵
    // 值在0~value-1之间
    public static int[][] randomMatrix(int len, int value) {
        int[][] ans = new int[len << 1][2];
        for (int i = 0; i < ans.length; i++) {
            ans[i][0] = (int) (Math.random() * value);
            ans[i][1] = (int) (Math.random() * value);
        }
        return ans;
    }

    public static void main(String[] args) {
        int N = 10;
        int value = 100;
        int testTime = 500;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N) + 1;
            int[][] matrix = randomMatrix(len, value);
            int ans1 = maxMoney1(matrix);
            int ans2 = maxMoney2(matrix);
            int ans3 = maxMoney3(matrix);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
