package com.zs.tixi.class38;

import java.util.ArrayList;

/*
 * 设计一个结构包含如下两个方法：
 * void add(int index, int num)：把num加入到index位置
 * int get(int index) ：取出index位置的值
 * void remove(int index) ：把index位置上的值删除
 * 要求三个方法时间复杂度O(logN)
 *
 * 思路：改写有序表。
 * 由于节点位置不是根据值确定，而是用户指定的序号确定。无需key，只有value即可。
 * add(index, num):
 *      添加时用index和size判断需要添加到什么位置。
 * get(index):
 *      用size判断找到和index匹配的节点。
 * remove(index):
 *      用size判断需要删除的节点。
 */
public class Code03_AddRemoveGetIndexGreat {
    public static class SBTNode<V>{
        public V val;
        public SBTNode l;
        public SBTNode r;
        public int size;

        public SBTNode(V v){
            val = v;
            size = 1;
        }
    }
    public static class SbtList<V>{
        private SBTNode<V> root;

        public int size(){
            return root==null?0:root.size;
        }

        /**
         * 在index位置新增num。
         */
        public void add(int index, V num){
            if(index<0 || index>size()) throw new RuntimeException("add : out of range");
            root = add(root, index+1, num);
        }

        /**
         * 在指定树上的指定index位置加入num。
         */
        public SBTNode<V> add(SBTNode<V> cur , int index, V num){
            if(cur==null) return new SBTNode<>(num);
            cur.size++;
            if(index<=getSize(cur.l)+1){
                cur.l = add(cur.l, index, num);
            }else {
                cur.r = add(cur.r, index-getSize(cur.l)-1, num);
            }
            return maintain(cur);
        }

        /**
         * 检查并调整平衡性。
         */
        private SBTNode<V> maintain(SBTNode<V> cur){
            int lSize = getSize(cur.l);
            int llSize = 0;
            int lrSize = 0;
            if(cur.l!=null){
                llSize = getSize(cur.l.l);
                lrSize = getSize(cur.l.r);
            }
            int rSize = getSize(cur.r);
            int rrSize = 0;
            int rlSize = 0;
            if(cur.r!=null){
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
         * 右旋
         * @param cur
         * @return
         */
        private SBTNode<V> rightRotate(SBTNode<V> cur){
            SBTNode left = cur.l;
            cur.l = left.r;
            left.r = cur;
            left.size = cur.size;
            cur.size = getSize(cur.l)+getSize(cur.r)+1;
            return left;
        }

        /**
         * 左旋
         * @param cur
         * @return
         */
        private SBTNode<V> leftRotate(SBTNode<V> cur){
            SBTNode right = cur.r;
            cur.r = right.l;
            right.l = cur;
            right.size = cur.size;
            cur.size = getSize(cur.l)+getSize(cur.r)+1;
            return right;
        }

        private int getSize(SBTNode<V> cur){
            return cur==null?0:cur.size;
        }

        /**
         * 返回index位置的值。
         */
        public V get(int index){
            if(index<0 || index>size()-1) throw new RuntimeException("get : out of range");

            return getIndex(root, index+1).val;
        }

        /**
         * 返回指定位置的节点
         */
        public SBTNode<V> getIndex(SBTNode<V> cur, int index){
            if(index==getSize(cur.l)+1) return cur;
            if(index<=getSize(cur.l)){
                return getIndex(cur.l, index);
            }else {
                return getIndex(cur.r, index - getSize(cur.l) -1);
            }
        }

        /**
         * 删除index位置的值
         */
        public void remove(int index){
            if(index<0 || index>size()-1) throw new RuntimeException("remove : out of range");
            root = delete(root, index+1);
        }

        /**
         * 在当前树上删除指定位置的节点。
         */
        private SBTNode<V> delete(SBTNode<V> cur, int index){
            cur.size--;
            if(index<=getSize(cur.l)){
                cur.l = delete(cur.l, index);
            }else if(index>getSize(cur.l)+1){
                cur.r = delete(cur.r, index - getSize(cur.l)-1);
            }else {
                if(cur.l==null&&cur.r==null) return null;
                if(cur.l!=null&&cur.r==null) return cur.l;
                if(cur.l==null&&cur.r!=null) return cur.r;
                SBTNode<V> next = cur.r;
                SBTNode<V> pre = null;
                while (next.l!=null){
                    next.size--;
                    pre = next;
                    next = next.l;
                }
                if(pre!=null){
                    pre.l = next.r;
                    next.r = cur.r;
                }
                next.l = cur.l;
                next.size = cur.size;
                cur = next;
            }
            return cur;
        }
    }


    // 通过以下这个测试，
    // 可以很明显的看到LinkedList的插入、删除、get效率不如SbtList
    // LinkedList需要找到index所在的位置之后才能插入或者读取，时间复杂度O(N)
    // SbtList是平衡搜索二叉树，所以插入或者读取时间复杂度都是O(logN)
    public static void main(String[] args) {

//        SbtList<Integer> testList = new SbtList<>();
//        for (int i = 0; i < 100; i++) {
//            testList.add(i,i);
//        }
//        for (int i = 20; i < 30; i++) {
//
//        }
//        for (int i = 0; i < testList.size(); i++) {
//            System.out.println(testList.get(i));
//        }

        // 功能测试
        int test = 50000;
        int max = 1000000;
        boolean pass = true;
        ArrayList<Integer> list = new ArrayList<>();
        SbtList<Integer> sbtList = new SbtList<>();
        for (int i = 0; i < test; i++) {
            if (list.size() != sbtList.size()) {
                pass = false;
                break;
            }
            if (list.size() > 1 && Math.random() < 0.5) {
                int removeIndex = (int) (Math.random() * list.size());
                list.remove(removeIndex);
                sbtList.remove(removeIndex);
            } else {
                int randomIndex = (int) (Math.random() * (list.size() + 1));
                int randomValue = (int) (Math.random() * (max + 1));
                list.add(randomIndex, randomValue);
                sbtList.add(randomIndex, randomValue);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals(sbtList.get(i))) {
                pass = false;
                break;
            }
        }
        System.out.println("功能测试是否通过 : " + pass);

        // 性能测试
        test = 500000;
        list = new ArrayList<>();
        sbtList = new SbtList<>();
        long start = 0;
        long end = 0;

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (list.size() + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            list.add(randomIndex, randomValue);
        }
        end = System.currentTimeMillis();
        System.out.println("ArrayList插入总时长(毫秒) ： " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            list.get(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("ArrayList读取总时长(毫秒) : " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * list.size());
            list.remove(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("ArrayList删除总时长(毫秒) : " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (sbtList.size() + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            sbtList.add(randomIndex, randomValue);
        }
        end = System.currentTimeMillis();
        System.out.println("SbtList插入总时长(毫秒) : " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            sbtList.get(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("SbtList读取总时长(毫秒) :  " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * sbtList.size());
            sbtList.remove(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("SbtList删除总时长(毫秒) :  " + (end - start));

    }

}
