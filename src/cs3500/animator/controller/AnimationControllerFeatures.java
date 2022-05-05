package cs3500.animator.controller;

/**
 * Represents a list of methods that the controller supports for changing an animation.
 * This list of methods is primarily for the view to use to signal the controller when something
 * has happened and action needs to be taken.
 */
public interface AnimationControllerFeatures {

  void startSlowMo(int tempo);

  void resumeNormalSpeed();

  /**
   * Increase the speed of the animation (in ticks per second) by implementation specific amt.
   * Cannot increase beyond Integer.MAX_VALUE
   */
  void increaseSpeed();

  /**
   * Decrease the speed of the animation (in ticks per second) by implementation specific amt.
   * Cannot decrease to below 1 tick per second.
   */
  void decreaseSpeed();

  /**
   * Set the speed of the animation in ticks per second.
   * Cannot set to less than 1 tick per second.
   * @param tps the ticks per second to set the animation speed to
   */
  void setAnimationSpeed(int tps);

  /**
   * Resume playing the animation. Does nothing if the animation is already playing.
   */
  void resumeAnimation();

  /**
   * Pause animation playing. Does nothing if the animation is already paused.
   */
  void pauseAnimation();

  /**
   * Go to the beginning of the animation. Will not change animation playing status.
   */
  void goToBeginning();

  /**
   * Toggle the looping status of the animation. If the status was true, will be set to false and
   * vice versa.
   */
  void toggleLooping();

  /**
   * Adds a new command, shape/motion to the existing animation.
   */
  void addNewCommand();
}
