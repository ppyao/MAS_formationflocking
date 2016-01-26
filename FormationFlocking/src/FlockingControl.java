import java.util.HashMap;

/**
 * Created by yao on 11/29/2015.
 */
public class FlockingControl implements Controller {

    public double[][] generateControl(MultiAgentSys2D sys) {
        int n = sys.getNumberOfAgents();
        double[][] force = new double[n][2];

        HashMap<Integer, DynamicAgent2DState> currStates = sys.getStates();
        for (int i = 0; i < n; i++) {
            force[i][0] = 0.0;
            force[i][1] = 0.0;
            for (int j = 0; j < n; j++) {
                if (sys.connected(i, j)) {
                    force[i][0] -= currStates.get(i).getVx() - currStates.get(j).getVx();
                    force[i][1] -= currStates.get(i).getVy() - currStates.get(j).getVy();
                }
            }
        }
        return force;
    }
}
