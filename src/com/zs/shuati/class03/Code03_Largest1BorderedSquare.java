package com.zs.shuati.class03;

/**
 * 3. 给定一个只有0和1组成的二维数组，返回边框全是1（内部无所谓）的最大正方形面积
 * 测试链接 : https://leetcode.com/problems/largest-1-bordered-square/
 */
public class Code03_Largest1BorderedSquare {

    /**
     * 从大到小遍历所有size可能性。
     *
     * 每一个指定的size，遍历二维数组。
     * 每个位置看作是正方形的左上角点。
     * 只遍历可能作为正方形左上角的位置。
     *
     * 如果出现了边框都是1的正方形，返回。
     *
     */
    public static int largest1BorderedSquare(int[][] m) {
        int N = m.length;
        int M = m[0].length;
        int[][] right = new int[N][M];
        int[][] down = new int[N][M];
        setBorder(m, right, down);
        int size = Math.min(M, N);
        for (int i = size; i >0; i--) {
            if(hasSizeBorder(i, right, down)) return i*i;
        }
        return 0; // 全部是0，没有1.
    }

    /**
     * 查看矩阵中是否存在边长为size的正方形，边框都是1的。
     */
    private static boolean hasSizeBorder(int size, int[][] right, int[][] down) {
        int N = right.length;
        int M = right[0].length;
        for (int i = 0; i <= N-size; i++) {
            for (int j = 0; j <= M-size; j++) {
                if(right[i][j]>=size && down[i][j]>=size &&
                        right[i+size-1][j]>=size && down[i][j+size-1]>=size){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 收集每个位置向右有多少连续的1.
     * 向下有多少连续的1.
     *
     * 每个位置依赖右和下位置。
     * 从右下角开始填，先填最右列和最下行。
     *
     * 剩余位置从右下向左上填写。
     */
    private static void setBorder(int[][] m, int[][] right, int[][] down) {
        int N = m.length;
        int M = m[0].length;
        if(m[N-1][M-1]==1){
            right[N-1][M-1] = 1;
            down[N-1][M-1] = 1;
        }
        for (int i = M-2; i >=0; i--) {
            if(m[N-1][i]==1){
                right[N-1][i] = right[N-1][i+1] +1;
                down[N-1][i] = 1;
            }
        }
        for (int i = N-2; i >=0; i--) {
            if(m[i][M-1]==1){
                down[i][M-1] = down[i+1][M-1] + 1;
                right[i][M-1] = 1;
            }
        }

        for (int i = N-2; i >=0; i--) {
            for (int j = M-2; j >=0; j--) {
                if(m[i][j]==1){
                    down[i][j] = down[i+1][j] + 1;
                    right[i][j] = right[i][j+1] + 1;
                }
            }
        }
    }
}
