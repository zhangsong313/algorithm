package com.zs.tixi.class33;

/**
 *      改写二维IndexTree:
 *      对于一个二维数组,要求支持以下功能:
 *          1.update(i, j, c); 更新(i, j)位置的数为c.时间复杂度O(logN*logM)
 *          2.query(i1, j1, i2, j2); 查询(i1, j1)到(i2, j2)这个矩形范围内的累加和.时间复杂度O(logN*logM)
 */
public class Code02_IndexTree2D {
    /**
     * 属性:
     *      1. tree:辅助二维数组.
     *      2. N:行数
     *      3. M:列数
     * 方法:
     *      1.update(i, j, c); 更新(i, j)位置的数为c.
     *      2.query(i1, j1, i2, j2); 查询(i1, j1)到(i2, j2)这个矩形范围内的累加和
     */
    public static class IndexTree2D{
        private int[][] tree;
        private int N;
        private int M;

        /**
         * 初始化辅助数组
         */
        public IndexTree2D(int[][] origin){
            int N = origin.length;
            int M = origin[0].length;
            int[][] num = new int[N+1][M+1];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    num[i+1][j+1] = origin[i][j];
                }
            }
            int[][] tree = new int[N+1][M+1];
            for (int i = 1; i < tree.length; i++) {
                for (int j = 1; j < tree[0].length; j++) {
                    int startI = i-(i&-i)+1;
                    int startJ = j-(j&-j)+1;

                    for (int k = startI; k <= i; k++) {
                        for (int l = startJ; l <= j; l++) {
                            tree[i][j] += num[k][l];
                        }
                    }
                }
            }
        }

        /**
         * 查询(i1, j1)到(i2, j2)这个矩形范围内的累加和
         */
        public int query(int i1, int j1, int i2, int j2){
            return preSum(i2, j2) - preSum(i1, j2) - preSum(i2, j1) + preSum(i1, j1);
        }

        /**
         * 查询(1, 1)到(i, j)这个矩形范围内的累加和
         */
        public int preSum(int i, int j){
            int ans = 0;
            while (i!=0){
                while (j!=0){
                    ans += tree[i][j];
                    j -= j & -j;
                }
                i -= i & -i;
            }
            return ans;
        }

        /**
         * (i, j)位置更新为c
         */
        public void update(int i, int j, int c){
            add(i, j, c-query(i, j, i, j));
        }

        /**
         * (i, j)位置加上c
         */
        public void add(int i, int j, int c){
            while (i<N){
                while (j<M){
                    tree[i][j] += c;
                    j += j & -j;
                }
                i += i & -i;
            }
        }
    }
}
