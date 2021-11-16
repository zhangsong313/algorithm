package com.zs.xiaobai.class1;

import com.zs.xiaobai.common.MyCompValue;

/**
 * 哪种排序效果最好呢？先比较时间复杂度，相同则实测出常数时间谁最少。
 * 排序
 * 1、选择排序：每次找到i-n-1上最小的值位置，将最小值与i位置数交换。
 * 2、冒泡排序：每次查看i-n-1位置上的数，如果左侧数大于右侧数，进行交换。
 * 3、插入排序：最差时间复杂度为O(n^2)，
 */
public class T02_Sort {

    private static void selectSort(int[] arr){
        if(arr == null || arr.length<2){
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            // 当前需要更新为最小值的位置为i。
            int mixIndex = i; // 将i作为minIndex的初始值。
            for (int j = i+1; j < arr.length; j++) {
                // 从i+1位置开始，比较当前位置与minIndex的位置值大小，如果小，更新minIndex。循环结束后，minIndex值为最终值。
                mixIndex = arr[j]<arr[mixIndex]? j : mixIndex;
            }
            // 交换minIndex与i的位置。
            swap(arr, mixIndex, i);
        }
    }

    private static void bubbleSort2(int[] arr){
        if(arr == null || arr.length<2){
            return ;
        }

        for (int i = 0; i < arr.length; i++) {
            // 每次循环会更新i位置为最小值
            for (int j = arr.length-1; j >i; j--) {
                // 从arr.length-1位置开始，比较当前位置与左侧位置的大小，如果小，交换与左侧的值。
                if (arr[j]<arr[j-1]){
                    swap(arr, j, j-1);
                }
            }
        }
    }

    private static void bubbleSort(int[] arr){
        if(arr == null || arr.length<2){
            return;
        }
        for (int i = arr.length-1; i >=0 ; i--) {
            for (int j = 1; j <= i; j++) {
                if (arr[j-1]>arr[j]){
                    swap(arr, j-1, j);
                }
            }
        }
    }

    private static void insertSort2(int[] arr){
        if (arr == null | arr.length<2){
            return ;
        }

        for (int i = 1; i < arr.length; i++) {
            // 当前下标初始值为i。每次循环保证0-i范围内有序，遇到比自己大的值就一直交换。直到左侧值比自己小。
            for (int index = i; index>0 && arr[index] <arr[index-1]; index--) {
                swap(arr, index, index-1);
            }
        }
    }

    private static void insertSort(int[]arr){
        if(arr == null || arr.length<2){
            return;
        }

        for (int i = 1; i < arr.length; i++) {

            int insertIndex = i;

            while(insertIndex>0 && arr[insertIndex -1] > arr[insertIndex]){
                swap(arr, insertIndex-1, insertIndex);
                insertIndex--;
            }
        }
    }

    private static void swap(int[]arr, int i, int j){
        int tmp = arr[j];
        arr[j] = arr[i];
        arr[i] = tmp;
    }

    private static void printArr(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
//        int[] arr = {1,4,0,5,1 };
//        bubbleSort2(arr);
//        printArr(arr);
//        insertSort(arr);
//        printArr(arr);
//        MyCompValue.checkSort(1000, 1000, 9999999, T02_Sort::insertSort);
//        MyCompValue.checkSort(1000, 1000, 9999999, T02_Sort::bubbleSort);
//        MyCompValue.checkSort(1000, 1000, 9999999, T02_Sort::selectSort);
        MyCompValue.checkSort(1000, 1000, 9999999, T02_Sort::insertSort2);
//        for (int i = 0; i < 10000; i++) {
//            int l = (int)(Math.random()*(5+1));
//            if (l == 0) {
//                System.out.println("aaa");
//                break;
//            }
//        }
    }
}
