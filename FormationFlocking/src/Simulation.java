import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by yao on 11/16/2015.
 */
public class Simulation {

    private MultiAgentSys2D sys;
    private int totalStep;
    private ArrayList<HashMap<Integer, DynamicAgent2DState>> states;
    private double[] time;
    private Controller controller;

    public Simulation(int n, Graph g, double[][] initialPos, double[][] initialSpd, double startTime, double endTime, double timeStep){
        sys = new MultiAgentSys2DFixedConnectivity(n, g, initialPos, initialSpd);
        controller = new FlockingControl();

        totalStep = (int)((endTime - startTime)/ timeStep) +1;
        states = new ArrayList<HashMap<Integer, DynamicAgent2DState>>();
        time = new double[totalStep];

        states.add(sys.getInitialStates());
        time[0] = startTime;

        for (int step = 1; step < totalStep; step++) {
            time[step] = startTime + step * timeStep;
            double[][] force = controller.generateControl(sys);

            sys.applyControl(force, timeStep);
            states.add(step, sys.getStates());
        }
    }

    public Simulation(int n, double[][] initialPos, double[][] initialSpd, double startTime, double endTime, double timeStep){
        sys = new MultiAgentSys2D(n, initialPos, initialSpd);
        controller = new FlockingControl();

        totalStep = (int)((endTime - startTime)/ timeStep) +1;
        //states = new double[totalStep][n][4];
        time = new double[totalStep];
        states = new ArrayList<HashMap<Integer, DynamicAgent2DState>>();
        states.add(sys.getInitialStates());
        time[0] = startTime;

        for (int step = 1; step < totalStep; step++) {
            time[step] = startTime + step * timeStep;
            double[][] force = controller.generateControl(sys);

            sys.applyControl(force, timeStep);
            states.add(step, sys.getStates());
        }
    }

    public HashMap<Integer, DynamicAgent2DState> getInitialState(int i) {
        return states.get(0);
    }

    public DynamicAgent2DState[] getAgentState(int i) {
        DynamicAgent2DState[] statei = new DynamicAgent2DState[totalStep];
        for (int step = 0; step < totalStep; step++) {
            statei[step] = states.get(step).get(i);
        }
        return statei;
    }

    public double[][] getAgentPos(int i) {
        double[][] pos = new double[totalStep][2];
        for (int step = 0; step < totalStep; step++) {
            pos[step][0] = states.get(step).get(i).getX();
            pos[step][1] = states.get(step).get(i).getY();
        }
        return pos;
    }

    public double[][] getAgentSpd(int i) {
        double[][] spd = new double[totalStep][2];
        for (int step = 0; step < totalStep; step++) {
            spd[step][0] = states.get(step).get(i).getVx();
            spd[step][1] = states.get(step).get(i).getVy();
        }
        return spd;
    }

    public double[] getInstantPosition(int i, int step) {
        return getAgentPos(i)[step];
    }

    public double[] getInstantSpd(int i, int step) {
        return getAgentSpd(i)[step];
    }

    public static void main(String[] args) {
        int[][] adjMatrix = {{0, 1, 0, 0, 1},
                {1, 0, 1, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 1, 0, 1},
                {1, 0, 0, 1, 0}};
        Graph g = new Graph(adjMatrix);
        int n = 5;
        double startTime = 0.0;
        double endTime = 5;
        double timeStep = 0.01;

        double[][] initialPos = new double[n][2];
        double[][] initialSpd = new double[n][2];

        Random generator = new Random();
        generator.setSeed(1234);

        for (int i = 0; i < n; i++) {
            initialPos[i][0] = 0;
            initialPos[i][1] = 0;
            initialSpd[i][0] = generator.nextDouble()*5;
            initialSpd[i][1] = generator.nextDouble()*5;
        }
        Simulation s = new Simulation(n, g, initialPos, initialSpd, startTime, endTime, timeStep);
        //Simulation s = new Simulation(n, initialPos, initialSpd, startTime, endTime, timeStep);

        /*double[][] state = s.getAgentState(0);
        System.out.println(s.totalStep);
        for (int step = 0; step< s.states.length; step++) {
            System.out.println(s.states[step][0][0] + " " + s.states[step][0][1]);
        }*/


        // write result to file
        int i = (int) Math.random()*n;
        int j = (int) Math.random()*n;
        DynamicAgent2DState[] statei = s.getAgentState(i);
        DynamicAgent2DState[] statej = s.getAgentState(j);
        try {
            File file = new File("C:/Users/yao/Documents/Job/PastWork/FormationFlocking/src/agentStates1.txt");
            FileOutputStream is = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            w.write(String.format("The position of agent %d:\n", i));
            for (int line = 0; line < statei.length; line++) {
                w.write(statei[line].getX() + " " + statei[line].getY() + "\n");
            }
            w.write(String.format("\nThe velocity of agent %d:\n", i));
            for (int line = 0; line < statei.length; line++) {
                w.write(statei[line].getVx() + " " + statei[line].getVy() + "\n");
            }

            w.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file agentStates1.txt");
        }

        try {
            File file = new File("C:/Users/yao/Documents/Job/PastWork/FormationFlocking/src/agentStates2.txt");
            FileOutputStream is = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            w.write(String.format("The position of agent %d:\n", j));
            for (int line = 0; line < statej.length; line++) {
                w.write(statej[line].getX() + " " + statej[line].getY() + "\n");
            }
            w.write(String.format("\nThe velocity of agent %d:\n", j));
            for (int line = 0; line < statej.length; line++) {
                w.write(statej[line].getVx() + " " + statej[line].getX() + "\n");
            }

            w.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file agentStates2.txt");
        }

    }
}
