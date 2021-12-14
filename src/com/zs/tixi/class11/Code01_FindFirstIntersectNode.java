package com.zs.tixi.class11;

/*
 * 1. 链表常见面试题（续）
 *      1）给定两个可能有环也可能无环的单链表，头节点head1和head2.
 *      请实现一个函数，如果两个链表相交，请返回相交的第一个节点。如果不相交，返回null
 *      要求：如果两个链表长度之和为N，时间复杂度请达到O（N），额外空间复杂度请达到O（1）.
 */
public class Code01_FindFirstIntersectNode {

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 分情况讨论：
     *      1.A链表无环，B链表有环。说明两个链表必定不相交。返回null。
     *      2.A链表无环，B链表无环。链表有可能相交。
     *      3.A链表有环，B链表有环。链表有可能相交。
     * 获取A链表的入环节点loop1
     * 获取B链表的入环节点loop2
     * 情况2求解相交点：noLoop函数
     * 情况2求相交点：bothLoop函数
     * 其它情况（情况1）：返回null;
     *
     * @param head1
     * @param head2
     * @return
     */
    public static Node getIntersectNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        if (loop1 == null && loop2 == null) {
            return noLoop(head1, head2);
        }
        if (loop1 != null && loop2 != null) {
            return bothLoop(head1, loop1, head2, loop2);
        }
        return null;
    }

    // 找到链表第一个入环节点，如果无环，返回null

    /**
     * 如果head为空返回null。
     * 定义快慢指针fast,slow
     * 快慢指针都从头节点出发，快指针每次走两步，慢指针每次走一步。
     * 直到快慢指针相遇。
     *      如果快指针走着走着遇到空，说明此链表不可能有环，返回null
     * 此时快慢指针相遇
     * 快指针再次从头部出发，每次走一步，慢指针也每次走一步。
     * 最终两个指针在入环点相遇
     *
     * 如下图：
     * s为链表头部。
     * a，a1, a2为每次经过的入环点
     * 快慢指针第一次相遇时：e为慢指针位置，e2为快指针位置。
     * 此时s->e2长度为环长的两倍。
     * s->e长度为环长。
     * a->a1长度为环长.
     * 所以有：s->a == e->a1
     *
     * s___a_________________e___a1_________________e2___a2
     *
     * @param head
     * @return
     */
    public static Node getLoopNode(Node head) {
        if (head == null) return null;
        Node fast = head;
        Node slow = head;
        do{
            if (fast.next==null || fast.next.next==null) return null;
            fast = fast.next.next;
            slow = slow.next;
        } while (fast!=slow);

        fast = head;
        while (fast!=slow){
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }

    // 如果两个链表都无环，返回第一个相交节点，如果不想交，返回null

    /**
     * 如果A链表和B链表有一个为空返回null。
     * 定义curr1从A链表头部开始遍历链表，直到链尾。同时记录下A链表长度m。
     * 定义curr2从B链表头部开始遍历链表，直到链尾。统计记录下B链表长度n.
     * 此时curr1和curr2分别来到了两个链表的尾部。如果此时curr1和curr2还不是同一节点。说明两个链表没有相交。
     *
     * (m-n)的绝对值为两条链表的长度差Lab。
     * curr1从较长链表的头部出发，curr2从较短链表的头部出发
     * curr1先走Lab的距离，此时curr1来到了和curr2距离链尾距离相等的位置。
     * 循环（curr1!=curr2）
     *      curr1走1步
     *      curr2走1步
     * 此时curr1和curr2相同，且为链表第一个相交点。
     * @param head1
     * @param head2
     * @return
     */
    public static Node noLoop(Node head1, Node head2) {
        if (head1==null || head2==null) return null;

        Node curr1 = head1;
        Node curr2 = head2;
        int n = 0;
        while (curr1.next != null){
            curr1 = curr1.next;
            n++;
        }
        while (curr2.next!=null){
            curr2 = curr2.next;
            n--;
        }
        if (curr1!=curr2) return null;


        curr1 = n>0?head1:head2;
        curr2 = n>0?head2:head1;
        n = Math.abs(n); // 两条链表的长度差。
        while (n>0){
            curr1 = curr1.next;
            n--;
        }
        while (curr1!=curr2){
            curr1 = curr1.next;
            curr2 = curr2.next;
        }
        return curr1;
    }

    // 两个有环链表，返回第一个相交节点，如果不想交返回null

    /**
     * A链表和B链表都有环，如果相交那么环必然是两个链表的公共环。
     * 以上为基础分情况讨论：
     *      1.A链表和B链表在入环前或入环时相交。
     *      2.A链表和B链表在环上相交。
     * 情况1：此时两个链表的入环点肯定相同。
     *      定义curr1=head1
     *      curr2=head2
     *      curr1走到A链表的入环点，同时记录长度m
     *      curr2走到B链表的入环点，同时记录长度n
     *      m-n的绝对值为两链表的长度差Lab。
     *      curr1从较长链表出发，curr2从较短链表出发。
     *      curr1先走过Lab距离。
     *      循环(curr1!=curr2)
     *          curr1走一步
     *          curr2走一步
     *      返回curr1
     * 情况2：此时两个链表的入环点肯定不相同。
     *      定义curr1从A链表的入环点loop1下一节点开始走。
     *      循环(curr1!=loop1)
     *          如果curr1==B链表的入环点loop2
     *              返回loop1(或返回loop2都对，因为此时两链表处于循环追赶相遇的状态)
     *          curr1走一步
     *      走完上面循环说明curr1在A链表环上没有遇到loop2，说明两链表不相交。
     *      返回null;
     * @param head1
     * @param loop1
     * @param head2
     * @param loop2
     * @return
     */
    public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        if (loop1 == loop2){
            Node curr1 = head1;
            Node curr2 = head2;
            int n = 0;
            while (curr1!=loop1){
                curr1 = curr1.next;
                n++;
            }
            while (curr2!=loop2){
                curr2 = curr2.next;
                n--;
            }
            curr1 = n>0?head1:head2;
            curr2 = n>0?head2:head1;
            n = Math.abs(n);
            while (n>0){
                curr1 = curr1.next;
                n--;
            }
            while (curr1!=curr2){
                curr1 = curr1.next;
                curr2 = curr2.next;
            }
            return curr1;
        }

        Node curr1 = loop1.next;
        while (curr1!=loop1){
            if (curr1==loop2) return loop1;
            curr1 = curr1.next;
        }
        return null;
    }
}
