package dev.thomaslienbacher.evolution.gui;

import dev.thomaslienbacher.evolution.world.Animal;
import dev.thomaslienbacher.evolution.world.World;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

public class CanvasPanel extends JPanel {

    public static final int WIDTH = 400, HEIGHT = 400;

    private World world;

    public CanvasPanel(World world) {
        super();
        this.world = world;


        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        LineBorder b = new LineBorder(Color.BLACK, 1);
        setBorder(b);
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

        int boxW = WIDTH / World.WIDTH;
        int boxH = HEIGHT / World.HEIGHT;

        if (world.getState() == World.State.SIMULATING) {

            for (int y = 0; y < world.height; y++) {
                for (int x = 0; x < world.width; x++) {
                    if (world.getFood(x, y) > 0) {
                        g.setColor(Color.RED);
                        g.fill(new Ellipse2D.Float(x * boxW, y * boxH, boxW, boxH));
                    }
                }
            }

            Animal a = world.getCurrentAnimal();
            g.setColor(Color.BLUE);
            g.fill(new Ellipse2D.Float(a.x * boxW, a.y * boxH, boxW, boxH));

            if (a.isScanning()) {
                g.setColor(new Color(0.1f, 0.1f, 0.3f, 0.4f));
                g.fill(new RoundRectangle2D.Float((a.x - 1) * boxW, (a.y - 1) * boxH, boxW * 3, boxH * 3,
                        boxW / 2.0f, boxH / 2.0f));
            }
        }
    }
}
