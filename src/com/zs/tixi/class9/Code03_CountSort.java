package com.zs.tixi.class9;

/*  3. 计数排序
 *      要求：样本是整数，且范围比较窄。
 *      方案：已知要求排序的数据为N-M范围内的整数，从N到M每个整数创建一个容器。
 *          将数据每个数放到对应容器里。从N-M依次取出数据，即排序完成。
 */
public class Code03_CountSort {

    /**
     * 对于数组arr。
     * 找到arr上的最小值min和最大值max。
     * 创建一个max-min+1大小的数组bucket，
     * 遍历arr：
     *      将arr[i]转化为bucket的下标，currIndex。
     *      bucket[currIndex]++
     * 初始化arrIndex=0;
     * 遍历bucket：
     *      转化bucket的i位置到对应arr的值arrVal。
     *      循环：bucket[i]-- >0
     *          arr[arrIndex++] = arrVal
     * @param arr
     */
    public static void countSort(int[] arr){
        if (arr == null || arr.length<2) return;

        int max = arr[0];
        int min = arr[0];
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
            min = Math.min(min, arr[i]);
        }

        int[] bucket = new int[max-min+1];

        for (int i = 0; i <arr.length; i++) {
            int currIndex = arr[i] - min;
            bucket[currIndex]++;
        }

        int arrIndex = 0;

        for (int i = 0; i < bucket.length; i++) {
            while (bucket[i]-- > 0){
                arr[arrIndex++] = min+i;
            }
        }
    }
}
