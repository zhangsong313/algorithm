package com.zs.tixi.class2;

import com.zs.xiaobai.common.MyCompValue;

public class Code01_BaseSort {
    /**
     * 选择排序：从i..n-1范围内找出最小值，将最小值与i位置交换。
     * 时间复杂度：O(N^2)
     * 空间复杂度：O(1)
     * 排序稳定性：无。由于每次i位置的数被交换到i..n-1范围内最小值位置，最小值位置前可能有与i相等的数。
     */
    public static void selectSort(int[] arr){
        if(arr==null || arr.length<2){
            return;
        }
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            int minIndex = i;
            for (int j = i+1; j < N; j++) {
                if(arr[j]<arr[minIndex]) minIndex = j;
            }
            MyCompValue.swap(arr, i, minIndex);
        }
    }

    /**
     * 插入排序：0..i-1范围已经有序，i向左，要求arr[i]小于arr[i-1], 每次交换i和i-1的值。
     * 时间复杂度: O(N^2)
     * 空间复杂度：O(1)
     * 排序稳定性：有。
     */
    public static void insertSort(int[] arr){
        if(arr==null || arr.length<2){
            return;
        }
        int N = arr.length;
        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && arr[j]<arr[j-1]; j--) {
                MyCompValue.swap(arr, j, j-1);
            }
        }
    }

    /**
     * 冒泡排序: i从0到N-1， 让i位置为i..N-1范围内的最小值，但不是选出最小值位置交换，而是从N-1到i+1，如果比左侧数小就交换。
     * 时间复杂度: O（N^2）
     * 空间复杂度: O(1)
     * 排序稳定性: 有
     */
    public static void bubbleSort(int[] arr){
        if(arr==null || arr.length<2){
            return;
        }
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            for (int j = N-1; j > i; j--) {
                if(arr[j]<arr[j-1]) MyCompValue.swap(arr, j, j-1);
            }
        }
    }

    public static void main(String[] args) {
        MyCompValue.checkSort(1000, 100, 999, Code01_BaseSort::selectSort);
        MyCompValue.checkSort(1000, 100, 999, Code01_BaseSort::insertSort);
        MyCompValue.checkSort(1000, 100, 999, Code01_BaseSort::bubbleSort);
    }
}
