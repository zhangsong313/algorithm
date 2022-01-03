package com.zs.tixi.class20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    /**
     * 审题:arr中的每个字符串都可以重复使用.即每个贴纸有无限张.
     *
     */
    public static int minStickers0(String[] stickers, String target){
        List<Character> rest = new ArrayList<>();
        for (char c : target.toCharArray()) {
            rest.add(c);
        }
        return process0(stickers, rest, 0);
    }

    /**
     * 当前来到arr的i位置, 字符串str被消耗到rest,从i位置往后至少需要多少张贴纸可以完成任务.
     * 如果rest为空,返回0,表示0张贴纸可以完成任务.
     * 如果当前来到末尾,返回-1,表示无法完成任务.
     * 尝试不使用当前贴纸,递归调用:i+1, rest
     * 尝试用当前贴纸消耗rest,rest变为rest2,递归调用:i+1, rest2.调用结果加1
     * 取以上两种尝试的较小值返回
     */
    public static int process0(String[] arr, List<Character> rest, int i){
        if(rest.isEmpty()) return 0;
        if(i==arr.length) return -1;
        char[] chars = arr[i].toCharArray();

        int p1 = process0(arr, rest, i+1);

        List<Character> rest2 = new ArrayList<>();
        Collections.copy(rest2, rest);// 恢复现场.(不破坏现场)
        for (char aChar : chars) {
            if (rest2.contains(aChar)) rest2.remove(new Character(aChar));
        }
        int p2 = process0(arr, rest2, i+1);

        int ans = 0;
        if (p1 != -1 && p2 != -1) {
            return Math.min(p1, p2+1);
        }
        if(p1!=-1){
            ans=p1;
        }
        if(p2!=-1){
            ans=p2+1;
        }

        return ans;
    }

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
