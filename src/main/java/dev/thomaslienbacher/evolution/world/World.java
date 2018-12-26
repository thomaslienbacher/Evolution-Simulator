package dev.thomaslienbacher.evolution.world;

import dev.thomaslienbacher.evolution.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;

public class World {

    public static final int WIDTH = 40, HEIGHT = 40;

    public int width, height;
    public ArrayList<Animal> animals;
    private byte[][] originalFood;
    private byte[][] food;
    private int numAnimals;
    private int currentAnimal;
    private State state;

    public World(int width, int height) {
        this.width = width;
        this.height = height;

        food = new byte[width][height];
        originalFood = new byte[width][height];
        animals = new ArrayList<>();
        state = State.READY;
    }

    private void placeFood(int numFood) {
        for (int i = 0; i < numFood; i++) {
            int x = Utils.randInt(width);
            int y = Utils.randInt(height);

            while (food[x][y] > 0) {
                x = Utils.randInt(width);
                y = Utils.randInt(height);
            }

            food[x][y] = 1;
        }

        originalFood = Arrays.copyOf(food, food.length);
    }

    public void startSimulation(int numAnimals) {
        if (state != State.READY) return;

        state = State.SIMULATING;
        this.numAnimals = numAnimals;

        for (int i = 0; i < numAnimals; i++) {
            animals.add(new Animal(WIDTH / 2, HEIGHT / 2));
        }

        placeFood(40);
        currentAnimal = 0;
    }

    public void tick() {
        if (state != State.SIMULATING) return;

        Animal animal = getCurrentAnimal();
        animal.tick(this);
        if (animal.isDead()) {
            currentAnimal++;
            food = Arrays.copyOf(originalFood, originalFood.length);
        }

        if (currentAnimal > numAnimals) state = State.REPRODUCING;
    }

    public byte getFood(int x, int y) {
        return food[Utils.wrap(x, width)][Utils.wrap(y, height)];
    }

    public void setFood(int x, int y, byte value) {
        food[Utils.wrap(x, width)][Utils.wrap(y, height)] = value;
    }

    public Animal getCurrentAnimal() {
        return animals.get(currentAnimal);
    }

    public State getState() {
        return state;
    }

    public enum State {
        READY, SIMULATING, REPRODUCING, RESTARING
    }
}
