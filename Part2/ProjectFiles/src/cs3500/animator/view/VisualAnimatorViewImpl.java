package cs3500.animator.view;

import java.awt.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import cs3500.animator.model.BasicShape;

public class VisualAnimatorViewImpl extends JFrame implements VisualAnimatorView, ActionListener {

  private AnimatorPanel panel;
  private JLabel display;
  private int timeStep = 0;

  public VisualAnimatorViewImpl(int width, int height, int delay) {
    this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //use a borderlayout with drawing panel in center and button panel in south
    this.setLayout(new BorderLayout());
    panel = new AnimatorPanel();
    panel.setPreferredSize(new Dimension(500, 500));
    this.add(panel, BorderLayout.CENTER);
    this.setVisible(true);
    Timer timer = new Timer(delay, this);
    timer.start();
    this.pack();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void setValues(ArrayList<ArrayList<float[][]>> valuesList, ArrayList<ArrayList<BasicShape>> shapesList) {
    if (timeStep <= valuesList.size()) {
      this.panel.setValues(valuesList.get(timeStep), shapesList.get(timeStep));
    } else {
      this.panel.setValues(valuesList.get(valuesList.size() - 1), shapesList.get(timeStep));
    }
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println(timeStep);
    this.refresh();
    timeStep += 1;
  }
}
