package dev.thomaslienbacher.evolution;

import dev.thomaslienbacher.evolution.gui.Gui;
import dev.thomaslienbacher.evolution.utils.Utils;
import dev.thomaslienbacher.evolution.world.World;

import java.awt.*;

/**
 * Created on 26.12.2018.
 *
 * @author Thomas Lienbacher
 */
public class Simulator {

    public enum State {
        INIT, READY, SIMULATING, RUNNING, REPRODUCING, RESTARTING
    }

    public static final int NUM_ANIMALS = 100;

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
            return;
        }

        gui.setInfoString(world.getCurrentRobot().toString());
        gui.repaintWorld();
    }

    public void startRunning(int speed) {
        if(state != State.SIMULATING) return;
        state = State.RUNNING;

        final int waitTime = 110 - (Utils.clamp(speed, 1, 10) * 10);

        runThread = new Thread(() -> {
            while(true) {
                this.tick();

                try {
                    Thread.sleep(waitTime);
                } catch(InterruptedException e) {
                    return;
                }
            }
        });
        runThread.start();
    }

    public void stopRunning() {
        if(state != State.RUNNING) return;
        runThread.interrupt();
        state = State.SIMULATING;
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
}
