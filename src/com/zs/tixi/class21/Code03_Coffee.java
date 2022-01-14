package com.zs.tixi.class21;

import com.zs.tixi.class8.HeapGreater;

import java.util.Arrays;
import java.util.Comparator;

/*
 * 题目三：
 *      给定一个数组arr，arr[i]代表第i号咖啡机泡一杯咖啡的时间
 *      给定一个正数N，表示N个人等着咖啡机泡咖啡。
 *      只有一台洗咖啡杯机，一次只能洗一个杯子，时间耗费a，洗完才能洗下一杯。
 *      每个咖啡杯也可以自己挥发干净，时间耗费b，咖啡杯可以并行挥发。
 *      假设所有人拿到咖啡之后立刻喝干净。
 *      返回从开始等到所有咖啡机变干净的最短时间。
 *      参数：int[] arr, int N, int a, int b
 */
public class Code03_Coffee {

    /**
     * 思路：
     * 1.先得到N个人喝完咖啡的时间点数组timeArr。
     * 2.对timeArr进行从左到右模型的动态规划。
     *
     * @return
     */
    public static int coffee(int[] arr, int n, int a, int b){
        int[] timeArr = getTimeArr(arr, n);
        return process(timeArr, a, b, 0, 0);
    }

    /**
     * time数组为每个人喝完咖啡的时间点。a为洗咖啡杯的时间，b为自动挥发干净的时间。
     * 当前来到time数组的i位置。请返回i位置往后所有咖啡杯都干净的最短时间。
     *
     * 如果当前来到末尾位置，返回0.
     * 当前咖啡杯最早可以洗的时间点为ready和time[i]的较大值。washTime
     * 尝试当前咖啡杯去洗，递归调用：i=i+1, ready=washTime+a.结果和washTime+a取较大值。
     * 尝试当前咖啡杯挥发，递归调用:i=i+1, ready=ready.结果和time[i]+b取较大值。
     *
     * 返回以上两种尝试的较小结果。
     */
    private static int process(int[] time, int a, int b, int i, int ready){
        if(i==time.length) return 0;
        int washTime = Math.max(ready, time[i]);
        int p1 = Math.max(process(time, a, b, i+1, washTime+a), washTime+a);
        int p2 = Math.max(process(time, a, b, i+1, ready), time[i]+b);
        return Math.min(p1, p2);
    }

    /**
     * 每台咖啡机制作咖啡的耗时放入加强堆中。每次调整下次泡出咖啡的时间。
     */
    private static int[] getTimeArr(int[] arr, int n) {
        HeapGreater<CoffeeTime> heap = new HeapGreater<>(new CoffeeTimeComparator());
        for (int i = 0; i < arr.length; i++) {
            heap.add(new CoffeeTime(arr[i], arr[i]));
        }

        int[] timeArr = new int[n];
        for (int i = 0; i < n; i++) {
            CoffeeTime peek = heap.peek();
            timeArr[i] = peek.time;
            peek.time += peek.cost;
            heap.resign(peek);
        }

        return timeArr;
    }

    /**
     * 每台咖啡机泡咖啡的信息
     */
    private static class CoffeeTime{
        public int time; // 下次泡出咖啡的时间。
        public int cost; // 泡一次咖啡需要消耗的时间
        public CoffeeTime(int time, int cost){
            this.time = time;
            this.cost = cost;
        }
    }
    private static class CoffeeTimeComparator implements Comparator<CoffeeTime>{

        @Override
        public int compare(CoffeeTime o1, CoffeeTime o2) {
            return o1.time-o2.time;
        }
    }

    /**
     * 动态规划表
     * 每个人喝完咖啡的时间点数组还是使用上面的方案。
     * 分析上面的递归依赖
     * i的最大值为time数组的长度N
     * ready值的最大值不超过[0..N-2]杯子都洗的情况下洗杯机最后的可用时间点。可以求出最大值readyMax （寻找业务限制的尝试模型）
     *
     * 创建一个二维数组dp[N+1][readyMax+1]
     * i位置的值依赖i+1位置的值。
     * 第N行的值都是0
     * 从第N-1行填到第0行.
     * (i, ready)位置依赖(i+1, ready+a)和(i+1, ready)的值
     * 返回dp[0][0]
     */
    public static int coffee2(int[] arr, int n, int a, int b){

        int[] timeArr = getTimeArr(arr, n);

        int readyMax = 0;
        for (int i = 0; i<timeArr.length; i++) {
            readyMax = Math.max(readyMax, timeArr[i])+a;
        }
        int[][] dp = new int[n+1][readyMax+1];
        for (int i = n-1; i >=0; i--) {
            for (int ready = 0; ready <= readyMax; ready++) {
                int washTime = Math.max(ready, timeArr[i]);
                if (washTime+a>readyMax) break;
                int p1 = Math.max(dp[i+1][washTime+a], washTime+a);
                int p2 = Math.max(dp[i+1][ready], timeArr[i]+b);
                dp[i][ready] = Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }

    // for test
    public static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) + 1;
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        System.out.print("arr : ");
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j] + ", ");
        }
        System.out.println();
    }

    // 验证的方法
    // 彻底的暴力
    // 很慢但是绝对正确
    public static int right(int[] arr, int n, int a, int b) {
        int[] times = new int[arr.length];
        int[] drink = new int[n];
        return forceMake(arr, times, 0, drink, n, a, b);
    }

    // 每个人暴力尝试用每一个咖啡机给自己做咖啡
    public static int forceMake(int[] arr, int[] times, int kth, int[] drink, int n, int a, int b) {
        if (kth == n) {
            int[] drinkSorted = Arrays.copyOf(drink, kth);
            Arrays.sort(drinkSorted);
            return forceWash(drinkSorted, a, b, 0, 0, 0);
        }
        int time = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int work = arr[i];
            int pre = times[i];
            drink[kth] = pre + work;
            times[i] = pre + work;
            time = Math.min(time, forceMake(arr, times, kth + 1, drink, n, a, b));
            drink[kth] = 0;
            times[i] = pre;
        }
        return time;
    }

    public static int forceWash(int[] drinks, int a, int b, int index, int washLine, int time) {
        if (index == drinks.length) {
            return time;
        }
        // 选择一：当前index号咖啡杯，选择用洗咖啡机刷干净
        int wash = Math.max(drinks[index], washLine) + a;
        int ans1 = forceWash(drinks, a, b, index + 1, wash, Math.max(wash, time));

        // 选择二：当前index号咖啡杯，选择自然挥发
        int dry = drinks[index] + b;
        int ans2 = forceWash(drinks, a, b, index + 1, washLine, Math.max(dry, time));
        return Math.min(ans1, ans2);
    }


    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int testTime = 10;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(len, max);
            int n = (int) (Math.random() * 7) + 1;
            int a = (int) (Math.random() * 7) + 1;
            int b = (int) (Math.random() * 10) + 1;
            int ans1 = right(arr, n, a, b);
            int ans2 = coffee(arr, n, a, b);
            int ans3 = coffee2(arr, n, a, b);
            if (ans1 != ans2
            || ans1!=ans3) {
                printArray(arr);
                System.out.println("n : " + n);
                System.out.println("a : " + a);
                System.out.println("b : " + b);
                System.out.println(ans1 + " , " + ans2+", "+ans3);
                System.out.println("===============");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
