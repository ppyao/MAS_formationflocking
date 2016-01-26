import java.util.HashSet;

/**
 * Created by yao on 11/28/2015.
 */
public class DynamicAgent2DState {
    private double x;
    private double y;
    private double vx;
    private double vy;

    public DynamicAgent2DState(double x, double y, double vx, double vy) {
        if (Double.isInfinite(x) || Double.isInfinite(y)) {
            throw new IllegalArgumentException("Coordinates must be finite");
        }
        if (Double.isNaN(x) || Double.isNaN(y)) {
            throw new IllegalArgumentException("Coordinates can not be NaN");
        }
        if (x == 0.0) this.x = 0.0; // convert -0.0 to +0.0
        else this.x = x;

        if (y == 0.0) this.y = 0.0; // convert -0.0 to +0.0
        else this.y = y;

        if (vx == 0.0) this.vx = 0.0; // convert -0.0 to +0.0
        else this.vx = vx;

        if (vy == 0.0) this.vy = 0.0; // convert -0.0 to +0.0
        else this.vy = vy;
    }

    public DynamicAgent2DState(double x, double y) {
        this(x, y, 0.0, 0.0);
    }

    public void applyControl(double[] force, double time) {
        vx += force[0]*time;
        vy += force[1]*time;
        x += vx*time;
        y += vy*time;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getVx() {
        return vx;
    }
    public double getVy() {
        return vy;
    }

}
