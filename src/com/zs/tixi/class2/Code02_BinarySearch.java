package com.zs.tixi.class2;

import com.zs.xiaobai.common.MyCompValue;

public class Code02_BinarySearch {
    /**
     * 有序数组二分查找指定值:
     *      要求左边界不能大于右边界，
     *      如果中点是要找的值，直接返回，
     *      如果比目标值大，右边界移动到中点左侧，否则左边界哟东到中点右侧。
     * 时间复杂度：O(logN)
     * 空间复杂度: O(1)
     */
    public static int binarySearch(int[] arr, int t){
        if(arr==null || arr.length==0){
            return -1;
        }
        int L = 0;
        int R = arr.length-1;
        while (L<=R){
            int mid = L+(R-L >> 1);
            if(arr[mid]==t) return mid;
            if(arr[mid]>t){
                R=mid-1;
            }else {
                L=mid+1;
            }
        }
        return -1;
    }

    /**
     * 有序数组中找到>=num最左的位置
     */
    public static int leftestLagerSearch(int[] arr, int t){
        if(arr==null || arr.length==0){
            return -1;
        }
        int L = 0;
        int R = arr.length-1;
        int ans = -1;
        while (L<=R){
            int mid = L+(R-L>>1);
            if(arr[mid]>=t){
                ans = mid;
                R = mid-1;
            }else {
                L = mid+1;
            }
        }
        return ans;
    }

    /**
     * 有序数组中找到<=num最右的位置
     */
    public static int rightestSmallerSearch(int[] arr, int t){
        if(arr==null || arr.length==0){
            return -1;
        }
        int L = 0;
        int R = arr.length-1;
        int ans = -1;
        while (L<=R){
            int mid = L+(R-L>>1);
            if(arr[mid]<=t){
                ans = mid;
                L = mid+1;
            }else {
                R = mid-1;
            }
        }
        return ans;
    }

    /**
     * 7 局部最小值问题
     * 定义何为局部最小值：
     * arr[0] < arr[1]，0位置是局部最小；
     * arr[N-1] < arr[N-2]，N-1位置是局部最小；
     * arr[i-1] > arr[i] < arr[i+1]，i位置是局部最小；
     * 给定一个数组arr，已知任何两个相邻的数都不相等，找到随便一个局部最小位置返回
     */
    public static int areaSmallestSearch(int[] arr){
        if(arr==null || arr.length==0){
            return -1;
        }
        int N = arr.length;
        if (N==1) return 0;
        if (arr[0]<arr[1]) return 0;
        if (arr[N-1]<arr[N-2]) return N-1;

        // 由于arr[0]>arr[1] 且 arr[N-2]<arr[N-1]， 两侧都是向中间下降趋势，因此中间必定有个位置是局部极小值。
        int L = 0;
        int R = N-1;
        while (L<=R){
            int mid = L+(R-L>>1);
            if (arr[mid]<arr[mid-1] && arr[mid]<arr[mid+1]) return mid;
            if(arr[mid]>arr[mid-1]){
                R=mid;
            }else {
                L=mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        MyCompValue.checkFind(1000, 100, 999, Code02_BinarySearch::binarySearch);
        MyCompValue.checkFindLeftestLargerIndex(1000, 100, 999, Code02_BinarySearch::leftestLagerSearch);
        MyCompValue.checkFindAreaMostSmall(1000, 100, 999, Code02_BinarySearch::areaSmallestSearch);
    }
}
