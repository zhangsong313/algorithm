package com.zs.shuati.class01;

import java.util.HashMap;

/**
 * 给定一个数组arr，你可以在每个数字之前决定+或者-但是必须所有数字都参与，再给定一个数target
 * 请问最后算出target的方法数
 */
public class Code07_TargetSum {
    public static int findTargetSumWays1(int[] arr, int target) {
        return process1(arr, 0, target);
    }

    /**
     * 当前来到arr的index位置,后续需要凑出target,请返回方法数.
     * 如果当前来到结束位置,target不为0,返回0,否则返回1.
     *
     * 尝试当前位置设置正号,递归调用index+1, rest-arr[index]
     * 尝试当前位置设置负号,递归调用index+1, rest+arr[index]
     * 返回两种尝试的方法数之和.
     */
    private static int process1(int[] arr, int index, int rest) {
        if(index==arr.length) return rest==0?1:0;
        int ans = 0;
        ans += process1(arr, index+1, rest-arr[index]);
        ans += process1(arr, index+1, rest+arr[index]);
        return ans;
    }

    /**
     * 记忆化搜索
     */
    public static int findTargetSumWays2(int[] arr, int target) {
        HashMap<Integer, HashMap<Integer, Integer>> dp = new HashMap<>();
        return process2(arr, 0, target, dp);
    }
    private static int process2(int[] arr, int index, int rest, HashMap<Integer, HashMap<Integer, Integer>> dp) {
        if(dp.containsKey(index) && dp.get(index).containsKey(rest)) return dp.get(index).get(rest);
        if(index==arr.length) return rest==0?1:0;
        int ans = 0;
        ans += process2(arr, index+1, rest-arr[index], dp);
        ans += process2(arr, index+1, rest+arr[index], dp);
        if(!dp.containsKey(index)){
            dp.put(index, new HashMap<>());
        }
        dp.get(index).put(rest, ans);
        return ans;
    }

    /**
     * 动态规划表,
     * target绝对值最大不会超出arr数组绝对值的累加和.
     * arr长度为N,绝对值累加和为sum.
     * 创建二维表dp,长度为N+1,宽度为sum*2+1
     * 观察process1递归过程,index位置的值,总是依赖index+1位置的值.
     * 从最后一行开始填,一直填到第0行.
     * 第N行,rest为0地方填1.(注意rest需要下标转换,加上sum)
     * 从N-1到0行.
     *      index,rest位置依赖index+1,rest-arr[index]和index+1, rest+arr[index].
     *      注意rest下标转换和判断rest下标越界.
     * 返回0,target+sum位置的值.
     */
    public static int findTargetSumWays3(int[] arr, int target) {
        int absSum = 0;
        for (int i = 0; i < arr.length; i++) {
            absSum += Math.abs(arr[i]);
        }
        if(Math.abs(target)>absSum)return 0;
        int N = arr.length;
        int M = absSum << 1 | 1;
        int[][] dp = new int[N+1][M];
        dp[N][absSum] = 1;
        for (int i = N-1; i >=0 ; i--) {
            for (int rest = 0; rest < M; rest++) {
                if(rest-arr[i]>=0 && rest-arr[i]<M) dp[i][rest] += dp[i+1][rest-arr[i]];
                if(rest+arr[i]>=0 && rest+arr[i]<M) dp[i][rest] += dp[i+1][rest+arr[i]];
            }
        }
        return dp[0][target+absSum];
    }

    /**
     * 优化:
     * 1. 如果目标绝对值超过了数据绝对值和.,直接返回false(必须优化,否则dp数组会越界.)
     * 2. 如果target是奇数,累加和也要是奇数,反之亦然.
     * 3. 将累加和sum划分为正负两个组A和B,有A+B=sum,A-B=target.所以2A = sum+target,
     *      因此我们的目标转换为在arr中可以选择是否要某位置上的数,最后凑齐(sum+target)>>1,变成一个背包问题.
     *
     * 4.空间压缩.从右往左填
     */
    public static int findTargetSumWays(int[] arr, int target) {
        int absSum = 0;
        for (int i = 0; i < arr.length; i++) {
            absSum += Math.abs(arr[i]);
        }

        if(Math.abs(target)>absSum)return 0;

        if((target & 1) != (absSum & 1)) return 0;

        int bag = absSum+Math.abs(target)>>1;
        int M = bag+1;
        int[] dp = new int[M];
        dp[0] = 1;
        for (int num : arr) {
            for (int rest = M-1; rest >=num; rest--) {
                dp[rest] += dp[rest-num];
            }
        }
        return dp[bag];
    }

    /**
     *      背包问题思路转换.
     *
     *      当前来到i位置,0..i要凑出rest,有几种方法.
     *      如果i位置为0,如果arr[0]==rest,返回1,否则返回0.
     *      当前位置拿,递归(i-1, rest-arr[i])
     *      不拿,递归(i-1, rest)
     *
     *      第0行,第arr[0]列填1,其余位置为0
     *      第1..N-1行
     *          (i, rest)依赖(i-1, rest-arr[i])和(i-1, rest)的值.
     *      最后返回dp[N][bag]
     */
    public static int findTargetSumWays4(int[] arr, int target) {
        int absSum = 0;
        for (int i = 0; i < arr.length; i++) {
            absSum += Math.abs(arr[i]);
        }

        if(Math.abs(target)>absSum)return 0;

        if((target & 1) != (absSum & 1)) return 0;

        int bag = absSum+Math.abs(target)>>1;
        int N = arr.length;
        int M = bag+1;
        int[] dp = new int[M];
        dp[0] += 1;
        dp[arr[0]] += 1;
        for (int i = 1; i <N ; i++) {
//            MyCompValue.printArr(dp);
            for (int rest = M-1; rest >=0; rest--) {
                if(rest-arr[i]>=0) dp[rest] += dp[rest-arr[i]];
            }
        }
//        MyCompValue.printArr(dp);
        return dp[bag];
    }

    public static void main(String[] args) {
        findTargetSumWays4(new int[]{0,0,0,0,0,0,0,0,1}, 1);
    }
}
