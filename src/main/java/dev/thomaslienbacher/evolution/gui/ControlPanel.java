package dev.thomaslienbacher.evolution.gui;

import dev.thomaslienbacher.evolution.world.World;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class ControlPanel {
    private JPanel pnlControl;
    private JButton btnStart;
    private JButton btnTick;
    private JTextPane tpInfo;
    private JButton btnRun;
    private JSpinner spnRunSpeed;

    public ControlPanel(Gui gui, World world) {
        btnStart.addActionListener((e) -> {
            world.startSimulation(15);
            gui.repaintWorld();
        });

        btnTick.addActionListener((e) -> {
            if (world.getState() != World.State.SIMULATING) return;

            world.tick();
            StyledDocument doc = getFreshDocument();

            try {
                doc.insertString(doc.getLength(), world.getCurrentAnimal().toString(), null);
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }

            gui.repaintWorld();
        });
    }

    public StyledDocument getFreshDocument() {
        StyledDocument doc = tpInfo.getStyledDocument();
        try {
            doc.remove(0, doc.getLength());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return doc;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        pnlControl = new JPanel();
        pnlControl.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        btnStart = new JButton();
        btnStart.setText("Start Simulation");
        pnlControl.add(btnStart, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnTick = new JButton();
        btnTick.setText("Tick");
        pnlControl.add(btnTick, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tpInfo = new JTextPane();
        pnlControl.add(tpInfo, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        pnlControl.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        spnRunSpeed = new JSpinner();
        panel1.add(spnRunSpeed, BorderLayout.WEST);
        btnRun = new JButton();
        btnRun.setText("Run");
        panel1.add(btnRun, BorderLayout.CENTER);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return pnlControl;
    }

}