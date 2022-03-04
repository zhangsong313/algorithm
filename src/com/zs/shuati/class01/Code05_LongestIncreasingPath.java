package com.zs.shuati.class01;

/**
 * 给定一个二维数组matrix，你可以从任何位置出发，走向上、下、左、右四个方向，返回能走出来的最长的递增链长度
 */
public class Code05_LongestIncreasingPath {
    /**
     * 对于每个i,j位置，都尝试走一遍最长递增链。
     */
    public static int longestIncreasingPath1(int[][] matrix) {
        int ans = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                ans = Math.max(ans, process1(matrix, i, j));
            }
        }
        return ans;
    }

    /**
     * 当前来到i,j位置，请返回能走出的最长递增链长度。
     * 上下左右每个方向都尝试走。
     * 比较后最长长度加1返回。
     */
    private static int process1(int[][] matrix, int i, int j) {
        int next = 0;
        if(i!=0 && matrix[i-1][j]>matrix[i][j]) next = Math.max(next, process1(matrix, i-1, j));
        if(i!=matrix.length-1 && matrix[i+1][j] > matrix[i][j]) next = Math.max(next, process1(matrix, i+1, j));
        if(j!=0 && matrix[i][j-1]>matrix[i][j]) next = Math.max(next, process1(matrix, i, j-1));
        if(j!=matrix[0].length-1 && matrix[i][j+1] > matrix[i][j]) next = Math.max(next, process1(matrix, i, j+1));
        return next+1;
    }


    /**
     * 对于每个i,j位置，都尝试走一遍最长递增链。
     * 增加记忆化搜索功能。
     */
    public static int longestIncreasingPath2(int[][] matrix) {
        int ans = 0;
        int N = matrix.length;
        int M = matrix[0].length;
        int[][] dp = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                ans = Math.max(ans, process2(matrix, i, j, dp));
            }
        }
        return ans;
    }

    /**
     * 当前来到i,j位置，请返回能走出的最长递增链长度。
     * 上下左右每个方向都尝试走。
     * 比较后最长长度加1返回。
     */
    private static int process2(int[][] matrix, int i, int j, int[][] dp) {
        if(dp[i][j]!=0) return dp[i][j];
        int next = 0;
        if(i!=0 && matrix[i-1][j]>matrix[i][j]) next = Math.max(next, process2(matrix, i-1, j, dp));
        if(i!=matrix.length-1 && matrix[i+1][j] > matrix[i][j]) next = Math.max(next, process2(matrix, i+1, j, dp));
        if(j!=0 && matrix[i][j-1]>matrix[i][j]) next = Math.max(next, process2(matrix, i, j-1, dp));
        if(j!=matrix[0].length-1 && matrix[i][j+1] > matrix[i][j]) next = Math.max(next, process2(matrix, i, j+1, dp));
        dp[i][j] = next+1;
        return next+1;
    }

}
