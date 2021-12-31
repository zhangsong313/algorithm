package com.zs.tixi.class19;

/*
 * 题目二：
 *      给定一个整型数组arr，代表数值不同的纸牌排成一条线。
 *      玩家A和玩家B依次拿走每张纸牌。
 *      规定玩家A先拿，玩家B后拿。
 *      但是每个玩家每次只能拿走最左或最右的牌。
 *      玩家A和玩家B都绝顶聪明。
 *      请返回最后获胜者的分数。
 */
public class Code02_CardsInLine {
    /**
     *  返回先手分数和后手分数中较大值。
     */
    public static int win1(int[] arr) {
        if(arr==null || arr.length==0) return 0;
        int p1 = f1(arr, 0, arr.length-1);
        int p2 = g1(arr, 0, arr.length-1);
       return Math.max(p1, p2);
    }

    /**
     * 在L到R范围上，先手选择的分数
     */
    public static int f1(int[] arr, int L, int R){
        if (L==R) return arr[L];
        int p1 = arr[L]+g1(arr, L+1, R);
        int p2 = arr[R]+g1(arr, L, R-1);
        return Math.max(p1, p2);
    }
    /**
     * 在L到R范围上，后手选择的分数
     */
    public static int g1(int[] arr, int L, int R){
        if (L==R) return 0;
        int p1 = f1(arr, L+1, R);
        int p2 = f1(arr, L, R-1);
        return Math.min(p1, p2);
    }

    /**
     * 记忆化搜索
     */
    public static int win2(int[] arr) {
        if(arr==null || arr.length==0) return 0;
        int N = arr.length;
        int[][] dpf = new int[N][N];
        int[][] dpg = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                dpf[i][j] = -1;
                dpg[i][j] = -1;
            }
        }
        int p1 = f2(arr, 0, arr.length-1, dpf, dpg);
        int p2 = g2(arr, 0, arr.length-1, dpf, dpg);
        return Math.max(p1, p2);
    }
    /**
     * 在L到R范围上，先手选择的分数
     */
    public static int f2(int[] arr, int L, int R, int[][] dpf, int[][] dpg){
        if (L==R) return arr[L];
        if(dpf[L][R] != -1) return dpf[L][R];
        int p1 = arr[L]+g2(arr, L+1, R, dpf, dpg);
        int p2 = arr[R]+g2(arr, L, R-1, dpf, dpg);

        dpf[L][R] = Math.max(p1, p2);
        return dpf[L][R];
    }
    /**
     * 在L到R范围上，后手选择的分数
     */
    public static int g2(int[] arr, int L, int R, int[][] dpf, int[][] dpg){
        if (L==R) return 0;
        if(dpg[L][R] != -1) return dpg[L][R];
        int p1 = f2(arr, L+1, R, dpf, dpg);
        int p2 = f2(arr, L, R-1, dpf, dpg);

        dpg[L][R] =  Math.min(p1, p2);
        return dpg[L][R];
    }

    /**
     * 填动态规划表
     */
    public static int win3(int[] arr) {
        if(arr==null || arr.length==0) return 0;
        int N = arr.length;
        int[][] dpf = new int[N][N];
        int[][] dpg = new int[N][N];
        for (int i = 0; i < N; i++) {
            dpf[i][i] = arr[i];
        }
        for (int i = 1; i < N; i++) {
            int L = 0;
            int R = i;
            while (R<N){
                dpf[L][R] =Math.max(arr[L]+dpg[L+1][R], arr[R]+dpg[L][R-1]);
                dpg[L][R] = Math.min(dpf[L+1][R], dpf[L][R-1]);
                L++;
                R++;
            }
        }
        return Math.max(dpf[0][N-1], dpg[0][N-1]);
    }

    public static void main(String[] args) {
        int[] arr = { 5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7 };
        System.out.println(win1(arr));
        System.out.println(win2(arr));
        System.out.println(win3(arr));
    }
}
