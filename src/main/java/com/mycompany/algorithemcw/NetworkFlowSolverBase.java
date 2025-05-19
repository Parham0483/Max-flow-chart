// Parham Golmohammadi
//w2046966

package com.mycompany.algorithemcw;

import com.mycompany.algorithemcw.Edge;
import java.util.ArrayList;
import java.util.List;

// Common stuff that both flow algorithms need
public  abstract class NetworkFlowSolverBase {

    // basically infinity but not quite, to avoid overflow issues
        static final long INF = Long.MAX_VALUE / 2;

        final int n, s, t;

        // keeps track of which nodes we've seen during path finding
        // we increment the token instead of resetting the whole array - way faster
        protected int visitedToken = 1;
        protected int[] visited;

        protected boolean solved;
        protected long maxFlow;
        protected List<Edge>[] graph;

        public NetworkFlowSolverBase(int n, int s, int t) {
            this.n = n;
            this.s = s;
            this.t = t;

            initializeEmptyFlowGraph();
            visited = new int[n];
        }

        public void initializeEmptyFlowGraph() {
            graph = new List[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new ArrayList<Edge>();
            }
        }
        
        // Add a directed edge to the graph with its backward/residual edge
        public void addEdge(int from, int to, long capacity) {
            if (capacity <= 0) {
                throw new IllegalArgumentException("Forward edge capacity <= 0");
            }

             // make the forward edge
            Edge e1 = new Edge(from, to, capacity);
             // make the backward edge with 0 capacity 
            Edge e2 = new Edge(to, from, 0); 
            // link them to each other
            e1.residual = e2;
            e2.residual = e1;
            // add them to our graph
            graph[from].add(e1);
            graph[to].add(e2);
       }
    // DELETE: Removing edges

    public void removeEdge(int from, int to) {
        graph[from].removeIf(e -> e.to == to && !e.isResidual());
    }


        public List<Edge>[] getGraph() {
        execute();
        return graph;
    }

    public long getMaxFlow() {
        execute();
        return maxFlow;
    }
    
   // helper function to mark a node as visited
    public void visit(int i) {
        visited[i] = visitedToken;
    }
    
   // has node i been visited in the current search?
    public boolean visited(int i) {
        return visited[i] == visitedToken;
    }
    
    // reset all node as unvisited when trying to find augmenting path , 0(1)
    // faster way to mark all nodes as not visited
    public void markAllNodesAsUnvisited() {
        visitedToken++;
    }

    private void execute() {
        if (solved) {
            return;
        }
        solved = true;
        solve();
    }

    public abstract void solve();

}
