package dev.thomaslienbacher.evolution;

import dev.thomaslienbacher.evolution.gui.Gui;
import dev.thomaslienbacher.evolution.utils.Utils;
import dev.thomaslienbacher.evolution.world.Robot;
import dev.thomaslienbacher.evolution.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created on 26.12.2018.
 *
 * @author Thomas Lienbacher
 */
public class Simulator {

    public enum State {
        INIT, READY, SIMULATING, RUNNING, REPRODUCING,
    }

    public static final int NUM_ANIMALS = 1000;
    public static final int NUM_ANIMALS_KILL = NUM_ANIMALS / 2;

    private State state;
    private World world;
    private Gui gui;

    private Thread runThread;

    public Simulator() {
        this.state = State.INIT;
        this.world = new World();
        this.gui = new Gui(this);
        this.state = State.READY;
    }

    public void start() {
        if(state != State.READY) return;

        world.startSimulation(NUM_ANIMALS);
        state = State.SIMULATING;
        gui.repaintWorld();
    }

    public void tick() {
        if(!isSimulating()) return;

        world.tick();

        if(world.generationFinished()) {
            state = State.REPRODUCING;
            gui.repaintWorld();
            showRobotsSorted();
            return;
        }

        gui.setInfoString(world.getBestRobot().toStringSmall() + "\n" + world.getCurrentRobot().toString());
        gui.repaintWorld();
    }

    public void startRunning(final int speed) {
        if(state != State.SIMULATING) return;
        state = State.RUNNING;

        final int waitTime = 100 - (Utils.clamp(speed, 1, 10) * 10);

        runThread = new Thread(() -> {
            try {
                while(true) {
                    if(state != State.RUNNING) break;

                    tick();

                    if(world.generationFinished()) {
                        reproduce();
                        state = State.RUNNING;
                        Thread.sleep(20);
                    }

                    Thread.sleep(waitTime);
                }
            } catch(InterruptedException e) {
                return;
            }
        });
        runThread.start();
    }

    public void stopRunning() {
        if(state != State.RUNNING) return;
        runThread.interrupt();
        state = State.SIMULATING;
    }

    public void finishGeneration() {
        if(!isSimulating()) return;

        while(!world.generationFinished()) {
            world.tick();
        }

        state = State.REPRODUCING;
        gui.repaintWorld();
        showRobotsSorted();
    }

    public void reproduce() {
        if(state != State.REPRODUCING) return;

        ArrayList<Robot> robots = world.getRobots();
        Collections.sort(robots);

        for(int i = NUM_ANIMALS_KILL; i < world.getNumRobots(); i++) {
            Robot mut = robots.get(i).mutate();
            robots.set(i, mut);
        }

        for(Robot r : robots) {
            r.reset();
        }

        world.setRobots(robots);
        world.restart();
    }

    public void reproduceAndRestart() {
        if(state != State.REPRODUCING) return;

        reproduce();
        state = State.SIMULATING;
        showRobotsSorted();
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

    public void showRobotsSorted() {
        StringBuilder sb = new StringBuilder();

        ArrayList<Robot> robots = world.getRobots();
        Collections.sort(robots);

        for(Robot r : world.getRobots()) {
            sb.append(r.toStringSmall());
            sb.append('\n');
        }

        gui.setInfoString(sb.toString());
    }
}
