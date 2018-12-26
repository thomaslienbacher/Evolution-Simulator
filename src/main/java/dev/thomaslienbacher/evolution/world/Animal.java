package dev.thomaslienbacher.evolution.world;

import dev.thomaslienbacher.evolution.utils.Utils;

import java.util.Arrays;

public class Animal implements Comparable<Animal> {
    public static final int MAX_MOVES = 6;
    public static final int MIN_MOVES = 2;
    public static final int START_ENERGY = 20;
    public static final int GAIN_FOOD = 10;

    public int x, y;
    public int fitness = 0;
    public int energy = START_ENERGY;
    private byte[] moves; //clockwise 0-7
    private int state = 0;

    /**
     * Creates a new randomized animal
     *
     * @param x x pos of animal
     * @param y y pos of animal
     */
    public Animal(int x, int y) {
        this.x = x;
        this.y = y;
        this.moves = new byte[Utils.randIntIncl(MIN_MOVES, MAX_MOVES)];

        for (int i = 0; i < this.moves.length; i++) {
            this.moves[i] = (byte) Utils.randIntIncl(0, 7);
        }
    }

    public void tick(World world) {
        if (state < moves.length) {
            switch (moves[state]) {
                case 0: {
                    y++;
                    break;
                }
                case 1: {
                    y++;
                    x++;
                    break;
                }
                case 2: {
                    x++;
                    break;
                }
                case 3: {
                    y--;
                    x++;
                    break;
                }
                case 4: {
                    y--;
                    break;
                }
                case 5: {
                    x--;
                    y--;
                    break;
                }
                case 6: {
                    x--;
                    break;
                }
                case 7: {
                    x--;
                    y++;
                    break;
                }
            }

            x = Utils.wrap(x, world.width);
            y = Utils.wrap(y, world.height);

            state++;
        } else {
            for (int y = -1; y <= 1; y++) {
                for (int x = -1; x <= 1; x++) {
                    if (world.getFood(this.x + x, this.y + y) > 0) {
                        fitness++;
                        energy += GAIN_FOOD;
                        world.setFood(this.x + x, this.y + y, (byte) 0);
                    }
                }
            }

            state = 0;
        }

        energy--;
    }

    public boolean isDead() {
        return energy < 0;
    }

    public boolean isScanning() {
        return state >= moves.length;
    }

    /**
     * Creates a new animal based on this one
     * and mutates it
     *
     * @param mutability percent on how much to mutate
     * @return new mutated animal
     */
    public Animal reproduce(double mutability) {
        return null;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "x=" + x +
                ", y=" + y +
                ", fitness=" + fitness +
                ", energy=" + energy +
                ", moves=" + Arrays.toString(moves) +
                ", state=" + state +
                '}';
    }

    @Override
    public int compareTo(Animal o) {
        return this.fitness - o.fitness;
    }
}
