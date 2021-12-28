package com.zs.tixi.class18;

import com.zs.xiaobai.common.MyCompValue;

import java.util.ArrayList;
import java.util.List;

/*
 *      4. 打印一个字符串的全部排列。
 *      5. 打印一个字符串的全部排列，要求不要出现重复的排列。
 *
 * 思路： 每次尝试每种字符选一次，后续选择剩下的字符。直到剩余字符为空表示考虑完所有的可能性了。
 */
public class Code04_PrintAllPermutations {

    /**
     * 使用动态数组rest收集全部字符。
     * 使用ans列表收集全部排列结果。
     * 使用path表示前面的结果.
     */
    public static List<String> permutation1(String s) {
        char[] chars = s.toCharArray();
        List<Character> rest = new ArrayList<>();
        for (char c : chars) {
            rest.add(c);
        }
        List<String> ans = new ArrayList<>();
        process1(rest, "", ans);
        return ans;
    }

    /**
     * rest为剩余可选字符。
     * path为之前做的决定。
     * ans为固定参数，收集答案的列表。
     * 如果rest为空，表示前面已经选择完了。ans添加path。返回。
     * rest不为空，当前需要在rest中选择一个字符。
     * 遍历rest：curr
     *      从rest中移除curr。
     *      path累加上curr，递归调用收集后续结果
     *      rest重新添加当前字符（恢复现场）
     */
    private static void process1(List<Character> rest, String path, List<String> ans) {
        if (rest.isEmpty()) {
            ans.add(path);
            return;
        }

        for (int i = 0; i < rest.size(); i++) {
            Character curr = rest.get(i);
            rest.remove(i);
            process1(rest, path+curr, ans);
            rest.add(i, curr);
        }
    }

    /**
     * 转化s为字符数组str。
     * 创建集合ans作为收集答案的集合。
     * 调用process
     * 返回答案
     * @param s
     * @return
     */
    public static List<String> permutation2(String s) {
        char[] str = s.toCharArray();
        List<String> ans = new ArrayList<>();
        process2(str, 0, ans);
        return ans;
    }

    /**
     * str为index之前位置已经选定的结果。
     * index为当前来到的位置。
     * ans为固定参数：收集答案的集合。
     *
     * 如果当前位置来到str结果以后，说明str为已经做好的选择。ans添加str。返回。
     *
     * 遍历index:str.length-1 ： i
     *      i来到当前位置：和index交换。
     *      递归调用process：index+1
     *      恢复现场：index和i交换回来。
     * @param str
     * @param index
     * @param ans
     */
    private static void process2(char[] str, int index, List<String> ans) {
        if (index ==  str.length) {
            ans.add(String.valueOf(str));
            return;
        }
        for (int i = index; i < str.length; i++) {
            swap(str, index, i);
            process2(str, index+1, ans);
            swap(str, index, i);
        }
    }


    private static void swap(char[] chs, int i, int j) {
        char tmp = chs[i];
        chs[i] = chs[j];
        chs[j] = tmp;
    }

    /**
     * 收集不重复的排列
     * @param s
     * @return
     */
    public static List<String> permutationNoRepeat(String s) {
        char[] str = s.toCharArray();
        List<String> ans = new ArrayList<>();
        process3(str, 0, ans);
        return ans;
    }

    /**
     * 照搬process2的代码。但之前已经来过当前位置的字符，不再重复交换。
     */
    private static void process3(char[] str, int index, List<String> ans) {
        if (index ==  str.length) {
            ans.add(String.valueOf(str));
            return;
        }
        boolean[] isSelected = new boolean[256]; // 标准字符总共256个。
        for (int i = index; i < str.length; i++) {
            if (!isSelected[str[i]]){ // 如果i位置的字符之前没有相同字符来过当前位置，才交换到当前位置。
                isSelected[str[i]] = true;
                swap(str, index, i);
                process3(str, index+1, ans);
                swap(str, index, i);
            }

        }
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
            System.out.println(permutation1(finalStr));
        });
        MyCompValue.printUseTime(()->{
            System.out.println(permutation2(finalStr));
        });

        MyCompValue.printUseTime(()->{
            System.out.println(permutationNoRepeat(finalStr));
        });
    }
}
