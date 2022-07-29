package com.zs.tixi.class10;

import com.zs.xiaobai.common.MyCompValue;

import java.util.Stack;
import java.util.function.Function;

/*
 *      1）给定一个单链表头节点，判断该链表是否为回文结构。
 *          哈希表解决很简单。（笔试用）
 *          改变原链表方式需要注意边界（面试用）
 */
public class Code02_IsPalindromeList {
    public static class Node{
        public int value;
        public Node next;
        public Node (int value){
            this.value = value;
        }
    }

    /**
     * 额外空间：N
     * 使用一个额外的栈空间将链表逆序存储
     * 如果head为空，直接返回true。
     * 从head开始，将链表的每个节点压栈。
     * 从head开始遍历链表，
     *      当前节点与栈每次弹出节点的value进行比较，如果出现不相等，返回false
     * 返回true。
     * @param head
     * @return
     */
    public static boolean isPalindrome1(Node head){
        // 将链表节点入栈。
        // 从head开始依次和栈中弹出的节点比较val大小。如果出现不相等的，直接返回false
        if (head==null) return true;
        Stack<Node> stack = new Stack<>();
        Node currNode = head;
        while (currNode!=null){
            stack.push(currNode);
            currNode = currNode.next;
        }
        currNode = head;
        while (currNode!=null){
            if (currNode.value!=stack.pop().value) return false;
            currNode = currNode.next;
        }
        return true;
    }

    /**
     * 额外空间N/2
     * 整体思路：用一个额外的栈将链表后半部分逆序存储，比较链表前半部分与后半部分逆序是否相同。（优点：节省了一半空间，比较也少了一半）
     * 使用快慢指针找到链表中点的下一位置。记为right，表示右半部分链表。
     * 定义stack
     * 循环(right不为空)
     *      right压栈
     *      right来到下一位置
     * 循环(stack不为空)
     *      如果:stack栈顶元素的value!=head.value。返回false
     *      head来到下一位置
     * 返回true
     * @param head
     * @return
     */
    public static boolean isPalindrome2(Node head){
        // 找到链表中点mid：奇数为中点，偶数为下中点。
        // 将中点开始的节点压栈。
        // 从head开始，依次与栈弹出的节点比较，直到栈为空。遇到val不相等的情况返回false。
        if (head==null) return true;
        Node pQuick = head;
        Node pSlow = head;
        while (pQuick.next!=null && pQuick.next.next!=null) {
            pQuick = pQuick.next.next;
            pSlow = pSlow.next;
        }
        Node right = pSlow.next;
        Stack<Node> stack = new Stack<>();
        while (right!=null){
            stack.push(right);
            right = right.next;
        }
        while (!stack.isEmpty()){
            if (stack.pop().value!=head.value) return false;
            head = head.next;
        }
        return true;
    }

    /**
     * 思路：将链表后半部分指针转向，head头节点和tail尾节点相向而行，
     *  在遇到之前对应节点的值都相等表示该链表为回文链表
     * 找到链表中点mid：奇数长度返回中点，偶数返回下中点
     * 定义pre=mid, curr=mid, next=mid
     * 循环（curr!=null）
     *      next = curr.next;
     *      curr.next = pre;
     *      pre = curr;
     *      curr = next;
     * 此时完成了链表后半部分转向。 pre为尾部节点
     * 定义start=head, end = pre;
     * do
     *      如果start != end
     *          返回false;
     *      start和end相向而行。
     * while(start != end)
     *
     * 将链表后半部分反转回来。
     * 返回true;
     * @param head
     * @return
     */
    public static boolean isPalindrome3(Node head){
        // 找到链表中点mid:奇数找到中点，偶数找到下中点
        // 从mid开始反转链表直到结束。（mid指向mid，保证不论奇偶头尾指针相向而行都可以相遇）
        // 当head!=tail时，比较head.val和tail.val。如果遇到不相等的情况，设置答案为false，跳出循环。
        // 最后记得将链表后半部分恢复，然后返回答案。
        while (head==null) return true;
        Node fast = head;
        Node slow = head;
        Node mid = null;
        while (true){
            if(fast.next==null) {
                mid = slow;
                break;
            }
            if(fast.next.next==null){
                mid = slow.next;
                break;
            }
            fast = fast.next.next;
            slow = slow.next;
        }
        Node pre = mid;
        Node cur = mid;
        Node next = null;
        while (cur != null){
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        Node tail = pre;
        while (head != tail){
            if(head.value != tail.value) return false;
            head = head.next;
            tail = tail.next;
        }

        cur = pre;
        pre = null;
        next = null;
        while (pre!=mid){
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return true;
    }


    public static void main(String[] args) {
        checkIsPalindrome(1000, 99, 99, Code02_IsPalindromeList::isPalindrome1);
        checkIsPalindrome(1000, 99, 99, Code02_IsPalindromeList::isPalindrome2);
        checkIsPalindrome(1000, 99, 99, Code02_IsPalindromeList::isPalindrome3);

//        Node head = null;
//        printLinkedList(head);
//        System.out.print(isPalindrome1(head) + " | ");
//        System.out.print(isPalindrome2(head) + " | ");
//        System.out.println(isPalindrome3(head) + " | ");
//        printLinkedList(head);
//        System.out.println("=========================");
//
//        head = new Node(1);
//        printLinkedList(head);
//        System.out.print(isPalindrome1(head) + " | ");
//        System.out.print(isPalindrome2(head) + " | ");
//        System.out.println(isPalindrome3(head) + " | ");
//        printLinkedList(head);
//        System.out.println("=========================");
//
//        head = new Node(1);
//        head.next = new Node(2);
//        printLinkedList(head);
//        System.out.print(isPalindrome1(head) + " | ");
//        System.out.print(isPalindrome2(head) + " | ");
//        System.out.println(isPalindrome3(head) + " | ");
//        printLinkedList(head);
//        System.out.println("=========================");
//
//        head = new Node(1);
//        head.next = new Node(1);
//        printLinkedList(head);
//        System.out.print(isPalindrome1(head) + " | ");
//        System.out.print(isPalindrome2(head) + " | ");
//        System.out.println(isPalindrome3(head) + " | ");
//        printLinkedList(head);
//        System.out.println("=========================");
//
//        head = new Node(1);
//        head.next = new Node(2);
//        head.next.next = new Node(3);
//        printLinkedList(head);
//        System.out.print(isPalindrome1(head) + " | ");
//        System.out.print(isPalindrome2(head) + " | ");
//        System.out.println(isPalindrome3(head) + " | ");
//        printLinkedList(head);
//        System.out.println("=========================");
//
//        head = new Node(1);
//        head.next = new Node(2);
//        head.next.next = new Node(1);
//        printLinkedList(head);
//        System.out.print(isPalindrome1(head) + " | ");
//        System.out.print(isPalindrome2(head) + " | ");
//        System.out.println(isPalindrome3(head) + " | ");
//        printLinkedList(head);
//        System.out.println("=========================");
//
//        head = new Node(1);
//        head.next = new Node(2);
//        head.next.next = new Node(3);
//        head.next.next.next = new Node(1);
//        printLinkedList(head);
//        System.out.print(isPalindrome1(head) + " | ");
//        System.out.print(isPalindrome2(head) + " | ");
//        System.out.println(isPalindrome3(head) + " | ");
//        printLinkedList(head);
//        System.out.println("=========================");
//
//        head = new Node(1);
//        head.next = new Node(2);
//        head.next.next = new Node(2);
//        head.next.next.next = new Node(1);
//        printLinkedList(head);
//        System.out.print(isPalindrome1(head) + " | ");
//        System.out.print(isPalindrome2(head) + " | ");
//        System.out.println(isPalindrome3(head) + " | ");
//        printLinkedList(head);
//        System.out.println("=========================");
//
//        head = new Node(1);
//        head.next = new Node(2);
//        head.next.next = new Node(3);
//        head.next.next.next = new Node(2);
//        head.next.next.next.next = new Node(1);
//        printLinkedList(head);
//        System.out.print(isPalindrome1(head) + " | ");
//        System.out.print(isPalindrome2(head) + " | ");
//        System.out.println(isPalindrome3(head) + " | ");
//        printLinkedList(head);
//        System.out.println("=========================");

    }

    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    private static void checkIsPalindrome(int times, int maxLen, int maxVal, Function<Node, Boolean> fun){
        MyCompValue.times(times, ()->{
            int len = (int)(Math.random()*(maxLen+1));
            Node head = randomLinkedList(len, maxVal);
            if(isPalindromeByStack(head) != fun.apply(head)){
                throw new RuntimeException("fail.");
            }
        });
    }

    private static boolean isPalindromeByStack(Node head){
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur!=null){
            stack.push(cur);
            cur = cur.next;
        }

        while (head != null){
            if(head.value!= stack.pop().value) return false;
            head = head.next;
        }
        return true;
    }

    private static Node randomLinkedList(int len, int maxVal){
        if(len==0) return null;
        int val = (int)(Math.random()*(maxVal+1));
        Node node = new Node(val);
        node.next = randomLinkedList(len-1, maxVal);
        return node;
    }
}
