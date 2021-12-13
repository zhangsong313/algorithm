package com.zs.tixi.class10;

/*
 *      3）一种特殊的单链表节点类描述如下：
 *          class Node{ int val; Node next; Node rand;}
 *          rand指针是单链表节点中新增的指针，可能指向链表中的任意节点，也可能指向Null。
 *          给定一个由Node节点组成的无环单链表的头节点Head，请完成一个函数实现这个链表的复制，并返回复制新链表的头节点。
 *          要求：时间复杂度O(N)，额外空间复杂度O(1)
 */
public class Code04_CopyListWithRandom {
    public static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public static Node copyRandomList1(Node head) {

        return null;
    }
}
