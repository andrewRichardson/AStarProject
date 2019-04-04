package com.arichardson.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AStarSearch {
	
	List<Node> nodes;
	List<Connection> connections;
	List<Request> requests;
	
	public AStarSearch(String path) {
		nodes = new ArrayList<Node>();
		connections = new ArrayList<Connection>();
		requests = new ArrayList<Request>();
		loadGraph(path);
		
		//System.out.println(Arrays.toString(nodes.toArray()));
		//System.out.println(Arrays.toString(connections.toArray()));
		//System.out.println(Arrays.toString(requests.toArray()));
	}
	
	public void loadGraph(String path) {
		BufferedReader file;
		try {
			file = new BufferedReader(new FileReader(path));
			String line = file.readLine();
			String[] values;
			int[] intValues;
			double[] doubleValues;
			int index;
			String name = "";
			while (line != null) {
				if (line.charAt(0) != '#') {
					values = line.split(" ");
					
					if (line.charAt(0) == 'N') {
						doubleValues = new double[2];
						index = 0;
						name = "";
						for (int i = 1; i < values.length; i++) {
							if(!values[i].equals(" ") && !values[i].equals("")) {
								doubleValues[index++] = Double.parseDouble(values[i]);
							}
							if (index >= 2) {
								i = values.length;
								name = values[values.length-1];
							}
						}
						Node node = new Node(doubleValues[0], doubleValues[1]);
						if (!name.equals("")) {
							node.name = name;
						}
						nodes.add(node);
					}
					if (line.charAt(0) == 'C') {
						intValues = new int[3];
						index = 0;
						for (int i = 1; i < values.length; i++) {
							if(!values[i].equals(" ") && !values[i].equals("")) {
								intValues[index++] = Integer.parseInt(values[i]);
							}
						}
						Connection connection = new Connection(intValues[0], intValues[1], intValues[2]);
						connections.add(connection);
					}
					if (line.charAt(0) == 'R') {
						intValues = new int[2];
						index = 0;
						for (int i = 1; i < values.length; i++) {
							if(!values[i].equals(" ") && !values[i].equals("")) {
								intValues[index++] = Integer.parseInt(values[i]);
							}
						}
						Request request = new Request(intValues[0], intValues[1]);
						requests.add(request);
					}
				}
				
				line = file.readLine();
			}
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void search(Node start, Node goal) {
		
	}
	
	public static void main(String args[]) {
		new AStarSearch("CS 330, Graph Adventure Bay v2.txt"); 
	}

}
