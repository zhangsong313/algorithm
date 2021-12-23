package com.zs.tixi.class15;

import java.util.PriorityQueue;

/*
 * 2. 一块金条切成两半，是需要花费和长度数值一样的铜板的。
 *      比如长为20的金条，不论怎么切，都要花费20个铜板，一群人想整分整块金条，怎么分最省铜板。
 *      例如：给定数组{10，20，30}代表一共三个人，整块金条长度为60，金条要分成10，20，30三个部分。
 *      如果先把长度为60的金条分成10和50，花费60，再把50长度分为20和30，花费50，一共花费110铜板。
 *      但如果先把60长度的金条分成30和30，花费60，再把再把长度30的金条分成10和20，花费30，一共花费90铜板。
 *      输入一个数组，返回分割的最小代价。
 */
public class Code02_LessMoneySplitGold {
    // 纯暴力！
    public static int lessMoney1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0);
    }

    // 等待合并的数都在arr里，pre之前的合并行为产生了多少总代价
    // arr中只剩一个数字的时候，停止合并，返回最小的总代价

    /**
     * 思路：数组中两个数值合并来统计切分代价。
     * 把所有组合都考虑的情况下，最小代价是多少。
     * @param arr
     * @param pre
     * @return
     */
    public static int process(int[] arr, int pre) {
        if (arr.length == 1) {
            return pre;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                ans = Math.min(ans, process(copyAndMergeTwo(arr, i, j), pre + arr[i] + arr[j]));
            }
        }
        return ans;
    }

    /**
     * 把arr中除了i和j以外的其它数值复制到新数组。然后新数组添加i位置和j位置的和。
     */
    public static int[] copyAndMergeTwo(int[] arr, int i, int j) {
        int[] ans = new int[arr.length - 1];
        int ansi = 0;
        for (int arri = 0; arri < arr.length; arri++) {
            if (arri != i && arri != j) {
                ans[ansi++] = arr[arri];
            }
        }
        ans[ansi] = arr[i] + arr[j];
        return ans;
    }

    /**
     * 准备一个小根堆。pq
     * 把所有长度放入小根堆。
     * 循环：（小根堆内还有至少两个数）
     * 		小根堆弹出两次，这两个长度为最小的两个长度。
     * 		将合并后的数放入小根堆。
     * 		将合并后的数累加到结果。
     * @param arr
     * @return
     */
    public static int lessMoney2(int[] arr) {
        if (arr==null || arr.length<2) return 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i : arr) {
            queue.add(i);
        }
        int ans = 0;
        while (queue.size()>1){
            int top2 = queue.poll() + queue.poll();
            queue.add(top2);
            ans+=top2;
        }
        return ans;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 6;
        int maxValue = 1000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            if (lessMoney1(arr) != lessMoney2(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
