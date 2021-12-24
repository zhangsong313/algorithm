package com.zs.tixi.class16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/*
 * 3. 岛问题扩展
 *      Leetcode 305.Number of Island Ⅱ
 *      给定一个m*n列大小的空间area，都是0.此时岛数量为0.
 *      给定一个二维数组positions, 里面的每个一维数组只有两位，(x,y)表示对应的area位置设置为1
 *      最后返回n次设置岛屿后，每次岛数量集合。
 */
public class Code03_NumberOfIslandsII {

    public static List<Integer> numIslands21(int m, int n, int[][] positions) {
        UnionFind1 uf = new UnionFind1(m, n);
        List<Integer> ans = new ArrayList<>();
        for (int[] position : positions) {
            ans.add(uf.connect(position[0], position[1]));
        }
        return ans;
    }

    public static class UnionFind1 {
        private int[] parent;
        private int[] size;
        private int[] help;
        private final int row;
        private final int col;
        private int sets;

        public UnionFind1(int m, int n) {
            row = m;
            col = n;
            sets = 0;
            int len = row * col;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
        }

        private int index(int r, int c) {
            return r * col + c;
        }

        private int find(int i) {
            int hi = 0;
            while (i != parent[i]) {
                help[hi++] = i;
                i = parent[i];
            }
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            return i;
        }

        private void union(int r1, int c1, int r2, int c2) {
            if (r1 < 0 || r1 == row || r2 < 0 || r2 == row || c1 < 0 || c1 == col || c2 < 0 || c2 == col) {
                return;
            }
            int i1 = index(r1, c1);
            int i2 = index(r2, c2);
            if (size[i1] == 0 || size[i2] == 0) {
                return;
            }
            int f1 = find(i1);
            int f2 = find(i2);
            if (f1 != f2) {
                if (size[f1] >= size[f2]) {
                    size[f1] += size[f2];
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
                sets--;
            }
        }

        public int connect(int r, int c) {
            int index = index(r, c);
            if (size[index] == 0) {
                parent[index] = index;
                size[index] = 1;
                sets++;
                union(r - 1, c, r, c);
                union(r + 1, c, r, c);
                union(r, c - 1, r, c);
                union(r, c + 1, r, c);
            }
            return sets;
        }
    }

    // 课上讲的如果m*n比较大，会经历很重的初始化，而k比较小，怎么优化的方法
    /**
     * 使用map形式的并查集
     * @param m
     * @param n
     * @param positions
     * @return
     */
    public static List<Integer> numIslands22(int m, int n, int[][] positions) {
        UnionFind2 uf = new UnionFind2();
        List<Integer> ans = new ArrayList<>();
        for (int[] position : positions) {
            ans.add(uf.connect(position[0], position[1]));
        }
        return ans;
    }

    public static class UnionFind2 {
        private HashMap<String, String> parent;
        private HashMap<String, Integer> size;
        private ArrayList<String> help;
        private int sets;

        public UnionFind2() {
            parent = new HashMap<>();
            size = new HashMap<>();
            help = new ArrayList<>();
            sets = 0;
        }

        private String find(String cur) {
            while (!cur.equals(parent.get(cur))) {
                help.add(cur);
                cur = parent.get(cur);
            }
            for (String str : help) {
                parent.put(str, cur);
            }
            help.clear();
            return cur;
        }

        private void union(String s1, String s2) {
            if (parent.containsKey(s1) && parent.containsKey(s2)) {
                String f1 = find(s1);
                String f2 = find(s2);
                if (!f1.equals(f2)) {
                    int size1 = size.get(f1);
                    int size2 = size.get(f2);
                    String big = size1 >= size2 ? f1 : f2;
                    String small = big == f1 ? f2 : f1;
                    parent.put(small, big);
                    size.put(big, size1 + size2);
                    sets--;
                }
            }
        }

        public int connect(int r, int c) {
            String key = String.valueOf(r) + "_" + String.valueOf(c);
            if (!parent.containsKey(key)) {
                parent.put(key, key);
                size.put(key, 1);
                sets++;
                String up = String.valueOf(r - 1) + "_" + String.valueOf(c);
                String down = String.valueOf(r + 1) + "_" + String.valueOf(c);
                String left = String.valueOf(r) + "_" + String.valueOf(c - 1);
                String right = String.valueOf(r) + "_" + String.valueOf(c + 1);
                union(up, key);
                union(down, key);
                union(left, key);
                union(right, key);
            }
            return sets;
        }

    }

    /**
     * 自己写的。
     * @param m
     * @param n
     * @param positions
     * @return
     */
    public static List<Integer> numIslands23(int m, int n, int[][] positions){
        UnionFind3 unionFind = new UnionFind3(m, n);
        List<Integer> ans = new ArrayList<>();
        for(int[] pos : positions){
            int r = pos[0];
            int c = pos[1];
            unionFind.add(r, c); // 当前位置加入并查集。
            // 当前位置尝试和上下左右合并。
            unionFind.union(r, c, r-1, c);
            unionFind.union(r, c, r+1, c);
            unionFind.union(r, c, r, c-1);
            unionFind.union(r, c, r, c+1);
            // 收集此时的集合个数。
            ans.add(unionFind.sets());
        }
        return ans;
    }

    private static class UnionFind3 {
        private int[]parents;
        private int[]size;
        private int sets;
        private int[]help;
        private int col; // 二维矩阵有多宽，用来转化数组下标。
        private int row; // 二维矩阵有多宽，用来转化数组下标。

        /**
         * 初始化一个长度为n，宽度为m的并查集大小。
         * @param l
         * @param w
         */
        public UnionFind3(int l, int w){
            parents = new int[l*w];
            size = new int[l*w];
            sets = 0;
            help = new int[l*w];
            col = w;
            row = l;
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
            // 先做边界检查，再合并。
            if(i1<0 || j1<0 || i2<0 || j2<0 || i1==row || i2==row || j1==col || j2==col) return;
            if (size[transIndex(i1, j1)]==0 || size[transIndex(i2, j2)]==0) return;

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
//                size[small] = 0; // size不要清空，可以用来判断对应位置是否是1.
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

    public static void main(String[] args) {
        int R = (int)(Math.random()*1000)+1;
        int C = (int)(Math.random()*1000)+1;
        List<List<Integer>> posList = new ArrayList<>();
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if(Math.random()<0.3){
                    posList.add(Arrays.asList(i, j));
                }
            }
        }
        int[][] posArr = new int[posList.size()][2];
        for (int i = 0; i < posArr.length; i++) {
            posArr[i][0] = posList.get(i).get(0);
            posArr[i][1] = posList.get(i).get(1);
        }

        List<Integer> ans1 = numIslands21(R, C, posArr);
        List<Integer> ans2 = numIslands22(R, C, posArr);
        List<Integer> ans3 = numIslands23(R, C, posArr);
        System.out.println("ans1 : "+ans1.size());
        System.out.println("ans2 : "+ans2.size());
        System.out.println("ans3 : "+ans3.size());
        isSameList(ans1, ans2);
        isSameList(ans1, ans3);

    }

    /**
     * 判断两个结果是否相同。
     * @param ans1
     * @param ans2
     * @return
     */
    private static boolean isSameList(List<Integer> ans1, List<Integer> ans2) {
        if(ans1.size()!=ans2.size()){
            throw new RuntimeException("size error");
        }
        for (int i = 0; i < ans1.size(); i++) {
            if (!ans1.get(i).equals(ans2.get(i))) {
                System.out.println("i-------- "+i);
                System.out.println(ans1.get(i));
                System.out.println(ans2.get(i));
                throw new RuntimeException("no same error");
            }
        }
        return false;
    }
}
