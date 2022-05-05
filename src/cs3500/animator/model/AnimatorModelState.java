package cs3500.animator.model;

import java.util.List;
import java.util.TreeSet;

/**
 * Represents the state of a simple animation with basic shapes. Contains all necessary functions to
 * query the model to obtain the status of the shapes in the animation at a particular time.
 */
public interface AnimatorModelState {

  /**
   * Get a List of Shape that exist in the animation at the given time.
   *
   * @return a list of the shapes that exist at that time
   */
  List<Shape> getShapes(int time);

  /**
   * Getter for unique turning points of the animation.
   * @return treeset of discrete ticks
   */
  TreeSet<Integer> getDiscreteTime();

  /**
   * Get a Shape at a specific time in the animation.
   *
   * @param shapeID the ID of the shape to get
   * @param time    the time (in ticks) in the animation where the shape is returned
   * @return the shape at the given time in the animation
   * @throws IllegalArgumentException if no shape with that ID exists or if the shape doesn't exist
   *                                  at that time in the animation
   */
  Shape getShapeAtTime(String shapeID, int time);

  //get the total duration of the animation

  /**
   * Get the total duration of the animation in ticks. The animation starts at tick 0.
   *
   * @return the total duration of the animation, in ticks.
   */
  int getAnimationDuration();

  //get the id's of all the shapes that have been added to the animation
  // (regardless of when they exist)

  /**
   * Get a list of the shapeIDs that currently exist in the animation.
   *
   * @return a list of the string IDs of the shapes currently in the animation.
   */
  List<String> getShapesInAnimation();

  /**
   * Get the total width (x-dimension) of the animation in pixels. The width starts at pixel 0 which
   * is the left side of animation.
   *
   * @return the max x-dimension in the animation in pixels.
   */
  int getWidthOfAnimation();

  /**
   * Get the total height (y-dimension) of the animation in pixels. The height starts at 0
   * top/bottom of the animation.
   *
   * @return the max y-dimension of the animation in pixels.
   */
  int getHeightOfAnimation();

  /**
   * Get a list of the possible shapes that can be added to the animation, as well as the number of
   * integers required to define the size of the shape.
   *
   * @return A list of String where each String is a specific shape that can be added.
   */
  List<String> getPossibleShapesAndDefinitions();

  /**
   * Get the {@code AniShape} that represents this shape.
   *
   * @param shapeID the id of the shape to get animations for
   * @return the animated shape that represents this shape.
   */
  AniShape getShapeAsAniShape(String shapeID);

  /**
   * Gets the tempo at a specific tick, returns -1 if there is no slow-motion interval happening.
   * @param time is the tick to look up
   * @return the tempo at the current tick
   */
  int getTempoAt(int time);
}
