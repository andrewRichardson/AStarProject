package com.arichardson.main;

public class Request {
	
	public int start, goal;
	
	public Request(int start, int goal) {
		this.start = start;
		this.goal = goal;
	}

	public String toString() {
		return " - Start: "+start+", Goal: "+goal;
	}
	
}
