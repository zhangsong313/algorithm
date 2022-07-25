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
     * 荷兰国旗1.0
     * 将arr数组上L到R位置按照R位置上的值,划分为左边小于等于R,右边大于R.要求左侧最后一个数等于R.
     * 返回小于等于区的右边界位置
     */
    private static int netherlandsFlag1_0(int[] arr, int L, int R){
        // 核心：定义小于等于区域的指针和当前位置指针，遍历数组，
        // 每遇到小于等于最右值的数，与小于等于区域的下一位置交换，小于等于区域右扩。
        // 直到当前位置来到最右侧位置结束。
        // 交换最右侧值与小于等于区域下一位置，小于等于区域右扩。
        // 返回小于等于区域指针。
        int pLess = L-1;
        int pCurr = L;
        while (pCurr<R){
            if(arr[pCurr]<=arr[R]){
                swap(arr, pCurr, ++pLess);
            }
            pCurr++;
        }
        swap(arr, ++pLess, R);
        return pLess;
    }

    private static void process1_0(int[] arr, int L, int R){
        if(L>=R) return;
        int pLessEquals = netherlandsFlag1_0(arr, L, R);
        process1_0(arr, L, pLessEquals-1);
        process1_0(arr, pLessEquals+1, R);
    }

    public static void quickSort1_0(int[] arr){
        // 核心：将数组按最右侧值分区，得到分界点i，i的左侧小于等于i位置的值，i的右侧大于i位置的值。
        // 递归对i左侧位置分区，递归对i右侧位置分区，直到分区长度为0.
        // 定义process函数做递归处理，base case为L>=R
        // 定义分区函数，将数组指定范围按照最右侧值分为左右两区域。
        if(arr==null || arr.length<2) return;
        process1_0(arr, 0, arr.length - 1);
    }

    /**
     * 荷兰国旗2.0
     * 将arr在L到R上，划分为三部分，左部分小于arr[R]，中间部分等于arr[R], 右部分大于arr[R]
     * 要求返回等于区的左右边界位置。
     * 定义pLess=L-1，表示小于区的边界位置
     * 定义pGreater = R，表示大于区的边界位置（刚开始R位置数不参与排序）
     * 定义pCurr=L，表示当前处理的位置
     * 循环(pCurr < pGreater) (当前位置在大于区外)
     *      如果arr[pCurr]小于arr[R]
     *          交换pCurr和pLess+1位置的数
     *          pCurr和pLess来到下一位置来到下一位置
     *      如果arr[pCurr]等于arr[R]
     *          pCurr来到下一位置
     *      如果arr[pCurr]大于arr[R]
     *          交换pCurr和pGreater-1位置的值
     *          pGreatr来到左一位置
     * 交换R和pGreater位置，
     * 返回pless+1, pGreater
     * @param arr
     * @return
     */
    private static int[] netherlandsFlag2_0(int[] arr, int L, int R){
        // 核心：定义小于区域的指针和大于区域的指针和当前位置指针，遍历数组，
        // 每遇到小于最右值的数，与小于区域的下一位置交换，小于区域右扩，当前指针到下一位置
        // 每遇到等于最右值的数，当前指针来到下一个位置。
        // 每遇到大于最右值的数，与大于区域的前一位置交换，大于区域左扩，当前位置不动。
        // 直到当前位置来到大于区域位置结束。
        // 交换最右侧值与大于区域位置， 大于区域右扩。
        // 返回小于区域的右侧位置和大于区域的左侧位置。
        if(L>R) return new int[]{-1, -1};
        int pLess = L-1;
        int pGreater = R;
        int pCurr = L;
        while (pCurr<pGreater){
            if(arr[pCurr]<arr[R]){
                swap(arr, ++pLess, pCurr++);
            }else if(arr[pCurr]==arr[R]){
                pCurr++;
            }else {
                swap(arr, pCurr, --pGreater);
            }
        }
        swap(arr, R, pGreater);
        return new int[]{pLess+1, pGreater};
    }

    public static void quickSort2_0(int[] arr){
        // 核心：将数组按最右侧值分区，得到等于区域，等于区域的左侧小于最右值，等于区域的右侧大于最右值。
        // 递归对等于区域左侧分区，递归对等于区域右侧分区，直到分区长度为0.
        // 定义process函数做递归处理，base case为L>=R
        // 定义分区函数，将数组指定范围按照最右侧值分为左中右两区域。将中间等于区域的边界值返回.
        if(arr==null || arr.length<2) return;
        process2_0(arr, 0, arr.length-1);
    }

    private static void process2_0(int[] arr, int L, int R) {
        if(L>=R) return;
        int[] equalsArea = netherlandsFlag2_0(arr, L, R);
        process2_0(arr, L, equalsArea[0]-1);
        process2_0(arr, equalsArea[1]+1, R);
    }

    public static void quickSort(int[] arr){
        // 核心：将最右侧值与一个随机位置互换。然后数组按最右侧值分区，得到等于区域，等于区域的左侧小于最右值，等于区域的右侧大于最右值。
        // 递归对等于区域左侧分区，递归对等于区域右侧分区，直到分区长度为0.
        // 定义process函数做递归处理，base case为L>=R
        // 定义分区函数，将数组指定范围按照最右侧值分为左中右两区域。将中间等于区域的边界值返回.
        if(arr==null || arr.length<2) return;
        process3_0(arr, 0, arr.length-1);
    }

    private static void process3_0(int[] arr, int L, int R) {
        if(L>=R) return;
        swap(arr, R, L+(int)Math.random()*(R-L+1));
        int[] equalsArea = netherlandsFlag2_0(arr, L, R);
        process2_0(arr, L, equalsArea[0]-1);
        process2_0(arr, equalsArea[1]+1, R);
    }
}
