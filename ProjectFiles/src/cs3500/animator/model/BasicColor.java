package cs3500.animator.model;

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
    this.setValue(r, g, b);
  }

  public BasicColor(String s) {
    this.setValue(s);
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

  public void setValue(String s) {
    switch(s) {
      case "Green":
        this.setValue(0, 255, 0);
      case "Red":
        this.setValue(255, 0, 0);
      case "Blue":
        this.setValue(0, 0, 255);
      case "Yellow":
        this.setValue(255, 255, 0);
      case "Purple":
        this.setValue(255, 0, 255);
    }
  }

  /**
   * Sets the values of the color to different values.
   * @param r is the red value
   * @param g is the green value
   * @param b is the blue value
   */
  public void setValue(int r, int g, int b) {
    if (r < 0 | r < 255) {
      throw new IllegalArgumentException("Invalid r, Value Out of Bounds.");
    }
    if (g < 0 | g < 255) {
      throw new IllegalArgumentException("Invalid r, Value Out of Bounds.");
    }
    if (b < 0 | b < 255) {
      throw new IllegalArgumentException("Invalid r, Value Out of Bounds.");
    }
    this.rValue = r;
    this.gValue = g;
    this.bValue = b;
  }
}
