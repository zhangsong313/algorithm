package com.zs.shuati.class04;

/**
 * 5.老师想给孩子们分发糖果，有N个孩子站成了一条直线，老师会根据每个孩子的表现，预先给他们评分
 * 你需要按照以下要求，帮助老师给这些孩子分发糖果：
 * 每个孩子至少分配到 1 个糖果。
 * 评分更高的孩子必须比他两侧的邻位孩子获得更多的糖果。
 * 那么这样下来，返回老师至少需要准备多少颗糖果
 * 进阶：在原来要求的基础上，增加一个要求，相邻的孩子间如果分数一样，分的糖果数必须一样，返回至少需要准备多少颗糖果
 */
public class Code05_CandyProblem {

    /**
     * 从左往右遍历数组，比左边大糖果加1，小于等于左边糖果变为1.
     * 从右往左遍历数组，比右边大糖果加1，小于等于右边糖果变1.
     * 两个数组每个对应位置求max得到结果。
     * 结果数组累加后返回总糖果数。
     */
    public int candyProblem(int[] arr){
        if(arr == null || arr.length==0) return 0;
        int N = arr.length;
        int[] arrL = new int[N];
        int[] arrR = new int[N];
        arrL[0] = 1;
        for (int i = 1; i < N; i++) {
            arrL[i] = arr[i]>arr[i-1] ? arrL[i-1]+1 : 1;
        }
        arrR[N-1] = 1;
        for (int i = N-2; i >=0 ; i--) {
            arrR[i] = arr[i]>arr[i+1] ? arrR[i+1]+1 : 1;
        }
        int ans = 0;
        for (int i = 0; i < N; i++) {
            ans += Math.max(arrL[i], arrR[i]);
        }
        return ans;
    }

    /**
     * 进阶问题答案：
     * 从左往右遍历数组，比左边大糖果加1，等于左边糖果不变，小于左边糖果变1.
     * 从右往左遍历数组，比右边大糖果加1，等于右边糖果不变，小于右边糖果变1.
     * 两个数组每个对应位置求max得到结果。
     * 结果数组累加后返回总糖果数。
     */
    public int candyProblem2(int[] arr){
        if(arr == null || arr.length==0) return 0;
        int N = arr.length;
        int[] arrL = new int[N];
        int[] arrR = new int[N];
        arrL[0] = 1;
        for (int i = 1; i < N; i++) {
            arrL[i] = arr[i]>arr[i-1] ? arrL[i-1]+1 : (arr[i]==arr[i-1] ? arrL[i-1] : 1);
        }
        arrR[N-1] = 1;
        for (int i = N-2; i >=0 ; i--) {
            arrR[i] = arr[i]>arr[i+1] ? arrR[i+1]+1 : (arr[i]==arr[i+1] ? arrR[i+1] : 1);
        }
        int ans = 0;
        for (int i = 0; i < N; i++) {
            ans += Math.max(arrL[i], arrR[i]);
        }
        return ans;
    }
}
