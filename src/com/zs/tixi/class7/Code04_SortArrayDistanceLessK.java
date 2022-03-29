package com.zs.tixi.class7;

import java.util.PriorityQueue;

/**
 * arr数组无序,但每个数距离有序后位置不超过K,排序使得数组有序,复杂度小于O(N*logN)
 */
public class Code04_SortArrayDistanceLessK {

    public static void main(String[] args) {
        sortArrDistanceListK(new T.LessKArr(new int[]{7, 2, 5, 1, 8}, 3));
    }

    /**
     * 时间复杂度O(N*log(K+1))
     * 先将arr前K个数放入小根堆。
     * i初始化为0
     * 在K..arr.length-1范围上，迭代变量为index。
     *      从小根堆弹出最小值，放入arr[i]位置
     *      将arr[index]加入小根堆
     *      i++, index++
     *  将小根堆中的数依次弹出，放入arr
     */
    public static void sortArrDistanceListK(T.LessKArr lessKArr){
        int[] arr = lessKArr.arr;
        int K = lessKArr.K;

        if (K<=0) return;
        if (K>=arr.length) {
            Code03_HeapSort.heapSort(arr);
            return;
        }
        
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int i = 0; i <= K; i++) {
            heap.add(arr[i]);
        }
        int i = 0;
        for (int index = K+1; index < arr.length; index++) {
            arr[i] = heap.poll();
            heap.add(arr[index]);
            i++;
        }
        while (!heap.isEmpty()){
            arr[i++] = heap.poll();
        }
    }
}
