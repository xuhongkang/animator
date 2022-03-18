package cs3500.animator.model;

public class Motion {
  private Integer startTime;
  private Integer endTime;
  private Integer startX;
  private Integer startY;
  private Integer endX;
  private Integer endY;
  private Integer startW;
  private Integer startH;
  private Integer endW;
  private Integer endH;
  private BasicColor startC;
  private BasicColor endC;

  public static class MotionBuilder {
    private Integer startTime;
    private Integer endTime;
    private Integer startX;
    private Integer startY;
    private Integer endX;
    private Integer endY;
    private Integer startW;
    private Integer startH;
    private Integer endW;
    private Integer endH;
    private BasicColor startC;
    private BasicColor endC;

    public MotionBuilder() {
      Integer startTime = null;
      Integer endTime = null;
      Integer startX = null;
      Integer startY = null;
      Integer endX = null;
      Integer endY = null;
      Integer startW = null;
      Integer startH = null;
      Integer endW = null;
      Integer endH = null;
      BasicColor startC = null;
      BasicColor endC = null;
    }

    public MotionBuilder setTime(int startTime, int endTime) {
      this.startTime = startTime;
      this.endTime = endTime;
      return this;
    }

    public MotionBuilder setStartXY(int startX, int startY) {
      this.startX = startX;
      this.startY = startY;
      return this;
    }

    public MotionBuilder setEndXY(int endX, int endY) {
      this.endX = endX;
      this.endY = endY;
      return this;
    }

    public MotionBuilder setStartWH(int startW, int startH) {
      this.startW = startW;
      this.startH = startH;
      return this;
    }

    public MotionBuilder setEndWH(int endW, int endH) {
      this.endW = endW;
      this.endH = endH;
      return this;
    }

    public MotionBuilder setStartColor(BasicColor startC) {
      this.startC = startC;
      return this;
    }

    public MotionBuilder setEndColor(BasicColor endC) {
      this.endC = endC;
      return this;
    }

    public Motion build() {
      return new Motion(this);
    }
  }

  private Motion(MotionBuilder b) {
    this.startTime = b.startTime;
    this.endTime = b.endTime;
    this.startX = b.startX;
    this.startY = b.startY;
    this.endX = b.endX;
    this.endY = b.endY;
    this.startW = b.startW;
    this.startH = b.startH;
    this.endW = b.endW;
    this.endH = b.endH;
    this.startC = b.startC;
    this.endC = b.endC;
  }

  public Integer getStartTime() {
    return startTime;
  }

  public Integer getEndTime() {
    return endTime;
  }

  public Integer getStartX() {
    return startX;
  }

  public Integer getStartY() {
    return startY;
  }

  public Integer getEndX() {
    return endX;
  }

  public Integer getEndY() {
    return endY;
  }

  public Integer getStartW() {
    return startW;
  }

  public Integer getStartH() {
    return startH;
  }

  public Integer getEndW() {
    return endW;
  }

  public Integer getEndH() {
    return endH;
  }

  public BasicColor getStartC() {
    return startC;
  }

  public BasicColor getEndC() {
    return endC;
  }

  public void setStartTime(Integer startTime) {
    this.startTime = startTime;
  }

  public void setEndTime(Integer endTime) {
    this.endTime = endTime;
  }

  public void setStartX(Integer startX) {
    this.startX = startX;
  }

  public void setStartY(Integer startY) {
    this.startY = startY;
  }

  public void setEndX(Integer endX) {
    this.endX = endX;
  }

  public void setEndY(Integer endY) {
    this.endY = endY;
  }

  public void setStartW(Integer startW) {
    this.startW = startW;
  }

  public void setStartH(Integer startH) {
    this.startH = startH;
  }

  public void setEndW(Integer endW) {
    this.endW = endW;
  }

  public void setEndH(Integer endH) {
    this.endH = endH;
  }

  public void setStartC(BasicColor startC) {
    this.startC = startC;
  }

  public void setEndC(BasicColor endC) {
    this.endC = endC;
  }
}
