package com.zs.tixi.class13;

/*
 * 5. 二叉树的递归套路深度实践：判断一棵树是否是满二叉树。
 *      Full Binary Tree FBT : 一个二叉树，如果每一个层的节点数都达到最大值，这个二叉树是满二叉树。
 *      如果二叉树的高度^2 -1为节点数。说明是满二叉树。
 */
public class Code04_IsFull {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }
    /**
     * 如果二叉树的高度^2 -1为节点数。说明是满二叉树。
     * @param head
     * @return
     */
    public static boolean isFull1(Node head) {
        if (head == null) {
            return true;
        }
        int height = h(head);
        int nodes = n(head);
        return (1 << height) - 1 == nodes;
    }

    public static int h(Node head) {
        if (head == null) {
            return 0;
        }
        return Math.max(h(head.left), h(head.right)) + 1;
    }

    public static int n(Node head) {
        if (head == null) {
            return 0;
        }
        return n(head.left) + n(head.right) + 1;
    }


    /**
     * 如果head为空，返回true。
     * 递归调用：左节点不满或右节点不满，返回false。
     * 如果左树和右树只有一个为空：返回false。
     * 如果左树和右树只有一个有非空子树，返回false。
     *
     * 返回true。
     * @param head
     * @return
     */
    public static boolean isFull2(Node head){
        if (head == null) return true;
        if (!isFull2(head.left) || !isFull2(head.right)) return false;
        if (head.left==null ^ head.right == null ) return false;
        if (hasChild(head.left) ^ hasChild(head.right) ) return false;
        return true;
    }

    /**
     * 判断当前二叉树是否有孩子.
     * @param head
     * @return
     */
    private static boolean hasChild(Node head) {
        if (head== null) return false;
        if (head.left != null || head.right!=null) return true;
        return false;
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
            if (isFull1(head) != isFull2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
