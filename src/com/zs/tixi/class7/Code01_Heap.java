package com.zs.tixi.class7;

import com.zs.xiaobai.common.MyCompValue;

/**
 * 手动构建大根堆
 * 1. 当前节点的父节点
 * 2. 当前节点的左子节点
 */
public class Code01_Heap {
    public static class MyMaxHeap{
        private int[] heap; // 堆空间
        private final int limit; // 堆容量
        private int size; // 堆大小

        /**
         * 构建堆
         * 初始化堆数组,容量,大小
         * @param limit 堆容量
         */
        public MyMaxHeap(int limit){
            this.limit = limit;
            heap = new int[limit];
            size=0;
        }

        /**
         * 向堆中新增数据
         * 如果堆空间满了,抛出异常
         * 将新进入的数放在size位置.
         * 对size位置执行heapInsert
         * size加一
         * @param data
         */
        public void add(int data){
            if (size == limit){
                throw new RuntimeException("heap is full..");
            }
            heap[size] = data;
            heapInsert(heap, size++);
        }

        /**
         * 从堆顶取出数据并删除
         * 如果堆为空,抛出异常
         * 取出堆顶数据
         * 交换堆顶和堆底数据
         * 堆大小减一
         * 对堆顶位置进行heapify
         * @return
         */
        public int poll(){
            return 0;
        }

        /**
         * 从堆顶取出数据不删除.
         * @return
         */
        public int peek(){
            return 0;
        }

        /**
         * 比较当前数与父节点的大小，如果大则替换，直到小于等于父节点。
         * @param heap 堆数组
         * @param index 新增数下标
         */
        private void heapInsert(int[] heap, int index) {
            while (heap[index] > heap[(index-1)>>1]){
                MyCompValue.swap(heap, index, (index-1)>>1);
                index = (index-1)>>1;
            }
        }

        /**
         * 找到当前数的两个子节点,并找出较大子节点.
         * 比较当前数与较大子节点大小,如果小,当前数与较大子节点交换.
         * 重复进行比较,直到当前数据不小于较大子节点或超出堆大小范围
         * @param heap
         * @param index
         * @param size
         */
        private void heapify(int[] heap, int index, int size){
            int i = index * 2 + 1; // 左子节点
        }
    }
}
