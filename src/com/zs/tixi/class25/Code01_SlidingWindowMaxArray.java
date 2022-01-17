package com.zs.tixi.class25;

import java.util.Deque;
import java.util.LinkedList;

/*
 * 题目一:
 *      假设一个固定大小为W的窗口,依次划过arr.
 *      返回每一次滑动状况的最大值
 *      例如:arr={4, 3, 5, 4, 3, 3, 6, 7} W=3
 *      返回:[5, 5, 5, 4, 6, 7]
 */
public class Code01_SlidingWindowMaxArray {

    /**
     * 创建双端队列dq
     * 定义返回的数组res,大小为arr.length-w+1
     * [0..w-1]范围上从队尾添加数据下标位置..
     *      先把双端队列中队尾小于等于当前数的所有下标弹出.
     *      将当前下标加入队尾.
     * 从dq的头部取出[0..w-1]范围的最大值放入res.
     * [w..arr.length-1]范围上从队尾添加数据下标位置..
     *      如果头部下标是当前下标-w,从头部弹出一个下标.
     *      先把双端队列中队尾小于等于当前位置的所有下标弹出.
     *      将当前下标加入队尾.
     *
     *      从头部取出当前最大值的下标,把对应的最大值放入res中.
     *
     * 返回res.
     */
    public static int[] getMaxWindow(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        Deque<Integer> dq = new LinkedList<>();
        int[] res = new int[arr.length-w+1];
        for (int i = 0; i < w; i++) {
            while (dq.size()>0 && arr[dq.peekLast()]<=arr[i]){
                dq.pollLast();
            }
            dq.addLast(i);
        }
        int index = 0;
        res[index++] = arr[dq.peekFirst()];
        for (int i = w; i < arr.length; i++) {
            if(dq.peekFirst()==i-w) dq.pollFirst();
            while (dq.size()>0 && arr[dq.peekLast()]<=arr[i]){
                dq.pollLast();
            }
            dq.addLast(i);
            res[index++] = arr[dq.peekFirst()];
        }
        return res;
    }

    // 暴力的对数器方法
    public static int[] right(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        int N = arr.length;
        int[] res = new int[N - w + 1];
        int index = 0;
        int L = 0;
        int R = w - 1;
        while (R < N) {
            int max = arr[L];
            for (int i = L + 1; i <= R; i++) {
                max = Math.max(max, arr[i]);

            }
            res[index++] = max;
            L++;
            R++;
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = getMaxWindow(arr, w);
            int[] ans2 = right(arr, w);
            if (!isEqual(ans1, ans2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
