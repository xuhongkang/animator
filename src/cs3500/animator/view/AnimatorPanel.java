package cs3500.animator.view;

import cs3500.animator.io.AnimationFileReader;
import cs3500.animator.io.TweenModelBuilderImpl;
import cs3500.animator.model.AnimatorModelState;
import cs3500.animator.model.Shape;
import cs3500.animator.model.ShapeType;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.TreeSet;

import javax.swing.JPanel;

/**
 * Represents a panel in the animation that actually shows the animation. Is controlled by the view
 * and can query the model to get the shapes at the current time of the animation to draw. Will not
 * draw any shapes or portions of shapes outside the defined animation dimensions.
 */
class AnimatorPanel extends JPanel {

  private AnimatorModelState model;
  private int tick;
  boolean regenShapes;
  boolean isFillMode;
  List<Shape> aniShapes;

  /**
   * Constructor for animator panel.
   *
   * @param model the model to query to get the shapes to draw.
   */
  public AnimatorPanel(AnimatorModelState model) {
    super();
    this.model = model;
    this.setBackground(Color.WHITE);
    this.tick = 0;
    this.isFillMode = true;
    regenShapes = true;
  }

  void toggleFillMode() {
    this.isFillMode = !this.isFillMode;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    //int tick = 0;
    Graphics2D g2d = (Graphics2D) g;
    g.clipRect(0, 0, model.getWidthOfAnimation() * 2, model.getHeightOfAnimation() * 2);

    //if need to regenShapes, call the model with the tick
    if (regenShapes) {
      aniShapes = model.getShapes(tick);
      regenShapes = false;
    }

    List<Shape> shapes = aniShapes;

    for (Shape s : shapes) {
      if (this.isFillMode) {
        if (s.getShapeType().equals(ShapeType.OVAL)) {
          g2d.setColor(s.getColor());
          g.fillOval((int) (s.getPosition().getX() - s.getWidth()),
                  (int) (s.getPosition().getY() - s.getHeight()),
                  (int) (s.getWidth() * 2),
                  (int) (s.getHeight() * 2));
        } else if (s.getShapeType().equals(ShapeType.RECTANGLE)) {
          g2d.setColor(s.getColor());
          g.fillRect((int) s.getPosition().getX(),
                  (int) s.getPosition().getY(),
                  (int) s.getWidth(),
                  (int) s.getHeight());
        } else {
          g2d.setColor(s.getColor());
          g.fillRect((int) (s.getPosition().getX() + s.getWidth() * 0.25f),
                  (int) s.getPosition().getY(),
                  (int) (s.getWidth() * 0.5f),
                  (int) s.getHeight());
          g.fillRect((int) s.getPosition().getX(),
                  (int) (s.getPosition().getY() + s.getHeight() * 0.25f),
                  (int) s.getWidth(),
                  (int) (s.getHeight() * 0.5f));
        }
      } else {
        if (s.getShapeType().equals(ShapeType.OVAL)) {
          g2d.setColor(s.getColor());
          g.drawOval((int) (s.getPosition().getX() - s.getWidth()),
                  (int) (s.getPosition().getY() - s.getHeight()),
                  (int) (s.getWidth() * 2),
                  (int) (s.getHeight() * 2));
        } else if (s.getShapeType().equals(ShapeType.RECTANGLE)) {
          g2d.setColor(s.getColor());
          g.drawRect((int) s.getPosition().getX(),
                  (int) s.getPosition().getY(),
                  (int) s.getWidth(),
                  (int) s.getHeight());
        } else {
          g2d.setColor(s.getColor());
          double x = s.getPosition().getX();
          double y = s.getPosition().getY();
          double w = s.getWidth();
          double h = s.getHeight();
          g.drawLine((int) (x + 0.25f * w), (int) y, (int) (x + 0.75f * w), (int) y);
          g.drawLine((int) (x + 0.25f * w), (int) y, (int) (x + 0.25f * w), (int) (y + 0.25f * h));
          g.drawLine((int) (x + 0.75f * w), (int) y, (int) (x + 0.75f * w), (int) (y + 0.25f * h));
          g.drawLine((int) (x + 0.75f * w), (int) (y + 0.25f * h),
                  (int) (x + w), (int) (y + 0.25f * h));
          g.drawLine((int) (x + w), (int) (y + 0.25f * h), (int) (x + w), (int) (y + 0.75f * h));
          g.drawLine((int) (x + 0.75f * w), (int) (y + 0.75f * h),
                  (int) (x + w), (int) (y + 0.75f * h));
          g.drawLine((int) (x + 0.25f * w), (int) (y + 0.25f * h),
                  (int) (x), (int) (y + 0.25f * h));
          g.drawLine((int) (x), (int) (y + 0.25f * h), (int) (x), (int) (y + 0.75f * h));
          g.drawLine((int) (x + 0.25f * w), (int) (y + 0.75f * h),
                  (int) (x), (int) (y + 0.75f * h));
          g.drawLine((int) (x + 0.25f * w), (int) (y + h), (int) (x + 0.75f * w), (int) (y + h));
          g.drawLine((int) (x + 0.25f * w), (int) (y + h), (int) (x + 0.25f * w),
                  (int) (y + 0.75f * h));
          g.drawLine((int) (x + 0.75f * w), (int) (y + h), (int) (x + 0.75f * w),
                  (int) (y + 0.75f * h));
        }
      }
    }
  }

  public void loadNewCommand() {
    AnimatorModelState backUp = this.model;
    AnimationFileReader reader = new AnimationFileReader();
    try {
      this.model = reader.readFile("TemporaryCommands.txt",
              new TweenModelBuilderImpl(this.model));
      new File("TemporaryCommands.txt").delete();
    } catch (FileNotFoundException fne) {
      throw new IllegalArgumentException("Temporary Commands File Not Found");
    } catch (IllegalArgumentException | IllegalStateException ise) {
      this.model = backUp;
    }
  }

  void setTick(int tick) {
    this.tick = tick;
  }

  void setRegenShapes() {
    regenShapes = true;
  }

  int getTempoAt() {
    return this.model.getTempoAt(tick);
  }

  TreeSet<Integer> getDiscreteTime() {
    return this.model.getDiscreteTime();
  }
}

