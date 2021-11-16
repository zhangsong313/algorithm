package com.zs.tixi.class4;

import com.zs.tixi.class4.Node;

/**
 * 链表反转
 */
public class Code01_LinkedTableReverse {
    /**
     * 单链表反转
     * @param head 给定链表的头
     * @return 反转后链表头
     */
    public static Node reverseSingleLinkedTable(Node head){
        Node prev = null;
        Node next = head; // prev为前值，head和next为当前值，当前值的next指针待调整。
        while (head != null){
            next = head.next; // 移动next到head.next
            head.next = prev; // 核心：将head的next指向prev
            prev = head; // step 1:移动prev到head
            head = next; // step 2:移动head到next
        }
        return prev;
    }

    /**
     * 双向链表反转。
     * @param head 给定链表的头
     * @return 反转后链表头
     */
    public static Node reverseDoubleLinkedTable(Node head){
        Node prev = null;
        Node next = null;
        while (head != null){
            next = head.next; // 移动next到head.next
            // 核心：调整head的prev和next;
            head.next = prev;
            head.prev = next;
            prev = head; // step 1:移动prev到head。
            head = next; // step 2:移动head到next.
        }
        return prev;
    }


    public static void main(String[] args) {

    }
}
