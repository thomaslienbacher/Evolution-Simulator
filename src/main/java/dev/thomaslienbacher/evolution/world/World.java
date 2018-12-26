package dev.thomaslienbacher.evolution.world;

import dev.thomaslienbacher.evolution.gui.CanvasPanel;
import dev.thomaslienbacher.evolution.utils.Utils;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class World {

    public static final int WIDTH = 80, HEIGHT = 80;
    public static final int NUM_FOOD = 200;

    private ArrayList<Robot> robots;
    private byte[][] originalFood;
    private byte[][] food;
    private int numRobots;//TODO: implement dynamic number of robots
    private int currentRobot;
    private boolean genFinished;

    public World() {
        food = new byte[WIDTH][HEIGHT];
        originalFood = new byte[WIDTH][HEIGHT];
        robots = new ArrayList<>();
    }

    private void generateFood() {
        for(int y = 0; y < HEIGHT; y++) {
            for(int x = 0; x < WIDTH; x++) {
                originalFood[x][y] = 0;
            }
        }

        for(int i = 0; i < NUM_FOOD; i++) {
            int x = Utils.randInt(WIDTH);
            int y = Utils.randInt(HEIGHT);

            while(originalFood[x][y] > 0) {
                x = Utils.randInt(WIDTH);
                y = Utils.randInt(HEIGHT);
            }

            originalFood[x][y] = 1;
        }

        food = Utils.copy2dArray(originalFood);
    }

    public void startSimulation(int numRobots) {
        this.numRobots = numRobots;

        for(int i = 0; i < numRobots; i++) {
            robots.add(new Robot());
        }

        generateFood();
    }

    public void tick() {
        Robot robot = getCurrentRobot();
        robot.tick(this);
        if(robot.isDead()) {
            currentRobot++;
            food = Utils.copy2dArray(originalFood);
            genFinished = currentRobot >= numRobots;
        }
    }

    public void render(Graphics2D g) {
        for(int y = 0; y < HEIGHT; y++) {
            for(int x = 0; x < WIDTH; x++) {
                if(getFood(x, y) > 0) {
                    g.setColor(Color.RED);
                    g.fill(new Ellipse2D.Float(x * CanvasPanel.BOX_W, y * CanvasPanel.BOX_H, CanvasPanel.BOX_W, CanvasPanel.BOX_H));
                }
            }
        }

        Robot a = getCurrentRobot();

        if(a != null) {
            g.setColor(Color.BLUE);
            g.fill(new Ellipse2D.Float(a.x * CanvasPanel.BOX_W, a.y * CanvasPanel.BOX_H, CanvasPanel.BOX_W, CanvasPanel.BOX_H));

            if(a.isScanning()) {
                g.setColor(new Color(0.1f, 0.1f, 0.3f, 0.4f));
                g.fill(new RoundRectangle2D.Float((a.x - 1) * CanvasPanel.BOX_W, (a.y - 1) * CanvasPanel.BOX_H, CanvasPanel.BOX_W * 3, CanvasPanel.BOX_H * 3,
                        CanvasPanel.BOX_W / 2.0f, CanvasPanel.BOX_H / 2.0f));
            }
        }
    }

    public void restart() {
        food = Utils.copy2dArray(originalFood);
        currentRobot = 0;
        genFinished = false;
    }

    public byte getFood(int x, int y) {
        return food[Utils.wrap(x, WIDTH)][Utils.wrap(y, HEIGHT)];
    }

    public void setFood(int x, int y, byte value) {
        food[Utils.wrap(x, WIDTH)][Utils.wrap(y, HEIGHT)] = value;
    }

    public Robot getCurrentRobot() {
        if(currentRobot >= robots.size()) return null;
        return robots.get(currentRobot);
    }

    public boolean generationFinished() {
        return genFinished;
    }

    public ArrayList<Robot> getRobots() {
        return robots;
    }

    public int getNumRobots() {
        return numRobots;
    }
}
