package com.zs.tixi.class32;

/**
 * 线段树
 *  1.一种支持范围整体修改和范围整体查询的数据结构
 *  2.解决的问题范畴:
 *      大范围信息可以只由左,右两侧信息加工出,
 *      而不必遍历左右两个子范围的具体情况.
 *
 * 线段树实例一:
 *  给定一个数组arr,用户希望你实现如下三个方法.
 *  1) void add(int L, int R, int V):让数组arr[L..R]上每个数都加上V
 *  2) void update(int L, int R, int V):让数组arr[L..R]上每个数都变成V
 *  3) int sum(int L,int R):让返回arr[L..R]这个范围整体的累加和
 *  怎么让这三个方法,时间复杂度都是O(logN)
 *
 * 线段树实例二:
 * 想象一下标准的俄罗斯方块游戏，X轴是积木最终下落到底的轴线
 *下面是这个游戏的简化版：
 * 1）只会下落正方形积木
 * 2）[a,b] -> 代表一个边长为b的正方形积木，积木左边缘沿着X = a这条线从上方掉落
 * 3）认为整个X轴都可能接住积木，也就是说简化版游戏是没有整体的左右边界的
 * 4）没有整体的左右边界，所以简化版游戏不会消除积木，因为不会有哪一层被填满。
 *
 * 给定一个N*2的二维数组matrix，可以代表N个积木依次掉落，
 * 返回每一次掉落之后的最大高度
 * https://leetcode.com/problems/falling-squares/
 *
 * 线段树总结：SegmentTree
 * 1.成员：nums:原始数据从1开始。sums:区间累加和数据。add:未下发的累加任务。update：未下发的更新任务。isUpdate：更新任务是否有效。
 * 2.初始化：nums长度为原始数组加1，数据复制。sums等数组长度为原始数组长度的4倍。
 * 3.build：sums数组初始化。将L..R范围上的累加和统计到rt位置。
 *      L==R直接赋值。否则将任务按中点拆分后分别下发到2*rt和2*rt+1位置。子任务完成后rt赋值。
 * 4.add(L,R,C,l,r,rt)在l,r范围上执行L..R范围加C的任务。对应更新区间和下标为rt。
 *      如果l..r被L..R包括。直接更新rt位置的sums为范围长度*C，add更新为C。
 *      否则：将rt位置的任务下发一层。pushDowm(rt, ln, rn);
 *      如果m在L右边，递归去左侧add。
 *      如果m+1在R左边，递归去右边add。
 *      子区间调整好后更新rt位置的值。
 * 5.pushDowm(rt, ln, rn):将rt位置的任务下发一层。
 *      由于update任务会清理掉add任务。因此先下发update任务。
 *      下发update任务：更新左右侧sums,update,isUpdate，清空左右侧add，清空rt位置的update。
 *      下发add任务：更新左右侧sums，add，清空rt位置的add。
 * 6.update(L,R,C,l,r,rt):在l,r范围上执行L..R更新为C的任务。对应区间和下标为rt。
 *      如果l..r被L..R包围，直接更新rt位置的sums，update，isUpdate。清空rt位置的add。返回。
 *      否则：将rt位置的任务下发一层。
 *      如果m在L右边，递归去左侧update。
 *      如果m+1在R左边，递归去右侧update。
 *      子区间调整好后更新rt位置的值。
 * 7.long query(L,R,l,r,rt):在l,r范围上，查询L..R范围累加和。
 *      如果l..r被L..R包括，直接返回rt位置的sums。
 *      否则：将rt位置的任务下发一层。
 *      如果m在L右边，递归去左侧查询。
 *      如果m+1在R左侧，递归去右侧查询。
 *      返回左右侧查询的和。
 */
public class T {
}
