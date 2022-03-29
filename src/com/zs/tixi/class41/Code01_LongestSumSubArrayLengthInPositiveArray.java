package com.zs.tixi.class41;

/**
 * 给定一个正整数组成的无序数组arr，给定一个正整数值K，找到arr的所有子数组里，哪个子数组的累加和等于K
 * 并且是长度最大的，返回其长度
 */
public class Code01_LongestSumSubArrayLengthInPositiveArray {

    /**
     * 由于数组arr中都是正整数。
     * 子数组变长，累加和变大，子数组变短，累加和变小。
     * 区间长度与累加和有单调性。
     * 滑动窗口。
     */
    public static int getMaxLength(int[] arr, int K) {
        if(arr==null || arr.length==0 || K<=0) return 0;

        int ans = 0;
        int sum = 0;
        int L = 0;
        int R = 0;
        while (R<arr.length){
            sum += arr[R];

            if(sum==K){
                ans = Math.max(ans, R-L+1);
                R++;
                continue;
            }
            if(sum<K){
                R++;
            }else {

            }
        }

        return 0;
    }
}
