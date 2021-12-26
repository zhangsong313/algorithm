package com.zs.tixi.class17;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/*
 *      宽度优先遍历：
 *          1.利用队列实现
 *          2.从源节点开始依次按照宽度进队列，然后弹出。
 *          3.每弹出一个点，把该节点所有没有进过队列的邻接点放入队列。
 *          4.直到队列变空。
 *
 *      注意:需要一个set来判断当前节点是否被访问过,否则可能会重复访问.
 */
public class Code01_BFS {

    /**
     * 从Node出发,宽度优先遍历.
     * 创建队列queue
     * 创建set用来保存已经进过队列的点.
     * 把start放入queue.
     * 遍历queue:
     *      从queue中取出一个点curr.
     *      打印curr
     *      遍历当前点的所有邻接点:
     *          如果不在set内:放入set后,放入队列.
     * @param start
     */
    public static void bfs(Node start) {
        Queue<Node>  queue = new LinkedList<>();
        Set<Node> set = new HashSet<>();
        queue.add(start);
        set.add(start);
        while (!queue.isEmpty()){
            Node curr = queue.poll();
            System.out.println(curr.value);
            for(Node next : curr.nexts){
                if (!set.contains(next)) {
                    set.add(next);
                    queue.add(next);
                }
            }
        }
    }
}
