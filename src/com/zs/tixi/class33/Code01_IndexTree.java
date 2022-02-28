package com.zs.tixi.class33;

/*
 * IndexTree
 *
 *      对于一个数组数据,支持如下功能:
 *          1.update(i, c);更新i位置的数为c.时间复杂度O(logN)
 *          2.query(i, j);查询i..j区间和.时间复杂度O(logN)
 *      特点:
 *      1)支持区间查询
 *      2)没有线段树那么强,但是非常容易改成一维,二维,三维结构.
 *      3)只支持单点更新
 */
public class Code01_IndexTree {
    /**
     * 属性:
     *      1.tree:辅助数组
     *      2.N:原始数据大小.
     *
     * 方法:
     *      1.update:更新指定位置的数据.
     *      2.query:查询指定范围的累加和.
     */
    public static class IndexTree{
        private int[] tree;
        private int N;

        /**
         * 初始化辅助数组.
         * 遍历num数组,从1开始:i
         *      将i按2的次方拆分,
         *      tree[i]应该填入num数组中:去掉最小次方+1到i下标范围的累加和
         *      例如i=10:拆分为8+2,tree[10]应该填入num[9~10]的累加和
         * 对于二进制来说,就是去掉二进制最右侧1后加1到i下标范围.
         * i的最右侧1:i & (-i)
         */
        public IndexTree(int[] origin){
            N = origin.length;
            int[] num = new int[N+1];
            for (int i = 0; i < origin.length; i++) {
                num[i+1] = origin[i];
            }
            tree = new int[N+1];
            for (int i = 1; i < tree.length; i++) {
                int start = i - (i&(-i))+1;
                for (int j = start; j <= i; j++) {
                    tree[i] += num[j];
                }
            }
        }

        /**
         * 返回i..j范围的累加和.
         * j前缀和 - i-1前缀和
         */
        public int query(int i, int j){
            return preSum(j)-preSum(i-1);
        }

        /**
         * 查询前缀和
         * 返回1..i的和.
         * 将i按照2的次方拆开后.
         * tree数组中每次减去最小次方后累加和就是1..i范围的和.
         * 最小次方就是最右侧1:i&(-i)
         */
        public int preSum(int i){
            int ans = 0;
            while (i!=0){
                ans += tree[i];
                i -= i&(-i);
            }
            return ans;
        }

        /**
         * 将i位置更新为c.
         * 等同于i位置加上 c-num[i]
         */
        public void update(int i, int c){
            add(i, c-query(i, i));
        }

        /**
         * i位置数加c.
         * 保证tree数组正确更新.
         *
         * i位置更新后影响tree数组的哪些位置?换句话说,tree有哪些位置会统计sum[i]的值?
         * 将i按照2的次方进行拆分.
         * 影响tree数组中,i+最小次方位置.
         * 例如i=10,10=8+2,影响以下位置:10,10+2=12, 12+4=16, 16+16=32, 32+32=64.......直到超出数组范围.
         * 这些位置都加一个c.
         */
        public void add(int i, int c){
            while (i<=N){
                tree[i] += c;
                i += i&(-i);
            }
        }

    }




    public static class Right {
        private int[] nums;
        private int N;

        public Right(int size) {
            N = size + 1;
            nums = new int[N + 1];
        }

        public int sum(int index) {
            int ret = 0;
            for (int i = 1; i <= index; i++) {
                ret += nums[i];
            }
            return ret;
        }

        public void add(int index, int d) {
            nums[index] += d;
        }

    }

    public static void main(String[] args) {
        int N = 100;
        int V = 100;
        int testTime = 2000000;
        IndexTree tree = new IndexTree(new int[N]);
        Right test = new Right(N);
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int index = (int) (Math.random() * N) + 1;
            if (Math.random() <= 0.5) {
                int add = (int) (Math.random() * V);
                tree.add(index, add);
                test.add(index, add);
            } else {
                int ans1 = tree.preSum(index);
                int ans2 = test.sum(index);
                if (ans1 != ans2) {
                    System.out.println("Oops!");
                }
            }
        }
        System.out.println("test finish");
    }
}
