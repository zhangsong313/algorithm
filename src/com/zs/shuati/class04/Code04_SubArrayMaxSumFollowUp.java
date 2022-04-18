package com.zs.shuati.class04;

/**
 * 4.返回一个数组中所选数字不能相邻的情况下最大子序列累加和
 */
public class Code04_SubArrayMaxSumFollowUp {

    /**
     * 思路：从左往右的动态规划。
     * 当前来到i位置：请返回0.。i范围内最大不相邻子序列的累加和
     * 尝试选择当前位置数，则i-1位置不能选:
     *      如果i-2位置结果大于0,结果为arr[i]加i-2位置结果,否则为arr[i]
     * 尝试不选择当前位置数:
     *      结果为i-1位置结果
     * 返回上面两种结果的较大值
     */
    public int subArrayMaxSumFollowUp(int[] arr){
        if(arr == null || arr.length ==0) return 0;
        int pre2 = 0;
        int pre1 = 0;
        for (int i = 0; i < arr.length; i++) {
            int cur = Math.max(pre1, pre2>0?arr[i]+pre2:arr[i]);
            pre2 = pre1;
            pre1 = cur;
        }
        return pre1;
    }
}
