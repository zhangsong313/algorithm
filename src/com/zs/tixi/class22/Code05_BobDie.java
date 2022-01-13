package com.zs.tixi.class22;

/*
 * 题目五：
 *      给定5个参数：N, M, row, col, k
 *      表示在N*M的区域上，醉汉Bob初始在(row, col)位置上。
 *      Bob一共要迈出k步，且每步都会等概率向上下左右任意方向走一个单位。
 *      任何时候Bob只要离开N*M的区域，就会直接死亡
 *      返回K步后，Bob还在N*M的区域的概率。
 */
public class Code05_BobDie {

    public static double livePosibility1(int row, int col, int k, int N, int M){
        return process(row, col, k, N, M)/Math.pow(4, k);
    }

    /**
     * 当前来到i,j位置,还剩余rest步要走,请返回存活的方法数
     *
     * 如果当前位置越界,直接返回0.
     * 如果rest==0, 直接返回1.
     * 尝试向上走,递归调用(i-1, j, rest-1)
     * 尝试向下走,递归调用(i+1, j, rest-1)
     * 尝试向左走, 递归调用(i, j-1, rest-1)
     * 尝试向右走,递归调用(i, j+1, rest-1)
     *
     * 返回累加和.
     */
    private static int process(int i, int j, int rest, int N, int M){
        if(i<0||i==N || j<0 || j==M) return 0;
        if(rest==0) return 1;
        int p1 = process(i-1, j, rest-1, N, M);
        int p2 = process(i+1, j, rest-1, N, M);
        int p3 = process(i, j-1, rest-1, N, M);
        int p4 = process(i, j+1, rest-1, N, M);

        return p1+p2+p3+p4;
    }

    /**
     * 动态规划表
     * 根据上面的递归,(i, j, rest)位置依赖上下左右rest-1位置的值.
     *
     * 创建一个三维数组dp[rest+1][N][M]
     * 第0层全部填1.
     * 从第1层填到第rest层.
     * 第i层当前位置的值等于第i-1层当前位置的上下左右之和(遇到越界返回0)
     *
     * 返回dp[rest][row][col]/4的k次方
     */
    public static double livePosibility2(int row, int col, int k, int N, int M) {
        int[][][] dp = new int[k+1][N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                dp[0][i][j] = 1;
            }
        }
        for (int l = 1; l <= k; l++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    dp[l][i][j] = getVal(l-1, i-1, j, N, M, dp)
                            +getVal(l-1, i+1, j, N, M, dp)
                            +getVal(l-1, i, j-1, N, M, dp)
                            +getVal(l-1, i, j+1, N, M, dp);
                }
            }
        }
        return dp[k][row][col] / Math.pow(4, k);
    }

    private static int getVal(int l, int i, int j, int N, int M, int[][][] dp) {
        if(i<0||i==N||j<0||j==M) return 0;
        return dp[l][i][j];
    }

    public static long pick(long[][][] dp, int N, int M, int r, int c, int rest) {
        if (r < 0 || r == N || c < 0 || c == M) {
            return 0;
        }
        return dp[r][c][rest];
    }

    public static void main(String[] args) {
        System.out.println(livePosibility1(6, 6, 10, 50, 50));
        System.out.println(livePosibility2(6, 6, 10, 50, 50));
    }
}
