package com.zs.tixi.class26;

import java.util.Stack;

/*
 * 题目二:
 *      给定一个只包含正数的数组arr,arr中的任何一个子数组sub.
 *      一定都可以算出(sub累加和)*(sub中的最小值)是什么,
 *      那么所有子数组中,这个值最大是多少?
 */
public class Code02_AllTimesMinToMax {

    public static int max1(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int minNum = Integer.MAX_VALUE;
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += arr[k];
                    minNum = Math.min(minNum, arr[k]);
                }
                max = Math.max(max, minNum * sum);
            }
        }
        return max;
    }

    /**
     * 子数组范围越大,累加和越大.但最小值却会变小.
     * 思路:对每个位置,求当前位置作为最小值的情况下,子数组范围最远能扩多大.
     * 遍历当前子数组,依赖单调栈求出每个位置左右最近的比自身小的位置.
     * 可以得到每个位置作为最小值情况下的最好结果.
     * 所有最好结果比较一遍求出最大值.
     *
     * 遍历数组arr生成前缀和数组preArr,方便获取区间累加和.
     * 定义ans用来收集答案.
     * 创建一个栈stack.
     * 遍历数组arr:i
     *      循环:栈不为空 ,且栈顶位置的值大于当前位置的值
     *          弹出栈顶元素pop,pop左边比自己小的数在栈顶位置left,右边比自己小的数为当前位置right
     *          根据preArr获得left+1..right-1范围上的累加和.preArr[right-1]-preArr[left]
     *          尝试更新ans.
     *      循环:栈不为空,且栈顶位置的值等于当前位置.
     *          弹出栈顶元素
     *      当前位置压栈.
     * 循环:栈不为空
     *      弹出栈顶元素pop.左边比自己小的位置在栈顶位置left,右边比自己小的位置是-1.
     *      根据preArr获得范围上累加和:preArr[arr.length-1]-preArr[left]
     *      尝试更新ans.
     * 返回ans
     */
    public static int max2(int[] arr) {
        int[] preArr = new int[arr.length];
        preArr[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            preArr[i] = arr[i]+preArr[i-1];
        }

        int ans = 0;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < arr.length; i++) {
            while (stack.size()>0 && arr[stack.peek()]>arr[i]){
                Integer pop = stack.pop();
                if(stack.isEmpty()){
                    ans = Math.max(preArr[i-1]*arr[pop], ans);
                } else {
                    ans = Math.max((preArr[i-1]-preArr[stack.peek()])*arr[pop], ans);
                }
            }
            while (stack.size()>0 && arr[stack.peek()]==arr[i]){
                stack.pop();
            }
            stack.push(i);
        }
        while (stack.size()>0){
            Integer pop = stack.pop();
            if(stack.isEmpty()){
                ans = Math.max(preArr[arr.length-1]*arr[pop], ans);
            } else {
                ans = Math.max((preArr[arr.length-1]-preArr[stack.peek()])*arr[pop], ans);
            }
        }
        return ans;
    }

    public static int[] gerenareRondomArray() {
        int[] arr = new int[(int) (Math.random() * 20) + 10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 101);
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTimes = 2000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = gerenareRondomArray();
            if (max1(arr) != max2(arr)) {
                System.out.println("FUCK!");
                break;
            }
        }
        System.out.println("test finish");
    }
}
