package com.zs.tixi.class9;

import com.zs.xiaobai.common.MyCompValue;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * 1. 前缀树：
 *      1）单个字符串中，字符从前到后的加到一棵多叉树上。
 *      2）字符放在路上，节点上有专属的数据项（常见的是pass和end值）
 *      3）所有样本都这样添加，如果没有路就新建，有路就复用。
 *      4）沿途节点的pass值加1，每个字符串结束时来到的节点end值加1.
 *
 *      功能接口：
 *          添加字符串到前缀树
 *          查询字符串在前缀树中有几个
 *          删除1个字符串
 *          查询符合指定前缀的字符串个数
 * 2. 不基于比较的排序
 *      桶排序思想下的排序：计数排序 & 基数排序
 *      1）桶排序思想下的排序都是不急于比较的排序
 *      2）时间复杂度为O(N)，额外空间复杂度为O(M)
 *      3）应用范围有限，需要样本的数据状况满足桶的划分。
 * 3. 计数排序
 *      要求：样本是整数，且范围比较窄。
 *      方案：已知要求排序的数据为N-M返回内的整数，从N到M每个整数创建一个容器。将数据每个数放到对应容器里。从N-M依次取出数据，即排序完成。
 * 4. 基数排序
 *      要求：样本是10进制的正整数。
 *      方案：准备0-9的十个栈，首先按照个位将数组中的数分别放入对应栈，将数据从0-9栈中一次取出放入数组。
 *      接下来按照十位，百位，千位。。。直到最大值的最大位数。即排序完成。
 * 5. 排序算法的稳定性。
 *      稳定性是指原数组中相同大小的数在经过排序后，仍然保持原先的相对顺序。
 *      对基础类型来说，稳定性毫无意义。
 *      对非基础类型来说，稳定性有重要意义。
 *      有些排序算法可以实现成稳定的，有些无论如何都无法实现成稳定的。
 *
 */
public class T {
    public static void main(String[] args) {
//        MyCompValue.checkSort(1000, 100 ,100, Code03_CountSort::countSort);
        checkRadixSort(10000, 100 ,100, Code04_RadixSort::radixSort);
    }

    public static void checkRadixSort(int times , int arrMaxLength, int arrMaxValue, Consumer<int[]> sortFun){
        MyCompValue.times(times, ()->{
            int[] arr = MyCompValue.randomPositiveIntArr(arrMaxLength, arrMaxValue);
            int[] copyArr = Arrays.copyOf(arr, arr.length);
            sortFun.accept(arr);
            if (MyCompValue.isSorted(arr) == false){
                System.out.println("排序有误");
                MyCompValue.printArr(copyArr);
                return;
            }
        });
    }
}
