import java.util.*;

/*
    The Network class is used to create a network of nodes. The network is represented by a graph.
    The graph is represented by an adjacency list. The adjacency list is represented by an arraylist
    of AdjacencyListHead objects. Each AdjacencyListHead object represents a node in the network.
    Each AdjacencyListHead object contains a NetNode object and a linked list of Adjacent objects.
    The linked list of Adjacent objects represents the edges of the graph. Each Adjacent object
    contains a NetNode object and a distance value. The distance value represents the weight of the
    edge between the two nodes.
 */
public class Network {
    private final NetGraph networkGraph;
    private static final double TRANSMISSION_RANGE = 20 * Math.sqrt(2);
    // Default constructor
    public Network() {
        this(1000, 200);
    }

    public Network(int numNodes, int fieldSize) {
        ArrayList<AdjacencyListHead> nodesList = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < numNodes; i++) {
            double x = rand.nextDouble() * fieldSize;
            double y = rand.nextDouble() * fieldSize;
            String name = "Node " + (i + 1);
            NetNode node = new NetNode(i, name, x, y);
            nodesList.add(new AdjacencyListHead(node));
        }

        networkGraph = new NetGraph(nodesList);

        // Connect nodes based on transmission range
        for (int i = 0; i < networkGraph.nodesList().size(); i++) {
            NetNode node1 = networkGraph.nodesList().get(i).getNetNode();
            for (int j = i + 1; j < networkGraph.nodesList().size(); j++) {
                NetNode node2 = networkGraph.nodesList().get(j).getNetNode();
                double distance = calculateDistance(node1, node2);

                if (distance <= TRANSMISSION_RANGE) {
                    networkGraph.addLink(node1, node2, distance);
                }
            }
        }
    }

    private double calculateDistance(NetNode node1, NetNode node2) {
        double xDifference = node1.getX_coordinate() - node2.getX_coordinate();
        double yDifference = node1.getY_coordinate() - node2.getY_coordinate();
        return Math.sqrt(xDifference * xDifference + yDifference * yDifference);
    }

    public int[][] minSpanningTree() {
        int numNodes = networkGraph.nodesList().size();
        int[][] mstMatrix = new int[numNodes][numNodes];
        Map<NetNode, Boolean> visited = new HashMap<>();
        PriorityQueue<Adjacent> minHeap = new PriorityQueue<>(Comparator.comparingDouble(Adjacent::getWeight));
        Map<NetNode, NetNode> nodeToSourceMap = new HashMap<>();

        // Initialize the MST matrix with 0s
        for (int i = 0; i < numNodes; i++) {
            Arrays.fill(mstMatrix[i], 0);
        }

        // Initialize the visited Map with false values
        for (AdjacencyListHead listHead : networkGraph.nodesList()) {
            visited.put(listHead.getNetNode(), false);
        }

        NetNode firstNode = networkGraph.nodesList().get(0).getNetNode();
        visited.put(firstNode, true);
        AdjacencyListHead firstNodeHead = networkGraph.nodesList().get(0);
        for (Adjacent adjacent : firstNodeHead.getAdjacencyList()) {
            minHeap.offer(adjacent);
            nodeToSourceMap.put(adjacent.getNeighbor(), firstNodeHead.getNetNode());
        }

        while (!minHeap.isEmpty()) {
            Adjacent adjacent = minHeap.poll();
            NetNode node1 = nodeToSourceMap.get(adjacent.getNeighbor());
            NetNode node2 = adjacent.getNeighbor();

            if (!visited.get(node2)) {
                int index1 = node1.getId();
                int index2 = node2.getId();
                mstMatrix[index1][index2] = 1;
                mstMatrix[index2][index1] = 1;
                visited.put(node2, true);

                AdjacencyListHead nodeHead = networkGraph.nodesList().get(index2);
                for (Adjacent nextAdjacent : nodeHead.getAdjacencyList()) {
                    NetNode neighbor = nextAdjacent.getNeighbor();
                    if (!visited.get(neighbor)) {
                        minHeap.offer(nextAdjacent);
                        nodeToSourceMap.put(neighbor, nodeHead.getNetNode());
                    }
                }
            }
        }
        return mstMatrix;
    }




    public ArrayList<NetNode> getShortestPath(NetNode node1, NetNode node2) {
        if (!networkGraph.nodesList().contains(new AdjacencyListHead(node1)) ||
                !networkGraph.nodesList().contains(new AdjacencyListHead(node2))) {
            return null; // Return null if either node is not part of the graph
        }

        Map<Integer, Boolean> visited = new HashMap<>();
        Map<Integer, Double> distances = new HashMap<>();
        Map<Integer, Integer> previous = new HashMap<>();
        PriorityQueue<NetNode> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(node ->
                distances.get(node.getId())));

        for (NetNode node : networkGraph.nodesList().stream().map(AdjacencyListHead::getNetNode).toList()) {
            int nodeId = node.getId();
            distances.put(nodeId, Double.POSITIVE_INFINITY);
            previous.put(nodeId, -1);
            visited.put(nodeId, false);
        }

        distances.put(node1.getId(), 0.0);
        priorityQueue.add(node1);

        if (node1.equals(node2)) {
            ArrayList<NetNode> path = new ArrayList<>();
            path.add(node1);
            return path;
        }

        while (!priorityQueue.isEmpty()) {
            NetNode currentNode = priorityQueue.poll();
            int currentNodeIndex = currentNode.getId();
            visited.put(currentNodeIndex, true);

            if (currentNode.equals(node2)) {
                break;
            }

            AdjacencyListHead currentNodeHead = networkGraph.nodesList().get(currentNodeIndex);
            for (Adjacent adjacent : currentNodeHead.getAdjacencyList()) {
                NetNode neighbor = adjacent.getNeighbor();
                int neighborIndex = neighbor.getId();

                if (visited.get(neighborIndex)) {
                    continue;
                }

                double newDistance = distances.get(currentNodeIndex) + adjacent.getWeight();
                if (newDistance < distances.get(neighborIndex)) {
                    distances.put(neighborIndex, newDistance);
                    previous.put(neighborIndex, currentNodeIndex);
                    priorityQueue.remove(neighbor);
                    priorityQueue.add(neighbor);
                }
            }
        }

        if (previous.get(node2.getId()) == -1) {
            return null; // There is no path between node1 and node2
        }

        ArrayList<NetNode> path = new ArrayList<>();
        int currentNodeIndex = node2.getId();
        while (currentNodeIndex != -1) {
            path.add(networkGraph.nodesList().get(currentNodeIndex).getNetNode());
            currentNodeIndex = previous.get(currentNodeIndex);
        }

        // Manually reverse the path ArrayList
        for (int i = 0; i < path.size() / 2; i++) {
            NetNode temp = path.get(i);
            path.set(i, path.get(path.size() - 1 - i));
            path.set(path.size() - 1 - i, temp);
        }

        return path;
    }

}

