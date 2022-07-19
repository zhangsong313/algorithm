package com.zs.tixi.class4;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 两个队列实现栈.
 */
public class Code07_TwoQueueImplementStack {

    static class TwoQueueImplementStack<T>{
        Queue<T> queue;
        Queue<T> help;

        public TwoQueueImplementStack(){
            queue = new LinkedList<>();
            help = new LinkedList<>();
        }

        public void push(T data){
            queue.offer(data); //queue队尾追加元素.
        }

        public T pop(){
            while (queue.size()>1){
                help.offer(queue.poll()); // help队尾追加queue的队头.
            }
            T ans = queue.poll();
            Queue<T> tmp = queue; // 交换两个队列.
            queue = help;
            help = tmp;
            return ans;
        }
    }

    public static void main(String[] args) {
        TwoQueueImplementStack stack = new TwoQueueImplementStack();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        stack.push("d");
        stack.push("e");
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}
