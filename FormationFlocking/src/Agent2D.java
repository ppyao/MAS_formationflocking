import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by yao on 11/16/2015.
 *
 * public class Agent2D:
 * Agent2D(double x, double y, double vx, double vy)  create an agent with given initial position and initial speed
 * public void applyControl(double[] force)
 *
 */
public class Agent2D {

    private DynamicAgent2DState state;
    private double sensingRange;
    private HashSet<Agent2D> neighbors;

    public Agent2D(DynamicAgent2DState state) {
        this.state = state;
        sensingRange = 99.0;
        neighbors = new HashSet<>();
    }

    public Agent2D(double x, double y) {
        this(x, y, 0.0, 0.0);
    }

    public Agent2D(double x, double y, double vx, double vy) {

        state = new DynamicAgent2DState(x, y, vx, vy);
        sensingRange = 99.0;
        neighbors = new HashSet<>();
    }

    public void setSensingRange(double r) {
        sensingRange = r;
    }

    public void applyControl(double[] force, double time) {
        state.applyControl(force, time);
    }

    public DynamicAgent2DState getState() {
        return state;
    }

    /**
     * Returns the x-coordinate.
     * @return the x-coordinate
     */
    public double getX() {
        return state.getX();
    }

    /**
     * Returns the y-coordinate.
     * @return the y-coordinate
     */
    public double getY() {
        return state.getY();
    }

    /**
     * Returns the velocity in x-coordinate.
     * @return the velocity in x-coordinate
     */
    public double getVx() {
        return state.getVx();
    }

    /**
     * Returns the velocity in y-coordinate.
     * @return the velocity in y-coordinate
     */
    public double getVy() {
        return state.getVy();
    }

    public void updateConnectivity(Agent2D that) {
        if (distanceTo(that) <= sensingRange) {
            neighbors.add(that);
        }
        else {
            neighbors.remove(that);
        }
    }

    public boolean connected(Agent2D that) {
        return neighbors.contains(that);
    }

    /*
    returns the distance in x direction between this agent and that agent.
    @return the distance in x direction between this agent and that agent.
    */
    public double getXDistanceTo(Agent2D that) {
        double dx = that.state.getX() - this.state.getX();
        return dx;
    }

    /*
    returns the distance in y direction between this agent and that agent.
    @return the distance in y direction between this agent and that agent.
    */
    public double getYDistanceTo(Agent2D that) {
        double dy = that.state.getY() - this.state.getY();
        return dy;
    }

    /**
     * Returns the Euclidean distance between this agent and that agent.
     * @param that the other agent
     * @return the Euclidean distance between this agent and that agent
     */
    public double distanceTo(Agent2D that) {
        return Math.sqrt(distanceSquaredTo(that));
    }

    /**
     * Returns the square of the Euclidean distance between this agent and that agent.
     * @param that the other agent
     * @return the square of the Euclidean distance between this agent and that agent
     */
    public double distanceSquaredTo(Agent2D that) {
        double dx = getXDistanceTo(that);
        double dy = getYDistanceTo(that);
        return dx*dx + dy*dy;
    }

    /**
     * Return a string representation of this agent.
     * @return a string representation of this agent in the format (x, y)
     */
    @Override
    public String toString() {
        return "(" + state.getX() + ", " + state.getY() + ")";
    }

    public static void main(String[] args) {

        /*StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.line(2000, 1000, 12000, 1000);
        StdDraw.show();*/
    }
}
