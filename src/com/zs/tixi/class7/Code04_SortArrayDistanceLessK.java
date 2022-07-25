package com.zs.tixi.class7;

import java.util.PriorityQueue;

/**
 * arr数组无序,但每个数距离有序后位置不超过K,排序使得数组有序,复杂度小于O(N*logN)
 */
public class Code04_SortArrayDistanceLessK {

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
    public static void sortArrDistanceListK(int[] arr, int K){
        // 思路：排序后第0位置的数，必定是[0, K]范围内的最小值。因此第0位置的数必定是[0, K]返回内组成的小根堆的堆顶值。
        // 小根堆弹出堆顶值放入0位置，然后小根堆加入第K+1位置的数，弹出堆顶数放在1位置，依次类推，直到每个位置都填好。
        // 小根堆规模为K，每次调整的代价为O（K），总调整代价为O(N*logK)
        // 如果K<=0，说明已经是arr已经是有序的，直接返回。
        // 如果K>=arr.length，说明数组是完全无序的，直接调用排序算法排序即可。
        // 将前K个数加入小根堆。
        // 循环：index=[K+1, arr.length-1]，小根堆每次弹出堆顶值放入第i位置（i从0开始每次加1），将index位置的数加入小根堆。
        // 将堆中剩下的数依次弹出放入arr的i位置中，每次i++。
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
