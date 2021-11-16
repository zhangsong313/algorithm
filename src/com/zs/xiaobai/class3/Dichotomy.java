package com.zs.xiaobai.class3;

import com.zs.xiaobai.common.MyCompValue;

/**
 * 二分法
 * 总结：
 * 1、数组边界条件处理,常见：数组为空，长度为0，长度为1。
 * 2、数组左右边界位置是否需要特殊判断：当问题是有关左右两侧位置时（左右边界只有一侧有值）。
 * 3、循环条件为L<=R,确保循环在R<L情况下结束检查。
 * 4、找到中间位置，考虑到int上限和效率问题，建议使用 int mid = L + (R-L>>1);
 * 5、判断mid位置情况，调整L和R：如果mid为最终结果直接返回，
 *      a) 如果mid位置非目标位置，且L和R位置没有循环前检查，L或R更新到mid的下一个位置。（mid和L/R位置情况不同）
 *      b) 如果mid位置非目标位置，且L和R位置在循环前已经检查，将L或R更新到mid位置。 (mid和L/R位置情况相同)
 */
public class Dichotomy {
    // 有序数组找到num.
    private static int find (int[] arr, int num){
        if (arr == null || arr.length==0){
            return -1;
        }
        int L = 0; // 定义左边界
        int R = arr.length-1; // 定义右边界

        while (L<=R){ // 循环条件设置为R不小于L（当只有一个值时也要检查）

            int mid = L+((R-L)>>1); //获取中间位置
            if (arr[mid] == num) return mid; // 检查中间位置
            // 根据中间位置值与目标值的大小，移动L或R。
            if (arr[mid]>num){
                R = mid-1;
            } else {
                L = mid+1;
            }
        }
        return -1;
    }


    // 有序数组找到大于等于num的最左边的位置。
    private static int findLeftestLargerIndex(int[] arr, int num){
        if (arr == null || arr.length==0) return -1;
        int L = 0;
        int R = arr.length-1;
        int ans = -1;
        while (L<=R){
            int mid = L+(R-L>>1);
            if (arr[mid] >= num){
                ans = mid;
                R = mid-1;
            } else {
                L = mid+1;
            }
        }
        return ans;
    }
    // 无序数组，且相邻数不同。找出局部最小值位置。(当前位置值小于左侧和右侧，如果左侧或右侧没有值，认为无穷大)
    private static int findAreaMostSmall(int[] arr){
        if (arr == null || arr.length==0) return -1;
        if (arr.length==1) return 0; // 当只有一个值时，左右两侧都为无穷大，为特殊情况。
        int L = 0;
        int R = arr.length-1;
        // 由于局部最小值问题中，数组的边界位置为特殊位置，需要在循环开始前进行检查。
        if (arr[L]<arr[L+1]) return L;
        if (arr[R]<arr[R-1]) return R;
        while (L<=R){
            int mid = L+(R-L>>1);
            if (arr[mid] < arr[mid-1] && arr[mid] < arr[mid+1]) return mid;
            if (arr[mid] > arr[mid-1]){
                R = mid;
            } else {
                L = mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
//        MyCompValue.checkFind(10000, 100, 100, Dichotomy::find);
//        MyCompValue.checkFindLeftestLargerIndex(10000, 100, 100, Dichotomy::findLeftestLargerIndex);
//        MyCompValue.checkFindAreaMostSmall(10000, 1000, 100, Dichotomy::findAreaMostSmall2);
//        int[] arr = {5, 4, 1, 3, 9};
//        find2(arr, 6);
//        System.out.println(findAreaMostSmall(arr ));;
    }
}
