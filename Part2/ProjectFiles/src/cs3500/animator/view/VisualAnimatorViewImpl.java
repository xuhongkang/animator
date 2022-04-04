package cs3500.animator.view;

import java.awt.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.BasicShape;
import cs3500.animator.model.ShapeProperty;

public class VisualAnimatorViewImpl extends JFrame implements VisualAnimatorView, ActionListener {

  private AnimatorModel model;
  private VisualPanel panel;
  private int endTime;
  private int currentTime;

  public VisualAnimatorViewImpl(AnimatorModel model, int delay) {
    this.model = model.build();
    this.panel = new VisualPanel();
    this.endTime = model.getMaxEndTime();

    // Swing Interface Setup
    this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(panel);
    this.setVisible(true);
    this.pack();

    // Timer Stuff
    this.currentTime = 0;
    Timer timeCounter = new Timer(delay, this);
    timeCounter.start();
    while (currentTime <= endTime) {
       //Do Nothing
    }
  }

  @Override
  public void switchAnimatorModel(AnimatorModelImpl model) {
    this.model = new AnimatorModelImpl(model);
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2D = (Graphics2D) g;

    ArrayList<ShapeProperty> shapeProperties = new ArrayList<ShapeProperty>();

    for (String tag : model.getTags()) {
      shapeProperties.add(model.getShapeProperty(tag));
    }

    for (ShapeProperty sp : shapeProperties) {
      if (currentTime >= sp.getStartTime() && currentTime <= sp.getDisApTime()) {
        float[] center;
        float[] color;
        float[] size;

        center = sp.getValsAt(currentTime)[0];
        color = sp.getValsAt(currentTime)[1];
        size = sp.getValsAt(currentTime)[2];

        if (sp.getShape() == BasicShape.RECTANGLE) {
          g2D.setColor(new Color((int) color[0],
                  (int) color[1],
                  (int) color[2]));
          g2D.fillRect((int) center[0], (int) center[1],
                  (int) size[0], (int) size[1]);
        } else if (sp.getShape() == BasicShape.OVAL) {
          g2D.setColor(new Color((int) color[0],
                  (int) color[1],
                  (int) color[2]));
          g2D.fillRect((int) center[0], (int) center[1],
                  (int) size[0], (int) size[1]);
        } else {
          throw new IllegalArgumentException("Null Shape.");
        }
      }
    }
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println(currentTime);
    this.refresh();
    currentTime += 1;
  }
}
