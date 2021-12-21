package com.zs.tixi.class14;

import java.util.Queue;

import java.util.LinkedList;

/*
 * 1.二叉树递归套路续：判断二叉树是否是完全二叉树。
 */
public class Code01_IsCBT {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 按层遍历二叉树
     *
     * 如果遇到了左子树为空，右子树不为空，返回false。
     * 如果已经遇到过子树不双全的节点，后续节点还有子树，返回false。
     *
     * 如果head为空返回true。
     * 创建队列queue，加入head。
     * 定义hasIncomplateNode=false,表示是否遇到过子节点不双全的节点。
     * 遍历queue：
     *      从queue中取出节点curr。
     *      如果左子树为空，右子树不为空
     *          返回false
     *      如果（左子树不为空或右子树不为空）且 hasIncomplateNode为true。
     *          返回false
     *      如果左子树不为空
     *          左子树加入queue
     *      如果右子树不为空
     *          右子树加入queue
     *      如果左子树为空，或右子树为空
     *          hasIncomplateNode=true
     * 返回true
     * @param head
     * @return
     */
    public static boolean isCBT1(Node head){
        if(head==null) return true;
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        boolean hasInComplateNode = false;
        while (queue.size()>0){
            Node curr = queue.poll();
            if(curr.left==null && curr.right!= null) return false;
            if (hasInComplateNode && (curr.left!=null || curr.right!=null)) return false;
            if (curr.left!=null) queue.add(curr.left);
            if (curr.right!=null) queue.add(curr.right);
            if(curr.left==null || curr.right==null) hasInComplateNode = true;
        }
        return true;
    }

    public static boolean isCBT2(Node head){
        return process(head).isCBT;
    }
    /**
     * 如果head为空
     * 		设置info是满二叉树，是完全二叉树，高度为0
     * 		返回info。
     * 递归返回左树信息leftInfo。
     * 递归返回右树信息rightInfo。
     * 设置info高度为左树和右树高度中的较大值加1.
     * 设置info是否为满二叉树：
     * 		左树是满 且 右树是满 且 左树高度等于右树高度
     * 设置info不是完全二叉树。
     * 如果左树满 且 右树满 且 左树高度等于右树高度：
     * 		设置info是完全二叉树
     * 如果左树是完全二叉树 且 右树是满二叉树 且 左树高度等于右树高度加1
     * 		设置info是完全二叉树
     * 如果左树满 且 右树满 且 左树高度等于右树高度加1
     * 		设置info是完全二叉树
     * 如果左树满 且 右树是完全二叉树 且左树高度等于右树高度。
     * 		设置info是完全二叉树。
     * 返回info
     * @param head
     * @return
     */
    public static Info process(Node head){
        if (head == null) return new Info(true, true, 0);
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int height = Math.max(leftInfo.height, rightInfo.height)+1;
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height==rightInfo.height;
        boolean isCBT = false;
        if (isFull)isCBT = true;
        if (leftInfo.isCBT && rightInfo.isFull && leftInfo.height==rightInfo.height+1) isCBT = true;
        if (leftInfo.isFull && rightInfo.isFull && leftInfo.height==rightInfo.height+1) isCBT = true;
        if (leftInfo.isFull && rightInfo.isCBT && leftInfo.height==rightInfo.height) isCBT = true;
        return new Info(isFull, isCBT, height);
    }
    public static class Info{
        public boolean isFull;
        public boolean isCBT;
        public int height;

        public Info(boolean isFull, boolean isCBT, int height) {
            this.isFull = isFull;
            this.isCBT = isCBT;
            this.height = height;
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
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isCBT1(head) != isCBT2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
