package com.zs.tixi.class14;

import java.util.ArrayList;

/*
 * 2.二叉树递归套路续：返回这颗二叉树中最大二叉搜索子树的头节点。
 */
public class Code02_MaxSubBSTHead {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 如果头节点为空，返回头节点。
     * 返回头节点是搜索树情况下的大小。
     * 返回左节点和右节点二叉搜索树的大小的较大结果。
     * @param head
     * @return
     */
    public static Node maxSubBSTHead1(Node head) {
        if (head == null) {
            return null;
        }
        if (getBSTSize(head) != 0) {
            return head;
        }
        Node leftAns = maxSubBSTHead1(head.left);
        Node rightAns = maxSubBSTHead1(head.right);
        return getBSTSize(leftAns) >= getBSTSize(rightAns) ? leftAns : rightAns;
    }
    // 如果是二叉搜索树，返回树的大小，否则返回0
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
    // 中序遍历收集二叉树所哟节点
    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    public static Node maxSubBSTHead2(Node head) {return process(head).maxBSTHead;}

    /**
     * 定义需要返回的信息：isBST, maxBSTSize, max, min,maxBSTHead
     * 如果head为空
     *      是否为二叉搜索树设置为是。
     *      最大二叉搜索树大小设置为0.
     *      最大二叉搜索树头节点设置为null
     *      返回info。
     * 递归收集左树信息leftInfo
     * 递归收集右树信息rightInfo
     * 如果左树为二叉搜索树 且 右树为二叉搜索树 且 左树的最大值<当前值<右树的最小值
     *      是否为二叉搜索树设置为是
     *      最大二叉搜索树大小设置为左树大小+右树大小+1
     *      最大值设置为右树最大值，
     *      最小值设置为左树最小值。
     *      最大二叉搜索树头节点设置为head
     *      返回
     * 是否二叉搜索树设置为否
     * 最大二叉搜索树大小设置为左树和右树最大二叉搜索子树大小的较大值。
     * 最小值设置为左树最小值，当前值，右树最小值中的最小
     * 最大值设置为左树最大值，当前值，右树最大值中的最大
     * 最大二叉搜索树头节点设置为左树和右树最大二叉搜索子树大小的较大节点的，最大二叉搜索子树的头节点
     *
     * @param head
     * @return
     */
    public static Info process(Node head){
        if(head == null) return new Info(true, 0, null);
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        boolean isBST;
        int maxBSTSize;
        Node maxBSTHead;
        int max;
        int min;
        if (leftInfo.isBST && rightInfo.isBST){
            if ((head.left==null || leftInfo.max<head.value)
            && (head.right==null || rightInfo.min>head.value)){
                isBST=true;
                maxBSTSize = leftInfo.maxBSTSize+rightInfo.maxBSTSize+1;
                max = head.right==null?head.value:rightInfo.max;
                min = head.left==null?head.value:leftInfo.min;
                maxBSTHead=head;
                return new Info(isBST, maxBSTSize, maxBSTHead, max, min);
            }
        }
        isBST = false;
        maxBSTSize = Math.max(leftInfo.maxBSTSize, rightInfo.maxBSTSize);
        maxBSTHead = leftInfo.maxBSTSize>=rightInfo.maxBSTSize?leftInfo.maxBSTHead:rightInfo.maxBSTHead;
        min = head.value;
        max = head.value;
        if (head.left!=null){
            min = Math.min(min, leftInfo.min);
            max = Math.max(max, leftInfo.max);
        }
        if (head.right!=null){
            min = Math.min(min, rightInfo.min);
            max = Math.max(max, rightInfo.max);
        }
        return new Info(isBST, maxBSTSize, maxBSTHead, max, min);
    }

    /**
     * 如果最大二叉搜索树包含当前节点，说明整棵树是二叉搜索树：
     *      左树为二叉搜索树，右树为二叉搜索树，且左树的最大值<当前值<右树的最小值
     *      返回当前节点。
     * 否则:
     *      返回左树和右树最大二叉搜索树大小的较大的那个，的最大二叉搜索树头节点。
     * 需要的信息：
     *  是否为二叉搜索树，最大值，最小值，最大二叉搜索子树大小，最大二叉搜索树的头节点。
     */
    public static class Info{
        public boolean isBST;
        public int maxBSTSize;
        public int max;
        public int min;
        public Node maxBSTHead;

        public Info(boolean isBST, int maxBSTSize, Node maxBSTHead) {
            this.isBST = isBST;
            this.maxBSTSize = maxBSTSize;
            this.maxBSTHead = maxBSTHead;
        }

        public Info(boolean isBST, int maxBSTSize, Node maxBSTHead, int max, int min) {
            this.isBST = isBST;
            this.maxBSTSize = maxBSTSize;
            this.max = max;
            this.min = min;
            this.maxBSTHead = maxBSTHead;
        }
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
            if (maxSubBSTHead1(head) != maxSubBSTHead2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }



}
