package com.zs.tixi.class33;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/*
 * AC自动机
 *      解决在一个大字符串中,找到多个候选字符串的问题.
 *      (大文章内找敏感词.)
 *
 * AC自动机算法核心:
 *      1)把所有匹配串生成一颗前缀树
 *      2)前缀树节点增加fail指针
 *      3)fail指针的含义:如果必须以当前字符结尾.当前形成的路径是str,剩下的哪一个字符串的前缀和str的后缀,
 *      拥有最大的匹配长度.fail指针就指向哪个字符串的最后一个字符所对应的节点.
 */
public class Code03_AC1 {

    /**
     * 敏感词字典的前缀树结构
     *
     * end: 当前节点如果是结束节点,记录敏感词.
     * endUse: 当前节点是结束节点的前提下.是否已经查找过了.避免重复查找.
     * fail: 当前节点匹配失败,接下来去哪个节点接着匹配.
     * nexts: 当前节点的后续节点.
     */
    public static class Node{
        public String end;
        public boolean endUse;
        public Node fail;
        public Node[] nexts;
        public Node(){
            end = null;
            fail = null;
            endUse = false;
            nexts = new Node[26];
        }
    }

    /**
     * 属性:
     *      1. root:敏感字前缀树的根节点.
     */
    public static class ACAutomation{
        private Node root;

        /**
         * strArr: 需要初始化的敏感词
         *
         * 先将字符串数组组成前缀树.
         * 变量root初始化一个新节点.
         * 遍历strArr:s
         *      定义变量cur为root.
         *      遍历s的字符数组:c
         *          定义next变量通过下标换算得到当前字符的nexts下标:c-'a'
         *          如果没有当前路径:cur.nexts[next]==null
         *              创建改路径的后续节点.
         *          cur来到下一节点.
         *      cur.end设置为s.
         *
         * 按层遍历前缀树,设置fail指针.
         * 定义queue作为按层遍历的队列.
         * root加入队列.
         * 循环:队列不为空
         *      取出队列头部节点poll.
         *      定义变量cur指向poll.
         *      循环i:0..25
         *          如果i路径有后续节点:
         *              cur的i路径节点的fail指针暂时设置为root.
         *              定义变量cf指向cur.fail
         *              循环:cf不为空
         *                  如果cf.的i路径有后续节点.（父节点的fail指针找到的位置有i路径）
         *                      设置cur的i路径节点的fail指针为cf的i路径节点.
         *                      退出循环.
         *                  cf来到cf的fail指针位置
         */
        public ACAutomation(String[] strArr){
            root = new Node();
            for (String s : strArr) {
                char[] chars = s.toCharArray();
                Node cur = root;
                for (char c : chars) {
                    int next = c - 'a';
                    if(cur.nexts[next] == null){
                        cur.nexts[next] = new Node();
                    }
                    cur = cur.nexts[next];
                }
                cur.end = s;
            }

            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()){
                Node poll = queue.poll();
                for (int i = 0; i < 26; i++) {
                    if(poll.nexts[i]!=null){
                        poll.nexts[i].fail = root;
                        Node cfail = poll.fail;
                        while (cfail!=null){
                            if(cfail.nexts[i]!=null){
                                poll.nexts[i].fail = cfail.nexts[i];
                                break;
                            }
                            cfail = cfail.fail;
                        }
                        queue.add(poll.nexts[i]);
                    }
                }
            }
        }

        /**
         * 从大文本content中查找敏感词.
         * content的字符数组定义变量chars
         *
         * 定义cur表示当前到达的节点,初始化为root.
         * 定义i表示当前所在字符数组下标位置,初始化为0.
         * 定义follow表示匹配成功后顺着fail指针环绕一周,查看是否有end标记的敏感词.
         * 定义ans来收集大文本中出现的敏感词.
         *
         * 循环:i<chars.length
         *      将当前下标对应的字符范围为路.road.
         *      如果cur节点road路径上没有节点 且 cur不是root
         *          cur跳到cur.fail
         *      如果cur的road路径上有节点
         *          cur设置为road路径的后续节点.
         *      follow来到cur位置.
         *      循环:follow不是root(follow沿着cur的fail指针扫描一圈,直到root节点)
         *          如果follow.endUse为true,表示之前已经收集过该节点
         *              break
         *          如果follow.end不为空
         *              ans收集follow.end的值
         *              follow.endUse标记为true
         *          follow来到follow.fail位置.
         *
         */
        public List<String> containWords(String content) {
            char[] str = content.toCharArray();
            Node cur = root;
            Node follow = null;
            int index = 0;
            List<String> ans = new ArrayList<>();
            for (int i = 0; i < str.length; i++) {
                index = str[i]-'a';
                // 没匹配成功，且没到root。cur沿着fail找到下一敏感词。
                // 直到有匹配的敏感词,或到root节点
                while (cur.nexts[index]==null && cur!=root){
                    cur = cur.fail;
                }
                // 如果找到匹配的就来到对应节点.否则就来到root.
                cur = cur.nexts[index] != null ? cur.nexts[index] : root;
                // follow顺着fail环绕一周,看是否有已经结束的敏感词.直到root.
                follow = cur;
                while (follow != root){
                    if(follow.endUse){ // 已经统计过的敏感词不再统计.
                        break;
                    }
                    if(follow.end != null){
                        ans.add(follow.end);
                        follow.endUse = true;
                    }
                    follow = follow.fail;
                }
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        String [] words = new String[]{
                "dhe", "he", "abcdheks", "xxxts"
        };
        ACAutomation ac = new ACAutomation(words);

        List<String> contains = ac.containWords("abcdhekskdjfafhasldkflskdjhwqaeruvxxxtss");
        for (String word : contains) {
            System.out.println(word);
        }
    }
}
