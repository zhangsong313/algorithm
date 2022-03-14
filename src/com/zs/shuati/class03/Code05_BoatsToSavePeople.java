package com.zs.shuati.class03;

import java.util.Arrays;

/**
 * 给定一个正数数组arr，代表若干人的体重，再给定一个正数limit，表示所有船共同拥有的载重量，每艘船最多坐两人，且不能超过载重
 * 想让所有的人同时过河，并且用最好的分配方法让船尽量少，返回最少的船数
 * Leetcode链接 : https://leetcode.com/problems/boats-to-save-people/
 */
public class Code05_BoatsToSavePeople {
    /**
     * 排序
     * 如果最大体重的人超过limit。直接返回-1，表示无法分配。
     * 如果每个人体重都是大于limit/2，直接返回N.
     *
     * 从两边到中间。
     * 每次找当前最小体重可以消耗的最大体重。
     * 当L和R相遇，相遇位置和较大体重没有被消耗的人每人一艘船。
     * 当L和R交错，较大体重没有被消耗的人每人一条船。
     */
    public static int numRescueBoats(int[] arr, int limit) {
        if(arr==null || arr.length==0) return 0;
        Arrays.sort(arr);
        int N = arr.length;
        if(arr[N-1]>limit) return -1;

        int lessR = -1;
        for (int i = N - 1; i >= 0; i--) {
            if (arr[i] <= (limit / 2)) {
                lessR = i;
                break;
            }
        }
        if (lessR == -1) {
            return N;
        }

        int L=0;
        int R=N-1;
        int ans = 0;
        while (L<R){
            if(arr[L]+arr[R]>limit){
                ans++;
                R--;
            }else {
                ans++;
                L++;
                R--;
            }
        }
        return L==R ? ans+1 : ans;
    }
}
