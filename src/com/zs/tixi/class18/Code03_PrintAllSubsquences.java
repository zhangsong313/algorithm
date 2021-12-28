package com.zs.tixi.class18;

import com.zs.xiaobai.common.MyCompValue;

import java.util.*;

/*
 *      2. 打印一个字符串的全部子序列。
 *      3. 打印一个字符串的全部子序列，要求不要出现重复字面值的子序列。
 *
 * 思路：对每个位置考虑要和不要，考虑到最后一个位置就收集了全部可能。
 */
public class Code03_PrintAllSubsquences {

    public static List<String> subs(String s) {
        return process(s, 0);
    }

    /**
     * str 为固定参数
     * index 表示当前来到的位置
     * 之前的位置已经考虑过了，无需考虑。
     * 返回从index位置到末尾的所有子序列。
     *
     * 如果index==str.length ，返回空字符串列表。
     * list1为递归调用从下一位置开始子序列集合。
     * 遍历list1，为每个子序列前添加一个当前位置字符，得到list2.
     * 返回list1和list2的并集。
     */
    private static List<String> process(String str, int index){
        if (str.length()==index) return Arrays.asList("");
        List<String> list1 = process(str, index+1);
        List<String> list2 = new ArrayList<>();
        for (String s : list1) {
            list2.add(str.charAt(index) + s);
        }
        list2.addAll(list1);
        return list2;
    }

    public static void main(String[] args) {
        String str = "";
        for (int i = 0; i < 6; i++) {
            char c = (char)('a'+Math.random()*('z'+1-'a'));
            str+=c;
        }
        System.out.println("str : "+str);
        String finalStr = str;
        MyCompValue.printUseTime(()->{
            System.out.println(subsNoRepeat(finalStr));
        });

        MyCompValue.printUseTime(()->{
            System.out.println(subsNoRepeat2(finalStr));
        });

    }

    // s -> "abc" ->
    /**
     * 老师的这种方案，虽然参数比较多，
     * 但每次递归运行的命令比较简单。
     * 总体时间大概是递归收集集合返回的一半。
     */
    public static List<String> subs1(String s) {
        char[] str = s.toCharArray();
        String path = "";
        List<String> ans = new ArrayList<>();
        process1(str, 0, ans, path);
        return ans;
    }

    // str 固定参数
    // 来到了str[index]字符，index是位置
    // str[0..index-1]已经走过了！之前的决定，都在path上
    // 之前的决定已经不能改变了，就是path
    // str[index....]还能决定，之前已经确定，而后面还能自由选择的话，
    // 把所有生成的子序列，放入到ans里去
    public static void process1(char[] str, int index, List<String> ans, String path) {
        if (index == str.length) {
            ans.add(path);
            return;
        }
        // 没有要index位置的字符
        process1(str, index + 1, ans, path);
        // 要了index位置的字符
        process1(str, index + 1, ans, path + String.valueOf(str[index]));
    }


    /**
     * 打印全部子序列，不包括重复的子序列。
     * @param s
     * @return
     */
    public static List<String> subsNoRepeat(String s) {
        char[] str = s.toCharArray();
        Set<String> ans = new HashSet<>();
        process3(str, 0, "", ans);
        List<String> ansList = new ArrayList<>();
        for (String an : ans) {
            ansList.add(an);
        }
        return ansList;
    }

    /**
     * str为给定字符串，固定参数
     * 当前来到i位置。
     * 之前选择的子序列结果为s
     * ans为收集答案的集合，固定参数。
     *
     * 如果当前来到str长度位置，把之前的结果s加入集合ans。返回
     * 不选择当前字符：递归调用下一位置，s不变。
     * 选择当前字符：递归调用下一位置，s累加上当前位置字符。
     * @param str
     * @param i
     * @param s
     * @param ans
     */
    private static void process3(char[] str, int i, String s, Set<String> ans) {
        if(i==str.length){
            ans.add(s);
            return;
        }
        process3(str, i+1, s, ans);
        process3(str, i+1, s+str[i], ans);
    }

    public static List<String> subsNoRepeat2(String s) {
        char[] str = s.toCharArray();
        String path = "";
        HashSet<String> set = new HashSet<>();
        process4(str, 0, set, path);
        List<String> ans = new ArrayList<>();
        for (String cur : set) {
            ans.add(cur);
        }
        return ans;
    }

    public static void process4(char[] str, int index, HashSet<String> set, String path) {
        if (index == str.length) {
            set.add(path);
            return;
        }
        String no = path;
        process4(str, index + 1, set, no);
        String yes = path + String.valueOf(str[index]);
        process4(str, index + 1, set, yes);
    }


}
