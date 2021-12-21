package com.zs.tixi.class14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/*
 * 3.二叉树递归套路续：给定二叉树的头节点head和另外两个节点a，b。返回a和b的最低公共祖先。
 */
public class Code03_lowestAncestor {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 收集二叉树中每个节点->父节点的map
     * 从o1开始，向上收集所有祖先节点o1Set。
     * 从o2开始，向上找，返回第一个在o1Set中的节点o3
     * o3为两节点的最低公共祖先。
     * @param head
     * @param o1
     * @param o2
     * @return
     */
    public static Node lowestAncestor1(Node head, Node o1, Node o2) {
        if (head == null) {
            return null;
        }
        // key的父节点是value
        HashMap<Node, Node> parentMap = new HashMap<>();
        parentMap.put(head, null);
        fillParentMap(head, parentMap);
        HashSet<Node> o1Set = new HashSet<>();
        Node cur = o1;
        o1Set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            o1Set.add(cur);
        }
        cur = o2;
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }
        return cur;
    }
    public static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }

    public static Node lowestAncestor2(Node head, Node o1, Node o2) {
        return process(head, o1, o2).lowestAnc;
    }

    /**
     * 如果head为空
     *      info设置没有找到最低公共祖先，设置没有a，设置没有b，直接返回
     * 递归收集左树信息leftInfo
     * 递归收集右树信息rightInfo
     * 如果左树已经找到最低公共祖先，直接返回。
     * 如果右树已经找到最低公共祖先，直接返回。
     * a节点：如果左树有a，或右树有a，或当前节点为a，则整颗树有a。
     * b节点：如果左树或右树有b，或当前节点为b，则整棵树有b。
     * 如果既有a又有b，说明当前节点为最低公共祖先。直接返回
     * 当前树没有找到最低公共祖先。
     *
     * @param head
     * @return
     */
    public static Info process(Node head, Node nodeA, Node nodeB){
        if (head==null) return new Info(null, false, false);
        Info leftInfo = process(head.left, nodeA, nodeB);
        Info rightInfo = process(head.right, nodeA, nodeB);

        if (leftInfo.lowestAnc!=null) return new Info(leftInfo.lowestAnc, false, false);
        if (rightInfo.lowestAnc!=null) return new Info(rightInfo.lowestAnc, false, false);

        boolean hasA = leftInfo.hasA || rightInfo.hasA || head == nodeA;
        boolean hasB = leftInfo.hasB || rightInfo.hasB || head == nodeB;
        if (hasA && hasB) return new Info(head, true, true);

        return new Info(null, hasA, hasB);
    }
    /**
     * 如果左右树已经找到了ab最低公共祖先，直接返回。
     * 如果左树有a，右树有b。或，左树有b，右树有a。说明当前节点为最低公共祖先。
     *      返回
     * a节点：如果左树有a，或右树有a，或当前节点为a，则整颗树有a。
     * b节点：如果左树或右树有b，或当前节点为b，则整棵树有b。
     * 当前树没有找到最低公共祖先。
     */
    public static class Info{
        public Node lowestAnc;
        public boolean hasA;
        public boolean hasB;

        public Info(Node lowestAnc, boolean hasA, boolean hasB) {
            this.lowestAnc = lowestAnc;
            this.hasA = hasA;
            this.hasB = hasB;
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

    // for test
    public static Node pickRandomOne(Node head) {
        if (head == null) {
            return null;
        }
        ArrayList<Node> arr = new ArrayList<>();
        fillPrelist(head, arr);
        int randomIndex = (int) (Math.random() * arr.size());
        return arr.get(randomIndex);
    }

    // for test
    public static void fillPrelist(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Node o1 = pickRandomOne(head);
            Node o2 = pickRandomOne(head);
            if (lowestAncestor1(head, o1, o2) != lowestAncestor2(head, o1, o2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
