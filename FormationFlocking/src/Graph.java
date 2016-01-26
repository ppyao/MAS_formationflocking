import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yao on 11/8/2015.
 */
public class Graph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private int V;
    private int E;
    private int[][] adj;

    /**
     * Initializes an empty graph with V vertices and 0 edges.
     * param V the number of vertices
     *
     * @param  V number of vertices
     * @throws IllegalArgumentException if V < 0
     */
    public Graph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = new int[V][V];
        for (int v = 0; v < V; v++) {
            for (int w = 0; w < V; w++) {
                adj[v][w] = 0;
            }
        }
    }

    public Graph(int[][] adj) {
        this.V = adj.length;
        int count = 0;
        this.adj = new int[V][V];
        for (int v = 0; v < V; v++) {
            for (int w = 0; w < V; w++) {
                if (adj[v][w] == 1) {
                    this.adj[v][w] = 1;
                    count++;
                }
                else this.adj[v][w] = 0;
            }
        }
        this.E = count/2;
    }

    public Graph(Graph G) {
        this.V = G.V();
        this.E = G.E();
        this.adj = new int[G.adj.length][G.adj[0].length];
        for (int v = 0; v < V; v++) {
            for (int w = 0; w < V; w++) {
                this.adj[v][w] = G.adj[v][w];
            }
        }
    }

    // throw an IndexOutOfBoundsException unless 0 <= v < V
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * Adds the undirected edge v-w to this graph.
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     * @throws IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        E++;
        adj[v][w] = 1;
        adj[w][v] = 1;
    }

    public void breakEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        E--;
        adj[v][w] = 0;
        adj[w][v] = 0;
    }

    /**
     * Returns the vertices adjacent to vertex.
     *
     * @param  v the vertex
     * @return the vertices adjacent to vertex v, as a List
     * @throws IndexOutOfBoundsException unless 0 <= v < V
     */
    public ArrayList<Integer> adj(int v) {
        validateVertex(v);
        ArrayList<Integer> adjList = new ArrayList<>();
        for (int w = 0; w < this.V; w++) {
            if (adj[v][w] == 1) adjList.add(w);
        }
        return adjList;
    }

    public boolean connected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return adj[v][w] == 1;
    }

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph
     */
    public int V(){
        return V;
    }

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */
    public int E() {
        return E;
    }

    /**
     * Returns the degree of vertex v.
     *
     * @param  v the vertex
     * @return the degree of vertex v
     * @throws IndexOutOfBoundsException unless 0 <= v < V
     */
    public int degree(int v) {
        validateVertex(v);
        return adj(v).size();
    }

    public String edges() {
        StringBuilder sb = new StringBuilder();
        sb.append(E + " edges:" + NEWLINE);
        for (int  v= 0; v < V(); v++) {
            for (int w: adj(v)) {
                sb.append( v + " - " + w + NEWLINE);
            }
        }
        return sb.toString();
    }

    public int[][] getAdjacencyMatrix() {
        return Arrays.copyOf(adj, adj.length);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < this.V; v++) {
            sb.append(v + ": ");
            for (int w : adj(v)) {
                sb.append(w + " ");
            }
            sb.append(NEWLINE);
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        int[][] adjMatrix = {{0, 1, 0, 0, 1},
                {1, 0, 1, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 1, 0, 1},
                {1, 0, 0, 1, 0}};
        Graph G = new Graph(adjMatrix);

        System.out.println(G);
        System.out.println(G.edges());
    }

}
