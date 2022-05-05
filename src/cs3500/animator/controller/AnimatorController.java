package cs3500.animator.controller;

/**
 * A controller for an Animation. Follows the MVC design pattern. Decides when things happen in an
 * animation. Needs to know the speed at which to play the animation.
 */
public interface AnimatorController {

  /**
   * Play the animation from the beginning. Will end once the animation finishes.
   */
  void playAnimation();

}
