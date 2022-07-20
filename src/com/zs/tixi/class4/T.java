package com.zs.tixi.class4;

import java.util.HashMap;

/**
 * 链表：
 * 1、单链表反转，双链表反转。
 * 2、把给定值删除。
 *
 * 队列和栈：
 * 1、双链表实现
 * 2、数组实现
 * 3、栈增加实现返回栈中最小元素的功能。
 * 4、栈实现队列。
 * 5、队列实现栈。
 *
 * 递归：
 * 新手做递归，画图。
 * Master公式分析递归时间复杂度。
 *
 * 哈希表和有序表。
 *
 */
public class T {
    /**
     * 笔记整理：
     *  一、链表
     *      1. 单链表反转
     *          思路:
     *               head.next = prev; 实质是要对每个节点进行next指针指向prev节点的操作。
     *              因此需要变量prev记录上一节点。
     *              需要变量next记录下一节点，否则next指针修改后会找不到下一个位置。
     *              每次修改完next指针后，prev移动到当前位置，head移动到下一位置
     *              直到head为null，返回prev。
     *
     *      2. 双链表反转
     *          思路：
     *             核心操作：对每个节点进行： head.next = prev; head.prev = next;
     *             需要变量记录next节点。否则head.next指针调整后next节点会丢失.
     *             需要变量记录prev节点，否则head.prev指针调整后prev节点会丢失.
     *             每次修改完指针后: prev移动到head位置，head移动到next位置，next移动到next的下一位置.
     *             直到head为null，最后返回prev
     *
     *      3. 链表删除给定值
     *          思路：
     *              核心操作：只有节点不等于给定值，prev.next才更新为head，prev来到head位置。
     *              更新完成后head来到下一位置。
     *              处理头节点被删除的情况，记录第一个不匹配给定值的节点为返回的头节点。
     *              处理尾部被删除的情况，返回前设置prev.next指向null。
     *              需要变量prev保存head的前一个节点.需要变量newHead保存返回的头节点。
     *
     *  二、队列和栈
     *      1.链表实现队列和栈
     *          思路：先实现双端队列.队列和栈通过封装双端队列得到
     *          双端队列：
     *              void addFirst(E e);
     *                  // 首先生成新节点
     *                  // 核心操作： 新节点指向头节点，头节点指向新节点，更新头节点为新节点。
     *                  // 注意处理头节点为空的情况:头节点和尾结点都指向新节点。
     *                  // size++
     *              void addLast(E e);
     *              E removeFirst();
     *                  // head为空，抛出异常。
     *                  // 获取head的值用来返回。
     *                  // 核心操作：head来到下一位置，更新head的prev为null。
     *                  // 注意处理head==tail的情况
     *                  // size--
     *              E removeLast();
     *              int size();
     *          栈：
     *              push操作调用addFirst， pop调用removeFirst
     *          队列：
     *              push调用addFirst, poll调用removeLast
     *      2. 数组实现栈和队列
     *          思路：先实现双端队列.队列和栈通过封装双端队列得到
     *          双端队列:
     *              成员变量: 需要head和tail表示当前头尾的位置，size记录当前容器大小，且用来判断是否达到容量上限。Object[]用来存放数据.
     *              构造方法：参数指定容量：创建数组需要指定大小。
     *              void addFirst(E e);
     *                  // 核心步骤：head指针移动到头部向前的位置，head位置设置为当前数据。
     *                  // 需要考虑size达到容量限制的情况。
     *                  // 需要考虑head和tail为-1的情况。
     *                  // size++
     *              void addLast(E e);
     *              E removeFirst();
     *                  // 获取head位置的数据用来返回
     *                  // 核心操作：head移动到head向后扩展的位置。
     *                  // 考虑size为0的情况
     *                  // 考虑size为1的情况
     *                  // size--
     *              E removeLast();
     *              int size();
     *          栈：
     *              push操作调用addFirst， pop调用removeFirst
     *          队列：
     *              push调用addFirst, poll调用removeLast
     *      3. 实现一个可以返回栈中最小值的栈
     *          思路：用Object[] arr保存栈数据的同时，定义Object[] minArr。
     *              使得在arr对应的范围内。minArr的头部始终是对应范围的最小值。
     *          void push(int data)
     *              // 核心步骤：head++, head位置设置为当前值。minArr[head]设置为当前值与[head-1]位置的较小值。
     *              // 考虑size到容量上限的情况
     *              // 考虑size==0的情况
     *              // size++
     *          pop()
     *              // 获取head位置值用于返回
     *              // 核心步骤：head--
     *              // 考虑size为0的情况。
     *              // 考虑size为1的情况。
     *              // size--;
     *              是否可以使用单调栈等结构节省空间实现？？？？？
     *      4. 栈实现队列
     *          思路：用两个栈倒换数据，push栈用来添加数据，pop栈用来弹出数据，
     *              pop为空时将push栈中的数据依次弹出，压入pop栈。实现数据方向的改变。
     *          核心：每次取出时,如果stackPop为空,将stackPush数据放入stackPop中.否则优先从stackPop取数。
     *      5. 队列实现栈
     *          思路：用两个队列。queue队列用来添加数据。
     *              需要取数据时，将queue队列的前n-1个数放入help队列中。
     *              取出queue队列剩下的最后一个数。
     *              交换两个队列变量，等待下次添加数据或取出数据。
     */
    public static void main(String[] args) {
        Integer a = 2500000;
        Integer b = 2500000;
        System.out.println(a==b);
        HashMap<Integer, String> map = new HashMap<>();
        map.put(a, "asdf");
        map.put(b, "asdf43434");
        System.out.println(map.size());
        System.out.println("队列满了");
        new HashMap<>();
    }
}
