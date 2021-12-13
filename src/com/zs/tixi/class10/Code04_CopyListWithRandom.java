package com.zs.tixi.class10;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * 使用容器，额外空间复杂度O(N)
     * 创建哈希表map
     * 遍历链表
     *      map.put(curr, new Node(curr.value))
     * 遍历链表
     *      map.get(curr).next = map.get(curr.next)
     *      map.get(curr).rand = map.get(curr.rand)
     * 返回map.get(head)
     * @param head
     * @return
     */
    public static Node copyRandomList1(Node head) {
        if (head == null) return null;
        Map<Node, Node> map = new HashMap<>();
        Node curr = head;
        while (curr!=null){
            map.put(curr, new Node(curr.val));
            curr = curr.next;
        }
        curr=head;
        while (curr!=null){
            map.get(curr).next = map.get(curr.next);
            map.get(curr).random = map.get(curr.random);
            curr = curr.next;
        }

        return map.get(head);
    }

    /**
     * 在next方向上为每个node创建新节点，并放在node的下一位置.
     * 遍历链表
     *      使用curr.val创建新节点currNew
     *      currNew的next指向curr的next。
     *      curr的next指向currNew
     * 为每个node的新节点设置random指针。
     * 遍历链表
     *      currNew.random = curr.random.next
     *
     * 定义变量ans = head.next;
     * 将新旧链表拆开。
     * 遍历链表
     *      Node next = curr.next.next;
     *      Node newCurr = curr.next;
     *      curr.next = next;
     *      newCurr.next = next==null?null:next.next
     *      curr = next;
     * 返回ans
     * @param head
     * @return
     */
    public static Node copyRandomList2(Node head) {
        if (head==null) return null;
        Node curr = head;
        while (curr!=null){
            Node currNew = new Node(curr.val);
            currNew.next = curr.next;
            curr.next = currNew;

            curr = currNew.next;
        }
        curr = head;
        while (curr!=null){
            curr.next.random = curr.random== null?null:curr.random.next;

            curr = curr.next.next;
        }
        Node ans = head.next;
        curr = head;
        while (curr!=null){
            Node next = curr.next.next;
            Node currNew = curr.next;
            curr.next = next;
            currNew.next = next==null?null:next.next;
            curr = next;
        }
        return ans;
    }
}
