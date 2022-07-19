package com.zs.tixi.class4;

import com.zs.xiaobai.common.MyCompValue;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    /**
     * 测试：单链表反转
     */
    public static void checkReverseSingleLinkedTable(int times, int maxLen, int maxVal, OneArgReturnFun<Node, Node> fun){
        MyCompValue.times(times, ()->{
            int len = (int)(Math.random()*(maxLen+1));
            Node head = randomSingleLinkedList(len, maxVal);

            List<Integer> list = new ArrayList<>();
            getLinkedList(head, list);

            Node head2 = fun.apply(head);

            List<Integer> list2 = new ArrayList<>();
            getLinkedList(head2, list2);

            checkListReverseData(list, list2);
        });
    }

    /**
     * 测试：双链表反转
     */
    public static void checkReverseDoubleLinkedTable(int times, int maxLen, int maxVal, OneArgReturnFun<Node, Node> fun){
        MyCompValue.times(times, ()->{
            int len = (int)(Math.random()*(maxLen+1));
            Node head = randomDoubleLinkedList(len, maxVal);

            List<Integer> list = new ArrayList<>();
            getLinkedList(head, list);

            Node head2 = fun.apply(head);

            List<Integer> list2 = new ArrayList<>();
            getLinkedList(head2, list2);

            checkListReverseData(list, list2);
            checkDoubleLinkedList(head2);
        });
    }

    /**
     * 测试：删除链表上指定值
     */
    public static void checkRemoveGiveData(int times, int maxLen, int maxVal,TwoArgReturnFun<Node, Integer, Node> fun){
        MyCompValue.times(times, ()->{
            int len = (int)(Math.random()*(maxLen+1));
            Node head = randomSingleLinkedList(len, maxVal);

            int removeData = (int)(Math.random()*(maxVal+1));
            Node ans = fun.apply(head, removeData);

            checkDataRemoved(head, ans, removeData);
        });
    }

    private static void checkDataRemoved(Node head, Node ans, int removeData) {

        while (head !=null){
            if(head.data!=removeData){
                if(ans.data != head.data) throw new RuntimeException("checkDataRemoved error....");
                ans = ans.next;
            }
            head = head.next;
        }
    }

    /**
     * 检查双链表指针是否正常。
     * @param head
     */
    private static void checkDoubleLinkedList(Node head) {
        Node prev = null;
        while (head!=null){
            if(head.prev != prev) throw new RuntimeException("double linked list data error: prev pointer is wrong");
            prev = head;
            head = head.next;
        }
    }

    public static interface OneArgReturnFun<T,R>{
        R apply(T t);
    }

    public static interface TwoArgReturnFun<T1, T2, R>{
        R apply(T1 t1, T2 t2);
    }


    private static Node randomDoubleLinkedList(int len, int maxVal) {
        if(len==0) return null;
        Node cur = new Node();
        cur.data = (int)(Math.random()*maxVal);
        cur.next = randomDoubleLinkedList(len-1, maxVal);
        if(cur.next!=null) cur.next.prev = cur;
        return cur;
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

    private static Node randomSingleLinkedList(int len, int maxVal){
        if(len==0) return null;
        Node cur = new Node();
        cur.data = (int)(Math.random()*maxVal);
        cur.next = randomSingleLinkedList(len-1, maxVal);
        return cur;
    }
}
