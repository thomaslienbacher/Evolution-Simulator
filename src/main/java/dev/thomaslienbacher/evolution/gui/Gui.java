package dev.thomaslienbacher.evolution.gui;

import dev.thomaslienbacher.evolution.world.World;

import javax.swing.*;
import java.awt.*;

public class Gui {

    private JFrame frame;
    private CanvasPanel pnlCanvas;
    private ControlPanel pnlControl;

    public Gui(World world) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        pnlCanvas = new CanvasPanel(world);
        pnlControl = new ControlPanel(this, world);

        frame = new JFrame("Evolution Simulator");
        frame.setLayout(new GridLayout(1, 2));
        frame.add(pnlCanvas, 0);
        frame.add(pnlControl.$$$getRootComponent$$$(), 1);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void repaintWorld() {
        pnlCanvas.repaint();
    }
}
