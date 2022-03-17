package cs3500.animator.model;

import java.util.HashMap;

/**
 * Basic Model for Animator, Handles data and operations.
 * @param <T> is the Designated Shape Class.
 * @param <V> is the Designated Motion Class.
 */
public interface AnimatorModel<T,V> {

  /**
   * Creates a new shape in the model.
   * @throws IllegalArgumentException if parameters are invalid.
   * @param tag is the tag for the target shape
   * @param bShape is a basic shape
   */
  void createShape(String tag, BasicShape bShape);

  HashMap getAnimations();

  void doNothing(String tag, BasicShape bShape, int startTime, int endTime);

  /**
   * Adds a new motion to an existing shape.
   * @throws IllegalArgumentException if shape is not initialized or if parameters are invalid.
   */
  void addMotion(String tag, BasicShape bShape, ShapeState start, ShapeState end);
}
