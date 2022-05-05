package cs3500.animator.io;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelState;
import cs3500.animator.model.Position2D;
import cs3500.animator.model.Shape;
import cs3500.animator.model.ShapeType;
import cs3500.animator.model.SimpleModel;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Implementation of TweenModelBuilder. Interfaces between local code design / implementation and
 * the provided AnimationFileReader class. Enables the AnimationFileReader to create and fill an
 * animation model with shapes and animations.
 */
public class TweenModelBuilderImpl implements TweenModelBuilder<AnimatorModel> {
  private AnimatorModel model;

  public TweenModelBuilderImpl() {
    // Alternative Constructor for tween model builder, creates new model
  }

  public TweenModelBuilderImpl(AnimatorModelState model) {
    this.model = (AnimatorModel) model;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> setBounds(int width, int height) {
    model = new SimpleModel(width, height);
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addSlowMo(int sTime, int eTime, int tempo) {
    this.model.addSlowMo(sTime, eTime, tempo);
    return null;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addLayer(String layerName, ArrayList<String> shapeIDs) {
    this.model.addLayer(layerName, shapeIDs);
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addOval(String name, float cx, float cy, float xRadius,
      float yRadius,
      float red, float green, float blue, int startOfLife, int endOfLife) {
    Shape s = new Shape(name, ShapeType.OVAL, new Position2D(cx, cy), new Color(red, green, blue),
        xRadius, yRadius);
    model.addShape(s, startOfLife, endOfLife);
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addRectangle(String name, float lx, float ly, float width,
      float height, float red, float green, float blue, int startOfLife, int endOfLife) {
    Shape s = new Shape(name, ShapeType.RECTANGLE, new Position2D(lx, ly),
        new Color(red, green, blue),
        width, height);
    model.addShape(s, startOfLife, endOfLife);
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addCross(String name, float lx, float ly, float width,
      float height, float red, float green, float blue, int startOfLife, int endOfLife) {
    Shape s = new Shape(name, ShapeType.CROSS, new Position2D(lx, ly),
            new Color(red, green, blue),
            width, height);
    model.addShape(s, startOfLife, endOfLife);
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addMove(String name, float moveFromX, float moveFromY,
      float moveToX,
      float moveToY, int startTime, int endTime) {
    model.addMoveCmd(name, startTime, endTime, new Position2D(moveFromX, moveFromY),
        new Position2D(moveToX, moveToY));
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addColorChange(String name, float oldR, float oldG,
      float oldB,
      float newR, float newG, float newB, int startTime, int endTime) {
    model.addColorCmd(name, startTime, endTime, new Color(oldR, oldG, oldB),
        new Color(newR, newG, newB));
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addScaleToChange(String name, float fromSx, float fromSy,
      float toSx, float toSy, int startTime, int endTime) {
    model.addSizeCmd(name, startTime, endTime, fromSx, fromSy,
        toSx, toSy);
    return this;
  }

  @Override
  public AnimatorModel build() {
    return model;
  }
}
