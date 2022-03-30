package cs3500.animator.model;

/**
 * The class representing motions of the destined figure.
 */
public class Motion {
  private String tag;
  private BasicShape shape;
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

  /**
   * Builder Class for motions.
   */
  public static class MotionBuilder {
    private String tag;
    private BasicShape shape;
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

    /**
     * Constructor for builder class.
     */
    public MotionBuilder() {
      BasicShape shape = null;
      String tag = null;
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

    /**
     * Sets the tag in the motion builder.
     * @param tag the target tag
     * @param shape the target shape
     * @return the motion builder
     */
    public MotionBuilder setTag(String tag, BasicShape shape) {
      this.tag = tag;
      this.shape = shape;
      return this;
    }

    /**
     * Sets the time in the motion builder.
     * @param startTime is the target start time
     * @param endTime is the target end time
     * @return the motion builder
     */
    public MotionBuilder setTime(int startTime, int endTime) {
      this.startTime = startTime;
      this.endTime = endTime;
      return this;
    }

    /**
     * Sets the starting XY positions.
     * @param startX is the target starting X position
     * @param startY is the target starting Y position
     * @return the motion builder
     */
    public MotionBuilder setStartXY(int startX, int startY) {
      this.startX = startX;
      this.startY = startY;
      return this;
    }

    /**
     * Sets the ending XY positions.
     * @param endX is the target ending X position
     * @param endY is the target ending Y position
     * @return the motion builder
     */
    public MotionBuilder setEndXY(int endX, int endY) {
      this.endX = endX;
      this.endY = endY;
      return this;
    }

    /**
     * Sets the starting WH dimensions.
     * @param startW is the target starting width
     * @param startH the target starting height
     * @return the motion builder
     */
    public MotionBuilder setStartWH(int startW, int startH) {
      this.startW = startW;
      this.startH = startH;
      return this;
    }

    /**
     * Sets the ending WH dimensions.
     * @param endW is the target ending width
     * @param endH the target ending height
     * @return the motion builder
     */
    public MotionBuilder setEndWH(int endW, int endH) {
      this.endW = endW;
      this.endH = endH;
      return this;
    }

    /**
     * Sets the starting color.
     * @param startC is the starting Color
     * @return the motion builder
     */
    public MotionBuilder setStartColor(BasicColor startC) {
      this.startC = startC;
      return this;
    }

    /**
     * Sets the ending color.
     * @param endC is the ending Color
     * @return the motion builder
     */
    public MotionBuilder setEndColor(BasicColor endC) {
      this.endC = endC;
      return this;
    }

    /**
     * Builds the motion builder into a motion.
     * @return the target motion
     */
    public Motion build() {
      return new Motion(this);
    }
  }

  /**
   * Constructor for motion.
   * @param b is the motion builder
   */
  public Motion(MotionBuilder b) {
    this.tag = b.tag;
    this.shape = b.shape;
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

  public String getTag() {
    return tag;
  }

  public BasicShape getShape() {
    return shape;
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

  public void setTag(String tag) {
    this.tag = tag;
  }

  public void setStartTime(Integer startTime) {
    this.startTime = startTime;
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