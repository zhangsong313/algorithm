package com.zs.tixi.class6;

import static com.zs.xiaobai.common.MyCompValue.swap;

/**
 *  * 2.荷兰国旗问题.给定一个数组arr和arr中的一个数X,返回arr,使得小于X的放左边,等于X的放中间,大于X的放右边.
 *  * 3.快速排序1.0版本,荷兰国旗问题中,X为初始最右边的数,小于等于放左边,以X结尾,大于X的放右边.
 *  * 4.快速排序2.0版本,X为初始最右边的数,小于X的放左边,等于X的放中间,大于X的放右边
 *  * 5.随机快速排序.(真正概念上的快速排序),随机将arr中的一个数与最右边互换.X为初始最右边的数,小于X的放左边,等于X的放中间,大于X的放右边
 */
public class Code02_PartitionAndQuickSort {

    /**
     * X为初始最右边的数,小于等于放左边,以X结尾,大于X的放右边.
     * @return
     */
    private static int partion(int[] arr, int L, int R){
        if (L>R) return -1;
        int pLess = L-1;
        int pGreater = R;
        int pCurr = L;
        while (pCurr < R){
            if (arr[pCurr] <= arr[R]){
                swap(arr, pCurr, ++pLess);
            }
            pCurr++;
        }
        swap(arr, R, ++pLess);
        return pLess;
    }

    /**
     * 将arr数组上L到R位置按照R位置上的值,划分为左边小于等于R,右边大于R.要求左侧最后一个数等于R.
     * 返回小于等于区的位置.
     * @param arr
     * @param L
     * @param R
     * @return
     */
    private static int partion2(int[] arr, int L, int R){
        int V = arr[R];
        int pLess = L-1; // 小于等于区的指针
        int pCurr = L; // 当前位置指针
        while (pCurr<R){
            if (arr[pCurr]<=V){ // 如果当前值小于等于V,将当前值与小于等于区的下一个值做交换,小于等于区向右一位.
                swap(arr, pCurr, ++pLess);
            }
            pCurr++; // 当前指针移动到下一位
        }
        // 将R位置的数与小于等于区下一位置的数交换.V来到小于等于区最右边.
        swap(arr, ++pLess, R);
        return pLess;
    }

    /**
     * 荷兰国旗2.0
     * @param arr
     * @return
     */
    private static int[] netherlandsFlag1_0(int[] arr, int L, int R){
        if (L>R) return new int[]{-1, -1};
        int pLess = L-1;
        int pGreater = R;
        int pCurr = L;
        while (pCurr < pGreater){
            if (arr[pCurr] < arr[R]){
                swap(arr, pCurr++, ++pLess);
            } else if (arr[pCurr] == arr[R]){
                pCurr++;
            } else {
                swap(arr, pCurr, --pGreater);
            }
        }
        swap(arr, arr.length-1, pGreater);

        return new int[]{pLess+1, pGreater};
    }
}
