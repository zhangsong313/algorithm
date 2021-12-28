package com.zs.tixi.class15;

/**
 * 贪心算法的解题套路实战：
 * 1. 给定一个字符串，只能由'X'和'.'两种字符构成。
 *      ‘X’表示墙，不能放灯，也不需要点亮。
 *      ‘.’表示居民点，可以放灯，需要点亮。
 *      如果灯放在i位置，可以让i-1, i, i+1三个位置被点亮。
 *      返回如果点亮str中所有需要点亮的位置，至少需要几盏灯。
 * 2. 一块金条切成两半，是需要花费和长度数值一样的铜板的。
 *      比如长为20的金条，不论怎么切，都要花费20个铜板，一群人想整分整块金条，怎么分最省铜板。
 *      例如：给定数组{10，20，30}代表一共三个人，整块金条长度为60，金条要分成10，20，30三个部分。
 *      如果先把长度为60的金条分成10和50，花费60，再把50长度分为20和30，花费50，一共花费110铜板。
 *      但如果先把60长度的金条分成30和30，花费60，再把再把长度30的金条分成10和20，花费30，一共花费90铜板。
 *      输入一个数组，返回分割的最小代价。
 * 3. 一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲。
 *      给你每一个项目开始的时间和结束的时间。
 *      你来安排宣讲的日程，要求会议室进行的宣讲场次最多。
 *      返回最多的宣讲场次。
 * 4. 输入正数数组costs，正数数组profits，正数K，正数M
 *      costs[i]表示i号项目的花费。
 *      profits[i]表示i号项目在扣除花费之后还能挣到的钱（利润）。
 *      K表示你只能串行做的最多K个项目。
 *      M表示初始资金。
 *      说明：每做完一个项目，马上获得的收益，可以支持你去做下一个项目。不能并行的做项目。
 *      输出：你最后获得的最大钱数。
 * 5. 并查集
 *      需要解决的问题：
 *      1）有若干个样本a,b,c,d......类型假设是V
 *      2）在并查集中中一开始认为每个样本都在单独的集合里。
 *      3）用户可以在任何时候调用如下两个方法：
 *          boolean isSameSet(V x, V y): 查询样本x和样本y是否属于同一个集合。
 *          void union(V x, V y):把x和y各自所在的集合的所有样本合并为一个集合。
 *      4）isSameSet和union方法的代价越低越好。
 *      并查集原理：
 *      1）每个节点都有一条往上指的指针。
 *      2）节点a往上找到的头节点，叫做a所在集合的代表节点。
 *      3）查询x和y是否属于同一集合，就是看找到的代表节点是否是同一个。
 *      4）把x和y各自所在集合的所有节点合并成一个集合。只需要小集合的代表节点挂在大集合的代表点的下方即可。
 *      并查集优化：
 *      1）节点往上找代表节点的过程，把沿途中的链变成扁平的。
 *      2）小集合挂在大集合的下面。
 *      3）如果方法调用很频繁，那么单次调用的代价为O(1)，两个方法都是如此。
 *      并查集应用：
 *      解决两大块区域的合并问题。
 *      常用在图等领域中.
 */
public class T {
}