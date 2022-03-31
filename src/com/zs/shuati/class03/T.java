package com.zs.shuati.class03;

/**
 * 题目：
 *
 * 1. 求一个字符串中，最长无重复字符子串长度
 * 本题测试链接 : https://leetcode.com/problems/longest-substring-without-repeating-characters/
 * ==== ： 窗口或动态规划。
 * 注意：（1）只要题目提到了子串，子数组，立刻考虑以i位置为结尾的子串或子数组的情况。
 *
 * 2. 只由小写字母（a~z）组成的一批字符串，都放在字符类型的数组String[] arr中，如果其中某两个字符串所含有的字符种类完全一样
 * 就将两个字符串算作一类，比如baacbba和bac就算作一类，返回arr中有多少类
 * ==== ： 频次统计问题，且只关心出现还是未出现，用bitmap记录信息。去重用hashSet。
 *
 * 3. 给定一个只有0和1组成的二维数组，返回边框全是1（内部无所谓）的最大正方形面积
 * 测试链接 : https://leetcode.com/problems/largest-1-bordered-square/
 * ==== ： 点阵中的长方形或正方形问题
 * 注意：（1）N*N点阵中，长方形的种类数量级在N^4，正方形的种类数量级在N^3
 *      （2）遇到每种情况最终需要遍历解决的问题，考虑动态规划依赖之前的结果或者提前建立辅助数据。
 *
 * 4. 给定一个数组arr，代表每个人的能力值。再给定一个非负数k，如果两个人能力差值正好为k，那么可以凑在一起比赛
 * 一局比赛只有两个人，返回最多可以同时有多少场比赛
 * === ： 两数差固定配对问题。排序后滑动窗口解决。(复习滑动窗口)
 *
 * 5.给定一个正数数组arr，代表若干人的体重，再给定一个正数limit，表示所有船共同拥有的载重量，每艘船最多坐两人，且不能超过载重
 * 想让所有的人同时过河，并且用最好的分配方法让船尽量少，返回最少的船数
 * Leetcode链接 : https://leetcode.com/problems/boats-to-save-people/
 * === ： 两数和不超过指定值问题。排序后滑动窗口解决。
 *
 * 6. 给定整数数组nums和目标值goal，需要从nums中选出一个子序列，使子序列元素总和最接近goal
 * 也就是说如果子序列元素和为sum ，需要最小化绝对差abs(sum - goal)，返回 abs(sum - goal)可能的最小值
 * 注意数组的子序列是通过移除原始数组中的某些元素（可能全部或无）而形成的数组。
 * 本题测试链接 : https://leetcode.com/problems/closest-subsequence-sum/
 * // 本题数据量描述:
 * // 1 <= nums.length <= 40
 * // -10^7 <= nums[i] <= 10^7
 * // -10^9 <= goal <= 10^9
 * // 通过这个数据量描述可知，需要用到分治，因为数组长度不大
 * // 而值很大，用动态规划的话，表会爆
 * ==== : 参数值过大导致动态规划超时，但数据长度不大的情况下，可以分治解决。(复习根据数据量猜解法)
 *
 * 7. 电子游戏“辐射4”中，任务“通向自由”要求玩家到达名为“Freedom Trail Ring”的金属表盘，并使用表盘拼写特定关键词才能开门
 * 给定一个字符串 ring，表示刻在外环上的编码；给定另一个字符串 key，表示需要拼写的关键词。您需要算出能够拼写关键词中所有字符的最少步数
 * 最初，ring 的第一个字符与12:00方向对齐。您需要顺时针或逆时针旋转 ring 以使 key 的一个字符在 12:00 方向对齐，然后按下中心按钮，以此逐个拼写完 key 中的所有字符
 * 旋转 ring 拼出 key 字符 key[i] 的阶段中：
 * 您可以将 ring 顺时针或逆时针旋转一个位置，计为1步。旋转的最终目的是将字符串 ring 的一个字符与 12:00 方向对齐，并且这个字符必须等于字符 key[i] 。
 * 如果字符 key[i] 已经对齐到12:00方向，您需要按下中心按钮进行拼写，这也将算作 1 步。按完之后，您可以开始拼写 key 的下一个字符（下一阶段）, 直至完成所有拼写。
 * Leetcode题目：https://leetcode.com/problems/freedom-trail/
 * ==== ： 动态规划。每个位置选择一种选择到指定字符的方式，并加上按下动作的1步。返回最终的最少步数。
 * 注意:由于表盘上可能出现重复数字,所以会导致指定数字可能会有多个步数选择，当前选择又会影响之后的选择。所以需要尝试各种方法。
 * 总结顺时针旋转和逆时针旋转的代码。
 *
 * 8. 给定三个参数，二叉树的头节点head，树上某个节点target，正数K。从target开始，可以向上走或者向下走，返回与target的距离是K的所有节点
 * ==== ：图的BFS。（复习图相关知识）
 */
public class T {
}
