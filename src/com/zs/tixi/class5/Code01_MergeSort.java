package com.zs.tixi.class5;

/**
 * 归并排序,分别使用递归和非递归方法.
 */
public class Code01_MergeSort {

    public static void sort(int[] arr){
        if (arr == null || arr.length<2){
            return;
        }

//        mergeSort(arr, 0, arr.length-1);
        mergeSort2(arr);
    }

    /**
     * 将arr上l到r位置的数排有序.
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
     * @param arr
     * @param l
     * @param r
     */
    public static void merge(int[] arr, int l ,int m, int r){
//        System.out.println("l : "+l);
//        System.out.println("m : "+m);
//        System.out.println("r : "+r);
        int[] help = new int[r-l+1]; // 辅助数组
//        int m = l + (r-l>>2); // 找到中间位置m,分为左右两组.
        int p1 = l, p2=m+1; // 定义两个指针,分别指向左右组的起始位置

        int p3 = 0; // help数组的当前位置.
        // 两个指针位置的数比较大小,谁小谁的数放到help中,并移动指针.
        while (p1<=m && p2<= r){ // 两个指针都没有到达边界.
            help[p3++] = arr[p1]<arr[p2] ? arr[p1++] : arr[p2++];
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
