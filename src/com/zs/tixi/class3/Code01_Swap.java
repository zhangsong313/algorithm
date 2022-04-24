package com.zs.tixi.class3;

/**
 * 1 不用额外变量交换两个数的值
 *
 * 2 不用额外变量交换数组中两个数的值
 */
public class Code01_Swap {
    public void swap(int[]arr, int i, int j){
        if (i == j) return;
        arr[i] = arr[i]^arr[j];
        arr[j] = arr[i]^arr[j];
        arr[i] = arr[i]^arr[j];
    }
}
