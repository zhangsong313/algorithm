package com.zs.tixi.class10;

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
        public int val;
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
    public static Node findMid(Node head){
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
        while (pQuick.next !=null && pQuick.next.next !=null){
            pQuick = pQuick.next.next;
            pSlow = pSlow.next;
        }
        return pSlow;
    }

    /**
     * 2）输入链表头节点，奇数长度返回中点，偶数返回下中点。
     * @return
     */
    public static Node findMidAndDownMid(){
        return null;
    }


}
