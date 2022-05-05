package cs3500.animator.model;

import java.awt.Color;

/**
 * General Animation that acts on a shape to transform one of its attributes. The animation can
 * occur over 0 time (start and end time are the same), essentially 'teleporting' the shape.
 */
public interface AniCommand {

  /**
   * Apply the entire animation to the given shape.
   *
   * @param shape Shape to apply this command to.
   * @return a Shape with entire animation command applied
   */
  Shape applyAnimation(Shape shape);

  /**
   * interpolate the command to find the resulting shape at the time specified.
   *
   * @param shape Shape to partially apply this command to
   * @param time  the time during the animation at which the shape should be returned
   * @return a Shape with part of the animation applied to it
   */
  Shape getIntermediateShape(Shape shape, int time);

  /**
   * Get the tick when this command starts.
   *
   * @return the start time of the animation, in ticks.
   */
  int getStartTime();

  /**
   * Get the tick when this command ends.
   *
   * @return the end time of this animation, in ticks.
   */
  int getEndTime();

  /**
   * Does this command have the same starting characteristics as the given command's ending ones.
   *
   * @param that the previous command to happen (may be null)
   * @return if the commands share the same relavent characteristics (position2D for move, etc...)
   * @throws IllegalArgumentException if the given AniCommand isn't the same type as the called
   *                                  one.
   */
  boolean adjacentCommand(AniCommand that);

  /**
   * Get the starting position of the command, or null if the command doesn't affect position.
   *
   * @return the starting position of the animation.
   */
  Position2D getStartPos();

  /**
   * Get the ending position of the command, or null if the command doesn't affect position.
   *
   * @return the ending position of the animation.
   */
  Position2D getEndPos();

  /**
   * Get the starting color of the command, or null if the command doesn't affect color.
   *
   * @return the starting color of the animation.
   */
  Color getStartColor();

  /**
   * Get the ending color of the command, or null if the command doesn't affect color.
   *
   * @return the ending position of the animation.
   */
  Color getEndColor();

  /**
   * Get the starting width of the command, or null if the command doesn't affect width.
   *
   * @return the starting width of the animation.
   */
  Float getStartWidth();

  /**
   * Get the starting height of the command, or null if the command doesn't affect height.
   *
   * @return the starting height of the animation.
   */
  Float getStartHeight();

  /**
   * Get the ending width of the command, or null if the command doesn't affect width.
   *
   * @return the ending width of the animation.
   */
  Float getEndWidth();

  /**
   * Get the ending height of the command, or null if the command doesn't affect height.
   *
   * @return the ending height of the animation.
   */
  Float getEndHeight();

}
