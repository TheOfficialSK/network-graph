import java.util.*;

/**
 * @param nodesList This is the nodesList arraylist which holds all nodes in the graph.
 */
public record NetGraph(ArrayList<AdjacencyListHead> nodesList) {

    /**
     * (WORKING TESTED)
     * This is the constructor for the NetGraph Object.
     *
     * @param nodesList An arraylist of AdjacencyListHead objects.
     */
    public NetGraph {
    }

    /**
     * (WORKING TESTED)
     * This is the getter for the nodesList arraylist.
     *
     * @return An arraylist of AdjacencyListHead objects.
     */
    @Override
    public ArrayList<AdjacencyListHead> nodesList() {
        return nodesList;
    }

    /**
     * This method gets the number of nodes in the graph by iterating nodesList arraylist.
     *
     * @return An integer which represents the number of nodes in the graph.
     */
    public int getNumNodes() {
        return nodesList.size();
    }

    /**
     * This method gets the number of edges in the graph between nodes.
     *
     * @return An integer which represents the number of edges between nodes.
     */
    public int getNumLinks() {
        int numEdges = 0;
        for (AdjacencyListHead adjacencyListHead : nodesList) {
            numEdges += adjacencyListHead.getAdjacencyList().size();
        }

        return numEdges / 2;
    }

    /**
     * This is the method that inserts a Node into the graph.
     *
     * @param id           The id of the node being inserted.
     * @param name         The name of the node being inserted.
     * @param x_coordinate The x_coordinate of the node being inserted.
     * @param y_coordinate The y_coordinate of the node being inserted.
     */
    public void insertNetNode(int id, String name, double x_coordinate, double y_coordinate) {
        NetNode newNode = new NetNode(id, name, x_coordinate, y_coordinate);

        for (AdjacencyListHead head : nodesList) {
            if (head.getNetNode().equals(newNode)) {
                throw new IllegalArgumentException();
            }
        }

        AdjacencyListHead newAdjListHead = new AdjacencyListHead(newNode);
        nodesList.add(newAdjListHead);
    }


    /**
     * This is the method that adds a link between Nodes in the graph.
     *
     * @param node1  Node1 which will be linked to Node2.
     * @param node2  Node2 which will be linked to Node1.
     * @param weight The weight of the edge or "link".
     */
    public void addLink(NetNode node1, NetNode node2, double weight) {
        int index1 = node1.getId();
        int index2 = node2.getId();

        AdjacencyListHead head1 = nodesList.get(index1);
        LinkedList<Adjacent> adjList1 = head1.getAdjacencyList();

        // Check if the link already exists
        boolean linkExists = false;
        for (Adjacent adjacent : adjList1) {
            if (adjacent.getNeighbor().getId() == index2) {
                linkExists = true;
                break;
            }
        }

        if (!linkExists) {
            adjList1.add(new Adjacent(node2, weight));
        }

        AdjacencyListHead head2 = nodesList.get(index2);
        LinkedList<Adjacent> adjList2 = head2.getAdjacencyList();

        // Check if the link already exists
        linkExists = false;
        for (Adjacent adjacent : adjList2) {
            if (adjacent.getNeighbor().getId() == index1) {
                linkExists = true;
                break;
            }
        }

        if (!linkExists) {
            adjList2.add(new Adjacent(node1, weight));
        }
    }


    /**
     * This is the method that deletes a node from the graph.
     *
     * @param node The node being deleted.
     */
    public void deleteNetNode(NetNode node) {
        if (node == null) {
            throw new IllegalArgumentException();
        }

        boolean removed = false;
        Iterator<AdjacencyListHead> iter1 = nodesList.iterator();
        while (iter1.hasNext()) {
            AdjacencyListHead curr = iter1.next();
            if (curr.getNetNode().getId() == node.getId()) {
                iter1.remove();
                removed = true;
                break;
            }
        }

        if (!removed) {
            throw new IllegalArgumentException();
        }

        for (AdjacencyListHead item : nodesList) {
            item.getAdjacencyList().removeIf(adj -> adj.getNeighbor().getId() == node.getId());
        }
    }


    /**
     * This method removes an edge or "link" between two nodes, node1 and node2.
     *
     * @param node1 The first node which will be unlinked with node2.
     * @param node2 The second node which will be unlinked with node1.
     */
    public void removeLink(NetNode node1, NetNode node2) {
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException();
        }

        AdjacencyListHead node1AdjacencyListHead = null;
        AdjacencyListHead node2AdjacencyListHead = null;

        for (AdjacencyListHead item : nodesList) {
            if (item.getNetNode().getId() == node1.getId()) {
                node1AdjacencyListHead = item;
            }
            if (item.getNetNode().getId() == node2.getId()) {
                node2AdjacencyListHead = item;
            }
        }

        if (node1AdjacencyListHead == null || node2AdjacencyListHead == null) {
            throw new IllegalArgumentException("One or both nodes are not found in the graph.");
        }

        boolean removedFromNode1 = false;
        boolean removedFromNode2 = false;

        removedFromNode1 = isRemovedFromNode2(node2, node1AdjacencyListHead, removedFromNode1);

        removedFromNode2 = isRemovedFromNode2(node1, node2AdjacencyListHead, removedFromNode2);

        if (!removedFromNode1 || !removedFromNode2) {
            throw new IllegalArgumentException("There is no link between the provided nodes.");
        }
    }

    private boolean isRemovedFromNode2(NetNode node1, AdjacencyListHead node2AdjacencyListHead, boolean removedFromNode2) {
        Iterator<Adjacent> iter2 = node2AdjacencyListHead.getAdjacencyList().iterator();
        while (iter2.hasNext()) {
            Adjacent adj = iter2.next();
            if (adj.getNeighbor().getId() == node1.getId()) {
                iter2.remove();
                removedFromNode2 = true;
                break;
            }
        }
        return removedFromNode2;
    }


    /**
     * This method returns the linked-list associated with the given node.
     *
     * @param node The node which
     * @return The linked-list associated with the given node.
     */
    public LinkedList<Adjacent> getAdjacent(NetNode node) {
        nullCheck(node);

        LinkedList<Adjacent> returnList = null;
        for (AdjacencyListHead adjacencyListHead : nodesList) {
            if (adjacencyListHead.getNetNode().getId() == node.getId()) {
                returnList = adjacencyListHead.getAdjacencyList();
            }
        }
        return returnList;
    }

    private void nullCheck(NetNode node) {
        if (node == null) {
            throw new IllegalArgumentException();
        }

        boolean nodeCheck = false;
        for (AdjacencyListHead adjacencyListHead : nodesList) {
            if (adjacencyListHead.getNetNode().getId() == node.getId()) {
                nodeCheck = true;
                break;
            }
        }

        if (!nodeCheck) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * This method returns the number of nodes connected to the node that is passed.
     *
     * @param node The node that we are checking the number of nodes connected to.
     * @return The integer which represents the number of nodes that the passed node is connected to.
     */
    public int degree(NetNode node) {
        nullCheck(node);

        int numAdj = 0;

        for (AdjacencyListHead adjacencyListHead : nodesList) {
            if (adjacencyListHead.getNetNode().getId() == node.getId()) {
                numAdj = adjacencyListHead.getAdjacencyList().size();
            }
        }

        return numAdj;
    }

    /**
     * This method gets the max degree of a node.
     *
     * @return An integer which represents the max degree.
     */
    public int getGraphMaxDegree() {
        //Implement this method
        //returns the maximum number of adjacent nodes connected to a particular node


        int numAdj = 0;

        for (AdjacencyListHead adjacencyListHead : nodesList) {
            if (adjacencyListHead.getAdjacencyList().size() > numAdj) {
                numAdj = adjacencyListHead.getAdjacencyList().size();
            }
        }
        return numAdj;
    }

    /**
     * This method returns a node given an index.
     *
     * @param index The index position of the node in node list.
     * @return The node which is at given index.
     */
    public NetNode nodeFromIndex(int index) {
        if (index < 0 || index > nodesList.size() - 1) {
            throw new IllegalArgumentException();
        }
        NetNode foundNode = null;
        for (int i = 0; i < nodesList.size(); i++) {
            if (index == i) {
                foundNode = nodesList.get(index).getNetNode();
                break;
            }
        }
        return foundNode;
    }

    public String printGraph() {
        StringBuilder sb = new StringBuilder();

        for (AdjacencyListHead list : nodesList) {
            sb.append(list.getNetNode().getName()).append(": ");
            for (Adjacent item : list.getAdjacencyList()) {
                sb.append(item.getNeighbor().getName()).append(", ").append(item.getWeight()).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
