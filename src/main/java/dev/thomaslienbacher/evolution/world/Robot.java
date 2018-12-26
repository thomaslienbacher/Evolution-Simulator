package dev.thomaslienbacher.evolution.world;

import dev.thomaslienbacher.evolution.utils.Utils;

import java.util.Arrays;
import java.util.Locale;

public class Robot implements Comparable<Robot> {
    public static final int MAX_MOVES = 20;
    public static final int MIN_MOVES = 3;
    public static final double START_ENERGY = 20;
    public static final double GAIN_FOOD = 5;
    public static final double SCAN_COST = 1;
    public static final double MOVE_COST = 0.2;
    public static final double MOVE_MUTABILITY = 0.6;
    public static final double DIRECTION_MUTABILITY = 0.6;
    public static final int NUM_DIRECTIONS = 7;

    private static int idCounter = 0;

    private int id;
    private int generation;
    public int x, y;
    private int fitness;
    private double energy;
    private byte[] moves; //clockwise 0-7
    private int state;

    public Robot() {
        this.id = idCounter;
        idCounter++;
        this.generation = 0;

        reset();
        this.moves = new byte[Utils.randIntIncl(MIN_MOVES, MAX_MOVES)];

        for(int i = 0; i < this.moves.length; i++) {
            this.moves[i] = (byte) Utils.randIntIncl(0, NUM_DIRECTIONS);
        }
    }

    public void reset() {
        this.x = World.WIDTH / 2;
        this.y = World.HEIGHT / 2;
        this.fitness = 0;
        this.state = 0;
        this.energy = START_ENERGY;
    }

    public void tick(World world) {
        if(state < moves.length) {
            switch(moves[state]) {
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


            energy -= MOVE_COST;
            state++;
        } else {
            for(int y = -1; y <= 1; y++) {
                for(int x = -1; x <= 1; x++) {
                    if(world.getFood(this.x + x, this.y + y) > 0) {
                        fitness++;
                        energy += GAIN_FOOD;
                        world.setFood(this.x + x, this.y + y, (byte) 0);
                    }
                }
            }

            energy -= SCAN_COST;
            state = 0;
        }
    }

    public Robot mutate() {
        Robot r = new Robot();
        r.moves = moves.clone();
        r.generation = generation + 1;

        if(Math.random() < MOVE_MUTABILITY) {
            byte[] mut;

            boolean canIncrease = r.moves.length < MAX_MOVES;
            boolean canDecrease = r.moves.length > MIN_MOVES;

            if (Math.random() <= 0.5 && canIncrease) {
                mut = new byte[r.moves.length + 1];
                System.arraycopy(r.moves, 0, mut, 0, r.moves.length);
                mut[r.moves.length] = (byte) Utils.randIntIncl(NUM_DIRECTIONS);
                r.moves = mut;
            }
            else if(canDecrease) {
                mut = new byte[r.moves.length - 1];
                System.arraycopy(r.moves, 0, mut, 0, r.moves.length - 1);
                r.moves = mut;
            }
        }

        if(Math.random() < DIRECTION_MUTABILITY) {
            int pos = Utils.randInt(r.moves.length);
            r.moves[pos] = (byte) Utils.randIntIncl(NUM_DIRECTIONS);
        }

        return r;
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
                "gen=" + generation +
                ", id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", fitness=" + fitness +
                ", energy=" + String.format(Locale.ENGLISH, "%.2f", energy) +
                ", moves=" + Arrays.toString(moves) +
                ", state=" + state +
                '}';
    }

    public String toStringSmall() {
        return "Robot{" +
                "gen=" + generation +
                ", id=" + id +
                ", fitness=" + fitness +
                ", moves=" + Arrays.toString(moves) +
                '}';
    }

    @Override
    public int compareTo(Robot o) {
        return o.fitness - this.fitness;
    }
}
