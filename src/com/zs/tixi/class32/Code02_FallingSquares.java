package com.zs.tixi.class32;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/*
 * 线段树实例二:
 * 想象一下标准的俄罗斯方块游戏，X轴是积木最终下落到底的轴线
 *下面是这个游戏的简化版：
 * 1）只会下落正方形积木
 * 2）[a,b] -> 代表一个边长为b的正方形积木，积木左边缘沿着X = a这条线从上方掉落
 * 3）认为整个X轴都可能接住积木，也就是说简化版游戏是没有整体的左右边界的
 * 4）没有整体的左右边界，所以简化版游戏不会消除积木，因为不会有哪一层被填满。
 *
 * 给定一个N*2的二维数组matrix，可以代表N个积木依次掉落，
 * 返回每一次掉落之后的最大高度
 * https://leetcode.com/problems/falling-squares/
 */
public class Code02_FallingSquares {
    /**
     * 线段树,
     * 变量:
     *      num,初始化后的原数组.
     *      max,指定范围的最大值.
     *      update,指定范围的更新值.
     *      isUpdate,指定范围是否更新.
     * 方法:
     *      query:查询指定范围的最大值.
     *      update:更新指定范围的值.
     */
    public static class SegmentTree{
        private int size;
        private int[] max;
        private int[] update;
        private boolean[] isUpdate;
        /**
         * 初始化数据大小,初始化所有位置高度为0即可.
         */
        public SegmentTree(int size){
            this.size = size;
            int helpLen = size << 2;
            max = new int[helpLen]; // 全部为0,无需初始化.
            update = new int[helpLen];
            isUpdate = new boolean[helpLen];
        }

        /**
         * 将L到R范围更新为C.
         * 正确更新对应的max数据.
         */
        public void update(int L, int R, int C){
            doUpdate(L, R, C, 1,size,1);
        }

        /**
         * 当前来到l-r范围,对应的位置为rt.
         * 执行的任务为L-R范围上全部更新为DC.
         * 请正确设置对应的max值.
         *
         * 如果L-R包括了l-r:
         *      更新rt位置的max值.
         *      更新rt位置的update值.
         *      更新rt位置的isUpdate值.
         *      返回.
         * 否则:
         *      得到l-r的中点.
         *      将l-r上的缓存任务下发到下一层.
         *      如果L<=m,左侧递归调用doUpate.
         *      如果R>=m+1,右侧递归调用doUpdate.
         *      此时左侧和右侧的max值都更新成功.设置rt位置的max值.
         */
        private void doUpdate(int L, int R, int C,
                              int l, int r,
                              int rt) {
            if(l>=L && r<=R){
                max[rt] = C;
                update[rt] = C;
                isUpdate[rt] = true;
                return;
            }
            int m = l+((r-l)>>1);
            pushDown(rt, m-l+1, r-m);
            if(L<=m){
                doUpdate(L, R, C, l, m, rt<<1);
            }
            if(R>=m+1){
                doUpdate(L, R, C, m+1, r, rt<<1|1);
            }
            max[rt] = Math.max(max[rt<<1], max[rt<<1|1]);
        }

        /**
         * 将rt位置的缓存任务下发到下一层,ln为左侧部分有多少数,rn为右侧部分有多少数.
         * 如果isUpdate为true:
         *      左侧max更新.
         *      左侧update更新.
         *      左侧isUpdate更新.
         *      右侧max更新
         *      右侧update更新.
         *      右侧isUpdate更细.
         *      rt位置的isUpdate设置为false.
         */
        private void pushDown(int rt, int ln, int rn) {
            if(isUpdate[rt]){
                max[rt<<1] = update[rt];
                update[rt<<1] = update[rt];
                isUpdate[rt<<1] = true;
                max[rt<<1|1] = update[rt];
                update[rt<<1|1] = update[rt];
                isUpdate[rt<<1|1] = true;
                isUpdate[rt] = false;
            }
        }

        /**
         * 返回L-R范围上的最大值.
         */
        public int query(int L, int R){
            return doQuery(L, R, 1, size, 1);
        }

        /**
         * 当前来到l-r范围,对应位置为rt.
         * 需要执行的任务为L-R范围上返回最大值.
         *
         * 如果L-R包括l-r:
         *      返回rt位置的max值.
         * 得到l-r范围的中点m.
         * 将l-r范围的缓存任务下发一层.
         * 如果L<=m,左侧递归调用doQuery查询最大值.
         * 如果R>=m+1,右侧递归调用doQuery查询最大值.
         * 左右两侧最大值比较后返回l-r范围上的最大值.
         */
        private int doQuery(int L, int R,
                             int l, int r,
                             int rt) {
            if(l>=L && r<=R){
                return max[rt];
            }
            int m = l+((r-l)>>1);
            pushDown(rt, m-l+1, r-m);
            int ans = Integer.MIN_VALUE;
            if(L<=m){
                ans = Math.max(ans, doQuery(L, R, l, m, rt<<1));
            }
            if(R>=m+1){
                ans = Math.max(ans, doQuery(L, R, m+1, r, rt<<1|1));
            }
            return ans;
        }
    }

    /**
     * positions为掉落的方块列表,n*2的规模,
     * 每行数据表示掉落一个方形,第一个数据表示掉落方形的左侧位置,第二个数据表示掉落方形的边长.
     * 要求返回每个方形落下后的最高高度,组成数组后返回.
     *
     * 思路:虽然x轴向左和右是无限的,但是由于落下的方形有限.所以只需关系落下方形的那些位置即可.
     * 将这些位置按照从左到右的顺序映射为1-N.
     * 每落下一个方形,将映射后的范围更新为新高度.然后尝试与之前的最大高度比较.
     */
    public List<Integer> fallingSquares(int[][] positions) {
        List<Integer> ans = new ArrayList<>(positions.length);
        HashMap<Integer, Integer> indexMap = getIndexMap(positions);
        SegmentTree seg = new SegmentTree(indexMap.size());
        int maxHeight = 0;
        for (int[] pos : positions) {
            Integer L = indexMap.get(pos[0]);
            Integer R = indexMap.get(pos[0] + pos[1] - 1);
            int curHeight = seg.query(L, R) + pos[1];
            maxHeight = Math.max(maxHeight, curHeight);
            ans.add(maxHeight);
            seg.update(L, R, curHeight);
        }
        return ans;
    }

    /**
     * 将每个方形的开始和结束位置都统计,映射为1-N范围的值.
     */
    private HashMap<Integer, Integer> getIndexMap(int[][] positions) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int[] pos : positions) {
            set.add(pos[0]);
            set.add(pos[0]+pos[1]-1);
        }
        HashMap<Integer, Integer> indexMap = new HashMap<>();
        int count = 0;
        for (Integer index : set) {
            indexMap.put(index, ++count);
        }
        return indexMap;
    }
//
//    public static void main(String[] args) {
//        int[][] positions = new int[][]{
//            {3, 2},
//            {9, 8},
//            {4, 4}
//        };
//        List<Integer> ans = new Code02_FallingSquares().fallingSquares(positions);
//
//    }
}
