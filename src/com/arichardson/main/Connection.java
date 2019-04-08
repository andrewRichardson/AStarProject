package com.arichardson.main;

public class Connection {
	
	public int from, to, cost;
	
	// Connection storage class
	public Connection(int from, int to, int cost) {
		this.from = from;
		this.to = to;
		this.cost = cost;
	}
	
	public String toString() {
		return from + "  ->  " + to + ",  Cost:" + cost + "\n";
	}

}
