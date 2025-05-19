// Parham Golmohammadi
//w2046966

package com.mycompany.algorithemcw;


//  This class represents an edge in our flow network
//  Each edge keeps track of where it starts, where it ends, and how much flow it can handle

// Constructor 
public  class Edge {

        public int from, to;
        public Edge residual;
        public long flow;
        public final long capacity;

        // Checks if this is a backwards edge (residual edges have 0 capacity)
        public Edge(int from, int to, long capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
        }

        public boolean isResidual() {
            return capacity == 0;
        }

        // Figures out how much more flow we can push through the edge
        public long remainingCapacity() {
            return capacity - flow;
        }

        // Updates the flow on this edge and its residual when we find a path
        public void augment(long minCapacityAlongPath) {
            flow += minCapacityAlongPath;
            residual.flow -= minCapacityAlongPath;
        }

    @Override
    public String toString() {
        return "Edge{" + "from=" + from + ", to=" + to + ", residual=" + residual + ", flow=" + flow + ", capacity=" + capacity + '}';
    }
        

   
}
