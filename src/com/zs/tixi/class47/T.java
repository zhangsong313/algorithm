package com.zs.tixi.class47;

/**
 * 动态规划猜法中和外部信息简化的相关问题（上）、哈夫曼树
 *
 * 内容：
 *
 * 以18节做总纲
 *
 * 有些动态规划面试题，需要很好的设计参数，这种设计方式都有"外部信息简化"的特征
 *
 * 哈夫曼树
 *
 * 题目：
 *
 * 有n个气球，编号为0到n-1，每个气球上都标有一个数字，这些数字存在数组nums中
 * 现在要求你戳破所有的气球。戳破第i个气球，你可以获得nums[i - 1] * nums[i] * nums[i + 1] 枚硬币
 * 这里的i-1和i+1代表和i相邻的、没有被戳爆的！两个气球的序号
 * 如果i-1或i+1超出了数组的边界，那么就当它是一个数字为1的气球
 * 求所能获得硬币的最大数量
 * Leetcode题目：https://leetcode.com/problems/burst-balloons/
 *
 * 给出一些不同颜色的盒子，盒子的颜色由数字表示，即不同的数字表示不同的颜色，你将经过若干轮操作去去掉盒子
 * 直到所有的盒子都去掉为止，每一轮你可以移除具有相同颜色的连续k个盒子（k >= 1）
 * 这样一轮之后你将得到 k * k 个积分，当你将所有盒子都去掉之后，求你能获得的最大积分和
 * Leetcode题目：https://leetcode.com/problems/remove-boxes/
 *
 * 如果一个字符相邻的位置没有相同字符，那么这个位置的字符出现不能被消掉
 * 比如:"ab"，其中a和b都不能被消掉
 * 如果一个字符相邻的位置有相同字符，就可以一起消掉
 * 比如:"abbbc"，中间一串的b是可以被消掉的，消除之后剩下"ac"
 * 某些字符如果消掉了，剩下的字符认为重新靠在一起
 * 给定一个字符串，你可以决定每一步消除的顺序，目标是请尽可能多的消掉字符，返回最少的剩余字符数量
 * 比如："aacca", 如果先消掉最左侧的"aa"，那么将剩下"cca"，然后把"cc"消掉，剩下的"a"将无法再消除，返回1
 * 但是如果先消掉中间的"cc"，那么将剩下"aaa"，最后都消掉就一个字符也不剩了，返回0，这才是最优解。
 * 再比如："baaccabb"，
 * 如果先消除最左侧的两个a，剩下"bccabb"，如果再消除最左侧的两个c，剩下"babb"，最后消除最右侧的两个b，剩下"ba"无法再消除，返回2
 * 而最优策略是：
 * 如果先消除中间的两个c，剩下"baaabb"，如果再消除中间的三个a，剩下"bbb"，最后消除三个b，不留下任何字符，返回0，这才是最优解
 *
 * 给定一个数组arr，和一个正数M，返回在arr的子数组在长度不超过M的情况下，最大的累加和
 *
 * 哈夫曼树的实现
 */
public class T {
}