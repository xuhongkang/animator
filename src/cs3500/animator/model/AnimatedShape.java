package cs3500.animator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a shape that can be animated in the animation. Stores all the animations that act upon
 * this specific shape at given times. Is able to return the shape at a given time, after it goes
 * through all necessary transformations via animations. Animations for a shape must be added in
 * ascending time order, though there can be gaps between each animation (the shape will stay the
 * same during those gaps)
 */
public class AnimatedShape implements AniShape {

  private final int stime; //the start time of the shape's existence in the animation
  private final int etime; //the end time of the shape's existence in the animation
  private final List<AniCommand> moveAnimations; //should be sorted by time
  private final List<AniCommand> colorAnimations; 
  private final List<AniCommand> scaleAnimations;
  private final Shape startingShape; //the shape as it exists at its creation

  /**
   * Constructor for an animated shape.
   *
   * @param stime         the starting time for the shape
   * @param etime         the ending time of the shape
   * @param startingShape the starting Shape that this class represents
   * @throws IllegalArgumentException if the stime is before tick 0, or if etime is before/same as
   *                                  stime, or if shape is null
   */
  public AnimatedShape(int stime, int etime, Shape startingShape) {
    if (stime < 0 || etime <= stime) {
      throw new IllegalArgumentException("invalid time for shape to exist");
    }
    if (startingShape == null) {
      throw new IllegalArgumentException("shape cannot be null");
    }
    this.stime = stime;
    this.etime = etime;
    this.startingShape = startingShape;
    //initialize the command lists
    this.moveAnimations = new ArrayList<>();
    this.colorAnimations = new ArrayList<>();
    this.scaleAnimations = new ArrayList<>();
  }

  /**
   * Copy constructor for cross actions.
   * @param sh is the animated shape to copy
   * @param shape is the shape
   */
  public AnimatedShape(AnimatedShape sh, Shape shape) {
    this.stime = sh.stime;
    this.etime = sh.etime;
    float ox = sh.startingShape.getPosition().getX();
    float oy = sh.startingShape.getPosition().getY();
    float nx = shape.getPosition().getX();
    float ny = shape.getPosition().getY();
    float dx = nx - ox;
    float dy = ny - oy;
    this.startingShape = shape;
    this.moveAnimations = this.copyMoveAnimations(sh.moveAnimations, dx, dy);
    this.colorAnimations = new ArrayList<>();
    this.colorAnimations.addAll(sh.colorAnimations);
    this.scaleAnimations = new ArrayList<>();
    this.scaleAnimations.addAll(sh.scaleAnimations);
  }

  /**
   * Copies the move animations for a different shape with offsets.
   * @param dx is the x offset
   * @param dy is the y offset
   * @return a list of ani commands
   */
  private List<AniCommand> copyMoveAnimations(List<AniCommand> ol, float dx, float dy) {
    if (ol.isEmpty()) {
      return new ArrayList<AniCommand>();
    }
    List<AniCommand> rList = new ArrayList<AniCommand>();
    for (int i = 0; i < ol.size(); i ++) {
      MoveAniCommand cmd = (MoveAniCommand) ol.get(i);
      rList.add(new MoveAniCommand(cmd, dx, dy));
    }
    return rList;
  }

  //get this shape at the time specified
  //need to walk through the sorted list of animations and modify the shape
  // then return shape at that time

  /**
   * Returns the shape as it exists at the given time.
   *
   * @param time the time to return the shape at
   * @return the shape as it exists at the given time (in ticks)
   * @throws IllegalArgumentException if the shape doesn't exist at that time
   */
  Shape getShapeAtTime(int time) {
    if (time < stime || time >= etime) {
      throw new IllegalArgumentException("cannot get shape outside the time range it exists");
    }
    Shape ans;
    ans = moveShapeToTime(time, moveAnimations, startingShape);
    ans = moveShapeToTime(time, colorAnimations, ans);
    ans = moveShapeToTime(time, scaleAnimations, ans);
    return ans;
  }

  /**
   * Walks through the given list of commands and applies their changes to the shape if the command
   * applies (fully or partially) before the indicated time.
   *
   * @param time    the time in ticks when the shape should be returned
   * @param cmdList the list of commands that apply to the shape
   * @return the shape at the specified time with all previous commands applied to the shape
   */
  private Shape moveShapeToTime(int time, List<AniCommand> cmdList, Shape shape) {
    Shape ans = shape;
    for (AniCommand cmd : cmdList) {
      if (time >= cmd.getEndTime()) {
        //command has fully applied to the animation
        ans = cmd.applyAnimation(ans);
      } else if (time < cmd.getStartTime()) {
        //have passed the last command that applied to the shape before this time.
        return ans;
      } else {
        //case where command is applying to the shape at this time.
        ans = cmd.getIntermediateShape(ans, time);
        //return the shape at this point because no two commands of the same type can apply
        return ans;
      }
    }
    return ans;
  }

  //add the given command to the list of animated commands
  //need to make sure it doesn't conflict with an existing command
  //can be added in any order, but make sure no conflicts

  /**
   * Add the given cmd to the correct list of animation commands. The command cannot overlap in time
   * with another command. Currently commands must be added in cronological order.
   *
   * @param cmd the command to add to the shape
   * @throws IllegalArgumentException if the command can't be added
   */
  void addAniCommand(AniCommand cmd) {
    if (cmd == null) {
      throw new IllegalArgumentException("cannot add null command");
    }
    AniCommand prevCmd;
    //need to check what type of command it is
    if (cmd instanceof MoveAniCommand) {
      prevCmd = moveAnimations.isEmpty() ? null : moveAnimations.get(moveAnimations.size() - 1);
      if ((prevCmd == null && !cmd.getStartPos().equals(startingShape.getPosition()))) {
        throw new IllegalArgumentException("starting shape position doesn't agree with cmd");
      }
      if (canAddCommand(cmd, prevCmd) && cmd.adjacentCommand(prevCmd)) {
        moveAnimations.add(cmd);
      } else {
        throw new IllegalArgumentException("cannot add command due to overlap in time or "
            + "teleporting shape values");
      }
    } else if (cmd instanceof ColorAniCommand) {
      prevCmd = colorAnimations.isEmpty() ? null : colorAnimations.get(colorAnimations.size() - 1);
      if ((prevCmd == null && !cmd.getStartColor().equals(startingShape.getColor()))) {
        throw new IllegalArgumentException("starting shape color doesn't agree with cmd");
      }
      if ((canAddCommand(cmd, prevCmd) && cmd.adjacentCommand(prevCmd))) {
        colorAnimations.add(cmd);
      } else {
        throw new IllegalArgumentException("cannot add command due to overlap in time or "
            + "teleporting shape values");
      }
    } else if (cmd instanceof ScaleAniCommand) {
      prevCmd = scaleAnimations.isEmpty() ? null : scaleAnimations.get(scaleAnimations.size() - 1);
      if ((prevCmd == null && (cmd.getStartWidth() != startingShape.getWidth()
          || cmd.getStartHeight() != startingShape.getHeight()))) {
        throw new IllegalArgumentException("starting shape dimensions don't agree with cmd");
      }
      if (canAddCommand(cmd, prevCmd) && cmd.adjacentCommand(prevCmd)) {
        scaleAnimations.add(cmd);
      } else {
        throw new IllegalArgumentException("cannot add command due to overlap in time or "
            + "teleporting shape values");
      }
    } else {
      throw new IllegalArgumentException("cannot recognize given command");
    }
  }

  /**
   * check if the command can be added based on its start time.
   *
   * @param cmd     the command to check if it can be added
   * @param prevCmd the previous command in the list (can be null)
   * @return true if the command can be added, false otherwise
   */
  private boolean canAddCommand(AniCommand cmd, AniCommand prevCmd) {
    //check if the new command starts later than the ending time of the previous command
    if (prevCmd == null) {
      //no command to check against, return 0
      return true;
    }
    if (prevCmd.getEndTime() > cmd.getStartTime()) {
      throw new IllegalArgumentException("Command must occur after previously added commands");
    }
    return prevCmd.getEndTime() <= cmd.getStartTime();
  }

  @Override
  public List<AniCommand> getCommandsOnShape() {
    List<AniCommand> ans = new ArrayList<>();
    ans.addAll(this.moveAnimations);
    ans.addAll(this.colorAnimations);
    ans.addAll(this.scaleAnimations);
    return ans;
  }

  @Override
  public Shape getStartingShape() {
    return startingShape;
  }

  @Override
  public int getStartTime() {
    return stime;
  }

  //will be -1 if the shape doesn't end

  /**
   * Get the ending time for this shape. Will be -1 if the shape doesn't have an ending time.
   *
   * @return the tick at which this shape will cease to exist in this animation.
   */
  public int getEndTime() {
    return etime;
  }

  /**
   * Remove all animations associated with this shape.
   */
  void removeAllAnimations() {
    this.moveAnimations.clear();
    this.colorAnimations.clear();
    this.scaleAnimations.clear();
  }

  //will have a shape id, "string"?
  //enum for the shape type
  //then a list of AniCommand for the shape's actions over time
  //interface for an animated shape?

  //a getter method for the shape at a specific time
  //so Shape is a value object. with an enum and various attributes that can be polled.
  //only need to care about the shape when it is drawn, so that belongs to the view
  //to-string method returns the shape and the actions of the shape I guess

}
