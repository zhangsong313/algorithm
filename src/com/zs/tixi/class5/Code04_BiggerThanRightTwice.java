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
        // 核心操作：找到中点， 左侧递归排序求结果，右侧递归排序求结果， merge合并过程求结果， 将三个结果相加得到答案。
        // 如果l==r, 返回0
        if(L==R) return 0;
        int M = L + ((R-L)>>1);
        return process(arr, L, M)+
                process(arr, M+1 , R)+
                merge2(arr, L, M, R);
    }

    private static int merge2(int[] arr, int l, int m, int r){
        // 核心操作：对于左部分每个位置，求右部分有多少个数的2倍小于它，由于左右部分有序，因此使用双指针，无需回退遍历即可统计出答案。
        // 统计完成后左右指针回归原始位置。
        // 剩余操作和归并排序完全相同。
        int[] help = new int[r-l+1];
        int p1=l, p2=m+1, p3=0, ans=0;
        while (p1<=m){
            while (p2<=r && arr[p2]*2 < arr[p1]){
                p2++;
            }
            ans+= p2-m-1;
            p1++;
        }
        p1=l;
        p2=m+1;

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
        return ans;
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
