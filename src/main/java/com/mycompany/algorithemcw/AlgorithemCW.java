
// Parham Golmohammadi
//w2046966

package com.mycompany.algorithemcw;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class AlgorithemCW {

    // prints the flow for each edge in the graph 
    public static void printGraphFlow(NetworkFlowSolverBase solver) {
        List<Edge>[] resultGraph = solver.getGraph();
        for (List<Edge> edges : resultGraph) {
            for (Edge edge : edges) {
                 // only print forward edges, not residual
                if (!edge.isResidual()) {
                    System.out.printf("Edge %d -> %d | flow = %d / %d\n",
                            edge.from, edge.to, edge.flow, edge.capacity);
                }
            }
        }
        System.out.println();
    }

    // reads a graph from a file in the format specified in the coursework
    public static EdmondsKarpSolver readGraphFromFile(String filename) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(filename));
    int n = Integer.parseInt(br.readLine().trim());

     // assuming first node is source, last is sink
    EdmondsKarpSolver solver = new EdmondsKarpSolver(n, 0, n - 1); // Assuming source=0, sink=n-1

    String line;
    while ((line = br.readLine()) != null) {
        if (line.trim().isEmpty()) continue;

        // each line has format: from to capacity
        String[] parts = line.trim().split("\\s+");
        int from = Integer.parseInt(parts[0]);
        int to = Integer.parseInt(parts[1]);
        int cap = Integer.parseInt(parts[2]);

        solver.addEdge(from, to, cap);
    }

    br.close();
    return solver;
}

    public static void main(String[] args) throws IOException {
        String filename = "benchmarks/ladder_15.txt";

        EdmondsKarpSolver solver = readGraphFromFile(filename);

        System.out.println("Using Edmonds-Karp Algorithm for: " + filename);
        solver.solve();
        System.out.printf("Maximum Flow: %d\n", solver.getMaxFlow());
        printGraphFlow(solver);

    }
}
