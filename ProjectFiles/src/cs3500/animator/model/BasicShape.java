package cs3500.animator.model;

/**
 * Representation of a basic shape.
 */
public enum BasicShape {
  RECTANGLE, OVAL, TRIANGLE;

  @Override
  public String toString() {
    if (this.equals(RECTANGLE)) {
      return "Rectangle";
    } else if (this.equals(OVAL)) {
      return "Oval";
    } else if (this.equals(TRIANGLE)) {
      return "Triangle";
    } else {
      throw new IllegalArgumentException("Not a Valid Basic Shape");
    }
  }

  public BasicShape copy() {
    if (this.equals(RECTANGLE)) {
      return RECTANGLE;
    } else if (this.equals(OVAL)) {
      return OVAL;
    } else if (this.equals(TRIANGLE)) {
      return TRIANGLE;
    } else {
      throw new IllegalArgumentException("Not a Valid Basic Shape");
    }
  }
}

