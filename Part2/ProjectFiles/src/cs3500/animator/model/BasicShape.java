package cs3500.animator.model;

public enum BasicShape {
  RECTANGLE, OVAL;

  @Override
  public String toString() {
    if (this.equals(BasicShape.RECTANGLE)) {
      return "rectangle";
    } else if (this.equals(BasicShape.OVAL)) {
      return "oval";
    } else {
      throw new IllegalArgumentException("Cannot Identify Shape");
    }
  }
}
