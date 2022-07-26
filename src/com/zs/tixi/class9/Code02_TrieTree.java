package com.zs.tixi.class9;

import java.util.HashMap;
import java.util.Map;

/*
 * 1. 前缀树：
 *      1）单个字符串中，字符从前到后的加到一棵多叉树上。
 *      2）字符放在路上，节点上有专属的数据项（常见的是pass和end值）
 *      3）所有样本都这样添加，如果没有路就新建，有路就复用。
 *      4）沿途节点的pass值加1，每个字符串结束时来到的节点end值加1.
 *
 *      功能接口：
 *          添加字符串到前缀树
 *          删除1个字符串
 *          查询字符串在前缀树中有几个
 *          查询符合指定前缀的字符串个数
 */
public class Code02_TrieTree {

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.add("abc");
        trie.add("abcd");
        trie.remove("abcd");
        System.out.println();
    }
    /**
     * 前缀树的节点对象
     * pass属性：有多少个字符串经过该节点
     * end属性：有多少个字符串以该节点结尾
     * Map<Integer, Node> nexts: 当前节点向前的若干条路。
     *      key是字符对应的ascii码，value，是下一个节点
     */
    public static class Node{
        public int pass; // 路过当前节点的次数
        public int end; // 以当前节点结尾的次数
        public Map<Integer, Node> nexts;

        public Node(){
           nexts = new HashMap<>();
           pass = 0;
           end = 0;
        }
    }

    /**
     * 前缀树
     */
    public static class Trie{
        private Node root = new Node(); // 根节点

        /**
         * 添加字符串到前缀树
         *
         * 将字符串拆分为字符数组。
         * 新建currNode指向root节点。
         * root.pass++
         * 遍历整数数组：
         *      当前字符值为currChar。
         *      如果currChar不在currNode的map内，
         *          说明之前没有字符串走过
         *          在map中放入(currChar，new Node())
         *      通过map找到下一个节点，
         *      currNode指向下一个节点， currNode的pass+1，
         *  currNode的end+1
         */
        public void add(String word){
            // 字符串拆解为字符数组。
            // 当前节点curNode指向root节点，root节点的pass++
            // 循环：每到一个字符，尝试寻找这个字符的路。没路新建，有路复用。
            // curNode根据当前字符的路来到下一节点。pass++
            // 循环结束curNode来到了最后一个节点。end++
            if (word==null) return;
            char[] charArr = word.toCharArray();
            Node currNode = root;
            root.pass++; // 此处可以使用root的pass值来记录前缀树中总共有多少个字符串（换一种说法，总共有多少字符串是以""开头的）
            for (int i = 0; i < charArr.length; i++) {
                char currChar = charArr[i];
                if (currNode.nexts.containsKey(Integer.valueOf(currChar)) == false){
                    currNode.nexts.put(Integer.valueOf(currChar), new Node());
                }
                currNode = currNode.nexts.get(Integer.valueOf(currChar));
                currNode.pass++;
            }
            currNode.end++;
        }

        /**
         * 删除1个字符串
         *
         * 调用count方法判断前缀树中是否含有字符串。不含有直接返回。
         *
         * 将字符串转换为字符数组。
         * 创建遍历currNode，指向root。
         * 遍历字符数组：
         *      当前字符为currChar。
         *      currNode的pass-1
         *      根据currChar和map找到下一个节点nextNode。
         *      如果nextNode的pass==1，
         *          将currChar从map中移除
         *          直接return
         *      currNode指向下一个节点.
         * currNode.end-1
         */
        public void remove(String word){
            // 首先调用count函数判断前缀树中有没有当前字符串，没有直接返回。
            // 字符串拆解为字符数组。
            // 当前节点curNode指向root节点，root节点的pass--
            // 循环：curNode根据当前字符的路找到下一节点nextNode。nextNode.pass--
            // 如果nextNode.pass减后为0,移除curNode指向当前字符的路，直接返回。
            // 循环结束curNode来到了最后一个节点。end++
            if (count(word) == 0) return;
            char[] chars = word.toCharArray();
            Node currNode = root;
            currNode.pass--;
            for (int i = 0; i < chars.length; i++) {
                char currChar = chars[i];
                Node nextNode = currNode.nexts.get(Integer.valueOf(currChar));
                if (nextNode.pass==1){
                    currNode.nexts.remove(Integer.valueOf(currChar));
                    return;
                }
                nextNode.pass--;
                currNode = nextNode;
            }
            currNode.end--;
        }

        /**
         * 查询字符串在前缀树中有几个
         *
         * 如果word为空，直接返回0；
         * word转化为字符数组
         * 创建变量currNode
         * currNode指向root
         * 遍历字符数组：
         *      当前字符currChar。
         *      currNode的map中不包含currChar.?
         *          返回0；
         *      找到下一个节点nextNode
         *      currNode指向nextNode
         * 返回currNode的end值。
         *
         */
        public int count(String word){

            // 字符串拆解为字符数组。
            // 当前节点curNode指向root
            // 循环：curNode根据当前字符的路找到下一节点nextNode。
            // 如果nextNode为null,说明没有符合条件的字符串，直接返回。
            // 循环结束curNode来到了最后一个节点。返回curNode的end值
            if (word == null) return 0;

            char[] chars = word.toCharArray();
            Node currNode = root;
            for (int i = 0; i < chars.length; i++) {
                char currChar = chars[i];
                Node nextNode = currNode.nexts.get(Integer.valueOf(currChar));
                if (nextNode == null){
                    return 0;
                }
                currNode = nextNode;
            }
            return currNode.end;
        }

        /**
         * 查询符合指定前缀的字符串个数
         *
         * 如果prefix为空，返回0；
         *
         * prefix转化为字符数组
         * 创建变量currNode 指向root.
         * 遍历字符数组
         *      当前字符currChar
         *      currNode的map中不包含currChar?
         *          返回c0.
         *      找到下一节点nextNode
         *      currNode指向nextNode
         * 返回currNode.pass
         */
        public int countByPrefix(String prefix){
            if (prefix == null) return 0;

            // 字符串拆解为字符数组。
            // 当前节点curNode指向root
            // 循环：curNode根据当前字符的路找到下一节点nextNode。
            // 如果nextNode为null,说明没有符合条件的字符串，直接返回。
            // 循环结束curNode来到了最后一个节点。返回curNode的pass值
            char[] chars = prefix.toCharArray();
            Node currNode = root;
            for (int i = 0; i < chars.length; i++) {
                char currChar = chars[i];
                Node nextNode = currNode.nexts.get(Integer.valueOf(currChar));
                if (nextNode == null){
                    return 0;
                }

                currNode = nextNode;
            }

            return currNode.pass;
        }
    }
}
