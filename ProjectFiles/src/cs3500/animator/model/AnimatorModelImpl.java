package cs3500.animator.model;

import java.util.ArrayList;
import java.util.HashMap;

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
  public void createShape() {

  }

  @Override
  public void addMotion() {

  }
}
