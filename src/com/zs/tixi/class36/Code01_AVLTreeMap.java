package com.zs.tixi.class36;

public class Code01_AVLTreeMap {
    /**
     * avlTree节点
     * k：节点key值
     * v：节点value值
     * l：左子树
     * r：右子树
     * h：节点高度。（平衡因子：avl要求每个树的左右两树高度差不超过1）
     * @param <K> 有序表的key类型
     * @param <V> 有序表的value类型
     */
    public static class AVLNode<K extends Comparable<K>, V>{
        public K k;
        public V v;
        public int h;
        public AVLNode<K, V> l;
        public AVLNode<K, V> r;
        public AVLNode(K key , V value){
            k = key;
            v = value;
            h = 1;
        }
    }

    /**
     * root。AVL树的根节点。
     * size： 节点数量。
     */
    public static class AVLTreeMap<K extends Comparable<K>, V>{
        private AVLNode<K, V> root;
        private int size;

        /**
         * root初始化为null。
         * 节点数量初始化为0.
         * 此时整棵树是空树。
         */
        public AVLTreeMap(){
        }

        /**
         * 右旋调整
         * 参数cur：对哪个节点进行右旋。
         * 返回值：返回右旋后树的头。
         * 		定义变量left赋值为cur的左子树。
         * 		cur的左子树更新为left的右子树。
         * 		left的右子树更新为cur
         * 	更新cur的高度值。
         * 	更新left的高度值。
         * 	返回left
         */
        private AVLNode<K, V> rightRotate(AVLNode<K, V> cur){
            AVLNode<K, V> left = cur.l;
            cur.l = left.r;
            left.r = cur;
            cur.h = updateHeight(cur);
            left.h = updateHeight(left);
            return left;
        }

        /**
         * 左旋
         */
        private AVLNode<K, V> leftRotate(AVLNode<K, V> cur){
            AVLNode<K, V> right = cur.r;
            cur.r = right.l;
            right.l = cur;
            cur.h = updateHeight(cur);
            right.h = updateHeight(right);
            return right;
        }

        /**
         * 更新当前树的高度
         */
        private int updateHeight(AVLNode<K, V> cur) {
            return Math.max(getHeight(cur.l), getHeight(cur.r))+1;
        }

        /**
         * 返回当前树的高度。
         */
        private int getHeight(AVLNode<K, V> cur) {
            return cur==null ? 0 : cur.h;
        }

        /**
         * 检查当前节点是否平衡并调整。
         * 参数cur：检查并调整的节点。
         * 返回值：调整后树的头节点。
         *		空节点直接返回空。
         *		获取左树高度leftHeight。
         *		获取右树高度rightHeight。
         *		如果左右两树高度差超过1.
         *			如果左树高度大于右树高度。
         *				获取左树的左子树高度llHeight和右子树的高度lrHeight。
         *				如果左树高度大于等于右树高度，按照LL型处理
         *					cur右旋后返回新的头节点。
         *				否则按照LR型处理。
         *					cur.l左旋
         *					cur右旋
         *			如果右树高度大于左树高度。
         *				反之。
         *		返回新的cur。
         *
         */
        private AVLNode<K, V> maintain(AVLNode<K, V> cur){
            if (cur == null) return null;
            int leftHeight = getHeight(cur.l);
            int rightHeight = getHeight(cur.r);
            if(Math.abs(leftHeight-rightHeight)>1){
                if(leftHeight>rightHeight){
                    int llHeight = getHeight(cur.l.l);
                    int lrHeight = getHeight(cur.l.r);
                    if(llHeight>=lrHeight){
                        cur = rightRotate(cur);
                    } else {
                        cur.l = leftRotate(cur.l);
                        cur = rightRotate(cur);
                    }
                }else {
                    int rrHeight = getHeight(cur.r.r);
                    int rlHeight = getHeight(cur.r.l);
                    if(rrHeight>rlHeight){
                        cur = leftRotate(cur);
                    }else {
                        cur.r = rightRotate(cur.r);
                        cur = leftRotate(cur);
                    }
                }
            }
            return cur;
        }

        /**
         * 返回最接近指定key的节点。
         * 定义变量pre初始化为空。
         * 定义变量cur初始化为root。
         * 循环：cur不为空
         * 		如果key等于cur.k
         * 			返回cur
         * 		pre来到cur
         * 		如果key小于cur.k
         * 			cur来到左树
         * 		否则
         * 			cur来到右树
         * 返回pre
         * @param key
         * @return
         */
        private AVLNode<K, V> findLastIndex(K key){
            AVLNode<K, V> pre = null;
            AVLNode<K, V> cur = root;
            while (cur!=null){
                if(key.compareTo(cur.k)==0) return cur;
                pre = cur;
                if(key.compareTo(cur.k)<0){
                    cur = cur.l;
                }else {
                    cur = cur.r;
                }
            }
            return pre;
        }

        /**
         * 返回不小于key的最小节点。
         * 只有向左划时记录答案。（左划时cur大于key）
         */
        private AVLNode<K, V> findLastNoSmallIndex(K key){
            AVLNode<K, V> ans = null;
            AVLNode<K, V> cur = root;
            while (cur != null){
                if(key.compareTo(cur.k)==0) return cur;
                if(key.compareTo(cur.k)<0) {
                    ans = cur;
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }
            return ans;
        }

        /**
         * 返回不大于key的最大节点。
         * 右划时记录答案。
         * @param key
         * @return
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
         * 给指定树添加一个节点。返回添加后的头节点（可能涉及平衡性调整）。
         * 如果为空树，直接构建节点返回
         * 否则：
         * 		如果key小于cur.k
         * 			递归调用左子树后返回。
         * 		否则
         * 			递归调用右子树后返回。
         * 		当前节点高度调整。
         * 		调整cur后返回
         * @param cur
         * @param key
         * @param value
         * @return
         */
        private AVLNode<K, V> add(AVLNode<K, V> cur, K key, V value){
            if(cur==null) return new AVLNode<>(key, value);
            if(key.compareTo(cur.k)<0){
                cur.l = add(cur.l, key, value);
            }else {
                cur.r = add(cur.r, key, value);
            }
            cur.h = updateHeight(cur);
            return maintain(cur);
        }

        /**
         * 删除cur树上key对应的节点。
         * 如果key大于cur.k
         * 		递归调用右树删除
         * 如果key小于cur.k
         * 		递归调用左树删除
         * 否则
         * 		如果左右树都为空。
         * 			cur更新为空
         * 		如果左空右有
         * 			cur更新为右树
         * 		如果右空左有
         * 			cur更新为左树
         * 		否则（左右都有）
         * 			定义变量des，更新cur右树的最左节点。
         * 			在cur的右树上删除des。
         * 			des的左右子树更新为cur的左右子树。
         * 			cur更新为des。
         * 更新cur的高度
         * 调整cur平衡后返回。
         * @param cur
         * @param key
         * @return
         */
        private AVLNode<K, V> delete(AVLNode<K, V> cur, K key){
            if(key.compareTo(cur.k)<0){
                cur.l = delete(cur.l, key);
            } else if (key.compareTo(cur.k)>0) {
                cur.r = delete(cur.r, key);
            } else {
                if(cur.l==null && cur.r==null){
                    cur = null;
                }else if(cur.l==null && cur.r!=null){
                    cur = cur.r;
                }else if(cur.l!=null && cur.r==null){
                    cur = cur.l;
                }else {
                    AVLNode<K, V> next = cur.r;
                    while (next.l!=null){
                        next = next.l;
                    }
                    cur.r = delete(cur.r, next.k);
                    next.l = cur.l;
                    next.r = cur.r;
                    cur = next;
                }
            }
            if(cur!=null){
                cur.h = updateHeight(cur);
            }
            return maintain(cur);
        }

        /**
         * 返回有序表大小
         */
        public int size() {
            return size;
        }

        /**
         * 有序表是否包含指定key
         * 找到最接近key的节点
         * 如果节点key等于指定key，返回true，否则返回false。
         * @param key
         * @return
         */
        public boolean containsKey(K key){
            AVLNode<K, V> lastIndex = findLastIndex(key);
            if(lastIndex!=null && key.compareTo(lastIndex.k)==0){
                return true;
            }
            return false;
        }

        /**
         * 有序表放入key->value
         * 找到最接近key的节点。
         * 如果对应节点的值等于key。
         * 		更新对应节点的value。
         * 否则
         * 		size加1
         * 		从root根节点添加一个新节点。
         * @param key
         * @param value
         */
        public void put(K key, V value){
            AVLNode<K, V> lastIndex = findLastIndex(key);
            if(lastIndex!=null && key.compareTo(lastIndex.k)==0){
                lastIndex.v = value;
            }else {
                size++;
                root = add(root, key, value);
            }
        }
        /**
         * 删除对应key的节点
         * 如果包含该key
         * 		size减一
         * 		root执行delete方法。
         * @param key
         */
        public void remove(K key){
            if(key==null) return;
            if(containsKey(key)){
                size--;
                root = delete(root, key);
            }
        }

        /**
         * 查询key对应的值。
         * 找到最接近key的节点。
         * 		如果节点值等于key
         * 		返回对应的value
         * 返回空
         * @param key
         * @return
         */
        public V get(K key){
            if(key==null) return null;
            AVLNode<K, V> lastIndex = findLastIndex(key);
            if(lastIndex!=null && key.compareTo(lastIndex.k)==0){
                return lastIndex.v;
            }
            return null;
        }

        /**
         * 返回最小key
         * 一致向左划，返回最后节点的key
         * @return
         */
        public K firstKey(){
            if(root==null) return null;
            AVLNode<K, V> cur = root;
            while (cur.l!=null){
                cur = cur.l;
            }
            return cur.k;
        }

        /**
         * 返回最大key
         * 一直向右划，返回最后节点key
         * @return
         */
        public K lastKey(){
            if(root == null) return null;
            AVLNode<K,V> cur = root;
            while (cur.r!=null){
                cur = cur.r;
            }
            return cur.k;
        }

        /**
         * 返回不超过key的k值。
         * @param key
         * @return
         */
        public K floorKey(K key){
            if(key==null) return null;
            AVLNode<K, V> lastNoBigIndex = findLastNoBigIndex(key);
            return lastNoBigIndex==null? null : lastNoBigIndex.k;
        }
        /**
         * 返回不小于key的k值。
         * @param key
         * @return
         */
        public K ceilingKey(K key){
            if(key == null) return null;
            AVLNode<K, V> lastNoSmallIndex = findLastNoSmallIndex(key);
            return lastNoSmallIndex==null? null : lastNoSmallIndex.k;
        }
    }
}
