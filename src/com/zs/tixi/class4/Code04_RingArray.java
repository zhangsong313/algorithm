package com.zs.tixi.class4;

import java.util.NoSuchElementException;

/**
 * 数组实现栈和队列.
 */
public class Code04_RingArray {

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
    public static class MyDeque<E> implements DequeInterface<E>{

        // 需要head和tail表示当前头尾的位置，size记录当前容器大小，且用来判断是否达到容量上限。Object[]用来存放数据
        Object[] datas;
        private int size = 0;
        private int head = -1;
        private int tail = -1;

        // 构造方法指定容量：创建数组需要指定大小。
        public MyDeque(int capacity){
            datas = new Object[capacity];
        }

        @Override
        public void addFirst(E e) {
            // 核心步骤：head指针移动到头部向前的位置，head位置设置为当前数据。
            // 需要考虑size达到容量限制的情况。
            // 需要考虑head和tail为-1的情况。
            // size++
            if(size==datas.length) throw new IllegalStateException("the element cannot be added at this" +
                    " time due to capacity restrictions");
            if(size==0){
                datas[0] = e;
                head = 0;
                tail = 0;
            }else {
                head = prevIndex(head);
                datas[head] = e;
            }
            size++;
        }

        // 返回下标向前扩展的位置
        private int prevIndex(int index){
            return index==datas.length-1?0:index+1;
        }

        // 返回下标向后扩展的位置。
        private int nextIndex(int index){
            return index==0?datas.length-1:index-1;
        }

        @Override
        public void addLast(E e) {
            if(size==datas.length) throw new IllegalStateException("the element cannot be added at this" +
                    " time due to capacity restrictions");
            if(size==0){
                datas[0] = e;
                head = 0;
                tail = 0;
            }else {
                tail = nextIndex(tail);
                datas[tail] = e;
            }
            size++;
        }

        @Override
        public E removeFirst() {
            // 获取head位置的数据用来返回
            // 核心操作：head移动到head向后扩展的位置。
            // 考虑size为0的情况
            // 考虑size为1的情况
            // size--
            if(size==0) throw new NoSuchElementException("is empty");
            E res = (E)datas[head];
            if(size==1){
                head = -1;
                tail = -1;
            }else {
                head = nextIndex(head);
            }
            size--;
            return res;
        }

        @Override
        public E removeLast() {
            if(size==0) throw new NoSuchElementException("is empty");
            E res = (E)datas[tail];
            if(size==1){
                head = -1;
                tail = -1;
            }else {
                tail = prevIndex(tail);
            }
            size--;
            return res;
        }

        @Override
        public int size() {
            return size;
        }
    }

	static class MyStack<T>{
		MyDeque<T> deque;
		public MyStack(int size){
			deque = new MyDeque<>(size);
		}
		public void push(T data){
			deque.addFirst(data);
		}
		public T pop(){
			return deque.removeFirst();
		}
		public Integer size(){return deque.size();}
	}

	static class MyQueue<T>{
        MyDeque<T> deque;
		public MyQueue(int size){
			deque = new MyDeque<>(size);
		}
		public void push(T data){
			deque.addFirst(data);
		}
		public T poll(){
			return deque.removeLast();
		}
		public Integer size(){return deque.size();}
	}
}
