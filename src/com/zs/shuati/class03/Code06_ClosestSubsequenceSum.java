package com.zs.shuati.class03;

import java.util.TreeSet;

/**
 * 6. 给定整数数组nums和目标值goal，需要从nums中选出一个子序列，使子序列元素总和最接近goal
 * 也就是说如果子序列元素和为sum ，需要最小化绝对差abs(sum - goal)，返回 abs(sum - goal)可能的最小值
 * 注意数组的子序列是通过移除原始数组中的某些元素（可能全部或无）而形成的数组。
 * 本题测试链接 : https://leetcode.com/problems/closest-subsequence-sum/
 * // 本题数据量描述:
 * // 1 <= nums.length <= 40
 * // -10^7 <= nums[i] <= 10^7
 * // -10^9 <= goal <= 10^9
 */
public class Code06_ClosestSubsequenceSum {
    /**
     * 将nums从中点分左右两部分。
     * 左右部分分别求出所有子序列的和。
     * 将右部分放入有序表。
     * 遍历左部分，每个答案记为leftSum
     *      在右部分找到goal-leftSum的floor和ceiling结果.
     *      收集goal-(leftSum+rightSum)绝对值作为结果。
     *
     * 返回结果中最小的答案.
     */
    public static int minAbsDifference(int[] nums, int goal) {
        if(nums==null || nums.length==0) return Math.abs(goal);
        if(nums.length==1) return Math.min(Math.abs(goal), Math.abs(nums[0] - goal));
        int N = nums.length;
        int mid = N-1 >> 1;
        TreeSet<Integer> leftSet = new TreeSet<>();
        TreeSet<Integer> rightSet = new TreeSet<>();
        process(nums, 0, mid, 0, leftSet);
        process(nums, mid+1, N-1, 0, rightSet);
        int ans = 0;
        for (Integer leftSum : leftSet) {
            Integer floor = rightSet.floor(goal-leftSum);
            if(floor!=null) ans = Math.min(ans, Math.abs(floor+leftSum-goal));
            Integer ceiling = rightSet.ceiling(goal-leftSum);
            if(ceiling!=null) ans = Math.min(ans, Math.abs(ceiling + leftSum-goal));
        }
        return ans;
    }

    /**
     * 当前来到的位置为index.之前收集的和为preSum，请将index..end的所有可能和加上preSum放入set中。
     * 如果来到end
     * 将preSum放到set。将preSum加end位置的值放入set。
     *
     * 尝试不拿当前位置的值，递归调用(index+1, preSum)
     * 尝试拿当前位置的值，递归调用(index+1, preSum+nums[index])
     */
    private static void process(int[] nums, int index, int end, int preSum, TreeSet<Integer> set) {
        if(index==end){
            set.add(preSum);
            set.add(preSum+nums[index]);
        }
        process(nums, index+1, end, preSum, set);
        process(nums, index+1, end, preSum+nums[index], set);
    }
}
