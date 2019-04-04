package com.arichardson.main;

public class Connection {
	
	public int from, to, cost;
	
	public Connection(int from, int to, int cost) {
		this.from = from;
		this.to = to;
		this.cost = cost;
	}
	
	public String toString() {
		return " - From: "+from+", To: "+to+", Cost:"+cost;
	}

}
