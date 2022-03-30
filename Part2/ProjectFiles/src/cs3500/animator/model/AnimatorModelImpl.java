package cs3500.animator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnimatorModelImpl implements AnimatorModel {
  // Invariant: If key is present in properties (at some instance), it is definitely in animations.
  HashMap<String, ShapeProperty> properties;
  HashMap<String, List<ShapeState>> animations;

  public AnimatorModelImpl() {
    this.properties = new HashMap<String, ShapeProperty>();
    this.animations = new HashMap<String, List<ShapeState>>();
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
    ShapeProperty nShapeProperty = new ShapeProperty(startOfLife, endOfLife, BasicShape.OVAL);
    this.properties.put(name, nShapeProperty);
    ShapeState nShapeState = new ShapeState(startOfLife, cx, cy, xRadius, yRadius, red, green, blue);
    this.animations.put(name, new ArrayList<ShapeState>());
    this.animations.get(name).add(nShapeState);
  }

  @Override
  public void addRectangle(String name,
                           float lx, float ly,
                           float width, float height,
                           float red, float green, float blue,
                           int startOfLife, int endOfLife) {
    if (this.properties.containsKey(name)) {
      throw new IllegalArgumentException("Rectangle Already Present");
    }
    ShapeProperty nShapeProperty = new ShapeProperty(startOfLife, endOfLife, BasicShape.RECTANGLE);
    this.properties.put(name, nShapeProperty);
    ShapeState nShapeState = new ShapeState(startOfLife, lx, ly, width, height, red, green, blue);
    this.animations.put(name, new ArrayList<ShapeState>());
    this.animations.get(name).add(nShapeState);
  }

  @Override
  public void addMove(String name,
                      float moveFromX, float moveFromY,
                      float moveToX, float moveToY,
                      int startTime, int endTime) {
    if (!this.properties.containsKey(name)) {
      throw new IllegalArgumentException("Target Shape Not Present.");
    }
    List<ShapeState> pStates = this.animations.get(name);
    ShapeState lState = pStates.get(pStates.size() - 1);
    if (lState.getTimeTick() != startTime ||
            lState.getCx() != moveFromX ||
            lState.getCy() != moveFromY) {
      throw new IllegalArgumentException("Does Not Match last Available State");
    }
    ShapeState nState = new ShapeState(endTime, moveToX, moveToY, lState.getSw(), lState.getSh(),
            lState.getRed(), lState.getGreen(), lState.getBlue());
    pStates.add(nState);
  }

  @Override
  public void addColorChange(String name,
                             float oldR, float oldG, float oldB,
                             float newR, float newG, float newB, int startTime, int endTime) {

    if (!this.properties.containsKey(name)) {
      throw new IllegalArgumentException("Target Shape Not Present.");
    }
    List<ShapeState> pStates = this.animations.get(name);
    ShapeState lState = pStates.get(pStates.size() - 1);
    if (lState.getTimeTick() != startTime ||
            lState.getRed() != oldR ||
            lState.getGreen() != oldG ||
            lState.getBlue() != oldB) {
      throw new IllegalArgumentException("Does Not Match last Available State");
    }
    ShapeState nState = new ShapeState(endTime, lState.getCx(), lState.getCy(), lState.getSw(),
            lState.getSh(), newR, newG, newB);
    pStates.add(nState);
  }

  @Override
  public void addScaleToChange(String name,
                               float fromSx, float fromSy,
                               float toSx, float toSy,
                               int startTime, int endTime) {
    if (!this.properties.containsKey(name)) {
      throw new IllegalArgumentException("Target Shape Not Present.");
    }
    List<ShapeState> pStates = this.animations.get(name);
    ShapeState lState = pStates.get(pStates.size() - 1);
    if (lState.getTimeTick() != startTime ||
            lState.getSw() != fromSx ||
            lState.getSh() != fromSy) {
      throw new IllegalArgumentException("Does Not Match last Available State");
    }
    ShapeState nState = new ShapeState(endTime, lState.getCx(), lState.getCy(), toSx, toSy,
            lState.getRed(), lState.getGreen(), lState.getBlue());
    pStates.add(nState);
  }
}
