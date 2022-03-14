package com.zs.shuati.class03;

/**
 * 求一个字符串中，最长无重复字符子串长度
 * 本题测试链接 : https://leetcode.com/problems/longest-substring-without-repeating-characters/
 */
public class Code01_LongestSubstringWithoutRepeatingCharacters {
    /**
     * 窗口
     * 窗口每形成一次，记录窗口长度。
     * 循环：R不超过s长度
     *      循环：R不超过s长度 且 窗口内没有R位置字符
     *          R右移。
     *      如果R位置已经到结尾
     *          记录长度后返回。
     *      记录窗口长度减一。
     *      循环：L位置不为R位置字符。
     *          L左移
     *      L左移
     * 返回最大窗口长度
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s==null || s.length()==0) return 0;
        // L R 左闭右开
        int L = 0;
        int R = 1;
        // ans
        int ans = 1;

        // lenth
        int N = s.length();
        // trans string
        char[] chars = s.toCharArray();
        // char last index
        int[] last = new int[256];
        for (int i = 0; i < 256; i++) {
            last[i] = -1;
        }
        last[chars[0]] = 0;
        // R是当前需要查看的字符位置。
        // 如果R位置的数在set中，L一直向左，直到把它去除。
        // R位置数加到set中，R向右移动一步。
        while (R<N){
            int lastIndex = last[chars[R]];
            if (lastIndex != -1){
                for (int i = L; i <= lastIndex; i++) {
                    last[chars[i]] = -1;
                }
                L = lastIndex+1; // 注意此处需要把L位置字符到下一个位置之间的所有字符信息从last中删除。
            }
            last[chars[R]] = R;
            R++;
            ans = Math.max(ans, R-L);
        }
        return ans;
    }

    /**
     * 动态规划：
     * 以i位置结尾的子数组最大不重复长度为多少？
     * 0位置的结果为1.
     * i-1位置的最大不重复长度为p1
     * i位置字符上次出现的位置如果大于等于i-p1,最大不重复长度为i-上次出现的位置。
     * 否则最大不重复长度为i-1位置结果加1
     * 返回结果
     *
     * 可以看出i位置依赖i-1位置的结果
     */
    public static int lengthOfLongestSubstring2(String s) {
        if (s==null || s.length()==0) return 0;
        char[] str = s.toCharArray();
        int N = str.length;

        int[] last = new int[256];
        for (int i = 0; i < last.length; i++) {
            last[i] = -1;
        }
        last[str[0]] = 0;
        int ans = 1;
        int pre = 1;
        for (int i = 1; i < N; i++) {
            pre = Math.min(i-last[str[i]], pre+1);
            ans = Math.max(pre, ans);
            last[str[i]] = i;
        }
        return ans;
    }

    public static void main(String[] args) {
        int maxLen = 10;
        for (int i = 0; i < 10000; i++) {
            String str = getRandomStr(maxLen);
            int ans1 = lengthOfLongestSubstring(str);
            int ans2 = lengthOfLongestSubstring2(str);
            if(ans1!=ans2){
                System.out.println(str);
                System.out.println(ans1);
                System.out.println(ans2);
                throw new RuntimeException("测试失败");
            }
        }
    }

    // test
    private static String getRandomStr(int maxLen) {
        int len = (int)(Math.random()*maxLen)+1;
        char[] chars = new char[len];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char)(Math.random()*256);
//            chars[i] += 'a';
        }
        return String.valueOf(chars);
    }
}
