package cs3500.animator.view;

import cs3500.animator.controller.AnimationControllerFeatures;

/**
 * Represents the view part of the MVC pattern for this animation. The view represents what the user
 * sees in the entire animation. View could be just a textual representation, or a more complicated
 * windowed animation. Can directly access the model via a non-mutating interface to get info about
 * the animation.
 */
public interface SimpleAnimatorView extends TextualAnimatorView {

  /**
   * Make the view visible. This is usually called after the view is constructed
   */
  void makeVisible();

  /**
   * Signal the view to draw the animation at the indicated tick. The view will reference the model
   * to get the shapes as they exist at that time.
   */
  void refresh(int tick);

  /**
   * Get an AnimationControllerFeatures object which contains pointers to functions which an
   * interactive view can call to tell the controller about input from the user. This method may do
   * nothing if the specific implementation doesn't support callbacks.
   *
   * @param controller the controller controlling the view
   */
  void setCallbacks(AnimationControllerFeatures controller);


  /**
   * ToString Method that queries all the shapes in the model, getting their text history for
   * commands. Compiled into String that describes the entire animation. This method may do nothing
   * if the specific implementation doesn't support callbacks.
   */
  String toString();

  /**
   * Adds a new command, if possible and allowed via functionality.
   * @return a string that represents the inputted command
   */
  String getNewCommand();

  int getTempo();

  /**
   * Sets the new command.
   */
  void setNewCommand();
}
