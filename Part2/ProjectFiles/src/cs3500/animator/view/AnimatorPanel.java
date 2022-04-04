package cs3500.animator.view;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

import cs3500.animator.model.BasicShape;
import cs3500.animator.model.ShapeProperty;

public class AnimatorPanel extends JPanel {
  ArrayList<float[][]> values;
  ArrayList<BasicShape> shapes;

  public AnimatorPanel() {
    this.values = new ArrayList<float[][]>();
    this.shapes = new ArrayList<BasicShape>();
  }

  public void setValues(ArrayList<float[][]> values, ArrayList<BasicShape> shapes) {
    this.values = values;
    this.shapes = shapes;
  }

  public void paintComponent(Graphics g) {
    Graphics2D g2D = (Graphics2D) g;
    for (int i = 0; i < values.size(); i++) {
      float[] center;
      float[] color;
      float[] size;

      float[][] shapeVals = values.get(i);
      BasicShape shapeType = shapes.get(i);

      center = shapeVals[0];
      color = shapeVals[1];
      size = shapeVals[2];

      if (shapeType == BasicShape.RECTANGLE) {
        g2D.setColor(new Color((int) color[0],
                (int) color[1],
                (int) color[2]));
        g2D.fillRect((int) center[0], (int) center[1],
                (int) size[0], (int) size[1]);
      } else if (shapeType == BasicShape.OVAL) {
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