package dev.thomaslienbacher.evolution.world;

import dev.thomaslienbacher.evolution.utils.Utils;

import java.util.Arrays;

public class Robot implements Comparable<Robot> {
    public static final int MAX_MOVES = 6;
    public static final int MIN_MOVES = 2;
    public static final int START_ENERGY = 20;
    public static final int GAIN_FOOD = 10;

    private static int idCounter = 0;

    private int id;
    public int x, y;
    public int fitness = 0;
    public int energy = START_ENERGY;
    private byte[] moves; //clockwise 0-7
    private int state = 0;

    public Robot(int x, int y) {
        this.id = idCounter;
        idCounter++;

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

            x = Utils.wrap(x, World.WIDTH);
            y = Utils.wrap(y, World.HEIGHT);

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


    @Override
    public String toString() {
        return "Robot{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", fitness=" + fitness +
                ", energy=" + energy +
                ", moves=" + Arrays.toString(moves) +
                ", state=" + state +
                '}';
    }

    @Override
    public int compareTo(Robot o) {
        return this.fitness - o.fitness;
    }
}
