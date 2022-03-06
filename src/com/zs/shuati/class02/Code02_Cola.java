package com.zs.shuati.class02;

/**
 * 贩卖机只支持硬币支付，且收退都只支持10 ，50，100三种面额
 * 一次购买只能出一瓶可乐，且投钱和找零都遵循优先使用大钱的原则
 * 需要购买的可乐数量是m，其中手头拥有的10、50、100的数量分别为a、b、c，可乐的价格是x(x是10的倍数)
 * 请计算出需要投入硬币次数
 *
 * 思路:
 *      从大面额到小面额,每种面额都算出可以买多少瓶可乐,将找零钱数划分给后面的小面额,
 *      需要注意第一瓶可乐要特殊计算:
 *          先加上前面大面额剩下的钱.
 *          第一瓶有可能也买不起,进入下个面额继续.
 * 注意题目要求只能一瓶一瓶买可乐. 比如有三张100,可乐150,不能一次投三张100,不找零.必须投两张100,找零后再买.
 *
 */
public class Code02_Cola {

    // 正式的方法
    // 要买的可乐数量，m
    // 100元有c张
    // 50元有b张
    // 10元有a张
    // 可乐单价x
    public static int putTimes(int m, int a, int b, int c, int x) {
        int[] money = new int[]{100, 50, 10};
        int[] count = new int[]{c, b, a};

        int ans = 0;
        int lastAllCount = 0;
        int lastAllMoney = 0;

        for (int i = 0; i < money.length && m>0; i++) {
            // 第一瓶可乐.
            // 如果买不起,进入下次循环
            if(lastAllMoney + money[i]*count[i] < x) {
                lastAllCount += count[i];
                lastAllMoney += money[i]*count[i];
                continue;
            } else {
                // 如果能买起
                int curCostCount = (x - lastAllMoney + money[i] - 1) / money[i]; // 当前面额花的张数.
                count[i] -= curCostCount;
                ans +=  curCostCount + lastAllCount; // 总硬币数.
                // 去找零
                giveChange(lastAllMoney+curCostCount*money[i]-x, 1, i+1, money, count);
                m--;
            }

            // 剩下的钱能买几瓶.(一次只能买一瓶.)
            int oneTimeCount = (x+money[i]-1)/money[i]; // 一瓶需要多少张钱.
            int buyCount =  Math.min(count[i] / oneTimeCount, m); // 买多少瓶.
            count[i] -= buyCount*oneTimeCount;
            ans+=buyCount*oneTimeCount;
            giveChange(oneTimeCount*money[i] - x, buyCount,i+1, money, count);

            // 当前位置剩下的钱
            lastAllCount = count[i];
            lastAllMoney = lastAllCount*money[i];
            m-=buyCount;
        }
        return m==0?ans:-1;
    }

    /**
     *
     * @param change 需要找零的钱数.
     * @param buyCount 找多少次. .
     * @param money 面额数组
     * @param count 面额张数数组.
     */
    private static void giveChange(int change, int buyCount,int i, int[] money, int[] count) {
//        int i = 0;
//        while (change>0){
//            count[i] += (change/money[i]) * buyCount;
//            change = change%money[i];
//            i++;
//        }

        for (; i < money.length && change > 0; i++) {
            count[i] += (change / money[i]) * buyCount;
            change %= money[i];
        }
    }

    // 暴力尝试，为了验证正式方法而已
    public static int right(int m, int a, int b, int c, int x) {
        int[] qian = { 100, 50, 10 };
        int[] zhang = { c, b, a };
        int puts = 0;
        while (m != 0) {
            int cur = buy(qian, zhang, x);
            if (cur == -1) {
                return -1;
            }
            puts += cur;
            m--;
        }
        return puts;
    }

    public static int buy(int[] qian, int[] zhang, int rest) {
        int first = -1;
        for (int i = 0; i < 3; i++) {
            if (zhang[i] != 0) {
                first = i;
                break;
            }
        }
        if (first == -1) {
            return -1;
        }
        if (qian[first] >= rest) {
            zhang[first]--;
            giveRest(qian, zhang, first + 1, qian[first] - rest, 1);
            return 1;
        } else {
            zhang[first]--;
            int next = buy(qian, zhang, rest - qian[first]);
            if (next == -1) {
                return -1;
            }
            return 1 + next;
        }
    }

    public static void giveRest(int[] qian, int[] zhang, int i, int oneTimeRest, int times) {
        for (; i < 3; i++) {
            zhang[i] += (oneTimeRest / qian[i]) * times;
            oneTimeRest %= qian[i];
        }
    }


    public static void main(String[] args) {
        int testTime = 1000;
        int zhangMax = 10;
        int colaMax = 10;
        int priceMax = 20;
        System.out.println("如果错误会打印错误数据，否则就是正确");
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int m = (int) (Math.random() * colaMax);
            int a = (int) (Math.random() * zhangMax);
            int b = (int) (Math.random() * zhangMax);
            int c = (int) (Math.random() * zhangMax);
            int x = ((int) (Math.random() * priceMax) + 1) * 10;
            int ans1 = putTimes(m, a, b, c, x);
            int ans2 = right(m, a, b, c, x);
            if (ans1 != ans2) {
                System.out.println("int m = " + m + ";");
                System.out.println("int a = " + a + ";");
                System.out.println("int b = " + b + ";");
                System.out.println("int c = " + c + ";");
                System.out.println("int x = " + x + ";");
                break;
            }
        }
        System.out.println("test end");
    }
}
