package dev.thomaslienbacher.evolution.world;

import dev.thomaslienbacher.evolution.gui.CanvasPanel;
import dev.thomaslienbacher.evolution.utils.Utils;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class World {

    public static final int WIDTH = 40, HEIGHT = 40;

    private ArrayList<Robot> robots;
    private byte[][] originalFood;
    private byte[][] food;
    private int numRobots;
    private int currentRobot;
    private boolean genFinished;

    public World() {
        food = new byte[WIDTH][HEIGHT];
        originalFood = new byte[WIDTH][HEIGHT];
        robots = new ArrayList<>();
    }

    private void placeFood(int numFood) {
        for(int i = 0; i < numFood; i++) {
            int x = Utils.randInt(WIDTH);
            int y = Utils.randInt(HEIGHT);

            while(food[x][y] > 0) {
                x = Utils.randInt(WIDTH);
                y = Utils.randInt(HEIGHT);
            }

            food[x][y] = 1;
            originalFood[x][y] = 1;
        }
    }

    public void startSimulation(int numRobots) {
        this.numRobots = numRobots;

        for(int i = 0; i < numRobots; i++) {
            robots.add(new Robot(WIDTH / 2, HEIGHT / 2));
        }

        placeFood(40);
        currentRobot = 0;
        genFinished = false;
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
        g.setColor(Color.BLUE);
        g.fill(new Ellipse2D.Float(a.x * CanvasPanel.BOX_W, a.y * CanvasPanel.BOX_H, CanvasPanel.BOX_W, CanvasPanel.BOX_H));

        if(a.isScanning()) {
            g.setColor(new Color(0.1f, 0.1f, 0.3f, 0.4f));
            g.fill(new RoundRectangle2D.Float((a.x - 1) * CanvasPanel.BOX_W, (a.y - 1) * CanvasPanel.BOX_H, CanvasPanel.BOX_W * 3, CanvasPanel.BOX_H * 3,
                    CanvasPanel.BOX_W / 2.0f, CanvasPanel.BOX_H / 2.0f));
        }
    }

    public byte getFood(int x, int y) {
        return food[Utils.wrap(x, WIDTH)][Utils.wrap(y, HEIGHT)];
    }

    public void setFood(int x, int y, byte value) {
        food[Utils.wrap(x, WIDTH)][Utils.wrap(y, HEIGHT)] = value;
    }

    public Robot getCurrentRobot() {
        return robots.get(currentRobot);
    }

    public boolean generationFinished() {
        return genFinished;
    }
}
