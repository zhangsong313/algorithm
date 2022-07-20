package com.zs.tixi.class5;

import java.util.function.Function;

import static com.zs.xiaobai.common.MyCompValue.printArr;
import static com.zs.xiaobai.common.MyCompValue.printErr;
import static com.zs.xiaobai.common.MyCompValue.randomIntArr;
import static com.zs.xiaobai.common.MyCompValue.times;

/**
 * 归并排序:O(N*logN)
 * 0.非递归实现归并
 * 1、小和问题，要求O（N*logN）:在数组arr中,每个数左边所有小于自己的数之和,求出总和.
 * 2、逆序对个数问题.:逆序对:在数组arr中,左侧数大于右侧数(不要求相邻)的两个数称为逆序对.求arr中逆序对总个数.
 * 3.大于两倍个数问题:在数组arr中,左侧数比右侧数(不要求相邻)两倍大的个数,求出总和.
 */
public class T {
    /**
     * 笔记整理
     * 1. 归并排序
     *      mergeSort：
     *         // 核心操作：找到中点，对左侧递归排序，对右侧递归排序。调用merge函数合并两侧使得有序。
     *         // 如果l==r，直接返回
     *      merge函数：
     *         // 核心操作：创建help数组，从左到右以此对比左右两部分的值，优先将左侧较小的值放入help数组。
     *         // 需要指针p1指向左部分起始位置， p2指向右部分起始位置, p3指向help数组起始位置。
     *         // 将左部分或右部分剩余的数依次放入help数组。
     *         // 将help内的数放回arr。
     *      非递归mergeSort：
     * 2. 小和问题
     *         题目：要求O（N*logN）:在数组arr中,每个数左边所有小于自己的数之和,求出总和.
     *         思路：归并排序的过程就是对比左右部分的值，可以在归并排序的过程中收集小和。
     *          process函数：排序同时收集小和。
     *              // 核心操作：找到中点， 左侧递归排序求小和，右侧递归排序求小和， merge合并过程求小和， 将三个结果相加得到答案。
     *              // 如果l==r， 返回0
     *          merge函数：合并左右有序部分，同时收集小和。
     *              // 核心操作：定义help数组收集左部分较小值，右部分较小值。在这个过程中每当左部分有数放入help，
     *              // 可以得到该数比右侧部分哪些数小。收集该数比右侧数小的和：该数乘以右侧大于它的个数。
     *              // 定义p1指向左部分起始位置， p2指向右部分起始位置，p3指向help起始位置， ans用来收集答案。
     *              // 左侧或右侧还有剩余数，依次放入help数组。
     *              // help数组的值依次填入arr相应位置。
     * 3. 逆序对问题
     *      题目：逆序对:在数组arr中,左侧数大于右侧数(不要求相邻)的两个数称为逆序对.求arr中逆序对总个数.
     *      思路：归并排序的过程就是对比左右部分的值，可以在归并排序的过程中收集逆序对。
     *      process函数：排序同时收集逆序对。
     *          // 核心操作：找到中点， 左侧递归排序求逆序对，右侧递归排序求逆序对， merge合并过程求逆序对， 将三个结果相加得到答案。
     *          // 如果l==r, 返回0
     *      merge函数：合并左右有序部分，同时收集逆序对。
     *         // 核心操作：定义help数组作为辅助，从左到右，优先收集右侧小于左侧的值。
     *         // 每当右侧有数放入help，统计p1到m位置的个数，累加到答案中。
     *         // 定义p1指向左部分开始位置，p2指向右部分开始位置，p3指向help开始位置， ans用来收集答案。
     *         // 左侧或右侧还有剩余数，依次放入help数组。
     *         // help数组的值依次填入arr相应位置。
     * 4. 大于两倍个数问题
     *      思路：归并排序的过程中左右部分已经有序，可以在合并左右部分过程中收集答案。
     *      process函数：排序同时收集答案。
     *         // 核心操作：找到中点， 左侧递归排序求结果，右侧递归排序求结果， merge合并过程求结果， 将三个结果相加得到答案。
     *         // 如果l==r, 返回0
     *      merge函数：合并左右有序部分，同时收集答案。
     *         // 核心操作：对于左部分每个位置，求右部分有多少个数的2倍小于它，由于左右部分有序，因此使用双指针，无需回退遍历即可统计出答案。
     *         // 统计完成后左右指针回归原始位置。
     *         // 剩余操作和归并排序完全相同。
     */




    public static void main(String[] args) {
//        MyCompValue.checkSort(10000, 100, 100, Code01_MergeSort::sort);

//        checkSmallSum(1000, 100, 100, Code02_SmallSum::smallSum);
//        checkReversPair(10000, 10, 10, Code03_ReversePair::reversePair);
        checkBiggerThanRightTwice(1000, 100,100,  Code04_BiggerThanRightTwice::biggerThanRightTwice);
    }

    /**
     * 小和问题对数器
     * @param times
     * @param arrMaxLength
     * @param arrMaxValue
     * @param smallSumFun
     */
    public static void checkSmallSum(int times , int arrMaxLength, int arrMaxValue, Function<int[], Integer> smallSumFun){
        times(times, ()->{
            int[] arr = randomIntArr(arrMaxLength, arrMaxValue);

            int ans = smallSumFun.apply(arr.clone());
            int ans2 = getSmallSum(arr.clone());
            if (ans != ans2){
                printArr(arr);
                printErr("小和问题测试失败 :", ans, ans2 );
            }
        });
    }

    /**
     * 逆序对问题对数器
     * @param times
     * @param arrMaxLength
     * @param arrMaxValue
     * @param reversPairFun
     */
    public static void checkReversPair(int times , int arrMaxLength, int arrMaxValue, Function<int[], Integer> reversPairFun){
        times(times, ()->{
            int[] arr = randomIntArr(arrMaxLength, arrMaxValue);

            int ans = reversPairFun.apply(arr.clone());
            int ans2 = getReversPair(arr.clone());
            if (ans != ans2){
                printArr(arr);
                printErr("逆序对问题测试失败 :", ans, ans2 );
            }
        });
    }

    /**
     * 大于两倍问题对数器
     * @param times
     * @param arrMaxLength
     * @param arrMaxValue
     * @param reversPairFun
     */
    public static void checkBiggerThanRightTwice(int times , int arrMaxLength, int arrMaxValue, Function<int[], Integer> reversPairFun){
        times(times, ()->{
            int[] arr = randomIntArr(arrMaxLength, arrMaxValue);

            int ans = reversPairFun.apply(arr.clone());
            int ans2 = getBiggerThanRightTwice(arr.clone());
            if (ans != ans2){
                printArr(arr);
                printErr("大于两倍问题测试失败 :", ans, ans2 );
            }
        });
    }

    /**
     * 暴力求大于两倍
     * @param arr
     * @return
     */
    private static int getBiggerThanRightTwice(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                ans += arr[j]*2<arr[i]?1:0;
            }
        }
        return ans;
    }

    /**
     * 暴力求逆序对
     * @param arr
     * @return
     */
    private static int getReversPair(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                ans += arr[j]<arr[i]?1:0;
            }
        }
        return ans;
    }

    /**
     * 暴力方法求得小和.
     * @param arr
     * @return
     */
    private static Integer getSmallSum(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                ans += arr[j]<arr[i]?arr[j]:0;
            }
        }
        return ans;
    }
}
