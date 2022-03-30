package cs3500.animator.model;

public class ShapeState {
  private int timeTick;
  private float cx;
  private float cy;
  private float sw;
  private float sh;
  private float red;
  private float green;
  private float blue;

  public ShapeState(int timeTick, float cx, float cy, float sw, float sh,
                    float red, float green, float blue) {
    this.timeTick = timeTick;
    this.cx = cx;
    this.cy = cy;
    this.sw = sw;
    this.sh = sh;
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  public int getTimeTick() {
    return timeTick;
  }

  public float getCx() {
    return cx;
  }

  public float getCy() {
    return cy;
  }

  public float getSw() {
    return sw;
  }

  public float getSh() {
    return sh;
  }

  public float getRed() {
    return red;
  }

  public float getGreen() {
    return green;
  }

  public float getBlue() {
    return blue;
  }
}
