package com.zs.tixi.class38;

/*
 * 1.给定一个数组arr,两个整数lower和upper,返回arr中有多少个子数组累加在[lower,upper]范围上.
 * // 这道题直接在leetcode测评：
 * // https://leetcode.com/problems/count-of-range-sum/
 */
public class Code01_CountofRangeSum {

    /**
     * 归并排序改写。O(N*logN)
     * 如果nums为空直接返回0.
     * 生成前缀和数组。
     * 对前缀和数组调用process，使得0-length-1范围有序并返回答案。
     */
    public static int countRangeSum(int[] nums, int lower, int upper){
        if(nums==null || nums.length==0) return 0;
        long[] sums = new long[nums.length];
        sums[0] = nums[0];
        for (int i = 1; i < sums.length; i++) {
            sums[i] = nums[i]+sums[i-1];
        }
        return process(sums, 0, sums.length-1, lower, upper);
    }

    /**
     * 使得sums数组在L..R范围有序，且返回L..R范围内的答案。
     * 如果L==R，无法二分。直接返回答案。
     * 找到L和R的中点位置M。
     * 递归调用process（L， M）得到左半部分的结果。
     * 递归调用precess（M+1， R）得到右半部分的结果。
     * 调用merge得到边界分别在左右的结果。
     * 上面三个结果相加后返回。
     */
    private static int process(long[] sums, int L, int R, int lower, int upper){
        if(L==R) return sums[L]>=lower && sums[L]<=upper?1:0;
        int M = L+(R-L>>1);
        return process(sums, L, M, lower, upper)+
                process(sums, M+1, R, lower, upper)+
                merge(sums, L, M, R, lower, upper);

    }

    /**
     * 左右两侧已经有序，请返回左边界在左侧，右边界在右侧，且满足要求的子数组。
     * 遍历右边界，尝试统计以i位置为结尾位置的子数组有多少符合条件。
     * merge使得整体有序。
     * 定义变量l，r。表示在右侧位置固定的情况下，左侧符合条件的左边界区间。
     * 遍历右侧：i
     *      根据sums[i]得到左边界前缀和的合法范围。
     *      由于右侧升序排列，所以l和r无需回退。
     *      r向右直到大于合法的最大值。
     *      l向右直到大于等于合法的最小值。
     *      r-l得到当前右侧位置下，左边界有多少种情况符合要求。
     * 进行归并排序种的merge操作。
     * 定义变量left从L开始，right从M+1开始。
     * 右侧小的情况下，右侧放入copy数组指针移动，否则左侧放入copy数组指针移动。
     * 将copy数组数组复制到L..R内。
     */
    private static int merge(long[] sums, int L, int M, int R, int lower, int upper){
        int ans = 0;

        int l = L;
        int r = L;
        for (int i = M+1; i <= R; i++) {
            long max = sums[i]-lower;
            long min = sums[i]-upper;
            while (r<=M && sums[r]<=max){
                r++;
            }
            while (l<=M && sums[l]<min){
                l++;
            }
            ans += r-l;
        }
        int left = L;
        int right = M+1;
        long[] copy = new long[R-L+1];
        int index = 0;
        while (left<=M && right<=R){
            copy[index++] = sums[right]<sums[left]?sums[right++]:sums[left++];
        }
        while (left<=M){
            copy[index++] = sums[left++];
        }
        while (right<=R){
            copy[index++] = sums[right++];
        }
        for (int i = 0; i < copy.length; i++) {
            sums[L+i] = copy[i];
        }
        return ans;
    }

    /**
     * 有序表改写。
     * 问题转换为以i位置结尾的子数组有多少符合条件。
     * i位置的前缀和为preSum
     * 统计之前有多少前缀和在【preSum-upper, preSum-lower】范围内。
     * 这个数就是以i位置结尾的子数组有多少符合条件。
     * 每到一个位置，将这个位置的前缀和放入有序表。
     * 在有序表种做范围查询，时间为O（logN）
     * 遍历一遍数组，总时间复杂度为O(N*logN)
     */
    public static int countRangeSum2(int[] nums, int lower, int upper){
        if(nums == null || nums.length==0) return 0;
        long sum = 0;
        int ans = 0;
        SBTreeMap treeMap = new SBTreeMap();
        treeMap.add(0);
        for (int i = 0;  i < nums.length; i++) {
            sum+=nums[i];
            ans += treeMap.range(sum-upper, sum-lower);
            treeMap.add(sum);
        }
        return ans;
    }

    /**
     * 由于是统计前缀和数据，只需要key，不需要val。
     * all：新增数据项，用来记录当前节点经过了多少个数。
     */
    public static class SBTNode{
        public long key;
        public SBTNode l;
        public SBTNode r;
        public int size;
        public int all;

        public SBTNode(long k){
            key = k;
            size = 1;
            all = 1;
        }
    }

    /**
     * 提供add方法，允许添加重复值。
     * 提供rangeCount方法，统计指定范围的数据量。
     */
    public static class SBTreeMap{
        private SBTNode root;

        /**
         * 左右旋同时要更新all.
         */
        private SBTNode leftRotate(SBTNode cur){
            int same = cur.all -getAll(cur.l)-getAll(cur.r);
            SBTNode right = cur.r;
            cur.r = right.l;
            right.l = cur;
            right.size = cur.size;
            cur.size = getSize(cur.l)+getSize(cur.r)+1;
            right.all = cur.all;
            cur.all = getAll(cur.l)+getAll(cur.r)+same;
            return right;
        }

        /**
         * 返回当前节点all数据项
         * @param cur
         * @return
         */
        private int getAll(SBTNode cur) {
            return cur==null?0:cur.all;
        }

        /**
         * 右旋
         */
        private SBTNode rightRotate(SBTNode cur){
            int same = cur.all - getAll(cur.l) - getAll(cur.r);
            SBTNode left = cur.l;
            cur.l = left.r;
            left.r =  cur;
            left.size = cur.size;
            cur.size = getSize(cur.l)+getSize(cur.r)+1;
            left.all = cur.all;
            cur.all = getAll(cur.l)+getAll(cur.r)+same;
            return left;
        }

        private int getSize(SBTNode cur) {
            return cur==null?0:cur.size;
        }

        /**
         * 检查并保持平衡。
         */
        private SBTNode maintain(SBTNode cur){
            int lSize = getSize(cur.l);
            int llSize = 0;
            int lrSize = 0 ;
            if(cur.l!=null){
                llSize = getSize(cur.l.l);
                lrSize = getSize(cur.l.r);
            }
            int rSize = getSize(cur.r);
            int rrSize = 0;
            int rlSize = 0;
            if(cur.r != null){
                rrSize = getSize(cur.r.r);
                rlSize = getSize(cur.r.l);
            }
            if(llSize>rSize){
                cur = rightRotate(cur);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }else if(lrSize>rSize){
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }else if(rrSize>lSize){
                cur = leftRotate(cur);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }else if(rlSize>lSize){
                cur.r = rightRotate(cur.r);
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }
            return cur;
        }

        /**
         * 先判断key是否存在，然后添加。
         */
        public void add(long key){
            root = add(root, key);
        }

        /**
         * add方法，允许新增重复数据
         * contains表示是否存在当前key.
         *
         * cur为空，直接新建节点返回.
         * cur.all加1.
         * 如果key等于cur.key,返回cur。
         * 如果不包含，cur.size加1
         * 如果key小于cur.key,去左树添加。
         * 否则，去右树添加。
         * 调整cur并返回。
         */
        private SBTNode add(SBTNode cur, long key){
            if(cur == null) return new SBTNode(key);
            cur.all++;
            if(cur.key==key) return cur;
            if(key<cur.key){
                cur.l = add(cur.l, key);
            }else {
                cur.r = add(cur.r, key);
            }
            cur.size = getSize(cur.l)+getSize(cur.r)+1;
            return maintain(cur);
        }

        /**
         * 返回[start, end]范围上有多少个数。
         */
        public int range(long start, long end){
            return lessKeyCount(end+1)-lessKeyCount(start);
        }

        /**
         * 返回小于指定key的数据有多少个。
         * root开始一路向下滑动。
         * 等于返回
         * 向右滑收集答案。
         * 向左滑不收集
         *
         * 表为空，返回0.
         * 定义cur为head。
         * 定义ans收集答案。
         * 循环：cur不为空
         *      如果cur.key等于key
         *          返回ans累加上cur.l的all
         *      如果key小于cur.key
         *          cur来到左树
         *      否则cur来到右树
         *          ans累加上cur.all减去右树的all
         *          cur来到右树
         * 返回ans
         */
        public int lessKeyCount(long key){
            SBTNode cur = root;
            int ans = 0;
            while (cur!=null){
                if(key == cur.key) return ans+getAll(cur.l);
                if(key < cur.key){
                    cur = cur.l;
                }else {
                    ans += cur.all-getAll(cur.r);
                    cur = cur.r;
                }
            }
            return ans;
        }
    }
}
