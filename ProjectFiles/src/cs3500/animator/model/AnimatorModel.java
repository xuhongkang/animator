package cs3500.animator.model;

/**
 * Basic Model for Animator, Handles data and operations.
 * @param <T> is the Designated Shape Class.
 * @param <V> is the Designated Motion Class.
 */
public interface AnimatorModel<T,V> {

  /**
   * Creates a new shape in the model.
   * @throws IllegalArgumentException if parameters are invalid.
   */
  void createShape();

  /**
   * Adds a new motion to an existing shape.
   * @throws IllegalArgumentException if shape is not initialized or if parameters are invalid.
   */
  void addMotion();
}
