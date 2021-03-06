package com.zs.tixi.class42;

/**
 * 四边形不等式技巧（上）
 *
 * 内容：
 *
 * 区间划分问题中的划分点不回退现象
 *
 * 四边形不等式技巧特征
 * 1，两个可变参数的区间划分问题
 * 2，每个格子有枚举行为
 * 3，当两个可变参数固定一个，另一个参数和答案之间存在单调性关系
 * 4，而且两组单调关系是反向的：(升 升，降 降)  (升 降，降 升)
 * 5，能否获得指导枚举优化的位置对：上+右，或者，左+下
 *
 * 四边形不等式技巧注意点
 * 1，不要证明！用对数器验证！
 * 2，枚举的时候面对最优答案相等的时候怎么处理？用对数器都试试！
 * 3，可以把时间复杂度降低一阶
 * O(N^3) -> O(N^2)
 * O(N^2 * M) -> O(N * M)
 * O(N * M^2) -> O(N * M)
 * 4，四边形不等式有些时候是最优解，有些时候不是
 * 不是的原因：尝试思路，在根儿上不够好
 *
 * 题目：
 *
 * 给定一个非负数组arr，长度为N，
 * 那么有N-1种方案可以把arr切成左右两部分
 * 每一种方案都有，min{左部分累加和，右部分累加和}
 * 求这么多方案中，min{左部分累加和，右部分累加和}的最大值是多少？
 * 整个过程要求时间复杂度O(N)
 *
 * 把题目一中提到的，min{左部分累加和，右部分累加和}，定义为S(N-1)，也就是说：
 * S(N-1)：在arr[0…N-1]范围上，做最优划分所得到的min{左部分累加和，右部分累加和}的最大值
 * 现在要求返回一个长度为N的s数组，
 * s[i] =在arr[0…i]范围上，做最优划分所得到的min{左部分累加和，右部分累加和}的最大值
 * 得到整个s数组的过程，做到时间复杂度O(N)
 *
 * 摆放着n堆石子。现要将石子有次序地合并成一堆，规定每次只能选相邻的2堆石子合并成新的一堆
 * 并将新的一堆石子数记为该次合并的得分，求出将n堆石子合并成一堆的最小得分（或最大得分）合并方案
 *
 * 给定一个整型数组 arr，数组中的每个值都为正数，表示完成一幅画作需要的时间，再给定一个整数num
 * 表示画匠的数量，每个画匠只能画连在一起的画作
 * 所有的画家并行工作，返回完成所有的画作需要的最少时间
 * arr=[3,1,4]，num=2。
 * 最好的分配方式为第一个画匠画3和1，所需时间为4
 * 第二个画匠画4，所需时间为4
 * 所以返回4
 * arr=[1,1,1,4,3]，num=3
 * 最好的分配方式为第一个画匠画前三个1，所需时间为3
 * 第二个画匠画4，所需时间为4
 * 第三个画匠画3，所需时间为3
 * 返回4
 */
public class T {
}
