/**
 * Created by yao on 11/25/2015.
 */
public class MultiAgentSys2DFixedConnectivity extends MultiAgentSys2D {

    public MultiAgentSys2DFixedConnectivity(int n, Graph g, double[][] initialPos, double[][] initialSpd) {
        super(n, initialPos, initialSpd);
        this.g = g;
    }


}
