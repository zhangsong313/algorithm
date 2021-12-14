package com.zs.tixi.class11;

/*
 * 2. 二叉树的先序，中序，后序遍历.递归方式实现
 */
public class Code02_RecursiveTraversalBT {
    public static class Node{
        public int value;
        public Node left;
        public Node right;
        public Node(int value){
            this.value = value;
        }
    }

    /**
     * 二叉树的递归
     * 遇到空节点返回。
     * 像遍历当前节点一样遍历左子树。
     * 像遍历当前节点一样遍历右子树。
     * @param node
     */
    public static void f(Node node){
        if (node == null) return;

        f(node.left);

        f(node.right);
    }

    /**
     * 二叉树的先序遍历：
     * 先打印自身，然后遍历左右子树
     * @param node
     */
    public static void pre(Node node){
        if (node == null) return;
        System.out.println(node.value);
        pre(node.left);
        pre(node.right);
    }

    /**
     * 二叉树的中序遍历：
     * 先遍历左子树
     * 打印自身
     * 遍历右子树
     * @param node
     */
    public static void in(Node node){
        if (node == null) return;
        in(node.left);
        System.out.println(node.value);
        in(node.right);
    }

    /**
     * 二叉树的后序遍历：
     * 先遍历左右子树。
     * 打印自身
     * @param node
     */
    public static void last(Node node){
        if (node == null) return;
        last(node.left);
        last(node.right);
        System.out.println(node.value);
    }

}
