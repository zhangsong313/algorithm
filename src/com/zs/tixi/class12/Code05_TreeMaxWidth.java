package com.zs.tixi.class12;

import java.util.*;

/*
 * 5. 求二叉树最宽的层有多少个节点。
 * 核心问题：二叉树按层遍历，如何对层做区分。
 */
public class Code05_TreeMaxWidth {
    public static class Node{
        public int value;
        public Node left;
        public Node right;
        public Node(int value){
            this.value = value;
        }
    }

    /**
     * 使用map辅助解决。额外空间复杂度O（N）
     * 在按层遍历时，把当前层的每个子节点的所在层都存起来。
     * 如果head为空，返回0.
     * 创建一个空队列queue。初始化将head添加进去
     * 定义变量maxWidth=0，表示返回的最大宽度。
     * 定义变量currWidth=0，表示当前层宽度。
     * 定义变量level=1,表示层数。
     * 创建一个空map，用来保存每个节点所在层。初始化map.put(head, 1).
     * 遍历queue
     *      从queue中取出一个节点curr。
     *      从map中取出当前节点所在层currLvl。
     *      如果curr的左节点不为空
     *          将左节点放入queue。
     *          将左节点放入map，层数为currLvl加1
     *      如果curr的右节点不为空
     *          将右节点放入queue
     *          将右节点放入map，层数为currLvl加1
     *      如果currLvl不等于level,说明来到了下一层。
     *          maxWidth 和currWidth比较后更新。
     *          currWidth 清零
     *          level等于currLvl
     *      currWidth加1
     * 最后一行的宽度和maxWidth比较后更新。
     * 返回maxWidth
     * @param head
     * @return
     */
    public static int maxWidthUseMap(Node head) {
        if (head==null) return 0;
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        int maxWidth = 0;
        int currWidth = 0;
        int level = 1;
        Map<Node, Integer> map = new HashMap<>();
        map.put(head, 1);
        while (queue.size()>0){
            Node curr = queue.poll();
            Integer currLvl = map.get(curr);
            if (curr.left!=null){
                queue.add(curr.left);
                map.put(curr.left, currLvl+1);
            }
            if (curr.right!=null){
                queue.add(curr.right);
                map.put(curr.right, currLvl+1);
            }
            if (currLvl!=level){
                maxWidth = Math.max(maxWidth, currWidth);
                currWidth = 0;
                level++;
            }
            currWidth++;
        }
        maxWidth = Math.max(maxWidth,currWidth);
        return maxWidth;
    }

    /**
     * 在按层遍历时，每次记录下一层的最右节点是哪一个。
     * 额外空间复杂度O(1)
     * 创建空队列queue,初始化将head加进去。
     * 定义变量endNode=head。表示当前层的最右节点。
     * 定义变量nextEndNode=null。表示下一层的最右节点。
     * 定义变量currWidth=0，表示当前层宽度。
     * 定义变量maxWidth=0，表示最大宽度。
     * 遍历queue：
     *      从queue中取出节点curr。
     *      如果curr的左节点不为空
     *          左节点加入queue。
     *          nextEndNode更新为左节点。
     *      如果curr的右节点不为空
     *          右节点加入queue。
     *          nextEndNode更新为右节点。
     *      currWidth加1.
     *      如果curr==endNode。表示当前层结束
     *          maxWidth与currWidth比较后更新.
     *          currWidth重置为0.
     *          endNode更新为nextEndNode.
     *          nextEndNode更新为null
     * 返回maxWidth
     * @param head
     * @return
     */
    public static int maxWidthNoMap(Node head){
        if(head==null) return 0;
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        Node endNode = head;
        Node nextEndNode = null;
        int currWidth = 0;
        int maxWidth = 0;
        while (queue.size()>0){
            Node curr = queue.poll();
            if (curr.left!=null){
                queue.add(curr.left);
                nextEndNode = curr.left;
            }
            if(curr.right!=null){
                queue.add(curr.right);
                nextEndNode = curr.right;
            }
            currWidth++;
            if (curr == endNode){
                maxWidth = Math.max(maxWidth, currWidth);
                currWidth=0;
                endNode = nextEndNode;
                nextEndNode=null;
            }
        }
        return maxWidth;
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            int ans1 = maxWidthUseMap(head);
            int ans2 = maxWidthNoMap(head);

            if ( ans1!= ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");

    }
}
