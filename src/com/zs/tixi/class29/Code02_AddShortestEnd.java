package com.zs.tixi.class29;

/*
 * 题目:
 *      给定一个字符串.要求在字符串末尾添加若干个字符,使得字符串变为回文串.
 *      求最少添加的字符个数.
 */
public class Code02_AddShortestEnd {
    /**
     * 思路:如果在字符串末尾逆序添加原字符串的全部字符,肯定可以构成一个回文串.
     *      有没有可能减少使用的字符呢?如果原字符串的右部分已经是一个回文串了.
     *      添加字符时跳过这些字符添加就可以了.
     *      所以问题转化为求原字符串中右边最长回文串长度.
     *
     * 还是采用manacher算法,但是算到第一个使得R扩到数据末尾的位置就可以了.
     * 求出这个位置的回文长度L,答案就是字符串总长度-L
     *
     * 将字符串s转换为字符数组str.
     * 将str处理为插入#的字符数组.
     * 定义radii数组收集经过位置的回文半径.
     * 定义R表示经过位置扩到最远的回文右边界,初始化为-1
     * 定义C表示达到当前最远回文右边界的位置,初始化为-1
     * 定义i表示当前来到的位置,初始化为0
     * 循环:i<str.length
     *      如果i>R
     *          radii[i] = 1
     *      否则:
     *          radii[i]是R-i+1和radii[C*2-i]中的较小值
     *      循环:i-radii[i]>=0 && i+radii[i]<str.length
     *          如果左右两边不相等
     *              break
     *          radii[i]++
     *      如果i+radii[i]-1大于R
     *          R = i+radii[i]-1
     *          C = i
     *      如果R==str.length-1
     *          break
     *      i++
     * 源字符串从str.length-(radii[i]-1)-1位置一直到0位置,逐个追加到原字符串后,返回
     */
    public static String shortestEnd(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        char[] strOrigin = s.toCharArray();
        char[] str = insertChar(strOrigin);
        int[] radii = new int[str.length];
        int R = -1;
        int C = -1;
        int i = 0;
        while (i<str.length){
            radii[i] = i>R?1:Math.min(R-i+1, radii[C*2-i]);
            while (i-radii[i] >=0 && i+radii[i]<str.length){
                if(str[i-radii[i]]!=str[i+radii[i]]) break;
                radii[i]++;
            }
            if(i+radii[i]-1>R){
                R = i+radii[i]-1;
                C = i;
            }
            if(R==str.length-1)break;
            i++;
        }
        StringBuffer sb = new StringBuffer();
        for (int j = strOrigin.length-(radii[i]-1)-1; j >=0; j--) {
            sb.append(strOrigin[j]);
        }
        return s+sb.toString();
    }

    private static char[] insertChar(char[] str) {
        char[] ans = new char[str.length*2+1];
        ans[0] = '#';
        int i = 1;
        for (char c : str) {
            ans[i++] = c;
            ans[i++] = '#';
        }
        return ans;
    }

    public static void main(String[] args) {
        String str1 = "abcd123321";
        System.out.println(shortestEnd(str1));
    }
}
