import java.util.ArrayList;
import java.util.LinkedList;

public class tester {
	public static void main(String[] args) {
		ArrayList<AdjacencyListHead> nodesList = new ArrayList<>();

		NetNode nodeA = new NetNode(0, "Node A", 0, 0);
		NetNode nodeB = new NetNode(1, "Node B", 12, 13);
		NetNode nodeC = new NetNode(2, "Node C", 10, 2);
		NetNode nodeD = new NetNode(3, "Node D", 1, 23);
		NetNode nodeE = new NetNode(4, "Node E", 9, 14);

		AdjacencyListHead nodeAHead = new AdjacencyListHead(nodeA);
		AdjacencyListHead nodeBHead = new AdjacencyListHead(nodeB);
		AdjacencyListHead nodeCHead = new AdjacencyListHead(nodeC);
		AdjacencyListHead nodeDHead = new AdjacencyListHead(nodeD);
		AdjacencyListHead nodeEHead = new AdjacencyListHead(nodeE);

		nodesList.add(nodeAHead);
		nodesList.add(nodeBHead);
		nodesList.add(nodeCHead);
		nodesList.add(nodeDHead);
		nodesList.add(nodeEHead);

		NetGraph myNetwork = new NetGraph(nodesList);

		System.out.println("The size of the nodesList is : " + myNetwork.nodesList().size());
		System.out.println("-------------------------------------------------------------------------------");

		myNetwork.addLink(nodeA, nodeC, 3);
		myNetwork.addLink(nodeA, nodeB, 1);
		myNetwork.addLink(nodeC, nodeB, 0);

		myNetwork.addLink(nodeB, nodeA, 12);
		myNetwork.addLink(nodeA, nodeC, 1);

		myNetwork.addLink(nodeC, nodeA, 43);

		System.out.println((myNetwork.printGraph()));
		System.out.println("-------------------------------------------------------------------------------");

		myNetwork.removeLink(nodeA, nodeC);

		System.out.println(myNetwork.printGraph());
		System.out.println("-------------------------------------------------------------------------------");

		System.out.println(myNetwork.getGraphMaxDegree());

		// Testing the Network class
		System.out.println("Testing the Network class:");
		System.out.println("-------------------------------------------------------------------------------");

		Network testNetwork = new Network(10, 50);
		int[][] mstMatrix = testNetwork.minSpanningTree();

		System.out.println("Minimum Spanning Tree Matrix:");
		for (int[] row : mstMatrix) {
			for (int value : row) {
				System.out.print(value + " ");
			}
			System.out.println();
		}
		System.out.println("-------------------------------------------------------------------------------");

		NetNode testNode1 = myNetwork.nodesList().get(0).getNetNode();
		NetNode testNode2 = myNetwork.nodesList().get(myNetwork.nodesList().size() - 1).getNetNode();

		System.out.println("Shortest Path between Node A and Node E:");
		ArrayList<NetNode> shortestPath = testNetwork.getShortestPath(testNode1, testNode2);
		if (shortestPath != null) {
			for (NetNode node : shortestPath) {
				System.out.print(node.getName() + " -> ");
			}
		} else {
			System.out.println("No path found.");
		}
		System.out.println("\n-------------------------------------------------------------------------------");

		// Test getNodesList()
		System.out.println("getNodesList test: " + myNetwork.nodesList());

		// Test getNumNodes()
		System.out.println("getNumNodes test: " + myNetwork.getNumNodes());

		// Test insertNetNode()
		myNetwork.insertNetNode(3, "Node D", 1, 23);
		System.out.println("insertNetNode test: " + myNetwork.nodesList());

		// Test addLink()
		myNetwork.addLink(nodeA, nodeB, 5);
		myNetwork.addLink(nodeA, nodeC, 3);
		System.out.println("addLink test:");
		System.out.println(myNetwork.printGraph());

		// Test getNumLinks()
		System.out.println("getNumLinks test: " + myNetwork.getNumLinks());

		// Test deleteNetNode()
		myNetwork.deleteNetNode(nodeC);
		System.out.println("deleteNetNode test:");
		System.out.println(myNetwork.printGraph());

		// Test removeLink()
		myNetwork.removeLink(nodeA, nodeB);
		System.out.println("removeLink test:");
		System.out.println(myNetwork.printGraph());

		// Test getAdjacent()
		LinkedList<Adjacent> adjacent = myNetwork.getAdjacent(nodeA);
		System.out.println("getAdjacent test: " + adjacent);

		// Test degree()
		int degree = myNetwork.degree(nodeA);
		System.out.println("degree test: " + degree);

		// Test getGraphMaxDegree()
		int maxDegree = myNetwork.getGraphMaxDegree();
		System.out.println("getGraphMaxDegree test: " + maxDegree);

		// Test nodeFromIndex()
		NetNode foundNode = myNetwork.nodeFromIndex(1);
		System.out.println("nodeFromIndex test: " + foundNode);
		System.out.println("-------------------------------------------------------------------------------");
		System.out.println(myNetwork.printGraph());
	}
}
