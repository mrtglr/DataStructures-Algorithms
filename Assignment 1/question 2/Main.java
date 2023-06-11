/*
 * Title Main.java
 * Author: Halil Mert Güler
 * ID: 16729097450
 * Section: 2
 * Assignment: 1, question 2
 * Description: This is a class for representing a graph data structure
 * with functions to finding the smallest cycle that starts from a vertex x and includes vertex y
 * also sorts the result in ascending order
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

class Graph {

    // This variable represents the number of vertices in the graph.
    private int V;
    // This is an array of linked lists representing the adjacency list of each
    // vertex.
    private LinkedList<Integer>[] adj;
    // This is a linked lists for determining the smallest cycle
    private LinkedList<Integer> smallestCycle;

    // This constructor initializes the undirected graph with V vertices and an
    // empty adjacency
    // list for each vertex.
    public Graph(int V) {
        this.V = V;
        adj = new LinkedList[V];
        for (int i = 0; i < V; i++)
            adj[i] = new LinkedList<Integer>();
    }

    // This method adds an edge between vertices u and v by adding v to the
    // adjacency list of u, and u to the adjacency list of v.
    public void addEdge(int u, int v) {
        adj[u].add(v);
        adj[v].add(u);
    }

    // This method finds cycles in a graph represented as an adjacency list,
    // starting from node x and ending at node y, and returns the smallest cycle
    // found as a linked list of integers. It uses a recursive depth-first search
    // approach and keeps track of visited nodes using a boolean array. If no cycles
    // are found, it returns null.
    public LinkedList<Integer> findCycles(int x, int y) {
        smallestCycle = null;
        boolean[] visited = new boolean[V];
        LinkedList<Integer> path = new LinkedList<>();
        path.add(x);
        findCyclesUtil(x, y, x, visited, path, 0);
        if (smallestCycle != null) {
            return smallestCycle;
        }

        return null;
    }

    // This is a recursive utility method used to find cycles in a graph. It starts
    // from
    // the 'start' node and explores adjacent nodes using an adjacency list. It
    // keeps track
    // of visited nodes using a boolean array. If a cycle of size greater than or
    // equal to 3
    // is found that ends at node 'y', it updates the smallest cycle found so far.
    private void findCyclesUtil(int start, int y, int current, boolean[] visited, LinkedList<Integer> path,
            int cycleSize) {
        visited[current] = true;

        for (Integer v : adj[current]) {
            if (v == start && path.size() >= 3) {
                path.add(v);
                if (path.contains(y)) {
                    if (smallestCycle == null || cycleSize + 1 < smallestCycle.size()) {
                        smallestCycle = new LinkedList<>(path);
                    }
                }
                path.removeLast();
            } else if (!visited[v]) {
                path.add(v);
                findCyclesUtil(start, y, v, visited, path, cycleSize + 1);
                path.removeLast();
            }
        }
        visited[current] = false;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        String[] values = input.split(" ");

        // Number of islands
        int n = Integer.parseInt(values[0]);
        // Number of connections
        int m = Integer.parseInt(values[1]);

        // Initialize the Graph
        Graph graph = new Graph(n + 1);
        for (int i = 0; i < m; i++) {
            String edge = scanner.nextLine();

            int u = Integer.parseInt(edge.split(" ")[0]);
            int v = Integer.parseInt(edge.split(" ")[1]);
            graph.addEdge(u, v);
        }

        // Initialize the start vertex and vertex that should be included in the cycle
        String direction = scanner.nextLine();
        int x = Integer.parseInt(direction.split(" ")[0]);
        int y = Integer.parseInt(direction.split(" ")[1]);

        scanner.close();

        // Result of the cycle in form of LinkedList<Integer>
        LinkedList<Integer> path = graph.findCycles(x, y);
        // This code is added because last vertex in the list is the start vertex which
        // is already included
        path.removeLast();

        // Initializing an array of integers to store path vertices and help the sorting
        // operation
        int[] finalPath = new int[path.size()];
        for (int i = 0; i < path.size(); i++) {
            finalPath[i] = path.get(i);
        }

        // Sorting paths in ascending order
        Arrays.sort(finalPath);

        // Printing the result
        for (int vertex : finalPath) {
            System.out.print(vertex + " ");
        }

    }
}