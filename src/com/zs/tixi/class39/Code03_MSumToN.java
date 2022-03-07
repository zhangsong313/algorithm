package com.zs.tixi.class39;

/**
 * 定义一种数：可以表示成若干（数量>1）连续正数和的数
 * 比如，5=2+3，5就是这样的数；12=3+4+5，12就是这样的数
 * 2=1+1，2不是这样的数，因为等号右边不是连续正数
 * 给定一个参数N，返回是不是可以表示成若干连续正数和的数
 */
public class Code03_MSumToN {
    /**
     * 暴力:
     * 从1..N
     *      尝试从i位置开始凑连续的数
     */
    public static boolean isMSum1(int N){
        for (int i = 1; i < N; i++) {
            int sum = i;
            for (int j = i+1; j < N; j++) {
                if(sum+j>N) break;
                if(sum+j==N) return true;
                sum+=j;
            }
        }
        return false;
    }

    /**
     * 观察结果规律可知，只有2的次方不可以。
     */
    public static boolean isMSum2(int N){
        return N != (N & (-N));
    }

    public static void main(String[] args) {
        for (int num = 1; num < 200; num++) {
            System.out.println(num + " : " + isMSum1(num));
        }
        System.out.println("test begin");
        for (int num = 1; num < 5000; num++) {
            if (isMSum1(num) != isMSum2(num)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");

    }
}
