package com.zs.tixi.class19;

/*
 * 题目一：
 *      假设有排成一行的N个位置，记为1-N，N一定大于或等于2
 *      开始时机器人在其中的M位置上（M一定是1-N中的一个）
 *      如果机器人来到1位置，那么下一步只能往右来到2位置。
 *      如果机器人来到N位置，那么下一步只能往左来到N-1位置。
 *      如果机器人来到中间位置，那么下一步可以往左走或者往右走。
 *      规定机器人必须走K步，最终能来到P位置，（P也是1-N中的一个）的方法有多少种？
 *      给定四个参数：N, M, K, P， 返回方法数。
 */
public class Code01_RobotWalk {
    /**
     * 暴力递归
     */
    public static int ways1(int N, int start, int aim, int K) {
        return process1(start, K, aim, N);
    }

    /**
     * cur为当前来到的位置
     * rest为剩余步数
     * aim为目标位置
     * N为位置范围
     *
     * 如果rest为0
     *      如果cur为aim 返回1;
     *      否则:返回0;
     * 如果当前来到左边界1位置:
     *      递归调用:cur更新为2, rest减一,返回结果
     * 如果当前位置来到右边界N位置:
     *      递归调用:cur更新为N-1, rest减一, 返回结果
     * 向左走-递归调用:cur更新为cur-1,rest减一
     * 向右走-递归调用:cur更新为cur+1,rest减一
     * 返回以上两个结果之和.
     *
     */
    private static int process1(int cur, int rest, int aim, int N) {
        if (rest==0){
            return cur==aim?1:0;
        }
        if (cur==1){
            return process1(2, rest-1, aim, N);
        }
        if (cur==N){
            return process1(N-1, rest-1, aim, N);
        }
        return process1(cur-1, rest-1, aim, N)+process1(cur+1, rest-1, aim, N);
    }

    /**
     * 傻缓存
     * 暴力递归的方法里,返回值只受两个参数影响:当前位置,范围在1-N.剩余步数:0-K
     * 创建缓存表,大小为N+1,K+1.
     */
    public static int ways2(int N, int start, int aim, int K) {
        int[][] dp = new int[N+1][K+1];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                dp[i][j] = -1;
            }
        }
        return process2(start, K, aim, N, dp);
    }

    /**
     * 如果dp缓存已经有当前cur和rest组合的值,直接返回.
     * 参照暴力递归代码
     * 将当前结果加入缓存表
     * 返回结果
     */
    private static int process2(int cur, int rest, int aim, int N, int[][] dp) {
        if (dp[cur][rest]!=-1) return dp[cur][rest];
        if (rest==0) return cur==aim?1:0;
        if (cur==1) return process2(2, rest-1, aim, N, dp);
        if (cur==N) return process2(N-1, rest-1, aim, N, dp);
        int ans = process2(cur-1, rest-1, aim, N, dp)+process2(cur+1, rest-1, aim, N, dp);
        dp[cur][rest] = ans;
        return ans;
    }

    /**
     * 动态规划表
     * 分析递归过程可以看到,递归过程只依赖两个变量cur和rest,设计一张二维表,cur表示列,rest表示行.
     * base case: rest为0时,只有cur==aim位置时,值为1,其他位置值为0.
     * 除第0行外,其余行都只依赖上一行的值,所以可以从第一行一直填到最后一行.
     * 最后返回aim, rest位置,就是结果.
     */
    public static int ways3(int N, int start, int aim, int K) {
        int[][] dp = new int[N+1][K+1];
        dp[aim][0] = 1; // 第0列
        for (int j = 1; j < K+1; j++) { // 从第一列开始填.
            for (int i = 1; i < N + 1; i++) { // 从1填到N
                if (i==1){
                    dp[i][j] = dp[2][j-1];
                    continue;
                }
                if (i==N){
                    dp[i][j] = dp[N-1][j-1];
                    continue;
                }
                dp[i][j] = dp[i-1][j-1]+dp[i+1][j-1];
            }
        }
        return dp[start][K];
    }

    public static void main(String[] args) {
        System.out.println(ways1(5, 2, 4, 6));
        System.out.println(ways2(5, 2, 4, 6));
        System.out.println(ways3(5, 2, 4, 6));
    }
}
