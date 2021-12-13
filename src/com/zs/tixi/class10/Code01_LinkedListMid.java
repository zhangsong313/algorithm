package com.zs.tixi.class10;

import java.util.ArrayList;

/*
 * 7. 快慢指针练习
 *      1）输入链表头节点，奇数长度返回中点，偶数长度返回上中点。
 *      2）输入链表头节点，奇数长度返回中点，偶数返回下中点。
 *      3）输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个。
 *      4）输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个。
 */
public class Code01_LinkedListMid {
    public static class Node{
        public Node next;
        public int value;
        public Node(int value){
            this.value = value;
        }
    }

    /**
     * 1）输入链表头节点，奇数长度返回中点，偶数长度返回上中点。
     * 如果head==null，长度为0，返回null
     * 如果head.next==null， 长度为1，返回head
     *
     * 定义pQuick=head为快指针, pSlow=head为慢指针。
     * 循环(true)
     *      如果pQuick.next为空
     *          返回pSlow
     *      如果pQuick.next.next为空
     *          返回pSlow.next
     *      pQuick = pQuice.next
     *      pSlow = pSlow.next
     * @return
     */
    public static Node midOrUpMidNode(Node head){
//        if (head == null) return null;
//        if (head.next == null) return head;
//        Node pQuick = head;
//        Node pSlow = head;
//        while(true){
//            if (pQuick.next == null) return pSlow;
//            if (pQuick.next.next == null) return pSlow.next;
//            pQuick = pQuick.next.next;
//            pSlow=pSlow.next;
//        }
        if (head == null){
            return head;
        }
        Node pQuick = head;
        Node pSlow = head;
        while (pQuick.next !=null // 链表长度为奇数
                && pQuick.next.next !=null){ // 链表长度为偶数
            pQuick = pQuick.next.next;
            pSlow = pSlow.next;
        }
        return pSlow;
    }

    /**
     * 2）输入链表头节点，奇数长度返回中点，偶数返回下中点。
     * @return
     */
    public static Node midOrDownMidNode(Node head){
        if (head == null) return head;
        Node pQuick = head;
        Node pSlow = head;
        while (true){
            if (pQuick.next==null){ // 链表长度为奇数
                return pSlow;
            }
            if (pQuick.next.next == null){ // 链表长度为偶数
                return pSlow.next;
            }
            pQuick = pQuick.next.next;
            pSlow = pSlow.next;
        }
    }

    /**
     * 3）输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个。
     */
    public static Node midOrUpMidPreNode(Node head){
        if (head == null) return head;
        Node pQuick = head;
        Node pSlow = head;
        Node preMid = null;
        while (true){
            if (pQuick.next==null) return preMid; // 链表长度为奇数
            if (pQuick.next.next == null) return preMid; // 链表长度为偶数
            pQuick = pQuick.next.next;
            preMid = pSlow;
            pSlow = pSlow.next;

        }
    }

    /**
     * 4）输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个。
     */
    public static Node midOrDownMidPreNode(Node head){
        if (head == null) return head;
        Node pQuick = head;
        Node pSlow = head;
        Node preMid = null;
        while (true){
            if (pQuick.next==null) return preMid; // 链表长度为奇数
            if (pQuick.next.next == null) return pSlow; // 链表长度为偶数
            pQuick = pQuick.next.next;
            preMid = pSlow;
            pSlow = pSlow.next;
        }
    }


    public static Node right1(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 1) / 2);
    }

    public static Node right2(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get(arr.size() / 2);
    }

    public static Node right3(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 3) / 2);
    }

    public static Node right4(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 2) / 2);
    }

    public static void main(String[] args) {
        Node test = null;
        test = new Node(0);
        test.next = new Node(1);
        test.next.next = new Node(2);
        test.next.next.next = new Node(3);
        test.next.next.next.next = new Node(4);
        test.next.next.next.next.next = new Node(5);
        test.next.next.next.next.next.next = new Node(6);
        test.next.next.next.next.next.next.next = new Node(7);
        test.next.next.next.next.next.next.next.next = new Node(8);

        Node ans1 = null;
        Node ans2 = null;

        ans1 = midOrUpMidNode(test);
        ans2 = right1(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

        ans1 = midOrDownMidNode(test);
        ans2 = right2(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

        ans1 = midOrUpMidPreNode(test);
        ans2 = right3(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

        ans1 = midOrDownMidPreNode(test);
        ans2 = right4(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");
    }
}
