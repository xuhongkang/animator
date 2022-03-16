package cs3500.animator.model;

/**
 * Representation of the state of a shape.
 */
public class ShapeState {
  private int time;
  private int ctrX;
  private int ctrY;
  private int dimW;
  private int dimH;
  private BasicColor color;

  /**
   * Constructor of ShapeState.
   * @throws IllegalArgumentException if color is invalid.
   */
  public ShapeState(int ctrX, int ctrY, int dimW, int dimH, BasicColor color) {
    if (time < 0) {
      throw new IllegalArgumentException("Invalid time, time is negative.");
    }
    if (ctrX < 0) {
      throw new IllegalArgumentException("Invalid time, centerX is negative.");
    }
    if (ctrY < 0) {
      throw new IllegalArgumentException("Invalid time, centerY is negative.");
    }
    if (dimW < 0) {
      throw new IllegalArgumentException("Invalid time, width is negative.");
    }
    if (dimH < 0) {
      throw new IllegalArgumentException("Invalid time, height is negative.");
    }
    if (color == null) {
      throw new IllegalArgumentException("Invalid color, color is Null.");
    }
    this.ctrX = ctrX;
    this.ctrY = ctrY;
    this.dimW = dimW;
    this.dimH = dimH;
    this.color = color;
  }

  @Override
  public String toString() {

  }
}
