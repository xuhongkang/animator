package cs3500.animator.model;

import java.awt.Color;
import java.util.ArrayList;

/**
 * An AnimatorModel represents a model for a simple animation. The model stores shapes and their
 * respective animations over the entire length of an animation. The model also stores the
 * dimensions of the animation. Shapes can be added to the model as well as animations on those
 * shapes. The model can be queried to get the shapes that exist at a specific time (ticks) in the
 * animation. Animator model takes in the size of the animation (x and y dimensions in pixels) as
 * well as a duration for the animation (in ticks).
 */
public interface AnimatorModel extends AnimatorModelState {
  //starts at time 0, duration taken in via constructor //may change later?
  //add shape to animation at given position

  /**
   * Add the given shape to the animation, starting at the specified time.
   *
   * @param shape     the shape to add to the animation.
   * @param startTime the time at which the shape should first appear in the animation.
   * @param endTime   time at which shape will be removed from animation
   * @throws IllegalArgumentException if the startTime is invalid(not during the animation), or the
   *                                  shape's position is invalid (outside the animation)
   */
  void addShape(Shape shape, int startTime, int endTime);
  //added an end time because animated shape needs an end time

  //remove the shape from the animation at the given time

  /**
   * Adds a new layer with assigned shape names.
   * @param layerName is the name of the layer
   * @param shapeIDs is an Arraylist of all the shape ids
   */
  void addLayer(String layerName, ArrayList<String> shapeIDs);

  /**
   * Removes the shape from the animation. The shape will no longer exist in the animation.
   *
   * @param shapeID the shape to remove
   * @throws IllegalArgumentException if the shapeID is invalid.
   */
  void removeShape(String shapeID);

  //todo abstract the commands? could do in the future
  //add a momvement to the animation at the end, shape can't teleport
  //might be a way to abstract it, but do it this way atm.

  /**
   * Add a movement command to a specified shape.
   *
   * @param shapeID the ID of the shape to add the command to
   * @param stime   the starting time of the command
   * @param etime   the ending time of the command
   * @param start   the starting positon of the shape
   * @param dest    the ending position of the shape
   * @throws IllegalArgumentException if the shape doesn't exist, the stime or etime are invalid, or
   *                                  the starting position of the shape doesn't agree with the
   *                                  ending position of shape after any previous command or the
   *                                  dest position is outside the animation.
   */
  void addMoveCmd(String shapeID, int stime, int etime, Position2D start, Position2D dest);
  //maybe have another version of the above without stime, which assumes the command
  // takes place right after last command?

  //add a change of color command to the animation

  /**
   * Add a change of color command from Color start to Color dest to the specified shape to take
   * place from stime to etime.
   *
   * @param shapeID the ID of the shape to change color
   * @param stime   the time at which the color change should start
   * @param etime   the time at which the color change should end
   * @param start   the starting color of the shape
   * @param dest    the ending color of the shape
   * @throws IllegalArgumentException if the shapeID is invalid, the stime or etime are outside the
   *                                  animation, or if the starting color doesn't agree with the
   *                                  shape's previous color
   */
  void addColorCmd(String shapeID, int stime, int etime, Color start, Color dest);

  //changes the size of the shape (depending on the shape multiple size ints may be needed)

  /**
   * Add a change of size command to the specified shape from size start to size dest to take place
   * over stime to etime.
   *
   * @param shapeID     the ID of the shape to add the command to.
   * @param stime       the starting time for the command
   * @param etime       the ending time for the command
   * @param startWidth  the starting width of the shape
   * @param startHeight the starting height of the shape
   * @param endWidth    the ending width of the shape
   * @param endHeight   the ending height of the shape
   * @throws IllegalArgumentException if the shapeID doesn't exist, the stime or etime aren't in the
   *                                  animation or conflict with another command, if the starting
   *                                  width or heigh don't agree with the previous shape.
   */
  void addSizeCmd(String shapeID, int stime, int etime, float startWidth, float startHeight,
      float endWidth, float endHeight);


  /**
   * Remove all animations associated with a shape, the shape itself still exists.
   *
   * @param shapeID the id of the shape to remove all animations from.
   * @throws IllegalArgumentException if the shape doesn't exist in the animation
   */
  void removeAnimations(String shapeID);

  /**
   * Adds the slow motions interval to the model.
   * @param sTime is the starting time
   * @param eTime is the ending time
   * @param tempo is the tempo
   */
  void addSlowMo(int sTime, int eTime, int tempo);


  //returns a list of the different shapes supported by the model along with the size
  // definitions for that shape


  /*
  old thinking
  Called by the controller:
  Model, gets commands to add shapes
  Also add a movement for a shape.

  View:
  play function, where it asks the model at a given time where all the shapes are and their statuses


  Ex:
  at time 10, make a square at 0,0
  at time 15, move square to 10,10 over 5 seconds.
  at time 30, finish

   */

}
