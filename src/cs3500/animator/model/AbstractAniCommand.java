package cs3500.animator.model;

//should these be in a different package?

import java.awt.Color;

/**
 * Represents an abstract animation command.
 */
abstract class AbstractAniCommand implements AniCommand {

  //the shape is known in the parent class (AnimatedShape),
  // and passed into the functions that apply this command
  protected final int startTime;
  protected final int endTime;

  protected AbstractAniCommand(int sTime, int eTime) {
    this.startTime = sTime;
    this.endTime = eTime;
  }

  /**
   * get the ratio for how far along in the command you should be at a given time.
   *
   * @param time the time in the animation, in ticks.
   * @return the ratio (between 0 and 1) for how far along in the command you are
   */
  protected float getDT(int time) {
    if (time < startTime || time > endTime) {
      throw new IllegalArgumentException("time must be in between the "
          + "starting and ending time for this animation");
    }
    return ((float) (time - startTime) / (endTime - startTime));
  }

  //not really needed, since protected is package visible?
  public int getStartTime() {
    return this.startTime;
  }

  public int getEndTime() {
    return this.endTime;
  }

  //get the shape at a specific time
  //time is assumed to be offset from the startTime of the shape


  @Override
  public Position2D getStartPos() {
    return null;
  }

  @Override
  public Position2D getEndPos() {
    return null;
  }

  @Override
  public Color getStartColor() {
    return null;
  }

  @Override
  public Color getEndColor() {
    return null;
  }

  @Override
  public Float getStartWidth() {
    return null;
  }

  @Override
  public Float getStartHeight() {
    return null;
  }

  @Override
  public Float getEndWidth() {
    return null;
  }

  @Override
  public Float getEndHeight() {
    return null;
  }
}
