import java.util.*;

class Node implements Comparable<Node> {
    int id;
    int distance;

    public Node(int id, int distance) {
        this.id = id;
        this.distance = distance;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.distance, other.distance);
    }
}

class Edge {
    int from;
    int to;
    int weight;

    public Edge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}

class Digraph {
    int numNodes;
    List<Edge>[] adjacencyList;

    public Digraph(int numNodes) {
        this.numNodes = numNodes;
        this.adjacencyList = new ArrayList[numNodes + 1];
        for (int i = 1; i <= numNodes; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
    }

    public void addEdge(int from, int to, int weight) {
        adjacencyList[from].add(new Edge(from, to, weight));
    }

    public int[] shortestPaths(int source) {
        int[] distances = new int[numNodes + 1];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(source, 0));

        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            int nodeId = curr.id;
            int distance = curr.distance;

            if (distance > distances[nodeId]) {
                continue;
            }

            for (Edge edge : adjacencyList[nodeId]) {
                int newDistance = distance + edge.weight;
                if (newDistance < distances[edge.to]) {
                    distances[edge.to] = newDistance;
                    pq.offer(new Node(edge.to, newDistance));
                }
            }
        }

        return distances;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numNodes = scanner.nextInt();
        int numEdges = scanner.nextInt();
        int parkingFee = scanner.nextInt();

        Digraph graph = new Digraph(numNodes);
        int[] capacities = new int[numNodes + 1];
        for (int i = 1; i <= numNodes; i++) {
            capacities[i] = scanner.nextInt();
        }

        for (int i = 0; i < numEdges; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            int weight = scanner.nextInt();
            graph.addEdge(from, to, weight);
        }

        int numVehicles = scanner.nextInt();

        scanner.close();

        int[] totalCosts = new int[numVehicles];
        int[] distances = graph.shortestPaths(1);

        for (int i = 0; i < numVehicles; i++) {
            int cost = parkingFee;

            int minDistance = Integer.MAX_VALUE;
            int chosenSlot = -1;

            for (int j = 1; j <= numNodes; j++) {
                if (capacities[j] > 0 && distances[j] < minDistance) {
                    minDistance = distances[j];
                    chosenSlot = j;
                }
            }

            if (chosenSlot != -1) {
                capacities[chosenSlot]--;
                cost += minDistance;
            } else {
                cost = -1;
            }

            totalCosts[i] = cost;
        }

        for (int cost : totalCosts) {
            System.out.print(cost + " ");
        }
    }
}
