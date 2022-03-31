package com.zs.tixi.class25;

import java.util.Deque;
import java.util.LinkedList;

/*
 * 题目三:
 *      加油站的良好出发点问题.
 *      N个加油站组成一个环形,给定两个长度都是N的非负数组oil和dis(N>1),
 *      oil[i]代表第i个加油站存的油可以跑多少距离.dis[i]代表第[i]个加油站到
 *      下一个加油站的距离.假如一辆初始没有油的车从第i个加油站出发,
 *      最终能回到第i个加油站,那么第i个加油站就算良好出发点,否则就不算.
 *      请返回长度为N的布尔数组res,res[i]代表第i个加油站是否是良好出发点.
 *      要求时间复杂度O(n),空间复杂度O(1)
 * 测试链接：https://leetcode.com/problems/gas-station
 */
public class Code03_GasStation {

    public static int canCompleteCircuit(int[] gas, int[] cost) {
        boolean[] good = goodArray(gas, cost);
        for (int i = 0; i < good.length; i++) {
            if(good[i]) return i;
        }
        return -1;
    }
    /**
     * 先将gas和cost数组对应位置相减。得到当前加油站到下个加油站剩余的油量数组rest。
     * 如果从rest的某一位置出发，经过的数累加，如果出现了负数，表示当前位置不是一个良好出发点。
     *
     * 问题转化为在数组rest上环形求固定长度区间(rest.length)内前缀和是否出现负数问题。转换为区间内最小值是否负数的问题。
     * 将rest复制一份添加到rest后，得到一个容易处理的环形数据。rest2
     * 在rest2上每个数更新为前缀和。某段区间的对应前缀和就是每个数减去区间前一个数。
     *
     * 0..rest.length-1范围内求当前位置到向右rest.length长度的位置区间内的最小值。
     * 最小值减去区间前的一个数，如果结果小于0，当前位置不是一个良好出发点。
     *
     */
    public static boolean[] goodArray(int[] gas, int[] cost) {
        int[] rest = new int[gas.length*2];
        for (int i = 0; i < gas.length; i++) {
            rest[i] = gas[i]-cost[i];
            rest[i+gas.length] = rest[i];
        }
        for (int i = 1; i < rest.length; i++) {
            rest[i]+=rest[i-1];
        }

        Deque<Integer> dqMin = new LinkedList<>();
        for (int R = 0; R < gas.length; R++) {
            while (dqMin.size()>0 && rest[dqMin.peekLast()]>=rest[R]){
                dqMin.pollLast();
            }
            dqMin.addLast(R);
        }

        boolean[] ans = new boolean[gas.length];

        int L = 0;
        int R = gas.length-1, preSum = 0;
        while ( L < gas.length ) { // 当前来到一个合理区间（R向右移动过了，L向右移动过了）

            while (dqMin.size()>0 && rest[dqMin.peekLast()]>=rest[R]){
                dqMin.pollLast();
            }
            dqMin.addLast(R); // 处理从R滑入数.
            R++; // R向右滑

            if(dqMin.peekFirst()<L) dqMin.pollFirst();// 处理从L滑出数.
            L++; //L向左滑.

            if(rest[dqMin.peekFirst()]-preSum>=0) ans[L] = true; // 获取合理区间的结果
            preSum=rest[L]; // 更新前缀和变量
        }
        return ans;
    }
}
