package com.zs.tixi.class17;

public class Edge {
	public int weight; // 当前边的权重值
	public Node from; // 当前边从哪个点来
	public Node to; // 当前边指向哪个点

	public Edge(int weight, Node from, Node to) {
		this.weight = weight;
		this.from = from;
		this.to = to;
	}

}
