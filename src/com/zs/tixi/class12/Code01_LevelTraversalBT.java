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
     * 定义endNode = head。表示第一层的末尾节点是head。
     * 遍历queue：
     *      从queue中取出一个节点curr。
     *      如果
     *
     *
     * @param head
     */
    public static void level(Node head){
        Queue<Node> nodes = new LinkedList<>();
//        nodes.

    }
}
