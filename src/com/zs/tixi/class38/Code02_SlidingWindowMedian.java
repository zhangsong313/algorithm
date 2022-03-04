package com.zs.tixi.class38;

/*
 * 有一个滑动窗口：
 * 1）L是滑动窗口最左位置、R是滑动窗口最右位置，一开始LR都在数组左侧
 * 2）任何一步都可能R往右动，表示某个数进了窗口
 * 3）任何一步都可能L往右动，表示某个数出了窗口
 * 想知道每一个窗口状态的中位数
 *
 * leetCode问题：
 * 窗口大小固定为k,返回窗口在nums数组中从左到右的中位数结果。
 *
 * 思路：
 * 改写有序表：
 *      支持添加重复数据。
 *      支持删除指定数据。
 *      支持查询排序后第k个数。
 *
 */
public class Code02_SlidingWindowMedian {


//    public static void main(String[] args) {
//        double[] doubles = medianSlidingWindow(new int[]{-2147483648,-2147483648,2147483647,-2147483648,1,3,-2147483648,-100,8,17,22,-2147483648,-2147483648,2147483647,2147483647,2147483647,2147483647,-2147483648,2147483647,-2147483648}, 6);
//        for (double d : doubles) {
//            System.out.print(d+" ");
//        }
//    }
    /**
     * 创建SBTreeSet 对象。set
     * 先设置窗口的L和R初始位置。
     * 循环：R不超过右边界情况下。
     *      k为奇数。收集中位数。
     *      k为偶数，计算上下中位数和的一半后收集。
     *      窗口L和R都向右移动。同时更新set。
     * 返回答案.
     */
    public static double[] medianSlidingWindow(int[] nums, int k) {
        SBTreeSet<Node> set = new SBTreeSet<>();
        for (int i = 0; i < k-1; i++) {
            set.add(new Node(i, nums[i]));
        }
        double[] ans = new double[nums.length-k+1];
        int index = 0;
        boolean isEven = k%2==0;
        int lower = k/2;
        int upper = k/2+1;
        for (int i = k-1; i < nums.length; i++) {
            set.add(new Node(i, nums[i]));
            if(isEven){
                int lMid = set.getIndexKey(lower).val;
                int uMid = set.getIndexKey(upper).val;
                ans[index++] = ((double)lMid+(double)uMid) / 2;
            }else {
                ans[index++] = set.getIndexKey(upper).val;
            }
            set.remove(new Node(i-k+1,nums[i-k+1]));
        }
        return ans;
    }

    /**
     * 包装后的节点。
     * val值相同 的情况下，由于index是外部流水号生成，不会重复，保证加入有序表时不会覆盖节点。
     */
    public static class Node implements Comparable<Node>{
        public int val;
        public int index;
        public Node(int i, int v){
            val = v;
            index = i;
        }

        @Override
        public int compareTo(Node o) {
            return val==o.val?Integer.valueOf(index).compareTo(o.index):Integer.valueOf(val).compareTo(o.val);
        }

        @Override
        public String toString() {
            return ""+val;
        }
    }

    /**
     * 有序表节点
     * key
     * size：当前树有多少节点。平衡因子=
     * l:左树
     * r：右树
     */
    public static class SBTNode<K extends Comparable<K>>{
        public K key;
        public int size;
        public SBTNode<K> l;
        public SBTNode<K> r;
        public SBTNode(K k){
            key = k;
            size = 1;
        }
    }

    public static class SBTreeSet<K extends Comparable<K>>{
        private SBTNode<K> root;

        /**
         * 加入一个数
         */
        public void add (K key){
            root = add(root, key);
        }

        /**
         * 在cur树上新增key。
         *
         * 如果cur为空，直接新建节点返回。
         * 如果cur.key等于key
         *      返回cur.
         * 如果key小于cur的key
         *      去左树添加
         * 否则去右树添加。
         * 调整cur后返回。
         */
        private SBTNode<K> add(SBTNode<K> cur, K key){
            if(cur==null) return new SBTNode(key);
            cur.size++;
            if(key.compareTo(cur.key)<0){
                cur.l = add(cur.l, key);
            }else {
                cur.r = add(cur.r, key);
            }
            return maintain(cur);
        }

        /**
         * 检查并调整平衡性
         * 查询需要的树大小
         * lSize, rSize,llSize, lrSize, rrSize, rlSize
         * 如果llSize大于rSize
         *      右旋
         *      调整右节点
         *      调整头节点
         * 如果lrSize大于rSize
         *      左树右旋
         *      头左旋
         *      调整左树
         *      调整右树
         *      调整头节点
         * 右树反之
         */
        private SBTNode<K> maintain(SBTNode<K> cur) {
            int lSize = getSize(cur.l);
            int rSize = getSize(cur.r);
            int llSize = 0;
            int lrSize = 0;
            if(cur.l!=null){
                llSize = getSize(cur.l.l);
                lrSize = getSize(cur.l.r);
            }
            int rrSize = 0;
            int rlSize = 0;
            if (cur.r!=null){
                rrSize = getSize(cur.r.r);
                rlSize = getSize(cur.r.l);
            }
            if(llSize>rSize){
                cur = rightRotate(cur);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }else if (lrSize>rSize){
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }else if(rrSize>lSize){
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
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
         * 左旋
         * @param cur
         * @return
         */
        private SBTNode<K> leftRotate(SBTNode<K> cur) {
            SBTNode<K> right = cur.r;
            cur.r = right.l;
            right.l = cur;
            right.size = cur.size;
            cur.size = getSize(cur.l)+getSize(cur.r)+1;
            return right;
        }

        /**
         * 右旋
         * @param cur
         * @return
         */
        private SBTNode<K> rightRotate(SBTNode<K> cur) {
            SBTNode left = cur.l;
            cur.l = left.r;
            left.r = cur;
            left.size=cur.size;
            cur.size = getSize(cur.l)+getSize(cur.r)+1;
            return left;
        }

        private int getSize(SBTNode<K> cur) {
            return cur!=null?cur.size:0;
        }

        /**
         * 是否包含当前key。
         * 找到最接近key的节点。
         * 判断节点key是否等于key
         */
        private boolean isContainsKey(K key){
            SBTNode<K> lastIndex = findLastIndex(key);
            return lastIndex!=null&& key.compareTo(lastIndex.key)==0;
        }

        /**
         * 返回最接近key的节点
         * 从root向下滑，直到为空。
         * 遇到key等于直接返回。
         * 记录下当前节点。
         * key小于向左滑。
         * key大于向右滑。
         *
         * 返回最后的节点
         * @param key
         * @return
         */
        private SBTNode<K> findLastIndex(K key){
            SBTNode<K> cur = root;
            SBTNode<K> pre = null;
            while (cur!=null){
                if(key.compareTo(cur.key)==0) return cur;
                pre = cur;
                if(key.compareTo(cur.key)<0){
                    cur = cur.l;
                }else {
                    cur = cur.r;
                }
            }
            return pre;
        }

        /**
         * 删除一个数
         * 如果不包含key，直接返回。
         * root执行delete
         */
        public void remove(K key){
            if(!isContainsKey(key))return;
            root = delete(root, key);
        }

        /**
         * key对应节点减少一个数。
         * cur.all减1
         * 如果key小于当前key
         *      去左树删除。
         * 如果key大于当前可以
         *      去右树删除
         * 否则当前节点为要删除的节点。
         *      查出same
         *      如果same大于0，返回
         *      如果左右都为空
         *          cur设置为空。
         *      如果左空右有
         *          cur设置为右树
         *      如果左有右空
         *          cur设置为左树
         *      如果左有右有
         *          从cur.r滑到后继节点next,找到后继节点next。
         *          从cur.r向左滑一直到next沿途节点。
         *              滑动过程中每个节点size减1
         *              每个节点all减去next的all
         *              同时记录下上一个节点pre
         *          如果cur.r有左树
         *              pre.l接next.r
         *              next.r接cur.r
         *          next.l接cur.l
         *          更新next的size和all
         *          cur来到next
         * 返回cur
         */
        private SBTNode<K> delete(SBTNode<K> cur, K key) {
            cur.size--; // 需要删除当前节点。
            if(key.compareTo(cur.key)<0){
                cur.l = delete(cur.l, key);
            }else if (key.compareTo(cur.key)>0){
                cur.r = delete(cur.r, key);
            }else {

                if(cur.l==null&& cur.r==null){
                    cur = null;
                }else if(cur.l==null&&cur.r!=null){
                    cur = cur.r;
                }else if(cur.l!=null&&cur.r==null){
                    cur = cur.l;
                }else{
                    SBTNode<K> next = cur.r;
                    SBTNode pre = null;
                    while (next.l!=null){ // cur.r的左边界每个节点调整all和size
                        next.size--;
                        pre = next;
                        next = next.l;
                    }

                    if(pre!=null){ // cur.r有左树。调整pre.l和next.r
                        pre.l = next.r;
                        next.r = cur.r;
                    }

                    next.l = cur.l; // 设置cur.l

                    next.size = cur.size;// 调整cur.size和cur.all

                    cur = next;
                }
            }
            return cur;
        }

        /**
         * 返回排序后第kth个数
         * 如果kth小于0或者大于总大小。直接异常。
         * 从root开始向下滑
         * kth在左树范围内，向左滑
         * kth在头减去右树范围内，返回当前数。
         * 否则，向右滑。
         * @param kth
         * @return
         */
        public K getIndexKey(int kth){
            if(kth<0||kth>getSize(root)) throw new RuntimeException("out of range : "+kth+" --- "+getSize(root));
            return getIndex(root, kth);
        }
        private K getIndex(SBTNode<K> cur, int kth){
            if(kth==getSize(cur.l)+1){
                return cur.key;
            }else if(kth<=getSize(cur.l)){
                return getIndex(cur.l, kth);
            }else {
                return getIndex(cur.r, kth - getSize(cur.l)-1);
            }
        }
//        public void printTree(){
//            System.out.print("[");
//            if(root!=null)pre(root);
//            System.out.println("]");
//
//        }
//        private void pre(SBTNode<K> cur){
//            if(cur==null) return;
//            pre(cur.l);
//            System.out.print(cur.key+" ");
//            pre(cur.r);
//        }
    }
}
