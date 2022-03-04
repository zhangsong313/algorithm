package com.zs.shuati.class01;

import java.util.Arrays;

/**
 * 给定两个非负数组x和hp，长度都是N，再给定一个正数range
 * x有序，x[i]表示i号怪兽在x轴上的位置
 * hp[i]表示i号怪兽的血量
 * 再给定一个正数range，表示如果法师释放技能的范围长度（施法半径）
 * 被打到的每只怪兽损失1点血量。返回要把所有怪兽血量清空，至少需要释放多少次AOE技能？
 */
public class Code06_AOE {

    // 纯暴力解法
    public static int minAoe1(int[] x, int[] hp, int range) {
        int N = x.length;
        int[] coverLeft = new int[N];
        int[] coverRight = new int[N];
        int left = 0;
        int right = 0;
        for (int i = 0; i < N; i++) {
            while (x[i] - x[left] > range) {
                left++;
            }
            while (right < N && x[right] - x[i] <= range) {
                right++;
            }
            coverLeft[i] = left; // 找到i位置情况下，最左边能覆盖到的位置，最右边能覆盖到的位置.
            coverRight[i] = right - 1;
        }
        return process(hp, coverLeft, coverRight);
    }

    public static int process(int[] hp, int[] coverLeft, int[] coverRight) {
        int N = hp.length;
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            for (int f = coverLeft[i]; f <= coverRight[i]; f++) {
                if (hp[f] > 0) {
                    int[] next = aoe(hp, coverLeft[i], coverRight[i]);
                    ans = Math.min(ans, 1 + process(next, coverLeft, coverRight));
                    break;
                }
            }
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }

    /**
     * 在L..R范围上对hp统一减1.
     */
    public static int[] aoe(int[] hp, int L, int R) {
        int N = hp.length;
        int[] next = new int[N];
        for (int i = 0; i < N; i++) {
            next[i] = hp[i];
        }
        for (int i = L; i <= R; i++) {
            next[i] -= next[i] > 0 ? 1 : 0;
        }
        return next;
    }

    // 贪心策略：永远让最左边缘以最优的方式(AOE尽可能往右扩，最让最左边缘盖住目前怪的最左)变成0，也就是选择：
    // 一定能覆盖到最左边缘, 但是尽量靠右的中心点
    // 等到最左边缘变成0之后，再去找下一个最左边缘...
    public static int minAoe2(int[] x, int[] hp, int range) {
        int N = x.length;
        int ans = 0;
        for (int i = 0; i < N; i++) {
            if (hp[i] > 0) {
                int triggerPost = i;
                while (triggerPost < N && x[triggerPost] - x[i] <= range) {
                    triggerPost++;
                }
                ans += hp[i];
                aoe(x, hp, i, triggerPost - 1, range);
            }
        }
        return ans;
    }

    /**
     * 把i..trigger位置
     */
    public static void aoe(int[] x, int[] hp, int L, int trigger, int range) {
        int N = x.length;
        int RPost = trigger;
        while (RPost < N && x[RPost] - x[trigger] <= range) {
            RPost++;
        }
        int minus = hp[L];
        for (int i = L; i < RPost; i++) {
            hp[i] = Math.max(0, hp[i] - minus);
        }
    }

    // 贪心策略和方法二一样，但是需要用线段树，可优化成O(N * logN)的方法，
    /**
     * 遍历x，求出以i位置为施法左边界的情况下的右边界。（施法半径为range，法师必须站在x对应位置上？）
     * 收集结果为R数组
     * 定义ans。
     * 遍历hp
     *      通过线段树查询i位置的值。
     *      如果i位置值大于0
     *          ans累加hp[i]的值。
     *          调用线段树，对[i..R[i]]，统一减去hp[i]（注意线段树下标从1开始）
     */
    public static int minAoe3(int[] x, int[] hp, int range) {
        int[] R = new int[x.length];
        int right = 0;
        int center = 0;
        for (int i = 0; i < x.length; i++) {
            while (center<x.length && x[center]-x[i]<=range){
                center++;
            }
            right = center-1;
            while (right<x.length && x[right]-x[center-1]<=range){
                right++;
            }
            R[i] = right-1;
        }
        int ans = 0;
        SegmentTree seg = new SegmentTree(hp);
        for (int i = 0; i < hp.length; i++) {
            long hpi = seg.query(i + 1, i + 1);
            if(hpi>0){
                ans+=hpi;
                seg.add(i+1, R[i]+1, (int)(-hpi));
            }
        }
        return ans;
    }

    public static class SegmentTree{
        private int[] nums;
        private long[] sums;
        private int[] add;
        private int[] update;
        private boolean[] isUpdate;
        public SegmentTree(int[] origin){
            int N = origin.length;
            nums = new int[N+1];
            for (int i = 0; i < N; i++) {
                nums[i+1] = origin[i];
            }
            int len = N << 2;
            sums = new long[len];
            add = new int[len];
            update = new int[len];
            isUpdate = new boolean[len];
            build(1, nums.length-1, 1);
        }

        private void build(int l, int r, int rt) {
            if(l==r) {
                sums[rt] = nums[l];
                return;
            }
            int m = l + (r-l>>1);
            build(l, m, rt<<1);
            build(m+1, r, rt<<1|1);
            sums[rt] = sums[rt<<1]+sums[rt<<1|1];
        }

        public void add(int L, int R, int C){
            doAdd(L, R, C, 1, nums.length-1, 1);
        }

        private void doAdd(int L, int R, int C, int l, int r, int rt) {
            if(l>=L && r<=R){
                sums[rt] += (r-l+1)*C;
                add[rt] += C;
                return;
            }
            int m = l + (r-l>>1);
            pushDown(rt, m-l+1, r-m);
            if(m>=L){
                doAdd(L, R, C, l, m, rt<<1);
            }
            if(m+1<=R){
                doAdd(L, R, C, m+1, r, rt<<1|1);
            }
            sums[rt] = sums[rt<<1]+sums[rt<<1|1];
        }

        private void pushDown(int rt, int ln, int rn) {
            int left = rt<<1;
            int right = rt<<1|1;
            if(isUpdate[rt]){
                sums[left] = ln*update[rt];
                update[left] = update[rt];
                isUpdate[left] = true;
                sums[right] = rn*update[rt];
                update[right] = update[rt];
                isUpdate[right] = true;

                add[left] = 0;
                add[right] = 0;

                isUpdate[rt] = false;
            }
            if(add[rt]!=0){
                sums[left] += ln*add[rt];
                add[left] += add[rt];
                sums[right] += rn*add[rt];
                add[right] += add[rt];

                add[rt] = 0;
            }
        }

        public void update(int L, int R, int C){
            doUpdate(L, R, C, 1, nums.length-1, 1);
        }

        private void doUpdate(int L, int R, int C, int l, int r, int rt) {
//            System.out.println("l:"+l+"r:"+r+"rt : "+rt);
            if(l>=L && r<=R){
                sums[rt] = (r-l+1)*C;
                update[rt] = C;
                isUpdate[rt] = true;

                add[rt] = 0;
                return;
            }
            int m = l + (r-l>>1);
            pushDown(rt, m-l+1, r-m);
            if(m>=L){
                doUpdate(L, R, C, l, m, rt<<1);
            }
            if(m+1<=R){
                doUpdate(L, R, C, m+1, r, rt<<1|1);
            }
            sums[rt] = sums[rt<<1]+sums[rt<<1|1];
        }

        public long query(int L, int R){
            return doQuery(L, R, 1, nums.length-1, 1);
        }

        private long doQuery(int L, int R, int l, int r, int rt) {
            if(l>=L && r<=R){
                return sums[rt];
            }
            int m = l + (r-l>>1);
            pushDown(rt, m-l+1, r-m);
            long ans = 0;
            if(m>=L){
                ans+=doQuery(L, R, l, m, rt<<1);
            }
            if(m+1<=R){
                ans+=doQuery(L, R, m+1, r, rt<<1|1);
            }
            return ans;
        }
    }

    // for test
    public static int[] randomArray(int n, int valueMax) {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) (Math.random() * valueMax) + 1;
        }
        return ans;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        int N = arr.length;
        int[] ans = new int[N];
        for (int i = 0; i < N; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    public static void main(String[] args) {
        int N = 500;
        int X = 10000;
        int H = 50;
        int R = 10;
        int time = 5000;
        System.out.println("test begin");
        for (int i = 0; i < time; i++) {
            int len = (int) (Math.random() * N) + 1;
            int[] x = randomArray(len, X);
            Arrays.sort(x);
            int[] hp = randomArray(len, H);
            int range = (int) (Math.random() * R) + 1;
            int[] x2 = copyArray(x);
            int[] hp2 = copyArray(hp);
            int ans2 = minAoe2(x2, hp2, range);
            // 已经测过下面注释掉的内容，注意minAoe1非常慢，
            // 所以想加入对比需要把数据量(N, X, H, R, time)改小
//			int[] x1 = copyArray(x);
//			int[] hp1 = copyArray(hp);
//			int ans1 = minAoe1(x1, hp1, range);
//			if (ans1 != ans2) {
//				System.out.println("Oops!");
//				System.out.println(ans1 + "," + ans2);
//			}
            int[] x3 = copyArray(x);
            int[] hp3 = copyArray(hp);
            int ans3 = minAoe3(x3, hp3, range);
            if (ans2 != ans3) {
                System.out.println("Oops!");
                System.out.println(ans2 + "," + ans3);
            }
        }
        System.out.println("test end");
    }
}
