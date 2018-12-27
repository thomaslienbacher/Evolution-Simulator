package dev.thomaslienbacher.evolution.world;

import dev.thomaslienbacher.evolution.utils.Utils;

import java.util.Arrays;
import java.util.Locale;

//TODO: implement genes and pre calc move vector
public class Robot implements Comparable<Robot> {
    public static final int MAX_GENES = 200;
    public static final int MIN_GENES = 1;
    public static final double GAIN_FOOD = 4;
    public static final double SCAN_COST = 1.5;
    public static final double MOVE_COST = 0.001;
    public static final double LEN_MUTABILITY = 0.5;
    public static final int MAX_MUTATIONS = MAX_GENES - MIN_GENES;
    public static final int NUM_DIRECTIONS = 7;

    private static int idCounter = 0;
    public int x, y;
    private int id;
    private int generation;
    private int fitness;
    private double energy;
    private byte[] genes; //clockwise 0-7
    private int movx, movy;//move vector
    private int state;//0 = moving, 1 = scanning

    public Robot() {
        this.id = idCounter;
        idCounter++;
        this.generation = 0;
        this.genes = new byte[Utils.randIntIncl(MIN_GENES, MAX_GENES)];

        for(int i = 0; i < this.genes.length; i++) {
            this.genes[i] = (byte) Utils.randIntIncl(0, NUM_DIRECTIONS);
        }

        reset();
    }

    public void reset() {
        this.x = World.WIDTH / 2;
        this.y = World.HEIGHT / 2;
        this.fitness = 0;
        this.state = 0;
        this.energy = GAIN_FOOD;
        calcMove();
    }

    private void calcMove() {
        movx = 0;
        movy = 0;

        for(int i = 0; i < genes.length; i++) {
            switch(genes[i]) {
                case 0: {
                    movy++;
                    break;
                }
                case 1: {
                    movy++;
                    movx++;
                    break;
                }
                case 2: {
                    movx++;
                    break;
                }
                case 3: {
                    movy--;
                    movx++;
                    break;
                }
                case 4: {
                    movy--;
                    break;
                }
                case 5: {
                    movx--;
                    movy--;
                    break;
                }
                case 6: {
                    movx--;
                    break;
                }
                case 7: {
                    movx--;
                    movy++;
                    break;
                }
            }
        }
    }

    public void tick(World world) {
        if(state == 0) {
            x = Utils.wrap(x + movx, World.WIDTH);
            y = Utils.wrap(y + movy, World.HEIGHT);

            energy -= MOVE_COST * genes.length;
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
        r.genes = genes.clone();
        r.generation = generation + 1;

        for(int i = 0; i < Math.max(r.genes.length, Utils.randInt(MAX_MUTATIONS)); i++) {
            if(Math.random() < LEN_MUTABILITY / (double) MAX_MUTATIONS) {
                byte[] mut;
                i++;

                boolean canIncrease = r.genes.length < MAX_GENES;
                boolean canDecrease = r.genes.length > MIN_GENES;

                if(Math.random() <= 0.5 && canIncrease) {
                    mut = new byte[r.genes.length + 1];
                    System.arraycopy(r.genes, 0, mut, 0, r.genes.length);
                    mut[r.genes.length] = (byte) Utils.randIntIncl(NUM_DIRECTIONS);
                    r.genes = mut;
                } else if(canDecrease) {
                    mut = new byte[r.genes.length - 1];
                    System.arraycopy(r.genes, 0, mut, 0, r.genes.length - 1);
                    r.genes = mut;
                }
            }

            int pos = Utils.randInt(r.genes.length);
            r.genes[pos] = (byte) Utils.randIntIncl(NUM_DIRECTIONS);
        }

        return r;
    }


    public boolean isDead() {
        return energy < 0;
    }

    public boolean isScanning() {
        return state == 1;
    }


    @Override
    public String toString() {
        return "Robot{" +
                "gen=" + generation +
                ", id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", movx=" + movx +
                ", movy=" + movy +
                ", fitness=" + fitness +
                ", energy=" + String.format(Locale.ENGLISH, "%.3f", energy) +
                ", genes=" + Arrays.toString(genes) +
                ", state=" + state +
                '}';
    }

    public String toStringSmall() {
        return "Robot{" +
                "gen=" + generation +
                ", id=" + id +
                ", fitness=" + fitness +
                ", genes=" + Arrays.toString(genes) +
                '}';
    }

    public String toStringData() {
        return "Robot{" +
                "gen=" + generation +
                ", id=" + id +
                ", genes=" + Arrays.toString(genes) +
                '}';
    }

    public int getFitness() {
        return fitness;
    }

    @Override
    public int compareTo(Robot o) {
        return Integer.compare(o.fitness, this.fitness);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Robot robot = (Robot) o;
        return Arrays.equals(genes, robot.genes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(genes);
    }
}
