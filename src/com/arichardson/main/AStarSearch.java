package com.arichardson.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AStarSearch {
	
	// Declare storage lists
	List<Node> nodes;
	List<Connection> connections;
	List<Request> requests;
	// Text output writer
	PrintWriter textOutput;
	
	// Constructor takes a path parameter
	public AStarSearch(String path) {
		nodes = new ArrayList<Node>();
		connections = new ArrayList<Connection>();
		requests = new ArrayList<Request>();
		
		// Open text file for writing
		try {
			textOutput = new PrintWriter("CS 330 19S, P3, Richardson, Results.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Load the graph from the text file prior to searching
		loadGraph(path);
		
		// Output the graph
		print("A* Pathfinding - Adventure Bay\n-------------------------\n");
		
		print("\nNODES:\n---------\n");
		print(Arrays.toString(nodes.toArray()));
		
		print("\nCONNECTIONS:\n---------\n");
		print(Arrays.toString(connections.toArray()));
		
		print("\nREQUESTS:\n---------\n");
		// Use A* to fulfill each request
		for(Request request : requests) {
			// Java begins arrays at 0, so decrement each Node index
			search(nodes.get(request.start-1), nodes.get(request.goal-1));
		}
		
		// Close text file I/O operations
		textOutput.close();
	}
	
	public void loadGraph(String path) {
		BufferedReader file;
		try {
			// Open file and split data into lines
			file = new BufferedReader(new FileReader(path));
			String line = file.readLine();
			String[] values;
			int[] intValues;
			double[] doubleValues;
			int index;
			String name = "";
			while (line != null) {
				// Split lines into Strings
				if (line.charAt(0) != '#') {
					values = line.split(" ");
					
					// If the first value is an N, it is a node
					if (line.charAt(0) == 'N') {
						doubleValues = new double[2];
						index = 0;
						name = "";
						// Every value that is not a space is parsed as number, except if the line has a String for a name
						for (int i = 1; i < values.length; i++) {
							if(!values[i].equals(" ") && !values[i].equals("")) {
								doubleValues[index++] = Double.parseDouble(values[i]);
							}
							if (index >= 2 && index < values.length) {
								i = values.length;
								name = values[values.length-1];
								if (name.length() <= 3) {
									name = "";
								}
							}
						}
						Node node = new Node(doubleValues[0], doubleValues[1]);
						if (!name.equals("")) {
							node.name = name;
						}
						nodes.add(node);
					}

					// If the first value is an C, it is a Connection
					if (line.charAt(0) == 'C') {
						intValues = new int[3];
						index = 0;
						// Every value that is not a space is parsed as number
						for (int i = 1; i < values.length; i++) {
							if(!values[i].equals(" ") && !values[i].equals("")) {
								intValues[index++] = Integer.parseInt(values[i]);
							}
						}
						Connection connection = new Connection(intValues[0], intValues[1], intValues[2]);
						connections.add(connection);
					}
					
					// If the first value is an R, it is a Request
					if (line.charAt(0) == 'R') {
						intValues = new int[2];
						index = 0;
						// Every value that is not a space is parsed as number
						for (int i = 1; i < values.length; i++) {
							if(!values[i].equals(" ") && !values[i].equals("")) {
								intValues[index++] = Integer.parseInt(values[i]);
							}
						}
						Request request = new Request(intValues[0], intValues[1]);
						requests.add(request);
					}
				}
				
				// Read next line
				line = file.readLine();
			}
			
			// Close file for I/O operations
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Run A* using start and goal Nodes
	public void search(Node start, Node goal) {
		List<Node> closed = new ArrayList<Node>();
		
		// Custom open list data structure
		OpenList<Node> open = new OpenList<Node>(start, Double.MAX_VALUE);
		open.add(start);
		
		// Path structure of node movements
		HashMap<Node, Node> previousNode = new HashMap<Node, Node>();
		
		// The estimated cost to the Node 
		HashMap<Node, Double> nodeCost = new HashMap<Node, Double>();
		nodeCost.put(start, 0.0);

		// The estimated cost to the goal through a Node
		HashMap<Node, Double> goalCost = new HashMap<Node, Double>();
		goalCost.put(start, euclideanDistance(start, goal));
		
		// While the open list is not empty
		while (open.size() > 0) {
			// Get the lowest cost Node from the open list
			Node currentNode = open.get(0);
			
			// If the currentNode is the goal Node, we are done
			if (currentNode == goal) {
				print("\nRequest:\n---------\nStart: " + start + "Goal:  " + goal);
				
				printPath(previousNode, currentNode);
			}

			// Add the Node to the closed (visited) list and remove it from the open list
			closed.add(currentNode);
			open.remove(currentNode);
			
			// Loop through the connections to find Nodes that the currentNode can reach
			for (int i = 0; i < connections.size(); i++) {
				Connection connection = connections.get(i);
				Node fromNode = nodes.get(connection.from-1);
				Node nextNode = nodes.get(connection.to-1);
				
				// If the connection is from the currentNode and has not already been visited, evaluate the cost and add it to the open list
				if (fromNode == currentNode) {
					if (!closed.contains(nextNode)) {
						
						double nodeCostGuess = nodeCost.get(currentNode) + connection.cost;
						
						// Make sure the Node is not already on the open list
						if (!open.contains(nextNode)) {
							open.add(nextNode, (nodeCostGuess + euclideanDistance(nextNode, goal)));
							previousNode.put(nextNode, currentNode);
							nodeCost.put(nextNode, nodeCostGuess);
							goalCost.put(nextNode, (nodeCostGuess + euclideanDistance(nextNode, goal)));
						}
					}
				}
			}
		}
	}
	
	// Print the path
	public void printPath(HashMap<Node, Node> previousNode, Node lowestCostNode) {
		// Path storing the final path with the first value being the goal
		List<Node> path = new ArrayList<Node>();
		path.add(lowestCostNode);
		
		// While there is a previous node for the current node, add the previous node to the path
		while (previousNode.containsKey(lowestCostNode)) {
			lowestCostNode = previousNode.get(lowestCostNode);
			path.add(0, lowestCostNode);
		}
		
		print("A* Path:\n--------");
		print(Arrays.toString(path.toArray()));
	}
	
	// Helper function for euclideanDistance heuristic
	public double euclideanDistance(Node a, Node b) {
		return Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2));
	}
	
	// Helper function to both print to console and print to text file
	public void print(String output) {
		System.out.println(output);
		
		textOutput.println(output+"\n");
	}
	
	public static void main(String args[]) {
		new AStarSearch("CS 330, Graph Adventure Bay v2.txt"); 
	}

}
