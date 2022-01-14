package com.zs.tixi.class21;

/**
 * 题目一：[范围尝试模型]
 *      给定一个字符串str，返回这个字符串的最长回文子序列长度。
 *      比如：str="a12b3c43def2ghi1kpm"
 *      最长回文子序列是“1234321” 或者"123c321"，返回长度7
 *      测试链接：https://leetcode.com/problems/longest-palindromic-subsequence/
 *
 * 题目二：[从左往右的尝试模型?(马一直往下个位置跳)]
 *      请同学们自行搜索或者想象一个象棋的棋盘。
 *      然后把整个棋盘放入第一象限。棋盘的最左下角是(0,0)位置。
 *      那么整个棋盘就是横坐标上9条线，纵坐标上10条线的区域。
 *      给你三个参数：x,y,k
 *      返回"马"从(0,0)位置出发,必须走k步
 *      最后落在(x,y)位置的方法数有多少?
 *
 * 题目三：[业务限制的尝试模型（ready参数的范围是根据业务确定的）]
 *      给定一个数组arr，arr[i]代表第i号咖啡机泡一杯咖啡的时间
 *      给定一个正数N，表示N个人等着咖啡机泡咖啡。
 *      只有一台洗咖啡杯机，一次只能洗一个杯子，时间耗费a，洗完才能洗下一杯。
 *      每个咖啡杯也可以自己挥发干净，时间耗费b，咖啡杯可以并行挥发。
 *      假设所有人拿到咖啡之后立刻喝干净。
 *      返回从开始等到所有咖啡机变干净的最短时间。
 *      参数：int[] arr, int N, int a, int b
 */
public class T {
}
