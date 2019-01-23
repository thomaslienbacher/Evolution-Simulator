package dev.thomaslienbacher.evolution.gui;

import dev.thomaslienbacher.evolution.Simulator;
import dev.thomaslienbacher.evolution.world.World;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CanvasPanel extends JPanel {

    public static final int WIDTH = 400, HEIGHT = 400;
    public static final int BOX_W = 400 / World.WIDTH, BOX_H = 400 / World.HEIGHT;

    private Simulator simulator;
    private Font canvasFont;

    public CanvasPanel(Simulator simulator) {
        super();
        this.simulator = simulator;
        this.canvasFont = new Font("Arial", Font.PLAIN, 20);

        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
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

        if (simulator.isSimulating()) {
            simulator.renderWorld(g);
        } else {
            String s = "State: " + simulator.stateName();
            g.setFont(canvasFont);
            int width = g.getFontMetrics().stringWidth(s);
            g.drawString(s, WIDTH / 2 - width / 2, HEIGHT / 2);
        }
    }
}
