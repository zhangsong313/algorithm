package com.zs.tixi.class7;

import com.zs.xiaobai.common.MyCompValue;

/**
 * 手动构建大根堆
 * 1. 当前节点的父节点: (index -1) / 2
 * 2. 当前节点的左子节点 : index * 2 + 1
 */
public class Code02_Heap {
    public static void main(String[] args) {
    }

    /**
     * 堆接口定义
     */
    public static interface HeapInterface{
        void add(int e);
        int poll();
        int peek();
        int size();
    }
    public static class MyMinHeap implements HeapInterface{

        // 定义int[]保存数据， size表示当前大小， capacity表示容量大小
        private int[] elementData;
        private int size;
        private int capacity;

        public MyMinHeap(int capacity){
            this.capacity = capacity;
            this.size = 0;
            elementData = new int[capacity];
        }

        @Override
        public void add(int e) {
            // 新加入的数据放入size位置。
            // 对size位置执行heapInsert，尝试将size位置的数据向堆顶移动。size++
            // 处理size达到容量上限的情况。
            if(size==capacity) throw new IllegalStateException("heap is full.");
            elementData[size] = e;
            heapInsert(elementData, size++);
        }

        private void heapInsert(int[] elementData, int index) {
            // 循环：如果当前节点值小于父节点，交换当前节点和父节点，当前节点来到父节点位置。
            while (elementData[index] < elementData[(index -1) / 2]){
                MyCompValue.swap(elementData, index, (index -1) / 2);
                index = (index -1) / 2;
            }
        }

        @Override
        public int poll() {
            // 取0位置的值作为返回值。
            // 交换size-1位置和0位置的值， size--
            // 对0位置调用heapify，尝试将0位置的值向堆底移动。
            if(size==0) throw new IllegalStateException("heap is empty.");
            int ans = elementData[0];
            MyCompValue.swap(elementData, 0, --size);
            heapify(elementData, 0, size);
            return ans;
        }

        private void heapify(int[] elementData, int index, int size) {
            // 定义left为左子节点。
            // 循环：left小于size，找到两个子节点的较小节点与当前节点比较。
            // 当前节点小于等于较小节点，返回。
            // 否则，交换当前节点和较小节点。
            // 更新当前节点为较小节点，更新左子节点的位置。
            int left = index*2 +1;
            while (left<size){
                int smaller = left+1<size && elementData[left+1]<elementData[left] ? left+1 : left;
                if(elementData[index] <= elementData[smaller]){
                    return;
                }
                MyCompValue.swap(elementData, index, smaller);
                index = smaller;
                left = index*2 + 1;
            }
        }

        @Override
        public int peek() {
            // 返回0位置的值，处理size==0的情况。
            if(size==0) throw new IllegalStateException("heap is empty.");
            return elementData[0];
        }

        @Override
        public int size() {
            return size;
        }
    }
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
            int ans = heap[0]; // 取出堆顶数据。
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
            while (heap[index] > heap[(index-1)/2]){
                MyCompValue.swap(heap, index, (index-1)/2);
                index = (index-1)/2;
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
                int largest = left+1<size && heap[left] < heap[left+1]? left+1: left; // 找到最大子节点。
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
