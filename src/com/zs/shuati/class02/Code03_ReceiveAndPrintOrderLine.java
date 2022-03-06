package com.zs.shuati.class02;

import java.util.HashMap;

/**
 * 已知一个消息流会不断地吐出整数1~N，但不一定按照顺序依次吐出，如果上次打印的序号为i， 那么当i+1出现时
 * 请打印i+1及其之后接收过的并且连续的所有数，直到1~N全部接收并打印完，请设计这种接收并打印的结构
 *
 * 思路,将消息包装为链表,用hashMap记录序号和链表节点的关系.
 * 分为两个hashMap,分别记录那些是结尾的点和是开头的点.
 * 每到一个节点,查看该节点需要前序号和后序号是否在hashMap.
 * 如果前序号的链表在结尾的hashMap中
 *      将当前节点接在前序号节点后面,前序号节点从结尾表中去除
 * 如果后序号链表在开头的hashMap中
 *      将当前节点的后节点指向后序号链表,后序号节点从开头表中去除.
 * 定义变量next记录等待播放的序号.
 * 如果next到了后,把next向后的所有节点打印,
 * 同时记得更新开头表和结尾表.去除打印过的节点.
 *
 */
public class Code03_ReceiveAndPrintOrderLine {

    public static class Node {
        public String info;
        public Node next;

        public Node(String str) {
            info = str;
        }
    }

    public static class MessageBox {
        private HashMap<Integer, Node> headMap;
        private HashMap<Integer, Node> tailMap;
        private int waitPoint;

        public MessageBox() {
            headMap = new HashMap<Integer, Node>();
            tailMap = new HashMap<Integer, Node>();
            waitPoint = 1;
        }

        // 消息的编号，info消息的内容, 消息一定从1开始
        public void receive(int num, String info) {
            if (num < waitPoint) {
                return;
            }
            Node cur = new Node(info);
            int preNum = num-1;
            if(tailMap.containsKey(preNum)){
                tailMap.get(preNum).next = cur;
                tailMap.remove(preNum);
            }else {
                headMap.put(num,cur);
            }
            int nextNum = num+1;
            if(headMap.containsKey(nextNum)){
                cur.next = headMap.get(nextNum);
                headMap.remove(nextNum);
            }else {
                tailMap.put(num, cur);
            }

            if(num == waitPoint){
                print();
            }
        }

        private void print() {
            int endNum = waitPoint;
            Node cur = headMap.get(waitPoint);
            while (cur!=null){
                System.out.print(cur.info+" ");
                endNum++;
                cur = cur.next;
            }
            System.out.println();
            headMap.remove(waitPoint);
            tailMap.remove(endNum-1);
            waitPoint = endNum;
        }

    }

    public static void main(String[] args) {
        // MessageBox only receive 1~N
        MessageBox box = new MessageBox();
        // 1....
        System.out.println("这是2来到的时候");
        box.receive(2,"B"); // - 2"
        System.out.println("这是1来到的时候");
        box.receive(1,"A"); // 1 2 -> print, trigger is 1
        box.receive(4,"D"); // - 4
        box.receive(5,"E"); // - 4 5
        box.receive(7,"G"); // - 4 5 - 7
        box.receive(8,"H"); // - 4 5 - 7 8
        box.receive(6,"F"); // - 4 5 6 7 8
        box.receive(3,"C"); // 3 4 5 6 7 8 -> print, trigger is 3
        box.receive(9,"I"); // 9 -> print, trigger is 9
        box.receive(10,"J"); // 10 -> print, trigger is 10
        box.receive(12,"L"); // - 12
        box.receive(13,"M"); // - 12 13
        box.receive(11,"K"); // 11 12 13 -> print, trigger is 11

    }
}
