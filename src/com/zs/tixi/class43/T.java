package com.zs.tixi.class43;

/**
 * 四边形不等式技巧（下）
 *
 * 内容：
 *
 * 继续熟悉四边形不等式
 *
 * 展示好的尝试是最关键的
 *
 * 题目：
 *
 * 一条直线上有居民点，邮局只能建在居民点上
 * 给定一个有序正数数组arr，每个值表示 居民点的一维坐标，再给定一个正数 num，表示邮局数量
 * 选择num个居民点建立num个邮局，使所有的居民点到最近邮局的总距离最短，返回最短的总距离
 * arr=[1,2,3,4,5,1000]，num=2
 * 第一个邮局建立在3位置，第二个邮局建立在1000位置
 * 那么1位置到邮局的距离为2，2位置到邮局距离为1，3位置到邮局的距离为0，4位置到邮局的距离为1，5位置到邮局的距离为2
 * 1000位置到邮局的距离为0
 * 这种方案下的总距离为6，其他任何方案的总距离都不会比该方案的总距离更短，所以返回6
 *
 * 一座大楼有0~N层，地面算作第0层，最高的一层为第N层
 * 已知棋子从第0层掉落肯定不会摔碎，从第i层掉落可能会摔碎，也可能不会摔碎(1≤i≤N)
 * 给定整数N作为楼层数，再给定整数K作为棋子数
 * 返回如果想找到棋子不会摔碎的最高层数，即使在最差的情况下扔的最少次数
 * 一次只能扔一个棋子
 * N=10，K=1
 * 返回10
 * 因为只有1棵棋子，所以不得不从第1层开始一直试到第10层
 * 在最差的情况下，即第10层是不会摔坏的最高层，最少也要扔10次
 * N=3，K=2
 * 返回2
 * 先在2层扔1棵棋子，如果碎了试第1层，如果没碎试第3层
 * N=105，K=2
 * 返回14
 * 第一个棋子先在14层扔，碎了则用仅存的一个棋子试1~13
 * 若没碎，第一个棋子继续在27层扔，碎了则用仅存的一个棋子试15~26
 * 若没碎，第一个棋子继续在39层扔，碎了则用仅存的一个棋子试28~38
 * 若没碎，第一个棋子继续在50层扔，碎了则用仅存的一个棋子试40~49
 * 若没碎，第一个棋子继续在60层扔，碎了则用仅存的一个棋子试51~59
 * 若没碎，第一个棋子继续在69层扔，碎了则用仅存的一个棋子试61~68
 * 若没碎，第一个棋子继续在77层扔，碎了则用仅存的一个棋子试70~76
 * 若没碎，第一个棋子继续在84层扔，碎了则用仅存的一个棋子试78~83
 * 若没碎，第一个棋子继续在90层扔，碎了则用仅存的一个棋子试85~89
 * 若没碎，第一个棋子继续在95层扔，碎了则用仅存的一个棋子试91~94
 * 若没碎，第一个棋子继续在99层扔，碎了则用仅存的一个棋子试96~98
 * 若没碎，第一个棋子继续在102层扔，碎了则用仅存的一个棋子试100、101
 * 若没碎，第一个棋子继续在104层扔，碎了则用仅存的一个棋子试103
 * 若没碎，第一个棋子继续在105层扔，若到这一步还没碎，那么105便是结果
 */
public class T {
}