package com.zs.tixi.class4;

/**
 * 数组实现栈和队列.
 */
public class Code04_RingArray {

	static class MyDeque<T> {
		private Object[] arr;
		private int head = -1;
		private int tail = -1;
		private int size;
		public MyDeque(int size){
			arr = new Object[size];
		}

		public void pushToHead(T data){
			if (size == arr.length) throw new RuntimeException("满了");
			if(size == 0){
				head = 0;
				tail = 0;
				arr[0] = data;
			} else {
				head = lastIndex(head);
				arr[head] = data;
			}

			size++;
		}

		public void pushToTail(T data){
			if (size == arr.length) throw new RuntimeException("满了");
			if(size == 0){
				head = 0;
				tail = 0;
				arr[0] = data;
			} else {
				tail = nextIndex(tail);
				arr[tail] = data;
			}

			size++;
		}

		public T popFromHead(){
			if (size == 0 ) throw new RuntimeException("空..");
			T data;
			if (size == 1){
				head = -1;
				tail = -1;
				data = (T)arr[0];
			} else {
				data = (T) arr[head];
				head = nextIndex(head);
			}

			size--;

			return data;
		}

		public T popFromTail(){
			if (size == 0 ) throw new RuntimeException("空..");
			T data;
			if (size == 1){
				head = -1;
				tail = -1;
				data = (T)arr[0];
			} else {
				data = (T) arr[tail];
				tail = lastIndex(tail);
			}

			size--;

			return data;
		}

		public int size(){
			return size;
		}

		private int lastIndex(int index){
			if(index ==0) return arr.length-1;
			return index - 1;
		}

		private int nextIndex(int index){
			if(index == arr.length-1) return 0;
			return index + 1;
		}
	}

	class MyStack<T>{
		Code04_RingArray.MyDeque<T> deque;
		public MyStack(int size){
			deque = new Code04_RingArray.MyDeque<>(size);
		}
		public void push(T data){
			deque.pushToHead(data);
		}
		public T pop(){
			return deque.popFromHead();
		}
		public Integer size(){return deque.size();}
	}

	class MyQueue<T>{
		Code04_RingArray.MyDeque<T> deque;
		public MyQueue(int size){
			deque = new Code04_RingArray.MyDeque<>(size);
		}
		public void push(T data){
			deque.pushToHead(data);
		}
		public T poll(){
			return deque.popFromTail();
		}
		public Integer size(){return deque.size();}
	}
}
