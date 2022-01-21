package com.zs.tixi.class26;

import java.util.LinkedList;
import java.util.Stack;

/*
 * 题目一:
 *      单调栈的实现
 */
public class Code01_MonotonousStack {
    /**
     * 创建一个栈stack.栈中下标对应的数从底到顶应该是从小到大的.
     * 遍历arr:i
     *      循环:比较arr[i]和栈顶位置的数,如果小于栈顶数:
     *          栈顶数弹出,左边最小为此时栈顶的下标,右边最小为当前位置i.
     *      当前下标压栈
     * 循环:从栈中弹出数.左边最小为此时栈顶的下标,右边没有最小.
     */
    public static int[][] getNearLessNoRepeat(int[] arr) {
        int[][] ans = new int[arr.length][2];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (stack.size()>0 && arr[i]<arr[stack.peek()]){
                Integer pop = stack.pop();
                ans[pop][0] = stack.isEmpty()?-1:stack.peek();
                ans[pop][1] = i;
            }
            stack.push(i);
        }
        while (stack.size()>0){
            Integer pop = stack.pop();
            ans[pop][0] = stack.isEmpty()?-1:stack.peek();
            ans[pop][1] = -1;
        }
        return ans;
    }

    /**
     * 创建一个栈stack,内部装整型链表结构.
     * 遍历arr:i
     *      循环:栈不为空,且arr[i]<栈顶位置的数.
     *          弹出栈顶位置.遍历栈顶链表:
     *              左边最小为此时栈顶链表末尾的位置,右边最小是i.
     *      如果栈顶下标的数等于当前数,当前数放在栈顶链表末尾.
     *      否则.当前数生成新链表压栈.
     * 循环:栈不为空
     *      弹出栈顶链表,遍历链表:
     *            左边最小为此时栈顶链表末尾的位置,右边最小是-1
     */
    public static int[][] getNearLess(int[] arr) {
        int[][] ans = new int[arr.length][2];
        Stack<LinkedList<Integer>> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (stack.size()>0 && arr[i]<arr[stack.peek().getLast()]){
                LinkedList<Integer> pop = stack.pop();
                for (Integer popI : pop) {
                    ans[popI][0] = stack.isEmpty()?-1:stack.peek().getLast();
                    ans[popI][1] = i;
                }
            }
            if(stack.size()>0 && arr[stack.peek().getLast()]==arr[i]){
                stack.peek().add(i);
            } else {
                LinkedList<Integer> addList = new LinkedList<>();
                addList.add(i);
                stack.push(addList);
            }
        }
        while (stack.size()>0){
            LinkedList<Integer> pop = stack.pop();
            for (Integer popI : pop) {
                ans[popI][0] = stack.isEmpty()?-1:stack.peek().getLast();
                ans[popI][1] = -1;
            }
        }
        return ans;
    }


    // for test
    public static int[] getRandomArrayNoRepeat(int size) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < arr.length; i++) {
            int swapIndex = (int) (Math.random() * arr.length);
            int tmp = arr[swapIndex];
            arr[swapIndex] = arr[i];
            arr[i] = tmp;
        }
        return arr;
    }

    // for test
    public static int[] getRandomArray(int size, int max) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }

    // for test
    public static int[][] rightWay(int[] arr) {
        int[][] res = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            int leftLessIndex = -1;
            int rightLessIndex = -1;
            int cur = i - 1;
            while (cur >= 0) {
                if (arr[cur] < arr[i]) {
                    leftLessIndex = cur;
                    break;
                }
                cur--;
            }
            cur = i + 1;
            while (cur < arr.length) {
                if (arr[cur] < arr[i]) {
                    rightLessIndex = cur;
                    break;
                }
                cur++;
            }
            res[i][0] = leftLessIndex;
            res[i][1] = rightLessIndex;
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[][] res1, int[][] res2) {
        if (res1.length != res2.length) {
            return false;
        }
        for (int i = 0; i < res1.length; i++) {
            if (res1[i][0] != res2[i][0] || res1[i][1] != res2[i][1]) {
                return false;
            }
        }

        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int size = 10;
        int max = 20;
        int testTimes = 2000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = getRandomArrayNoRepeat(size);
            int[] arr2 = getRandomArray(size, max);
            if (!isEqual(getNearLessNoRepeat(arr1), rightWay(arr1))) {
                System.out.println("Oops!");
                printArray(arr1);
                break;
            }
            if (!isEqual(getNearLess(arr2), rightWay(arr2))) {
                System.out.println("Oops!");
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
