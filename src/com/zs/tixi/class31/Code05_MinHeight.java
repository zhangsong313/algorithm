package com.zs.tixi.class31;

/*
 * 题目:
 *      给定一颗二叉树的头节点head
 *      求以head为头的树中,最小深度是多少?
 *      最小深度:从根节点开始到最近的叶子节点经过的节点数.(注意:对于有一个子树为空的节点,最小深度并不为1,因为自身并非叶子节点)
 *
 * 二叉树递归套路可以解决的题目,如果不是强制依赖左右树的信息整合,可以考虑morris遍历.
 *
 */
public class Code05_MinHeight {
    private static class Node{
        public Node left;
        public Node right;
        public int value;
        public Node(int val){
            this.value = val;
        }
    }

    /**
     * 思路:使用二叉树递归套路.
     *      来到某一节点.左子树高度为h1,右子树高度为h2
     *      当前节点高度为左右子树的最小高度+1
     *      为什么不对 ????
     */
    public static int minHeight1(Node head){
        if(head==null) return 0;
        return p(head);
    }
    private static int p(Node node){
        if (node.left == null && node.right == null) return 1;
        int lH = Integer.MAX_VALUE;
        if(node.left != null) lH = p(node.left);
        int rH = Integer.MAX_VALUE;
        if(node.right != null ) rH = p(node.right);
        return Math.min(lH, rH)+1;
    }

    /**
     * 使用morris遍历:
     * 每到一个节点,想办法得到当前节点的层数.
     *      判断当前节点是否为叶节点,如果是,与之前的答案相比,看能否比之前的答案小,如果小就更新答案.
     * 判断当前节点的层数有两种情况:
     *      0. 初始层数为1
     *      1. 当前节点是上一个节点的子节点,是上一个节点的左节点或右节点,
     *          判断依据:此时mr.right肯定不等于cur,
     *          结论:层数等于之前层数加1.
     *      2. 不是上一个节点的子节点,当前节点是从mr.right过来的,
     *          判断依据:此时mr.right等于cur,
     *          结论:层数更新为之前层数减去cur左树右边界的高度.
     * 最终层数就是答案.
     */
    public static int minHeight2(Node head){
        if (head == null) return 0;
        int lv = 0;
        Node cur = head;
        Node mr = null;
        int ans = Integer.MAX_VALUE;
        while (cur != null){
            if(cur.left != null){
                mr = cur.left;
                int mrNum = 1;
                while (mr.right != null && mr.right != cur){
                    mr = mr.right;
                    mrNum++;
                }
                if (mr.right == null){
                    mr.right = cur;
                    cur = cur.left;
                    // 当前节点为上一个节点的子节点.
                    lv += 1;
                    continue;
                } else {
                    if (mr.left == null) {
                        ans = Math.min(ans, lv);
                    }
                    mr.right = null;
                    // 当前节点是从mr.right滑过来的.
                    lv -= mrNum;
                }
            } else {
                // 当前节点为上一个节点的子节点.
                lv += 1;
            }
            cur = cur.right;
        }
        int mrNum = 1;
        cur = head;
        while (cur.right != null){
            mrNum++;
            cur = cur.right;
        }
        if(cur.left == null){
            ans = Math.min(ans, mrNum);
        }

        return ans;
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
        int treeLevel = 7;
        int nodeMaxValue = 5;
        int testTimes = 100000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(treeLevel, nodeMaxValue);
            int ans1 = minHeight1(head);
            int ans2 = minHeight2(head);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
                throw new RuntimeException("Oops");
            }
        }
        System.out.println("test finish!");

//        Node head = new Node(1);
//        head.left = new Node(2);
//        int ans1 = minHeight1(head);
//        int ans2 = minHeight2(head);
//        if (ans1 != ans2) {
//            System.out.println(ans1);
//            System.out.println(ans2);
//            throw new RuntimeException("Oops");
//        }

    }
}
