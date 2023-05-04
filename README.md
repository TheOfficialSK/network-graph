# Network Graph

This Java-based program allows you to create and manipulate network graphs, find the shortest path between nodes using Dijkstra's algorithm, and visualize the graph in a textual format. The graph is represented using an adjacency list, which provides efficient storage and traversal for sparse graphs.

## Features

* Create a network graph with nodes and edges
* Add and remove nodes and edges
* Find the shortest path between two nodes using Dijkstra's algorithm
* Visualize the network graph as a string representation

## Usage
To use this program, create an instance of the NetworkGraph class and manipulate the graph using its methods:

```
NetworkGraph graph = new NetworkGraph();
NetNode node1 = new NetNode(0, "A");
NetNode node2 = new NetNode(1, "B");
NetNode node3 = new NetNode(2, "C");

graph.addNode(node1);
graph.addNode(node2);
graph.addNode(node3);

graph.addEdge(node1, node2, 10);
graph.addEdge(node2, node3, 5);
```

To find the shortest path between two nodes:

```
ArrayList<NetNode> shortestPath = graph.getShortestPath(node1, node3);
if (shortestPath != null) {
    System.out.println("Shortest path: " + shortestPath);
} else {
    System.out.println("No path found between the nodes.");
}
```

To visualize the network graph:

```
String graphString = graph.printGraph();
System.out.println(graphString);
```
