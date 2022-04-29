package com.zs.tixi.class37;

public class SizeBalancedTreeMap2 {

    /**
     * k : key
     * v : value
     * l : 左子树
     * r : 右子树
     * size : 树节点个数，平衡因子
     */
    public static class SBTNode<K extends Comparable<K>, V>{
        public K k;
        public V v;
        public SBTNode<K, V> l;
        public SBTNode<K, V> r;
        public int size;
        public SBTNode(K key, V value){
            k = key;
            v = value;
            size = 1;
        }
    }

    public static class SizeBalancedTreeMap<K extends Comparable<K>, V>{
        private SBTNode<K, V> root;
        /**
         * 设置指定key的值
         */
        public void put(K key, V value){
            if(key==null) return;
            SBTNode<K,V> last = findLastIndex(key);
            if(last!=null && key.compareTo(last.k)==0){
                last.v = value;
            }else {
                root = add(root, key, value);
            }
        }

        /**
         * 查询指定key的值
         */
        public V get(K key){
            if(key==null) return null;
            SBTNode<K, V> last = findLastIndex(key);
            if(last!=null && key.compareTo(last.k)==0){
                return last.v;
            }
            return null;
        }

        /**
         * 删除指定key的节点
         */
        public void remove(K key){
            if(key==null) return;
            if(containsKey(key)){
                root = delete(root, key);
            }
        }

        /**
         * 返回有序表大小
         */
        public int size(){
            return root==null ? 0 : root.size;
        }

        /**
         * 是否包含指定key
         */
        public boolean containsKey(K key){
            if(key==null) return false;
            SBTNode<K, V> last = findLastIndex(key);
            if(last!=null && key.compareTo(last.k)==0){
                return true;
            }
            return false;
        }

        /**
         * 不大于指定key的最大key
         */
        public K floorKey(K key){
            if(key==null)return null;
            SBTNode<K, V> noBig = findLastNoBigIndex(key);
            return noBig==null?null:noBig.k;
        }

        /**
         * 不小于指定key的最小key
         */
        public K ceilingKey(K key){
            if(key==null) return null;
            SBTNode<K, V> noSmall = findLastNoSmallIndex(key);
            return noSmall==null?null:noSmall.k;
        }

        /**
         * 第一个key
         */
        public K firstKey(){
            if(root==null) return null;
            SBTNode<K, V> cur = root;
            while (cur.l!=null){
                cur = cur.l;
            }
            return cur.k;
        }

        /**
         * 最后一个key
         */
        public K lastKey(){
            if(root==null) return null;
            SBTNode<K, V> cur = root;
            while (cur.r != null){
                cur = cur.r;
            }
            return cur.k;
        }

        /**
         * 返回指定下标位置的key。(系统api不支持)
         */
        public K getIndexKey(int index){
            if(index<0 || index>getSize(root) -1 ) return null;
            return getIndex(root, index+1).k;
        }

        /**
         * 返回指定下标位置的value。(系统api不支持)
         */
        public V getIndexValue(int index){
            if(index<0 || index>getSize(root) -1 ) return null;
            return getIndex(root, index+1).v;
        }

        /**
         * 找到指定key对应的节点，如果没有，返回查找过程中的最后一个节点。
         */
        private SBTNode<K, V> findLastIndex(K key) {
            SBTNode<K,V> ans = null;
            SBTNode<K,V> cur = root;
            while (cur!=null){
                if(key.compareTo(cur.k)==0) return cur;
                ans = cur;
                if(key.compareTo(cur.k)<0){
                    cur = cur.l;
                }else {
                    cur = cur.r;
                }
            }
            return ans;
        }


        /**
         * 在指定子树上新增一个节点。
         */
        private SBTNode<K,V> add(SBTNode<K,V> node, K key, V value) {
            if(node==null)return new SBTNode<>(key, value);
            node.size++; // 由于子树上新增了一个节点，所有size必定加1，和height需要查询左右子树的情况不同。
            if(key.compareTo(node.k)<0){
                node.l = add(node.l, key, value);
            }else {
                node.r = add(node.r, key, value);
            }
            return maintain(node);
        }

        /**
         * 检查并调整当前树平衡性。
         */
        private SBTNode<K, V> maintain(SBTNode<K, V> node) {
            if(node==null) return null;
            int ls = getSize(node.l);
            int lls = 0;
            int lrs = 0;
            if(node.l!=null){
                lls = getSize(node.l.l);
                lrs = getSize(node.l.r);
            }
            int rs = getSize(node.r);
            int rrs = 0;
            int rls = 0;
            if(node.r!=null){
                rrs = getSize(node.r.r);
                rls = getSize(node.r.l);
            }
            if(lls>rs){
                node = rightRotate(node);
                node.r = maintain(node.r);
                node = maintain(node);
            }else if(lrs>rs){
                node.l = leftRotate(node.l);
                node = rightRotate(node);
                node.l = maintain(node.l);
                node.r = maintain(node.r);
                node = maintain(node);
            }else if(rrs>ls){
                node = leftRotate(node);
                node.l = maintain(node.l);
                node = maintain(node);
            }else if(rls>ls){
                node.r = rightRotate(node.r);
                node = leftRotate(node);
                node.l = maintain(node.l);
                node.r = maintain(node.r);
                node = maintain(node);
            }
            return node;
        }


        /**
         * 左旋
         */
        private SBTNode<K, V> leftRotate(SBTNode<K, V> node) {
            SBTNode<K, V> right = node.r;
            node.r = right.l;
            right.l = node;
            node.size = updateSize(node);
            right.size = updateSize(right);
            return right;
        }

        /**
         * 右旋
         */
        private SBTNode<K, V> rightRotate(SBTNode<K, V> node) {
            SBTNode<K, V> left = node.l;
            node.l = left.r;
            left.r = node;
            node.size = updateSize(node);
            left.size = updateSize(left);
            return left;
        }

        /**
         * 根据左右子树size返回当前节点的size
         */
        private int updateSize(SBTNode<K, V> node) {
            return getSize(node.l) + getSize(node.r) + 1;
        }

        /**
         * 返回节点的size
         */
        private int getSize(SBTNode<K, V> node) {
            return node==null?0:node.size;
        }

        /**
         * 在指定树上删除key对应的节点。
         */
        private SBTNode<K, V> delete(SBTNode<K, V> node, K key) {
            node.size--; // 注意：此处size--不可放在最后.因为删除后node可能已经被替换为子树了。这种情况下size--会发生错误。
            if(key.compareTo(node.k)<0){
                node.l = delete(node.l, key);
            }else if(key.compareTo(node.k)>0){
                node.r = delete(node.r, key);
            }else {
                if(node.l == null && node.r==null){
                    node = null;
                }else if(node.l==null){
                    node = node.r;
                }else if(node.r==null){
                    node = node.l;
                }else { // 核心，需要测试后梳理过程。
                    SBTNode<K, V> next = node.r;
                    while (next.l!=null){
                        next = next.l;
                    }
                    node.r = delete(node.r, next.k);
                    next.l = node.l;
                    next.r = node.r;
                    next.size = node.size;
                    node = next;
                }
            }

            return node; // 删除不需要调整平衡性。
        }

        /**
         * 不大于指定key的最大key对应的节点
         */
        private SBTNode<K, V> findLastNoBigIndex(K key) {
            SBTNode<K, V> ans = null;
            SBTNode<K, V> cur = root;
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
         * 不小于指定key的最小key对应的节点
         */
        private SBTNode<K, V> findLastNoSmallIndex(K key) {
            SBTNode<K, V> ans = null;
            SBTNode<K, V> cur = root;
            while (cur!=null){
                if(key.compareTo(cur.k)==0) return cur;
                if(key.compareTo(cur.k)<0){
                    ans = cur;
                    cur = cur.l;
                }else {
                    cur = cur.r;
                }
            }
            return ans;
        }

        /**
         * 返回node树上第kth个元素
         */
        private SBTNode<K, V> getIndex(SBTNode<K,V> node, int kth) {
            if(getSize(node.l)+1 == kth){
                return node;
            }else if(getSize(node.l) >= kth){
                return getIndex(node.l, kth);
            }else{
                return getIndex(node.r, kth - getSize(node.l) - 1);
            }
        }
    }
}
