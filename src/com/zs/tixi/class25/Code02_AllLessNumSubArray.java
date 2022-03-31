package com.zs.tixi.class25;

import java.util.Deque;
import java.util.LinkedList;

/*
 * 题目二:
 *      给定一个整型数组arr,和一个整数num.
 *      某个arr中的子数组sub,如果想达标,必须满足:sub中最大值-sub中最小值<=num,
 *      返回arr中达标子数组的数量.
 *
 * 注意：这道题的代码思路比较简单。但代码比较难写。
 */
public class Code02_AllLessNumSubArray {
    /**
     * 题目分析:要求子数组的最值差在一定范围内,随着子数组范围扩大,最值差也在扩大.
     *      以i位置开头,结尾扩大到不能再扩,停,这个范围内的子数组都是符合条件的子数组.
     *
     * 定义滑动窗口的两个指针L=0,R=0
     * 定义滑动窗口的两个队列,分别记录窗口内的最大值和最小值.
     * 定义需要返回的答案ans
     * 循环：L<arr.length
     *      循环:R<arr.length
     *          两个滑动窗口队列更新，将R位置数追加到队列。
     *          如果窗口最值差已经超过sum，break。
     *          否则，R++
     *      ans+=R-L
     *      L++;
     *      两个滑动窗口队列更新，将L位置数弹出队列
     * 返回ans.
     */
    public static int num(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }
        int L=0, R=0;
        Deque<Integer> dqMax = new LinkedList<>();
        Deque<Integer> dqMin = new LinkedList<>();
        dqMax.add(arr[0]);
        dqMin.add(arr[0]);
        int ans = 1;
        while (L<arr.length){
            while (R<arr.length){
                while (dqMax.size()>0 && arr[dqMax.peekLast()]<=arr[R]){
                    dqMax.pollLast();
                }
                dqMax.addLast(R);
                while (dqMin.size()>0 && arr[dqMin.peekLast()]>=arr[R]){
                    dqMin.pollLast();
                }
                dqMin.addLast(R);
                if(arr[dqMax.peekFirst()]-arr[dqMin.peekFirst()]>sum) break;
                R++;
            }

            ans+=R-L;

            L++;
            if(dqMax.peekFirst()<L) dqMax.pollFirst();
            if(dqMin.peekFirst()<L) dqMin.pollFirst();
        }
        return ans;
    }

    // 暴力的对数器方法
    public static int right(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }
        int N = arr.length;
        int count = 0;
        for (int L = 0; L < N; L++) {
            for (int R = L; R < N; R++) {
                int max = arr[L];
                int min = arr[L];
                for (int i = L + 1; i <= R; i++) {
                    max = Math.max(max, arr[i]);
                    min = Math.min(min, arr[i]);
                }
                if (max - min <= sum) {
                    count++;
                }
            }
        }
        return count;
    }

    // for test
    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 500;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int sum = (int) (Math.random() * (maxValue + 1));
            int ans1 = right(arr, sum);
            int ans2 = num(arr, sum);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(sum);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
