package com.zs.tixi.class4;

import java.util.ArrayList;
import java.util.List;

/**
 * 链表反转
 */
public class Code01_LinkedTableReverse {
    /**
     * 单链表反转
     * @param head 给定链表的头
     * @return 反转后链表头
     */
    public static Node reverseSingleLinkedTable2(Node head){
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

    public static Node reverseDoubleLinkedTable2(Node head){
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
        checkReverseSingleLinkedTable();
        checkReverseDoubleLinkedTable();
    }

    /**
     * 测试：双链表反转
     */
    private static void checkReverseDoubleLinkedTable() {
        int maxLen = 10;
        for (int i = 0; i < 1000; i++) {
            int len = (int)(Math.random()*(maxLen+1));
            Node head = randomDoubleLinkedList(len);

            List<Integer> list = new ArrayList<>();
            getLinkedList(head, list);
            System.out.println("list : " + list);

            Node head2 = reverseDoubleLinkedTable2(head);

            List<Integer> list2 = new ArrayList<>();
            getLinkedList(head2, list2);
            System.out.println("list2 : " + list2);

            checkListReverseData(list, list2);
        }
    }

    private static Node randomDoubleLinkedList(int len) {
        if(len==0) return null;
        Node cur = new Node();
        cur.data = (int)(Math.random()*99);
        cur.next = randomDoubleLinkedList(len-1);
        if(cur.next!=null) cur.next.prev = cur;
        return cur;
    }

    /**
     * 测试：单链表反转
     */
    private static void checkReverseSingleLinkedTable(){
        int maxLen = 10;
        for (int i = 0; i < 1000; i++) {
            int len = (int)(Math.random()*(maxLen+1));
            Node head = randomSingleLinkedList(len);

//            printSingleLinkedList(head);
            List<Integer> list = new ArrayList<>();
            getLinkedList(head, list);
            System.out.println("list : " + list);

            Node head2 = reverseDoubleLinkedTable(head);
//            printSingleLinkedList(head2);

            List<Integer> list2 = new ArrayList<>();
            getLinkedList(head2, list2);
            System.out.println("list2 : " + list2);

            checkListReverseData(list, list2);
        }
    }

    private static void checkListReverseData(List<Integer> list, List<Integer> list2){
        if(list.size()!=list2.size()) throw new RuntimeException("list size not equals.");
        for (int i = 0; i < list.size(); i++) {
            if(!list.get(i).equals(list2.get(list.size()-1-i))){
                throw new RuntimeException("list not reverse equals.. : index : "+i);
            }
        }
    }

    private static void printSingleLinkedList(Node head) {
        if(head==null){
            System.out.println("null ");
            return;
        }
        System.out.print(head.data+" -> ");
        printSingleLinkedList(head.next);
    }

    private static void getLinkedList(Node head, List<Integer> list) {
        if(head==null){
            return;
        }
        list.add(head.data);
        getLinkedList(head.next, list);
    }

    private static Node randomSingleLinkedList(int len){
        if(len==0) return null;
        Node cur = new Node();
        cur.data = (int)(Math.random()*99);
        cur.next = randomSingleLinkedList(len-1);
        return cur;
    }
}
