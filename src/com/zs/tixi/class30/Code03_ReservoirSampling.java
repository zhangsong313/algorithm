package com.zs.tixi.class30;

import java.util.ArrayList;
import java.util.List;

/*
 * 蓄水池算法:
 *      解决的问题:
 *      假设有一个源源不断吐出不同球的机器.
 *      只有装下10个球的袋子,每一个吐出的球,要么放入袋子,要么永远扔掉
 *      如何做到机器吐出每一个球之后,所有吐出球都等概率被放入袋子.
 */
public class Code03_ReservoirSampling {
    /**
     * 袋子大小为N
     * 每次拿到一个球后,更新当前球数量count.
     * 当前球是否放入袋子概率用N/i,然后随机从袋子中取出一个球,将当前球放入.
     * 到第11个球时,放入袋子的概率为N/11
     * 到第12号球时,11号球还在袋子中的概率为1-N/12*1/N = 11/12
     * 到第13号球时,11号球还在袋子中的概率为1-N/13*1/N = 12/13
     * 累乘起来,到第i号球时,11号球还在袋子中的概率为N/i
     */
    public static class RandomBox{
        private String[] bag;
//        private int N;
        private int count;
        public RandomBox(int N){
//            this.N = N;
            bag = new String[N];
            count = 0;
        }
        public void add(String no){
            count++;
            if(count<=bag.length){
                bag[count-1] = no;
            } else {
                if((int)(Math.random()*count)+1 <= bag.length){
                    bag[(int)(Math.random()*bag.length)] = no;
                }
            }
        }
        public List<String> getResult(){
            List<String> ans = new ArrayList<>(bag.length);
            for (int i = 0; i < bag.length; i++) {
                if(bag[i]!=null){
                    ans.add(bag[i]);
                }
            }
            return ans;
        }
    }
}
