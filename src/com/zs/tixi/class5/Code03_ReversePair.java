package com.zs.tixi.class5;

/**
 * 逆序对个数问题.:逆序对:在数组arr中,左侧数大于右侧数(不要求相邻)的两个数称为逆序对.求arr中逆序对总个数.
 */
public class Code03_ReversePair {
    public static int reversePair(int[] arr){
        if (arr == null || arr.length<2) return 0;

        return process2(arr, 0, arr.length-1);
//        return process(arr, 0, arr.length-1);
    }
    private static int process2(int[] arr, int l, int r){
        // 核心操作：找到中点， 左侧递归排序求逆序对，右侧递归排序求逆序对， merge合并过程求逆序对， 将三个结果相加得到答案。
        // 如果l==r, 返回0
        if(l==r) return 0;
        int m = l + (r-l >>1);
        return process2(arr, l, m) + process2(arr, m+1, r) + merge2(arr, l, m, r);
    }
    private static int merge2(int[] arr, int l, int m, int r){
        // 核心操作：定义help数组作为辅助，从左到右，优先收集右侧小于左侧的值。
        // 每当右侧有数放入help，统计p1到m位置的个数，累加到答案中。
        // 定义p1指向左部分开始位置，p2指向右部分开始位置，p3指向help开始位置， ans用来收集答案。
        // 左侧或右侧还有剩余数，依次放入help数组。
        // help数组的值依次填入arr相应位置。
        int[] help = new int[r-l+1];
        int p1=l, p2=m+1, p3=0, ans=0;
        while (p1<=m && p2<=r){
            if(arr[p2] < arr[p1]){
                ans += m-p1+1;
                help[p3++] = arr[p2++];
            }else {
                help[p3++] = arr[p1++];
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
