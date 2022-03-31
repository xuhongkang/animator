package cs3500.animator.model;

import java.util.HashMap;

public class AnimatorModelImpl implements AnimatorModel {
  HashMap<String, ShapeProperty> properties;

  public AnimatorModelImpl() {
    this.properties = new HashMap<String, ShapeProperty>();
  }

  @Override
  public void build() {
    for (ShapeProperty s : this.properties.values()) {
      s.build();
    }
  }

  @Override
  public void addOval(String name,
                      float cx, float cy,
                      float xRadius, float yRadius,
                      float red, float green, float blue,
                      int startOfLife, int endOfLife) {
    if (this.properties.containsKey(name)) {
      throw new IllegalArgumentException("Oval Already Present");
    }
    ShapeProperty nShapeProperty = new ShapeProperty(BasicShape.OVAL, cx, cy, xRadius, yRadius, red,
    green, blue, startOfLife, endOfLife);
    this.properties.put(name, nShapeProperty);
  }

  @Override
  public void addRectangle(String name,
                           float lx, float ly,
                           float width, float height,
                           float red, float green, float blue,
                           int startOfLife, int endOfLife) {
    if (this.properties.containsKey(name)) {
      throw new IllegalArgumentException("Oval Already Present");
    }
    ShapeProperty nShapeProperty = new ShapeProperty(BasicShape.RECTANGLE, lx, ly, width, height, red,
            green, blue, startOfLife, endOfLife);
    this.properties.put(name, nShapeProperty);
  }

  @Override
  public void addMove(String name,
                      float moveFromX, float moveFromY,
                      float moveToX, float moveToY,
                      int startTime, int endTime) {
    if (!this.properties.containsKey(name)) {
      throw new IllegalArgumentException("Target Shape Not Present.");
    }
    this.properties.get(name).addMove(moveFromX, moveFromY, moveToX, moveToY, startTime, endTime);
  }

  @Override
  public void addColorChange(String name,
                             float oldR, float oldG, float oldB,
                             float newR, float newG, float newB, int startTime, int endTime) {

    if (!this.properties.containsKey(name)) {
      throw new IllegalArgumentException("Target Shape Not Present.");
    }
    this.properties.get(name).addColorChange(oldR, oldG, oldB, newR, newG, newB, startTime, endTime);
  }

  @Override
  public void addScaleToChange(String name,
                               float fromSx, float fromSy,
                               float toSx, float toSy,
                               int startTime, int endTime) {
    if (!this.properties.containsKey(name)) {
      throw new IllegalArgumentException("Target Shape Not Present.");
    }
    this.properties.get(name).addScaleToChange(fromSx, fromSy, toSx, toSy, startTime, endTime);
  }
}
