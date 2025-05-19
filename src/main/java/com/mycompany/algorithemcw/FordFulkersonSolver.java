// Parham Golmohammadi
//w2046966

package com.mycompany.algorithemcw;

import static com.mycompany.algorithemcw.NetworkFlowSolverBase.INF;
import static java.lang.Math.min;
import java.util.List;

// Standard Ford-Fulkerson implementation
// Uses DFS to find augmenting paths

public class FordFulkersonSolver extends NetworkFlowSolverBase {

    public FordFulkersonSolver(int[][] capacityMatrix, int source, int sink) {
        super(capacityMatrix.length, source, sink);
        
         for (int i = 0; i < capacityMatrix.length; i++) {
        for (int j = 0; j < capacityMatrix[i].length; j++) {
            if (capacityMatrix[i][j] > 0) {
                addEdge(i, j, capacityMatrix[i][j]);
            }
        }
    }

    }

    @Override
    public void solve() {
        // keep finding paths and adding their flows
    // f will be 0 when no more paths can be found
        for (long f = dfs(s, INF); f != 0; f = dfs(s, INF)) {
            visitedToken++; // mark all nodes as unvisited for next search
            maxFlow += f; // add to total flow
        }
        
    }

    private long dfs(int node, long flow) {
         // if we reached the sink, we found a path!
        if (node == t) {
            return flow;
        }

        List<Edge> edges = graph[node];
        visit(node); // mark this node as visited!

        // try all edges from this node
        for (Edge edge : edges) {
            long rcap = edge.remainingCapacity();
            if (rcap > 0 && !visited(edge.to)) {
                // this edge works - try to complete the path from next node
                long bottleNeck = dfs(edge.to, min(flow, rcap));

                // if we found a complete path, update the flow and return
                if (bottleNeck > 0) {
                    edge.augment(bottleNeck);
                    return bottleNeck;
                }
            }
        }
         // no path found from this node
        return 0;
    }

}
