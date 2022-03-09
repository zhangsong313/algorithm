package com.zs.tixi.class40;

import java.util.HashSet;
import java.util.TreeSet;

/**
 * 给定一个非负数组arr，和一个正数m，返回arr的所有子序列中累加和%m之后的最大值
 */
public class Code01_SubsquenceMaxModM {
    /**
     * 收集所有子序列情况的下的累加和
     * 对收集的每个累加和%m后得到最大结果。
     */
    public static int max1(int[] arr, int m){
        HashSet<Integer> set = new HashSet<>();
        process1(arr, 0, 0, set);
        int ans = 0;
        for (Integer sum : set) {
            ans = Math.max(ans, sum%m);
        }
        return ans;
    }

    /**
     * 当前来到i位置，之前的累加和为preSum，收集后续所有结果的累加和。
     * 如果当前来到结尾位置，收集preSum，返回。
     * 尝试用当前数，递归调用(i+1, preSum+arr[i])
     * 尝试不用当前数，递归调用(i+1, preSum)
     *
     * 此解法适用于arr的累加和较小的情况。
     */
    private static void process1(int[] arr, int i, int preSum, HashSet<Integer> set) {
        if(i==arr.length){
            set.add(preSum);
            return;
        }
        process1(arr, i+1, preSum,set);
        process1(arr, i+1, preSum+arr[i],set);
    }

    /**
     * process1的动态规划表解法。
     * 由于这个动态规划的结果是一个集合，而不是返回一个值。
     * 动态规划表的创建参数设置为i和sum。
     * sum为所有数的累加和。
     * dp表用来记录0..i位置达到sum是否可行。
     *
     * (i, sum)位置的值依赖(i-1, sum)和(i-1, sum-arr[i])
     * 最后遍历N-1行的所有结果找出%m后最大值。
     */
    public static int dp1(int[] arr, int m){
        int N = arr.length;
        int SUM = 0;
        for (int n : arr) {
            SUM += n;
        }
        boolean[] dp = new boolean[SUM+1];
        dp[0] = true;
        dp[arr[0]] = true;
        for (int i = 1; i < N; i++) {
            for (int sum = SUM; sum >0; sum--) {
                if(sum-arr[i]>=0) dp[sum] |= dp[sum-arr[i]];
            }
        }
        int ans = 0;
        for (int i = 0; i < dp.length; i++) {
            if(dp[i]) ans = Math.max(ans, i%m);
        }
        return ans;
    }

    /**
     * 换一种动态规划思路：
     * dp[N][m]
     * 每到i,j位置，判断0..i子序列是否能使得累加和%m = j
     * (i, j)位置依赖
     * i位置不选：(i-1, j)
     * i位置选：(i-1, j-arr[i]%m)或(i-1, m+j-arr[i]%m)
     *
     * 遍历N-1行结果，找出最大值。
     *
     * 此解法适用于m较小的情况。
     */
    public static int dp2(int[] arr, int m){
        int N = arr.length;

        boolean[] dp = new boolean[m];
        dp[arr[0]%m] = true;
        dp[0] = true;

        boolean[] pre;
        for (int i = 1; i < N; i++) {
            pre = dp;
            dp = new boolean[m];

            for (int j = m-1; j >=0; j--) {
                dp[j] |= pre[j];
                if(j-arr[i]%m>=0) dp[j] |= pre[j-arr[i]%m];
                if(j+m-arr[i]%m<m) dp[j] |= pre[j+m-arr[i]%m];
            }
        }
        for (int i = m-1; i >=0; i--) {
            if(dp[i]) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 如果arr的累加和很大，m也很大。
     * 但数组长度不大。
     * 考虑分治。
     * 将数组从中点分为左右两部分。
     * 左数组暴力求出全部累加和%m的结果。
     * 右数组暴力求出全部累加和%m的结果。
     * 对每个mod左，在右数组中找到小于m-mod左最大的mod右 (找的时候用有序表)
     * mod左和mod右相加后尝试去更新ans
     */
    public static int dp3(int[] arr, int m){
        TreeSet<Integer> leftSet = new TreeSet<>();
        TreeSet<Integer> rightSet = new TreeSet<>();
        int mid = arr.length-1>>1;
        process3(arr, 0, mid,0, m, leftSet);
        process3(arr, mid+1, arr.length-1,0, m, rightSet);
        int ans = 0;
        for (Integer leftMod : leftSet) {
            Integer rightMod = rightSet.lower(m - leftMod);
            ans = Math.max(ans, leftMod+rightMod);
        }
        return ans;
    }

    /**
     * 将index..end范围的所有子序列的累加和%m的结果收集到set里。之前的累加和为preSum
     * 如果来到end+1位置
     * set收集preSum%m，返回
     *
     * 尝试不拿当前位置的数：递归调用(index+1, end, preSum);
     * 尝试拿当前位置的数,递归调用(index+1, end, preSum+arr[index]);
     */
    private static void process3(int[] arr, int index, int end, int preSum, int m,  TreeSet<Integer> set){
        if(index == end+1){
            set.add(preSum%m);
            return;
        }
        process3(arr, index+1, end, preSum, m, set);
        process3(arr, index+1, end, preSum+arr[index], m, set);
    }

    public static int[] generateRandomArray(int len, int value) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value);
        }
        return ans;
    }

    public static void main(String[] args) {
        int len = 10;
        int value = 100;
        int m = 76;
        int testTime = 500000;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(len, value);
            int ans1 = max1(arr, m);
            int ans2 = dp1(arr, m);
            int ans3 = dp2(arr, m);
            int ans4 = dp3(arr, m);
            if (ans1 != ans2 || ans2 != ans3 || ans3 != ans4) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

    }
}
