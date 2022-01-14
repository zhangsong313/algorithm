package com.zs.tixi.class24;

/*
 * 题目二：
 *      给定一个正数数组arr，请把arr中所有的数分成两个集合。
 *      如果arr长度为偶数，两个集合包含数的个数要一样多。
 *      如果arr长度为奇数，两个集合包含的个数必须只差一个。
 *      请尽量让两个集合的累加和接近。
 *      返回：最接近情况下，较小集合的累加和。
 */
public class Code02_SplitSumClosedSizeHalf {

    /**
     * 如果是arr长度偶数,只能选择arr.length/2个数.
     * 如果长度为奇数,可能选址arr.length/2和arr.length/2+1个数.
     */
    public static int right(int[] arr) {
        if(arr==null|| arr.length==0) return 0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum+=arr[i];
        }
        if((arr.length & 1)==0){
            return process(0, arr.length/2, sum/2, arr);
        } else {
            return Math.max(process(0, arr.length/2, sum/2, arr), process(0, arr.length/2+1, sum/2, arr));
        }

    }

    /**
     * 当前来到了数组arr的i位置,还有rc个数字要选,选的累加和不能超过rn.请返回选择数字的累加和.
     *
     * 如果当前来到末尾位置,如果rc为0,返回0.否则返回-1表示选择错误.
     * 如果rc为0返回0.
     *
     * 尝试不选当前数字,递归调用(i+1, rc, rn)
     * 如果当前数字不超过rn,递归调用(i+1, rc-1, rn-arr[i])+arr[i]
     *
     * 返回上面两种尝试结果的较大值.
     */
    private static int process(int i, int rc, int rn, int[] arr){
        if(rc==0) return 0;
        if(i==arr.length) return -1;

        int p1 = process(i+1, rc, rn, arr);
        int p2 = -1 ;
        if(arr[i]<=rn) {
            p2 = process(i+1, rc-1, rn-arr[i],arr);
            if(p2!=-1) p2+=arr[i];
        }
        return Math.max(p1, p2);
    }


    /**
     * 动态规划表
     * 根据上面的递归过程可以看出 (i, rc, rn)依赖(i+1, rc, rn)和(i+1, rc-1, rn-arr[i])
     *
     * arr的长度为N, RC = arr.length/2+1, RN = sum/2
     * 创建一张三维表dp[N+1][RC+1][RN+1]
     *
     * 第N层,除了rc==0外其它值全为-1.
     * 从第N-1层填起(i, rc, rn)依赖(i+1, rc, rn)和(i+1, rc-1, rn-arr[i])+arr[i],注意判断-1.
     *
     * 长度为偶数,返回dp[0][arr.length/2][sum/2]
     * 如果长度为奇数,返回dp[0][arr.length/2][sum/2]和dp[0][arr.length/2+1][sum/2]中的较大值
     */
    public static int dp2(int[] arr) {
        if(arr==null|| arr.length==0) return 0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum+=arr[i];
        }
        int N = arr.length;
        int RC = arr.length/2+1;
        int RN = sum/2;
        int [][][] dp = new int[N+1][RC+1][RN+1];

        for (int i = 1; i <= RC; i++) {
            for (int j = 0; j <= RN; j++) {
                dp[N][i][j] = -1;
            }
        }
        for (int i = N-1; i >=0; i--) {
            for (int rc = 1; rc <= RC; rc++) {
                for (int rn = 0; rn <= RN; rn++) {
                    int p1 =dp[i+1][rc][rn];
                    int p2 = -1 ;
                    if(arr[i]<=rn) {
                        p2 = dp[i+1][rc-1][rn-arr[i]];
                        if(p2!=-1) p2+=arr[i];
                    }
                    dp[i][rc][rn] = Math.max(p1, p2);
                }
            }
        }
        if((arr.length&1)==0){
            return dp[0][arr.length/2][sum/2];
        } else {
            return Math.max(dp[0][arr.length/2][sum/2] , dp[0][arr.length/2+1][sum/2]);
        }



//        if (arr == null || arr.length < 2) {
//            return 0;
//        }
//        int sum = 0;
//        for (int num : arr) {
//            sum += num;
//        }
//        sum >>= 1;
//        int N = arr.length;
//        int M = (arr.length + 1) >> 1;
//        int[][][] dp = new int[N][M + 1][sum + 1];
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j <= M; j++) {
//                for (int k = 0; k <= sum; k++) {
//                    dp[i][j][k] = Integer.MIN_VALUE;
//                }
//            }
//        }
//        for (int i = 0; i < N; i++) {
//            for (int k = 0; k <= sum; k++) {
//                dp[i][0][k] = 0;
//            }
//        }
//        for (int k = 0; k <= sum; k++) {
//            dp[0][1][k] = arr[0] <= k ? arr[0] : Integer.MIN_VALUE;
//        }
//        for (int i = 1; i < N; i++) {
//            for (int j = 1; j <= Math.min(i + 1, M); j++) {
//                for (int k = 0; k <= sum; k++) {
//                    dp[i][j][k] = dp[i - 1][j][k];
//                    if (k - arr[i] >= 0) {
//                        dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j - 1][k - arr[i]] + arr[i]);
//                    }
//                }
//            }
//        }
//        return Math.max(dp[N - 1][M][sum], dp[N - 1][N - M][sum]);
    }

    // for test
    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 50;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = right(arr);
//            int ans2 = dp(arr);
            int ans3 = dp2(arr);
            if (
//                    ans1 != ans2 ||
                            ans1 != ans3) {
                printArray(arr);
                System.out.println(ans1);
//                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
