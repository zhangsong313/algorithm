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
     * @param head
     * @return
     */
    public static boolean isFull2(Node head){
        return process(head).isFull;
    }

    /**
     * 如果左树为满二叉树，右树为满二叉树，且左树高度等于右树高度。则当前树为满二叉树。
     * @param head
     * @return
     */
    public static Info process(Node head){
        if (head == null) return new Info(true, 0);
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        int height = Math.max(leftInfo.height, rightInfo.height)+1;
        return new Info(isFull, height);
    }

    public static class Info {
        public boolean isFull;
        public int height;
        public Info(boolean isFull, int height){
            this.isFull = isFull;
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
            if (isFull1(head) != isFull2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
