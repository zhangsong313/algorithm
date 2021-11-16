package com.zs.tixi.class6;

/**
 * 给定一个数组arr,两个整数lower和upper,返回arr中有多少个子数组累加在[lower,upper]范围上.
 * // 这道题直接在leetcode测评：
 * // https://leetcode.com/problems/count-of-range-sum/
 * 总结:base case解决了0位置到当前位置i子数组的判断,merge过程解决了1..i位置到当前位置i子数组的判断.
 */
public class Code01_CountOfRangSum {
    public static int countRangeSum(int[] nums, int lower, int upper){
        // 如果数组为空,返回0.
        if (nums == null || nums.length==0) return 0;
        // 将arr转化为前缀和数组.
        long[] sum  = new long[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i-1]+nums[i];
        }
        return process(sum, 0 , nums.length-1, lower, upper);
    }

    /**
     * 返回前缀和数组sum中,有多少子数组使得arr累和在[lower,upper]范围内.
     * 从中点将数组分为左右组,结果为左组结果+右组结果+跨左右组情况的结果.
     * base case 为数组范围为1时,无法拆分左右组,此时判断结果后直接返回.
     * @param sum
     * @param L
     * @param R
     * @param lower
     * @param upper
     * @return
     */
    private static int process(long[] sum, int L, int R, int lower, int upper) {
        // basecase:L==R, 返回
        if(L==R){
            return sum[L]>= lower && sum[L]<= upper ? 1 : 0;
        }
        int M = L +((R-L)>>1);
        return process(sum, L, M, lower, upper)+
                process(sum, M+1, R, lower, upper)+
                merge(sum, L, M, R, lower, upper);
    }

    /**
     * 返回合并过程中以左组某位置开始,到右组某位置结束的子数组,符合要求的个数.
     * 对于右组的每个位置为终点的情况,验证左组对应位置是否符合子数组起点要求.
     * @param sum
     * @param L
     * @param M
     * @param R
     * @param lower
     * @param upper
     * @return
     */
    private static int merge(long[] sum, int L, int M, int R, int lower, int upper) {
        int ans = 0;
        // 使用两个指针记录符合要求的左组范围.(左闭右开)
        int windowLeft = L;
        int windowRight = L;
        // 对于每个右组的位置,验证左组位置是否符合要求.
        for (int i = M+1; i <=R ; i++) {
            long max = sum[i] - lower;
            long min = sum[i] - upper;
            // 右指针移动到超过最大值停止.
            while (windowRight <= M && sum[windowRight] <= max){
                windowRight++;
            }
            // 左指针移动到不小于最小值时停止
            while (windowLeft <= M && sum[windowLeft] < min){
                windowLeft++;
            }
            ans += windowRight-windowLeft;
        }

        // 合并左右组为有序
        long[] copy = new long[R-L+1];
        int pCopy = 0;
        int pLeft = L;
        int pRight = M+1;
        while (pLeft <= M && pRight<=R){
            copy[pCopy++] = sum[pLeft]>sum[pRight]?sum[pRight++]:sum[pLeft++];
        }
        while (pLeft<=M){
            copy[pCopy++] = sum[pLeft++];
        }
        while (pRight<=R){
            copy[pCopy++] = sum[pRight++];
        }
        for (int i = 0; i < copy.length; i++) {
            sum[L+i] = copy[i];
        }
        return ans;
    }
}
