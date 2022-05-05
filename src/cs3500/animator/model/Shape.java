package cs3500.animator.model;

import java.awt.Color;
import java.util.Objects;

/**
 * A shape for an animation. A shape has: Position, Size List, Color, ShapeType, ShapeID. Length of
 * size list depends on the shapeType and is used to fully define the shape.
 */
public class Shape {

  private final String shapeID;
  private final ShapeType shapeType;
  private final Position2D position;
  private final Color color;
  private final float width;
  private final float height;
  //do we want a time var here? may not be needed

  /**
   * represents a shape in an animation.
   *
   * @param shapeID   user specified name
   * @param shapeType type of shape (oval or rectangle)
   * @param position  a 2d position
   * @param color     shape color
   * @param width     width of the shape
   * @param height    height of the shape
   */
  public Shape(String shapeID, ShapeType shapeType, Position2D position, Color color,
      float width, float height) {
    //builder class for a shape?
    if (shapeID == null || shapeType == null || position == null || color == null) {
      throw new IllegalArgumentException("can't have null inputs");
    }
    if (shapeID.isEmpty()) {
      throw new IllegalArgumentException("shape ID can't be empty");
    }
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("width or height can't be negative");
    }

    this.shapeID = shapeID;
    this.shapeType = shapeType;
    this.position = position;
    this.color = color;
    this.width = width;
    this.height = height;
  }

  /**
   * Constructor for shape where everything is the same as the given shape except for width/height.
   *
   * @param shape  the shape to copy values from
   * @param width  the width of the new shape
   * @param height the height of the new shape
   */
  public Shape(Shape shape, float width, float height) {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("width or height can't be negative");
    }
    this.shapeID = shape.shapeID;
    this.shapeType = shape.shapeType;
    this.position = shape.position;
    this.color = shape.color;
    this.width = width;
    this.height = height;
  }

  /**
   * Constructor for shape where everything is the same as the given shape except for color.
   *
   * @param shape the shape to copy values from
   * @param color the color of the new shape
   */
  public Shape(Shape shape, Color color) {
    if (color == null) {
      throw new IllegalArgumentException("can't have null inputs");
    }
    this.shapeID = shape.shapeID;
    this.shapeType = shape.shapeType;
    this.position = shape.position;
    this.color = color;
    this.width = shape.width;
    this.height = shape.height;
  }

  /**
   * Constructor for shape where everything is the same as the given shape except for position.
   *
   * @param shape the shape to copy values from
   * @param pos   the position of the new shape
   */
  public Shape(Shape shape, Position2D pos) {
    if (pos == null) {
      throw new IllegalArgumentException("can't have null inputs");
    }
    this.shapeID = shape.shapeID;
    this.shapeType = shape.shapeType;
    this.position = pos;
    this.color = shape.color;
    this.width = shape.width;
    this.height = shape.height;
  }

  /**
   * Get the ID of the shape.
   *
   * @return the ID of the shape
   */
  public String getShapeID() {
    return shapeID;
  }

  /**
   * Get the type of shape.
   *
   * @return type of shape
   */
  public ShapeType getShapeType() {
    return shapeType;
  }

  /**
   * Get the position of the shape.
   *
   * @return the position of the shape.
   */
  public Position2D getPosition() {
    return position;
  }

  /**
   * Get the color of the shape.
   *
   * @return the color as a {@link Color}
   */
  public Color getColor() {
    return color;
  }

  /**
   * Get the width of the shape.
   *
   * @return the width of the shape
   */
  public float getWidth() {
    return width;
  }

  /**
   * Get the height of the shape.
   *
   * @return the height of the shape
   */
  public float getHeight() {
    return height;
  }

  /**
   * is the shape equal to the given object.
   *
   * @param o other object
   * @return fields are equals
   */
  public boolean equals(Object o) {
    if (!(o instanceof Shape)) {
      return false;
    }
    Shape other = (Shape) o;
    return this.color.equals(other.color)
        && this.shapeID.equals(other.shapeID)
        && this.shapeType.equals(other.shapeType)
        && Float.compare(this.width, other.width) == 0
        && Float.compare(this.height, other.height) == 0
        && this.position.equals(other.position);
  }

  /**
   * overrides hashcode.
   *
   * @return hashcode
   */
  public int hashCode() {
    return Objects.hash(this.color, this.shapeID, this.shapeType,
        this.width, this.height, this.position);
  }
}
