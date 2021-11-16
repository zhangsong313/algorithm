package com.zs.tixi.class5;

/**
 * 小和问题，要求O（N*logN）:在数组arr中,每个数左边所有小于自己的数之和,求出总和.
 */
public class Code02_SmallSum {

    public static int smallSum(int[] arr){
        if (arr == null || arr.length<2) return 0;

        return process(arr, 0, arr.length-1);
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
