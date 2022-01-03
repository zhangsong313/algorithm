package com.zs.tixi.class20;

/*
 * 题目二：
 *      规定1和A对应，2和B对应，3和C对应...26和Z对应。
 *      那么一个数字字符串比如'111'可以转化为:
 *      'AAA', 'KA', 'AK'
 *      给定一个只有数字字符组成的字符串str，返回有多少种转化结果。
 */
public class Code02_ConvertToLetterString {


    public static int convertToLetterString(String str){
        if (str == null || str.length()==0) return 0;
        char[] chars = str.toCharArray();
        return process1(chars, 0);
    }

    /**
     * 当前来到i位置。请返回i位置往后有多少种转化结果。
     * 如果当前来到末尾位置，返回1.表示之前的转化成功。
     * 如果i位置数为0，返回0，表示之前的转化错误。
     * 尝试i位置单独翻译，递归调用：i+1 .收集结果数。
     * 如果i位置和i+1位置合并后的数值在1-26之间,尝试i和i+1位置合并翻译，递归调用:i+2，如果结果不为-1，收集结果数。
     * 返回收集的结果数。
     */
    public static int process1(char[] str, int i){
        if (i==str.length) return 1;
        if (str[i]=='0') return 0;

        int p1 = process1(str, i + 1);
        // i位置要求[1, 2], i+1位置要求不越界且[0-6]
        int p2 = 0;
        if(i+1<str.length && (str[i]-'0')*10+str[i+1]-'0' < 27){
            p2 = process1(str, i + 2);
        }
        int ans=0;
        ans +=p1;
        ans +=p2;

        return ans;
    }

    /**
     * 分析上面递归行为，i位置的值依赖i+1位置的值和i+2位置的值.
     */
   public static int dp1(String str){
       if(str == null || str.length()==0) return 0;
       char[] chars = str.toCharArray();
       int N = chars.length;
       int[] dp = new int[N+1];

       dp[N] = 1;

       for (int i = N-1; i >=0 ; i--) {
           if (chars[i]=='0') continue;

           dp[i] = dp[i + 1];
           // i位置要求[1, 2], i+1位置要求不越界且[0-6]
           if(i+1<chars.length && (chars[i]-'0')*10+chars[i+1]-'0' < 27){
               dp[i] += dp[i + 2];
           }
       }
       return dp[0];
   }

    /**
     * 考虑递归行为这样定义：
     * 当前来到i位置，
     * 请返回0..i范围上有多少种转换结果
     *
     * 0..i-1已经有转换结果了。
     * 尝试将i位置单独翻译,如果当前位置是'0',有0种转换结果,否则,递归调用:i-1.
     * 尝试将i位置和i+1位置合并翻译,要求i-1位置和i位置合并后的数字在1-26之间,递归调用:i-2.
     * 返回上面两种尝试结果之和.
     *
     * 分析上述递归行为:
     * i位置的值依赖i-1位置和i-2位置的值.
     */
   public static int dp2(String str){
       if(str == null || str.length()==0) return 0;
       char[] chars = str.toCharArray();
       int N = chars.length;
       int[] dp = new int[N];
       if(chars[0]!='0') dp[0] = 1;
       for (int i = 1; i < N; i++) {
           if(chars[i]!='0'){
               dp[i]+=dp[i-1];
           }
           if(i>0 && chars[i-1]!='0' && (chars[i-1]-'0')*10+(chars[i]-'0') <27){
               if(i==1){
                   dp[i]+=1;
               } else{
                   dp[i]+=dp[i-2];
               }

           }
       }
       return dp[N-1];
   }

    // 为了测试
    public static String randomString(int len) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int) (Math.random() * 10) + '0');
        }
        return String.valueOf(str);
    }

    // 为了测试
    public static void main(String[] args) {
        int N = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            String s = randomString(len);
            int ans0 = dp1(s);
            int ans1 = convertToLetterString(s);
            int ans2 = dp2(s);
            if (ans0 != ans2) {
                System.out.println(s);
                System.out.println(ans0);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
