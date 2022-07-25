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
    /*
        笔记整理：
            1. 加强堆：
                改写堆需求:
                    系统提供的堆无法实现以下两个功能，要求改写堆实现：
                    1) 任意位置元素排序大小发生变化，重新调整堆。要求O(logN)的时间复杂度
                    2) 删除堆内任意元素,要求O(logN)时间内完成。
                    说明：核心是无法快速找到指定元素的位置，可以使用建立反向索引表解决

                实现：
                    新增成员变量map：
                        用来维护对象与下标的关系。当发生新增，删除，交换位置时要同步更新map。
                    新增函数：void resign(T data)
                        data的排序属性发生了变化，请正确调整data在堆中的位置。
                        从map中取出data的下标位置index。
                        对index位置执行heapInsert
                        对index位置执行heapify。
                    新增函数：void remove(T obj)
                        从堆中删除指定元素.
                        从map中取出data的下标位置index。
                        交换index和size-1两位置。
                        从heap中移除size-1位置的值。
                        从map中移除obj。
                        如果index不等于size-1
                            对index位置执行heapInsert
                            对index位置执行heapify。
             2.实时获取获奖名单：
                题目：
                    给消费最高的客户发奖。要求每次消费发生后,实时展示获奖人和消费信息。
                    给定一个整型数组int[] arr，和一个布尔型数组boolean[] op。两个数组一定等长，arr[i]表示客户编号, op[i]表示客户发生了一次操作
                    arr = [3, 3, 1, 2, 1, 2, 5, ...]
                    op = [T, T, T, T, F, T, F, ...]
                    依次表示：3用户购买了一件商品，3用户购买了一件商品，1用户购买了一件商品，2用户购买了一件商品， 1用户退货了一件商品， 2用户购买了一件商品， 5用户退货了一件商品...
                    得奖的规则：
                      1) 用户购买0件商品时，发生的退货事件无效。
                      2) 购买事件发生一次，用户购买商品数加1，反之，减一。
                      3) 获奖池大小为K，如果消费人数不够K，就按不够的情况输出。
                      4) 得奖系统分为得奖区和候选区，用户只要购买数>0，必在这两个区域的一个。
                      5) 购买数最大的前K名用户进入得奖区。如果得奖区没有满，新购买用户直接进入得奖区
                      6) 得奖区已满，购买数不足以进入得奖区的用户，进入候选区
                      7) 候选区购买数最多的用户，有资格评选是否进入得奖区:
                          该用户大于得奖区购买最少的用户，替换该用户进入得奖区。
                          得奖区购买最少的用户有多个，替换最早进入得奖区的用户
                          候选区购买最多的用户有多个，机会优先给最早进入候选区的用户
                      8) 用户进入候选区或得奖区时，需要记录当前购买时间，只要一直保持在当前区域，购买时间不变。否则购买时间删除。
                      9) 如果用户购买数变为0，将用户消费记录删除（不论在候选区还是得奖区）。下次用户再次购买，视为新用户.
                    要求，根据系统给定的arr数组，op数组和得奖区大小K，遍历arr，每i位置输出当前得奖名单，要求每次输出时间复杂度O(1)（实际需求是要求实时展示得奖区的情况）
                思路：
                     创建候选区的加强堆candHeap。比较器逻辑：消费最高在前，消费最高的取最先消费的在前。
                     创建得奖区的加强堆winHeap。比较逻辑：消费最低的在前，消费最低的取最先消费的在前。
                     创建map<id, Customer>记录对应id的客户消费情况。
                     创建List<List<Customer>> everStepWinList作为每一步得奖名单列表的集合
                     循环事件数组arr：
                          查询map中是否存在当前客户，如果不存在：
                              消费为退货：无效消费，不做任何操作。
                              消费为买货：将当前客户初始化后放入候选区，在map中登记客户
                          如果存在，map中取出信息更新客户的消费情况，
                              如果更新后消费为0，将客户从map中删除，分别判断得奖区和候选区中是否存在该客户并删除。
                              如果更新后消费不为0，分别判断候选区和得奖区是否包含客户并更新加强堆。
                          如果候选区为空：【直接返回得奖区进入下次循环】。
                          如果得奖区size小于K，从候选区弹出客户直到得奖区size等于K。（得奖区客户被删除情况）
                          如果候选区堆顶消费金额大于得奖区的堆顶，互相弹出一个客户到对方堆。（候选区消费增加，或得奖区消费减少导致）
                          返回得奖区进入下次循环, 从winHeap获取所有元素组成新列表加入everStepWinList。（此处应该不能直接存入对象，而是重新创建Customer，否则历史得奖信息会发生变化）

     */
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
