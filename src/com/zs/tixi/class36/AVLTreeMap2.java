package com.zs.tixi.class36;

public class AVLTreeMap2 {
    /**
     * k : key
     * v ： value
     * l ：左子树
     * r : 右子树
     * h : 高度（平衡因子）
     *
     */
    public static class AVLNode<K extends Comparable<K>, V>{
        public K k;
        public V v;
        public AVLNode<K, V> l;
        public AVLNode<K, V> r;
        public int h;

        public AVLNode(K key, V value){
            k = key;
            v = value;
            h = 1;
        }
    }

    public static class AVLTreeMap<K extends Comparable<K>, V>{
        private AVLNode<K, V> root;
        private int size;

        /**
         * 设置指定key的值
         */
        public void put(K key, V value){
            if(key == null) return;
            AVLNode<K, V> last = findLastIndex(key);
            if(last != null && last.k.compareTo(key) == 0){
                last.v = value;
            }else{
                size++;
                root = add(root, key, value);
            }
        }

        /**
         * 查询指定key的值
         */
        public V get(K key){
            if(key == null) return null;
            AVLNode<K, V> last = findLastIndex(key);
            if(last != null && last.k.compareTo(key) == 0){
                return last.v;
            }
            return null;
        }

        /**
         * 删除指定key的值
         */
        public void remove(K key){
            if(key == null) return;
            if(containsKey(key)){
                size--;
                root = delete(root, key);
            }
        }

        /**
         * 判断是否包含指定key
         */
        public boolean containsKey(K key){
            if(key==null) return false;
            AVLNode<K, V> last = findLastIndex(key);
            if(last != null && last.k.compareTo(key)==0){
                return true;
            }
            return false;
        }

        /**
         * 返回集合大小
         */
        public int size(){
            return size;
        }

        /**
         * 不大于当前key的最大key
         */
        public K floorKey(K key){
            AVLNode<K, V> noBig = findLastNoBigIndex(key);
            return noBig!=null ? noBig.k : null;
        }

        /**
         * 不小于当前key的最小key
         */
        public K ceilingKey(K key){
            AVLNode<K, V> noSmall = findLastNoSmallIndex(key);
            return noSmall==null ? null : noSmall.k;
        }

        /**
         * 第一个key
         */
        public K firstKey(){
            if(root == null) return null;
            AVLNode<K, V> cur = root;
            while (cur.l != null){
                cur = cur.l;
            }
            return cur.k;
        }

        /**
         * 最后一个key
         */
        public K lastKey(){
            if(root == null) return null;
            AVLNode<K, V> cur = root;
            while (cur.r != null){
                cur = cur.r;
            }
            return cur.k;
        }

        public boolean isBalanced(){
            return isBalance(root);
        }

        /**
         * 返回查询过程中等于key或者最后一个节点的key
         */
        private AVLNode<K,V> findLastIndex(K key) {
            AVLNode<K, V> ans = null;
            AVLNode<K, V> cur = root;
            while (cur != null){
                if(key.compareTo(cur.k) == 0) return cur;
                ans = cur;
                if(key.compareTo(cur.k) < 0){
                    cur = cur.l;
                }else {
                    cur = cur.r;
                }
            }
            return ans;
        }

        /**
         * 在指定子树上添加一个新节点
         */
        private AVLNode<K, V> add(AVLNode<K, V> node, K key, V value) {
            if(node == null) return new AVLNode<>(key, value);
            if(key.compareTo(node.k)<0){
                node.l = add(node.l, key, value);
            }else {
                node.r = add(node.r, key, value);
            }
            node.h = updateHeight(node); // 需要手动更新高度.
            return maintain(node);
        }

        /**
         * 检查节点平衡性并调整
         */
        private AVLNode<K, V> maintain(AVLNode<K, V> node) {
            if(node==null) return null;
            int lH = getHeight(node.l);
            int rH = getHeight(node.r);
            if(Math.abs(lH-rH)>1){
                if(lH > rH){
                    int llH = getHeight(node.l.l);
                    int lrH = getHeight(node.l.r);
                    if(llH >= lrH){
                        node = rightRotate(node);
                    }else {
                        node.l = leftRotate(node.l);
                        node = rightRotate(node);
                    }
                }else{
                    int rrH = getHeight(node.r.r);
                    int rlH = getHeight(node.r.l);
                    if(rrH >= rlH){
                        node = leftRotate(node);
                    }else{
                        node.r = rightRotate(node.r);
                        node = leftRotate(node);
                    }
                }
            }
            return node;
        }

        /**
         * 左旋调整
         */
        private AVLNode<K, V> leftRotate(AVLNode<K, V> node) {
            AVLNode<K, V> right = node.r;
            node.r = right.l;
            right.l = node;
            node.h = updateHeight(node);
            right.h = updateHeight(right);
            return right;
        }

        /**
         * 右旋调整
         */
        private AVLNode<K, V> rightRotate(AVLNode<K, V> node) {
            AVLNode<K, V> left = node.l;
            node.l = left.r;
            left.r = node;
            node.h = updateHeight(node);
            left.h = updateHeight(left);
            return left;
        }

        /**
         * 根据子树高度，更新当前高度
         */
        private int updateHeight(AVLNode<K, V> node) {
            return Math.max(getHeight(node.l), getHeight(node.r))+1;
        }

        /**
         * 返回子树高度
         */
        private int getHeight(AVLNode<K, V> node) {
            return node==null ? 0 : node.h;
        }


        /**
         * 在指定子树上删除指定key对应的节点
         */
        private AVLNode<K, V> delete(AVLNode<K, V> node, K key) {
            if(key.compareTo(node.k)<0){
                node.l = delete(node.l, key);
            }else if(key.compareTo(node.k)>0) {
                node.r = delete(node.r, key);
            }else {
                if(node.l==null && node.r==null){
                    return null;
                }else if(node.l==null){
                    node = node.r;
                }else if(node.r==null){
                    node = node.l;
                }else {
                    // 核心
                    AVLNode<K, V> next = node.r;
                    while (next.l != null){
                        next = next.l;
                    }
                    node.r = delete(node.r, next.k);
                    next.l = node.l;
                    next.r = node.r;
                    node = next;
                }
            }
            node.h = updateHeight(node); // 需要更新高度。
            return maintain(node);
        }


        /**
         * 返回不大于key的最大key对应的节点
         */
        private AVLNode<K, V> findLastNoBigIndex(K key) {
            AVLNode<K, V> ans = null;
            AVLNode<K, V> cur = root;
            while (cur!=null){
                if(key.compareTo(cur.k)==0) return cur;
                if(key.compareTo(cur.k)<0){
                    cur = cur.l;
                }else {
                    ans = cur;
                    cur = cur.r;
                }
            }
            return ans;
        }


        /**
         * 返回不小于key的最小key对应的节点
         */
        private AVLNode<K, V> findLastNoSmallIndex(K key) {
            AVLNode<K, V> ans = null;
            AVLNode<K, V> cur = root;
            while (cur != null){
                if(key.compareTo(cur.k) == 0) return cur;
                if(key.compareTo(cur.k) < 0){
                    ans = cur;
                    cur = cur.l;
                }else {
                    cur = cur.r;
                }
            }
            return ans;
        }

        private boolean isBalance(AVLNode<K, V> node){
            if(node==null) return true;
            int lH = getHeight(node.l);
            int rH = getHeight(node.r);
            return Math.abs(lH-rH)<=1 && isBalance(node.l) && isBalance(node.r);
        }
    }
}
