package com.arichardson.main;

public class Request {
	
	public int start, goal;

	// Request storage class
	public Request(int start, int goal) {
		this.start = start;
		this.goal = goal;
	}

	public String toString() {
		return "Request:\n---------\nStart: " + start + "Goal:  " + goal;
	}
	
}
