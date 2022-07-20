package com.zs.tixi.class5;

import com.zs.xiaobai.common.MyCompValue;

/**
 * 归并排序,分别使用递归和非递归方法.
 */
public class Code01_MergeSort {

    public static void main(String[] args) {
        MyCompValue.checkSort(10000, 99, 99, Code01_MergeSort::sort);
    }

    public static void sort(int[] arr){
        if (arr == null || arr.length<2){
            return;
        }

        mergeSort(arr, 0, arr.length-1);
//        mergeSort3(arr, 0, arr.length-1);
//        mergeSort2(arr);
    }
    public static void mergeSort3(int[] arr, int l , int r){
        // 核心操作：找到中点，对左侧递归排序，对右侧递归排序。调用merge函数合并两侧使得有序。
        // 如果l==r，直接返回
        if(l==r) return;
        int m = l + (r-l >> 1);
        mergeSort3(arr, l, m);
        mergeSort3(arr, m+1, r);
        merge3(arr, l, m, r);
    }
    private static void merge3(int[] arr, int l , int m, int r){
        // 核心操作：创建help数组，从左到右以此对比左右两部分的值，优先将左侧较小的值放入help数组。
        // 需要指针p1指向左部分起始位置， p2指向右部分起始位置, p3指向help数组起始位置。
        // 将左部分或右部分剩余的数依次放入help数组。
        // 将help内的数放回arr。
        int[] help = new int[r-l+1];
        int p1=l, p2=m+1, p3=0;
        while (p1<=m && p2<=r){
            help[p3++] = arr[p1]<=arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1<=m){
            help[p3++] = arr[p1++];
        }
        while (p2<=r){
            help[p3++] = arr[p2++];
        }
        for (int i = 0; i < help.length; i++) {
            arr[l+i] = help[i];
        }
    }

    /**
     * 将arr上l到r位置的数排有序.
     * 如果l大于等于r，无需排序直接返回。
     * 找到l和r的中点位置m
     * 对l..m范围，使用mergeSort排有序.
     * 对m+1..r范围,使用mergeSort排有序.
     * 调用merge方法,合并左组和右组，使得整体有序。
     * @param arr
     * @param l
     * @param r
     */
    public static void mergeSort(int[] arr, int l , int r){
        if(l>=r) return;

        int m = l +((r-l)>>1); // 找到中点位置
        mergeSort(arr, l, m);// 左组有序
        mergeSort(arr, m+1, r);// 右组有序
        merge(arr, l, m, r);// 合并两个有序数组,使得整体有序.
    }

    /**
     * 将arr上l到r位置的数排有序.(非递归)
     * @param arr
     */
    public static void mergeSort2(int[] arr){
        if (arr == null || arr.length<2) return;

        int mergeSize = 1;
        int N = arr.length;
        while (mergeSize<N) {

            int L = 0;
            while (L<N){

//                if (mergeSize>=N-L) {
//                    break;
//                }

                int M = L+mergeSize-1;

                if (M>=N-1){break;} // 如果中点在数组边界或以外退出.

                int R = Math.min(M+mergeSize, N-1);

                merge(arr, L, M, R);

                L = R+1;
            }

            if (mergeSize > N/2){
                break;
            }
            mergeSize <<= 1;
        }
    }

    /**
     * arr的l到m上有序,m+1到r上有序.合并两个有序数组,使得l到r上整体有序.
     *
     * 创建一个r-l+1大小的辅助数组help
     * 定义p1=l, p2=m+1。分别表示左组和右组上两个可以移动的指针。
     * 定义p3-0，表示help数组上可以移动的指针
     * 循环（p1<=m, p2<=r） ,当左组和右组指针都没有到达边界时。
     *      将p1和p2位置上较小的数放在help的p3位置上。
     *      如果相等取p1位置上的数（此处只能取p1位置上的数，才能保证算法稳定性）
     *      p1和p2中较小数的那个指针向后移动一位,p3向后移动一位。
     * 循环(p1没有到m边界)
     *      将p1位置数放到p3位置。
     *      p1，p3均向后移动一位
     * 循环(p2没有到r边界)
     *      将p2位置数放到p3位置
     *      p2,p3均向后移动一位
     * 将help数组拷贝到l..r上对应位置.
     * @param arr
     * @param l
     * @param r
     */
    public static void merge(int[] arr, int l ,int m, int r){
        int[] help = new int[r-l+1]; // 辅助数组
        int p1 = l, p2=m+1; // 定义两个指针,分别指向左右组的起始位置

        int p3 = 0; // help数组的当前位置.
        // 两个指针位置的数比较大小,谁小谁的数放到help中,并移动指针.
        while (p1<=m && p2<= r){ // 两个指针都没有到达边界.
            help[p3++] = arr[p1]<=arr[p2] ? arr[p1++] : arr[p2++];
        }
        // 左组不越界.
        while (p1<=m){
            help[p3++] = arr[p1++];
        }
        // 右组不越界.
        while (p2<=r){
            help[p3++] = arr[p2++];
        }
        // 拷贝help到arr的l到r位置.
        for (int i = 0; i < help.length; i++) {
            arr[l+i] = help[i];
        }
    }
}
