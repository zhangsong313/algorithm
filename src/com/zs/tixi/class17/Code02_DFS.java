package com.zs.tixi.class17;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/*
 *      深度优先遍历：
 *          1.利用栈实现。
 *          2.从源节点开始把节点放入栈，然后弹出。
 *          3.每弹出一个节点，把该节点下一个没有进入过栈的临界点放入栈。
 *          4.直到栈变空。
 *
 *       注意:需要一个set来判断当前节点是否被访问过,否则可能会重复访问.
 */
public class Code02_DFS {
    /**
     * 从start节点开始深度遍历图
     * 创建栈stack.
     * 创建set用来保存访问过的节点
     * 打印start
     * 把start压栈
     * 把start加入set.
     * 遍历stack;
     *      从stack中取出一个元素curr
     *      遍历curr的临接点:next
     *          如果next不在set中:
     *              打印next
     *              将curr压栈
     *              将next压栈.
     *              将next加入set.
     *              break.
     * @param start
     */
    public static void dfs(Node start) {
        Stack<Node> stack = new Stack<>();
        Set<Node> set = new HashSet<>();
        System.out.println(start);
        stack.push(start);
        set.add(start);
        while (!stack.isEmpty()){
            Node curr = stack.pop();
            for(Node next : curr.nexts){
                if(!set.contains(next)){
                    System.out.println(next);
                    stack.push(curr);
                    stack.push(next);
                    set.add(next);
                    break;
                }
            }
        }
    }
}
