package com.zs.tixi.class22;

/*
 * 题目一：
 *      给定一个二维数组matrix，一个人必须从左上角出发，最后到达右下角。
 *      沿途只可以向下或者向右走，沿途的数字都累加就是距离累加和
 *      返回最小距离累加和。
 */
public class Code01_MinPathSum {
    /**
     *
     * @param m
     * @return
     */
    public static int minPathSum1(int[][] m) {
        if(m==null || m.length==0 || m[0] ==null || m[0].length==0) return 0;
        return process1(0, 0, m);
    }

    /**
     * 当前来到matrix数组的(i, j)位置，请返回当前位置到右下角的最小距离。
     *
     * 如果当前位置越界，返回整型最大值。
     * 如果当前位置是右下角，返回当前位置的值。
     *
     * 尝试向右走：递归调用(i, j+1)。
     * 尝试向下走：递归调用(i+1, j)。
     *
     * 上面两种尝试结果取较小值，加上(i, j)位置的值后返回。
     */
    public static int process1(int i, int j, int[][] m){
       if(i==m.length || j== m[0].length) return Integer.MAX_VALUE;
       if(i==m.length-1 && j==m[0].length-1) return m[i][j];

       int p1 = process1(i, j+1, m);
       int p2 = process1(i+1, j, m);
       int ans = Math.min(p1, p2)+m[i][j];
        return ans;
    }

    /**
     * 动态规划表。
     * 根据process1递归过程可以看出，(i, j)位置的值依赖(i, j+1)(i+1, j)的值。
     *
     * m数组有有M行，N列。
     * 创建一个二维数组dp[M][N]
     * (M-1, N-1)位置填m[M-1][N-1]
     * 第M-1行从右往左填：m[i][j]位置加上右侧值。
     * 第N-1列从下往上填：m[i][j]位置加上下方值。
     * (i, j)位置填m[i][j+1]和m[i+1][j]的较小值，再加上m[i][j]的值.
     *
     * 最后返回dp[0][0]
     */
    public static int dp1(int[][] m){
        if(m==null || m.length==0 || m[0] ==null || m[0].length==0) return 0;

        int M = m.length;
        int N = m[0].length;
        int[][] dp = new int[M][N];
        dp[M-1][N-1] = m[M-1][N-1];
        for (int i = N-2; i >=0 ; i--) {
            dp[M-1][i] = dp[M-1][i+1]+m[M-1][i];
        }
        for (int i = M-2; i >=0; i--) {
            dp[i][N-1] = dp[i+1][N-1]+m[i][N-1];
        }
        for (int i = M-2; i >=0; i--) {
            for (int j =N-2; j >=0 ; j--) {
                dp[i][j] = Math.min(dp[i][j+1], dp[i+1][j])+m[i][j];
            }
        }
        return dp[0][0];
    }

    public static int minPathSum2(int[][] m) {
        if(m==null || m.length==0 || m[0] ==null || m[0].length==0) return 0;
        int M = m.length;
        int N = m[0].length;
        return process2( M-1, N-1, m);
    }

    /**
     * 当前来到(i, j)位置,请返回(0, 0)位置到当前位置的最小距离.
     *
     * 如果当前位置是(0, 0) 直接返回m[0][0]
     * 如果当前位置越界,返回整型最大值
     * 尝试从上方来到当前位置:递归调用:(i-1, j, m)
     * 尝试从左方来到当前位置:递归调用:(i, j-1, m)
     *
     * 返回上面两种尝试结果的较小值加上m[i][j]
     */
    private static int process2(int i, int j, int[][] m) {
        if (i==0 && j==0) return m[0][0];
        if (i<0 || j<0) return Integer.MAX_VALUE;
        int p1 = process2(i-1, j, m);
        int p2 = process2(i, j-1, m);
        return Math.min(p1, p2)+m[i][j];
    }

    // for test
    public static int[][] generateRandomMatrix(int rowSize, int colSize) {
        if (rowSize < 0 || colSize < 0) {
            return null;
        }
        int[][] result = new int[rowSize][colSize];
        for (int i = 0; i != result.length; i++) {
            for (int j = 0; j != result[0].length; j++) {
                result[i][j] = (int) (Math.random() * 100);
            }
        }
        return result;
    }

    // for test
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int rowSize = 10;
        int colSize = 10;
        int[][] m = generateRandomMatrix(rowSize, colSize);
        System.out.println(minPathSum1(m));
        System.out.println(dp1(m));
        System.out.println(minPathSum2(m));

    }
}
