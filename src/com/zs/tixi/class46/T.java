package com.zs.tixi.class46;

/**
 * 后缀数组解决的面试题
 *
 * 内容：
 *
 * 通过题目进一步熟悉DC3算法
 *
 * 通过DC3算法得到height数组
 *
 * 题目：
 *
 * 给定两个字符串str1和str2，想把str2整体插入到str1中的某个位置，形成最大的字典序，返回字典序最大的结果
 *
 * 给两个长度分别为M和N的整型数组nums1和nums2，其中每个值都不大于9，再给定一个正数K。 你可以在nums1和nums2中挑选数字，要求一共挑选K个，并且要从左到右挑。返回所有可能的结果中，代表最大数字的结果
 *
 * 最长公共子串问题是面试常见题目之一，假设str1长度N，str2长度M
 * 一般在面试场上回答出O(N*M)的解法已经是比较优秀了
 * 因为得到O(N*M)的解法，就已经需要用到动态规划了
 * 但其实这个问题的最优解是O(N+M)，需要用到后缀数组+height数组
 * 课上将对本题解法代码进行详解
 */
public class T {
}
