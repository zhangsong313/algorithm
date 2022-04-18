package com.zs.shuati.class04;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 1.数组为{3, 2, 2, 3, 1}，查询为(0, 3, 2)，意思是在数组里下标0~3这个范围上，有几个2？答案返回2
 * 假设给你一个数组arr，对这个数组的查询非常频繁，且都给了查询组，请返回所有查询的结果
 */
public class Code01_QueryHobby {

    /**
     * 对数器：遍历指定区间，查询对应值有多少个。
     * 时间复杂度O(N)
     */
    public static class Right{
        private int[] arr;
        public Right(int[] arr){
            this.arr = arr;
        }
        public int query(int start, int end, int value){
            int ans = 0;
            for (int i = start; i <= end; i++) {
                if(arr[i]==value) ans++;
            }
            return ans;
        }
    }

    /**
     * 思路：创建辅助map。
     * key为arr中的值，value为值对应的下标列表。
     * 检索时通过值找到下标列表。
     * 通过二分找到小于start和小于等于end的下标数量。相减得到答案。
     *
     * 时间复杂度：O(logN)
     */
    public static class QueryBox{
        private HashMap<Integer, List<Integer>> help;
        public QueryBox(int[] arr){
            help = new HashMap<>();
            for (int i = 0; i < arr.length; i++) {
                if(!help.containsKey(arr[i])){
                   help.put(arr[i], new ArrayList<>());
                }
                help.get(arr[i]).add(i);
            }
        }
        public int query(int start, int end, int value){
            List<Integer> indexList = help.get(value);
            if(indexList == null) return 0;
            int lessStart = countLess(indexList, start);
            int lessEnd = countLess(indexList, end+1);
            return lessEnd-lessStart;
        }

        /**
         * indexList为有序数组
         * 请返回小于val有多少个数
         */
        private int countLess(List<Integer> indexList, int val) {
            int L = 0;
            int R = indexList.size()-1;
            int mostRight = -1;
            while (L<=R){
                int mid = L + (R-L >> 1);
                if(indexList.get(mid)<val){
                    mostRight = mid;
                    L = mid + 1;
                }else {
                    R = mid -1;
                }
            }

            return mostRight+1;
        }
    }


    public static int[] generateRandomArray(int len, int value) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value) + 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        int len = 300;
        int value = 20;
        int testTimes = 1000;
        int queryTimes = 1000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(len, value);
            int N = arr.length;
            Right box1 = new Right(arr);
            QueryBox box2 = new QueryBox(arr);
            for (int j = 0; j < queryTimes; j++) {
                int a = (int) (Math.random() * N);
                int b = (int) (Math.random() * N);
                int L = Math.min(a, b);
                int R = Math.max(a, b);
                int v = (int) (Math.random() * value) + 1;
                if (box1.query(L, R, v) != box2.query(L, R, v)) {
                    System.out.println("Oops!");
                }
            }
        }
        System.out.println("test end");
    }
}
