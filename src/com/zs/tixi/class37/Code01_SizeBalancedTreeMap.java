package com.zs.tixi.class37;

public class Code01_SizeBalancedTreeMap {
    /**
     * SB树节点
     * key
     * value
     * l : 左子树
     * r : 右子树
     * size : 节点数
     *
     * @param <K>
     * @param <V>
     */
    public static class SBTNode<K extends Comparable<K>, V> {
        public K key;
        public V value;
        public int size;
        public SBTNode<K, V> l;
        public SBTNode<K, V> r;

        public SBTNode(K k, V v){
            key = k;
            value = v;
            size = 1;
        }
    }

    public static class SizeBalancedTreeMap<K extends Comparable<K>, V>{
        /**
         * 根节点
         */
        private SBTNode<K, V> root;

        /**
         * 对cur右旋
         * 定义变量leftNode为cur的左子树
         * cur的左子树更新为leftNode的右子树
         * leftNode的右子树更新为cur
         * 更新leftNode的size为cur的size。
         * 更新cur的size：左子树节点数加上右子树节点数加1.
         * 返回leftNode。
         * @param cur
         * @return
         */
        private SBTNode<K, V> rightRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> left = cur.l;
            cur.l = left.r;
            left.r = cur;
            left.size = cur.size;
            cur.size = getSize(cur.l)+getSize(cur.r)+1;
            return left;
        }

        /**
         * 返回当前树的size
         */
        private int getSize(SBTNode<K, V> cur) {
            return cur!=null?cur.size:0;
        }

        /**
         * 左旋
         */
        private SBTNode<K, V> leftRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> right = cur.r;
            cur.r = right.l;
            right.l = cur;
            right.size = cur.size;
            cur.size = getSize(cur.l)+getSize(cur.r)+1;
            return right;
        }

        /**
         * 检查cur树的平衡性并调整。
         *
         * 空树直接返回。
         * leftSize：左树大小。
         * leftLeftSize：左树的左子树大小
         * leftRightSize:左树的右子树大小。
         * rightSize：右树大小
         * rightLeftSize：右树的左子树大小。
         * rightRightSize：右树的右子树大小。
         * 如果左左树大小大于右树大小
         * 		cur右旋后更新
         * 		cur的右子树调整。
         * 		cur调整。
         * 如果左右树大小大于右树大小
         * 		cur.l左旋后更新。
         * 		cur右旋后更新。
         * 		（此时cur，cur.l，cur.r均发生了变化）
         * 		cur.l调整
         * 		cur.r调整
         * 		cur调整
         * 如果右右树大小大于左树大小
         * 		反之
         * 如果右左树大小大于左树大小
         * 		反之
         * 返回cur
         * @param cur
         * @return
         */
        private SBTNode<K, V> maintain(SBTNode<K, V> cur) {
            if(cur==null) return null;
            int leftSize = getSize(cur.l);
            int leftLeftSize = 0;
            int leftRightSize = 0;
            if(cur.l!=null){
                leftLeftSize = getSize(cur.l.l);
                leftRightSize = getSize(cur.l.r);
            }
            int rightSize = getSize(cur.r);
            int rightRightSize = 0;
            int rightLeftSize = 0;
            if(cur.r != null){
                rightRightSize = getSize(cur.r.r);
                rightLeftSize = getSize(cur.r.l);
            }

            if(leftLeftSize>rightSize){
               cur = rightRotate(cur);
               cur.r = maintain(cur.r);
               cur = maintain(cur);
            }else if(leftRightSize>rightSize){
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }else if(rightRightSize>leftSize){
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur = maintain(cur);
            }else if(rightLeftSize>leftSize){
                cur.r = rightRotate(cur.r);
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }
            return cur;
        }

        /**
         * 找到最接近key的节点。
         * 同AVL树
         * @param key
         * @return
         */
        private SBTNode<K, V> findLastIndex(K key) {
            if(key==null) return null;
            SBTNode<K, V> pre = null;
            SBTNode<K, V> cur = root;
            while (cur!=null){
                if(key.compareTo(cur.key)==0) return cur;
                pre = cur;
                if(key.compareTo(cur.key)<0){
                    cur = cur.l;
                }else{
                    cur = cur.r;
                }
            }
            return pre;
        }

        /**
         * 找到最接近但不小于key的节点。
         * 同AVL树
         * @param key
         * @return
         */
        private SBTNode<K, V> findLastNoSmallIndex(K key) {
            SBTNode<K, V> ans = null;
            SBTNode<K, V> cur = root;
            while (cur!=null){
                if(key.compareTo(cur.key)==0) return cur;
                if(key.compareTo(cur.key)<0){
                    ans = cur;
                    cur = cur.l;
                }else {
                    cur = cur.r;
                }
            }
            return ans;
        }

        /**
         * 找到最近但不大于key的节点。
         * 同AVL树
         * @param key
         * @return
         */
        private SBTNode<K, V> findLastNoBigIndex(K key) {
            SBTNode<K, V> ans = null;
            SBTNode<K, V> cur = root;
            while (cur!=null){
                if(key.compareTo(cur.key)==0) return cur;
                if(key.compareTo(cur.key)<0){
                    cur = cur.l;
                }else {
                    ans = cur;
                    cur = cur.r;
                }
            }
            return ans;
        }

        /**
         * 在cur树中新增节点。
         * 如果cur为空，创建节点后返回。
         * 否则
         * 		cur的size加1
         * 		如果key小于cur.key
         * 			递归调用左子树添加
         * 		否则
         * 			递归调用右子树添加
         * 		调整cur的平衡性。
         * @param cur
         * @param key
         * @param value
         * @return
         */
        private SBTNode<K, V> add(SBTNode<K, V> cur, K key, V value) {
            if(cur==null) return new SBTNode<>(key, value);
            cur.size++;
            if(key.compareTo(cur.key)<0){
                cur.l = add(cur.l, key, value);
            }else {
                cur.r = add(cur.r, key, value);
            }
            return  maintain(cur);
        }

        /**
         * 在cur树上删除key的节点。
         * cur的size减一
         * 如果key小于cur.key
         * 		递归去左子树删除
         * 如果key大于cur.key
         * 		递归去右子树删除
         * 否则（cur就是要删除的节点）
         * 		如果左右都为空
         * 			cur更新为空
         * 		如果左空右有
         * 			cur更新为cur的右子树
         * 		如果左有右空
         * 			cur更新为cur的左子树
         * 		如果左右都不为空
         * 			找到后继节点next和后继节点的父节点pre
         * 			如果pre不为空
         * 				pre的左子树设置为next的右子树。
         * 				next的右子树设置为cur的右子树。
         * 			next的左子树设置为cur的左子树
         * 			更新next的size大小
         * 			cur更新为next。
         * 返回cur。
         * @param cur
         * @param key
         * @return
         */
        private SBTNode<K, V> delete(SBTNode<K, V> cur, K key) {
            cur.size--;
            if(key.compareTo(cur.key)<0){
                cur.l = delete(cur.l, key);
            }else if(key.compareTo(cur.key)>0){
                cur.r = delete(cur.r, key);
            }else {// 当前节点是要删除的节点。
                if(cur.l==null && cur.r==null){
                    cur = null;
                }else if(cur.l==null && cur.r!=null){
                    cur = cur.r;
                }else if(cur.l!=null && cur.r==null){
                    cur = cur.l;
                }else {
                    SBTNode<K, V> next = cur.r;
                    SBTNode<K, V> pre = null;
                    while (next.l!=null){
                        next.size--;
                        pre = next;
                        next = next.l;
                    }
                    if(pre!=null){ // 右子树至少有一个左子树.
                        pre.l = next.r;
                        next.r = cur.r;
                    }
                    next.l = cur.l;
                    next.size = next.l.size+getSize(next.r)+1;
                    cur = next;
                }
            }
            // ?????????????为什么删除完以后不需要重新尝试调整平衡呢？
            return cur;
        }

        /**
         * 返回第k个元素
         * 如果k等于左子树大小加1，返回当前元素。
         * 如果k小于等于左子树大小。
         * 		递归调用左子树查找，返回
         * 否则
         * 		递归调用右子树查找（k更新为k减去左子树大小再减1），返回
         * @param cur
         * @param kth
         * @return
         */
        private SBTNode<K, V> getIndex(SBTNode<K, V> cur, int kth) {
            if(getSize(cur.l)+1 == kth){
                return cur;
            }else if(kth<= getSize(cur.l)){
                return getIndex(cur.l, kth);
            }else {
                return getIndex(cur.r, kth-getSize(cur.l)-1);
            }
        }

        /**
         * 节点数
         * @return
         */
        public int size() {
            return root==null?0:root.size;
        }

        /**
         * 是否包含指定key
         * 查找最接近key的节点，
         * 如果不等于key返回false，否则返回true
         * @param key
         * @return
         */
        public boolean containsKey(K key) {
            if(key==null) return false;
            SBTNode<K, V> lastIndex = findLastIndex(key);
            if(lastIndex!=null && key.compareTo(lastIndex.key)==0) {
                return true;
            }
            return false;
        }

        /**
         * 设置key，value
         * key为空返回。
         * 查找最接近key的节点
         * 如果等于key，更新该节点的value
         * 否则，调用add在root上新增节点。
         * @param key
         * @param value
         */
        public void put(K key, V value) {
            if(key==null) return;
            SBTNode<K, V> lastIndex = findLastIndex(key);
            if(lastIndex!=null && key.compareTo(lastIndex.key)==0){
                lastIndex.value = value;
            }else {
                root = add(root, key, value);
            }
        }

        /**
         * 删除指定key对应的节点
         * @param key
         */
        public void remove(K key) {
            if(key==null) return;
            if(containsKey(key)){
                root = delete(root, key);
            }
        }

        /**
         * 返回指定位置的key。
         * 如果位置小于0或大于总大小。异常。
         * 调用getIndex（root， index+1）的key
         * index+1是由于用户调用从0位置开始。
         * @param index
         * @return
         */
        public K getIndexKey(int index) {

        }

        /**
         * 返回指定位置的value
         * @param index
         * @return
         */
        public V getIndexValue(int index) {

        }

        /**
         * 返回key对应的value
         * @param key
         * @return
         */
        public V get(K key) {

        }

        /**
         * 返回第一个key
         * @return
         */
        public K firstKey() {

        }

        /**
         * 返回最后一个key
         * @return
         */
        public K lastKey() {

        }

        /**
         * 返回不大于指定key的key
         * @param key
         * @return
         */
        public K floorKey(K key) {

        }

        /**
         * 返回不小于指定key的key
         * @param key
         * @return
         */
        public K ceilingKey(K key) {

        }
    }
}
