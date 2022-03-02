package com.zs.tixi.class38;

/**
 * 讲解有序表相关的面试题
 *
 * 讲解改写有序表的题目核心点
 *
 * 题目：
 *
 * 给定一个数组arr，和两个整数a和b（a<=b）。求arr中有多少个子数组，累加和在[a,b]这个范围上。返回达标的子数组数量
 *
 * 有一个滑动窗口：
 * 1）L是滑动窗口最左位置、R是滑动窗口最右位置，一开始LR都在数组左侧
 * 2）任何一步都可能R往右动，表示某个数进了窗口
 * 3）任何一步都可能L往右动，表示某个数出了窗口
 * 想知道每一个窗口状态的中位数
 *
 * 设计一个结构包含如下两个方法：
 * void add(int index, int num)：把num加入到index位置
 * int get(int index) ：取出index位置的值
 * void remove(int index) ：把index位置上的值删除
 * 要求三个方法时间复杂度O(logN)
 *
 * 假设有打乱顺序的一群人站成一个队列，数组people表示队列中一些人的属性（不一定按顺序）
 * 每个people[i]=[hi, ki]表示第i个人的身高为hi，前面正好有ki个身高大于或等于hi的人
 * 请你重新构造并返回输入数组people所表示的队列，返回的队列应该格式化为数组queue
 * 其中queue[j]=[hj, kj]是队列中第j个人的属性（queue[0] 是排在队列前面的人）。
 * Leetcode题目：https://leetcode.com/problems/queue-reconstruction-by-height/
 */
public class T {
}
