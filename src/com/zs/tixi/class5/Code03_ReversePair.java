package com.zs.tixi.class5;

/**
 * 逆序对个数问题.:逆序对:在数组arr中,左侧数大于右侧数(不要求相邻)的两个数称为逆序对.求arr中逆序对总个数.
 */
public class Code03_ReversePair {
    public static int reversePair(int[] arr){
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
     *  合并左右两组数的同时,计算逆序对.
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
            if (arr[pLeft]<=arr[pRight]){
                ans += pRight-1-M;
                copy[pCopy++] = arr[pLeft++];

            } else {
                copy[pCopy++] = arr[pRight++];
            }
        }
        while (pLeft<=M){
            ans += R-M;
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
