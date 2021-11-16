package com.zs.tixi.class4;

/**
 * 实现一个可以返回当前栈中最小值的栈.
 */
public class Code05_GetMinStack {
    static class MyMinValDeque {
        private int[] arr;
        private int[] minArr; // 用来保存当前范围内的最小值.
        private int head = -1;
        private int size;
        public MyMinValDeque(int size){
            arr = new int[size];
            minArr = new int[size];
        }

        public void push(int data){
            if (size == arr.length) throw new RuntimeException("满了");
            if(size == 0){
                head = 0;
                arr[0] = data;
                minArr[0] = data;
            } else {
                head++;
                arr[head] = data;
                minArr[head] = minArr[head-1] < data ? minArr[head-1] : data;
            }

            size++;
        }

        public int pop(){
            if (size == 0 ) throw new RuntimeException("空..");
            int data;
            if (size == 1){
                head = -1;
                data = arr[0];
            } else {
                data = arr[head];
                head--;
            }

            size--;

            return data;
        }
        public int getMin(){
            if (size == 0 ) throw new RuntimeException("空..");
            return minArr[head];
        }

        public int size(){
            return size;
        }
    }
}
