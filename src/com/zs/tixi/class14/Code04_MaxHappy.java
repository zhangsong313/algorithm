package com.zs.tixi.class14;

import java.util.ArrayList;
import java.util.List;

/*
 * 4.二叉树递归套路续：
 *      派对的最大快乐值：
 *          员工信息定义如下：
 *              class Employee{
 *                  public int happy; // 这名员工可以带来的快乐值。
 *                  List<Employee> subordinates; // 这名员工有哪些直接下级。
 *              }
 *          公司的每个员工都符合Employee的描述。整个公司的人员结构可以看作是一颗标准的，没有环的多叉树。
 *          树的头节点是公司唯一的老板。除老板以外的每一个员工都有唯一的一个直接上级。叶节点是没有任何下级的基础员工（subordinates为空）
 *          除基层员工外，每个员工都有一个或多个直接下级。
 *
 *          这个公司现在要办party，你可以决定哪些员工来，哪些员工不来。规则：
 *          1）如果某个员工来了，那么这个员工的所有直接下级都不能来。
 *          2）排队的整体快乐值是所有到场员工快乐值的累加。
 *          3）你的目标是让派对的快乐值尽量大。
 *          给定一颗多叉树的头节点boss。请返回排队的最大快乐值。
 */
public class Code04_MaxHappy {

    public static class Employee {
        public int happy;
        public List<Employee> nexts;

        public Employee(int h) {
            happy = h;
            nexts = new ArrayList<>();
        }

    }

    public static int maxHappy1(Employee boss) {
        if (boss == null) {
            return 0;
        }
        return process1(boss, false);
    }

    // 当前来到的节点叫cur，
    // up表示cur的上级是否来，
    // 该函数含义：
    // 如果up为true，表示在cur上级已经确定来，的情况下，cur整棵树能够提供最大的快乐值是多少？
    // 如果up为false，表示在cur上级已经确定不来，的情况下，cur整棵树能够提供最大的快乐值是多少？

    /**
     * 这是一种类似动态规划的思想。
     */
    public static int process1(Employee cur, boolean up) {
        if (up) { // 如果cur的上级来的话，cur没得选，只能不来
            int ans = 0;
            for (Employee next : cur.nexts) {
                ans += process1(next, false);
            }
            return ans;
        } else { // 如果cur的上级不来的话，cur可以选，可以来也可以不来
            int p1 = cur.happy;
            int p2 = 0;
            for (Employee next : cur.nexts) {
                p1 += process1(next, true);
                p2 += process1(next, false);
            }
            return Math.max(p1, p2);
        }
    }

    /**
     * 当前员工为头的多叉树的最大快乐值：
     * 如果当前员工不去：
     *      所有直接下级的最大快乐值相加。
     * 否则：
     *      所有直接下级自己不去的情况下的最大快乐值相加，再加上当前员工的快乐值
     *
     * 两种快乐值取最大为最大快乐值。
     *
     * 需要的信息：最大快乐值， 自己不去情况下的最大快乐值
     */
    private static class Info {
        public int maxHappy;
        public int noSelfHappy;
        public Info(int maxHappy, int noSelfHappy){
            this.maxHappy = maxHappy;
            this.noSelfHappy = noSelfHappy;
        }
    }

    /**
     * 如果当前员工为空，返回(0, 0)
     * 递归查询所有员工的信息。infoArr
     * 当前员工不去：将infoArr中所有的maxHappy相加得到maxHappy1.
     * 当前员工去：将infoArr中所有的noSelfHappy相加，再加上当前员工的快乐值，得到maxHappy2
     * 当前的maxHappy为maxHappy1和maxHappy2的较大值。
     * 当前的noSelfHappy为maxHappy1
     *
     * 返回
     * @param emp
     * @return
     */
    public static Info process(Employee emp){
        if (emp==null) return new Info(0, 0);
        int maxHappy1 = 0;
        int maxHappy2 = emp.happy;
        for (int i = 0; i < emp.nexts.size(); i++) {
            Info nextInfo = process(emp.nexts.get(i));
            maxHappy1+=nextInfo.maxHappy;
            maxHappy2+=nextInfo.noSelfHappy;
        }
        int maxHappy = Math.max(maxHappy1, maxHappy2);
        return new Info(maxHappy, maxHappy1);
    }

    public static int maxHappy2(Employee emp){
        return process(emp).maxHappy;
    }

    // for test
    public static Employee genarateBoss(int maxLevel, int maxNexts, int maxHappy) {
        if (Math.random() < 0.02) {
            return null;
        }
        Employee boss = new Employee((int) (Math.random() * (maxHappy + 1)));
        genarateNexts(boss, 1, maxLevel, maxNexts, maxHappy);
        return boss;
    }

    // for test
    public static void genarateNexts(Employee e, int level, int maxLevel, int maxNexts, int maxHappy) {
        if (level > maxLevel) {
            return;
        }
        int nextsSize = (int) (Math.random() * (maxNexts + 1));
        for (int i = 0; i < nextsSize; i++) {
            Employee next = new Employee((int) (Math.random() * (maxHappy + 1)));
            e.nexts.add(next);
            genarateNexts(next, level + 1, maxLevel, maxNexts, maxHappy);
        }
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxNexts = 7;
        int maxHappy = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            Employee boss = genarateBoss(maxLevel, maxNexts, maxHappy);
            if (maxHappy1(boss) != maxHappy2(boss)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
