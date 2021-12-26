package com.zs.tixi.class17;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 图
 */
public class Graph {
	public HashMap<Integer, Node> nodes; // 图的所有节点 ,key为值,value为对应的节点对象.
	public HashSet<Edge> edges; // 图的所有边
	
	public Graph() {
		nodes = new HashMap<>();
		edges = new HashSet<>();
	}
}
