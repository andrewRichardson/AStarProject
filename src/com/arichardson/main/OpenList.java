package com.arichardson.main;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings({ "serial", "hiding" })
public class OpenList<Node> extends ArrayList<Node>{ // Open List data structure avoiding need to search array for lowest cost

	// This class works by storing the Nodes in the open list by ascending cost.
	// Each node that is added to the list is inserted by cost
	
	// Goal cost stored in HashMap
	public HashMap<Node, Double> goalCost;
	
	// Constructor requires initialization of first element in goalCost
	public OpenList(Node initNode, double initCost) {
		goalCost = new HashMap<Node, Double>();
		goalCost.put(initNode, initCost);
	}
	
	// Function to add the node to the list using the cost
	public void add(Node node, double nodeGoalCost) {
		// Where to insert the node
		int index = -1;
		// Iterate through the list until the cost is less than the next Node cost
		for(int i = 0; i < this.size(); i++) {
			if (nodeGoalCost < goalCost.get(this.get(i))) {
				index = i;
				i = this.size();
			}
		}
		// If the index was unchanged, it is the largest cost and is appended to the list, otherwise, insert
		if (index != -1) {
			this.add(index, node);
		}else {
			this.add(node);
		}

		// Add the Node and its cost to the goalCost HashMap
		goalCost.put(node, nodeGoalCost);
	}
	
	// This function makes sure that when a node is removed from the list, it is first removed from the goalCost HashMap
	@Override
	public boolean remove(Object node) {
		goalCost.remove(node);
		return super.remove(node);
	}
	
}
