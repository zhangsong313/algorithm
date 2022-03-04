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
 *
 * 有序表改写总结：
 *  1.使用SBT改写。
 *  2.支持查询排序后第k个数且支持重复数据：
 *      1） 可以新增数据项all，表示有多少数经过了当前节点。改写需要注意同时调整size和all。add方法好改。delete方法不好改。
 *      2）可以包装一个Node类型用来保存key和index（相同key也要有不同的index序号），比较时，key相同情况下用index比较。
 *          这种方案有序表不用考虑重复数据的问题。好改写。
 *     3)自定义比较器要慎重写。尽量调用系统的compareTo方法。。。。否则有些边界问题自己考虑不到。
 */
public class T {
}
