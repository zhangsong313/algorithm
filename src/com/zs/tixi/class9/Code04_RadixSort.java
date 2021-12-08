package com.zs.tixi.class9;

/*
 * 4. 基数排序
 *      要求：样本是10进制的正整数。
 *      方案：准备0-9的十个栈，首先按照个位将数组中的数分别放入对应栈，将数据从0-9栈中依次取出放入数组。
 *      接下来按照十位，百位，千位。。。直到最大值的最大位数。即排序完成。
 */
public class Code04_RadixSort {

    /**
     * arr中最大值的位数：maxLvl
     *      先找到数组中最大值maxVal。
     *      maxVal不为0情况下可以除10多少次，得到位数。
     * 循环1..maxLvl
     *      当前位currLvl
     *      创建一个辅助数组help，大小与arr相同。
     *      创建一个桶数组bucket，大小为10
     *      循环arr: i
     *          arr[i]在currLvl的数字: currLvlNum（通过除以pow(10, currLvl-1)，将数字移动到个位，然后对10取余）
     *          bucket[currLvlNum]++
     *      定义遍历tempSum=0;
     *      循环bucket:i
     *          tempSum+=bucket[i];
     *          bucket[i] = tempSum-bucket[i]（此时将每个桶第一个数应该放到help数组的哪个位置得到了）
     *      循环[L..R]:i
     *          arr[i]在currLvl的数字: currLvlNum
     *          将arr[i]放到help数组中bucket[currLvlNum]的位置
     *          bucket[currLvlNum]++
     *      将help数组复制给arr。
     * @param arr
     */
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length<2) return;

        int maxLvl = getMaxLvl(arr);
        for (int currLvl = 1; currLvl <=maxLvl ; currLvl++) {
            int[] help = new int[arr.length];
            int[] bucket = new int[10];
            for (int i = 0; i < arr.length; i++) {
                int currLvlNum = getLvlNum(arr[i], currLvl);
                bucket[currLvlNum]++;
            }
            int tempSum = 0;
            for (int i = 0; i < bucket.length; i++) {
                tempSum+=bucket[i];
                bucket[i] = tempSum-bucket[i];
            }
            for (int i = 0; i < arr.length; i++) {
                int currLvlNum = getLvlNum(arr[i], currLvl);
                help[bucket[currLvlNum]] = arr[i];
                bucket[currLvlNum]++;
            }
            int arrIndex = 0;
            for (int i = 0; i < help.length; i++) {
                arr[arrIndex++] = help[i];
            }
        }
    }

    /**
     * 通过除以pow(10, currLvl-1)，将数字移动到个位，然后对10取余
     */
    private static int getLvlNum(int i, int currLvl) {
        return (int)((i / Math.pow(10, currLvl-1))%10);
    }

    /**
     * arr中最大值的位数：maxLvl
     *      先找到数组中最大值maxVal。
     *      maxVal不为0情况下可以除10多少次，得到位数。
     */
    private static int getMaxLvl(int[] arr) {
        int maxVal = arr[0];
        for (int i = 1; i < arr.length; i++) {
            maxVal = Math.max(maxVal, arr[i]);
        }
        int maxLvl = 0;
        while (maxVal!=0){
            maxLvl++;
            maxVal /= 10;
        }
        return maxLvl;
    }
}