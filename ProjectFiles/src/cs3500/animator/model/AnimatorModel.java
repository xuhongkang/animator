package cs3500.animator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

  /**
   * Adds a new motion to an existing shape.
   * @throws IllegalArgumentException if shape is not initialized or if parameters are invalid.
   */
  void addMotion(String tag, Motion m);

  void doNothing(String tag, int startTime, int endTime);

  Set getTags();

  List getMotions(String tag);

  HashMap<String, ArrayList<ShapeState>> getAnimations();

  HashMap<String, BasicShape> getShapes();

  /**
   * ONLY FOR TESTING MAJOR VULNERABILITY DELETE ON SUBMISSION!
   */
  void addState(String tag, ShapeState... states);
}
