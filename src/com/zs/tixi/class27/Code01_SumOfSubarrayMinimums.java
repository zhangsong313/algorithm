package com.zs.tixi.class27;

/*
 * 题目一:单调栈续
 *      给定一个数组arr.
 *      返回所有子数组最小值的累加和.
 * 测试链接：https://leetcode.com/problems/sum-of-subarray-minimums/
 *
 * 注意:遇到题目提醒结果可能超出系统最大值的情况下,一定要用long类型收集答案,答案计算过程中也要转换为long.....太坑了.
 */
public class Code01_SumOfSubarrayMinimums {

    /**
     * 思路:当前来到i位置,以arr[i]为最小值的子数组向左到left,向右到right
     *      以arr[i]做最小值的子数组个数:
     *          起点范围:left+1..i
     *          终点:i..right-1
     *          个数:(i-left)*(right-i)
     *      以arr[i]做最小值的子数组最小值累加和:arr[i]*(i-left)*(right-i)
     *      每个位置答案累加得到结果
     * 考虑相等位置的处理:...
     *
     * 定义ans收集答案.
     * 创建栈stack
     * 遍历arr:i
     *      循环:栈不为空,栈顶位置值大于等于当前位置
     *          弹出栈顶元素pop,左侧小于自身的位置为此时栈顶的值,右侧小于等于自身的位置为i.
     *          以arr[pop]做最小值的子数组最小值累加和:arr[pop]*(pop-stack.peek())(i-pop)
     *          ans累加上面的答案.
     *      当前位置i压栈
     * 循环:栈不为空
     *      弹出栈顶元素pop,左侧小于自身的位置为此时栈顶的值,右侧小于自身的位置为-1.
     *      以arr[pop]做最小值的答案:arr[pop]*(pop-stack.peek())*(arr.length-pop)
     *      ans累加上面的答案
     *
     */
    public static int sumSubarrayMins(int[] arr) {
        long ans = 0;
        int[] stack = new int[arr.length];
        int s = -1;
        for (int i = 0; i < arr.length; i++) {
            while (s != -1 && arr[stack[s]]>=arr[i]){
                int pop = stack[s--];
                ans += s==-1?(long)arr[pop]*(pop+1)*(i-pop) : (long)arr[pop]*(pop-stack[s])*(i-pop);
                ans %= 1000000007;
            }
            stack[++s] = i;
        }
        while (s!=-1){
            int pop = stack[s--];
            ans += s==-1 ? (long)arr[pop]*(pop+1)*(arr.length-pop) : (long)arr[pop]*(pop-stack[s])*(arr.length-pop);
            ans %= 1000000007;
        }
        return (int)ans;
    }

    public static void main(String[] args) {
        int [] test = new int[]{3,1,2,4};

        System.out.println(sumSubarrayMins(test));
    }
}
