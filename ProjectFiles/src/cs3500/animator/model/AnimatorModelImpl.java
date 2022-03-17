package cs3500.animator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple Implementation of the animator model.
 */
public class AnimatorModelImpl implements AnimatorModel<Shape, ShapeState> {
  private HashMap<Shape, ArrayList<ShapeState>> animations;

  /**
   * Constructor for the animator model implementation.
   */
  public AnimatorModelImpl() {
    this.animations = new HashMap<Shape, ArrayList<ShapeState>>();
  }

  @Override
  public void createShape(String tag, BasicShape bShape) {
    Shape nShape = new Shape(tag, bShape);
    if (!this.checkExistingShape(nShape)) {
      this.animations.put(nShape, new ArrayList<ShapeState>());
    } else {
      throw new IllegalArgumentException("Animations already contains target Shape.");
    }
  }

  @Override
  public void doNothing(String tag, BasicShape bShape, int startTime, int endTime) {
    Shape eShape = new Shape(tag, bShape);
    if (!this.checkExistingShape(eShape)) {
      throw new IllegalArgumentException("Animations does not contain target Shape, " +
              "Please Initialize First.");
    }
    ArrayList<ShapeState> motions = this.animations.get(eShape);
    if (motions.isEmpty()) {
      throw new IllegalArgumentException("Cannot stall when not Initialized, " +
              "Please Initialize First");
    } else {
      ShapeState lastState = motions.get(motions.size() - 1);
      motions.add(lastState.stall(startTime, endTime));
    }
  }

  @Override
  public void addMotion(String tag, BasicShape bShape, ShapeState start, ShapeState end) {
    Shape eShape = new Shape(tag, bShape);
    if (!this.checkExistingShape(eShape)) {
      throw new IllegalArgumentException("Animations does not contain target Shape, " +
              "Please Initialize First.");
    }
    ArrayList<ShapeState> motions = this.animations.get(eShape);
    if (motions.isEmpty()) {
      motions.add(start);
      motions.add(end);
    } else {
      ShapeState lastState = motions.get(motions.size() - 1);
      if (start != lastState) {
        throw new IllegalArgumentException("Starting Parameters did not match the last state.");
      } else {
        motions.add(end);
      }
    }
  }

  @Override
  public HashMap getAnimations() {
    HashMap<Shape, ArrayList<ShapeState>> copy = new HashMap<Shape, ArrayList<ShapeState>>();
    for (Map.Entry<Shape, ArrayList<ShapeState>> entry : this.animations.entrySet())
    {
      copy.put(entry.getKey(), new ArrayList<ShapeState>(entry.getValue()));
    }
    return copy;
  }

  private boolean checkExistingShape(Shape s) {
    if (animations.containsKey(s)) {
      return true;
    } else {
      Boolean isTagTaken = animations.keySet().stream().anyMatch(x -> s.getTag().equals(x.getTag()));
      if (isTagTaken) {
        throw new IllegalArgumentException("Animations already contains target Shape tag.");
      } else {
        return false;
      }
    }
  }
}
