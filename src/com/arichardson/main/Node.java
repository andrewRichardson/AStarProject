package com.arichardson.main;

public class Node {
	
	public double x, y;
	public String name = "";
	
	public Node(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Node(double x, double y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
	}
}
