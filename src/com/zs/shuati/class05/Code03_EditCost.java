package com.zs.shuati.class05;

/**
 * 3 编辑距离问题
 * 字符串的每个字符新增代价为a，删除代价为b，更新代价为c。str1通过最少代价cost的操作变为str2，
 * 则最少代价cost称为str1到str2的编辑距离
 * 给定字符串str1和字符串str2，请返回编辑距离。
 */
public class Code03_EditCost {
    /**
     * 动态规划：样本对应模型
     * 当前s1的len1长度编辑得到s2的len2长度，编辑距离为多少？
     * len1==len2==0情况下，编辑距离为0
     * len1==0 情况下，编辑距离为len2*add
     * len2==0 情况下，编辑距离为len1*del
     * 其余情况下:
     * 考虑0..len1-1范围变换为0..len2：递归调用(len1-1, len2)+del
     * 考虑0..len1范围变换为0..len2-1：递归调用(len1, len2-1)+add
     * 考虑0..len1范围变换为0..len2：如果(len1位置字符等于len2位置字符) 递归调用(len1-1, len2-1) 否则 递归调用(len1-1, len2-1)+update
     * 三种情况取最小值。
     */
    public static int minCost(String s1, String s2, int add, int del, int update) {
        if(s1==null || s2==null) throw new RuntimeException("参数不能为空");
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int M = str1.length;
        int N = str2.length;
        int[][] dp = new int[M+1][N+1];
        for (int i = 1; i <= M; i++) {
            dp[i][0] = i*del;
        }
        for (int i = 1; i <= N; i++) {
            dp[0][i] = i*add;
        }
        for (int i = 1; i <=M ; i++) {
            for (int j = 1; j <=N ; j++) {
                dp[i][j] = dp[i-1][j] + del;
                dp[i][j] = Math.min(dp[i][j], dp[i][j-1]+add);
                if(str1[i-1]==str2[j-1]){
                    dp[i][j] = Math.min(dp[i][j] , dp[i-1][j-1]);
                }else{
                    dp[i][j] = Math.min(dp[i][j], dp[i-1][j-1] + update);
                }
            }
        }
        return dp[M][N];
    }

    /**
     * 对解法1使用数组压缩优化
     * dp[i][j] 依赖 dp[i-1][j], dp[i][j-1], dp[i-1][j-1]
     * 每次要记录下左上角的值。否则会被覆盖。
     */
    public static int minCost1(String s1, String s2, int add, int del, int update) {
        if(s1==null || s2==null) throw new RuntimeException("参数不能为空");
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int M = str1.length;
        int N = str2.length;
        int[] dp = new int[N+1];
        for (int i = 1; i <= N; i++) {
            dp[i] = i*add;
        }
        for (int i = 1; i <=M ; i++) {
            int leftUp = dp[0];
            dp[0] = i*del;
            for (int j = 1; j <=N ; j++) {
                int temp = dp[j];
                dp[j] = temp + del;
                dp[j] = Math.min(dp[j], dp[j-1]+add);
                if(str1[i-1]==str2[j-1]){
                    dp[j] = Math.min(dp[j] , leftUp);
                }else{
                    dp[j] = Math.min(dp[j], leftUp + update);
                }
                leftUp = temp;
            }
        }
        return dp[N];
    }

    /**
     * 老师写的验证方法
     */
    public static int minCost2(String str1, String str2, int ic, int dc, int rc) {
        if (str1 == null || str2 == null) {
            return 0;
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        char[] longs = chs1.length >= chs2.length ? chs1 : chs2;
        char[] shorts = chs1.length < chs2.length ? chs1 : chs2;
        if (chs1.length < chs2.length) {
            int tmp = ic;
            ic = dc;
            dc = tmp;
        }
        int[] dp = new int[shorts.length + 1];
        for (int i = 1; i <= shorts.length; i++) {
            dp[i] = ic * i;
        }
        for (int i = 1; i <= longs.length; i++) {
            int pre = dp[0];
            dp[0] = dc * i;
            for (int j = 1; j <= shorts.length; j++) {
                int tmp = dp[j];
                if (longs[i - 1] == shorts[j - 1]) {
                    dp[j] = pre;
                } else {
                    dp[j] = pre + rc;
                }
                dp[j] = Math.min(dp[j], dp[j - 1] + ic);
                dp[j] = Math.min(dp[j], tmp + dc);
                pre = tmp;
            }
        }
        return dp[shorts.length];
    }

    public static void main(String[] args) {
        String str1 = "ab12cd3";
        String str2 = "abcdf";
        System.out.println(minCost1(str1, str2, 5, 3, 2));
        System.out.println(minCost2(str1, str2, 5, 3, 2));

        str1 = "abcdf";
        str2 = "ab12cd3";
        System.out.println(minCost1(str1, str2, 3, 2, 4));
        System.out.println(minCost2(str1, str2, 3, 2, 4));

        str1 = "";
        str2 = "ab12cd3";
        System.out.println(minCost1(str1, str2, 1, 7, 5));
        System.out.println(minCost2(str1, str2, 1, 7, 5));

        str1 = "abcdf";
        str2 = "";
        System.out.println(minCost1(str1, str2, 2, 9, 8));
        System.out.println(minCost2(str1, str2, 2, 9, 8));

    }
}
