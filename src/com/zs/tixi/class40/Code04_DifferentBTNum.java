package com.zs.tixi.class40;

/**
 * 有N个二叉树节点，每个节点彼此之间无任何差别，返回由N个二叉树节点，组成的不同结构数量是多少？
 */
public class Code04_DifferentBTNum {
    /**
     * 有0个节点，只有一种。
     * 有1个节点，也只有一种。
     * 有两个节点，头节点固定，左边0个，右边1个，左边1个右边0个。
     * 有n个节点，头节点，固定，左边0~(n-1)各，右边(n-1)~0个。
     */
    public static long num1(int N) {
        if(N<0) return 0;
        if(N<2) return 1;
        int ans = 0;
        for (int i = 0; i < N; i++) {
            ans += num1(i)*num1(N-1-i);
        }
        return ans;
    }


    public static long num2(int N) {
        if (N < 0) {
            return 0;
        }
        if (N < 2) {
            return 1;
        }
        long a = 1;
        long b = 1;
        for (int i = 1, j = N + 1; i <= N; i++, j++) {
            a *= i;
            b *= j;
            long gcd = gcd(a, b);
            a /= gcd;
            b /= gcd;
        }
        return (b / a) / (N + 1);
    }

    public static long gcd(long m, long n) {
        return n == 0 ? m : gcd(n, m % n);
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        for (int i = 0; i < 15; i++) {
            long ans1 = num1(i);
            long ans2 = num2(i);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
