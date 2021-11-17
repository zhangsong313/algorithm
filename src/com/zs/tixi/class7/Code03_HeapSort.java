package com.zs.tixi.class7;

import com.zs.xiaobai.common.MyCompValue;

/**
 *  * 3.堆排序
 *  * 4.O(N)复杂度建堆
 */
public class Code03_HeapSort {

    /**
     * 使用堆排序对arr进行排序
     *
     * 临界条件判断
     * 遍历arr，从0-arr.length-1：
     *      对当前位置执行heapInsert
     * 将arr看作大根堆结构，循环直到堆大小为空:
     *      交换堆顶与堆底位置的数
     *      堆大小减一
     *      对0位置执行heapify
     * @param arr
     */
    public static void heapSort(int[] arr){
        if (arr == null || arr.length<2) return;
        // O(N)
        for (int i = arr.length-1; i >=0 ; i--) {
            heapify(arr, i, arr.length);
        }
        // O(N*logN)
//        for (int i = 0; i < arr.length; i++) {
//            heapInsert(arr, i);
//        }
        int heapSize = arr.length;
        while (heapSize>0){
            MyCompValue.swap(arr, 0, --heapSize);
            heapify(arr, 0, heapSize);
        }
    }

    /**
     * 对index位置执行heapify。
     * @param arr
     * @param index
     * @param heapSize
     */
    public static void heapify(int[] arr, int index, int heapSize){
        int left = index*2+1;
        while (left<heapSize){
            int largest = left+1<heapSize && arr[left+1]>arr[left]? left+1 : left;
            if (arr[index]>=arr[largest]){
                break;
            }
            MyCompValue.swap(arr, index, largest);
            index = largest;
            left = index*2+1;
        }
    }

    /**
     * 对index位置上的数执行heapInsert
     * @param arr
     * @param index
     */
    public static void heapInsert(int[] arr, int index){
        while (arr[index] > arr[(index-1)/2]){
            MyCompValue.swap(arr, index, (index-1)/2);
            index = (index-1)/2;
        }
    }


}
