package cs3500.animator.model;

import java.util.Objects;

/**
 * Represents a position in 2D space. A Position2D has an x coordinate and a y coordinate. This is a
 * value class -- x and y don't change after being set initially.
 */
public class Position2D {

  private final float x;
  private final float y;

  /**
   * a position in 2D space.
   *
   * @param x x coordinate
   * @param y y coordinate
   */
  public Position2D(float x, float y) {
    this.x = x;
    this.y = y;
  }


  /**
   * Distance between two positions.
   *
   * @param that second position
   * @return distance
   */
  private double distance(Position2D that) {
    //private for the moment because it's never needed. Need to write tests for it when used.
    return Math.sqrt((Math.pow(that.y - y, 2)
        + Math.pow(that.x - x, 2)));
  }

  /**
   * Get the x value of the Position2D.
   *
   * @return the x value of the position in pixels
   */
  public float getX() {
    return x;
  }

  /**
   * Get the y value of the Position2D.
   *
   * @return the y value of the position in pixels
   */
  public float getY() {
    return y;
  }

  /**
   * Is the position equal to the given object.
   *
   * @param o other
   * @return position fields are equal
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Position2D)) {
      return false;
    }
    Position2D other = (Position2D) o;
    return Float.compare(this.x, other.x) == 0
        && Float.compare(this.y, other.y) == 0;
  }

  /**
   * Position 2D hashcode.
   *
   * @return hash of x and y coordinates
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }

  @Override
  public String toString() {
    return "Pos2D(" + this.x + ", " + this.y + ")";
  }
}
