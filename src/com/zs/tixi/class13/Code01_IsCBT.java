package com.zs.tixi.class13;

import java.util.Queue;

import java.util.LinkedList;

/*
 * 1. 判断二叉树是否是完全二叉树（不需要使用二叉树的递归套路）
 * Complate Binary Tree
 * 完全二叉树：二叉树每层从左到右，如果遇到空节点，则该层后序节点一定都为空，且该层为二叉树最后一层。
 */
public class Code01_IsCBT {

    public static class Node {
        public int value;
        public Node left;
        public Node right;
        public Node(int value){
            this.value = value;
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
    public static boolean isCBT(Node head){
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
}
