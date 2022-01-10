package com.zs.tixi.class21;

/*
 * 题目二：
 *      请同学们自行搜索或者想象一个象棋的棋盘。
 *      然后把整个棋盘放入第一象限。棋盘的最左下角是(0,0)位置。
 *      那么整个棋盘就是横坐标上9条线，纵坐标上10条线的区域。
 *      给你三个参数：x,y,k
 *      返回"马"从(0,0)位置出发,必须走k步
 *      最后落在(x,y)位置的方法数有多少?
 */
public class Code02_HorseJump {

    public static int jump(int a, int b, int k){
        return process(a, b, 0, 0, k);
    }

    /**
     * 当前来到的位置是(i, j)，还剩下rest步要走，
     * 请返回从现在开始走rest步最后落在x，y位置的方法数有多少。
     *
     * 如果rest==0 如果当前来到(x, y)位置，返回1，否则返回0.
     * 如果i<0或i>9 返回0.
     * 如果j<0或j>10 返回0.
     * 尝试向"马"可以跳的八个方向跳，递归调用：rest=rest-1,(i, j)为以下八种参数
     * (i-1, j+2), (i-2, j+1), (i-2, j-1), (i-1, j-2)
     * (i+1, j+2), (i+2, j+1), (i+2, j-1), (i+1, j-2)
     *
     * 返回以上八种尝试结果的和。
     * @return
     */
    public static int process(int x, int y, int i, int j, int rest){
        if (rest==0) return i==x && j==y ? 1 : 0;
        if (i<0 || i>8 || j<0 || j>9) return 0;
        return process(x, y, i-1, j+2, rest-1)+
                process(x, y, i-2, j+1, rest-1)+
                process(x, y, i-2, j-1, rest-1)+
                process(x, y, i-1, j-2, rest-1)+
                process(x, y, i+1, j+2, rest-1)+
                process(x, y, i+2, j+1, rest-1)+
                process(x, y, i+2, j-1, rest-1)+
                process(x, y, i+1, j-2, rest-1);
    }

    /**
     * 动态规划表
     * 分析以上递归过程，rest情况下,i, j 位置依赖rest-1情况下的周围八个位置值。
     *
     * 创建一张三维表dp[k+1][9+1][10+1],第一维表示rest，可以看作z坐标。第二维为i，表示横坐标，第三维为j，表示纵坐标。
     *
     * 第0层只有(x, y)位置是1，其余位置都是0.
     * 第i层依赖第i-1层的值。
     * 一直填到第k层
     *
     * 最后返回dp[k][0][0]
     */
    public static int jump2(int x, int y, int k){
        int[][][] dp = new int[k+1][9][10];
        dp[0][x][y] = 1;
        for (int rest = 1; rest <= k; rest++) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 10; j++) {
                    dp[rest][i][j] = getVal(i-1, j+2, rest-1, dp)+
                            getVal(i-2, j+1, rest-1, dp)+
                            getVal(i-2, j-1, rest-1, dp)+
                            getVal(i-1, j-2, rest-1, dp)+
                            getVal(i+1, j+2, rest-1, dp)+
                            getVal(i+2, j+1, rest-1, dp)+
                            getVal(i+2, j-1, rest-1, dp)+
                            getVal( i+1, j-2, rest-1, dp);
                }
            }
        }

        return dp[k][0][0];
    }

    /**
     * 处理边界问题
     */
    private static int getVal(int i, int j, int rest, int[][][] dp) {
        if (i<0 || i>8 || j<0 || j>9) return 0;
        return dp[rest][i][j];
    }


    // --- 测试

    public static int dp(int a, int b, int k) {
        int[][][] dp = new int[10][9][k + 1];
        dp[a][b][0] = 1;
        for (int rest = 1; rest <= k; rest++) {
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 9; y++) {
                    int ways = pick(dp, x + 2, y + 1, rest - 1);
                    ways += pick(dp, x + 1, y + 2, rest - 1);
                    ways += pick(dp, x - 1, y + 2, rest - 1);
                    ways += pick(dp, x - 2, y + 1, rest - 1);
                    ways += pick(dp, x - 2, y - 1, rest - 1);
                    ways += pick(dp, x - 1, y - 2, rest - 1);
                    ways += pick(dp, x + 1, y - 2, rest - 1);
                    ways += pick(dp, x + 2, y - 1, rest - 1);
                    dp[x][y][rest] = ways;
                }
            }
        }
        return dp[0][0][k];
    }

    public static int pick(int[][][] dp, int x, int y, int rest) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        return dp[x][y][rest];
    }

    public static void main(String[] args) {
        int x = 7;
        int y = 7;
        int step = 10;
        System.out.println(dp(x, y, step));
        System.out.println(jump(x, y, step));
        System.out.println(jump2(x, y, step));
    }
}
