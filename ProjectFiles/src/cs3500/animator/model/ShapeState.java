package cs3500.animator.model;

import java.util.Objects;

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
      throw new IllegalStateException("Invalid time, time is negative.");
    }
    if (ctrX < 0) {
      throw new IllegalStateException("Invalid time, centerX is negative.");
    }
    if (ctrY < 0) {
      throw new IllegalStateException("Invalid time, centerY is negative.");
    }
    if (dimW < 0) {
      throw new IllegalStateException("Invalid time, width is negative.");
    }
    if (dimH < 0) {
      throw new IllegalStateException("Invalid time, height is negative.");
    }
    if (color == null) {
      throw new IllegalStateException("Invalid color, color is Null.");
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

  public int getTime() {
    return this.time;
  }

  public int getCtrX() {
    return ctrX;
  }

  public int getCtrY() {
    return ctrY;
  }

  public int getDimW() {
    return dimW;
  }

  public int getDimH() {
    return dimH;
  }

  public BasicColor getColor() {
    return color;
  }

  @Override
  public boolean equals(Object o) {
    if (o != null) {
      if (o instanceof ShapeState) {
        ShapeState that = (ShapeState) o;
        return this.time == that.time && this.ctrX == that.ctrX && this.ctrY == that.ctrY &&
                this.dimW == that.dimW && this.color.equals(that.color);
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.time, this.ctrX, this.ctrY, this.dimW, this.dimH, this.color);
  }

  @Override
  public String toString() {
    return String.format("%-3d %-3d %-3d %-3d %-3d %-3d %-3d %-3d", this.time, this.ctrX, this.ctrY,
            this.dimW, this.dimH, this.color.getRValue(), this.color.getGValue(),
            this.color.getBValue());
  }
}
