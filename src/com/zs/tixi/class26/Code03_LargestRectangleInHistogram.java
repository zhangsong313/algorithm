package com.zs.tixi.class26;

import java.util.Stack;

/*
 * 题目三:
 *      给定一个非负数组arr,代表直方图,返回直方图的最大长方形面积.
 * https://leetcode.com/problems/largest-rectangle-in-histogram
 */
public class Code03_LargestRectangleInHistogram {

    /**
     * 思路:遍历arr,以当前位置做高的矩形最远可以向左右扩展多少.
     *
     * 定义ans收集答案.
     * 创建栈stack
     * 遍历arr:i
     *      循环:栈不为空.栈顶对应位置值大于当前值
     *          弹出栈顶pop.左侧比自身小的位置是此时栈顶位置,右侧比自身小的位置为此时i位置.
     *          pop做高的最大长方形面积:arr[pop]*(i-1-stack.peek())
     *          尝试更新ans
     *      循环:栈不为空 栈顶位置值等于当前值.
     *          弹出栈顶.
     *      当前位置压栈
     * 循环:栈不为空
     *      弹出栈顶元素pop,左侧比自身小的位置是当前栈顶位置,右侧比自身小的位置-1.
     *      pop做高的最大长方形面积:arr[pop]*(arr.length-1-stack.peek())
     *      尝试更新ans
     * 返回ans.
     */
    public static int largestRectangleArea1(int[] arr) {
        int ans = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (stack.size()>0 && arr[stack.peek()]>arr[i]){
                int pop = stack.pop();
                ans = Math.max(stack.isEmpty() ? arr[pop]*i : arr[pop]*(i-1-stack.peek()), ans);
            }
            while (stack.size()>0 && arr[stack.peek()]==arr[i]) stack.pop();
            stack.push(i);
        }
        while (stack.size()>0){
            int pop = stack.pop();
            ans = Math.max(stack.isEmpty()?arr[pop]*arr.length:arr[pop]*(arr.length-1-stack.peek()), ans);
        }
        return ans;
    }

    /**
     * 用固定长度的数组和栈顶指针实现栈.
     */
    public static int largestRectangleArea2(int[] arr) {
        int ans = 0;
        int[] stack = new int[arr.length];
        int s = -1; // 栈顶位置
        for (int i = 0; i < arr.length; i++) {
            while (s!=-1 && arr[stack[s]]>arr[i]){
                int pop = stack[s--];
                ans = Math.max(s==-1 ? arr[pop]*i : arr[pop]*(i-1-stack[s]), ans);
            }
            while (s!=-1 && arr[stack[s]]==arr[i]) s--;
            stack[++s] = i;
        }
        while (s!=-1){
            int pop = stack[s--];
            ans = Math.max(s==-1?arr[pop]*arr.length:arr[pop]*(arr.length-1-stack[s]), ans);
        }
        return ans;
    }
}
