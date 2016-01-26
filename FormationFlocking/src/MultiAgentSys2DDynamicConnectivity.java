/**
 * Created by yao on 11/24/2015.
 */
public class MultiAgentSys2DDynamicConnectivity extends MultiAgentSys2D {

    public MultiAgentSys2DDynamicConnectivity(int n, double[][] initialPos, double[][] initialSpd) {
        super(n, initialPos, initialSpd);

        int[][] adjMatrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) adjMatrix[i][j] =0;
                else {
                    agents[i].updateConnectivity(agents[j]);
                    if (agents[i].connected(agents[j])) adjMatrix[i][j] = 1;
                    else adjMatrix[i][j] = 0;
                }
            }
        }
        g = new Graph(adjMatrix);
    }

    public void setSensingRange(double r) {
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
    }
}
