package com.zs.tixi.class48;

/**
 * 动态规划猜法中和外部信息简化的相关问题（下）、最大网络流算法之Dinic算法
 *
 * 内容：
 *
 * 进一步解决带有"外部信息简化"特征的动态规划
 *
 * Dinic算法
 *
 * 题目：
 *
 * 有台奇怪的打印机有以下两个特殊要求：
 * 打印机每次只能打印由同一个字符组成的序列。
 * 每次可以在任意起始和结束位置打印新字符，并且会覆盖掉原来已有的字符。
 * 给你一个字符串s，你的任务是计算这个打印机打印它需要的最少打印次数。
 * Leetcode题目：https://leetcode.com/problems/strange-printer/
 *
 * 整型数组arr长度为n(3 <= n <= 10^4)，最初每个数字是<=200的正数且满足如下条件：
 * 1. 0位置的要求：arr[0]<=arr[1]
 * 2. n-1位置的要求：arr[n-1]<=arr[n-2]
 * 3. 中间i位置的要求：arr[i]<=max(arr[i-1],arr[i+1])
 * 但是在arr有些数字丢失了，比如k位置的数字之前是正数，丢失之后k位置的数字为0
 * 请你根据上述条件，计算可能有多少种不同的arr可以满足以上条件
 * 比如 [6,0,9] 只有还原成 [6,9,9]满足全部三个条件，所以返回1，即[6,9,9]达标
 *
 * Dinic算法详解
 * 测试链接：https://lightoj.com/problem/internet-bandwidth
 */
public class T {
}
