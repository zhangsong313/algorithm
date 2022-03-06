package com.zs.shuati.class02;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * 给定数组hard和money，长度都为N，hard[i]表示i号工作的难度， money[i]表示i号工作的收入
 * 给定数组ability，长度都为M，ability[j]表示j号人的能力，每一号工作，都可以提供无数的岗位，难度和收入都一样
 * 但是人的能力必须>=这份工作的难度，才能上班。返回一个长度为M的数组ans，ans[j]表示j号人能获得的最好收入
 *
 * 思路:按工作好坏排序后,取j号人小于等于其能力的最好工作.
 */
public class Code01_ChooseWork {

    /**
     * 对工作进行排序.比较工作要求的能力,相同能力的报酬高的放前面.
     * 将数据筛选放入有序表:
     * 如果当前工作能力要求比前一个高 且 报酬比前一个高,加入有序表.
     * 对每个worker,求小于等于自身能力的工作报酬.
     * 返回累加和.
     * @return
     */
    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        Integer[][] jobs = new Integer[difficulty.length][2]; // [能力,报酬]
        for (int i = 0; i < difficulty.length; i++) {
            jobs[i] = new Integer[]{difficulty[i], profit[i]};
        }
        Arrays.sort(jobs, new MyComparator());
        TreeMap<Integer, Integer> map = new TreeMap<>();
        Integer[] last = new Integer[]{Integer.MIN_VALUE, Integer.MIN_VALUE};
        map.put(last[0], last[1]);
        for (Integer[] job : jobs) {
            if(job[0]>last[0] && job[1]>last[1]){
                map.put(job[0], job[1]);
                last = job;
            }
        }
        int ans = 0;
        for (int w : worker) {
            Integer value = map.floorEntry(w).getValue();
            ans += value>0?value:0;
        }
        return ans;
    }
    private static class MyComparator implements Comparator<Integer[]> {

        @Override
        public int compare(Integer[] o1, Integer[] o2) {
            return o1[0].compareTo(o2[0])==0 ? o2[1].compareTo(o1[1]) : o1[0].compareTo(o2[0]);
        }
    }
}
