package com.zs.tixi.class26;

/**
 * 单调栈是什么?
 *      一种特别设计的栈结构,为了解决如下的问题:
 *      给定一个可能含有重复值的数组arr,i位置的数一定存在如下两个信息.
 *      1) arr[i]的左侧离i最近并且小于arr[i]的数在哪?
 *      2) arr[i]的右侧离i最近并且小于arr[i]的数在哪?
 *      如果想要得到arr中所有位置的这两个信息,怎么才能让得到信息的过程尽量快.
 *      那么到底怎么设计呢?
 *
 * 题目一:
 *      单调栈的实现
 *
 * 题目二:
 *      给定一个只包含正数的数组arr,arr中的任何一个子数组sub.
 *      一定都可以算出(sub累加和)*(sub中的最小值)是什么,
 *      那么所有子数组中,这个值最大是多少?
 *
 * 题目三:
 *      给定一个非负数组arr,代表直方图,返回直方图的最大长方形面积.
 *      https://leetcode.com/problems/largest-rectangle-in-histogram
 *
 * 题目四:
 *      给定一个二维数组matrix,其中的值不是0就是1,
 *      返回全部由1组成的最大子矩形,内部有多少个1.
 *      测试链接：https://leetcode.com/problems/maximal-rectangle/
 *
 * 题目五:
 *      给定一个二维数组matrix,其中的值不是0就是1.
 *      返回全部由1组成的子矩形数量.
 *      测试链接：https://leetcode.com/problems/count-submatrices-with-all-ones
 */
public class T {
}
