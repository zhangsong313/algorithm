package com.zs.tixi.class13;

import java.util.ArrayList;

/*
 * 6. 二叉树的递归套路深度实践：返回一颗二叉树中最大的二叉搜索子树的大小。
 */
public class Code05_MaxSubBSTSize {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 如果head为空，返回0.
     * 返回以head为头的二叉搜索树大小，如果不等于0，直接返回结果。
     * 返回左树和右树中最大的二叉搜索树大小。
     *
     * 复杂度说明：每个节点都要遍历一次当前节点为头的二叉树。时间复杂度为O(n^2)
     * @param head
     * @return
     */
    public static int maxSubBSTSize1(Node head) {
        if (head == null) {
            return 0;
        }
        int h = getBSTSize(head);
        if (h != 0) {
            return h;
        }
        return Math.max(maxSubBSTSize1(head.left), maxSubBSTSize1(head.right));
    }

    /**
     * 如果是二叉搜索树，返回树大小，否则返回0
     *
     * 如果head为空，返回null。
     * 创建一个集合arr。
     * 使用中序遍历收集所有节点到arr。
     * 遍历arr，如果当前节点值小于等于前一个节点值，说明当前树不是二叉搜索树，返回0.
     *
     * 返回arr的大小。
     * @param head
     * @return
     */
    public static int getBSTSize(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return 0;
            }
        }
        return arr.size();
    }

    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    /**
     * 返回最大二叉搜索子树的大小。
     *
     * 如果head
     * @param head
     * @return
     */
    public static int maxSubBSTSize2(Node head) {
        return process(head).maxBSTSize;
    }

    /**
     * 创建当前节点信息对象info。
     * 如果当前节点为空，
     *      info设置最大二叉搜索子树大小为0，
     *      info设置当前树是二叉搜索树
     *      返回info
     * 递归查询左右子树的信息
     * 如果左树和右树都是二叉搜索树，
     *      如果左树不为空 或 左树的最大值小于当前值
     *      且
     *      如果右树不为空 或 右树的最小值大于当前值。
     *          info设置当前树是二叉搜索树，
     *          info设置最大二叉搜索子树的大小为左树大小加右树大小加1.
     *          info设置最小值为当前值。
     *          如果左树不为空，
     *              更新最小值为左树最小值。
     *          info设置最大值为当前值。
     *          如果右树不为空：
     *              更新最大值为右树最大值。
     *          返回info
     *
     * info设置当前树不是二叉搜索树。
     * info设置最大搜索子树的大小为左树和右树的最大搜索子树大小的较大值。
     * info设置最小值为当前值。
     * info设置最大值为当前值。
     *
     * 如果左树不为空，
     *     更新最小值为当前值与左树最小值的较小。
     *     更新最大值为当前值与左树最大值的较大。
     * 如果右树不为空，
     *     更新最小值为当前值与右树最小值的较小。
     *     更新最大值为当前值与右树最大值的较大。
     * 返回info。
     * @param head
     * @return
     */
    public static Info process(Node head){
        Info info  = new Info();
        if (head == null){
            info.maxBSTSize = 0;
            info.isBST = true;
            return info;
        };
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        if (leftInfo.isBST && rightInfo.isBST){
            if (
                    (head.left==null || leftInfo.max<head.value)
                &&
                    (head.right==null || rightInfo.min>head.value)
            ){
                info.isBST = true;
                info.maxBSTSize = leftInfo.maxBSTSize+rightInfo.maxBSTSize+1;
                info.min = head.value;
                if (head.left!=null) info.min = leftInfo.min;
                info.max = head.value;
                if (head.right!=null) info.max = rightInfo.max;
                return info;
            }
        }
        info.isBST = false;
        info.maxBSTSize = Math.max(leftInfo.maxBSTSize, rightInfo.maxBSTSize);
        info.min = head.value;
        info.max = head.value;
        if (head.left!=null){
            info.min = Math.min(info.min, leftInfo.min);
            info.max = Math.max(info.max, leftInfo.max);
        }
        if (head.right!=null){
            info.min = Math.min(info.min, rightInfo.min);
            info.max = Math.max(info.max, rightInfo.max);
        }
        return info;
    }

    public static class Info{
        public int maxBSTSize; // 最大搜索子树大小.
        public boolean isBST; // 当前树是否为二叉搜索树。
        public int max; // 最大值
        public int min; // 最小值
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxSubBSTSize1(head) != maxSubBSTSize2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
