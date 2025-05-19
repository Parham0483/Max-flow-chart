// Parham Golmohammadi
//w2046966

package com.mycompany.algorithemcw;

import static java.lang.Math.min;
import java.util.ArrayDeque;
import java.util.Queue;


// This is the Edmonds-Karp algorithm for max flow
// It's basically Ford-Fulkerson but uses BFS to find paths
// BFS finds the shortest paths which makes the algorithm faster
 class EdmondsKarpSolver extends NetworkFlowSolverBase {

    public EdmondsKarpSolver(int n, int source, int sink) {
    super(n, source, sink);
    }

    @Override
    public void solve() {
        long flow;
        do {
            markAllNodesAsUnvisited();
            flow = bfs(); // try to find an augmenting path
            maxFlow += flow;  // add the flow from this path
        } while (flow != 0);  // keep going until no more paths
    }

    private long bfs() {
        Edge[] prev = new Edge[n]; // keep track of how we got to each node

        // The queue can be optimized to use a faster queue
        Queue<Integer> q = new ArrayDeque<>(n);
        visit(s); // mark source as visited
        q.offer(s); // add source to queue

        // Perform BFS from source to sink
        while (!q.isEmpty()) {
            int node = q.poll();
            if (node == t) {
                break; // found the sink, we're done!
            }

            // check all edges from this node
            for (Edge edge : graph[node]) {
                long cap = edge.remainingCapacity();
                if (cap > 0 && !visited(edge.to)) {
                    // this edge has room and leads to unvisited node
                    visit(edge.to);
                    prev[edge.to] = edge;  // remember how we got here
                    q.offer(edge.to);
                }
            }
        }

         // if we didn't reach the sink, no path exists
        if (prev[t] == null) {
            return 0;
        }

        // find the smallest capacity along the path - that's our bottleneck
        long bottleNeck = Long.MAX_VALUE;
        // Find augmented path and bottle neck
        for (Edge edge = prev[t]; edge != null; edge = prev[edge.from]) {
            bottleNeck = min(bottleNeck, edge.remainingCapacity());
        }

       // now go through the path again and update all the flows
        for (Edge edge = prev[t]; edge != null; edge = prev[edge.from]) {
            edge.augment(bottleNeck);
        }

        // this is how much we improved the flow
        return bottleNeck;
    }

}
