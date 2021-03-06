package com.zs.shuati.class04;

/**
 *题目：
 *
 * 1.数组为{3, 2, 2, 3, 1}，查询为(0, 3, 2)，意思是在数组里下标0~3这个范围上，有几个2？答案返回2
 * 假设给你一个数组arr，对这个数组的查询非常频繁，且都给了查询组，请返回所有查询的结果
 * 解：二分法：根据查询的数值将数据分组。组内使用二分找出指定范围。
 *
 * 2.返回一个数组中子数组最大累加和
 * 本题测试链接 : https://leetcode.com/problems/maximum-subarray/
 * 解：动态规划：从左往右模型
 *
 * 3.返回一个二维数组中子矩阵最大累加和
 * 本题测试链接 : https://leetcode-cn.com/problems/max-submatrix-lcci/
 * 解：动态规划：列出所有矩阵高度后，压缩后使用子数组最大累加和问题来解。
 *
 * 4.返回一个数组中所选数字不能相邻的情况下最大子序列累加和
 * 解：动态规划：从左往右模型
 *
 * 5.老师想给孩子们分发糖果，有N个孩子站成了一条直线，老师会根据每个孩子的表现，预先给他们评分
 * 你需要按照以下要求，帮助老师给这些孩子分发糖果：
 * 每个孩子至少分配到 1 个糖果。
 * 评分更高的孩子必须比他两侧的邻位孩子获得更多的糖果。
 * 那么这样下来，返回老师至少需要准备多少颗糖果
 * 测试链接 : https://leetcode.com/problems/candy/
 * 进阶：在原来要求的基础上，增加一个要求，相邻的孩子间如果分数一样，分的糖果数必须一样，返回至少需要准备多少颗糖果
 * 解：计算数据变化的左右坡度。
 *
 * 6.生成长度为size的达标数组，什么叫达标？对于任意的i<k<j，满足[i]+[j]!=[k]*2。给定一个正数size，返回长度为size的达标数组
 * 解：分治思路，将要求的数组划分为子数组，且解决子数组合并问题。解决合并思路使用奇偶性。
 *
 * 7.给定三个字符串s1、s2、s3，请你帮忙验证s3是否是由s1和s2交错组成的
 * Leetcode题目：https://leetcode.com/problems/interleaving-string/
 * 解：动态规划：样本对应模型。
 *
 * 8.大楼轮廓线问题
 * Leetcode题目：https://leetcode.com/problems/the-skyline-problem/
 * 解：有序表：将大楼抽象为左侧位置升高和右侧位置降低的数据。按坐标从小到大放入有序表。
 * 有序表使用高度为key，每次当前位置坐标数据都放入有序表后查询最大高度。
 */
public class T {
}
