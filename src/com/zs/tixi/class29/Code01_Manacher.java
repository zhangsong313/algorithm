package com.zs.tixi.class29;

/*
 * Manacher算法
 *      假设字符串str长度为N,想返回最长回文子串的长度.
 *      时间复杂度O(N)
 *
 * Manacher算法核心
 *      1)理解回文半径数组
 *      2)理解所有中心的回文最右边界R,和取得R时的中心点C
 *      3)理解L...(i`)...C...(i)...R的结构,以及根据i`回文长度进行的状况划分.
 *      4)每一种情况划分,都可以加速求解i回文半径的过程.
 */
public class Code01_Manacher {

    /**
     * 当前来到i位置,检查i为中心是否存在回文:从i的左右两边每扩一位,检查一次是否相等,直到不相等停下.
     * 由于回文存在偶数回文,以某个数为中心向外扩的方法不适用于偶数回文.
     * 将原始字符串做处理,生成新字符串.
     * 首先字符串开头为#,之后每次取原始串中的一个字符,后面再拼接一个#
     * 这样处理后的字符串不论原先是奇数回文,还是偶数回文,都变成了新字符串中的奇数回文.
     * 求出新字符串中奇数回文的最大长度后,除以2就得到了原始串中的最大回文长度.
     *
     * 定义radii表示经过位置的回文半径数组.
     * 定义R表示之前统计过的回文最右边界.
     * 定义C表示达到最右边界时的回文中心点C.
     * 当前来到i位置
     *      如果i在R外:从i的两侧遍历比较直到不等.
     *      如果i在R内,找到i关于C对称位置i`,i`为之前位置,其回文半径肯定存在radii数组内:
     *          1) 如果i`的回文半径小于R-i:
     *              i的回文半径和i`的回文半径相同.
     *              原因:由于i`回文关于C的对称位置为i回文,所以i回文半径至少为i`的回文半径.
     *                  假设i`的回文再向外一个位置左右分别是a,b.i的对应位置分别是x,y
     *                  由于关于C对称,有b=x和a=y,又有a!=b所以x必不等于y.
     *                  所以i位置的回文半径和i`位置相等
     *          2) 如果i`的回文半径等于R-i
     *              i的回文半径至少为R-1.再往外位置需要验证.
     *          3) 如果i`的回文半径大于R-i
     *              i的回文半径等于R-i.
     *              原因:由于i`回文到L位置与i到R位置关于C对称,所以i回文半径至少是R-i.
     *                  假设L位置的左侧是a,a关于i`对称位置为b.R的右侧是y,y关于i对称位置为x
     *                  由于C位置的回文半径最远到R,所以a!=y,根据i`回文可得a=b,根据C回文可得b=x
     *                  所以x必不等于y
     *                  所以i位置的回文半径为R-i
     *      总结上面的各种情况,优化逻辑为:
     *          i的回文半径至少是i`回文半径和R-i的较小值.再往外的位置可尝试验证.
     */
    public static int manacher(String s) {
        if(s==null || s.length()==0) return 0;
        char[] str = manacherString(s);
        int[] radii = new int[str.length];
        int R = -1;
        int C = -1;
        int ans = 0;
        for (int i = 0; i < str.length; i++) {
            radii[i] = i<=R?Math.min(R-i+1, radii[C*2-i]):1;
            while (i-radii[i] >= 0 && i+radii[i]<str.length){
                if(str[i-radii[i]]!=str[i+radii[i]]){
                    break;
                }
                radii[i]++;
            }
            if(i+radii[i]-1>R){
                R=i+radii[i]-1;
                C=i;
            }
            ans = Math.max(radii[i], ans);
        }
        return ans-1;
    }


    public static char[] manacherString(String str) {
        char[] charArr = str.toCharArray();
        char[] res = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i != res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return res;
    }

    // for test
    public static int right(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        int max = 0;
        for (int i = 0; i < str.length; i++) {
            int L = i - 1;
            int R = i + 1;
            while (L >= 0 && R < str.length && str[L] == str[R]) {
                L--;
                R++;
            }
            max = Math.max(max, R - L - 1);
        }
        return max / 2;
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
        int possibilities = 5;
        int strSize = 20;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            if (manacher(str) != right(str)) {
                System.out.println("Oops!");
                System.out.println(str);
                System.out.println(manacher(str));
                System.out.println(right(str));
                throw new RuntimeException();
            }
        }
        System.out.println("test finish");
//        System.out.println(manacher("b121a"));
    }
}
