package com.zs.tixi.class17;

import java.util.ArrayList;

// 点结构的描述
public class Node {
	public int value; // 值
	public int in; // 入度,有多少条边指向该点.
	public int out; // 出度,从当前点有多少条边出去.
	public ArrayList<Node> nexts; // 临接点,当前点出发直接路径可以到哪些点.
	public ArrayList<Edge> edges; // 临接边,当前点出发有哪些边

	public Node(int value) {
		this.value = value;
		in = 0;
		out = 0;
		nexts = new ArrayList<>();
		edges = new ArrayList<>();
	}
}
