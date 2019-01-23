package dev.thomaslienbacher.evolution.gui;

import dev.thomaslienbacher.evolution.Simulator;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class Gui {

    private JFrame frame;
    private CanvasPanel pnlCanvas;
    private ControlPanel pnlControl;

    public Gui(Simulator simulator) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        pnlCanvas = new CanvasPanel(simulator);
        pnlControl = new ControlPanel(simulator);

        frame = new JFrame("Evolution Simulator");
        frame.setLayout(new GridBagLayout());
        frame.add(pnlCanvas, 0);
        frame.add(pnlControl.$$$getRootComponent$$$(), 1);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }

    public void repaintWorld() {
        pnlCanvas.repaint();
    }

    public void setInfoString(String info) {
        StyledDocument doc = pnlControl.getFreshDocument();
        try {
            doc.insertString(0, info, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
