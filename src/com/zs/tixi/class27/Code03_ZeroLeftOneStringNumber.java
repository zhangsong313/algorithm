package com.zs.tixi.class27;

/**
 * 题目五:
 *      给定一个数N,想象只由0和1两种字符,组成的所有长度为N的字符串.
 *      如果某个字符串,任何0字符的左边都有1紧挨着,认为这个字符串达标.
 *      返回有多少达标的字符串.
 */
public class Code03_ZeroLeftOneStringNumber {

    /**
     * 当前还有n个字符要放,前面的位置是1,请返回有多少达标的字符串.
     * 第1个字符尝试放0,第2个字符只能放1.递归调用n-2
     * 第一个位置尝试放1,递归调用n-1
     * 所以fn = fn-1+fn-2
     *
     * f1 = 2
     * f2 = 3
     *
     * 问题转化为求fn-1的问题
     */
    public static int getNum1(int n) {
        if(n==0) return 0;
        if(n==2|| n==3) return n-1;
        int pre1 = 2;
        int pre2 = 3;
        int ans = 0;
        for (int i = 3; i <= n-1; i++) {
            ans = pre1+pre2;
            pre1 = pre2;
            pre2 = ans;
        }
        return ans;
    }

    /**
     * 将递推式转换为矩阵求解.
     */
    public static int getNum2(int n) {
        if(n==0) return 0;
        if(n==2|| n==3) return n-1;
        int[][] rect = Code02_FibonacciProblem.getConstantRect(new int[]{1, 1});
        int[][] rectN = Code02_FibonacciProblem.rectPow(rect, n - 3);
        return Code02_FibonacciProblem.rectMulti(new int[][]{{3,2}}, rectN)[0][0];
    }

    public static void main(String[] args) {
        int n = 19;
        System.out.println(getNum1(n));
        System.out.println(getNum2(n));
    }

}
