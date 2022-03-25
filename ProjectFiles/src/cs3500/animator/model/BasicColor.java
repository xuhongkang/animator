package cs3500.animator.model;

import java.util.Objects;

/**
 * Basic representation of a shade of color.
 */
public class BasicColor {
  private int rValue;
  private int gValue;
  private int bValue;

  /**
   * Constructor for the basic color.
   * @param r is the red value
   * @param g is the green value
   * @param b is the blue value
   */
  public BasicColor(int r, int g, int b) {
    this.setRGBValue(r, g, b);
  }

  public BasicColor(String s) {
    this.setRGBValue(s);
  }

  public BasicColor copy() {
    return new BasicColor(this.rValue, this.gValue, this.bValue);
  }

  public int getRValue() {
    return this.rValue;
  }

  public int getGValue() {
    return this.gValue;
  }

  public int getBValue() {
    return this.bValue;
  }

  /**
   * Sets the RGB value when given string representation of a specific color.
   * @param s is a string representation of a documented color
   */
  public void setRGBValue(String s) {
    switch (s) {
      case "Red":
        this.setRGBValue(255, 0, 0);
        break;
      case "Green":
        this.setRGBValue(0, 255, 0);
        break;
      case "Blue":
        this.setRGBValue(0, 0, 255);
        break;
      case "Yellow":
        this.setRGBValue(255, 255, 0);
        break;
      case "Purple":
        this.setRGBValue(255, 0, 255);
        break;
      default:
        throw new IllegalArgumentException("Invalid Color, unable to Identify.");
    }
  }

  /**
   * Sets the values of the color to different values.
   * @param r is the red value
   * @param g is the green value
   * @param b is the blue value
   */
  public void setRGBValue(int r, int g, int b) {
    if (r < 0 | r > 255) {
      throw new IllegalArgumentException("Invalid r, Value Out of Bounds.");
    }
    if (g < 0 | g > 255) {
      throw new IllegalArgumentException("Invalid g, Value Out of Bounds.");
    }
    if (b < 0 | b > 255) {
      throw new IllegalArgumentException("Invalid b, Value Out of Bounds.");
    }
    this.rValue = r;
    this.gValue = g;
    this.bValue = b;
  }

  @Override
  public boolean equals(Object o) {
    if (o != null) {
      if (o instanceof BasicColor) {
        BasicColor that = (BasicColor) o;
        return this.rValue == that.rValue && this.gValue == that.gValue &&
                this.bValue == that.bValue;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.rValue, this.gValue, this.bValue);
  }
}
