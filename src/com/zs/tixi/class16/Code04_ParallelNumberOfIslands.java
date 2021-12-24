package com.zs.tixi.class16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/*
 * 4. 岛问题扩展
 *      如果matrix极大，设计一种并行计算方案。
 */
public class Code04_ParallelNumberOfIslands {
    /**
     * 思路：将矩阵划分为16个区域。边界上的位置先不统计。
     * 每个小区域内统计后，最后统计边界位置，尝试合并。
     * @param matrix
     * @return
     */
    public static int parallelIslands(char[][] matrix){
        int R = matrix.length;
        int C = matrix[0].length;
        UnionFind<Dot> unionFind = new UnionFind<>();
        Map<String, Dot> map = new HashMap<>();
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (matrix[i][j]=='1'){
                    Dot dot = new Dot(i, j);
                    map.put(i+"_"+j, dot);
                    unionFind.add(dot);
                }
            }
        }
//        for (int i = 0; i < R; i++) {
//            for (int j = 0; j < C; j++) {
//                if (matrix[i][j]=='1'){
//                    unionFind.union(map.get(getKey(i-1, j)), map.get(getKey(i, j)));
//                    unionFind.union(map.get(getKey(i, j-1)), map.get(getKey(i, j)));
//                }
//            }
//        }

        // 将矩阵横向尽量均匀分为16份
        int splitCount = 8;
        int r = R / (splitCount-1);
        CyclicBarrier cb = new CyclicBarrier(splitCount+1);
        for (int i = 0; i < splitCount-1; i++) {
            int start = i*r;
            int end = (i+1)*r;
//            end = end<R?end:R; //最后一块可能到不了end。
            System.out.println("["+start+"-"+end+"]");
            int finalEnd = end;
            new Thread(()->{
                for (int j = start+1; j < finalEnd; j++) {
                    for (int k = 0; k < C; k++) {
                        if (matrix[j][k]=='1'){
                            unionFind.union(map.get(getKey(j-1, k)), map.get(getKey(j, k)));
                            unionFind.union(map.get(getKey(j, k-1)), map.get(getKey(j, k)));
                        }
                    }
                }
                try {
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        new Thread(()->{
            int start = (splitCount-1)*r;
            int end = R;
            System.out.println("["+start+"-"+end+"]");
            for (int j = (splitCount-1)*r+1; j < R; j++) {
                for (int k = 0; k < C; k++) {
                    if (matrix[j][k]=='1'){
                        unionFind.union(map.get(getKey(j-1, k)), map.get(getKey(j, k)));
                        unionFind.union(map.get(getKey(j, k-1)), map.get(getKey(j, k)));
                    }
                }
            }
            try {
                cb.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            cb.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(unionFind.sets());
        // 分区边界位置进行计算
        for (int i = 0; i < splitCount; i++) {
            int board = i*r;
            if (board==R) break;
            System.out.println("["+board+"]");
            for (int j = 0; j < C; j++) {
                if (matrix[board][j]=='1'){
                    unionFind.union(map.get(getKey(board-1, j)), map.get(getKey(board, j)));
                    unionFind.union(map.get(getKey(board+1, j)), map.get(getKey(board, j)));
                }
            }
        }

        return unionFind.sets();
    }

    private static String getKey(int i, int j){
        return i+"_"+j;
    }

    /**
     * 并行计算的
     */
    public static class UnionFind<V>{
        private Map<V, V> parents;
        private Map<V, Integer> size;


        public UnionFind(){
            parents = new HashMap<>();
            size = new HashMap<>();

        }

        public void add(V v){
            parents.put(v, v);
            size.put(v, 1);
        }

        public V findHead(V v){
            List<V> help = new ArrayList<>();
            while (parents.get(v)!=v){
                help.add(v);
                v = parents.get(v);
            }
            for (int i = 0; i < help.size(); i++) {
                parents.put(help.get(i), v);
            }
            return v;
        }

        public void union(V v1, V v2){
            if(v1==null||v2==null) return;
            
            V head1 = findHead(v1);
            V head2 = findHead(v2);
            if(head1!=head2){
                Integer size1 = size.get(head1);
                Integer size2 = size.get(head2);
                V big = size1>=size2?head1:head2;
                V small = big == head1?head2:head1;
                parents.put(small, big);
                size.put(big, size1+size2);
                size.remove(small);
            }
        }

        public int sets(){
            return size.size();
        }
    }


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
        System.out.println("并查集(并行map实现)的运行结果: " + parallelIslands(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集(并行map实现)的运行时间: " + (end - start) + " ms");

        System.out.println();
    }

}
