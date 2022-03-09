package com.zs.tixi.class40;



/**
 * //本文件是Code02_SnacksWays问题的牛客题目解答
 * //但是用的分治的方法
 * //这是牛客的测试链接：
 * //https://www.nowcoder.com/questionTerminal/d94bb2fa461d42bcb4c0f2b94f5d4281
 * //把如下的全部代码拷贝进编辑器（java）
 * //可以直接通过
 *
 * 采用分治思想。
 * 将arr分为左右两部分。
 * 左右分别暴力枚举所有选择方法，
 *      使用map收集所有方法的背包容量。（每种方法的背包容量都是不同的，超出w的就不要收集了）
 *
 * 定义ans收集答案。
 * 遍历左侧结果集
 *      当前背包容量为left
 *      从右侧结果集种查出小于等于w-left的方法数funCount
 *      ans累加上funCount
 * 返回ans
 */
import java.util.Scanner;

public class Code02_SnacksWaysMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int bag = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
        }
        long ways = ways(arr, bag);
        System.out.println(ways);
        sc.close();
    }

    /**
     * 分治统计有多少种零食的拿法。
     */
    private static long ways(int[] arr, int bag) {
        int N = arr.length;
        int mid = arr.length-1 >> 1;
        SBTSet<Long> leftSet = new SBTSet<>();
        process(arr, bag, 0, mid, 0L,leftSet);
        SBTSet<Long> rightSet = new SBTSet<>();
        process(arr, bag, mid+1, arr.length-1, 0L, rightSet);

        int ans = 0;
        for (int i = 0; i < leftSet.size(); i++) {
            Long leftWeight = leftSet.getIndexKey(i);
            ans += rightSet.lessEqualsCount(bag - leftWeight);
        }
        return ans;
    }

    /**
     * 当前来到i位置，背包内放入了preSum体积的零食，
     * 将i..end的所有选择后的体积放入set，但要求总体积不能超过bag
     * 如果当前来到end+1位置，set添加preSum后返回。
     * 尝试不拿当前位置零食，递归调用(i+1, preSum)
     * 如果arr[i]+preSum<=bag, 递归调用(i+1, preSum+arr[i])
     */
    private static void process(int[] arr, int bag, int i, int end, Long preSum, SBTSet<Long> set) {
        if(i == end+1){
            set.put(preSum);
            return;
        }
        process(arr, bag, i+1,end, preSum, set);
        if(arr[i]+preSum<=bag){
            process(arr, bag, i+1,end, preSum+arr[i], set);
        }
    }

    public static class SBTNode<K extends Comparable<K>>{
        public K key;
        public int all;
        public int size;
        public SBTNode l;
        public SBTNode r;
        public SBTNode(K k){
            key = k;
            size = 1;
            all = 1;
        }
    }

    public static class SBTSet<K extends Comparable<K>>{
        private SBTNode<K> root;

        public int size(){
            return getAll(root);
        }

        /**
         * 放入一个数据。
         */
        public void put(K key){
            root = add(root, key);
        }

        /**
         * 在cur树上新增节点，值为key。
         */
        private SBTNode<K> add(SBTNode<K> cur, K key) {
            if(cur==null) return new SBTNode<>(key);
            cur.all++;
            if(key.compareTo(cur.key)==0) return cur;
            if(key.compareTo(cur.key)<0){
                cur.l = add(cur.l, key);
            }else {
                cur.r = add(cur.r, key);
            }
            cur.size = getSize(cur.l)+getSize(cur.r)+1;
            return maintain(cur);
        }

        /**
         * 调整cur节点的平衡性并返回新节点。
         *
         */
        private SBTNode<K> maintain(SBTNode<K> cur) {
            int ls = getSize(cur.l);
            int lls = 0;
            int lrs = 0;
            if(cur.l != null){
                lls = getSize(cur.l.l);
                lrs = getSize(cur.l.r);
            }
            int rs = getSize(cur.r);
            int rrs = 0;
            int rls = 0;
            if(cur.r != null){
                rrs = getSize(cur.r.r);
                rls = getSize(cur.r.l);
            }

            if(lls>rs){
                cur = rightRotate(cur);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }else if(lrs>rs){
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                cur.l = maintain(cur.l);
                cur.r= maintain(cur.r);
                cur = maintain(cur);
            }else if(rrs>ls){
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur = maintain(cur);
            }else if(rls>ls){
                cur.r = rightRotate(cur.r);
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }

            return cur;
        }

        /**
         * 左旋
         */
        private SBTNode<K> leftRotate(SBTNode<K> cur) {
            int same = cur.all - getAll(cur.l) - getAll(cur.r);
            SBTNode<K> right = cur.r;
            cur.r = right.l;
            right.l = cur;
            right.size = cur.size;
            cur.size = getSize(cur.l)+getSize(cur.r)+1;
            right.all = cur.all;
            cur.all = getAll(cur.l)+getAll(cur.r)+same;
            return right;
        }

        private int getAll(SBTNode<K> cur) {
            return cur==null?0:cur.all;
        }

        /**
         * 右旋
         */
        private SBTNode<K> rightRotate(SBTNode<K> cur) {
            int same = cur.all - getAll(cur.l) - getAll(cur.r);
            SBTNode left = cur.l;
            cur.l = left.r;
            left.r = cur;
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
         * 返回小于等于指定值的个数。
         * 从root开始向下滑。
         * 等于返回答案。
         * 向右滑收集答案。
         * 向左滑不收集。
         */
        public int lessEqualsCount(K key){
            SBTNode<K> cur = root;
            int ans = 0;
            while (cur!=null){
                if(key.compareTo(cur.key)==0) return ans+cur.all-getAll(cur.r);
                if(key.compareTo(cur.key)<0){
                    cur = cur.l;
                }else {
                    ans += cur.all-getAll(cur.r);
                    cur = cur.r;
                }
            }
            return ans;
        }

        /**
         * 返回排序后位于指定位置的key
         * 检查key是否超出集合范围。
         * 从root向下滑。
         * 如果小于左树all范围，来到左树找。
         * 如果大于等于cur.all-右树.all,去右树找。
         * 否则，返回当前值。
         */
        public K getIndexKey(int index){
            if(index<0 || index>=getAll(root)) throw new RuntimeException("out of range");
            return getIndex(root, index).key;
        }

        /**
         * 返回cur树上的第k个节点。
         */
        private SBTNode<K> getIndex(SBTNode<K> cur, int index){
            if(index<getAll(cur.l)){
                return getIndex(cur.l, index);
            }else if(index >= cur.all-getAll(cur.r)){
                return getIndex(cur.r, index-(cur.all-getAll(cur.r)));
            }else {
                return cur;
            }
        }
    }
}
