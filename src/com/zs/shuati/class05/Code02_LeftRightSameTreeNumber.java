package com.zs.shuati.class05;

/**
 * 2 如果一个节点X，它左树结构和右树结构完全一样，
 * 那么我们说以X为头的树是相等树，给定一棵二叉树的头节点head，返回head整棵树上有多少棵相等子树
 */
public class Code02_LeftRightSameTreeNumber {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    private static class Info{
        public int sameCount;
    }

    // 时间复杂度O(N * logN)

    /**
     * 递归：
     * 左右树分别返回相等子树的数量，然后比较左子树和右子树是否完全相同。
     */
    public static int sameNumber1(Node head) {
//        return process1(head);
    }
}
