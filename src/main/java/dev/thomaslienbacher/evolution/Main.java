package dev.thomaslienbacher.evolution;

import dev.thomaslienbacher.evolution.gui.Gui;
import dev.thomaslienbacher.evolution.world.World;

import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        World world = new World(World.WIDTH, World.HEIGHT);
        Gui gui = new Gui(world);
        gui.repaintWorld();
        world.startSimulation(100);

        while (true) {
            world.tick();
            gui.repaintWorld();

            if (world.getState() == World.State.REPRODUCING) break;

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(world.animals);
        System.out.println(world.animals);
    }
}
