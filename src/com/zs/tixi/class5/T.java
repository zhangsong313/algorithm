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
    public static void main(String[] args) {
//        MyCompValue.checkSort(10000, 100, 100, Code01_MergeSort::sort);

//        checkSmallSum(1000, 100, 100, Code02_SmallSum::smallSum);
//        checkReversPair(1000, 10, 10, Code03_ReversePair::reversePair);
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
