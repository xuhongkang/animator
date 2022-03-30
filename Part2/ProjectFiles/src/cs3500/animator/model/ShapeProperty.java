package cs3500.animator.model;

public class ShapeProperty {
  private int startTime;
  private int endTime;
  private BasicShape shape;

  public ShapeProperty(int startTime, int endTime, BasicShape shape) {
    if (shape == null) {
      throw new IllegalArgumentException("Null Shape in ShapeProperty Constructor.");
    }
    if (startTime < 0 || endTime < 0) {
      throw new IllegalArgumentException("Negative TimeStep in ShapeProperty Constructor.");
    }
    if (startTime >= endTime) {
      throw new IllegalArgumentException("EndTime precedes StartTime in ShapeProperty Constructor.");
    }
    this.shape = shape;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public int getStartTime() {
    return startTime;
  }

  public int getEndTime() {
    return endTime;
  }

  public BasicShape getShape() {
    return shape;
  }
}
