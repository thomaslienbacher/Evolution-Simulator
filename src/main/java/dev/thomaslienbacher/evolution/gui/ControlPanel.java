package dev.thomaslienbacher.evolution.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import dev.thomaslienbacher.evolution.Simulator;
import dev.thomaslienbacher.evolution.world.Robot;

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
    private JSpinner spnAmountInst;
    private JButton btnGc;
    private JButton btnInfinteRun;

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

        btnInstantGen.addActionListener((e) -> {
            for (int i = 0; i < (int) spnAmountInst.getValue(); i++) {
                simulator.finishGeneration();
            }
        });

        btnShowRobots.addActionListener((e) ->
                simulator.showRobotsSorted(Robot::toStringSmall)
        );

        btnEvolve.addActionListener((e) ->
                simulator.reproduceAndRestart()
        );

        btnGc.addActionListener((e) -> {
            System.gc();
            Runtime.getRuntime().gc();
            System.runFinalization();
        });

        btnInfinteRun.addActionListener((e) -> {
            JOptionPane.showMessageDialog(null, "Information will be printed to stdout", "Notice", JOptionPane.INFORMATION_MESSAGE);
            Thread t = new Thread(() -> simulator.infiniteSimulation());
            t.setName("Infinte-Simulation-Thread");
            t.start();
        });

        spnRunSpeed.setValue(10);
        spnAmountInst.setValue(1);
        pnlControl.setBorder(new EmptyBorder(1, 1, 1, 1));
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
        pnlControl.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, -1));
        pnlControl.setMinimumSize(new Dimension(174, 200));
        pnlControl.setOpaque(false);
        pnlControl.setPreferredSize(new Dimension(500, 540));
        btnStart = new JButton();
        btnStart.setText("Start Simulation");
        pnlControl.add(btnStart, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnTick = new JButton();
        btnTick.setText("Tick");
        pnlControl.add(btnTick, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        pnlControl.add(panel1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        spnRunSpeed = new JSpinner();
        spnRunSpeed.setMaximumSize(new Dimension(150, 24));
        spnRunSpeed.setMinimumSize(new Dimension(150, 24));
        spnRunSpeed.setPreferredSize(new Dimension(150, 24));
        spnRunSpeed.setToolTipText("Speed of Auto Run");
        panel1.add(spnRunSpeed, BorderLayout.WEST);
        btnStartRun = new JButton();
        btnStartRun.setLabel("Start Auto Run");
        btnStartRun.setText("Start Auto Run");
        panel1.add(btnStartRun, BorderLayout.CENTER);
        btnStopRun = new JButton();
        btnStopRun.setLabel("Stop Auto Run");
        btnStopRun.setText("Stop Auto Run");
        pnlControl.add(btnStopRun, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sbInfo = new JScrollPane();
        pnlControl.add(sbInfo, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tpInfo = new JTextPane();
        tpInfo.setEditable(false);
        sbInfo.setViewportView(tpInfo);
        btnEvolve = new JButton();
        btnEvolve.setText("Evolve");
        pnlControl.add(btnEvolve, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        pnlControl.add(panel2, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        spnAmountInst = new JSpinner();
        spnAmountInst.setMaximumSize(new Dimension(150, 24));
        spnAmountInst.setMinimumSize(new Dimension(150, 24));
        spnAmountInst.setPreferredSize(new Dimension(150, 24));
        spnAmountInst.setRequestFocusEnabled(false);
        spnAmountInst.setToolTipText("How many generations simulated");
        panel2.add(spnAmountInst, BorderLayout.WEST);
        btnInstantGen = new JButton();
        btnInstantGen.setText("Instant Generation");
        panel2.add(btnInstantGen, BorderLayout.CENTER);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        pnlControl.add(panel3, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnGc = new JButton();
        btnGc.setActionCommand("");
        btnGc.setInheritsPopupMenu(true);
        btnGc.setText("Invoke GC");
        panel3.add(btnGc);
        btnShowRobots = new JButton();
        btnShowRobots.setText("Show Robots");
        panel3.add(btnShowRobots);
        btnInfinteRun = new JButton();
        btnInfinteRun.setText("Run forever");
        panel3.add(btnInfinteRun);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return pnlControl;
    }

}
