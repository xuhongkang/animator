package cs3500.animator.model;

import java.util.List;

/**
 * Represents a shape that can be 'animated' with various animations.
 */
public interface AniShape {

  /**
   * Get a list of all the animations that act on this shape.
   *
   * @return a list of {@code AniCommand} that apply to this shape.
   */
  List<AniCommand> getCommandsOnShape();

  /**
   * Get the {@code Shape} that represents this shape as it exists at the start of its existence.
   *
   * @return the shape at the start of its life
   */
  Shape getStartingShape();

  /**
   * Get the time at which this shape starts (in ticks).
   *
   * @return the tick at which this shape will start to exist in the animation.
   */
  int getStartTime();

  /**
   * Get the time at which this shape ends (in ticks).
   *
   * @return the tick at which this shape will cease to exist in this animation
   */
  int getEndTime();
}
