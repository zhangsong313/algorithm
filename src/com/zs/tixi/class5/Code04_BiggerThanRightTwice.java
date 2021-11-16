package com.zs.tixi.class5;

/**
 * 大于两倍个数问题:在数组arr中,左侧数比右侧数(不要求相邻)两倍大的个数,求出总和.
 */
public class Code04_BiggerThanRightTwice {
    public static int biggerThanRightTwice(int[] arr){
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
     *  合并左右两组
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
        for (int i = L; i <=M; i++) {
            while (pRight <= R && arr[pRight]*2 < arr[i]){
                pRight++;
            }
            ans += pRight-1-M;
        }
        pRight = M+1;

        while (pLeft<=M && pRight<=R){
            if (arr[pLeft]<=arr[pRight]){
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
