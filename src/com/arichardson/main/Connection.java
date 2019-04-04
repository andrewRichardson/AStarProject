package com.arichardson.main;

public class Connection {
	
	public Node from, to;
	public int cost;
	
	public Connection(Node from, Node to, int cost) {
		this.from = from;
		this.to = to;
		this.cost = cost;
	}

}
