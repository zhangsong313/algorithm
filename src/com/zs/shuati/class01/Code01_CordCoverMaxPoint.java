package com.zs.shuati.class01;

import java.util.Arrays;

/**
 * 给定一个有序数组arr，代表坐落在X轴上的点，给定一个正数K，代表绳子的长度，返回绳子最多压中几个点？
 * 即使绳子边缘处盖住点也算盖住
 *
 * 思路：滑动窗口，每次窗口不超过绳子长度的情况下，收集窗口内有多少数字，每次尝试更新最大值。最后返回最大值。
 */
public class Code01_CordCoverMaxPoint {

    /**
     *
     * 以i位置为L，arr结尾位置为R，绳子左侧放在L位置的数，通过绳子长度计算出右侧位置的数target。
     * 在[L..R]范围内，通过二分查找返回小于等于target最右边的位置R2。
     * R2-L+1为绳子左侧在L位置时，最多覆盖点数。
     * 返回最大的结果。
     * @param arr
     * @param L
     * @return
     */
    public static int maxPoint1(int[] arr, int L) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            int less = greaterThanIndex(arr, i, arr[i]+L);
            ans = Math.max(ans, less-i+1);
        }
        return ans;
    }

    /**
     * 请在有序数组arr的[L, arr.length-1]范围内，通过二分查找返回小于等于target最右的位置。
     */
    private static int greaterThanIndex(int[] arr, int L, int target) {
        int R = arr.length-1;
        int ans = L;
        while (L<=R){
            int mid = L+(R-L>>1);
            if(arr[mid]>target){
                R = mid-1;
            }else {
                ans = mid;
                L = mid+1;
            }
        }
        return ans;
    }

    /**
     * 滑动窗口
     */
    public static int maxPoint2(int[] arr, int L) {
        int left = 0;
        int right = 0;
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            left = i;
            while (right<arr.length && arr[right]-arr[left]<=L){
                right++;
            }
            ans = Math.max(ans, right-left);
        }
        return ans;
    }

    // for test
    public static int test(int[] arr, int L) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            int pre = i - 1;
            while (pre >= 0 && arr[i] - arr[pre] <= L) {
                pre--;
            }
            max = Math.max(max, i - pre);
        }
        return max;
    }

    // for test
    public static int[] generateArray(int len, int max) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        Arrays.sort(ans);
        return ans;
    }

    public static void main(String[] args) {
        int len = 100;
        int max = 1000;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int L = (int) (Math.random() * max);
            int[] arr = generateArray(len, max);
            int ans1 = maxPoint1(arr, L);
            int ans2 = maxPoint2(arr, L);
            int ans3 = test(arr, L);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("oops!");
                break;
            }
        }

    }
}
