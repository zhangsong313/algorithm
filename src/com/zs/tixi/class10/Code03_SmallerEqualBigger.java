package com.zs.tixi.class10;

import com.zs.xiaobai.common.MyCompValue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

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
        // 将链表节点依次放入list中。
        // 定义小于区域指针和大于区域指针。根据pivot进行分区。
        // 将list中的节点串起成链表。注意最后一个节点的next需要设置为null。
        // 返回链表头节点
        if(head==null) return null;
        List<Node> list = new ArrayList<>();
        while (head != null){
            list.add(head);
            head = head.next;
        }

        int lessArea = -1;
        int bigArea = list.size();
        int cur = 0;
        while (cur<bigArea){
            if(list.get(cur).value < pivot){
                swap(list, cur++, ++lessArea);
            }else if(list.get(cur).value == pivot){
                cur++;
            }else {
                swap(list, cur, --bigArea);
            }
        }

        Node node = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            node.next = list.get(i);
            node = node.next;
        }
        node.next = null;
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
        // 定义smallHead, smallTail, equalsHead, equalsTail, bigHead, bigTail用来表示小于区域链表，等于区域链表，大于区域链表。
        // 通过与pivot相比找到对应区域的尾指针tail，tail.next指向当前节点。tail更新为当前节点。
        // 将三条链表串起来，这步是难点。(定义head和tail。先合并小于区域，再合并等于区域，再合并大于区域，最后设置tail.next为null)
        if(head==null) return null;
        Node smallHead=null, smallTail=null, equalsHead=null, equalsTail=null, bigHead=null, bigTail=null;
        while (head!=null){
            if(head.value < pivot){
                if(smallHead!=null){
                    smallTail.next = head;
                    smallTail = head;
                }else {
                    smallHead = smallTail = head;
                }
            }else if(head.value == pivot){
                if(equalsHead!=null){
                    equalsTail.next = head;
                    equalsTail = head;
                }else {
                    equalsHead = equalsTail = head;
                }
            }else {
                if(bigHead!=null){
                    bigTail.next = head;
                    bigTail = head;
                }else {
                    bigHead = bigTail = head;
                }
            }

            head = head.next;
        }

        head = null;
        Node tail = null;

        if(smallTail!=null){
            head = smallHead;
            tail = smallTail;
        }
        if(equalsTail!=null){
            if(head!=null){
                tail.next = equalsHead;
                tail = equalsTail;
            }else {
                head = equalsHead;
                tail = equalsTail;
            }
        }
        if(bigHead!=null){
            if(head!=null){
                tail.next = bigHead;
                tail = bigTail;
            }else {
                head = bigHead;
                tail = bigTail;
            }
        }

        tail.next = null;

        return head;
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
        checkListPartition(1000, 99, 99, Code03_SmallerEqualBigger::listPartition1);
        checkListPartition(1000, 99, 99, Code03_SmallerEqualBigger::listPartition2);
//        Node head1 = new Node(7);
//        head1.next = new Node(9);
//        head1.next.next = new Node(1);
//        head1.next.next.next = new Node(8);
//        head1.next.next.next.next = new Node(5);
//        head1.next.next.next.next.next = new Node(2);
//        head1.next.next.next.next.next.next = new Node(5);
//        printLinkedList(head1);
////         head1 = listPartition1(head1, 5);
//        head1 = listPartition2(head1, 5);
//        printLinkedList(head1);

    }

    private static void checkListPartition(int times, int maxLen, int maxVal, BiFunction<Node, Integer, Node> fun){
        final int small = 1;
        final int equals = 2;
        final int big = 3;
        MyCompValue.times(times, ()->{
            int len = (int)(Math.random()*(maxLen+1));
            Node head = randomLinkedList(len, maxVal);
            int pivot = (int)(Math.random()*(maxVal+1));
            Node newHead = fun.apply(head, pivot);
            if(len != getListLen(newHead)) {
                throw new RuntimeException("error");
            }

            int pre = small;
            while (newHead!=null){
                if(newHead.value<pivot){
                    if(small<pre) throw new RuntimeException("error");
                }else if(newHead.value==pivot){
                    if(equals<pre) throw new RuntimeException("error");
                    pre = equals;
                }else {
                    if(big<pre) throw new RuntimeException("error");
                    pre = big;
                }
                newHead = newHead.next;
            }
        });
    }

    private static int getListLen(Node head){
        if(head == null) return 0;
        return getListLen(head.next)+1;
    }

    private static Node randomLinkedList(int len, int maxVal){
        if(len==0) return null;
        int val = (int)(Math.random()*(maxVal+1));
        Node node = new Node(val);
        node.next = randomLinkedList(len-1, maxVal);
        return node;
    }
}
