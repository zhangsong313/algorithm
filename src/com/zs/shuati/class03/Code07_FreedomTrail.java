package com.zs.shuati.class03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 7. 电子游戏“辐射4”中，任务“通向自由”要求玩家到达名为“Freedom Trail Ring”的金属表盘，并使用表盘拼写特定关键词才能开门
 * 给定一个字符串 ring，表示刻在外环上的编码；给定另一个字符串 key，表示需要拼写的关键词。您需要算出能够拼写关键词中所有字符的最少步数
 * 最初，ring 的第一个字符与12:00方向对齐。您需要顺时针或逆时针旋转 ring 以使 key 的一个字符在 12:00 方向对齐，然后按下中心按钮，以此逐个拼写完 key 中的所有字符
 * 旋转 ring 拼出 key 字符 key[i] 的阶段中：
 * 您可以将 ring 顺时针或逆时针旋转一个位置，计为1步。旋转的最终目的是将字符串 ring 的一个字符与 12:00 方向对齐，并且这个字符必须等于字符 key[i] 。
 * 如果字符 key[i] 已经对齐到12:00方向，您需要按下中心按钮进行拼写，这也将算作 1 步。按完之后，您可以开始拼写 key 的下一个字符（下一阶段）, 直至完成所有拼写。
 * Leetcode题目：https://leetcode.com/problems/freedom-trail/
 * ==== ： 动态规划。每个位置选择一种选择到指定字符的方式，并加上按下动作的1步。返回最终的最少步数。
 * 注意:由于表盘上可能出现重复数字,所以会导致指定数字可能会有多个步数选择，当前选择又会影响之后的选择。所以需要尝试各种方法。
 */
public class Code07_FreedomTrail {
    public static void main(String[] args) {
        int ans = findRotateSteps("godding", "gd");
        System.out.println(ans);
    }

    /**
     * 当前来到key的ki位置。ring的ri位置在12点位置，请返回后续需要的最少步数。
     */
    public static int findRotateSteps(String r, String k) {
        char[] ring = r.toCharArray();
        char[] key = k.toCharArray();
//        int[] ringIndex = new int[256];
//        for (int i = 0; i < ring.length; i++) {
//            ringIndex[ring[i]] = i;
//        }
        HashMap<Character, List<Integer>> ringIndexMap = new HashMap<>();
        for (int i = 0; i < ring.length; i++) {
            if(ringIndexMap.get(ring[i]) == null){
                ArrayList<Integer> indexList = new ArrayList<>();
                indexList.add(i);
                ringIndexMap.put(ring[i], indexList);
            }else {
                ringIndexMap.get(ring[i]).add(i);
            }
        }
        int[][] cache = new int[ring.length][key.length];
        return process(ring, key, ringIndexMap, 0, 0, cache);
    }

    /**
     * 如果ki已经来到key结束位置。返回0
     *
     * ring需要来到nextRI位置
     * 尝试顺时针旋转，需要step1步，递归调用(nextRI, ki+1)+step1
     * 尝试逆时针旋转，需要step2步, 递归调用(nextRI, ki+1)+step2
     * 返回两种尝试较小的结果值。
     */
    private static int process(char[] ring, char[] key, HashMap<Character, List<Integer>> ringIndexMap, int ri, int ki, int[][] cache) {
        if(ki==key.length) return 0;
        if(cache[ri][ki] != 0) return cache[ri][ki]; // 从缓存中获取当前结果.

        int ans = Integer.MAX_VALUE;
        for (Integer nextRI : ringIndexMap.get(key[ki])) { // 对于ki位置字符，ring上每个对应位置都取顺时针逆时针旋转最短步骤后。递归获取后续步骤。
            int step = 1;
            if(nextRI != ri){
                // 顺时针旋转
                int step1 = nextRI>ri?nextRI-ri+1:nextRI+ring.length - ri+1;
                // 逆时针旋转
                int step2 = nextRI<ri?ri-nextRI+1:ri-(nextRI-ring.length)+1;

                step = Math.min(step1, step2);
            }


            ans = Math.min(ans, process(ring, key, ringIndexMap, nextRI, ki+1, cache) + step);
        }

        cache[ri][ki] = ans;
        return ans;
    }
}
