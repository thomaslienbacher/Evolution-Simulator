package dev.thomaslienbacher.evolution.gui;

import dev.thomaslienbacher.evolution.Simulator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class ControlPanel {
    private JPanel pnlControl;
    private JButton btnStart;
    private JButton btnTick;
    private JTextPane tpInfo;
    private JButton btnStartRun;
    private JSpinner spnRunSpeed;
    private JButton btnInstantGen;
    private JButton btnStopRun;
    private JScrollPane sbInfo;
    private JButton btnShowRobots;
    private JButton btnEvolve;

    public ControlPanel(Simulator simulator) {
        btnStart.addActionListener((e) ->
                simulator.start()
        );

        btnTick.addActionListener((e) ->
                simulator.tick()
        );

        btnStartRun.addActionListener((e) ->
                simulator.startRunning((int) spnRunSpeed.getValue())
        );

        btnStopRun.addActionListener((e) ->
                simulator.stopRunning()
        );

        btnInstantGen.addActionListener((e) ->
                simulator.finishGeneration()
        );

        btnShowRobots.addActionListener((e) ->
                simulator.showAnimalsSorted()
        );

        btnEvolve.addActionListener((e) ->
                simulator.reproduceAndRestart()
        );

        spnRunSpeed.setValue(10);
        pnlControl.setBorder(new EmptyBorder(1, 1, 1, 1));
    }

    public StyledDocument getFreshDocument() {
        StyledDocument doc = tpInfo.getStyledDocument();
        try {
            doc.remove(0, doc.getLength());
        } catch(Exception e) {
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
        pnlControl.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, -1));
        pnlControl.setMinimumSize(new Dimension(174, 200));
        pnlControl.setOpaque(false);
        pnlControl.setPreferredSize(new Dimension(500, 500));
        btnStart = new JButton();
        btnStart.setText("Start Simulation");
        pnlControl.add(btnStart, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnTick = new JButton();
        btnTick.setText("Tick");
        pnlControl.add(btnTick, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        pnlControl.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        spnRunSpeed = new JSpinner();
        spnRunSpeed.setMaximumSize(new Dimension(150, 24));
        spnRunSpeed.setMinimumSize(new Dimension(150, 24));
        spnRunSpeed.setPreferredSize(new Dimension(150, 24));
        panel1.add(spnRunSpeed, BorderLayout.WEST);
        btnStartRun = new JButton();
        btnStartRun.setLabel("Start Auto Run");
        btnStartRun.setText("Start Auto Run");
        panel1.add(btnStartRun, BorderLayout.CENTER);
        btnStopRun = new JButton();
        btnStopRun.setLabel("Stop Auto Run");
        btnStopRun.setText("Stop Auto Run");
        pnlControl.add(btnStopRun, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sbInfo = new JScrollPane();
        pnlControl.add(sbInfo, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tpInfo = new JTextPane();
        sbInfo.setViewportView(tpInfo);
        btnShowRobots = new JButton();
        btnShowRobots.setText("Show Robots");
        pnlControl.add(btnShowRobots, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnInstantGen = new JButton();
        btnInstantGen.setText("Instant Generation");
        pnlControl.add(btnInstantGen, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnEvolve = new JButton();
        btnEvolve.setText("Evolve");
        pnlControl.add(btnEvolve, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return pnlControl;
    }

}
