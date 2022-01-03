package com.zs.tixi.class20;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
 * 题目一：
 *      给定两个长度都为N的数组weights和values。
 *      weights[i]和values[i]分别代表i号物品的重量和价值。
 *      给定一个正数bag，表示一个载重为bag的袋子。
 *      你装的物品不能超过这个重量。
 *      返回你能装下的最大价值是多少？
 */
public class Code01_Knapsack {

    /**
     * 错误：
     * 将重量数组weights和values的每个值对应整合成物品Goods对象
     * 将Goods放入列表进行排序，比较器：价值大的在前，同价值下重量轻的在前。
     * 遍历列表：保证放入物品重量不超过载重。
     *
     * 新思路:
     *      比较器:性价比(价格/重量)高的在前.
     *      遇到放不下的情况,尝试将背包中最后放入的对象取出.直到背包可以放下当前对象.
     *      比较当前对象与取出所有对象的价值
     *      如果当前对象价值小,返回本次尝试之前的价值.
     *      如果当前对象价值大:将当前对象放入背包,继续.
     * 还是错误
     *
     *
     */
    public static int knapsack0(int[] weights, int[] values, int bag){
        if(weights==null||weights.length==0) return 0;
        int N = weights.length;
        List<Goods> goodsList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            goodsList.add(new Goods(weights[i], values[i]));
        }

        GoodsComparator goodsComparator = new GoodsComparator();
        goodsList.sort(goodsComparator);
        System.out.println("sorted goods : "+goodsList);
        int ans = 0;
        GoodsComparator2 goodsComparator2 = new GoodsComparator2();
        List<Goods> selectedGoods = new ArrayList<>();
        for (int i = 0; i < goodsList.size(); i++) {

//            Goods poll = goodsList.get(i);
//            if(bag-poll.weight<0) continue;
//            ans+=poll.value;
//            bag-=poll.weight;
//            System.out.println(poll);

            Goods poll = goodsList.get(i);
            if(bag-poll.weight<0) { // 背包放不下了.
                int bag2 = bag;
                int ans2 = ans;
                List<Goods> pollList = new ArrayList<>();
                selectedGoods.sort(goodsComparator2);
                for (int j = 0; bag2-poll.weight<0 && j <selectedGoods.size(); j++) {// 取出一直到背包可以放下当前物品或为空.
                    bag2+=selectedGoods.get(j).weight;
                    ans2-=selectedGoods.get(j).value;
                    pollList.add(selectedGoods.get(j));
                }
                if (bag2-poll.weight<0) continue;
                if ((ans-ans2)<poll.value){ // 如果取出的物品价值小于当前物品,真的取出这些物品
                    bag=bag2;
                    ans=ans2;
                    for (int j = 0; j < pollList.size(); j++) {
                        selectedGoods.remove(pollList.get(j));
                    }
                } else {
                    continue;
                }
            }
            ans+=poll.value;
            bag-=poll.weight;
            selectedGoods.add(poll);
        }
        return ans;
    }

    private static class Goods{
        public int weight;
        public int value;
        public Goods(int w, int v){
            weight = w;
            value = v;
        }

        @Override
        public String toString() {
            return "["+weight+","+value+"]";
        }
    }

    /**
     * 1. 价值大的放前面，价值相同的把重量小的放前面：错误：多个价值小的加起来价值和可能超过价值大的，而且重量和比价值大的轻，
     * 2. 【价值/重量】大的放前面。错误：可能由于前面拿了性价比高的货物，导致后面拿不到性价比低，但价值更高的货物。如下：拿了【1,13】，导致[5,17]拿不到.
     *      错误案例sorted goods : [[0,19], [1,13], [5,18], [5,17], [10,20], [20,18], [20,16]]
     */
    private static class GoodsComparator implements Comparator<Goods>{

        @Override
        public int compare(Goods o1, Goods o2) {
            if(o1.weight==0 && o2.weight==0) return o2.value-o1.value;
            if(o1.weight==0) return -1;
            if(o2.weight==0) return 1;

            double ans = ((double)o2.value/(double)o2.weight)-((double)o1.value/(double)o1.weight);
            return ans>0?1:ans<0?-1:0;
//            return o1.value!=o2.value?o2.value-o1.value:o1.weight-o2.weight;
        }
    }

    private static class GoodsComparator2 implements Comparator<Goods>{ // 优先取重量小的,相同重量取价值小的.

        @Override
        public int compare(Goods o1, Goods o2) {
            return o1.weight==o2.weight?o1.value-o2.value:o1.weight-o2.weight;
        }
    }

    /**
     * 从左往右的尝试模型
     * 总共右N件物品
     * i从0到N-1每个位置做尝试，
     */
    public static int knapsack1(int[] weights, int[] values, int bag){
        return process1(weights, values, 0 ,bag);
    }

    /**
     * 当前来到i位置，还有rest载重可以装东西。
     * 请返回i到结尾位置在rest载重下的最大价值。
     *
     * 如果当前来到末尾，返回0.
     * 尝试不选择当前物品：递归调用：i+1, rest.
     * 如果当前位置的重量比载重大，直接返回上边尝试结果.
     * 选择当前物品: 递归调用:i+1, rest-weight[i]。返回的结果加上values[i]
     * 返回以上两种尝试的最大值。
     */
    private static int process1(int[] weights, int[] values, int i, int rest) {
        if (i==weights.length) return 0;
        int p1 = process1(weights, values, i+1, rest);
        if (weights[i]>rest) return p1;
        int p2 = process1(weights, values, i+1, rest-weights[i])+values[i];
        return Math.max(p1, p2);
    }

    /**
     * 记忆化搜索
     * 从左往右的尝试模型
     * 总共右N件物品
     * i从0到N-1每个位置做尝试，
     */
    public static int knapsack2(int[] weights, int[] values, int bag){
        int W = weights.length;
        int R = bag+1;
        int[][] dp = new int[W][R];
        for (int i = 0; i < W; i++) {
            for (int j = 0; j < R; j++) {
                dp[i][j] = -1;
            }
        }
        return process2(weights, values, 0 ,bag, dp);
    }

    /**
     * 当前来到i位置，还有rest载重可以装东西。
     * 请返回i到结尾位置在rest载重下的最大价值。
     *
     * 如果当前来到末尾，返回0.
     * 尝试不选择当前物品：递归调用：i+1, rest.
     * 如果当前位置的重量比载重大，直接返回上边尝试结果.
     * 选择当前物品: 递归调用:i+1, rest-weight[i]。返回的结果加上values[i]
     * 返回以上两种尝试的最大值。
     */
    private static int process2(int[] weights, int[] values, int i, int rest, int[][] dp) {
        if (i==weights.length) return 0;
        if(dp[i][rest]!=-1) return dp[i][rest];

        int ans = 0;
        int p1 = process1(weights, values, i+1, rest);
        if (weights[i]>rest){
            ans = p1;
        } else {
            int p2 = process1(weights, values, i+1, rest-weights[i])+values[i];
            ans = Math.max(p1, p2);
        }
        dp[i][rest] = ans;
        return ans;
    }

    /**
     * 动态规划表
     * 变量有i和rest。
     * [i, rest]位置的值依赖[i+1, rest]和[i+1, rest-weight[i]]
     * base case：i==weights.length， 返回0.
     *
     * 创建一张二维表[weight+1, rest+1]
     * 先填最后一行:全部为0
     * 然后向上填写，上边行依赖下边行。
     *
     * 最后返回[0, bag]位置的值。
     */
    public static int dp(int[] w, int[] v, int bag) {
        int N = w.length+1;
        int R = bag+1;
        int[][] dp = new int[N][R];
        for (int i = w.length-1; i >=0; i--) {
            for (int rest = 0; rest < R; rest++) {
                int p1 = dp[i+1][rest];
                if (w[i]>rest){
                    dp[i][rest] = p1;
                } else {
                    int p2 = dp[i+1][rest-w[i]]+v[i];
                    dp[i][rest] = Math.max(p1, p2);
                }
            }
        }
        return dp[0][bag];
    }

    public static void main(String[] args) {
        int max = 10;
//        int bag = (int)(Math.random()*max);
        int num = (int)(Math.random()*max);
//        int[] weights = new int[num];
//        int[] values = new int[num];
//        for (int i = 0; i < num; i++) {
//            weights[i] = (int)(Math.random()*max);
//            values[i] = (int)(Math.random()*max);
//        }
        int[] weights = { 14, 4, 11, 2, 6, 5 };
        int[] values = { 7, 11, 14, 6, 6, 7 };
        int bag = 15;
        System.out.println(knapsack0(weights, values, bag));
        System.out.println(knapsack1(weights, values, bag));
        System.out.println(knapsack2(weights, values, bag));
        System.out.println(dp(weights, values, bag));
    }
}
