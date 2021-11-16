package com.zs.tixi.class4;

/**
 * 链表实现队列和栈。
 * 先实现双端队列.
 * 队列和栈通过封装双端队列得道.
 */
public class Code03_LinkListQueueAndStack {
    public static class Node<T> {
        public T value;
        public Node<T> last;
        public Node<T> next;

        public Node(T data) {
            value = data;
        }
    }

    public class Deque<T>{
        private Node<T> head;
        private Node<T> tail;
        private Integer size = 0;

        /**
         * 从头部添加.
         * @return
         */
        public void pushToHead(T data){
            Node<T> curr = new Node<>(data);

            if (head == null){ // 如果为空.head和tail指向curr.
                head = curr;
                tail = curr;
            } else {
                // curr和head互相连接,更新head变量指向curr.
                curr.next = head;
                head.last = curr;
                head = curr;
            }
            size++;
        }

        /**
         * 向尾部添加
         * @return
         */
        public void pushToTail(T data){
            Node<T> curr = new Node<>(data);

            if (head == null){ // 如果为空.head和tail指向curr.
                head = curr;
                tail = curr;
            } else {
                // curr和tail互相连接,更新tail变量指向curr.
                curr.last = tail;
                tail.next = curr;
                tail = curr;
            }
            size++;
        }

        /**
         * 从头部拿出
         * @return
         */
        public T popFromHead(){
            if (head == null) return null; // 链表为空直接返回null
            T value = head.value;

            if (head == tail){ // 需要考虑只有一个的情况
                head = null;
                tail = null;
            } else {
                head = head.next;// 移动head位置,并断开head与上一节点.
                head.last = null;
            }

            size--;
            return value;
        }

        /**
         * 从尾部拿出.
         * @return
         */
        public T popFromTail(){
            if (head == null) return null; // 链表为空直接返回null
            T value = tail.value;

            if (head == tail){ // 需要考虑只有一个的情况
                head = null;
                tail = null;
            } else {
                tail = tail.last;// 移动tail位置,并断开tail与下一节点.
                tail.next = null;
            }

            size--;
            return value;
        }

        public Integer size(){
            return size;
        }
    }

    /**
     * 栈,提供push,pop方法.
     * @param <T>
     */
    class MyStack<T>{
        Deque<T> deque = new Deque<>();
        public void push(T data){
            deque.pushToHead(data);
        }
        public T pop(){
            return deque.popFromHead();
        }
        public Integer size(){return deque.size();}
    }

    class MyQueue<T>{
        Deque<T> deque = new Deque<>();
        public void push(T data){
            deque.pushToHead(data);
        }
        public T poll(){
            return deque.popFromTail();
        }
        public Integer size(){return deque.size();}
    }

}
