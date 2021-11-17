package com.zs.tixi.class7;

import com.zs.xiaobai.common.MyCompValue;

/**
 * 手动构建大根堆
 * 1. 当前节点的父节点
 * 2. 当前节点的左子节点
 */
public class Code02_Heap {
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
            if (size ==0 ) throw new RuntimeException("heap is empty");
            int ans = heap[size-1]; // 取出堆顶数据。
            MyCompValue.swap(heap, 0, --size); // 交换堆顶和堆底数据。堆大小减一
            heapify(heap, 0, size);
            return ans;
        }

        /**
         * 从堆顶取出数据不删除.
         * 如果堆为空抛出异常。
         * 返回堆顶的数据。
         * @return
         */
        public int peek(){
            if (size == 0) throw new RuntimeException("heap is empty");
            return heap[0];
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
            int left = index * 2 + 1; // 左子节点
            while (left < size){ // 当左子节点不超出堆大小时进行循环
                int largest = left+1<size && heap[left] < heap[left+1]? left+1: left;
                if (heap[index] >= heap[largest]) { // 如果当前数不小于最大子节点，退出循环。
                    break;
                }
                MyCompValue.swap(heap, index, largest); // 交换当前数与最大子节点。
                index = largest; // 更新当前节点位置
                left = index * 2 +1; // 更新左子节点位置。
            }
        }
    }
}
