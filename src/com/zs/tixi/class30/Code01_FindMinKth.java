package com.zs.tixi.class30;

import com.zs.xiaobai.common.MyCompValue;

/*
 * 在无序数组中求第K小的数
 *      1) 改写快排的方法
 *      2) bfprt算法
 */
public class Code01_FindMinKth {
    /**
     * 思路: 改写快排O(N)
     */
    public static int minKth1(int[] arr, int k){
        if(arr==null || arr.length==0 || k>arr.length) throw new RuntimeException("wrong arg...");
        return process(arr, 0, arr.length-1, k-1);
    }

    /**
     * 在arr的L..R范围上,求排序后k位置的数.
     *
     * 如果L==R,返回arr[L]
     *
     * 在L到R上任意取一个数.
     * 这个数交换到R位置.作为比较数
     * 定义cur=L,表示从L开始遍历
     * 定义less=L-1,表示比R位置数小的区域边界,
     * 定义greater=R,表示比R位置数大的区域边界.
     * 循环:cur<greater
     *      如果cur的数小于R位置的数.
     *          交换cur和less+1位置
     *          less向右扩
     *          cur加1
     *      如果cur的数等于R位置的数.
     *          cur加1
     *      如果cur的数大于R位置的数
     *          交换greater-1位置和cur位置
     *          greater向左扩.
     * 交换R位置和greater-1位置
     * greater向右扩
     * 此时less为小于区的右边界,greater为大于区的左边界.
     * 如果k大于less且小于greater,返回k位置的数.
     * 如果k小于等于less,递归调用:L, less
     * 如果k大于等于greater,递归调用greater,R
     */
    private static int process(int[] arr, int L, int R, int k) {
        if(L==R) return arr[L];
        int randIndex = (int)(Math.random()*(R-L+1))+L;
        swap(arr, randIndex, R);
        int cur = L;
        int less = L-1;
        int greater = R;
        while (cur<greater){
            if(arr[cur]<arr[R]){
                swap(arr, cur++, ++less);
            }else if(arr[cur] == arr[R]){
                cur++;
            }else {
                swap(arr, --greater, cur);
            }
        }
        swap(arr, R, greater++);
        if(k>less&& k<greater)return arr[k];
        if(k<=less){
            return process(arr, L, less, k);
        } else {
            return process(arr, greater, R, k);
        }
    }

    /**
     * 思路二:bfprt算法
     *      改写快排的方法,每次比较值会随机选择一个位置.
     *      这个位置的可能比较靠近中部,这样可以快速淘汰掉至少一半的数据.
     *      也可能靠近量表,这样需要重新经历一个O(N)的过程.
     *      只是通过随机,让各种可能性平均下来时间复杂度收敛到O(N)
     *      brprt算法的想法是,每次都选择一个平凡值,
     *      这个平凡值至少可以淘汰掉部分数据.不会出现最差情况.
     *      最后通过公式证明复杂度同样可以收敛到O(N),而不是依赖概率.
     *      思路是:
     *          1)将数组每5个数看做一组
     *          2)将每个小组范围进行排序.
     *          3)找到每个小组排序后的中位数组成新的组midArr
     *          4)对上面的中位数组midArr找到排序后中间位置的数x.
     *          5)用上面的数x作为partition的比较值.
     *          解释:midArr的大小是N/5,由于x是midArr中间位置的数,
     *              有N/10的数比x小,又midArr为每5个数组成的中位数构成的小组,
     *              所以有N*3/10的数比x小.同理可得有N*3/10的数比x大.
     *              所以每次至少可以淘汰掉3/10的数.最后通过数学证明时间复杂度
     *              可以收敛到O(N).
     */
    public static int bfprt(int[] arr, int k) {
        if(arr==null || arr.length==0 || k>arr.length) throw new RuntimeException("wrong arg...");
        return process2(arr, 0, arr.length-1, k-1);
    }

    /**
     * 如果L==R,直接返回.
     *
     * 定义midArr作为每5个数中位数组成的数组.如果arr.length是5的倍数,长度为N/5,否则为N/5+1
     * 循环:i从0开始;i不超过arr长度;i步长为5
     *      以i为起始位置,以i+4或arr.length-1中较小的数为结束位置,
     *      对开始位置和结束位置范围上进行排序.
     *      同时收集排序后的中位数.放入midArr
     * 递归调用bfprt:midArr, midArr.length/2.得到midArr中位数.mid
     *
     *
     * 用mid作为partition的比较值,
     * 定义less表示小于区的右边界,初始化为L-1
     * 定义greater表示大于区的左边界初始化为R+1
     * 定义cur表示当前位置,初始化为L
     * 循环:cur<greater
     *      cur值小于比较值.
     *          交换cur和less+1位置.
     *          less加一
     *          cur加一
     *      cur值等于比较值
     *          cur加1
     *      cur值大于比较值
     *          cur和greater-1交换
     *          greater减1
     * 如果k大于less且小于greater,直接返回k位置的数.
     * 如果k小于等于less,递归:(0, less,k)
     * 如果k大于等于greater,递归:(greater,R,k)
     */
    private static int process2(int[] arr, int L, int R, int k) {
        if(L==R) return arr[L];

        // 使用bfprt算法找到一个平凡值作为比较值.
        int midArrLength = (R-L+1)%5==0?(R-L+1)/5:(R-L+1)/5+1;
        int[] midArr = new int[midArrLength];
        for (int i = 0; i < midArrLength; i++) {
            int start = L+i*5;
            int end = Math.min(R, start+4);
            insertSort(arr, start, end);
            midArr[i] = arr[start+(end-start)/2];
        }
        int mid = bfprt(midArr, midArrLength / 2);

        int less = L-1;
        int greater = R+1;
        int cur = L;
        while (cur<greater){
            if(arr[cur]<mid){
                swap(arr, ++less, cur++);
            }else if(arr[cur]==mid){
                cur++;
            }else {
                swap(arr, --greater, cur);
            }
        }
        if(k>less && k<greater)return arr[k];
        if(k<=less){
            return process2(arr, 0, less, k);
        }else {
            return process2(arr, greater, R, k);
        }
    }

    /**
     * 用插入排序对arr数组的L..R范围进行排序
     */
    private static void insertSort(int[] arr, int L, int R) {
        if(arr==null|| L==R) return;
        for (int i = 1; i < arr.length; i++) {
            for (int j = i-1; j >=0 && arr[j]>arr[j+1]; j--) {
                swap(arr, j, j+1);
            }
        }
    }


    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
//        int [] arr = new int[]{1, 2, 3, 5, 6, 7, 10, 10};
//        int k = 7;
//        int ans1 = minKth1(arr, k);
//        int ans2 = bfprt(arr, k);
//        System.out.println(ans1);
//        System.out.println(ans2);

        int testTime = 1000000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = minKth1(arr, k);
            int ans2 = 0 ;
            try{
               ans2 = bfprt(arr, k);
            }catch (RuntimeException e){
                MyCompValue.printArr(arr);
                System.out.println(k);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                throw new RuntimeException(e);
            }
//            int ans3 = minKth3(arr, k);
            if (ans1 != ans2
//                    || ans2 != ans3
            ) {
                MyCompValue.printArr(arr);
                System.out.println(k);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                throw new RuntimeException();
            }
        }
        System.out.println("test finish");
    }

    /**
     * 交换i位置和j位置的数
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
//        arr[i] = arr[i]^arr[j];
//        arr[j] = arr[i]^arr[j];
//        arr[i] = arr[i]^arr[j];
    }
}
