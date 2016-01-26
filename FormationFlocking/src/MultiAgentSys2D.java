import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by yao on 11/23/2015.
 */
public class MultiAgentSys2D {
    public int n;
    public Agent2D[] agents;
    public Graph g;

    public HashMap<Integer, DynamicAgent2DState> initialStates;
    //public double[][] states;
    public HashMap<Integer, DynamicAgent2DState> states;

    public MultiAgentSys2D(int n, double[][] initialPos, double[][] initialSpd) {
        this.n = n;
        agents = new Agent2D[n];
        //g = new Graph(n);

        initialStates = new HashMap<>();
        states = new HashMap<>();

        for (int i = 0; i < n; i++) {
            DynamicAgent2DState state = new DynamicAgent2DState(initialPos[i][0], initialPos[i][1], initialSpd[i][0], initialSpd[i][1]);
            agents[i] = new Agent2D(state);
            initialStates.put(i, state);
            //states[i] = initialStates[i];
            states.put(i, state);
        }
    }

    public void applyControl(double[][] force, double time) {
        for (int i = 0; i < n; i++) {
            agents[i].applyControl(force[i], time);
            states.put(i, agents[i].getState());
        }
    }

    /*public void setSensingRange(double r) {
        for (int i = 0; i < n; i++) {
            agents[i].setSensingRange(r);
        }
    }

    public void updateConnectivity() {
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                boolean connection = agents[i].connected(agents[j]);
                agents[i].updateConnectivity(agents[j]);
                if (!connection && agents[i].connected(agents[j])) {
                    g.addEdge(i, j);
                }
                else if (connection && !agents[i].connected(agents[j])) {
                    g.breakEdge(i, j);
                }
            }
        }
    }*/

    public Graph getConnectivityGraph() {return g;}

    public int[][] getAdjacencyMatrix() {
        return g.getAdjacencyMatrix();
    }

    public HashMap<Integer, DynamicAgent2DState> getInitialStates() {
        return initialStates;
    }

    public HashMap<Integer, DynamicAgent2DState> getStates() {
        return states;
    }

    public int getNumberOfAgents() {
        return n;
    }

    public boolean connected(int i, int j){
        return g.connected(i,j);
    }

    public double distance(int i, int j) {
        return agents[i].distanceTo(agents[j]);
    }

    public static void main(String[] args) {
        int n = 5;
        double[][] initialPos = new double[n][2];
        double[][] initialSpd = new double[n][2];

        Random generator = new Random();
        generator.setSeed(1234);

        for (int i = 0; i < n; i++) {
            initialPos[i][0] = generator.nextDouble()*10;
            initialPos[i][1] = generator.nextDouble()*10;
            initialSpd[i][0] = generator.nextDouble()*5;
            initialSpd[i][1] = generator.nextDouble()*5;
        }

        MultiAgentSys2DDynamicConnectivity sysDynamic = new MultiAgentSys2DDynamicConnectivity(n, initialPos,initialSpd);

        // expected to be all connected
        int[][] adj = sysDynamic.getAdjacencyMatrix();
        System.out.println("The adjacency matrix:");
        for (int i = 0; i < adj.length; i++) {
            System.out.println(Arrays.toString(adj[i]));
        }

        // set sensing range
        double r = 7.0;
        sysDynamic.setSensingRange(r);

        // update connectivity, lose some connections
        sysDynamic.updateConnectivity();
        adj = sysDynamic.getAdjacencyMatrix();
        System.out.println("The adjacency matrix:");
        for (int i = 0; i < adj.length; i++) {
            System.out.println(Arrays.toString(adj[i]));
        }

        // print out new edges:
        Graph g = sysDynamic.getConnectivityGraph();
        System.out.printf("There are %d edges:\n", g.E());
        System.out.println(g.edges());

        // print out distance of each pair:
        for (int i = 0; i < n; i++) {
            for (int j = i +1; j < n; j++) {
                System.out.printf("The distance between agent %d and agent %d are: %.6f \n", i,j,sysDynamic.distance(i,j));
            }
        }

    }
}
