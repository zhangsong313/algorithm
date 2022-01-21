package com.zs.tixi.class26;

/*
 * 题目五:
 *      给定一个二维数组matrix,其中的值不是0就是1.
 *      返回全部由1组成的子矩形数量.
 * 测试链接：https://leetcode.com/problems/count-submatrices-with-all-ones
 */
public class Code05_CountSubmatricesWithAllOnes {
    /**
     * 思路:每一个子矩形的底必在0..mat.length-1范围上.
     *      当前来到第i行:求以当前行为底的子矩形数量.
     *      将当前行为底的高度数组视作直方图,问题转化为求直方图中有多少子矩形.
     *      将所有行做底的子矩形数量累加起来得到结果
     *
     * mat有N行,M列
     * 定义height[M]数组用来保存当前行的直方图数据.
     * 定义ans收集答案.
     * 遍历mat所有行:i
     *      遍历当前行的所有列:j
     *          如果mat[i][j]==0,height[j]=0
     *          否则,height[j]+=1
     *      对height数组调用getRectNum函数得到当前行做底的直方图数量.
     *      ans累加上上面的结果.
     * 返回ans
     *
     */
    public static int numSubmat(int[][] mat) {
        int N = mat.length;
        int M = mat[0].length;
        int[] height = new int[M];
        int ans = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                height[j] = mat[i][j]==0?0:height[j]+1;
            }
            ans += getRectNum(height);
        }
        return ans;
    }

    /**
     * 返回直方图中的子矩形数量(必须包含底部)
     *
     * 定义栈stack
     * 定义ans收集答案.
     * 遍历height:i
     *      循环:栈不为空,栈顶元素值大于当前值
     *          弹出栈顶元素pop,左边比自身小的位置在当前栈顶位置,右边比自身小的位置为i.
     *          以pop位置做高的矩形内部有多少个子矩形:
     *              当前矩形的宽度为n,横向的可能性有n*(n+1)/2中
     *              当前矩形的高为m,左右两边比自身小的最高高度为m2,纵向的可能性有m-m2
     *              有多少个子矩形:(m-m2)*(n*(n-1)/2)
     *          ans累加上面的结果.
     *      循环:栈不为空,栈顶位置值等于当前值.
     *          弹出栈顶元素.
     *      当前位置压栈
     * 循环:栈不为空
     *      弹出栈顶元素pop,左边比自身小的位置为此时的栈顶元素,右边比自身小的位置为-1
     *      以pop位置做高的矩形内部有多少个子矩形:(m-m2)*(n*(n-1)/2)
     *      ans累加上面的结果
     * 返回ans
     */
    private static int getRectNum(int[] height){
        int[] stack = new int[height.length];
        int s = -1;
        int ans = 0;
        for (int i = 0; i < height.length; i++) {
            while (s!=-1 && height[stack[s]]>height[i]){
                int pop = stack[s--];
                ans += s==-1 ? (height[pop]-height[i])*(i*(i+1)/2) :
                        (height[pop]-Math.max(height[stack[s]], height[i]))*((i-1-stack[s])*(i-stack[s])/2);
            }
            while (s!=-1 && height[stack[s]]==height[i]) s--;
            stack[++s] = i;
        }
        while (s!=-1){
            int pop = stack[s--];
            ans += s==-1 ? height[pop]*(height.length*(height.length+1)/2):
                    (height[pop]-height[stack[s]])*((height.length-1-stack[s])*(height.length-stack[s])/2);
        }
        return ans;
    }
}
