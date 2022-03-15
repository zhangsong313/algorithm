package com.zs.shuati.class03;

/**
 * 7. 电子游戏“辐射4”中，任务“通向自由”要求玩家到达名为“Freedom Trail Ring”的金属表盘，并使用表盘拼写特定关键词才能开门
 * 给定一个字符串 ring，表示刻在外环上的编码；给定另一个字符串 key，表示需要拼写的关键词。您需要算出能够拼写关键词中所有字符的最少步数
 * 最初，ring 的第一个字符与12:00方向对齐。您需要顺时针或逆时针旋转 ring 以使 key 的一个字符在 12:00 方向对齐，然后按下中心按钮，以此逐个拼写完 key 中的所有字符
 * 旋转 ring 拼出 key 字符 key[i] 的阶段中：
 * 您可以将 ring 顺时针或逆时针旋转一个位置，计为1步。旋转的最终目的是将字符串 ring 的一个字符与 12:00 方向对齐，并且这个字符必须等于字符 key[i] 。
 * 如果字符 key[i] 已经对齐到12:00方向，您需要按下中心按钮进行拼写，这也将算作 1 步。按完之后，您可以开始拼写 key 的下一个字符（下一阶段）, 直至完成所有拼写。
 * Leetcode题目：https://leetcode.com/problems/freedom-trail/
 */
public class Code07_FreedomTrail {
    /**
     * 当前来到key的ki位置。ring的ri位置在12点位置，请返回后续需要的最少步数。
     */
    public static int findRotateSteps(String r, String k) {
        char[] ring = r.toCharArray();
        char[] key = k.toCharArray();
        int[] ringIndex = new int[256];
        for (int i = 0; i < ring.length; i++) {
            ringIndex[ring[i]] = i;
        }
        return process(ring, key, ringIndex, 0, 0);
    }

    /**
     * 如果ki已经来到key结束位置。返回0
     *
     * ring需要来到nextRI位置
     * 尝试顺时针旋转，需要step1步，递归调用(nextRI, ki+1)+step1
     * 尝试逆时针旋转，需要step2步, 递归调用(nextRI, ki+1)+step2
     * 返回两种尝试较小的结果值。
     */
    private static int process(char[] ring, char[] key, int[] ringIndex, int ri, int ki) {
        if(ki==key.length) return 0;
        if(ringIndex[key[ki]] == ri) return 1;

        int nextRI = ringIndex[key[ki]];
        // 顺时针旋转
        int step1 = nextRI>ri?nextRI-ri+1:nextRI+ring.length - ri+1;
        // 逆时针旋转
        int step2 = nextRI<ri?ri-nextRI+1:ri-(nextRI-ring.length)+1;

        return process(ring, key, ringIndex, nextRI, ki+1) + Math.min(step1, step2);
    }
}
