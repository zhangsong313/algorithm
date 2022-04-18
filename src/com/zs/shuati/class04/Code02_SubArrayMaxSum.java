package com.zs.shuati.class04;

/**
 * 2.返回一个数组中子数组最大累加和
 * 本题测试链接 : https://leetcode.com/problems/maximum-subarray/
 */
public class Code02_SubArrayMaxSum {

    /**
     * 考虑以i位置结尾的子数组最大累加和
     * 如果i-1位置的结果大于0，i位置的结果为arr[i]加上i-1位置的结果
     * 时间复杂度：O（N）
     */
    public int subArrayMaxSum(int[] arr){
        if(arr == null || arr.length==0){return 0;}
        int pre = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            pre = pre>0 ? arr[i]+pre : arr[i];
            max = Math.max(max, pre);
        }
        return max;
    }
}
