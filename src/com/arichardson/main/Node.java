package com.arichardson.main;

public class Node {
	
	public double x, y;
	public String name = "";

	// Node storage class
	public Node(double x, double y) { // Node with no name
		this.x = x;
		this.y = y;
	}
	
	public Node(double x, double y, String name) { // Node with name
		this.x = x;
		this.y = y;
		this.name = name;
	}
	
	public String toString() {
		return (name.equals("")?"Road":name)+":   x = "+x+", y = "+y+"\n";
	}
}
