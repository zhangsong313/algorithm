package com.zs.tixi.class20;

import com.zs.xiaobai.common.MyCompValue;

import java.util.function.Function;

/**
 * 动态规划
 *
 * 题目一：[从左往右的尝试模型]
 *      给定两个长度都为N的数组weights和values。
 *      weights[i]和values[i]分别代表i号物品的重量和价值。
 *      给定一个正数bag，表示一个载重为bag的袋子。
 *      你装的物品不能超过这个重量。
 *      返回你能装下的最大价值是多少？
 *
 * 题目二：[从左往右的尝试模型(也可以从右往左)]
 *      规定1和A对应，2和B对应，3和C对应...26和Z对应。
 *      那么一个数字字符串比如'111'可以转化为:
 *      'AAA', 'KA', 'AK'
 *      给定一个只有数字字符组成的字符串str，返回有多少种转化结果。
 *
 * 题目三：[从左往右的尝试模型（目标字符串在不断被消耗）]:可变参数是字符串，无法画动态规划表，直接记忆化搜索。
 *      给定一个字符串str，给定一个字符串数组arr，出现的字符都是小写英文
 *      arr每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出来str。
 *      返回需要至少多少张贴纸可以完成这个任务：
 *      例如：str='babac', arr={"ba", "c", "abcd"}
 *      ba+ba+c 3 abcd+abcd 2 abcd+ba 2
 *      所以返回2
 *      本题测试链接：https://leetcode.com/problems/stickers-to-spell-word
 *
 * 题目四：[样本对应模型]
 *      给定两个字符串str1和str2
 *      返回这两个字符串的最长公共子序列
 *      比如：str1="a12b3c456d", str2="1ef23ghi4j56k"
 *      最长公共子序列是"123456"，所以返回长度6.
 *       这个问题leetcode上可以直接测
 *  链接：https://leetcode.com/problems/longest-common-subsequence/
 */
public class T {
    public static void main(String[] args) {
        testSnapsack(1000, 10, 10, T::knapsack0Fun, T::knapsack1Fun);
    }
    public static int knapsack0Fun(SnapsackParam param){
        return Code01_Knapsack.knapsack0(param.w, param.v, param.bag);
    }
    public static int knapsack1Fun(SnapsackParam param){
        return Code01_Knapsack.knapsack1(param.w, param.v, param.bag);
    }

    /**
     * 测试背包问题：两个解法对比测试.
     */
    public static void testSnapsack(int times, int maxArrLength, int maxBag, Function<SnapsackParam, Integer> fun1, Function<SnapsackParam, Integer> fun2){
        MyCompValue.times(times, ()->{
            int bag = (int)(Math.random()*maxBag)+1;
            int l = (int)(Math.random()*maxArrLength)+1;
            int[] w = MyCompValue.randomValueArr(l, bag*2);
            int[] v = MyCompValue.randomValueArr(l, bag*2);
            Integer ans1 = fun1.apply(new SnapsackParam(w, v, bag));
            Integer ans2 = fun2.apply(new SnapsackParam(w, v, bag));
            if(!ans1.equals(ans2)){
                MyCompValue.printArr(w);
                MyCompValue.printArr(v);
                System.out.println(bag);
                MyCompValue.printErr("ans1 : ", ans1, "ans2 : ", ans2);
            }
        });
    }

    private static class SnapsackParam{
        public int[] w;
        public int[] v;
        public int bag;
        public SnapsackParam(int[] w, int[] v, int bag){
            this.w = w;
            this.v = v;
            this.bag = bag;
        }
    }
}
