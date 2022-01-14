package com.zs.tixi.class23;

/*
 * 题目一：
 *      给定3个参数: N, M, K
 *      怪兽有N滴血，等着英雄来砍自己。
 *      英雄每一次打击，都会让怪兽流失[0-M]的血量
 *      到底流失多少？每次在[0-M]上等概率获得一个值。
 *      求K次打击后，英雄把怪兽砍死的概率。
 */
public class Code01_KillMonster {

    /**
     * 求K次打击后,怪兽存活的概率
     * 存活的情况数/总情况数
     * 总情况数:每次有M+1中伤害可能,总共砍K次.所以是M+1的K次方
     */
    public static double right(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        return 1-process(K, N, M)/Math.pow(M+1,K);
    }

    /**
     * 当前还有rest次要砍,怪兽血量为hp.请返回怪兽存活的情况数.
     * 如果hp<=0返回1
     * 如果rest==0 返回0.
     * 尝试砍m滴血,m为[0-M],递归调用rest-1, hp-m
     *
     * 返回上面可能的累加和.
     */
    private static int process(int rest, int hp, int M){
        if(hp<=0) return 0;
        if(rest==0) return 1;
        int p = 0;
        for (int m = 0; m <= M; m++) {
            p+=process(rest-1, hp-m, M);
        }
        return p;
    }

    /**
     * 动态规划表
     * 分析上面的递归过程(rest, hp)依赖(rest-1, hp-m)的值
     *
     * 创建二维表dp[rest+1][hp+1]
     * 第0行的值除了第0列都为1.
     * 第rest行的值为第(rest-1,hp-m)的累加和
     *
     * 斜率优化:
     * (rest, hp) = (rest, hp-1)+(rest-1, hp)-(rest-1, hp-M-1)
     * 返回dp[K][N]
     */
    public static double dp1(int N, int M, int K) {
        int[][] dp = new int[K+1][N+1];
        for (int i = 1; i <= N; i++) {
            dp[0][i] = 1;
        }
        for (int rest = 1; rest <= K; rest++) {
            for (int hp = 0; hp <= N; hp++) {
                if (hp - M - 1 >= 0) {
                    dp[rest][hp] = dp[rest][hp - 1] + dp[rest - 1][hp] + dp[rest - 1][hp - M - 1];
                } else if (hp - 1 >= 0) {
                    dp[rest][hp] = dp[rest][hp - 1] + dp[rest - 1][hp];
                } else {
                    dp[rest][hp] = dp[rest - 1][hp];
                }
            }
        }
        return dp[K][N];
    }

    public static void main(String[] args) {
        int NMax = 10;
        int MMax = 10;
        int KMax = 10;
        int testTime = 200;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * NMax);
            int M = (int) (Math.random() * MMax);
            int K = (int) (Math.random() * KMax);
            double ans1 = right(N, M, K);
            double ans2 = dp1(N, M, K);
//            double ans3 = dp2(N, M, K);
            if (ans1 - ans2 >0.0000001
//                    || ans1 - ans3>0.0000001
            ) {
                System.out.println("Oops!");
                System.out.println(ans1);
                System.out.println(ans2);
//                System.out.println(ans3);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
