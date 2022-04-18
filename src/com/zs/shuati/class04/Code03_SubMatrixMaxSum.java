package com.zs.shuati.class04;

/**
 * 3.返回一个二维数组中子矩阵最大累加和
 * 本题测试链接 : https://leetcode-cn.com/problems/max-submatrix-lcci/
 */
public class Code03_SubMatrixMaxSum {

    /**
     * 思路，一个矩阵的高度如果确定，将对应高度的数据在列的方向压缩相加后。则问题变为求一维数组的最大子数组累加和。
     * 对于二维数组来说，子矩阵高度的可能性为
     * 0-0行，0-1行，0-2行...0-n行
     * 1-1行，1-2行...1-n行
     * 2-2行...2-n行
     * 遍历所有可能性，对每种可能性压缩高度后求一维数组的最大子数组累加和。
     *
     * 时间复杂度：
     * 假设matirx规模为m*n
     * 则时间复杂度为O(m^2*n)
     */
    public int subMatrixMaxSum(int[][] matrix){
        if(matrix == null || matrix.length==0 || matrix[0] == null || matrix[0].length==0){
            return 0;
        }
        int max = Integer.MIN_VALUE;
        int M = matrix.length;
        int N = matrix[0].length;

        for (int i = 0; i < M; i++) {
            int[] arr = new int[N];
            for (int j = i; j < M; j++) {
                // 压缩累加当前行数据
                for (int k = 0; k < arr.length; k++) {
                    arr[k] += matrix[j][k];
                }
                // 求出当前数组的最大子数组累加和结果.
                int max2 = Integer.MIN_VALUE;
                int pre2 = 0;
                for (int k = 0; k < arr.length; k++) {
                    pre2 = pre2>0?arr[k]+pre2:pre2;
                    max2 = Math.max(max2, pre2);
                }
                max = Math.max(max, max2);

//                // 压缩累加当前行数据，同时计算最大累加和结果
//                int pre = 0;
//                for (int k = 0; k < arr.length; k++) {
//                    arr[k] += matrix[j][k];
//                    pre  = pre>0?arr[k]+pre:pre;
//                    max = Math.max(max, pre);
//                }
            }
        }
        return max;
    }
}
