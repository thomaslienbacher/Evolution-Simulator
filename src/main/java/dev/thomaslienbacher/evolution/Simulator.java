package dev.thomaslienbacher.evolution;

import dev.thomaslienbacher.evolution.gui.Gui;
import dev.thomaslienbacher.evolution.utils.Utils;
import dev.thomaslienbacher.evolution.world.Robot;
import dev.thomaslienbacher.evolution.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class Simulator {

    public static final int NUM_ANIMALS = 100_000;
    public static final double PERCENT_KILL = 0.5;
    private State state;
    private World world;
    private Gui gui;
    private Thread runThread;
    private int cycle = 0;

    public Simulator() {
        this.state = State.INIT;
        this.world = new World();
        this.gui = new Gui(this);
        this.state = State.READY;
    }

    public void start() {
        if (state != State.READY) return;

        world.startSimulation(NUM_ANIMALS);
        state = State.SIMULATING;
        gui.repaintWorld();
    }

    public void tick() {
        if (!isSimulating()) return;

        world.tick();

        if (world.generationFinished()) {
            state = State.REPRODUCING;
            gui.repaintWorld();
            showRobotsSorted(Robot::toStringSmall);
            return;
        }

        gui.setInfoString("At cycle: " + cycle + "\n" +
                "At: " + world.atRobot() + " of " + world.getNumRobots() + "\n" +
                "Best: " + world.getBestRobot().toStringSmall() + "\n" +
                "Current: " + world.getCurrentRobot().toString());
        gui.repaintWorld();
    }

    public void startRunning(final int speed) {
        if (state != State.SIMULATING) return;
        state = State.RUNNING;

        final int waitTime = 100 - (Utils.clamp(speed, 1, 10) * 10);

        runThread = new Thread(() -> {
            while (true) {
                if (state != State.RUNNING) break;

                tick();

                if (world.generationFinished()) {
                    reproduce();
                    state = State.RUNNING;
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        break;
                    }
                }

                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        runThread.setName("Run-Thread");
        runThread.start();
    }

    public void stopRunning() {
        if(runThread == null) return;

        if (runThread.isAlive()) {
            runThread.interrupt();
        }

        if (state != State.RUNNING) return;
        runThread.interrupt();
        state = State.SIMULATING;
    }

    public void finishGeneration() {
        if (!isSimulating()) {
            if (state == State.REPRODUCING) {
                reproduceAndRestart();
                gui.repaintWorld();
                showRobotsSorted(Robot::toStringSmall);
            } else return;
        }

        while (!world.generationFinished()) {
            world.tick();
        }

        state = State.REPRODUCING;
        gui.repaintWorld();
        showRobotsSorted(Robot::toStringSmall);
    }

    public void infiniteSimulation() {
        if(state != State.SIMULATING) return;
        int lastFitness = 0;

        while (true) {
            if (!isSimulating()) {
                if (state == State.REPRODUCING) {
                    reproduce();
                    state = State.SIMULATING;
                } else return;
            }

            while (!world.generationFinished()) {
                world.tick();
            }

            state = State.REPRODUCING;
            Robot r = world.getBestRobot();

            if (r.getFitness() > lastFitness) {
                lastFitness = r.getFitness();
                System.out.println(r.toStringSmall());
            }
        }
    }

    private void reproduce() {
        if (state != State.REPRODUCING) return;

        ArrayList<Robot> robots = world.getRobots();
        Collections.sort(robots);

        Set<Robot> set = new HashSet<>(robots.size());
        int start = (int) Math.floor((1.0 - PERCENT_KILL) * robots.size());

        for (int i = 0; i < start; i++) {
            set.add(robots.get(i));
        }

        for (int i = start; set.size() < robots.size(); i++) {
            Robot mut = robots.get(i % start).mutate();
            set.add(mut);
        }

        robots = new ArrayList<>(set);
        Collections.sort(robots);

        for (Robot r : robots) {
            r.reset();
        }

        world.setRobots(robots);
        world.restart();
        cycle++;
    }

    public void reproduceAndRestart() {
        if (state != State.REPRODUCING) return;

        reproduce();
        state = State.SIMULATING;
        showRobotsSorted(Robot::toStringData);
        gui.repaintWorld();
    }

    public boolean isReady() {
        return state != State.INIT;
    }

    public boolean isSimulating() {
        return state == State.SIMULATING || state == State.RUNNING;
    }

    public void renderWorld(Graphics2D g) {
        world.render(g);
    }

    public String stateName() {
        return state.toString();
    }

    public void showRobotsSorted(Function<Robot, String> fn) {
        StringBuilder sb = new StringBuilder();

        ArrayList<Robot> robots = world.getRobots();
        Collections.sort(robots);

        for (Robot r : robots) {
            sb.append(fn.apply(r));
            sb.append('\n');
        }

        gui.setInfoString(sb.toString());
    }

    public enum State {
        INIT, READY, SIMULATING, RUNNING, REPRODUCING,
    }
}
