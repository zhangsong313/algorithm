package com.zs.tixi.class20;

/*
 * 题目三：
 *      给定一个字符串str，给定一个字符串数组arr，出现的字符都是小写英文
 *      arr每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出来str。
 *      返回需要至少多少张贴纸可以完成这个任务：
 *      例如：str='babac', arr={"ba", "c", "abcd"}
 *      ba+ba+c 3 abcd+abcd 2 abcd+ba 2
 *      所以返回2
 *      本题测试链接：https://leetcode.com/problems/stickers-to-spell-word
 */
public class Code03_StickersToSpellWord {

    public static int minStickers1(String[] stickers, String target) {
        int ans = process1(stickers, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    // 所有贴纸stickers，每一种贴纸都有无穷张
    // target
    // 最少张数
    public static int process1(String[] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (String first : stickers) {
            String rest = minus(target, first);
            if (rest.length() != target.length()) {
                min = Math.min(min, process1(stickers, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    public static String minus(String s1, String s2) {
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int[] count = new int[26];
        for (char cha : str1) {
            count[cha - 'a']++;
        }
        for (char cha : str2) {
            count[cha - 'a']--;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                for (int j = 0; j < count[i]; j++) {
                    builder.append((char) (i + 'a'));
                }
            }
        }
        return builder.toString();
    }
}
