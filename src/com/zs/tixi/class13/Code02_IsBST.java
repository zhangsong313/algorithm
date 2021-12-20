package com.zs.tixi.class13;

import java.util.ArrayList;
import java.util.List;

/*
 * 3. 二叉树的递归套路深度实践：判断一棵树是否是搜索二叉树。
 *      Binary Search Tree 二叉搜索树：
 *      所有节点满足：左子树的所有节点比当前节点小，右子树的所有节点比当前节点大。
 */
public class Code02_IsBST {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 按中序顺序整理所有的节点值。如果从左到右不是递增的话，返回false。
     * 创建一个集合对象list用来保存中序遍历的所有节点。
     *
     * 使用中序遍历的函数in得到中序遍历的节点集合。
     * 遍历集合：
     *      如果当前数小于等于前一个数，返回false。
     * 返回true。
     * @param head
     * @return
     */
    public static boolean isBST1(Node head) {
        List<Node> list = new ArrayList<>();
        in(head, list);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).value <= list.get(i-1).value) return false;
        }
        return true;
    }

    /**
     * 按照先序遍历收集节点。
     * @param head
     * @return
     */
    private static List<Node> in(Node head, List<Node> inNodeList) {
        if (head==null) return inNodeList;
        in(head.left, inNodeList);
        inNodeList.add(head);
        in(head.right, inNodeList);
        return inNodeList;
    }

    /**
     * 如果当前节点为空，返回true。
     * 否则调用process查询当前树是否是二叉搜索树。
     * @param head
     * @return
     */
    public static boolean isBST2(Node head) {
        if (head == null) return true;
        return process(head).isBST;
    }

    /**
     * 返回当前树的最大值，最小值，是否二叉搜索数。
     * 由上层调用保证当前树不为空
     *
     * 左树，右树返回最大值，最小值，是否二叉搜索树。
     * 定义变量info包含min,max,isBST。表示当前树的最大值，最小值，是否二叉搜索树。
     *
     * 如果左树不是二叉搜索树或右树不是二叉搜素树
     *      设置isBST为false，返回。
     * 设置isBST为true。
     *
     * 设置min=head.value
     * 设置max=head.value
     * 如果左树不为空
     *      比较当前节点值是否大于左树最大值。不是设置isBST为false，返回.
     *      设置min = left.min
     * 如果右树不为空
     *      比较当前节点是否小于右树最小值。不是设置isBST为false，返回.
     *      设置max = right.max
     *
     * 返回true。
     * @param head
     * @return
     */
    private static Info process(Node head) {
        Info leftInfo = null;
        if (head.left!=null){
            leftInfo = process(head.left);
        } else {
            leftInfo = new Info();
            leftInfo.isBST = true;
        }
        Info rightInfo = null;
        if (head.right!=null){
            rightInfo = process(head.right);
        } else {
            rightInfo = new Info();
            rightInfo.isBST= true;
        }

        Info ans = new Info();
        if (!leftInfo.isBST || !rightInfo.isBST){
            ans.isBST = false;
            return ans;
        }
        ans.isBST=true;
        ans.min = head.value;
        ans.max = head.value;
        if (head.left!=null){
            if (head.value<=leftInfo.max){
                ans.isBST=false;
                return ans;
            }
            ans.min = leftInfo.min;
        }

        if (head.right!=null) {
            if (head.value>=rightInfo.min){
                ans.isBST = false;
                return ans;
            }
            ans.max = rightInfo.max;
        }
        return ans;
    }


    private static class Info{
        public boolean isBST;
        public int min;
        public int max;
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
            if (isBST1(head) != isBST2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
