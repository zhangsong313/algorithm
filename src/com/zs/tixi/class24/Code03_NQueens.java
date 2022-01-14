package com.zs.tixi.class24;
/*
 * 题目三：N皇后问题
 *      在N*N的棋盘上要摆N个皇后。
 *      要求任何两个皇后不同行，不同列，也不在同一条斜线上。
 *      给定一个整数N，返回N皇后的摆法有多少种。
 *      N=1, 返回1
 *      N=2或3, 2皇后和3皇后问题无论怎么摆都不行，返回0
 *      N=8， 返回92
 */
public class Code03_NQueens {

    public static int num1(int n) {
        if (n < 1) {
            return 0;
        }
        int[] record = new int[n];
        return process1(0, record, n);
    }

    /**
     * 按行放皇后,当前来到第i行,pre为之前行放的皇后在哪一列.请返回从第i行开始到结尾一共有多少种方法.
     *
     * 如果当前来到结尾,返回1.
     * 尝试放在第i行的每一列,试着和pre不冲突:
     *      1. 如果之前pre已经有当前列,就不能放.
     *      2. pre中保存的是第row行的数放的列数为pre[row],
     *      那么pre[row]斜向影响当前行的第pre[row]+(i-row)和pre[row]-(i-row)列.
     * 上面所有尝试均失败,则返回0.
     */
    private static int process1(int i, int[] pre, int N){
        if(i==N)return 1;
        int p = 0;
        for (int col = 0; col < N; col++) {
            if(isVaild(i, col, pre)) {
                pre[i] = col;
                p+=process1(i+1, pre, N);
            }
        }

        return p;
    }

    private static boolean isVaild(int row, int col, int[] pre) {
        for (int i = 0; i < row; i++) {
            if (pre[i]==col || pre[i]+(row-i)==col || pre[i]-(row-i)==col) return false;
        }
        return true;
    }



    // 请不要超过32皇后问题
    public static int num2(int n) {
        if (n < 1 || n > 32) {
            return 0;
        }
        // 如果你是13皇后问题，limit 最右13个1，其他都是0
        int limit = n == 32 ? -1 : (1 << n) - 1;
        return process2(limit, 0, 0, 0);
    }

    // 7皇后问题
    // limit : 0....0 1 1 1 1 1 1 1
    // 之前皇后的列影响：colLim
    // 之前皇后的左下对角线影响：leftDiaLim
    // 之前皇后的右下对角线影响：rightDiaLim
    public static int process2(int limit, int colLim, int leftDiaLim, int rightDiaLim) {
        if (colLim == limit) {
            return 1;
        }
        // pos中所有是1的位置，是你可以去尝试皇后的位置
        int pos = limit & (~(colLim | leftDiaLim | rightDiaLim));
        int mostRightOne = 0;
        int res = 0;
        while (pos != 0) {
            mostRightOne = pos & (~pos + 1);
            pos = pos - mostRightOne;
            res += process2(limit, colLim | mostRightOne, (leftDiaLim | mostRightOne) << 1,
                    (rightDiaLim | mostRightOne) >>> 1);
        }
        return res;
    }

    public static void main(String[] args) {
        int n = 12;

        long start = System.currentTimeMillis();
        System.out.println(num2(n));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println(num1(n));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

    }
}
