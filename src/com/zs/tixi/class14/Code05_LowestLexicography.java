package com.zs.tixi.class14;

import java.util.Arrays;
import java.util.TreeSet;

/*
 * 5. 贪心算法：
 *      1）最自然智慧的算法。
 *      2）用一种局部最功利的标准，总是做出在当前看来是最好的选择。
 *      3）难点在于证明局部最功利的标准可以得到全局最优解。
 *      4）对贪心算法的学习，主要以增加阅历和经验为主。
 *   贪心练习：
 *      给定一个由字符串组成的数组strs。
 *      必须把所有的字符串拼接起来，返回所有可能的拼接结果中，字典序最小的结果。
 *   贪心算法的标准求解过程：
 *      1）分析业务
 *      2）根据业务逻辑找到不同的贪心策略。
 *      3）对于能举出反例的策略直接跳过，不能举出反例的策略要证明有效性。
 *          这往往是特别困难的，要求数学能力很高且不具有同一的技巧性。
 *   贪心算法的解题套路：
 *      1）实现一个不依靠贪心策略的解法X，可以用最暴力的尝试。
 *      2）脑补出贪心策略A，贪心策略B，贪心策略C。。。
 *      3）用解法X和对数器，用实验的方式得知那个贪心策略正确。
 *      4）不要去纠结贪心策略的证明。
 */
public class Code05_LowestLexicography {
    public static String lowestString1(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        TreeSet<String> ans = process(strs);
        return ans.size() == 0 ? "" : ans.first();
    }

    /**
     * 返回全排列中字典序最大的结果。
     *
     * 时间复杂度：n^n
     * @param strs
     * @return
     */
    public static TreeSet<String> process(String[] strs) {
        TreeSet<String> ans = new TreeSet<>();
        if (strs.length == 0) {
            ans.add("");
            return ans;
        }
        for (int i = 0; i < strs.length; i++) {
            String first = strs[i];
            String[] nexts = removeIndexString(strs, i);
            TreeSet<String> next = process(nexts);
            for (String cur : next) {
                ans.add(first + cur);
            }
        }
        return ans;
    }

    // {"abc", "cks", "bct"}
    // 0 1 2
    // removeIndexString(arr , 1) -> {"abc", "bct"}
    public static String[] removeIndexString(String[] arr, int index) {
        int N = arr.length;
        String[] ans = new String[N - 1];
        int ansIndex = 0;
        for (int i = 0; i < N; i++) {
            if (i != index) {
                ans[ansIndex++] = arr[i];
            }
        }
        return ans;
    }

    public static String lowestString2(String[] strs) {
        if (strs==null || strs.length== 0) return "";
        Arrays.sort(strs, (a, b)->(a+b).compareTo(b+a));
        String res = "";
        for (int i = 0; i < strs.length; i++) {
            res+=strs[i];
        }
        return res;
    }

    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 5);
            ans[i] = (Math.random() <= 0.5) ? (char) (65 + value) : (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    // for test
    public static String[] copyStringArray(String[] arr) {
        String[] ans = new String[arr.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = String.valueOf(arr[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int arrLen = 6;
        int strLen = 5;
        int testTimes = 10000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String[] arr1 = generateRandomStringArray(arrLen, strLen);
            String[] arr2 = copyStringArray(arr1);
            if (!lowestString1(arr1).equals(lowestString2(arr2))) {
                for (String str : arr1) {
                    System.out.print(str + ",");
                }
                System.out.println();
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
