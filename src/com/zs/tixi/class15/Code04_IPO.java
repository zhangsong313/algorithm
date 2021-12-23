package com.zs.tixi.class15;

import java.util.PriorityQueue;

/*
 * 4. 输入正数数组costs，正数数组profits，正数K，正数M
 *      costs[i]表示i号项目的花费。
 *      profits[i]表示i号项目在扣除花费之后还能挣到的钱（利润）。
 *      K表示你只能串行做的最多K个项目。
 *      M表示初始资金。
 *      说明：每做完一个项目，马上获得的收益，可以支持你去做下一个项目。不能并行的做项目。
 *      输出：你最后获得的最大钱数。
 */
public class Code04_IPO {

    private static class Program {
        public int p;
        public int c;

        public Program(int p, int c) {
            this.p = p;
            this.c = c;
        }
    }

    // 最多K个项目
    // W是初始资金
    // Profits[] Capital[] 一定等长
    // 返回最终最大的资金

    /**
     * 构建一个以花费为指标的小根堆。
     * 构建一个以利润为指标的大根堆。
     *
     * 将所有项目放入小根堆。
     * 从小根堆中弹出所有小于等于当前资金的项目，放入大根堆。
     * 从大根堆弹出一个项目。更新做完项目后的资金。
     * 再次从小根堆中取出资金可负担的项目放入大根堆，循环直到大根堆为空。
     *
     * 返回最终的资金
     * @param K
     * @param W
     * @param Profits
     * @param Capital
     * @return
     */
    public static int findMaximizedCapital(int K, int W, int[] Profits, int[] Capital) {
        if (Capital==null || Capital.length==0) return W;
        PriorityQueue<Program> cHeap = new PriorityQueue<>((p1, p2)->p1.c-p2.c);
        PriorityQueue<Program> pHeap = new PriorityQueue<>((p1, p2) -> p2.p-p1.p);
        for (int i = 0; i < Capital.length; i++) {
            cHeap.add(new Program(Profits[i], Capital[i]));
        }
        for (int i = 0; i < K; i++) {
            while (!cHeap.isEmpty() && cHeap.peek().c<W){
                pHeap.add(cHeap.poll());
            }
            if (pHeap.isEmpty()) break;
            W+=pHeap.poll().p;
        }

        return W;
    }
}
