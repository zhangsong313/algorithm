package com.zs.tixi.class25;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/*
 * 题目四:
 *      arr是货币数组,其中的值都是正数.再给定一个正数aim.
 *      每个值都认为是一张货币.
 *      返回组成aim的最少货币数.
 *      注意:
 *      因为是求最少货币数,所以每一张货币认为是相同还是不同就不重要了.
 */
public class Code04_MinCoinsOnePaper {
    public static int minCoins(int[] arr, int aim) {
        int ans = process(arr, 0, aim);
        return ans!=-1?ans:Integer.MAX_VALUE;
    }

    /**
     * 当前来到arr的i位置,还剩下rest钱需要凑.请返回需要的最少张数.
     *
     * 如果rest为0,返回0
     * 如果当前来到结尾位置,返回-1表示无法完成.
     * 如果当前位置货币大于rest,尝试拿当前位置的钱.递归调用(i+1, rest-arr[i]),返回结果加1.
     * 尝试不拿当前位置的钱,递归调用(i+1, rest)
     *
     * 以上两种尝试结果取较小值返回.
     */
    private static int process(int[] arr, int i, int rest) {
        if(rest==0) return 0;
        if(i==arr.length) return -1;

        int p1 = process(arr, i+1, rest);
        if (arr[i]<=rest){
            int p2 = process(arr, i+1, rest-arr[i]);
            if(p2!=-1){
                if(p1!=-1) {
                    p1 = Math.min(p1, p2+1);
                } else {
                    p1 = p2+1;
                }
            }
        }
        return p1;
    }

    /**
     * 动态规划表.
     * 从上面的递归过程可以看出,i位置的值依赖i+1位置的值.
     *
     * arr的长度为N
     * 创建一个二维数组dp[N+1][aim+1]
     * 第N行除了第一列外都填-1;
     * 从第N-1行填到第0行.
     *      (i, rest)的值依赖(i+1, rest)和(i+1, rest-arr[i])的值.
     *
     * 返回dp[0][aim]
     */
    public static int dp1(int[] arr, int aim) {
        int N = arr.length;
        int[][] dp = new int[N+1][aim+1];
        for (int i = 1; i <= aim; i++) {
            dp[N][i] = -1;
        }
        for (int i = N-1; i >=0 ; i--) {
            for (int rest = 1; rest <=aim; rest++) {
                int p1 =dp[i+1][rest];
                if (arr[i]<=rest){
                    int p2 = dp[i+1][rest-arr[i]];
                    if(p2!=-1){
                        if(p1!=-1) {
                            p1 = Math.min(p1, p2+1);
                        } else {
                            p1 = p2+1;
                        }
                    }
                }
                dp[i][rest] = p1;
            }
        }
        int ans = dp[0][aim];
        return ans!=-1?ans:Integer.MAX_VALUE;
    }

    /**
     * 先将arr整理成coin数组和zhang数组.coin[i]位置的货币面值有zhang[i]张.
     *
     * 当前来到i位置,还剩余rest,请返回需要的张数.
     * 如果rest=0返回0.
     * 如果当前来到数组末尾,返回-1.
     * 尝试从当前位置拿z张,要求z*coin[i]不能超过rest,且z不能超过zhang[i]
     *      递归调用:(i+1, rest-z*coin[i])+z;
     * 返回以上各种尝试的最小值
     *
     * 以上递归过程可以看出:i位置值依赖i+1位置值.
     * arr的长度为N
     * 创建二维数组dp[N+1][aim+1]
     * 第N行除了第0列外都为-1.
     * 从第N-1行填到第0行
     *      (i, rest)值依赖(i+1, rest-z*coin[i])+z中的最小值
     *
     * 斜率优化.
     * 当前可以最多拿zMax张,考虑(i, rest-coin[i])位置:
     *      依赖(i+1, rest-z*coin[i])+(z-1)中的最小值其中z范围为[1..zMax+1]
     *      所以从(i, rest-coin[i])到(i, rest)经历了一次窗口滑动
     *      (i+1, rest-(zMax+1)*coin[i])从左侧滑出.
     *      (i+1, rest)从右侧滑入.
     *      且每次滑动后的比较需要先补(进入和比较的两个rest差值除以coin)张后比较
     */
    public static int dp3(int[] arr, int aim) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if(map.get(arr[i])==null){
                map.put(arr[i], 1);
            } else {
                map.put(arr[i], map.get(arr[i])+1);
            }
        }
        int[] coin = new int[map.size()];
        int[] zhang = new int[map.size()];
        int index = 0;
        for (Integer i : map.keySet()) {
            coin[index] = i;
            zhang[index++] = map.get(i);
        }
        int N = coin.length;
        int[][] dp = new int[N+1][aim+1];
        for (int i = 1; i <= aim; i++) {
            dp[N][i] = -1;
        }
        for (int i = N-1; i >=0 ; i--) {
            for (int j = 0; j < Math.min(aim+1, coin[i]); j++) { // 滑动窗口遍历次数: i位置的货币面值.
                Deque<Integer> dqMin = new LinkedList<>();
                int zMax = zhang[i]; // 滑动窗口大小:i位置的货币张数.
                for (int R = j, L=j; R <= aim; R+=coin[i]) { // 注意窗口是在i+1行滑动取值.
                    while (dqMin.size()>0 && (
                            dp[i+1][dqMin.peekLast()] == -1 || (dp[i+1][R] != -1 && dp[i+1][dqMin.peekLast()]+(R-dqMin.peekLast())/coin[i]>=dp[i+1][R])
                            )){
                        dqMin.pollLast();
                    }

                    dqMin.addLast(R);
//                    if(dqMin.peekFirst()==R-(zMax+1)*coin[i]){
//                        dqMin.pollFirst();
//                    }
                    if((R-L)/coin[i]>zMax){ // 窗口左侧该动了.
                        L+=coin[i];
                        if(dqMin.peekFirst()<L) dqMin.pollFirst();
                    }
                    // 填当前位置的值.双端队列中的最小值补齐张数后的结果.
                    if(dp[i+1][dqMin.peekFirst()]!=-1){
                        dp[i][R] = dp[i+1][dqMin.peekFirst()]+(R-dqMin.peekFirst())/coin[i];
                    } else {
                        dp[i][R] = -1;
                    }

                }
            }
        }
        int ans = dp[0][aim];
        return ans!=-1?ans:Integer.MAX_VALUE;
    }

    // dp2时间复杂度为：O(arr长度) + O(货币种数 * aim * 每种货币的平均张数)
    /**
     * 搬来老师的代码辅助测试.
     */
    public static int dp2(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        // 得到info时间复杂度O(arr长度)
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] zhangs = info.zhangs;
        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        // 这三层for循环，时间复杂度为O(货币种数 * aim * 每种货币的平均张数)
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                for (int zhang = 1; zhang * coins[index] <= aim && zhang <= zhangs[index]; zhang++) {
                    if (rest - zhang * coins[index] >= 0
                            && dp[index + 1][rest - zhang * coins[index]] != Integer.MAX_VALUE) {
                        dp[index][rest] = Math.min(dp[index][rest], zhang + dp[index + 1][rest - zhang * coins[index]]);
                    }
                }
            }
        }
        return dp[0][aim];
    }

    public static class Info {
        public int[] coins;
        public int[] zhangs;

        public Info(int[] c, int[] z) {
            coins = c;
            zhangs = z;
        }
    }

    public static Info getInfo(int[] arr) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int value : arr) {
            if (!counts.containsKey(value)) {
                counts.put(value, 1);
            } else {
                counts.put(value, counts.get(value) + 1);
            }
        }
        int N = counts.size();
        int[] coins = new int[N];
        int[] zhangs = new int[N];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            coins[index] = entry.getKey();
            zhangs[index++] = entry.getValue();
        }
        return new Info(coins, zhangs);
    }


    // 为了测试
    public static int[] randomArray(int N, int maxValue) {
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
        int maxLen = 50;
        int maxValue = 30;
        int testTime = 300000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = randomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
//            int[] arr = new int[]{4, 2, 2};
//            int aim = 4;
            int ans1 = minCoins(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp2(arr, aim);
            int ans4 = dp3(arr, aim);
            if (ans1 != ans2 || ans3 != ans4 || ans1 != ans3) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println(ans4);
                break;
            }
        }
        System.out.println("功能测试结束");

        System.out.println("==========");

        int aim = 0;
        int[] arr = null;
        long start;
        long end;
        int ans2;
        int ans3;

        System.out.println("性能测试开始");
        maxLen = 30000;
        maxValue = 20;
        aim = 60000;
        arr = randomArray(maxLen, maxValue);

        start = System.currentTimeMillis();
        ans2 = dp2(arr, aim);
        end = System.currentTimeMillis();
        System.out.println("dp2答案 : " + ans2 + ", dp2运行时间 : " + (end - start) + " ms");

        start = System.currentTimeMillis();
        ans3 = dp3(arr, aim);
        end = System.currentTimeMillis();
        System.out.println("dp3答案 : " + ans3 + ", dp3运行时间 : " + (end - start) + " ms");
        System.out.println("性能测试结束");

        System.out.println("===========");

        System.out.println("货币大量重复出现情况下，");
        System.out.println("大数据量测试dp3开始");
        maxLen = 20000000;
        aim = 10000;
        maxValue = 10000;
        arr = randomArray(maxLen, maxValue);
        start = System.currentTimeMillis();
        ans3 = dp3(arr, aim);
        end = System.currentTimeMillis();
        System.out.println("dp3运行时间 : " + (end - start) + " ms");
        System.out.println("大数据量测试dp3结束");

        System.out.println("===========");

        System.out.println("当货币很少出现重复，dp2比dp3有常数时间优势");
        System.out.println("当货币大量出现重复，dp3时间复杂度明显优于dp2");
        System.out.println("dp3的优化用到了窗口内最小值的更新结构");
    }
}
