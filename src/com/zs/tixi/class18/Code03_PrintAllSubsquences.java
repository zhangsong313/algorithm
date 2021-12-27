package com.zs.tixi.class18;

import com.zs.xiaobai.common.MyCompValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 *      2. 打印一个字符串的全部子序列。
 *      3. 打印一个字符串的全部子序列，要求不要出现重复字面值的子序列。
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
        for (int i = 0; i < 18; i++) {
            char c = (char)('a'+Math.random()*('z'+1-'a'));
            str+=c;
        }
        System.out.println("str : "+str);
        String finalStr = str;
        MyCompValue.printUseTime(()->{
            subs(finalStr);
        });

        MyCompValue.printUseTime(()->{
            subs1(finalStr);
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



}
