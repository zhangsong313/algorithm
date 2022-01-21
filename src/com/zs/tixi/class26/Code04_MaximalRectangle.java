package com.zs.tixi.class26;

/*
 * 题目四:
 *      给定一个二维数组matrix,其中的值不是0就是1,
 *      返回全部由1组成的最大子矩形,内部有多少个1.
 * 测试链接：https://leetcode.com/problems/maximal-rectangle/
 */
public class Code04_MaximalRectangle {
    /**
     * 思路:全部由1组成的最大子矩形的底必在0..map.length-1范围上.
     *      对于第i行,将当前行为底的高度值组成数组.把这个高度数组看做直方图,
     *      转化为了Code03_LargestRectangleInHistogram问题.
     *      可以求出当前行为底的所有子矩形的最大面积.
     *      遍历所有行,得到最终结果.
     *
     * map的长度为N,宽度为M
     * 定义一维数组height[M]用来收集当前行为底的直方图高度.
     * 定义ans用来收集答案.
     * 遍历数组map的行:i
     *      遍历当前行的所有列,更新height:j
     *          如果map[i][j] == 0,height[j]更新为0
     *          否则,height[j]更新为height[j]加1
     *      使用Code03_LargestRectangleInHistogram
     *      求解出height数组的最大直方图面积,并尝试更新ans
     * 返回ans.
     */
    public static int maximalRectangle(char[][] map) {
        int N = map.length;
        int M = map[0].length;
        int[] height = new int[M];
        int ans=0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                height[j] = map[i][j]=='0'?0:height[j]+1;
            }
            ans = Math.max(largestRectangleArea2(height), ans);
        }
        return ans;
    }

    /**
     * 从Code03_LargestRectangleInHistogram复制过来的代码
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
