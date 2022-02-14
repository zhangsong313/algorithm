package com.zs.tixi.class32;

import com.zs.xiaobai.common.MyCompValue;

/*
 * 线段树
 *  1.一种支持范围整体修改和范围整体查询的数据结构
 *  2.解决的问题范畴:
 *      大范围信息可以只由左,右两侧信息加工出,
 *      而不必遍历左右两个子范围的具体情况.
 *
 * 线段树实例一:
 *  给定一个数组arr,用户希望你实现如下三个方法.
 *  1) void add(int L, int R, int V):让数组arr[L..R]上每个数都加上V
 *  2) void update(int L, int R, int V):让数组arr[L..R]上每个数都变成V
 *  3) int sum(int L,int R):让返回arr[L..R]这个范围整体的累加和
 *  怎么让这三个方法,时间复杂度都是O(logN)
 */
public class Code01_SegmentTree {
    /**
     * 定义变量:
     *      num表示初始化后的原始数据(从1开始保存数据)
     *      sum表示范围的累加和.
     *      add表示未下发的累加任务.
     *      update表示未下发的更新任务.
     *      isUpdate表示是否执行当前更新任务.
     * 定义函数:
     *      build:初始化sum数组.
     *      add:执行范围累加任务.
     *      update:执行范围更新任务.
     *      query:查询范围上的累加和.
     */
    public static class SegmentTree {
        private int[] num;
        private int[] sum;
        private int[] add;
        private int[] update;
        private boolean[] isUpdate;

        /**
         * 构造函数
         * origin为原始数据,长度为N
         * 初始化:
         * num为N+1长度,将数组从origin复制到num.
         * sum为4N长度,初始化sum数组内容.
         * add为4N长度
         * update为4N长度
         * isUpdate为4N长度的布尔数组.
         */
        public SegmentTree(int[] origin){
            int N = origin.length;
            num = new int[N+1];
            for (int i = 1; i < num.length; i++) {
                num[i] = origin[i-1];
            }
            int helpLen = N << 2;
            sum = new int[helpLen];
            build(1, num.length-1, 1);
            add = new int[helpLen];
            update = new int[helpLen];
            isUpdate = new boolean[helpLen];
        }

        /**
         * 设置sum数组中L到R范围的值,设置的位置是rt
         * 如果当前L==R,说明当前到了叶节点,直接在rt位置上设置L位置的值.
         * 求出范围中点M,递归调用build设置左侧范围的sum
         * 递归调用build设置右侧范围的sum.
         * 根据左侧sum值和右侧sum值求出当前范围sum并设置.
         */
        private void build(int L, int R, int rt){
            if(L==R){
                sum[rt] = num[L];
                return;
            }
            int M = L+((R-L)>>1);
            build(L, M, rt<<1);
            build(M+1, R, rt<<1 | 1);
            sum[rt] = sum[rt<<1]+sum[rt<<1 | 1];
        }

        /**
         * L到R范围上所有数加C.
         * 从最大范围开始下发任务.能懒则懒
         */
        public void add(int L, int R, int C){
            doAdd(L, R, C, 1, num.length-1, 1);
        }

        /**
         * 当前来到l-r范围,对应数据的位置在rt位置.
         * 接收到的任务是L-R范围都加C.
         * 请正确更新rt位置的sum值.
         *
         * 如果L-R范围包括了l-r范围:
         *      更新rt位置的sum.
         *      更新rt位置的add.
         *      返回
         * 否则:
         *      将rt位置的懒任务下发.
         *      找到l-r的中点位置m.
         *      如果m>=L,说明任务需要在左侧范围执行.左侧递归调用doAdd
         *      如果m+1<=R,说明任务需要在右侧范围执行,右侧递归调用doAdd.
         *      此时左侧范围的sum和右侧范围的sum都更新成功.设置rt位置的sum值.
         */
        private void doAdd(int L, int R, int C,
                           int l, int r,
                           int rt) {
            if(l>=L && r<=R){
                sum[rt] += (r-l+1)*C;
                add[rt] += C;
                return;
            }
            int m = l+((r-l)>>1);
            pushDown(rt, m-l+1, r-m);
            if(m>=L){
                doAdd(L, R, C, l, m, rt<<1);
            }
            if(m+1<=R){
                doAdd(L, R, C, m+1, r, rt<<1|1);
            }
            sum[rt] = sum[rt<<1] + sum[rt<<1|1];
        }

        /**
         * 将rt位置累积的任务下发.ln为左侧范围有多少数,rn为右侧范围有多少数.
         * 由于update任务总会覆盖掉add任务,因此,
         * 先下发update任务.
         * 后下发add任务.
         * update任务:
         *      左侧范围的sum更新.
         *      左侧范围的update更新.
         *      左侧方位的isUpdate更新.
         *      右侧的sum更新.
         *      右侧的update更新.
         *      右侧的isUpdate更新.
         *      左侧的add清空.右侧的add清空
         *      当前范围的isUpdate清空.
         * add任务:
         *      左侧范围的sum加上当期任务.
         *      左侧范围的add加上当前任务.
         *      右侧范围的sum加上当前任务.
         *      右侧范围的add加上当前任务.
         *      当前范围位置rt上的add任务清空.
         */
        private void pushDown(int rt, int ln, int rn) {
            int left = rt<<1; // 左部分范围对应位置.
            int right = rt<<1|1; // 右部分范围对应位置.

            if(isUpdate[rt]){
                sum[left] = ln*update[rt];
                update[left] = update[rt];
                isUpdate[left] = true;
                sum[right] = rn*update[rt];
                update[right] = update[rt];
                isUpdate[right] = true;

                add[left] = 0;
                add[right] = 0;
                isUpdate[rt] = false;
            }
            if(add[rt]!=0){
                add[left] += add[rt];
                sum[left] += add[rt]*ln;
                add[right] += add[rt];
                sum[right] += add[rt]*rn;
                add[rt] = 0;
            }
        }

        /**
         * 将L到R范围上所有数变为C
         * 从最大范围开始下发任务,能懒则懒.
         */
        public void update(int L, int R, int C){
            doUpdate(L, R, C, 1, num.length-1, 1);
        }

        /**
         * 当前来到l-r范围,对应位置是rt位置.
         * 接收到的任务是L-R范围更新为C.
         * 请正确更新sum信息.
         *
         * 如果L-R范围包括了l-r范围:
         *      更新rt位置的sum.
         *      更新rt位置的update.
         *      更新rt位置的isUpdate.
         *      更新rt位置的add.
         *      返回
         * 否则:
         *      求出l-r范围的中间位置m
         *      将rt位置缓存的任务下发.
         *      如果m>=L,说明左侧范围需要下发任务,左侧递归调用doAdd.
         *      如果m+1<=R,说明右侧范围需要下发任务,右侧递归调用doAdd.
         *      左右两侧范围都正确设置了sum,设置当前范围rt位置的sum值.
         */
        private void doUpdate(int L, int R, int C,
                              int l, int r,
                              int rt) {
            if(L<=l && R>=r){
                sum[rt] = C*(r-l+1);
                update[rt] = C;
                isUpdate[rt] = true;
                add[rt] = 0;
                return;
            }
            int m = l+((r-l)>>1);
            pushDown(rt, m-l+1, r-m);
            if(m>=L){
                doUpdate(L, R, C, l, m, rt<<1);
            }
            if(m+1<=R){
                doUpdate(L, R, C, m+1, r,rt<<1|1);
            }
            sum[rt] = sum[rt<<1] + sum[rt<<1|1];
        }

        /**
         * 查询L到R范围的累加和
         * 从最大范围向下查询,需要拆分的就拆分.
         */
        public long query(int L, int R){
            return doQuery(L, R, 1, num.length-1, 1);
        }

        /**
         * 当前来到l-r范围,对应的位置是rt.
         * 需要查询的任务是L到R范围的累加和.
         * 请返回正确的结果.
         * 如果L-R包括l-r:
         *      直接返回rt位置的sum
         * 求出l-r范围的中点m
         * 将当前范围的懒任务下发.
         * 如果m>=L,说明查询任务需要发给左部分,左侧递归调用doQuery.
         * 如果m+1<=R,说明查询任务需要发给右部分,右侧递归调用doQuery.
         * 将左侧的结果和右侧结果加起来返回.
         */
        private long doQuery(int L, int R,
                            int l, int r,
                            int rt) {
            if(l>=L && r<=R){
                return sum[rt];
            }
            int m = l+((r-l)>>1);
            pushDown(rt, m-l+1, r-m);
            long ans = 0;
            if(L<=m){
                ans += doQuery(L, R, l, m, rt<<1);
            }
            if(R>=m+1){
                ans += doQuery(L, R, m+1, r, rt<<1|1);
            }
            return ans;
        }
    }






    public static class Right {
        public int[] arr;

        public Right(int[] origin) {
            arr = new int[origin.length + 1];
            for (int i = 0; i < origin.length; i++) {
                arr[i + 1] = origin[i];
            }
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public long query(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }

    }

    public static int[] genarateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }

    public static boolean test() {
        int len = 100;
        int max = 10;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = genarateRandomArray(len, max);
            SegmentTree seg = new SegmentTree(origin);
            int S = 1;
            int N = origin.length;
            int root = 1;
//            seg.build(S, N, root);
            Right rig = new Right(origin);
//            MyCompValue.printArr(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
//                    System.out.println(L+"-"+R+"加"+C);
                    seg.add(L, R, C);
                    rig.add(L, R, C);
                } else {
//                    System.out.println(L+"-"+R+"更新"+C);
                    seg.update(L, R, C);
                    rig.update(L, R, C);
                }

//                int num3 = (int) (Math.random() * N) + 1;
//                int num4 = (int) (Math.random() * N) + 1;
//                int L2 = Math.min(num3, num4);
//                int R2 = Math.max(num3, num4);
//                long ans1 = seg.query(L2, R2);
//                long ans2 = rig.query(L2, R2);
//                if (ans1 != ans2) {
//
//                    System.out.println("查询范围:"+L2+"-"+R2);
//                    System.out.println("ans1 : "+ans1);
//                    System.out.println("ans2 : "+ans2);
//                    return false;
//                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R);
                long ans2 = rig.query(L, R);
                if (ans1 != ans2) {
                    MyCompValue.printArr(origin);
                    System.out.println("ans1 : "+ans1);
                    System.out.println("ans2 : "+ans2);
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
//        int[] origin = { 1, 2, 3, 2 };
//        int[] origin = genarateRandomArray(100, 10);
        int[] origin = { -8, -3, 5, -5, -5, 7, 1, -1, 2, 3, -5, -8 };
        SegmentTree seg = new SegmentTree(origin);
        int S = 1; // 整个区间的开始位置，规定从1开始，不从0开始 -> 固定
        int N = origin.length; // 整个区间的结束位置，规定能到N，不是N-1 -> 固定
        int root = 1; // 整棵树的头节点位置，规定是1，不是0 -> 固定
        int L = 2; // 操作区间的开始位置 -> 可变
        int R = 6; // 操作区间的结束位置 -> 可变
        int C = 4; // 要加的数字或者要更新的数字 -> 可变
//        int L = 2; // 操作区间的开始位置 -> 可变
//        int R = 5; // 操作区间的结束位置 -> 可变
//        int C = 4; // 要加的数字或者要更新的数字 -> 可变
        // 区间生成，必须在[S,N]整个范围上build
//        seg.build(S, N, root);
        // 区间更新，可以改变L、R和C的值，其他值不可改变
        seg.update(6, 12, 1);
        // 区间修改，可以改变L、R和C的值，其他值不可改变
        seg.add(3, 3, -2);
        // 区间查询，可以改变L和R的值，其他值不可改变
        long sum = seg.query(8, 10);
        System.out.println(sum);

        Right rig = new Right(origin);
        rig.update(6, 12, 1);
        rig.add(3, 3, -2);
        long sum2 = rig.query(8, 10);
        System.out.println(sum2);

        System.out.println("对数器测试开始...");
        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));

    }
}
