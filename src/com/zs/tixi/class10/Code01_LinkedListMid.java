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
        if (head == null){
            return head;
        }
        // 定义快慢指针fast和slow指向head
        // 快指针每次走两步，慢指针每次走一步。快慢指针的位置组合规律为[1, 1], [2, 3], [3, 5], [4, 7]
        // 可以看出规律：快指针每次走在奇数位置上，且慢指针位置为快指针结尾的链表中点。
        // 循环：如果fast.next==null，说明链表长度为奇数。
        //      如果fast.next.next==null，说明链表长度为偶数。
        //      根据题目要求,不论长度奇数还是偶数，都返回slow
        //      fast移动两步，slow移动一步
        Node fast = head;
        Node slow = head;
        while (true){
            if(fast.next ==null // 链表长度为奇数
                    || fast.next.next ==null) {// 链表长度为偶数
                return slow;
            }
            fast = fast.next.next;
            slow = slow.next;
        }
    }

    /**
     * 2）输入链表头节点，奇数长度返回中点，偶数返回下中点。
     * @return
     */
    public static Node midOrDownMidNode(Node head){
        // 循环：如果fast.next==null，说明链表长度为奇数。
        //      如果fast.next.next==null，说明链表长度为偶数。
        //      根据题目要求,奇数返回slow，偶数返回slow.next
        //      fast移动两步，slow移动一步
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
        // 循环：如果fast.next==null，说明链表长度为奇数。
        //      如果fast.next.next==null，说明链表长度为偶数。
        //      根据题目要求,奇数或偶数都返回slow的前一个节点
        //      fast移动两步，slow移动一步
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
        // 循环：如果fast.next==null，说明链表长度为奇数。
        //      如果fast.next.next==null，说明链表长度为偶数。
        //      根据题目要求,奇数返回slow的前一个节点，偶数返回slow
        //      fast移动两步，slow移动一步
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
//        Node test = null;
//        test = new Node(0);
//        test.next = new Node(1);
//        test.next.next = new Node(2);
//        test.next.next.next = new Node(3);
//        test.next.next.next.next = new Node(4);
//        test.next.next.next.next.next = new Node(5);
//        test.next.next.next.next.next.next = new Node(6);
//        test.next.next.next.next.next.next.next = new Node(7);
//        test.next.next.next.next.next.next.next.next = new Node(8);
//
//        Node ans1 = null;
//        Node ans2 = null;
//
//        ans1 = midOrUpMidNode(test);
//        ans2 = right1(test);
//        System.out.println(ans1 != null ? ans1.value : "无");
//        System.out.println(ans2 != null ? ans2.value : "无");
//
//        ans1 = midOrDownMidNode(test);
//        ans2 = right2(test);
//        System.out.println(ans1 != null ? ans1.value : "无");
//        System.out.println(ans2 != null ? ans2.value : "无");
//
//        ans1 = midOrUpMidPreNode(test);
//        ans2 = right3(test);
//        System.out.println(ans1 != null ? ans1.value : "无");
//        System.out.println(ans2 != null ? ans2.value : "无");
//
//        ans1 = midOrDownMidPreNode(test);
//        ans2 = right4(test);
//        System.out.println(ans1 != null ? ans1.value : "无");
//        System.out.println(ans2 != null ? ans2.value : "无");

        for (int i = 0; i < 1000; i++) {
            int len = (int)(Math.random()*100);
            Node head = createSingleLinkedList(len, 1);
            Node node = midOrUpMidNode(head);
            if(len==0){
                if (node!=null) throw new RuntimeException("error 1");
            }else if(len%2==0) {
                if(node.value!=len/2) throw new RuntimeException("error 2");
            }else if(len%2!=0) {
                if(node.value!=len/2+1) throw new RuntimeException("error 3");
            }

            Node node2 = midOrDownMidNode(head);
            if(len==0){
                if (node2!=null) throw new RuntimeException("error 21");
            }else if(len%2==0) {
                if(node2.value!=len/2+1) throw new RuntimeException("error 22");
            }else if(len%2!=0) {
                if(node2.value!=len/2+1) throw new RuntimeException("error 23");
            }

            Node node3 = midOrUpMidPreNode(head);
            if(len==0 || len==1 || len==2){
                if (node3!=null) throw new RuntimeException("error 31");
            }else if(len%2==0) {
                if(node3.value!=len/2-1) throw new RuntimeException("error 32: "+len+" "+node3.value);
            }else if(len%2!=0) {
                if(node3.value!=len/2) throw new RuntimeException("error 33: "+len+" "+node3.value);
            }

            Node node4 = midOrDownMidPreNode(head);
            if(len==0 || len==1){
                if (node4!=null) throw new RuntimeException("error 41");
            }else if(len%2==0) {
                if(node4.value!=len/2) throw new RuntimeException("error 42: "+len+" "+node4.value);
            }else if(len%2!=0) {
                if(node4.value!=len/2) throw new RuntimeException("error 43: "+len+" "+node4.value);
            }
        }
    }

    private static Node createSingleLinkedList(int len, int val){
        if(len==0) return null;
        Node cur = new Node(val);
        cur.next = createSingleLinkedList(len-1, val+1);
        return cur;
    }
}
