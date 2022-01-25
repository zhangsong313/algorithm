package com.zs.tixi.class30;

import com.zs.xiaobai.common.MyCompValue;


/*
 * 题目:
 *      给定一个无序数组arr中,长度为N,给定一个正数k,返回topk个最大的数.
 *      不同时间复杂度三个方法:
 *      1.O(N*logN)
 *      2.O(N+K*logN)
 *      3.O(n+k*logk)
 */
public class Code02_MaxTopK {
    /**
     * O(N*logN)
     * 思路:
     *      排序O(N*logN)
     *      然后取前k个数
     *
     */
    public static int[] maxTopK(int[] arr, int k){
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        mergeSort(arr, 0, arr.length-1);
        k = Math.min(k, arr.length);
        int[] ans = new int[k];
        for (int i = arr.length-1,j=0; j<ans.length;) {
            ans[j++] = arr[i--];
        }
        return ans;
    }

    /**
     * 归并排序
     * 找到数组的中间位置,将左边排有序,将右边排有序,然后合并使得两边都有序.
     */
    private static void mergeSort(int[] arr, int L, int R) {
        if(L==R) return;
        int M = L+((R-L)>>1);
        mergeSort(arr, L, M);
        mergeSort(arr, M+1, R);
        merge(arr, L , M, R);
    }

    /**
     * l..m有序
     * m+1..r有序
     * 合并左右两组使得整体有序.
     *
     * 创建辅助数组help大小是l..r
     * 定义遍历左侧的指针pLeft
     * 定义遍历右侧的指针pRight
     * 循环:左右指针都没有到边界.
     *      如果左指针值小于等于右指针值.
     *          左指针值放入help,左指针右移.
     *      否则
     *          右指针值放入lelp,右指针右移.
     * 循环:左指针没有到达边界
     *      左指针值放入help,左指针右移
     * 循环:右指针没有到达边界
     *      右指针值放入help,右指针右移.
     * 将help数组值从左到右一次放入l..r范围.
     */
    private static void merge(int[] arr, int l, int m, int r) {
        int[] help = new int[r-l+1];
        int pLeft=l;
        int pRight=m+1;
        int index = 0;
        while (pLeft<=m && pRight<=r){
            help[index++] = arr[pLeft]<=arr[pRight]?arr[pLeft++]:arr[pRight++];
        }
        while (pLeft<=m){
            help[index++] = arr[pLeft++];
        }
        while(pRight<=r){
            help[index++] = arr[pRight++];
        }
        for (int i = 0; i < help.length; i++) {
            arr[l+i] = help[i];
        }
    }


    /**
     * O(N+K*logN)
     *
     * 建大根堆O(N)
     * 弹出k次,k*O(logN)
     */
    public static int[] maxTopK1(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        for (int i = arr.length-1; i >=0 ; i--) {
            heapify(arr, i, arr.length);
        }
        k = Math.min(k, arr.length);
        int[] ans = new int[k];
        int heapSize = arr.length;
        for (int i = 0; i < k; i++) {
            ans[i] = arr[0];
            MyCompValue.swap(arr, 0, --heapSize);
            heapify(arr, 0, heapSize);
        }
        return ans;
    }

    /**
     * O(n+k*logk)
     * 找到topK的值O(N)
     * 对topk之前的范围进行排序k*logk
     * 返回topK范围数据.
     */
    public static int[] maxTopK2(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        k = Math.min(arr.length, k);
        topK(arr,0, arr.length-1, arr.length-k);
        mergeSort(arr, arr.length-k,arr.length-1);

        int[] ans = new int[k];
        int index = 0;
        for (int i = arr.length-1; i >=arr.length-k ; i--) {
            ans[index++]  = arr[i];
        }
        return ans;
    }

    /**
     * 将topK的数调整到前K个位置.
     *
     */
    private static void topK(int[] arr,int l, int r, int k) {
        if(l==r) return;
        int cn = l+(int)(Math.random()*(r-l+1)); // 比较值位置

        MyCompValue.swap(arr, cn, r);
        int pLess = l-1;
        int pGreater = r;
        int cur = l;
        while (cur<pGreater){
            if(arr[cur]<arr[r]){
                MyCompValue.swap(arr, cur++, ++pLess);
            }else if(arr[cur]>arr[r]){
                MyCompValue.swap(arr, cur, --pGreater);
            }else {
                cur++;
            }
        }
        MyCompValue.swap(arr, r, pGreater++);
        if(k>pLess&&k<pGreater){
            return;
        }
        if(k<=pLess){
            topK(arr, 0, pLess, k);
        }else {
            topK(arr, pGreater, r, k);
        }
    }


    /**
     * 对arr,i位置执行heapify.
     * 堆大小为size
     */
    private static void heapify(int[] arr, int i, int size) {
        int left = i*2+1;
        while (left<size){
            int largest = left+1<size && arr[left+1]>arr[left]?left+1:left;
            if(arr[i]>=arr[largest]){
                return;
            }
            MyCompValue.swap(arr, i, largest);
            i = largest;
            left = i*2+1;
        }
    }


    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            // [-? , +?]
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 生成随机数组测试
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean pass = true;
        System.out.println("测试开始，没有打印出错信息说明测试通过");
        for (int i = 0; i < testTime; i++) {
            int k = (int) (Math.random() * maxSize) + 1;
            int[] arr = generateRandomArray(maxSize, maxValue);

            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int[] arr3 = copyArray(arr);

            int[] ans1 = maxTopK1(arr1, k);
            int[] ans2 = maxTopK(arr2, k);
            int[] ans3 = maxTopK2(arr3, k);
            if (!isEqual(ans1, ans2)
                    || !isEqual(ans1, ans3)
            ) {
                pass = false;
                System.out.println("出错了！");
                MyCompValue.printArr(arr1);
                System.out.println(k);
                MyCompValue.printArr(ans1);
                MyCompValue.printArr(ans2);
                MyCompValue.printArr(ans3);
                break;
            }
        }
        System.out.println("测试结束了，测试了" + testTime + "组，是否所有测试用例都通过？" + (pass ? "是" : "否"));
    }


}
