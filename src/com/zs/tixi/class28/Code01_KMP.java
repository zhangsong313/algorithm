package com.zs.tixi.class28;

import com.zs.xiaobai.common.MyCompValue;

/*
 * KMP算法
 *      假设字符串str长度为N,字符串match长度为M,M<=N
 *      想确定str中是否有某个子串是等于match的.
 *      时间复杂度O(N)
 *
 * KMP算法核心
 *      1)如何理解next数组
 *      2)如何利用next数组加速匹配过程,优化时的两个实质.
 */
public class Code01_KMP {

    /**
     * s2字符求解next数组.
     * 定义i表示s1当前的搜索位置,初始化为0
     * 定义j表示s2当前的搜索位置,初始化为0
     * s1长度为N,s2长度为M
     * 循环:i不超过N,j不超过M
     *      如果i位置和j位置相等,
     *          i和j都向右移动
     *      否则:
     *          如果next[j]不为-1,
     *              j来到next[j]位置
     *          否则:
     *              i向右移动.(j在0位置)
     * 如果j==M表示找到了匹配位置,返回i-M.
     * 否则,返回-1表示s1中没有找到s2
     */
    public static int kmp(String s1, String s2){
        if(s1==null || s2==null ||s2.length()<1 || s1.length()<s2.length()){
            return -1;
        }
        int x = 0, y = 0;
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int[] next = getNextArr(str2);
        while(x < str1.length && y < str2.length){
            if(str1[x] == str2[y]){
                x++;
                y++;
            } else if(next[y]!=-1){
                y = next[y];
            } else {
                x++;
            }
        }
        return y == str2.length ? x-str2.length : -1;
    }

    /**
     * 返回kmp算法所需的next数组.
     * 0位置固定为-1.
     * 1位置固定为0.
     * 创建和str相同长度的next数组,初始化前两项位置.
     * 定义k==next[1],表示i-1位置和哪个位置比较
     * 定义i=2
     * 循环:i < str.length
     *      如果str[i-1]和str[k]的值相等:
     *          next[i++]= ++k; // 当前位置的next值为k+1,i来到下一位置,下一位置需要和当前位置的next值k+1比
     *      否则:
     *          如果k==0
     *              i++;
     *          否则:
     *              k = next[k]
     * 返回next数组
     */
    private static int[] getNextArr(char[] str) {
        if(str.length==1) return new int[]{-1};
        int[] next = new int[str.length];
        next[0] = -1;
        next[1] = 0;
        int k = next[1];
        int i =2;
        while (i < str.length) {
            if(str[i-1]==str[k]){
                next[i++] = ++k;
            } else if(k!=0){
                k = next[k];
            } else {
                i++;
            }
        }
        return next;
    }

    // for test
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
//        int possibilities = 5;
//        int strSize = 20;
//        int matchSize = 5;
//        int testTimes = 5000000;
//        System.out.println("test begin");
//        for (int i = 0; i < testTimes; i++) {
//            String str = getRandomString(possibilities, strSize);
//            String match = getRandomString(possibilities, matchSize);
//            if (kmp(str, match) != str.indexOf(match)) {
//                System.out.println("Oops!");
//            }
//        }
//        System.out.println("test finish");
        int[] next = getNextArr("aabaa".toCharArray());
        MyCompValue.printArr(next);
    }
}
