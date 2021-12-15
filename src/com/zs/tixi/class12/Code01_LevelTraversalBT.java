package com.zs.tixi.class12;

import java.util.LinkedList;
import java.util.Queue;

/*
 * 1. 实现二叉树的按层遍历。
 *      1）其实就是宽度优先遍历，使用队列。
 */
public class Code01_LevelTraversalBT {

    public static class Node{
        public int value;
        public Node left;
        public Node right;
        public Node(int value){
            this.value = value;
        }
    }

    /**
     * 按层遍历二叉树。
     * 将head放入队列queue中。
     * 定义endNode = head。表示当前层的末尾节点。
     * 定义变量nextEndNode=null.表示下一层末尾节点。
     * 遍历queue：
     *      从queue中取出一个节点curr。
     *      如果curr有左节点
     *          左节点入队列
     *          更新curr.left到nextEndNode
     *      如果curr有右节点
     *          右节点入队列
     *          更新curr.right到nextEndNode
     *      如果curr等于endNode。
     *          更新nextEndNode到endNode。
     *
     * @param head
     */
    public static void level(Node head){
        if (head == null) return;

        Queue<Node> nodes = new LinkedList<>();
        nodes.add(head);
        Node endNode = head;
        Node nextEndNode = null;
        while (!nodes.isEmpty()){
            Node curr = nodes.poll();
            System.out.print(curr.value+" ");
            if (curr.left != null){
                nodes.add(curr.left);
                nextEndNode = curr.left;
            }
            if (curr.right != null){
                nodes.add(curr.right);
                nextEndNode = curr.right;
            }
            if (curr == endNode){
                endNode = nextEndNode;
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        level(head);
        System.out.println("========");
    }
}
