package com.zs.tixi.class16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 2. 岛问题：
 *      给定一个二维数组matrix, 里面的值不是1就是0.
 *      上下左右相邻的1认为是一片岛。
 *      返回matrix中岛的数量。
 */
public class Code02_NumberOfIslands {
    /**
     * 使用并查集（map）解决，
     *
     * 将第一行加入并查集，如果为1，加入并查集，与左边位置尝试合并。
     * 将第一列加入并查集，如果为1，加入并查集，与上边位置尝试合并。
     * 从第二行第二列开始遍历矩阵，如果为1，加入并查集，尝试与左上位置合并。
     *
     * 返回并查集内集合个数
     * @param matrix
     * @return
     */
    public static int numIslands1(char[][] matrix){
        if (matrix==null || matrix.length==0) return 0;
        int R = matrix.length;
        int C = matrix[0].length;
        Dot[][] dots = new Dot[R][C];
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if(matrix[i][j]=='1') dots[i][j] = new Dot(i, j);
            }
        }

        UnionFind1<Dot> unionFind = new UnionFind1();
        if(matrix[0][0]=='1') unionFind.add(dots[0][0]);
        for (int i = 1; i < C; i++) {
            if (matrix[0][i]=='1'){
                unionFind.add(dots[0][i]);
                if (matrix[0][i-1]=='1') unionFind.union(dots[0][i-1], dots[0][i]);
            }
        }
        for (int i = 1; i < R; i++) {
            if(matrix[i][0]=='1'){
                unionFind.add(dots[i][0]);
                if(matrix[i-1][0]=='1') unionFind.union(dots[i-1][0],dots[i][0]);
            }
        }
        for (int i = 1; i < R; i++) {
            for (int j = 1; j < C; j++) {
                if (matrix[i][j]=='1'){
                    unionFind.add(dots[i][j]);
                    if(matrix[i-1][j]=='1') unionFind.union(dots[i-1][j],dots[i][j]);
                    if(matrix[i][j-1]=='1') unionFind.union(dots[i][j-1],dots[i][j]);
                }
            }
        }
        return unionFind.sets();
    }
    private static class Dot{
        public int r;
        public int c;
        public Dot(int r, int c){
            this.r = r;
            this.c = c;
        }

        @Override
        public String toString() {
            return "{" + r +
                    "," + c +
                    '}';
        }
    }
    private static class UnionFind1<V>{
        private Map<V, V> parents; // 当前对象到父对象
        private Map<V, Integer> size; // 当前代表对象所属集合大小
        public UnionFind1(){
            parents = new HashMap<>();
            size = new HashMap<>();
        }

        /**
         * 加入并查集
         */
        public void add(V v){
            parents.put(v, v);
            size.put(v, 1);
        }

        /**
         * 查询代表元素
         */
        public V findHead(V v){
            List<V> help = new ArrayList<>();
            while (v!=parents.get(v)){
                help.add(v);
                v = parents.get(v);
            }
            for (int i = 0; i < help.size(); i++) {
                parents.put(help.get(i), v);
            }
            return v;
        }

        /**
         * 合并
         */
        public void union(V v1, V v2){
            V head1 = findHead(v1);
            V head2 = findHead(v2);
            if (head1!=head2){
                Integer size1 = size.get(head1);
                Integer size2 = size.get(head2);
                V big = size1>=size2?head1:head2;
                V small= big == head1?head2:head1;
                parents.put(small, big);
                size.remove(small);
                size.put(big, size1+size2);
            }
        }

        /**
         * 返回并查集集合数
         */
        public int sets(){
            return size.size();
        }
    }


    /**
     * 使用并查集（数组）解决，
     *
     * 将第一行加入并查集，如果为1，加入并查集，与左边位置尝试合并。
     * 将第一列加入并查集，如果为1，加入并查集，与上边位置尝试合并。
     * 从第二行第二列开始遍历矩阵，如果为1，加入并查集，尝试与左上位置合并。
     *
     * 返回并查集内集合个数
     * @param matrix
     * @return
     */
    public static int numIslands2(char[][] matrix){
        int L = matrix.length;
        int W = matrix[0].length;
        UnionFind2 unionFind = new UnionFind2(L, W);

        if(matrix[0][0]=='1') unionFind.add(0, 0);
        for (int i = 1; i < W; i++) {
            if(matrix[0][i]=='1'){
                unionFind.add(0, i);
                if (matrix[0][i-1]=='1')unionFind.union(0, i, 0, i-1);
            }
        }
        for (int i = 1; i < L; i++) {
            if(matrix[i][0]=='1'){
                unionFind.add(i, 0);
                if (matrix[i-1][0]=='1')unionFind.union(i, 0, i-1, 0);
            }
        }
        for (int i = 1; i < L; i++) {
            for (int j = 1; j < W; j++) {
                if (matrix[i][j] == '1'){
                    unionFind.add(i, j);
                    if (matrix[i-1][j]=='1')unionFind.union(i, j, i-1, j);
                    if (matrix[i][j-1]=='1')unionFind.union(i, j, i, j-1);
                }
            }
        }

        return unionFind.sets();
    }

    private static class UnionFind2{
        private int[]parents;
        private int[]size;
        private int sets;
        private int[]help;
        private int col; // 二维矩阵有多宽，用来转化数组下标。

        /**
         * 初始化一个长度为n，宽度为m的并查集大小。
         * @param l
         * @param w
         */
        public UnionFind2(int l, int w){
            parents = new int[l*w];
            size = new int[l*w];
            sets = 0;
            help = new int[l*w];
            col = w;
        }

        /**
         * 将指定i,j位置转为一维数组的下标
         */
        private int transIndex(int i, int j){
            return i*col+j;
        }

        /**
         * (i,j)加入并查集。
         */
        public void add(int i, int j){
            int ij = transIndex(i, j);
            parents[ij] = ij;
            size[ij] = 1;
            sets++;
        }

        /**
         * 返回指定位置的集合代表
         * @return
         */
        public int findHead(int ij){
            int index = 0;
            while (ij!= parents[ij]){
                help[index++] = ij;
                ij = parents[ij];
            }
            for (int k = 0; k < index; k++) {
                parents[k] = ij;
            }
            return ij;
        }

        /**
         * 合并(i1, j1)和(i2, j2)
         */
        public void union(int i1, int j1, int i2, int j2){
            int index1 = transIndex(i1, j1);
            int index2 = transIndex(i2, j2);
            int head1 = findHead(index1);
            int head2 = findHead(index2);
            if(head1 != head2){
                int size1 = size[index1];
                int size2 = size[index2];
                int big = size1>=size2?head1:head2;
                int small = big==head1?head2:head1;
                parents[small] = big;
                size[small] = 0;
                size[big] = size1+size2;
                sets--;
            }
        }

        /**
         * 返回有多少个集合。
         */
        public int sets(){
            return sets;
        }
    }

    /**
     * 感染方法
     */
    public static int numIslands3(char[][] matrix){
        int ans = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j]=='1'){
                    ans++;
                    infect(matrix, i, j);
                }
            }
        }
        return ans;
    }

    /**
     * 如果当前位置是界外，或者不是'1'，返回
     *
     * 设置当前位置为'0'
     * 递归调用当前位置的上下左右位置。
     * @param matrix
     * @param i
     * @param j
     */
    private static void infect(char[][] matrix, int i, int j) {
        if( i<0 || i==matrix.length || j<0 || j==matrix[0].length || matrix[i][j]!='1') return;
        matrix[i][j] = '0';
        infect(matrix, i-1, j);
        infect(matrix, i+1, j);
        infect(matrix, i, j-1);
        infect(matrix, i, j+1);
    }


    // 为了测试
    public static char[][] generateRandomMatrix(int row, int col) {
        char[][] board = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = Math.random() < 0.5 ? '1' : '0';
            }
        }
        return board;
    }

    // 为了测试
    public static char[][] copy(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        char[][] ans = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                ans[i][j] = board[i][j];
            }
        }
        return ans;
    }

    // 为了测试
    public static void main(String[] args) {
        int row = 0;
        int col = 0;
        char[][] board1 = null;
        char[][] board2 = null;
        char[][] board3 = null;
        long start = 0;
        long end = 0;

        row = 1000;
        col = 1000;
        board1 = generateRandomMatrix(row, col);
        board2 = copy(board1);
        board3 = copy(board1);

        System.out.println("感染方法、并查集(map实现)、并查集(数组实现)的运行结果和运行时间");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numIslands3(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(map实现)的运行结果: " + numIslands1(board2));
        end = System.currentTimeMillis();
        System.out.println("并查集(map实现)的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行结果: " + numIslands2(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行时间: " + (end - start) + " ms");

        System.out.println();

//        row = 10000;
//        col = 10000;
//        board1 = generateRandomMatrix(row, col);
//        board3 = copy(board1);
//        System.out.println("感染方法、并查集(数组实现)的运行结果和运行时间");
//        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);
//
//        start = System.currentTimeMillis();
//        System.out.println("感染方法的运行结果: " + numIslands3(board1));
//        end = System.currentTimeMillis();
//        System.out.println("感染方法的运行时间: " + (end - start) + " ms");
//
//        start = System.currentTimeMillis();
//        System.out.println("并查集(数组实现)的运行结果: " + numIslands2(board3));
//        end = System.currentTimeMillis();
//        System.out.println("并查集(数组实现)的运行时间: " + (end - start) + " ms");

    }

}
