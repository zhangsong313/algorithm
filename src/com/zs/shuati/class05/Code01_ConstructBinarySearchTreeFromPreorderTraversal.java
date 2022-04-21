package com.zs.shuati.class05;

import java.util.Stack;

/**
 * 1 已知一棵搜索二叉树上没有重复值的节点，现在有一个数组arr，
 * 是这棵搜索二叉树先序遍历的结果，请根据arr生成整棵树并返回头节点
 */
public class Code01_ConstructBinarySearchTreeFromPreorderTraversal {
    // 不用提交这个类
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // 提交下面的方法
    public static TreeNode bstFromPreorder1(int[] pre) {
        if (pre == null || pre.length == 0) {
            return null;
        }
        return process1(pre, 0, pre.length - 1);
    }

    /**
     * 请将l..r范围内先序遍历结果构建出二叉树，返回头节点。
     * 如果l>r返回空
     * l位置作为头节点
     * 找到左树范围和右树范围的边界点：
     * l右边第一个比l大的位置。
     * 递归调用左树范围和右树范围，得到左树头节点和右树头节点.
     */
    private static TreeNode process1(int[] pre, int l, int r) {
        if(l>r) return null;
        TreeNode head = new TreeNode(pre[l]);
        int nearGreater = -1; // 右边比l位置大的最近的位置。

        for (int i = l+1; i <= r; i++) { // 是否能找到比l位置大的数。
            if(pre[i]>pre[l]) {
                nearGreater = i;
                break;
            }
        }

        if(nearGreater!=-1){ // 找到最近比l位置大的数。
            head.left = process1(pre, l+1, nearGreater-1); // 设置左树
            head.right = process1(pre, nearGreater, r); // 设置右树
        }else { // 没找到
            head.left = process1(pre, l+1, r); // 全部设置为左树
        }

        return head;
    }

    /**
     * 使用单调栈，时间复杂度达到O(N)
     */
    public static TreeNode bstFromPreorder2(int[] pre) {
        if (pre == null || pre.length == 0) {
            return null;
        }

        Stack<Integer> stack = new Stack<>(); // 单调栈提前查询出每个位置右边大的位置。
        int[] nearBig = new int[pre.length];
        for (int i = 0; i < pre.length; i++) {
            while (!stack.isEmpty() && pre[i]>pre[stack.peek()]){
                Integer pop = stack.pop();
                nearBig[pop] = i;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()){
            Integer pop = stack.pop();
            nearBig[pop] = -1;
        }

        return process2(pre, 0, pre.length - 1, nearBig);
    }


    private static TreeNode process2(int[] pre, int l, int r, int[] nearBig) {
        if(l>r) return null;
        TreeNode head = new TreeNode(pre[l]);
        int nearGreater = nearBig[l];

        if(nearGreater!=-1){ // 找到最近比l位置大的数。
            head.left = process1(pre, l+1, nearGreater-1); // 设置左树
            head.right = process1(pre, nearGreater, r); // 设置右树
        }else { // 没找到
            head.left = process1(pre, l+1, r); // 全部设置为左树
        }

        return head;
    }

    /**
     * 使用单调栈，时间复杂度达到O(N)
     * 使用数组代替栈，进一步优化。
     */
    public static TreeNode bstFromPreorder3(int[] pre) {
        if (pre == null || pre.length == 0) {
            return null;
        }

        int size = 0; // 栈大小。
        int[] stack = new int[pre.length]; // 单调栈提前查询出每个位置右边大的位置。
        int[] nearBig = new int[pre.length];
        for (int i = 0; i < pre.length; i++) {
            while (size != 0 && pre[i]>pre[stack[size-1]]){
                Integer pop = stack[--size];
                nearBig[pop] = i;
            }
            stack[size++] = i;
        }
        while (size != 0){
            Integer pop = stack[--size];
            nearBig[pop] = -1;
        }

        return process2(pre, 0, pre.length - 1, nearBig);
    }
}
