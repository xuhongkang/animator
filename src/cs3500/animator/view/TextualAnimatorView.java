package cs3500.animator.view;

/**
 * Represents the view part of the MVC pattern for this animation. The view represents what the user
 * sees in the entire animation. Currently the view only represents a textual description of the
 * animation. Directly accesses the model via a non-mutating interface to get info about the
 * animation.
 */
public interface TextualAnimatorView {

  /*
   * DIFFERENT VIEWS. VIEW 1: tostring method (need to subst ticks for seconds based on tempo) VIEW
   * 2: makeVisible setCallback(probably not in this assignment?) - later on refresh/paint method
   * (potentially taking a tick in) - draw the animation at the specified tick VIEW 3: make_svg -
   * can be the toString method interface for view 1&3: TextualAnimatorView interface for view 2:
   * ComplexAnimatorView (extends other view) ToString Method that queries all the shapes in the
   * model, getting their text history for commands.
   */

  /*
   *
   * Example ToString:
   * Shape: R, RECTANGLE
   * Move Commands
   *     Start  ->  End
   *   ------------------
   *     t   x   y   ->   t   x   y
   *
   * Color Commands
   *     Start  ->  End
   *   ------------------
   *     t   r   g   b    ->   t   r   g   b
   *
   * Scale Commands
   *     Start  ->  End
   *   ------------------
   *     t  d1  d2    ->   t  d1  d2
   *     4,  5,  10  ->  6,   4,   15
   *
   * Shape: S, SQUARE
   * Move Commands
   *     Start  ->  End
   *   ------------------
   *     t   x   y   ->   t   x   y
   *     2,  0,  0   ->  3,  1,  3
   *
   * Color Commands
   *     Start  ->  End
   *   ------------------
   *     t   r   g   b    ->   t   r   g   b
   *
   * Scale Commands
   *     Start  ->  End
   *   ------------------
   *     t  d1    ->   t  d1
   *
   * Shape: C, CIRCLE
   * Move Commands
   *     Start  ->  End
   *   ------------------
   *     t   x   y   ->   t   x   y
   *
   * Color Commands
   *     Start  ->  End
   *   ------------------
   *     t   r   g   b    ->   t   r   g   b
   *
   * Scale Commands
   *     Start  ->  End
   *   ------------------
   *     t  d1    ->   t  d1
   *
   * Shape: O, OVAL
   * Move Commands
   *     Start  ->  End
   *   ------------------
   *     t   x   y   ->   t   x   y
   *
   * Color Commands
   *     Start  ->  End
   *   ------------------
   *     t   r   g   b    ->   t   r   g   b
   *     2,  0,  0,  255   ->  7,  0,  0,  0
   *
   * Scale Commands
   *     Start  ->  End
   *   ------------------
   *     t  d1  d2    ->   t  d1  d2
   */

  /**
   * Textual representation of the animation.
   *
   * @return A representation of the animation as 1 giant string. Content and formatting is
   *         implementation specific.
   */
  String toString();

}
