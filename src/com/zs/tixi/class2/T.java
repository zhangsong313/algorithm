package com.zs.tixi.class2;

/**
 * 内容：
 *
 * 评估算法优劣的核心指标
 *
 * 时间复杂度、空间复杂度、估算方式、意义
 *
 * 常数时间的操作
 *
 * 选择排序、冒泡排序、插入排序
 *
 * 最优解
 *
 * 对数器
 *
 * 二分法
 *
 * 题目：
 *
 * 1 选择排序及其对数器验证
 *
 * 2 冒泡排序及其对数器验证
 *
 * 3 插入排序及其对数器验证
 *
 * 4 有序数组中找到num
 *
 * 5 有序数组中找到>=num最左的位置
 *
 * 6 有序数组中找到<=num最右的位置
 *
 * 7 局部最小值问题
 * 定义何为局部最小值：
 * arr[0] < arr[1]，0位置是局部最小；
 * arr[N-1] < arr[N-2]，N-1位置是局部最小；
 * arr[i-1] > arr[i] < arr[i+1]，i位置是局部最小；
 * 给定一个数组arr，已知任何两个相邻的数都不相等，找到随便一个局部最小位置返回
 */
public class T {

    /**
     * 笔记整理:
     *
     * 一、基础排序
     *  1. 选择排序
     *  从i..n-1范围内找出最小值，将最小值与i位置交换。
     *  时间复杂度：O(N^2)
     *  空间复杂度：O(1)
     *  排序稳定性：无。由于每次i位置的数被交换到i..n-1范围内最小值位置，最小值位置前可能有与i相等的数。
     *
     *  2. 插入排序
     *  0..i-1范围已经有序，i持续向左移动，如果arr[i]小于arr[i-1], 交换i和i-1的值。
     *  时间复杂度: O(N^2)
     *  空间复杂度：O(1)
     *  排序稳定性：有。
     *
     *  3. 冒泡排序
     *  想办法i位置为i..N-1范围内的最小值，但不是选出最小值位置交换（和选择排序不同），而是从N-1到i+1，如果比左侧数小就交换。
     *  时间复杂度: O（N^2）
     *  空间复杂度: O(1)
     *  排序稳定性: 有
     *
     * 二、二分查找
     *  1. 有序数组二分查找指定值:
     *      过程：
     *          要求左边界不能大于右边界，
     *          如果中点是要找的值，直接返回，
     *          如果比目标值大，右边界移动到中点左侧，否则左边界哟东到中点右侧。
     *      时间复杂度：O(logN)
     *      空间复杂度: O(1)
     *
     *  2. 有序数组中找到>=num最左的位置
     *      过程：按照中点>=num和中点<num判断。
     *
     *  3. 有序数组中找到<=num最右的位置
     *      过程：按照中点<=num和中点>num判断
     *
     *  4. 局部最小值问题
     *      定义何为局部最小值：
     *      arr[0] < arr[1]，0位置是局部最小；
     *      arr[N-1] < arr[N-2]，N-1位置是局部最小；
     *      arr[i-1] > arr[i] < arr[i+1]，i位置是局部最小；
     *      给定一个数组arr，已知任何两个相邻的数都不相等，找到随便一个局部最小位置返回
     *
     *      过程:
     *          长度为1，直接返回
     *          判断左边界和右边界。
     *          由于arr[0]>arr[1] 且 arr[N-2]<arr[N-1]， 两侧都是向中间下降趋势，因此中间必定有个位置是局部极小值。
     *          如果中点符合条件直接返回。
     *          否则，找到arr[mid-1]< arr[mid]作为右边界。
     *          或者,找到arr[mid+1]<arr[mid]作为左边界。
     */
}
