package com.zs.tixi.class4;

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
        // head.next = prev;  实质是要对每个节点将next指针指向prev节点。

        // 因此需要变量prev记录上一节点。
        // 需要变量next记录下一节点，否则next指针修改后会找不到下一个位置。
        // 每次修改完next指针后，prev移动到当前位置，head移动到下一位置
        // 直到head为null，返回prev。
        Node prev = null;
        Node next = null;
        while (head!=null){
            next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }

    /**
     * 双向链表反转。
     * @param head 给定链表的头
     * @return 反转后链表头
     */
    public static Node reverseDoubleLinkedTable(Node head){
        // 核心操作：对每个节点进行： head.next = prev; head.prev = next;
        // 需要变量记录next节点。否则head.next指针调整后next节点会丢失.
        // 需要变量记录prev节点，否则head.prev指针调整后prev节点会丢失.
        // 每次修改完指针后: prev移动到head位置，head移动到next位置，next移动到next的下一位置.
        // 直到head为null，最后返回prev
        Node prev = null;
        Node next = null;
        while (head != null){
            next = head.next;
            head.next = prev;
            head.prev = next;
            prev = head;
            head = next;
        }
        return prev;
    }


    public static void main(String[] args) {
        TestUtil.checkReverseSingleLinkedTable(1000, 99, 99, Code01_LinkedTableReverse::reverseSingleLinkedTable);
        TestUtil.checkReverseDoubleLinkedTable(1000, 99, 99, Code01_LinkedTableReverse::reverseDoubleLinkedTable);
    }

}
