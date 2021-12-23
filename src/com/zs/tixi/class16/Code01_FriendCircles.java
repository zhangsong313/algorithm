package com.zs.tixi.class16;

/*
 * 1. Leetcode 547. Number of Provinces
 *      有n个城市，部分城市之间是连通的。
 *      省份被定义为直接连接或间接连接的城市集合。
 *      参数给定一个nxn的矩阵。第[i][j]位置表示i号城市与j号城市之间的连通性。1表示联通，0表示不连通。
 *      返回省份数量。
 */
public class Code01_FriendCircles {
    /**
     * 在n*n的矩阵中，只有上半部分有作用。
     * 将矩阵的上半部分加入并查集，返回省份数量即可。
     * @param M
     * @return
     */
    public static int findCircleNum(int[][] M) {
        UnionFind unionFind = new UnionFind(M.length);
        for (int i = 0; i < M.length; i++) {
            for (int j = i+1; j < M[i].length; j++) {
                int isUnion = M[i][j];
                if(isUnion == 1){
                    unionFind.union(i, j);
                }
            }
        }
        return unionFind.sets();
    }

    public static class UnionFind {
        private int[] parents; // 当前对象的父对象
        private int[] size; // 当前代表对象的集合大小。
        private int sets; // 当前集合数
        private int[] help; // 长链压缩的辅助数组。

        /**
         * 为从0-N减一个数构建并查集
         * @param N
         */
        public UnionFind(int N){
            parents = new int[N];
            size = new int[N];
            sets = N;
            help = new int[N];
            for (int i = 0; i < N; i++) {
                parents[i] = i;
                size[i] = 1;
            }
        }

        /**
         * 找到指定对象的代表对象
         *
         * 同时将沿途节点的父节点改为代表节点。
         */
        private int findHead(int i){
            int index = 0;
            while (i!=parents[i]){
                help[index++] = i;
                i = parents[i];
            }
            for (int j = 0; j < index; j++) {
                parents[help[j]] = i;
            }
            return i;
        }

        /**
         * 合并两对象所在集合
         */
        public void union(int i, int j){
            int iHead = findHead(i);
            int jHead = findHead(j);
            if (iHead!=jHead){
                int iSize = size[iHead];
                int jSize = size[jHead];
                int big = iSize>=jSize?iHead:jHead;
                int small = big == iHead?jHead:iHead;
                parents[small] = big;
                size[small] = 0;
                size[big] = iSize+jSize;
                sets --;
            }
        }

        /**
         * 判断两个对象是否在同一集合
         */
        public boolean isSameSet(int i, int j){
            return findHead(i)==findHead(j);
        }

        /**
         * 返回并查集内集合数
         */
        public int sets(){return sets;}
    }
}
