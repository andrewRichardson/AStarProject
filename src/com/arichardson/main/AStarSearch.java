package com.arichardson.main;

import java.util.List;

public class AStarSearch {
	
	List<Node> nodes;
	List<Connection> connections;
	
	public AStarSearch(String path) {
		loadGraph(path);
	}
	
	public void loadGraph(String path) {
		
	}
	
	public void search(Node start, Node goal) {
		
	}
	
	public static void main(String args[]) {
		new AStarSearch("CS 330, Graph Adventure Bay v2.txt"); 
	}

}
