package com.zs.tixi.class8;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/*
 * 1. 最大线段重合问题（用堆实现）:
 *      给定很多线段，每个线段用两个数[start, end]表达开始位置和结束位置.
 *      1) 线段开始和结束位置是整数
 *      2) 线段重合区域必须大于等于1.
 *      要求返回线段重合区域中，最多包含了几条线段
 */
public class Code01_CoverMax {

    public static class Segment{
        public int start;
        public int end;
        public Segment(int start, int end){
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "{"+ start +
                    "," + end +
                    '}';
        }
    }

    /**
     * 1. 将线段数组segments按照start进行排序。
     * 2. 创建一个小根堆heap
     * 3. 创建一个int变量ans，初始化为0;
     * 3. 循环segments：
     *      取出当前线段currSeg。
     *      将当前线段的start与堆顶线段的end比较，如果end小于等于start，弹出。一直比较，直到不满足条件为止。
     *      将当前线段的end放入heap。
     *      获取当前heap的大小size，将ans与size更大的一个赋值给ans。
     * 4. 返回ans
     * @param segments
     * @return
     */
    public static int coverMax(Segment[] segments){
        if (segments == null || segments.length==0) return 0;
        List<Segment> sortedSegList = Arrays.asList(segments)
                .stream().sorted((seg1, seg2) -> seg1.start - seg2.start).collect(Collectors.toList());

        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int ans = 0;
        for (int i = 0; i < sortedSegList.size(); i++) {
            Segment currSeg = sortedSegList.get(i);
            while (!heap.isEmpty()){
                if (heap.peek() <= currSeg.start){
                    heap.poll();
                } else {
                    break;
                }

            }
            heap.add(currSeg.end);
            ans = ans>=heap.size()?ans:heap.size();
        }
        return ans;
    }
}
