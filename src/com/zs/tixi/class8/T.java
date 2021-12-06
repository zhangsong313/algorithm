package com.zs.tixi.class8;

import com.zs.xiaobai.common.MyCompValue;

import java.util.function.Function;

/**
 * 增强堆和练习题
 * 1. 最大线段重合问题（用堆实现）:
 *      给定很多线段，每个线段用两个数[start, end]表达开始位置和结束位置.
 *      1) 线段开始和结束位置是整数
 *      2) 线段重合区域必须大于等于1.
 *      要求返回线段重合区域中，最多包含了几条线段
 * 2. 改写堆:系统提供的堆无法试下以下两个功能：
 *      1) 任意位置元素排序大小发生变化，重新调整堆。要求O(logN)的时间复杂度
 *      2) 删除堆内任意元素,要求O(logN)时间内完成。
 *      说明：核心是无法快速找到指定元素的位置，可以使用建立反向索引表解决
 * 3. 改写堆练习题：给消费最高的客户发奖。要求每次消费发生后,实时展示获奖人和消费信息。
 *      给定一个整型数组int[] arr，和一个布尔型数组boolean[] op。两个数组一定等长，arr[i]表示客户编号, op[i]表示客户发生了一次操作
 *      arr = [3, 3, 1, 2, 1, 2, 5, ...]
 *      op = [T, T, T, T, F, T, F, ...]
 *      依次表示：3用户购买了一件商品，3用户购买了一件商品，1用户购买了一件商品，2用户购买了一件商品， 1用户退货了一件商品， 2用户购买了一件商品， 5用户退货了一件商品...
 *      得奖的规则：
 *          1) 用户购买0件商品时，发生的退货事件无效。
 *          2) 购买事件发生一次，用户购买商品数加1，反之，减一。
 *          3) 获奖池大小为K，如果消费人数不够K，就按不够的情况输出。
 *          4) 得奖系统分为得奖区和候选区，用户只要购买数>0，必在这两个区域的一个。
 *          5) 购买数最大的前K名用户进入得奖区。如果得奖区没有满，新购买用户直接进入得奖区
 *          6) 得奖区已满，购买数不足以进入得奖区的用户，进入候选区
 *          7) 候选区购买数最多的用户，有资格评选是否进入得奖区:
 *              该用户大于得奖区购买最少的用户，替换该用户进入得奖区。
 *              得奖区购买最少的用户有多个，替换最早进入得奖区的用户
 *              候选区购买最多的用户有多个，机会优先给最早进入候选区的用户
 *          8) 用户进入候选区或得奖区时，需要记录当前购买时间，只要一直保持在当前区域，购买时间不变。否则购买时间删除。
 *          9) 如果用户购买数变为0，将用户消费记录删除（不论在候选区还是得奖区）。下次用户再次购买，视为新用户.
 *      要求，根据系统给定的arr数组，op数组和得奖区大小K，遍历arr，每i位置输出当前得奖名单，要求每次输出时间复杂度O(1)（实际需求是要求实时展示得奖区的情况）
 * 4. 加强堆对数器。
 */
public class T {
    public static void main(String[] args) {
        checkCoverMax(10, 100, 100, Code01_CoverMax::coverMax);
    }

    /**
     * 最多线段重合问题对数器
     * @param times 测试次数
     * @param maxSegCount 最大线段数量
     * @param maxSegLength 最大线段长度（起始位置0-100随机位置）
     * @param fun 测试方法，接收一个线段数组，返回最多重合数
     */
    public static void checkCoverMax(int times, int maxSegCount, int maxSegLength,
                                     Function<Code01_CoverMax.Segment[], Integer> fun){
        MyCompValue.times(times, ()->{
            int segCount = (int)(Math.random()*(maxSegCount))+1;
            Code01_CoverMax.Segment[] segments = new Code01_CoverMax.Segment[segCount];
            for (int i = 0; i < segments.length; i++) {
                int start = (int)(Math.random()*(100));
                int segLength = (int)(Math.random()*(maxSegLength)+1);
                Code01_CoverMax.Segment seg = new Code01_CoverMax.Segment(start, start+segLength);
                segments[i] = seg;
            }

            Integer ans = fun.apply(segments);
            Integer ans2 = countCoverMax(segments);
            if (ans.equals(ans2) == false){
                System.out.println("测试最多线段重合错误：");
                System.out.print("线段数组：[");
                for (int i = 0; i < segments.length; i++) {
                    System.out.print(segments[i]+" ");
                }
                System.out.println("]");

                System.out.println("错误结果 ： "+ans);
                System.out.println("正确结果 ： "+ans2);
            }
        });
    }

    /**
     * 暴力方法
     * @param segments
     * @return
     */
    private static Integer countCoverMax(Code01_CoverMax.Segment[] segments) {
        if (segments == null || segments.length==0) return 0;
        // 找到最小start。
        int lessStart=Integer.MAX_VALUE;
        for (int i = 0; i < segments.length; i++) {
            lessStart = lessStart<segments[i].start?lessStart:segments[i].start;
        }
        // 找到最大end。
        int greatestEnd = Integer.MIN_VALUE;
        for (int i = 0; i < segments.length; i++) {
            greatestEnd = greatestEnd>segments[i].end?greatestEnd:segments[i].end;
        }
        // 在最小start与最小end中间，遍历0.5这种位置，每个位置统计所有end小于该位置，start大于该位置的线段。
        int ans = 0;
        for (double i = lessStart+0.5; i < greatestEnd; i++) {
            int ans2 = 0;
            for (int j = 0; j < segments.length; j++) {
                if (segments[j].start<i && segments[j].end>i){
                    ans2++;
                }
            }
            ans = ans>ans2?ans:ans2;
        }
        return ans;
    }
}
