package com.zs.tixi.class22;

import java.util.HashMap;
import java.util.Map;

/*
 * 题目四：
 *      arr是货币数组，其中的值都是正数，再给定一个正数aim。
 *      每个值都认为是一张货币
 *      认为值相同的货币没有什么不同。
 *      返回组成aim的方法数。
 *      例如：arr={1, 2, 1, 1, 2, 1, 2}, aim=4
 *      方法：1+1+1+1, 1+1+2, 2+2
 *      一共就三种方法，返回3.
 */
public class Code04_CoinsWaySameValueSamePapper {

    /**
     * 将arr数组转化为表示不同面值的coin数组和对应面值有多少张的zhang数组.
     */
    public static int coinsWay(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        return transAndProcess(aim, arr);
    }

    /**
     * 转换
     */
    private static int transAndProcess(int aim, int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            Integer count = map.get(arr[i]);
            if(count == null){
                map.put(arr[i], 1);
            } else {
                map.put(arr[i], map.get(arr[i])+1);
            }
        }
        int N = map.size();
        int[] coin = new int[N];
        int[] zhang = new int[N];
        int i=0;
        for (Integer key : map.keySet()) {
            coin[i] = key;
            zhang[i++] = map.get(key);
        }
        return process(0, aim, coin, zhang);
    }

    /**
     * 当前来到coin的i位置,zhang为对应张数的数组,还剩rest钱需要凑,请返回方法数
     *
     * 如果rest为0, 返回1
     * 如果当前位置来到结束位置,返回0.
     * 尝试从i位置拿z张钱,要求z<=zhang[i]且z*coin[i]<=rest
     *      递归调用(i+1, rest-z*coin[i])
     * 返回上面尝试结果的累加和.
     */
    private static int process(int i, int rest, int[] coin, int[] zhang) {
        if(rest==0) return 1;
        if(i==coin.length) return 0;
        int p = 0;
        for (int z = 0; z <= zhang[i]&&z*coin[i]<=rest; z++) {
            p += process(i+1, rest-z*coin[i], coin, zhang);
        }
        return p;
    }

    /**
     * 动态规划表
     * 根据上面的递归逻辑可以看出(i, rest)位置总是依赖(i+1, rest-z*coin[i])位置的值
     *
     * arr数组长度为N
     * 创建二维数组dp[N+1][aim+1]
     *
     * 先填第N行:除了第一列为1,其余位置都为0.
     * 从第N-1行向上填:(i, rest)位置值等于(i+1, rest-z*coin[i])的累加和
     *
     * 使用斜率优化:
     * 如果是无限张的情况,(i, rest) = (i, rest-coin[i])+(i+1, rest)
     * 当张数有限时,会出现即使rest>=z*coin[i],但没有钱可拿的情况.
     * 此时,(i, rest) = (i, rest-coin[i])+(i+1, rest)-(i+1, rest-zhang[i]*coin[i])
     * 返回dp[0][aim]
     */
    public static int dp1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            Integer count = map.get(arr[i]);
            if(count == null){
                map.put(arr[i], 1);
            } else {
                map.put(arr[i], map.get(arr[i])+1);
            }
        }
        int N = map.size();
        int[] coin = new int[N];
        int[] zhang = new int[N];
        int index=0;
        for (Integer key : map.keySet()) {
            coin[index] = key;
            zhang[index++] = map.get(key);
        }

        int[][] dp = new int[N+1][aim+1];
        dp[N][0] = 1;
        for (int i = N-1; i >=0; i--) {
            for (int rest = 0; rest <= aim; rest++) {
                if(rest>=(zhang[i]+1)*coin[i]){ // rest-coin[i]后仍然可以把当前位置的钱全部拿完,那么相邻位置会相差一个(i+1, rest-zhang[i]*coin[i])的值
                    dp[i][rest] = dp[i][rest-coin[i]] + dp[i+1][rest] - dp[i+1][rest-(zhang[i]+1)*coin[i]];
                } else if(rest>=coin[i]) {
                    dp[i][rest] = dp[i][rest-coin[i]] + dp[i+1][rest];
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
        int maxLen = 10;
        int maxValue = 20;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinsWay(arr, aim);
            int ans2 = dp1(arr, aim);
//            int ans3 = dp2(arr, aim);
            if (
//                    ans1 != ans3
//                    ||
                    ans1 != ans2
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
        System.out.println("测试结束");
    }
}
