package cs3500.animator.model;

/**
 * Object representing basic shape structures for the animator model.
 */
public class Shape {
  private String tag;
  private BasicShape shape;

  /**
   * Constructor for shape.
   * @param tag is a Unique String Tag
   * @param shape is a Basic Shape
   */
  public Shape(String tag, BasicShape shape) {
    if (tag.isEmpty() | tag.equals(null)) {
      throw new IllegalArgumentException("Invalid tag, tag is Null.");
    }
    if (shape == null) {
      throw new IllegalArgumentException("Invalid shape, shape is Null.");
    }
    this.tag = tag;
    this.shape = shape;
  }
}
