package com.zs.shuati.class04;

import java.util.*;

/**
 * 8.大楼轮廓线问题
 * Leetcode题目：https://leetcode.com/problems/the-skyline-problem/
 */
public class Code08_TheSkylineProblem {
    /**
     * 要求返回轮廓线变化的拐点坐标.比如{{5, 3}, {7, 0}}
     *
     */
    public List<List<Integer>> getSkyline(int[][] matrix) {
        if(matrix == null || matrix.length == 0
                || matrix[0] == null || matrix[0].length == 0){
            return new ArrayList<>();
        }

        int N = matrix.length;

        // 转换为位置的升高降低信息。
        Info[] infos = new Info[N*2];
        for (int i = 0; i < N; i++) {
            infos[i] = new Info(matrix[i][0], matrix[i][2], true);
            infos[i+N] = new Info(matrix[i][1], matrix[i][2], false);
        }

        // 按照位置排序。
        Arrays.sort(infos, new MyComparator());

        // 遍历info数组，每次更新当前的最大高度，
        // key 高度，value 次数
        TreeMap<Integer, Integer> heightMap = new TreeMap<>();
        // key 位置 value 高度
        TreeMap<Integer, Integer> maxHeightMap = new TreeMap<>();
        for (int i = 0; i < infos.length; i++) {
            int curH = infos[i].height;
            Integer count = heightMap.get(curH);
            if(infos[i].isUp){

                if(count != null){
                    heightMap.put(curH,  count + 1);
                }else {
                    heightMap.put(curH, 1);
                }
            }else {
                if(count==1){
                    heightMap.remove(curH);
                }else{
                    heightMap.put(curH, count-1);
                }
            }
            if(heightMap.isEmpty()){
                maxHeightMap.put(infos[i].site, 0);
            }else {
                maxHeightMap.put(infos[i].site, heightMap.lastKey());
            }
        }

        List<List<Integer>> ans = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : maxHeightMap.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            if(ans.isEmpty() || ans.get(ans.size()-1).get(1) != value){
                ans.add(Arrays.asList(key, value));
            }
        }
        return ans;
    }

    class MyComparator implements Comparator<Info>{
        @Override
        public int compare(Info o1, Info o2) {
            return Integer.compare(o1.site, o2.site);
        }
    }

    class Info{
        public int site; // 变化的为位置
        public int height; // 变化的高度
        public boolean isUp; // 是否是上升。
        public Info(int s, int h, boolean b){
            site = s;
            height = h;
            isUp = b;
        }
    }
}
