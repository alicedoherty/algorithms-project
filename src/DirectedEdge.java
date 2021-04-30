/*
	@author Robert Sedgewick
	@author Kevin Wayne
*/

public class DirectedEdge { 
    private final int v;
    private final int w;
    private final double weight;

    /**
     * Initializes a directed edge from vertex v to vertex w with
     * the given weight.
     * @param v the tail vertex
     * @param w the head vertex
     * @param weight the weight of the directed edge
     */
    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    /**
     * Returns the tail vertex of the directed edge.
     * @return the tail vertex of the directed edge
     */
    public int from() {
        return v;
    }

    /**
     * Returns the head vertex of the directed edge.
     * @return the head vertex of the directed edge
     */
    public int to() {
        return w;
    }

    /**
     * Returns the weight of the directed edge.
     * @return the weight of the directed edge
     */
    public double weight() {
        return weight;
    }
}
