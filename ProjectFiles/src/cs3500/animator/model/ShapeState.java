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
  public ShapeState(int time, int ctrX, int ctrY, int dimW, int dimH, BasicColor color) {
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
    this.time = time;
    this.ctrX = ctrX;
    this.ctrY = ctrY;
    this.dimW = dimW;
    this.dimH = dimH;
    this.color = color;
  }

  public ShapeState copy() {
    return new ShapeState(time, ctrX, ctrY, dimW, dimH, color.copy());
  }

  public ShapeState stall(int startTime, int endTime) {
    if (startTime < 0) {
      throw new IllegalArgumentException("Invalid Start Time, Start time is negative.");
    }
    if (endTime < 0) {
      throw new IllegalArgumentException("Invalid End Time, End Time is negative.");
    }
    if (startTime > endTime) {
      throw new IllegalArgumentException("Invalid End Time, Start time is greater than End Time");
    }
    if (this.getTime() != startTime) {
      throw new IllegalArgumentException("Starting Parameters did not match the Last State.");
    } else {
      return new ShapeState(endTime, this.ctrX, this.ctrY, this.dimW, this.dimH, this.color);
    }
  }

  public int getTime() {
    return this.time;
  }

  @Override
  public String toString() {
    return String.format("%-3d %-3d %-3d %-3d %-3d %-3d %-3d %-3d", this.time, this.ctrX, this.ctrY,
            this.dimW, this.dimH, this.color.getRValue(), this.color.getGValue(),
            this.color.getBValue());
  }
}
