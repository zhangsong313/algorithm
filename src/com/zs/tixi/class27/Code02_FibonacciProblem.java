package com.zs.tixi.class27;

/*
 * 题目二:
 *      斐波那契数列矩阵乘法方式的实现.
 *
 * 题目三:
 *      一个人可以一次往上迈1个台阶,可以迈2个台阶.
 *      返回这个人迈N级台阶的方法数.
 *
 * 题目四:
 *      第一年农场有一只成熟的母牛A,往后的每年
 *      1) 每一只成熟的母牛都会生出一只母牛.
 *      2) 每一只新出生的母牛都在出生的第三年成熟.
 *      3) 每一只母牛永远不会死
 *      返回N年后牛的数量.
 */
public class Code02_FibonacciProblem {
    /**
     * 斐波那契数列又称兔子数列.
     * 农户家中有一只小兔子,兔子二年后成熟,
     * 成熟后可以生小兔子,小兔子两年后成熟又可以再生
     * 假设兔子不会死,请返回n年后农户有多少只兔子.
     *
     * 1, 1, 2, 3, 5, 8....
     * 第n年兔子数量fn = 第n-1年兔子会全部存活下来fn-1 + 只有两年前的那些兔子可以生小兔子fn-2
     *
     * 思路:已知f1=1, f2=1, fn=fn-1+fn-2.那么从f3开始一直递推到fn,可以得到答案.
     * 时间复杂度:O(N)
     */
    public static int fibonacci(int n){
        if(n==0) return 0;
        if(n==1 || n==2) return 1;

        int pre1 = 1;
        int pre2 = 1;
        int ans = 0;
        for (int i = 3; i <= n; i++) {
            ans = pre1+pre2;
            pre1=pre2;
            pre2=ans;
        }
        return ans;
    }

    /**
     * O(logN)的思路
     * 如果将斐波那契数列问题推广到任何严格递推式.
     * fn = afn-1+bfn-2+cfn-3+...+xfn-i
     * 那么必有|fn,fn-1,...,fn-i+1|=|fn-1, fn-2,...,fn-i|*|i阶固定矩阵|
     * 所以|fn,fn-1,...,fn-i+1|=|fi,...,f2, f1|*|i阶固定矩阵|^n-i
     *
     * 所以递推式问题转化为两个问题:
     * 1. 求出|i阶固定矩阵|,该矩阵可以有前几项列方程组推算出来.或者通过方程组得出
     * 2. 求出固定矩阵的n-i次方.
     *
     * 求i阶固定矩阵,参考getConstantRect方法实现.

     * 求某个数的n次方,可以将n按照拆成2的次方累乘结果,根据n的二进制形式,哪个位置有1就是需要乘哪个位置的次方.
     *
     * 由前几项可以推出,斐波那契数列的常数矩阵为[[1, 1], [1, 0]]
     *
     */
    public static int fibonacci2(int n){
        if(n==0) return 0;
        if(n==1 || n==2) return 1;
//        int[][] rect = new int[][]{{1, 1}, {1, 0}};
        int[][] rect = getConstantRect(new int[]{1, 1});
        int[][] rectN = rectPow(rect, n-2);
        return rectN[0][0]+rectN[1][0];
    }

    /**
     * 台阶方法数问题
     * 当前还有n阶,方法数为step(n)
     * 尝试当前选择迈一步,那么方法数为step(n-1).
     * 尝试当前选择迈两步,那么方法数为step(n-2)
     * 所以所有step(n) = step(n-1)+step(n-2)
     * step(1) = 1
     * step(2) = 2
     */
    public static int step(int n){
        if (n==0) return 0;
        if (n==1||n==2) return n;
        int pre1 = 1;
        int pre2 = 2;
        int ans = 0;
        for (int i = 3; i <= n; i++) {
            ans = pre1+pre2;
            pre1=pre2;
            pre2=ans;
        }
        return ans;
    }

    /**
     * 由递推式可得
     * |fn, fn-1|=|f2,f1|*|2阶矩阵|^(n-2)
     *
     */
    public static int step2(int n){
        int[][] rect = getConstantRect(new int[]{1, 1});
        int[][] rectN = rectPow(rect, n - 2);
        return 2*rectN[0][0]+rectN[1][0];
    }

    /**
     * n年母牛数量问题
     * fn = fn-1+fn-3
     * f1=1
     * f2=2
     * f3=3
     *
     */
    public static int cow(int n){
        if (n==0) return 0;
        if (n==1||n==2||n==3) return n;
        int pre1 = 1;
        int pre2 = 2;
        int pre3 = 3;
        int ans = 0;
        for (int i = 4; i <= n; i++) {
            ans = pre3+pre1;
            pre1 = pre2;
            pre2=pre3;
            pre3=ans;
        }
        return ans;
    }

    /**
     * 根据递推式转换矩阵求解
     */
    public static int cow2(int n){
        int[][] rect = getConstantRect(new int[]{1, 0, 1});
        int[][] rectN = rectPow(rect, n - 3);
//        return 3*rectN[0][0] + 2*rectN[1][0] + 1*rectN[2][0];
        return rectMulti(new int[][]{{3, 2, 1}}, rectN)[0][0];
    }

    /**
     * 获取递推式中的常数矩阵.
     * coe为递推式的系数列表.(暂不考虑有常数项的递推式)
     *
     * 举例:fn = fn-1+0*fn-2+fn-3
     * |fn, fn-1, fn-2|=|fn-1, fn-2, fn-3|*|3*3矩阵|
     * 假设3*3矩阵为下面的矩阵:
     * |a1, b1, c1|
     * |a2, b2, c2|
     * |a3, b3, c3|
     * 根据矩阵乘法规则,有:
     * fn = a1*fn-1+a2*fn-2+a3*fn-3,所以a1=1, a2=0, a3=1.
     * fn-1 = b1*fn-1+b2*fn-2+b3*fn-3,所以b1=1,b2=0,b3=0.
     * fn-2 = c1*fn-1+c2*fn-2+c3*fn-3,所以c1=0,c2=1,c3=0
     * 所以以上递推式的常数矩阵为
     * |1,1,0|
     * |0,0,1|
     * |1,0,0|
     *
     * 推广:fn=a*fn-1+b*fn-2+c*fn-3
     * |a,1,0|
     * |b,0,1|
     * |c,0,0|
     * fn=a*fn-1+b*fn-2+c*fn-3+d*fn-4
     * |a,1,0,0|
     * |b,0,1,0|
     * |c,0,0,1|
     * |d,0,0,0|
     */
    public static int[][] getConstantRect(int[] coe){
        int N = coe.length;
        int[][] ans = new int[N][N];
        for (int i = 0; i < N-1; i++) {
            ans[i][0] = coe[i];
            ans[i][i+1] = 1;
        }
        ans[N-1][0] = coe[N-1];
        return ans;
    }

    /**
     * 求矩阵rect的n次方.
     * 一个N*N的矩阵,乘完后仍然是一个N*N规模的矩阵.
     *
     * 定义ans,初始结果为矩阵中的1(i,j相等位置为1,其余位置为0).
     * 定义p,初始值为rect.
     * 循环:i=0;n!=0;i++
     *      n>>=i
     *      如果n的最低位是1,说明需要当前次方项:
     *          ans *= p;
     *      p *= p; // p扩展为之前次方项的2倍.
     * 返回ans
     */
    public static int[][] rectPow(int[][] rect, int n) {
        if(rect==null || rect.length==0 ||
                rect[0]==null || rect[0].length==0
        ||rect.length!=rect[0].length ){
            throw new RuntimeException("wrong arg...");
        }
        int N = rect.length;

        int[][] ans = new int[N][N];
        for (int i = 0; i < N; i++) {
            ans[i][i] = 1;
        }
        int[][] p = rect;
        while (n!=0) {
           if( (n & 1) != 0){
               ans = rectMulti(ans, p);
           }
           n>>=1;
           p = rectMulti(p, p);
        }
        return ans;
    }

    /**
     * 返回两个矩阵相乘的结果.
     * 矩阵乘法的规则如下:
     * 1、当矩阵A的列数（column）等于矩阵B的行数（row）时，A与B可以相乘。
     * 2、矩阵C的行数等于矩阵A的行数，C的列数等于B的列数。
     * 3、乘积C的第m行第n列的元素等于矩阵A的第m行的元素与矩阵B的第n列对应元素乘积之和。
     */
    public static int[][] rectMulti(int[][] r1, int[][] r2) {
        if (r1==null || r2==null ||
                r1.length==0||r2.length==0
                ||r1[0]==null || r2[0]==null
        || r1[0].length==0 || r2[0].length==0
        || r1[0].length!=r2.length){
            throw new RuntimeException("wrong arg...");
        }
        int N = r1.length;
        int M = r2[0].length;
        int[][] ans = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int k = 0; k < r1[0].length; k++) {
                    ans[i][j] += r1[i][k]*r2[k][j];
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 19;
        System.out.println("============斐波那契数列=========");
        System.out.println(fibonacci(n));
        System.out.println(fibonacci2(n));
        System.out.println("=======台阶方法数问题===========");
        System.out.println(step(n));
        System.out.println(step2(n));
        System.out.println("=======奶牛个数问题===========");
        System.out.println(cow(n));
        System.out.println(cow2(n));

//        getConstantRect(new int[]{1,0,1});




//        int[][] r1 = new int[][]{{1, 2}, {4, 5}, {7, 8}};
//        int[][] r2 = new int[][]{{1, 2, 3}, {4, 5, 6}};
//        // 答案应该是{}
//        rectMulti(r1, r2);

//        int[][] r = new int[][]{{1, 2}, {3, 4}};
//        rectPow(r, 4);
    }
}
