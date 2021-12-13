package com.zs.tixi.class10;

import java.util.ArrayList;
import java.util.List;

/*
 *      2）将单向链表按某值划分成左边小中间相等，右边大的形式。
 *          将链表放入数组，对数组做partition（笔试用）
 *          分成小，中，大三部分，再把各个部分串起来。（面试用）
 */
public class Code03_SmallerEqualBigger {
    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 定义动态数组list。
     * 将链表从head节点开始依次放入数组。
     * 在数组中根据node.value对list做partition。
     * 遍历list。转化为链表返回头节点
     * @param head
     * @param pivot
     * @return
     */
    public static Node listPartition1(Node head, int pivot) {
        if (head == null ) return null;
        if (head.next == null) return head;

        List<Node> list = new ArrayList<>();
        while (head != null){
            list.add(head);
            head = head.next;
        }

        int smaller = -1;
        int bigger = list.size();
        int currIndex=0;
        while (currIndex!=bigger){
            if (list.get(currIndex).value < pivot){
                smaller++;
                swap(list, smaller, currIndex);
                currIndex++;
            } else if (list.get(currIndex).value == pivot){
                currIndex++;
            } else {
                bigger--;
                swap(list, currIndex, bigger);
            }
        }

        Node curr = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            curr.next = list.get(i);
            curr = list.get(i);
        }
        curr.next = null;
        return list.get(0);
    }

    /**
     * 定义六个变量:
     *      smalHead, smalTail
     *      equalsHead, equalsTail
     *      bigHead, bigTail
     * 从头开始遍历链表
     *      当前节点currNode
     *      如果currNode的值小于pivot
     *          smalTail.next = currNode
     *          smalTail = currNode
     *      如果currNode的值等于pivot
     *          equalsTail.next = currNode
     *          equalsTail = currNode
     *      如果currNode的值大于pivot
     *          bigTail.next = currNode
     *          bigTail = currNode
     *
     * 如果小于区不为空
     *      小于区尾巴连等于区的头
     *      如果等于区为空
     *          等于区尾巴更新为小于区尾巴.
     * 如果等于区不为空
     *      等于区尾巴连大于区的头
     *
     * 返回小于区，等于区，大于区，第一个不为空的头。
     *
     * @param head
     * @param pivot
     * @return
     */
    public static Node listPartition2(Node head, int pivot) {
        Node sH=null, sT=null, eH=null, eT=null, bH=null, bT=null;
        while (head!=null){
            if (head.value<pivot){
                if(sH!=null){
                    sT.next = head;
                    sT = head;
                } else {
                    sH = sT = head;
                }
            }
            if (head.value==pivot){
                if (eH!=null){
                    eT.next=head;
                    eT = head;
                } else{
                    eH = eT = head;
                }
            }
            if (head.value>pivot){
                if (bH!= null){
                    bT.next = head;
                    bT = head;
                } else {
                    bH = bT= head;
                }
            }

            head = head.next;
//            Node next = head.next;
//            head.next = null;
//            head = next;
        }
        if (sH!= null){
            sT.next = eH;
            eT = eT==null?sT:eT;
        }
        if (eH!=null){
            eT.next = bH;
        }

        // 如果在分区过程中没有对tail的next设置为null。需要将合并后的链表尾部设置为null
        Node tail = bT!=null? bT : eT!=null?eT:sT;
        if (tail!=null) tail.next = null;

        return sH != null ? sH : ( eH != null ? eH : bH );
    }


    /**
     * 交换list中两个位置的数据
     * @param list
     */
    private static void swap(List<Node> list, int i1, int i2) {
        Node n1 = list.get(i1);
        Node n2 = list.get(i2);
        list.set(i1, n2);
        list.set(i2, n1);
    }

    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head1 = new Node(7);
        head1.next = new Node(9);
        head1.next.next = new Node(1);
        head1.next.next.next = new Node(8);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(2);
        head1.next.next.next.next.next.next = new Node(5);
        printLinkedList(head1);
//         head1 = listPartition1(head1, 5);
        head1 = listPartition2(head1, 5);
        printLinkedList(head1);

    }
}
