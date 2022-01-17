package com.zs.tixi.class25;

/**
 * 滑动窗口
 *      滑动窗口是一种想象出来的结构:
 *      滑动窗口有左边界L和右边界R
 *      在数组或者字符串或者一个序列上,记为S,窗口就是S[L..R]这一部分
 *      L往左滑意味着一个样本出了窗口.R往右滑意味着一个样本进了窗口
 *      L和R都只能往右滑.
 *
 * 滑动窗口内的最大值和最小值的更新结构
 *      窗口不管是L还是R滑动之后,都会让窗口呈现新状况.
 *      如何能够更快的得到窗口当前状况下的最大值和最小值?
 *      最好平均下来复杂度能做到O(1)
 *      利用单调双端队列
 *
 * 题目一:
 *      假设一个固定大小为W的窗口,依次划过arr.
 *      返回每一次滑动状况的最大值
 *      例如:arr={4, 3, 5, 4, 3, 3, 6, 7} W=3
 *      返回:[5, 5, 5, 4, 6, 7]
 *
 * 题目二:
 *      给定一个整型数组arr,和一个整数num.
 *      某个arr中的子数组sub,如果想达标,必须满足:sub中最大值-sub中最小值<=num,
 *      返回arr中达标子数组的数量.
 *
 * 题目三:
 *      加油站的良好出发点问题.
 *      N个加油站组成一个环形,给定两个长度都是N的非负数组oil和dis(N>1),
 *      oil[i]代表第i个加油站存的油可以跑多少距离.dis[i]代表第[i]个加油站到
 *      下一个加油站的距离.假如一辆初始没有油的车从第i个加油站出发,
 *      最终能回到第i个加油站,那么第i个加油站就算良好出发点,否则就不算.
 *      请返回长度为N的布尔数组res,res[i]代表第i个加油站是否是良好出发点.
 *      要求时间复杂度O(n),空间复杂度O(1)
 *      测试链接：https://leetcode.com/problems/gas-station
 *
 * 题目四:
 *      arr是货币数组,其中的值都是正数.再给定一个正数aim.
 *      每个值都认为是一张货币.
 *      返回组成aim的最少货币数.
 *      注意:
 *      因为是求最少货币数,所以每一张货币认为是相同还是不同就不重要了.
 *
 * 代码技巧总结:
 *      L和R同时滑动时可以采用如下思路.
 *      1.当前来到一个合理区间（R向右移动过了，L向右移动过了）
 *      2.队列处理从R滑入数.(一定注意此处是while循环,要把比不过当前值的所有值都弹出)
 *      3.队列处理从L滑出数.
 *      4.获取合理区间的结果.
 *      5.L向左滑.
 *      6.R向右滑
 */
public class T {
}
