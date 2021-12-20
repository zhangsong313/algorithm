package com.zs.tixi.class13;
/*
 * 4. 二叉树的递归套路深度实践：判断一棵树是否是平衡二叉树。
 *      Balance Tree BT: 任意节点的子树高度差都小于等于1.
 */
public class Code03_IsBalanced {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static boolean isBalanced1(Node head) {
        boolean[] ans = new boolean[1];
        ans[0] = true;
        process1(head, ans);
        return ans[0];
    }

    /**
     * 如果ans为false或head为空，返回-1。
     * 得到左子树递归的结果leftHeight。
     * 得到右子树递归的结果rightHeight.
     * 如果左树高度-右树高度绝对值大于1.
     * 		ans为false
     * 返回左树高度和右树高度的较大值加1.
     *
     * @param head
     * @param ans
     * @return
     */
    public static int process1(Node head, boolean[] ans) {
        if (!ans[0] || head == null) {
            return -1;
        }
        int leftHeight = process1(head.left, ans);
        int rightHeight = process1(head.right, ans);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            ans[0] = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }


    public static boolean isBalanced2(Node head) {
        return process(head).isBalanced;
    }

    /**
     * 定义当前节点需要返回的信息：info
     * 如果head为空
     *      设置info的isBalanced为true。
     *      设置info的height为0.
     *      返回info
     * 递归调用得到左树的信息leftInfo
     * 递归调用得到右树的信息rightInfo
     * 如果左树不平衡或右树不平衡
     *      设置info不平衡
     *      返回info
     * 如果左树高度和右树高度差的绝对值大于1
     *      返回info不平衡
     * 设置info平衡
     * 设置info的高度为左树高度和右树高度的最大值加1.
     * 返回info
     * @param head
     * @return
     */
    private static Info process(Node head) {
        Info info = new Info(false, -1);
        if (head==null){
            info.isBalanced = true;
            info.height = 0;
            return info;
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        if (!leftInfo.isBalanced
                || !rightInfo.isBalanced
        || Math.abs(leftInfo.height-rightInfo.height) > 1){
            info.isBalanced = false;
            return info;
        }
        info.isBalanced = true;
        info.height = Math.max(leftInfo.height, rightInfo.height)+1;
        return info;
    }

    public static class Info{
        public boolean isBalanced;
        public int height;

        public Info(boolean i, int h) {
            isBalanced = i;
            height = h;
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
            if (isBalanced1(head) != isBalanced2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
