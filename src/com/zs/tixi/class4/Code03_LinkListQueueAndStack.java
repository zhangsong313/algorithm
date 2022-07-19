package com.zs.tixi.class4;

import java.util.NoSuchElementException;

/**
 * 链表实现队列和栈。
 * 先实现双端队列.
 * 队列和栈通过封装双端队列得道.
 */
public class Code03_LinkListQueueAndStack {

    /**
     * 双端队列接口
     * @param <E>
     */
    public static interface DequeInterface<E>{
        void addFirst(E e);
        void addLast(E e);
        E removeFirst();
        E removeLast();
        int size();
    }

    /**
     * 实现双端队列
     * @param <E>
     */
    public static class MyDeque<E> implements DequeInterface<E> {
        private int size = 0;
        private Node<E> head;
        private Node<E> tail;

        @Override
        public void addFirst(E e) {
            // 首先生成新节点
            // 核心操作： 新节点指向头节点，头节点指向新节点，更新头节点为新节点。
            // 注意处理头节点为空的情况:头节点和尾结点都指向新节点。
            // size++
            Node<E> newNode = new Node<>(e);
            if(head==null){
                head = newNode;
                tail = newNode;
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
            size++;
        }

        @Override
        public void addLast(E e) {
            Node<E> newNode = new Node<>(e);
            if(head==null){
                head = newNode;
                tail = newNode;
            }else {
                newNode.prev = tail;
                tail.next = newNode;
                tail = newNode;
            }
            size++;
        }

        @Override
        public E removeFirst() {
            // head为空，抛出异常。
            // 获取head的值用来返回。
            // 核心操作：head来到下一位置，更新head的prev为null。
            // 注意处理head==tail的情况
            // size--
            if(head==null){
                throw new NoSuchElementException();
            }
            E res = head.val;
            if(head==tail){
                head = null;
                tail = null;
            }else {
                head = head.next;
                head.prev = null;
            }
            size--;
            return res;
        }

        @Override
        public E removeLast() {
            if(head==null) throw new NoSuchElementException();
            E res = tail.val;
            if(head==tail){
                head = null;
                tail = null;
            }else {
                tail = tail.prev;
                tail.next = null;
            }
            size--;
            return res;
        }

        @Override
        public int size() {
            return size;
        }

        private static class Node<N>{
            public N val;
            public Node<N> prev;
            public Node<N> next;
            public Node(N data){val = data;}
        }
    }

    /**
     * 栈,提供push,pop方法.
     * @param <T>
     */
    class MyStack<T>{
        // 实现栈：push操作调用addFirst， pop调用removeFirst
        MyDeque<T> deque = new MyDeque<>();
        public void push(T data){
            deque.addFirst(data);
        }
        public T pop(){
            return deque.removeFirst();
        }
        public Integer size(){return deque.size();}
    }

    class MyQueue<T>{
        // 实现队列：push调用addFirst, poll调用removeLast
        MyDeque<T> deque = new MyDeque<>();
        public void push(T data){
            deque.addFirst(data);
        }
        public T poll(){
            return deque.removeLast();
        }
        public Integer size(){return deque.size();}
    }

}
