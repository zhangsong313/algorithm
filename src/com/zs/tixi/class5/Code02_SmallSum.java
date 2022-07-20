package com.zs.tixi.class5;

/**
 * 小和问题，要求O（N*logN）:在数组arr中,每个数左边所有小于自己的数之和,求出总和.
 */
public class Code02_SmallSum {

    public static void main(String[] args) {
//        MyCompValue.checkSort(1000, 99, 99, Code02_SmallSum::smallSum);
    }

    public static int smallSum(int[] arr){
        if (arr == null || arr.length<2) return 0;

        return process(arr, 0, arr.length-1);
    }
    private static int process2(int[] arr, int l, int r){
        // 核心操作：找到中点， 左侧递归排序求小和，右侧递归排序求小和， merge合并过程求小和， 将三个结果相加得到答案。
        // 如果l==r， 返回0
        if(l==r)return 0;
        int m = l + (r-l >> 1);
        return process2(arr, l, m)+ process2(arr, m+1, r)+ merge2(arr, l, m, r);
    }
    private static int merge2(int[] arr, int l, int m, int r){
        // 核心操作：定义help数组收集左部分较小值，右部分较小值。在这个过程中每当左部分有数放入help，
        // 可以得到该数比右侧部分哪些数小。收集该数比右侧数小的和：该数乘以右侧大于它的个数。
        // 定义p1指向左部分起始位置， p2指向右部分起始位置，p3指向help起始位置， ans用来收集答案。
        // 左侧或右侧还有剩余数，依次放入help数组。
        // help数组的值依次填入arr相应位置。
        int[] help = new int[r-l+1];
        int p1=l, p2=m+1, p3=0, ans=0;
        while (p1<=m && p2<=r){
            if(arr[p1] < arr[p2]){
                ans += arr[p1]*(r-p2+1);
                help[p3++] = arr[p1++];
            }else {
                help[p3++] = arr[p2++];
            }
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
        return ans;
    }

    /**
     * 将arr上L..R分为左右两组,分别执行process,然后merge使得数组整体有序.
     * @param arr
     * @param L
     * @param R
     * @return
     */
    private static int process(int[] arr, int L, int R) {
        if(L==R) return 0;
        int M = L + ((R-L)>>1);
        return process(arr, L, M)+
                process(arr, M+1 , R)+
                merge(arr, L, M, R);
    }

    /**
     *  合并左右两组数的同时,左组数进入copy数组的时候产生小和.
     * @param arr
     * @param L
     * @param M
     * @param R
     * @return
     */
    private static int merge(int[] arr, int L, int M, int R) {
        int pLeft = L;
        int pRight = M+1;
        int[] copy = new int[R-L+1];
        int pCopy=0;
        int ans = 0;
        while (pLeft<=M && pRight<=R){
            if (arr[pLeft]<arr[pRight]){
                ans += arr[pLeft]*(R-pRight+1);

                copy[pCopy++] = arr[pLeft++];
            } else {
                copy[pCopy++] = arr[pRight++];
            }
        }
        while (pLeft<=M){
            copy[pCopy++] = arr[pLeft++];
        }
        while (pRight<=R){
            copy[pCopy++] = arr[pRight++];
        }

        for (int i = 0; i < copy.length; i++) {
            arr[L+i] = copy[i];
        }

        return ans;
    }
}
