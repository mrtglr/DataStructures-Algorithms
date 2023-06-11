/*
 * Title Main.java
 * Author: Halil Mert Güler
 * ID: 
 * Section: 2
 * Assignment: 1, question 1
 * Description: This is a class for representing a graph data structure
 * with functions to finding the shortest path between two vertices
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

class Graph {
    // This variable represents the number of vertices in the graph.
    private int V;
    // This is an array of linked lists representing the adjacency list of each
    // vertex.
    private LinkedList<Integer>[] adj;

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

    // This method uses breadth-first search to find the shortest path between the
    // start and end vertices.
    public List<Integer> shortestPath(int start, int end) {

        // This array keeps track of whether a vertex has been visited during the
        // search.
        boolean[] visited = new boolean[V];
        // This array keeps track of the distance from the start vertex to each vertex.
        int[] distance = new int[V];
        // This array keeps track of the parent of each vertex in the shortest path.
        int[] parent = new int[V];

        // This queue stores the vertices to be visited during the search.
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(start);
        visited[start] = true;

        // Perform the breadth-first search.
        while (!queue.isEmpty()) {
            int curr = queue.poll();

            // If we have reached the end vertex, we can stop the search.
            if (curr == end)
                break;

            // For each neighbor of the current vertex:
            for (int next : adj[curr]) {
                if (!visited[next]) {

                    // Mark the neighbor as visited, update its distance and parent, and add it to
                    // the queue.
                    visited[next] = true;
                    distance[next] = distance[curr] + 1;
                    parent[next] = curr;
                    queue.add(next);
                }
            }
        }

        // Reconstruct the shortest path from the start to the end vertex.
        List<Integer> path = new ArrayList<Integer>();
        int curr = end;
        while (curr != start) {
            path.add(curr);
            curr = parent[curr];
        }
        path.add(start);
        Collections.reverse(path);

        return path;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        String[] values = input.split(" ");

        // Number of cities
        int n = Integer.parseInt(values[0]);
        // Number of connections
        int m = Integer.parseInt(values[1]);
        // Time required by airports to change their states
        int t = Integer.parseInt(values[2]);
        // The time for traveling one city to another
        int c = Integer.parseInt(values[3]);

        // Initialize the Graph
        Graph graph = new Graph(n);
        for (int i = 0; i < m; i++) {
            String edge = scanner.nextLine();

            // Bidirectional roads added as edges to the Graph
            int u = Integer.parseInt(edge.split(" ")[0]) - 1;
            int v = Integer.parseInt(edge.split(" ")[1]) - 1;
            graph.addEdge(u, v);
        }

        // Desired direction of the plane
        String direction = scanner.nextLine();
        int start = Integer.parseInt(direction.split(" ")[0]) - 1;
        int end = Integer.parseInt(direction.split(" ")[1]) - 1;

        scanner.close();

        // Running the BFS algorithm
        List<Integer> path = graph.shortestPath(start, end);

        // First output line to denote the number of cities traveled
        System.out.println(path.size());

        // Second output line to denote traveled cities in order
        for (int node : path) {
            System.out.print((node + 1) + " ");
        }

        // Third output line to denote total amount of time passed while traveling the
        // shortest path
        System.out.println();
        int totalFlight = (path.size() - 1) * c;
        int totalStateTime = (totalFlight % t) * (path.size() - 2);
        System.out.println(totalFlight + totalStateTime);
    }
}